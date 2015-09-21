package engineTester;

import guis.GuiRenderer;
import guis.GuiTexture;

import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import renderEngine.DisplayManager;
import renderEngine.Loader;

public class MainGameGuiLOOP implements Runnable {

	@Override
	public void run() {
		Loader loader= new Loader();
		GuiTexture guibag= new GuiTexture(loader.loadTexture("loot bag"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guiskills= new GuiTexture(loader.loadTexture("skills"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guisarmour= new GuiTexture(loader.loadTexture("armour"), new Vector2f(0.65f,-0.3f), new Vector2f(0.35f,0.54f));
		GuiTexture guisBar= new GuiTexture(loader.loadTexture("bar"), new Vector2f(0.9f,-0.95f), new Vector2f(0.6f,0.1f));
		GuiRenderer guiRenderer= new GuiRenderer(loader);
		boolean skills=false;
		boolean bag=false;
		boolean armour=false;
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

		DisplayManager.updateDisplay();
		
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
