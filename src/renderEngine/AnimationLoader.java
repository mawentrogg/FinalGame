package renderEngine;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;

public class AnimationLoader 
{
	List<TexturedModel> models = new ArrayList<>();
	
	public AnimationLoader(TexturedModel... models)
	{
		for(TexturedModel model : models)
		{
			this.models.add(model);
		}
	}
	
	public List<TexturedModel> getModels()
	{
		return this.models;
	}
	
	public void animation(Entity entity, Loader loader)
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
	}
}
