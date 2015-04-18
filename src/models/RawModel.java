package models;

public class RawModel //represents a 3d model stored in memory
{
	private int vaoID;
	private int vertexCount;
	
	private float[] vertexArray;
	private float[] normalsArray;
	private float[] texturesArray;
	private int[] indices;
	
	public RawModel(int vaoID, int vertexCount, float[] vertexArray, float[] normalsArray, float[] texturesArray, int[] indices)	//OBS May want to remove the vertexArray part!
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.vertexArray = vertexArray;
		this.normalsArray = normalsArray;
		this.texturesArray = texturesArray;
		this.indices = indices;
	}
	
	public int getVaoID()
	{
		return vaoID;
	}
	
	public int getVertexCount()
	{
		return vertexCount;
	}
	
	public float[] getVertexArray()
	{
		return vertexArray;
	}

	public float[] getNormalsArray() {
		return normalsArray;
	}

	public float[] getTexturesArray() {
		return texturesArray;
	}
	
	public int[] getIndicesArray()
	{
		return indices;
	}
}
