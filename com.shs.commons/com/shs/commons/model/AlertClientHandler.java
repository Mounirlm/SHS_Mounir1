package com.shs.commons.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Alert;

public class AlertClientHandler {
	private Socket server;
	private JsonReader reader;
	private JsonWriter writer;
	private Gson gson;
	//WITH VM
	private int port = ServerAccess.getPORT_SERVER();
	private String adress =ServerAccess.getSERVER();
	
	public AlertClientHandler() throws UnknownHostException, IOException {
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
	
	public String insertAlertToServer(Alert alert)throws IOException  {
		//connections
     	getFlux();
		try {
			String request = "insert-Alert";
			
			//Creation request Json for server
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(alert));
		    writer.endObject();
		    writer.flush();//send to server
		    System.out.println("client :request:"+request+"\n object"+gson.toJson(alert));
		   
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
	
	
	public String updateAlertToServer(Alert alert) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "update-Alert";
			
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(alert));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson(alert));
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
	
	
	public String deleteAlert(Alert alert) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "delete-Alert";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(alert));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson(alert));
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

	public String deleteAllAlert() throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "deleteAll-Alert";
			
			
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
	
	public List<Alert> searchAlertToServer(Alert alert) throws IOException {
		//connections
     	getFlux();
		List<Alert> list=new ArrayList<>(); 
		try {
			//Type class
			String request="select-Alert";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(alert));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n"+gson.toJson(alert));
		    //response
		    String name=null;
		    
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	 name = reader.nextName();
			    if(name.equals("alert")) {
			    	String objectJson=reader.nextString();
			    	Alert receivRoom=new Gson().fromJson(objectJson, Alert.class);
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
		

	public List<Alert> searchAllAlert() throws IOException {
		List<Alert> list = new ArrayList<>();
		//connections
     	getFlux();
		try {
			String request="selectAll-Alert";
			
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
				   	list.add(new Gson().fromJson(objectJson, Alert.class));
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

	public int countAlertByFloor(int floor2, int month2, int year2) throws IOException {
		getFlux();
		int nAlerts = 0;
		try {
			
			String request = "countByFloor-Alert-"+floor2+"-"+month2+"-"+year2;
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
		    	if(name.equals("nAlerts"))
		    		nAlerts=reader.nextInt();
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
		
		return nAlerts;
	}

	public int countAlertByWing(int wingInt, int month2, int year2) throws IOException {
		getFlux();
		int nAlerts = 0;
		try {
			
			String request = "countByWing-Alert-"+wingInt+"-"+month2+"-"+year2;
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
		    	if(name.equals("nAlerts"))
		    		nAlerts=reader.nextInt();
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
		
		return nAlerts;
	}

	public int countAlert(int month2, int year2) throws IOException {
		getFlux();
		int nAlerts = 0;
		try {
			
			String request = "countAll-Alert-"+month2+"-"+year2;
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
		    	if(name.equals("nAlerts"))
		    		nAlerts=reader.nextInt();
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
		
		return nAlerts;
	}
	

	
}
