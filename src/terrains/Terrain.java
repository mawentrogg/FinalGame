package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import renderEngine.Loader;
import textures.ModelTexture;
import models.RawModel;

public class Terrain 
{
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	//private static final float MAX_HEIGHT = 40;
	
	
	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;	//Not really a terrain texture but I'm to lazy to change it (sorry group)
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap)
	{
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader);
	}
	
	public float getX() {
	return x;
}



public void setX(float x) 
{
	this.x = x;
}



public float getZ() 
{
	return z;
}



public void setZ(float z) 
{
	this.z = z;
}



public RawModel getModel() 
{
	return model;
}



public void setModel(RawModel model) 
{
	this.model = model;
}



public TerrainTexturePack getTexturePack() 
{
	return texturePack;
}



public TerrainTexture getBlendMap() 
{
	return blendMap;
}


	// ALL OF THIS IS TO GENERATE FLAT TERRAIN!
	
	/*MAKE SURE THAT OUR CAMERA IS IN A POSITION THAT ALLOWS US TO SEE THE TERRAIN! MAKE SURE THE CAMERA'S Y
	POSITION IS ABOVE 0, AND MAKE SURE THAT THE TERRAIN IS INFRONT OF THE CAMERA (EITHER MOVE THE
	CAMERA BACK, ROTATE THE CAMERA AROUND, OR CHOOSE NEGATIVE GRIDX & GRIDZ VALUES WHEN CALLING THE TERRAIN
	CONSTRUCTOR).*/


		//private RawModel generateTerrain(Loader loader, String heightMap){
		//altered method above is for heightmaping
		private RawModel generateTerrain(Loader loader)
		{
			/*BufferedImage image = null;
			try {
				image = ImageIO.read(new File("res/" + heightMap + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			//OBS CODE ABOVE IS FOR HEIGHTMAPING
			
			// Gets the area of the square (the unit is vertexes [how many vertexes in our square])
			int count = VERTEX_COUNT * VERTEX_COUNT;
			//Makes an array the size of 3 * the amount of vertexes we have since they are stored as floats and these are Vector3f's
			float[] vertices = new float[count * 3];
			//One normal for each vertex 
			float[] normals = new float[count * 3];
			// Textures are only 2d so they only need to have 2 floats per vertex
			float[] textureCoords = new float[count*2];
			// Creates the array of indices for our vertexes
			int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
			int vertexPointer = 0;
			for(int i=0;i<VERTEX_COUNT;i++){
				for(int j=0;j<VERTEX_COUNT;j++){
					//for each row and column in our arrays fills it with vertex coordinates, normals and floats
					vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
					vertices[vertexPointer*3+1] = 0;
					vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
					normals[vertexPointer*3] = 0;
					normals[vertexPointer*3+1] = 1;
					normals[vertexPointer*3+2] = 0;
					textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
					textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
					vertexPointer++;
				}
			}
			int pointer = 0;
			for(int gz=0;gz<VERTEX_COUNT-1;gz++){
				for(int gx=0;gx<VERTEX_COUNT-1;gx++){
					//Does something (sets the indices for the vertexes)
					int topLeft = (gz*VERTEX_COUNT)+gx;
					int topRight = topLeft + 1;
					int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
					int bottomRight = bottomLeft + 1;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = topRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = bottomRight;
				}
			}
			//puts our terrain into the Vertex Array Object (VAO)
			return loader.loadToVAO(vertices, textureCoords, normals, indices);
		}
	// ============================================ CUT AND PASTE CODE PART END! =================================================================
}
