package animationTests;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import entities.Entity;

public class Animation 
{
	//TexturedModel modelPre;
	//TexturedModel modelPost;
	
	List<String> animations = new ArrayList<>();
	
	public Animation(String... animations)
	{
		for(String animation : animations)
		{
			this.animations.add(animation);
		}
	}
	
	public Animation(List<String> animations)
	{
		this.animations = animations;
	}
	
	/*public Animation(TexturedModel modelPre, TexturedModel modelPost)
	{
		this.modelPre = modelPre;
		this.modelPost = modelPost;
	}	*/
	
	/*public void animationsasda(Entity entity, Loader loader)
	{
		for(int i = 0; i < models.size() - 1; i++)
		{
			int time = 1;
			RawModel RawModelPre = models.get(i).getRawModel();
			RawModel RawModelPost = models.get(i+1).getRawModel();
			float[] vertexArrayPre = RawModelPre.getVertexArray();
			float[] vertexArrayPost = RawModelPost.getVertexArray();
			float[] normalsArrayPre = RawModelPre.getNormalsArray();
			float[] normalsArrayPost = RawModelPost.getNormalsArray();
			float[] texturesArrayPre = RawModelPre.getTexturesArray();
			float[] texturesArrayPost = RawModelPost.getTexturesArray();
			int[] indicesArrayPre = RawModelPre.getIndicesArray();
			for(int o = 0; o <= vertexArrayPre.length - 1; o++)
			{
				float currentVertex = vertexArrayPre[o];
				float currentNormal = normalsArrayPre[o];
				//float currentTexture = texturesArrayPre[o];
				for(int t = 0; t <= time; t++)
				{
					vertexArrayPre[o] += (vertexArrayPost[o] - currentVertex)/time;
					normalsArrayPre[o] += (normalsArrayPost[o] - currentNormal)/time;
					//texturesArrayPre[o] += (texturesArrayPost[o] - currentTexture)/time;
					RawModel rawModel = loader.loadToVAO(vertexArrayPre, texturesArrayPre, normalsArrayPre, indicesArrayPre);
					TexturedModel model = new TexturedModel(rawModel, models.get(i).getTexture());
					entity = new Entity(model, entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
				}
				vertexArrayPre[o] = currentVertex;
				normalsArrayPre[o] = currentNormal;
				//texturesArrayPre[o] = currentTexture;
			}
		}
	} */
	
	/*public void animation(Entity entity, Loader loader)
	{
		int time = 1;
		RawModel rawModelPre = modelPre.getRawModel();
		RawModel rawModelPost = modelPost.getRawModel();
		float[] vertexArrayPre = rawModelPre.getVertexArray();
		float[] vertexArrayPost = rawModelPost.getVertexArray();
		float[] normalsArrayPre = rawModelPre.getNormalsArray();
		float[] normalsArrayPost = rawModelPost.getNormalsArray();
		float[] texturesArrayPre = rawModelPre.getTexturesArray();
		float[] texturesArrayPost = rawModelPost.getTexturesArray();
		int[] indicesArrayPre = rawModelPre.getIndicesArray();
		for(int o = 0; o <= vertexArrayPre.length - 1; o++)
		{
			float currentVertex = vertexArrayPre[o];
			float currentNormal = normalsArrayPre[o];
			//float currentTexture = texturesArrayPre[o];
			for(int t = 0; t <= time; t++)
			{
				vertexArrayPre[o] += (vertexArrayPost[o] - currentVertex)/time;
				normalsArrayPre[o] += (normalsArrayPost[o] - currentNormal)/time;
				//texturesArrayPre[o] += (texturesArrayPost[o] - currentTexture)/time;
				RawModel rawModel = loader.loadToVAO(vertexArrayPre, texturesArrayPre, normalsArrayPre, indicesArrayPre);
				TexturedModel model = new TexturedModel(rawModel, modelPre.getTexture());
				entity = new Entity(model, entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
			}
			vertexArrayPre[o] = currentVertex;
			normalsArrayPre[o] = currentNormal;
			//texturesArrayPre[o] = currentTexture;
		}
	}	*/
	
	public Entity animation(int animationStepNumber, Loader loader, Entity entity)
	{
		RawModel model = OBJLoader.loadObjModel(animations.get(animationStepNumber), loader);
		TexturedModel finalModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		entity = new Entity(finalModel, new Vector3f(0, 0, -20), entity.getRotX(), entity.getRotY(), entity.getRotZ(), 1);
		return entity;
	}
	
	public Entity animation2(int animationStepNumber, Loader loader, Entity entity, List<String> animations)
	{
		RawModel model = OBJLoader.loadObjModel(animations.get(animationStepNumber), loader);
		TexturedModel finalModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		entity = new Entity(finalModel, new Vector3f(0, 0, -20), entity.getRotX(), entity.getRotY(), entity.getRotZ(), 1);
		return entity; 
	}
}
