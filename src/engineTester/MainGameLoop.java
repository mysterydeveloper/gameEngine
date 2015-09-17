package engineTester;

import java.util.ArrayList;
import java.util.Iterator;
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
		
		RawModel model2 = OBJLoader.loadObjModel("lowPolyTree",loader);
		TexturedModel staticModel2= new TexturedModel(model2, new ModelTexture(loader.loadTexture("lowPolyTree")));
		RawModel model4 = OBJLoader.loadObjModel("tree",loader);
		TexturedModel staticModel4= new TexturedModel(model4, new ModelTexture(loader.loadTexture("tree")));
		
		//staticModel2.getTexture().setHasTransparency(true);
		//staticModel2.getTexture().setUseFakeLighting(true);
		RawModel model3 = OBJLoader.loadObjModel("person",loader);
		TexturedModel staticModel3= new TexturedModel(model3, new ModelTexture(loader.loadTexture("playerTexture")));
		
		
		Light light= new Light(new Vector3f( 0, 30,0), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1, loader, texturePack, blendMap,"heightmap");
		
		
		
		ModelTexture texture= staticModel2.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		MasterRenderer renderer = new MasterRenderer();
		List<Entity> allTrees= new ArrayList<Entity>();

		Random random = new Random();
		Player player= new Player(staticModel3, new Vector3f(0,0,-40), 0, 0, 0, 1);
		Camera  camera = new Camera(player);
		
		
		for (int i = 0; i < 100; i++) {
			if(i%20==0){
				float x= random.nextInt(750);
				float z= random.nextInt(750)*-1;
				float y=terrain.getHeightOfTerrain(x, z);;
				allTrees.add(new Entity(staticModel2, new Vector3f(x,y,z), 0, 0,0f,0.5f));
			}else {
				float x= random.nextInt(750);
				float z= random.nextInt(750)*-1;
				float y=terrain.getHeightOfTerrain(x, z);;
				allTrees.add(new Entity(staticModel4, new Vector3f(x,y,z), 0, 0,0f,5f));
			}
		}


		
		while(!Display.isCloseRequested()){
			//entity.increasePosition(0,0,0);
			//entity.increaseRotation(0, 1, 0);
			camera.move(); 
			player.move(terrain);
			renderer.prosessEntity(player);
			for (Entity entity2 : allTrees) {
				renderer.prosessEntity(entity2);
			}
			renderer.processTerrain(terrain);

			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
