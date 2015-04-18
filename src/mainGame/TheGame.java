package mainGame;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import entities.Player;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import terrains.TerrainTexture;
import terrains.TerrainTexturePack;
import textures.ModelTexture;

public class TheGame 
{
	public static void main(String[] args) 
	{
		DisplayManager.createDisplay();
		DisplayManager.printDimentions();
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer();
		
		// ================================= RAW MODELS ==========================================
		
		RawModel playerRawModel = OBJLoader.loadObjModel("full Model1", loader);
		
		// =======================================================================================
		
		// =============================== TEXTURED MODELS =======================================
		
		TexturedModel playerModel = new TexturedModel(playerRawModel, new ModelTexture(loader.loadTexture("Pink")));
		
		// =======================================================================================
		
		// =================================== ENTITIES ==========================================
		
		Player player = new Player(playerModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		// =======================================================================================
		
		// =================================== LIGHTS ============================================
			
		Light sun = new Light(new Vector3f(0, 200000, -10), new Vector3f(1, 1, 1));
		
		// =======================================================================================
		
		// =================================== CAMERA ============================================
		
		Camera camera = new Camera(player);
		
		// =======================================================================================
		
		// ================================== TERRAIN ============================================
				// ===================== TERRAIN TEXTURE PACK! ====================
		
				TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
				TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
				TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
				TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
				
				TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
				TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));
				
				
				// =================================================================
				Terrain terrain = new Terrain(-1, -1, loader, texturePack, blendMap);
				Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap);
				Terrain terrain3 = new Terrain(-1, 0, loader, texturePack, blendMap);
				Terrain terrain4 = new Terrain(0, 0, loader, texturePack, blendMap);
		// =======================================================================================
				
		// =================================== CHARACTERS ========================================
		// =======================================================================================
				
		// ====================================== ITEMS ==========================================
		// =======================================================================================
		
		while(!Display.isCloseRequested())
		{
			player.move();
			camera.move();
			renderer.render(sun, camera);
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain3);
			renderer.processTerrain(terrain4);
			DisplayManager.updateDisplay();
		}
		loader.cleanUp();
		renderer.cleanUp();
		DisplayManager.closeDisplay();
	}
}
