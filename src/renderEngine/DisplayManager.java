package renderEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager 
{
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void printDimentions()
	//OBS for testing only
	{
		System.out.println("Width: " + screenSize.getWidth() + " Height: " + screenSize.getHeight());
	}
	
	
	//private static final int WIDTH = 1280;
	//private static final int HEIGHT = 720;
	
	private static final int WIDTH = (int) screenSize.getWidth();
	private static final int HEIGHT = (int) screenSize.getHeight();
	private static final int FPS_CAP = 120;
	
	//these track the time between frames allowing us to create a world where framerate does not
	//change the percieveable time in the game (movements based on actual time not framerate etc)
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay()
	{	
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		
		try {
			Display.setTitle("FF Clone in progress");
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			//TODO fullscreen
			//Need to figure out rendering before implementing this
			//Display.setResizable(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		
		//TELLS OPENGL TO USE THE WHOLE DISPLAY TO RENDER THE GAME IN!
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		//The time (in seconds {hence / 1000}) it took to render the last frame
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getFrameTimeSeconds()
	{
		return delta;
	}
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	public static Dimension getScreenSize()
	{
		return screenSize;
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();  
		//Better with Sys.getTime() * 1000 to keep it all in lwjgl?
		//Changed from System.currentTimeMillis()
		//That change made a huge difference so find out why at some point
	}
}
