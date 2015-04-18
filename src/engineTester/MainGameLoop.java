package engineTester;

import java.util.ArrayList;
import java.util.List;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import animationTests.Animation;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import renderEngine.AnimationLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import terrains.TerrainTexture;
import terrains.TerrainTexturePack;
import textures.ModelTexture;

public class MainGameLoop 
{	
	//OBS PSA HERE: PLEASE MAKE ALL COMMENTS IN ENGLISH SO I DON'T HAVE TO DEAL WITH THAT STUPID UTC-8 SHIT AGAIN

	public static void main(String[] args) 
	{
		DisplayManager.createDisplay();
		DisplayManager.printDimentions();
		
		Loader loader = new Loader();
		//Renderer renderer = new Renderer();
		
		//StaticShader shader = new StaticShader();
		//Renderer renderer = new Renderer(shader);	//NEEDS TO BE AFTER THE SHADER IS CREATED AS IT NEEDS THE SHADER AS A PARAMETER FOR THE Projection matrix
		//OpenGL expects vertices to be defined counter clockwise by default (why?)
		
		// ========================== TERRAIN TEXTURE PACK! =========================================================
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
		
		
		// ====================================================================================================================
		
		RawModel model = OBJLoader.loadObjModel("Stall", loader);
		RawModel model2 = OBJLoader.loadObjModel("stallUp", loader);
		RawModel ourModel = OBJLoader.loadObjModel("Full model1", loader);
		RawModel dragon = OBJLoader.loadObjModel("dragon", loader);
		RawModel RawGrass = OBJLoader.loadObjModel("grassModel", loader);
		RawModel treeRaw = OBJLoader.loadObjModel("tree", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel staticModel2 = new TexturedModel(model2, new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel staticModel3 = new TexturedModel(dragon, new ModelTexture(loader.loadTexture("Pink")));
		TexturedModel staticModel4 = new TexturedModel(ourModel, new ModelTexture(loader.loadTexture("white")));
		TexturedModel grassModel = new TexturedModel(RawGrass, new ModelTexture (loader.loadTexture("grassTexture")));
		TexturedModel playerModel = new TexturedModel(ourModel, new ModelTexture (loader.loadTexture("lowPolyTree")));
		TexturedModel camo = new TexturedModel(treeRaw, new ModelTexture (loader.loadTexture("lowPolyTree")));
		//Animation animationTest = new Animation(staticModel, staticModel2);
		
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);	//Combine?
		
		List<TexturedModel> modelList = new ArrayList<>();
		modelList.add(staticModel);
		modelList.add(staticModel2);
		modelList.add(staticModel3);
		
		/*ModelTexture texture = staticModel4.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		ModelTexture texture2 = staticModel.getTexture();
		texture2.setShineDamper(10);
		texture2.setReflectivity(1);
		ModelTexture texture3 = staticModel3.getTexture();
		texture3.setShineDamper(10);
		texture3.setReflectivity(0);	*/
		
		
		Entity entity = new Entity(staticModel, new Vector3f(-10, 0, -25), 0, 0, 0, 1);
		Entity entity2 = new Entity(staticModel4, new Vector3f(0, 0, -20), 0, 0, 0, 2);
		Entity entity3 = new Entity(staticModel3, new Vector3f(10, 0, -25), 0, 0, 0, 1);
		Entity grassEntity = new Entity(grassModel, new Vector3f(20, 0, -10), 0, 0, 0, 5);
		
		Player player = new Player(playerModel, new Vector3f(0, 0, -10), 0, 0, 0, 2f);
		
		//Camera camera = new Camera(0, 15, 0);
		Camera camera = new Camera(player);
		
		Light lightOff = new Light(new Vector3f(0, 0, -20) , new Vector3f(0, 0, 0));
		Light lightOn = new Light(new Vector3f(0, 0, -20) , new Vector3f(1, 1, 1));
		Light light = lightOff;
		Light light2 = new Light(new Vector3f(0, 200000, -10), new Vector3f(1, 1, 1));
		
		// OBS -1 z coordinate gives where the camera starts while 0 and -1 x coordinates give the border where the camera starts
		Terrain terrain = new Terrain(-1, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain3 = new Terrain(-1, 0, loader, texturePack, blendMap);
		Terrain terrain4 = new Terrain(0, 0, loader, texturePack, blendMap);
		
		Light cameraLight = new Light(camera.getPosition(), new Vector3f(0.4f, 0.4f, 0.5f));
		 
		boolean hasBeenOn = false;
		int i = 0;
		
		long lastTimeOn = System.currentTimeMillis(); 
		long lastTimeOnK = System.currentTimeMillis();
		long lastTimeOnMenu = System.currentTimeMillis();
		
		MasterRenderer renderer = new MasterRenderer();
		
		Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);	//640 x 360
		//Mouse.setGrabbed(true);
		
		boolean menu = false;
		
		entity.increaseRotation(0, 0, 0);
		entity.setRotY(1);
		
		//================================================ WHILE LOOP (For clarity) ====================================================\\
		
		while(!Display.isCloseRequested())
		{
			player.move();
			camera.move();
			
			cameraLight = new Light(camera.getPosition(), cameraLight.getColour());
			if(Keyboard.isKeyDown(Keyboard.KEY_B) && System.currentTimeMillis() > lastTimeOnK + 300)
			{
				//TODO This fucks with camera WHY?!
				player = new Player(camo, player.getPosition(), player.getRotX(), player.getRotY(), player.getRotZ(), 5.0f);
				//animationTest.animation(entity, loader); 	//TODO OBSOLETE NEED TO DECIDE ON STYLE OF ANIMATION LOADING (SEE GAMETESTER?)
			}
			
			//light = lightOn;	//For clarity this keeps the light on for testing purpouses
			if(Keyboard.isKeyDown(Keyboard.KEY_K) && System.currentTimeMillis() > lastTimeOnK + 300)
			{
				if(System.currentTimeMillis() > lastTimeOnK + 1000)
				{
					i = 0;
				}
				lastTimeOnK = System.currentTimeMillis();
				//if(hasBeenOn == false)
				//{
					entity = new Entity(modelList.get(i), entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
					hasBeenOn = true;
					if(i == 2)
					{
						i = 0;
					}
					else
					{
						i++;
					}
				//}
				/*else
				{
					entity = new Entity(staticModel, entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
					hasBeenOn = false;
				}*/
			}
			else if (!Keyboard.isKeyDown(Keyboard.KEY_K))
			{
				entity = new Entity(modelList.get(i), entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				camera.setSpeed(100);
			}
			else
			{
				camera.setSpeed(0.2f);
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_G) && System.currentTimeMillis() > lastTimeOn + 300)		//With 3/100 sec delay to compensate for debounce and the time the button is pressed
			{
				lastTimeOn = System.currentTimeMillis();
				if(hasBeenOn == false)
				{
					//cameraLight = new Light(camera.getPosition(), new Vector3f(1, 1, 1));
					light2 = new Light(light2.getPosition(), lightOn.getColour());
					hasBeenOn = true;
				}
				else
				{
					//cameraLight = new Light(camera.getPosition(), new Vector3f(0, 0, 0));
					light2 = new Light(light2.getPosition(), lightOff.getColour());
					hasBeenOn = false;
				}
			}
			//System.out.println("Display hight: " + Display.getHeight() + "\n" + "Display width:" + Display.getWidth() + "\n");
			/*if(Display.getHeight() == 1018 && Display.getWidth() == 1920)
			{
				renderer.adjustProjectionMatrix();
			}*/
			entity2.increaseRotation(0, 0.2f, 0);
			//entity.increaseRotation(0, 0.5f, 0);
			//entity3.increaseRotation(0, -0.5f, 0);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && System.currentTimeMillis() > lastTimeOnMenu + 300)
			{
				lastTimeOnMenu = System.currentTimeMillis();
				if(!menu)
				{
					menu = true;
				}
				else
				{
					menu = false;
					Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);	//640 x 360
				}
			}
			
			if(!menu)
			{
				Mouse.setGrabbed(true);
				player.move();
				//camera.move();
				//camera.rotate();
			}
			if(menu)
			{
				Mouse.setGrabbed(false);
			}
			
			//renderer.prepare();
			//=====================================GAME LOGIC==========================================
			//======================================RENDER=============================================
			renderer.render(light2, camera);		//, cameraLight seems to be an issue WHY?
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);
			renderer.processTerrain(terrain4);
			renderer.processEntity(entity);
			renderer.processEntity(entity2);
			renderer.processEntity(entity3);
			renderer.processEntity(grassEntity);

			//DISPLAY UPDATER
			DisplayManager.updateDisplay();

		}
		
		//shader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
