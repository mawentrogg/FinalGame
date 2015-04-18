package engineTester;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Vertex_reader_test 
{
	
	public Vertex_reader_test()
	{
		
	}
	
	private float[] vertexes;
	
	public void readVertexes()
	{
		try {
			Scanner inFile = new Scanner(new FileReader("/res/FinalBaseMesh.obj"));
			for(int i = 0; i < 11; i++)   //24471
			{
				float line = inFile.nextFloat();
				System.out.println(line);
				/*if(i > 9)
				{
					float vertex1 = line;
				}*/
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("no");
			e.printStackTrace();
		}
	}
	
	public float[] getVertexes()
	{
		return vertexes;
	}
	
	
	public static void main(String[] args) 
	{
		Vertex_reader_test reader = new Vertex_reader_test();
		reader.readVertexes();
	}
}
