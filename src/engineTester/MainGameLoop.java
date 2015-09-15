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
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;



public class MainGameLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader= new Loader();	
		
		//opengl expects vertices to be defined counter clockwise by default
		/*float[] vertices = { 
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
		};
		
		int[] indices={
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22
		};
		
		float[] textureCoords ={
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0
		};*/
		
		RawModel model = OBJLoader.loadObjModel("fern",loader);
		TexturedModel staticModel= new TexturedModel(model, new ModelTexture(loader.loadTexture("flower")));
		
		RawModel model2 = OBJLoader.loadObjModel("tree",loader);
		TexturedModel staticModel2= new TexturedModel(model2, new ModelTexture(loader.loadTexture("tree")));
		
		RawModel model3 = OBJLoader.loadObjModel("Snow covered CottageOBJ",loader);
		TexturedModel staticModel3= new TexturedModel(model3, new ModelTexture(loader.loadTexture("Cottage Texture")));
		
		Entity entity= new Entity(staticModel, new Vector3f(0,0,-25),0,0,0,1);
		Entity entityTree= new Entity(staticModel2, new Vector3f(0,0,-25),0,0,0,1);
		Entity entityStalls= new Entity(staticModel3, new Vector3f(0,0,-25),0,0,0,1);
		
		Light light= new Light(new Vector3f( 0, 0,-20), new Vector3f(1,1,1));
		Terrain terrain = new Terrain(-1,0, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(-1, 1, loader, new ModelTexture(loader.loadTexture("grass")));
		Camera  camera = new Camera();
		ModelTexture texture= staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		MasterRenderer renderer = new MasterRenderer();
		List<Entity> allFerns = new ArrayList<Entity>();
		List<Entity> allTrees= new ArrayList<Entity>();
		List<Entity> allGrass = new ArrayList<Entity>();
		Random random = new Random();
		
		for (int i = 0; i < 500; i++) {
			float x= random.nextInt(750)*-1;
			float y=1;
			float z= random.nextInt(1500);
			allFerns.add(new Entity(staticModel, new Vector3f(x,y,z), 0, 0,0f,1f));
			
		}
		for (int i = 0; i < 500; i++) {
			float x= random.nextInt(750)*-1;
			float y=1;
			float z= random.nextInt(1500);
			allTrees.add(new Entity(staticModel2, new Vector3f(x,y,z), 0, 0,0f,10f));
			
		}
		for (int i = 0; i < 5; i++) {
			float x= (float) (random.nextInt(750)*-1);
			float y=1;
			float z= random.nextInt(1500);
			allGrass.add(new Entity(staticModel3, new Vector3f(x,y,z), 0, 0,0f,4f));
			
		}
		
		while(!Display.isCloseRequested()){
			//entity.increasePosition(0,0,0);
			//entity.increaseRotation(0, 1, 0);
			camera.move(); 
			for (Entity entity2 : allFerns) {
				renderer.prosessEntity(entity2);
			}
			for (Entity entity3 : allTrees) {
				renderer.prosessEntity(entity3);
			}
			for (Entity entity4 : allGrass) {
				renderer.prosessEntity(entity4);
			}
			
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
