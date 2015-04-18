package engineTester;

import java.util.ArrayList;
import java.util.List;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import animationTests.Animation;
import entities.Camera;
import entities.Entity;
import entities.Light;
import renderEngine.AnimationLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class GameTester 
{
	public static void main(String[] args) 
	{
		DisplayManager.createDisplay();
		DisplayManager.printDimentions();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
		List<String> models = new ArrayList<>();
		List<Entity> animation = new ArrayList<>();
		
		for(int i = 1; i < 11; i++) 
		{
			models.add("animationTest"+i);
		}
		
		Animation testAnimation = new Animation(models);
		
		RawModel model = OBJLoader.loadObjModel("animationTest1", loader);
		TexturedModel finalModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		Entity entity = new Entity(finalModel, new Vector3f(0, 0, -20), 0, 0, 0, 1);
		
		for(int i = 0; i < models.size(); i++)
		{
			Entity entity2 = testAnimation.animation2(i, loader, entity, models);
			animation.add(entity2);
		}
		
		Light light = new Light(new Vector3f(0, 0, -20) , new Vector3f(1, 1, 1));
		
		//Camera camera = new Camera(entity);
		
		int i = 0;
		
		long currentTime = 0;
		int lastI = -1;
		
		boolean back = false;
		
		while(!Display.isCloseRequested())
		{
			if(i < models.size() && lastI != i)
			{
				Vector3f position = entity.getPosition();
				float rotX = entity.getRotX();
				float rotY = entity.getRotY();
				float rotZ = entity.getRotZ();
				float scale = entity.getScale();
				//entity = testAnimation.animation2(i, loader, entity, models);
				entity = animation.get(i);
				entity.setPosition(position);
				entity.setRotX(rotX);
				entity.setRotY(rotY);
				entity.setRotZ(rotZ);
				entity.setScale(scale);
				System.out.println(i);
			}
			else if(i < models.size() * 2 && lastI != i)
			{
				//entity = testAnimation.animation(i - 10, loader, entity);
				System.out.println(i);
			}
			//camera.move();
			entity.increaseRotation(0, 0, 0);
			//renderer.render(light, camera);
			renderer.processEntity(entity);
			DisplayManager.updateDisplay();
			if(i > models.size()*2)
			{
				i = 0;
				System.out.println("a");
			}
			else if (currentTime + 50 < System.currentTimeMillis())
			{
				currentTime = System.currentTimeMillis();
				i++;
				System.out.println("b");
			}
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
