package renderEngine;

import java.util.List;
import java.util.Map;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;
import entities.Entity;

public class EntityRenderer 
{
	
	/*private static final float FOV = 70; 				//The angle for your projection matrix (see notes for details on what it does)
	private static final float NEAR_PLANE = 0.1f;		//The near plane for your projection matrix
	private static final float FAR_PLANE = 1000;		//The far plane (ie: how far into the distance you can see)
	
	//private Matrix4f projectionMatrix;
	 * 
	 */
	private StaticShader shader;
	
	// ================================================= CONSTRUCTOR =========================================================================
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		/*//Prevents triangles with normalVectors directly away from the camera from being rendered (prevents rendering of non vissible triangles)
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		*/
		//
		//createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	// ======================== MOVED TO MASTERRENDERER (NEEDED FOR ALL KINDS OF RENDERING NOT JUST MODELS/ENTITIES) ========================
	
	/*public void prepare()
	{
		//DepthTest would be our Z-buffer thingy me thinks
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);  //DEPTH BUFFER BIT CLEARS OUR DEPTH BUFFER PER FRAME
		GL11.glClearColor(0, 0, 0, 1);		//THIS SETS THE COLOUR OF OUR DISPLAY
	}	*/
	
	public void render(Map<TexturedModel, List<Entity>> entities)
	//OBS changed the code since the original code (dead code in comments bellow just to be safe) required binding
	//and unbinding the exact same data every time you wanted to render identical objects rather (runs once per frame
	//so this got kinda annoying when I tried to make many of that stupid mokeyman(yup still looks like a monkey to me Hallvard!)
	{
		for(TexturedModel model : entities.keySet())
			//loops through all of the keys in our hashMap (god I love hashMaps <3 ) (ps it's also 2at night so yay me for being hard working I guess) (pps. useless random sleep depraved comments are useless and random (and long as fuck))
		{
			//prepares our model (DUH)
			prepareTexturedModel(model);
			//gets all entities that use the same model and adds them to a list
			List<Entity> batch = entities.get(model);
			for(Entity entity : batch)
			//loops through all of the entities in our list of entities (for-loop-ception)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			//once it's done rendering all of the instances of one entity it unbinds our model and goes back into the original loop
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = texturedModel.getTexture();
		if(texture.isHasTransparency())
		{
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
	}
	
	private void unbindTexturedModel()
	{
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);	
	}
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	/*public void render(Entity entity, StaticShader shader)
	{
		TexturedModel texturedModel = entity.getModel();
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = texturedModel.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	} */   //OUR GLORIOUS ORIGINAL RENDERER (NOT CRASH PROOF AT ALL ;P)
	//YES YES DEAD CODE IS NOT A GREAT THING BUT I AM STILL PARANOID I FUCKED SOMETHING UP SO THIS STAYS UNTILL I FEEL CONFIDENT
	//(Just like how I have my old OBJLoader in OneNote)
	
	/*private void createProjectionMatrix()
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
	*/						//OBS ALSO MOCED TO MASTER RENDERER SINCE ALL RENDERING REQUIRES A PROJECTION MATRIX
}
