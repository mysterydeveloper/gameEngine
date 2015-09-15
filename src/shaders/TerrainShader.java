package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import toolBox.Maths;
import entities.Camera;
import entities.Light;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE="src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE="src/shaders/terrainFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPostion;
	private int location_lightColour;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_useFakeLighting;
	private int location_skyColor;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);

	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");

	}

	@Override
	protected void getAllUiformLoacation() {
		location_transformationMatrix =super.getUinformLocation("transformationMatrix");
		location_projectionMatrix =super.getUinformLocation("projectionMatrix");
		location_viewMatrix =super.getUinformLocation("viewMatrix");
		location_lightPostion=super.getUinformLocation("lightPosition");
		location_lightColour=super.getUinformLocation("lightColour");
		location_shineDamper=super.getUinformLocation("shineDamper");
		location_reflectivity=super.getUinformLocation("reflectivity");
		location_useFakeLighting=super.getUinformLocation("useFakeLighting");
		location_skyColor=super.getUinformLocation("skyColour");
		
	
	}
	public void loadSkyColor(float r, float g, float b){
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}
	
	
	public void loadShineVariables(float damper, float refectivity ){
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, refectivity);
	}
	

	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLight(Light light){
		super.loadVector(location_lightPostion, light.getPosition());
		super.loadVector(location_lightColour, light.getColour());
		
	}
	
	public void loadVewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
		
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	
	
	
}
