package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Player extends Entity
{
	//Units per second
	private static final float RUN_SPEED = 20;
	//Degrees per second
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private final float JUMP_POWER = 30;
	
	//TODO Propper non hardcoded terrain detection
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean hasJumped = false;

	public Player(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		// TODO Auto-generated constructor stub
	}
	
	public void move()
	{
		checkInputs();
		//OBS only rotating aroun y-axis because rotating your character around the x or z axis
		//in normal movement is just plain silly
		//PS: multiplying the turnSpeed by getFrameTimeSeconds since we want a continuous movement
		//speed per seconds independently of our framerate and getFrameTimeSeconds is how many
		//seconds since last frame. (and obviously last frame was last time we called this method
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		//Same logic to speed * getFrameTimeSeconds();
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		//Basic trigonometry for a right angle tiangle where we know the hypotenus (distance)
		//and the angle between the hypotenus and one of the hypotenus(? not sure about the english name :P)
		//is the rotation around the y axis
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		if(super.getPosition().y < TERRAIN_HEIGHT)
		{
			upwardsSpeed = 0;
			hasJumped = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	private void jump()
	{
		if(!hasJumped)
		{
			this.upwardsSpeed = JUMP_POWER;
			hasJumped = true;
		}
	}
	
	private void checkInputs()
	//OBS did this rather than the previous camera verson for 2 reasons:
	//1: I think this will let me work around moving along the axis which made rotating the camera a nightmare
	//2: It is more logical to look for player movement commands in the player class rather than the camera
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			currentSpeed = RUN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			currentSpeed = -RUN_SPEED;
		}
		else
		{
			currentSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			currentTurnSpeed = TURN_SPEED;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			currentTurnSpeed = -TURN_SPEED;
		}
		else
		{
			currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			jump();
		}
	}
}
