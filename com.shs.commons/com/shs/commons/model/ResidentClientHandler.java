package com.shs.commons.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class ResidentClientHandler {
	
	private Socket server;
	private JsonReader reader;
	private JsonWriter writer;
	private Gson gson;
	//WITH VM
	private int port = ServerAccess.getPORT_SERVER();
	private String adress =ServerAccess.getSERVER();
	
	public ResidentClientHandler() {
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
	public int countResidentByFloor(int floor2) throws IOException {
		getFlux();
		int nResidents = 0;
		try {
			
			String request = "countByFloor-Resident-"+floor2;
			//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request);
		    //response
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	String name = reader.nextName();
		    	if(name.equals("nResidents"))
		    		nResidents=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		    reader.endObject();
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nResidents;
	}

	public int countResidentByWing(int wingInt) throws IOException {
		getFlux();
		int nResidents = 0;
		try {
			
			String request = "countByWing-Resident-"+wingInt;
			//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request);
		    //response
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	String name = reader.nextName();
		    	if(name.equals("nResidents"))
		    		nResidents=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		    reader.endObject();
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nResidents;
	}

	public int countResident() throws IOException {
		getFlux();
		int nResidents = 0;
		try {
			
			String request = "countAll-Resident";
			//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request);
		    //response
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	String name = reader.nextName();
		    	if(name.equals("nResidents"))
		    		nResidents=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		    reader.endObject();
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nResidents;
	}

}
