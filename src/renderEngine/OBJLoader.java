package renderEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class OBJLoader {

	public static RawModel loadObjModel(String fileName,Loader Loader){
		FileReader fr= null;
		try {
			//orderObjFile(fileName);
			fr = new FileReader( new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("couldnt find file");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		String line = null;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticiesArray=null;
		float[] normalsArray=null;
		float[] textureArray=null;
		int[] indicesArray=null;
		try {
		while(true){
			
				line=reader.readLine();
			
			String[] currentLine= line.split(" ");
			if(line.startsWith("v ")){
				Vector3f vertex= new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]) );
				vertices.add(vertex);
			}else if(line.startsWith("vt ")){
				Vector2f texture= new Vector2f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]) );
				textures.add(texture);
			}else if(line.startsWith("vn ")){
				Vector3f normal= new Vector3f(Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]) );
				normals.add(normal);
			}else if(line.startsWith("f ")){
				textureArray = new float[vertices.size()*2];
				normalsArray = new float[vertices.size()*3];
				break;
			}
		}
		while(line!=null){
			if(!line.startsWith("f ")){
				line = reader.readLine();
				continue;
			}
			String[] currentLine= line.split(" ");
			String[] vertex1= currentLine[1].split("/");
			String[] vertex2= currentLine[2].split("/");
			String[] vertex3= currentLine[3].split("/");
			processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
			processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
			processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
			line = reader.readLine();
		}
		reader.close();
	} catch (IOException e) {
				e.printStackTrace();
			
	}
		verticiesArray = new float [vertices.size()*3];	
		indicesArray= new int[indices.size()];
		
		int vertexPointer=0;
		
		for(Vector3f vertex:vertices){
			verticiesArray[vertexPointer++] = vertex.x;
			verticiesArray[vertexPointer++] = vertex.y;
			verticiesArray[vertexPointer++] = vertex.z;
		}
		for(int i=0; i<indices.size();i++){
			indicesArray[i]= indices.get(i);
		}
		return Loader.loadtoVAO(verticiesArray, textureArray,normalsArray, indicesArray);
		
		
	}
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,List<Vector3f> normals,float[]textureArray , float[] normalsArray ){
			int currentVertexPosition= Integer.parseInt(vertexData[0])-1;
			indices.add(currentVertexPosition);
			Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
			textureArray[currentVertexPosition*2]= currentTex.x;
			textureArray[currentVertexPosition*2]= 1- currentTex.y;
			Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
			normalsArray[currentVertexPosition*3]= currentNorm.x;
			normalsArray[currentVertexPosition*3+1]= currentNorm.y;
			normalsArray[currentVertexPosition*3+2]= currentNorm.z;
			
			
		}
	private static void orderObjFile(String fileName){
		FileReader fr= null;
		try {
			fr = new FileReader( new File("res/"+fileName+".obj"));
			
		} catch (FileNotFoundException e) {
			System.err.println("couldnt find file");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line = " ";
		List<String> vertices = new ArrayList<String>();
		List<String> textures = new ArrayList<String>();
		List<String> normals = new ArrayList<String>();
		List<String> faces = new ArrayList<String>();
		List<String> nos = new ArrayList<String>();
		
		try {
			line=reader.readLine();
		while(!line.startsWith("x")){
			
			
			
			if(line.startsWith("v ")){
				vertices.add(line.toString());
			}else if(line.startsWith("vt ")){
				textures.add(line.toString());
			}else if(line.startsWith("vn ")){
				normals.add(line.toString());
			}else if(line.startsWith("f ")){	
				faces.add(line.toString());
			}
			line=reader.readLine();
		}
		fr.close();
		
		 BufferedWriter bwr = new BufferedWriter(new FileWriter(new File("res/"+fileName+".obj")));
		 StringBuilder builder= new StringBuilder();
         for (String vertice : vertices) {
        	 bwr.append(vertice+"\n");
 		 }
         for (String texture : textures) {
        	 bwr.append(texture+"\n");
 		 }
         for (String normal : normals) {
        	 bwr.append(normal+"\n");
 		 }
     	for (String face : faces) {
     		bwr.append(face+"\n");
		 }
     	bwr.append("x");
     	bwr.close();
		reader.close();
	}catch (IOException e) {
		e.printStackTrace();
		
	}
	}
	
}