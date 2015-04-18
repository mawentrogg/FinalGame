package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer 
{
	private static final float FOV = 70; 				//The angle for your projection matrix (see notes for details on what it does)
	private static final float NEAR_PLANE = 0.1f;		//The near plane for your projection matrix
	private static final float FAR_PLANE = 1000;		//The far plane (ie: how far into the distance you can see)
	
	private static final float RED = 0.2f;
	private static final float GREEN = 0.4f;
	private static final float BLUE = 1.0f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	//A hashMap is basically the java equivalent of a python dictionary
	private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
	
	private List<Terrain> terrains = new ArrayList<>();
	
	
	// =================================================== CONSTRUCTOR =======================================================
	
	public MasterRenderer()
	{
		//Prevents triangles with normalVectors directly away from the camera from being rendered (prevents rendering of non vissible triangles)
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public static void enableCulling()
	{
		//Prevents triangles with normalVectors directly away from the camera from being rendered (prevents rendering of non vissible triangles)
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Light sun, Camera camera)
	//OBS changed it to compensate for the possibility that we would want multiple lightsources in the same scene (wich we obviously do) 
	//OBS OBS Currently only works for the last light loaded in
	{
		prepare();
		shader.start();
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLight(sun);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		//this is important since it calls once per frame (trust me it crashed Eclipse when I let it run without emptying, not fun [frame rate goes to shit FAST])
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity)
	//Sorts our entity into the list (list is in the hashMap with it's model as it's key)
	{
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
		//if there is an existing list for this model allready
		{
			batch.add(entity);
		}
		else
		{
			//makes a new list
			List<Entity> newBatch = new ArrayList<>(); //Diamond memory I love you so much!
			//Puts the entity in our new list
			newBatch.add(entity);
			//adds our new list to our hashMap with it's model as the key
			entities.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public void prepare()
	{
		//DepthTest would be our Z-buffer thingy me thinks
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  //DEPTH BUFFER BIT CLEARS OUR DEPTH BUFFER PER FRAME
		GL11.glClearColor(RED, GREEN, BLUE, 1);		//THIS SETS THE COLOUR OF OUR DISPLAY (Try 0.1f, 0.4f, 1, 1)
	}
	
	private void createProjectionMatrix()
	{
		//The (float) is casting to float meaning it takes a value that is convertable to a float (here a double) and makes it a float instead (me thinks)
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight(); 
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;  // This is the length of the pyramide box thingy your vission is in (see notes)
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale; 	//Sets row 0 column 0 in our matrix to (1/tan(fov/2))/a (See notes)
		projectionMatrix.m11 = y_scale;		//Sets row 1 column 1 in our matrix to 1/tan(fov/2)		(same notes motherfucker)
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE)/frustrum_length);		//BETTER AS (-(FAR_PLANE + NEAR_PLANE))/frustrum_length; ???
		//Sets row 2 column 2 in our matrix to -((FAR_PLANE + NEAR_PLANE)/(FAR_PLANE - NEAR_PLANE))
		projectionMatrix.m23 = -1;			//Sets row 3 column 2 (OBS) in our matrix to -1  	(to convert from right to left coordinates (SEE NOTES ASSHOLE)
		projectionMatrix.m32 = -((2*NEAR_PLANE * FAR_PLANE)/frustrum_length);
		projectionMatrix.m33 = 0;
	}
	
	/*public void adjustProjectionMatrix()
	{
		//The (float) is casting to float meaning it takes a value that is convertable to a float (here a double) and makes it a float instead (me thinks)
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight(); 
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustrum_length = FAR_PLANE - NEAR_PLANE;  // This is the length of the pyramide box thingy your vission is in (see notes)
				
		//projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale; 	//Sets row 0 column 0 in our matrix to (1/tan(fov/2))/a (See notes)
		projectionMatrix.m11 = y_scale;		//Sets row 1 column 1 in our matrix to 1/tan(fov/2)		(same notes motherfucker)
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE)/frustrum_length);		//BETTER AS (-(FAR_PLANE + NEAR_PLANE))/frustrum_length; ???
		//Sets row 2 column 2 in our matrix to -((FAR_PLANE + NEAR_PLANE)/(FAR_PLANE - NEAR_PLANE))
		projectionMatrix.m23 = -1;			//Sets row 3 column 2 (OBS) in our matrix to -1  	(to convert from right to left coordinates (SEE NOTES ASSHOLE)
		projectionMatrix.m32 = -((2*NEAR_PLANE * FAR_PLANE)/frustrum_length);
		projectionMatrix.m33 = 0;
	}	*/
}
