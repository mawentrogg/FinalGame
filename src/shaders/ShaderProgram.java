package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram 
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	//============================= CONSTRUCTOR ===============================================
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		// ATTACHES THE VERTEX AND FRAGMENT SHADERS TO A PROGRAM (THIS IS HOW THEY ARE BOUND TOGETHER!)
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName)
	//Takes in the name of the uniform variable as a String and returns an integer representing the location of the uniform variable in our shadercode
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	public void start()
	{
		GL20.glUseProgram(programID	);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public void cleanUp()
	//MEMORY MANAGEMENT WHICH DELETES BOTH SHADERS
	{
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes();
	//Links up the inputs to the shaderprograms to one of the attributes(slots) of the Vertex Array Object (VAO)
	
	protected void bindAttribute(int attribute, String variableName)
	// Binds an attribute -- takes in the number of the attribute list (slot) in the VAO which we want to bind and the variableName in the shader
	// code that we want to bind that attribute to.
	{
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected void loadFloat(int location, float value)
	//Takes in the location of the uniform variable and the float value we want to load to that uniform
	//(OBS glUniformXA(int Z, parameters...) is "Load X parameters of type A (floats, ints etc) to uniform variable in location Z)
	{
		GL20.glUniform1f(location, value);
	}
	
	protected void loadInt(int location, int value)
	{
		GL20.glUniform1i(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector)
	//Takes in the location of the uniform variable and the 3d vector (vec3) we want to load to that uniform
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value)
	//Takes in the location of the uniform variable and the boolean value we want to load to that uniform then converts the boolean to
	// true = 1 and false = 0 since shader code does not have booleans as a type natively.
	{
		float toLoad = 0;
		if (value)
		{
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix)
	//Takes in the location of the uniform variable and the matrix (of 4 floats) we want to load to that uniform
	{
		matrix.store(matrixBuffer);
		//Flip turns prepares it to be read from after it has been prepared to be written to (write to when storing matrix then read from later)
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	
//Loads shader source code files -- takes in filename of source code file -- and an integer which indicates whether it's an vertex or a fragment shader
	private static int loadShader(String file, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine())!= null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}
		catch(IOException e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));		//500 == MAX LENGHT (WHY?)
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
	
	
}
