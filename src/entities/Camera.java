package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,5,0);
	
	//Camera's rotation around the x,y and z axis
	private float pitch = 0;		//how high or low the camera is aiming
	private float yaw = 0;			//How much left or right the camera is aiming
	private float roll = 0;			//How much the camera is tilted (180 is upside down)
	
	private float Speed = 0.2f;
	
	private Player player;
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	/*public Camera(float x, float y, float z)
	{
		this.position = new Vector3f(x, y, z);
	}	*/
	
	/*public void move()
	//Moves the camera (DUH!)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z -= Speed;//0.20f;	//0.02f
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += Speed;//0.2f;	//0.02f
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)||Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			position.x -= Speed; //0.2f;	//0.02f
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)||Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			position.x += Speed; //0.2f;	//you get it it's the same for all of them
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			position.y -= Speed; //0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			position.y += Speed; //0.2f;
		}
	}
	*/
	
	public void move()
	{
		changeZoom();
		changeAngleAroundPlayer();
		changePitch();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		//This makes the camera always aim at the player (see notes)
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	//DOWN LEFT = -
	// + / - 15 instead of Display.get(Width/Height)/x
	public void rotate()
	{
		if(Mouse.getY() <= Display.getHeight()/2 - Display.getHeight()/20)	//345
		{
			float pitchSpeed = 0.001f*(Display.getHeight()/2 - Mouse.getY());		//360
			this.pitch += pitchSpeed;
		}
		if(Mouse.getY() >= Display.getHeight()/2 + Display.getHeight()/20)	// 375
		{
			float pitchSpeed = 0.001f*(Display.getHeight()/2 - Mouse.getY());	//(Display.getHeight() - Mouse.getY());  //360
			this.pitch += pitchSpeed;
		}
		if(Mouse.getX() >= Display.getWidth()/2 + Display.getWidth()/20)		//655
		{
			float yawSpeed = 0.001f*(Mouse.getX() - Display.getWidth()/2);		//640
			this.yaw += yawSpeed;
		}
		if(Mouse.getX() <= Display.getWidth()/2 - Display.getWidth()/20)		//625
		{
			float yawSpeed = 0.001f*(Mouse.getX() - Display.getWidth()/2);		//(Mouse.getX() - Display.getWidth());		//640
			this.yaw += yawSpeed;
		}
	}
	
	public void setSpeed(float Speed)
	{
		this.Speed = Speed;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance)
	{
		float totalAngle = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(totalAngle)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(totalAngle)));
		//The offsets are subtracted since they are i the negative x & Z directions!
		this.position.x = player.getPosition().x - offsetX;
		this.position.z = player.getPosition().z - offsetZ;
		//OBS Random value here is y offset
		this.position.y = player.getPosition().y + verticalDistance + 1.5f;
		//TODO Remove once this can be tested with a scrolwheel!
		if(position.y <= 1)
		{
			position.y = 1;
		}
	}
	
	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void changeZoom()
	{
		float zoom = Mouse.getDWheel() * 0.1f;
		//Zooms out by scrolling down
		distanceFromPlayer -= zoom;
		if(distanceFromPlayer <= 6)
		{
			distanceFromPlayer = 6;
		}
	}
	
	private void changePitch()
	{
		//OBS Mouse 0 = LMB, Mouse 1 = RMB
		if(Mouse.isButtonDown(1))
		{
			//DY = delta y = change in y position
			float pitchChange = Mouse.getDY() * 0.1f;
			//Increases the pitch if mouse is moved down
			pitch -= pitchChange;
			//Temorary values change if needed
			if(pitch <= -20)
			{
				pitch = -20;
			}
			else if(pitch >= 20)
			{
				pitch = 20;
			}
		}
	}
	
	private void changeAngleAroundPlayer()
	{
		//OBS might be better as button 0 (rotation when LMB is down)
		if(Mouse.isButtonDown(1))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
}
