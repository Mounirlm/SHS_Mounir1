package com.shs.commons.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class FloorClientHandler {

	private Socket server;
	private JsonReader reader;
	private JsonWriter writer;
	private Gson gson;
	//WITH VM
	private int port = ServerAccess.getPORT_SERVER();
	private String adress =ServerAccess.getSERVER();
	
	//request json from client towards server  to retrieve list of Floor
	public FloorClientHandler() throws UnknownHostException, IOException {
		gson = new Gson();
	}
	
	public void getFlux() throws IOException { 
		try {
			this.server = new Socket(adress,port);		
			reader = new JsonReader(new InputStreamReader(server.getInputStream(), "UTF-8"));
			writer = new JsonWriter(new OutputStreamWriter(server.getOutputStream(), "UTF-8"));
		}catch(IOException e) {
			throw new IOException("Error connection to server ");
		}
	}
	
	public void stopFlux() throws IOException {
        try{
        	reader.close();
	        writer.close();
	        server.close();}
        catch(IOException e) {
        	throw new IOException("Error closed flux "+e);
        }
    }
	public List<Floor> getFloorInBuilding() throws IOException {
		
		List<Floor > list= new ArrayList<>();
		getFlux();
		
		try {
			
			String request = "select-Floor";
			//Creation request Json
			
			writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request);
		    
		    //response
		    reader.beginObject();
		    while (reader.hasNext()) {
			    String name= reader.nextName();
			    if(name.equals("null")) {
			    	System.out.println(reader.nextString());
			    
			    }else {
			    String objectJson=reader.nextString();
			    list.add(new Gson().fromJson(objectJson, Floor.class));
		    	
		    	System.out.println(list); 	
			    }
		   
		    }
		    reader.endObject();
		    return  list;
	    } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}
	
}
