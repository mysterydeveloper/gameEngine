package engineTester;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
		
		TerrainTexture backgroundTexture= new TerrainTexture(loader.loadTexture("blue"));
		TerrainTexture rTexture= new TerrainTexture(loader.loadTexture("road"));
		TerrainTexture gTexture= new TerrainTexture(loader.loadTexture("road"));
		TerrainTexture bTexture= new TerrainTexture(loader.loadTexture("road"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap= new TerrainTexture(loader.loadTexture("blendMap2"));
		

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);
		TexturedModel staticModel5= new TexturedModel(OBJLoader.loadObjModel("fern", loader),fernTextureAtlas);
		Entity fern= new Entity(staticModel5, new Vector3f(0,0,-40), 0, 0, 0, 1);
		//staticModel2.getTexture().setHasTransparency(true);
		//staticModel2.getTexture().setUseFakeLighting(true);
		RawModel model3 = OBJLoader.loadObjModel("knight",loader);
		TexturedModel staticModel3= new TexturedModel(model3, new ModelTexture(loader.loadTexture("white")));
		
		Light light= new Light(new Vector3f( 0, 30,0), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(-0.5f,-0.5f, loader, texturePack, blendMap,"heightmap2");
	
		//ModelTexture texture= staticModel2.getTexture();
		//texture.setShineDamper(10);
		//texture.setReflectivity(1);
		
		MasterRenderer renderer = new MasterRenderer(loader);

		Player player= new Player(staticModel3, new Vector3f(0,0,-40), 0, 0, 0, 0.01f);
		Camera  camera = new Camera(player);
		GuiTexture guibag= new GuiTexture(loader.loadTexture("loot bag"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guiskills= new GuiTexture(loader.loadTexture("skills"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guisarmour= new GuiTexture(loader.loadTexture("armour"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guisBar= new GuiTexture(loader.loadTexture("bar"), new Vector2f(0.9f,-0.95f), new Vector2f(0.6f,0.1f));
		GuiRenderer guiRenderer= new GuiRenderer(loader);
		boolean skills=false;
		boolean bag=false;
		boolean armour=false;

		while(!Display.isCloseRequested()){
			//entity.increasePosition(0,0,0);
			//entity.increaseRotation(0, 1, 0);
			camera.move(); 
			player.move(terrain);
			renderer.prosessEntity(player);
			renderer.processTerrain(terrain);

			renderer.render(light, camera);
			 if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
				 skills=true;
				 bag=false;
				 armour=false;
			 }else if(Keyboard.isKeyDown(Keyboard.KEY_X)){
				 skills=false;
				 bag=true;
				 armour=false;
			 }else if(Keyboard.isKeyDown(Keyboard.KEY_C)){
				 skills=false;
				 bag=false;
				 armour=true;
			 }else if(Keyboard.isKeyDown(Keyboard.KEY_V)){
				 skills=false;
				 bag=false;
				 armour=false;
			 }
			 
			 
			if(skills==true){
				guiRenderer.render(guiskills);
			}
			if(bag==true){
				guiRenderer.render(guibag);
			}
			if(armour==true){
				guiRenderer.render(guisarmour);
			}
			guiRenderer.render(guisBar);
			renderer.prosessEntity(fern);
			DisplayManager.updateDisplay();
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	    

}
