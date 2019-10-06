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
import com.shs.commons.model. Historical;

public class HistoricalClientHandler {
	private Socket server;
	private JsonReader reader;
	private JsonWriter writer;
	private Gson gson;
	//WITH VM
	private int port = ServerAccess.getPORT_SERVER();
	private String adress =ServerAccess.getSERVER();
	
	public HistoricalClientHandler() throws UnknownHostException, IOException {
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
	
	public String insertHistoricalToServer( Historical  historical)throws IOException  {
		//connections
     	getFlux();
		try {
			String request = "insert-Historical";
			
			//Creation request Json for server
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(historical));
		    writer.endObject();
		    writer.flush();//send to server
		    System.out.println("client :request:"+request+"\n object"+gson.toJson(historical));
		   
		    //response from server
		    reader.beginObject();
		    String response = "Server "+reader.nextName()+": "+reader.nextString();
		    System.out.println(response);
		    reader.endObject();
		    return response;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	//stop connections
	    	stopFlux();
	    }
	} 
	
	
	public String updateHistoricalToServer( Historical  historical) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "update-Historical";
			
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson( historical));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson( historical));
		    //response
		    reader.beginObject();reader.nextName();
		    String response = reader.nextString();
		    reader.endObject();System.out.println("Server response: "+response);
		    return response;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}
	
	
	public String deleteHistorical( Historical  historical) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "delete-Historical";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson( historical));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson( historical));
		    //response
		    reader.beginObject();reader.nextName();
		    String response = reader.nextString();
		    reader.endObject();System.out.println("Server response: "+response);
		    return response;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}

	public String deleteAllHistorical() throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "deleteAll-Historical";
			
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request);
		    //response
		    reader.beginObject();reader.nextName();
		    String response = reader.nextString();
		    reader.endObject();System.out.println("Server response: "+response);
		    return response;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}
	
	public List< Historical> searchHistoricalToServer( Historical  historical) throws IOException {
		//connections
     	getFlux();
		List< Historical> list=new ArrayList<>(); 
		try {
			//Type class
			String request="select-Historical";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson( historical));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n"+gson.toJson( historical));
		    //response
		    String name=null;
		    
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	 name = reader.nextName();
			    if(name.equals("historical")) {
			    	String objectJson=reader.nextString();
			    	 Historical receivRoom=new Gson().fromJson(objectJson,  Historical.class);
			    	list.add(receivRoom);System.out.println(list); 	
			    }
			    else if(name.equals("null")) {
			    	System.out.println(reader.nextString());
			    }
		    }
		    reader.endObject();
		 
		    return list;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
	}
		

	public List< Historical> searchAllHistorical() throws IOException {
		List< Historical> list = new ArrayList<>();
		//connections
     	getFlux();
		try {
			String request="selectAll-Historical";
			
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
				   	list.add(new Gson().fromJson(objectJson,  Historical.class));
				 }
		    }
		    reader.endObject();
		    return list;
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}
	

	
}
