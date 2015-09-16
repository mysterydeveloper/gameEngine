package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;



public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader= new Loader();	
		
		TerrainTexture backgroundTexture= new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture= new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture= new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture= new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap= new TerrainTexture(loader.loadTexture("blendMap"));
		
		RawModel model2 = OBJLoader.loadObjModel("untitled",loader);
		TexturedModel staticModel2= new TexturedModel(model2, new ModelTexture(loader.loadTexture("tree")));
		staticModel2.getTexture().setHasTransparency(true);
		//staticModel2.getTexture().setUseFakeLighting(true);
		//RawModel model3 = OBJLoader.loadObjModel("bunny",loader);
		//TexturedModel staticModel3= new TexturedModel(model3, new ModelTexture(loader.loadTexture("white")));
		
		
		Light light= new Light(new Vector3f( 0, 30,0), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack ,blendMap);
		
		Camera  camera = new Camera();
		
		ModelTexture texture= staticModel2.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		MasterRenderer renderer = new MasterRenderer();
		List<Entity> allTrees= new ArrayList<Entity>();

		Random random = new Random();
		Player player= new Player(staticModel2, new Vector3f(0,0,-40), 0, 0, 0, 10);
	
		for (int i = 0; i < 500; i++) {
			float x= random.nextInt(750);
			float y=1;
			float z= random.nextInt(750)*-1;
			allTrees.add(new Entity(staticModel2, new Vector3f(x,y,z), 0, 0,0f,5f));
		}
		for (int i = 0; i < 500; i++) {
			float x= random.nextInt(750)*-1;
			float y=0;
			float z= random.nextInt(750)*-1;
			allTrees.add(new Entity(staticModel2, new Vector3f(x,y,z), 0, 0,0f,5f));
			
		}

		
		while(!Display.isCloseRequested()){
			//entity.increasePosition(0,0,0);
			//entity.increaseRotation(0, 1, 0);
			camera.move(); 
			player.move();
			renderer.prosessEntity(player);
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
