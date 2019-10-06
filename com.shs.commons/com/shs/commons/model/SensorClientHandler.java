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
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Wing_Room;

public class SensorClientHandler {
	private Socket server;
	private JsonReader reader;
	private JsonWriter writer;
	private Gson gson;
	//WITH VM
	private int port = ServerAccess.getPORT_SERVER();
	private String adress =ServerAccess.getSERVER();
	
	public SensorClientHandler() throws UnknownHostException, IOException {
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
	
	public String insertSensorToServer(Sensor sensor)throws IOException  {
		//connections
     	getFlux();
		try {
			String request = "insert-Sensor";
			
			//Creation request Json for server
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(sensor));
		    writer.endObject();
		    writer.flush();//send to server
		    System.out.println("client :request:"+request+"\n object"+gson.toJson(sensor));
		   
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
	
	
	public String updateSensorToServer(Sensor sensor) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "update-Sensor";
			
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(sensor));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson(sensor));
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
	
	public String deleteSensor(Sensor sensor) throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "delete-Sensor";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(sensor));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n object: "+gson.toJson(sensor));
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

	public String deleteAllSensors() throws IOException {
		//connections
     	getFlux();
		try {
			//Type class
			String request = "deleteAll-Sensor";
			
			
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
	
	public List<Sensor> searchSensorToServer(Sensor sensor) throws IOException {
		//connections
     	getFlux();
		List<Sensor> list=new ArrayList<>(); 
		try {
			//Type class
			String request="select-Sensor";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.name("object").value(gson.toJson(sensor));
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n"+gson.toJson(sensor));
		    //response
		    String name=null;
		    
		    reader.beginObject();
		    while(reader.hasNext()) {
		    	 name = reader.nextName();
			    if(name.equals("sensor")) {
			    	String objectJson=reader.nextString();
			    	Sensor receivRoom=new Gson().fromJson(objectJson, Sensor.class);
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
		

	public List<Sensor> searchAllSensors() throws IOException {
		List<Sensor> list = new ArrayList<>();
		//connections
     	getFlux();
		try {
			String request="selectAll-Sensor";
			
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
				   	list.add(new Gson().fromJson(objectJson, Sensor.class));
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
	

	/*
	 * @return all types of sensors
	 */
	public List<Type_Sensor> selectAllTypeSensors() throws IOException {
		List<Type_Sensor> list = new ArrayList<>();
		//connections
     	getFlux();
     	
     	//communication to server
		try {
			String request="selectAll-Type_Sensor";
			
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
				   	list.add(new Gson().fromJson(objectJson, Type_Sensor.class));
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
	
	
	
	
	
	/*
	 * @return all positions of sensors
	 */
	public List<Wing_Room> selectAllWingSensors() throws IOException {
		List<Wing_Room> list = new ArrayList<>();
		//connections
     	getFlux();
     	
     	//communication to server
		try {
			String request="selectAll-Wing_Room";
			
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
				   	list.add(new Gson().fromJson(objectJson, Wing_Room.class));
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

	public int countSensorsByFloor(int floor2) throws IOException {
		getFlux();
		int nSensors = 0;
		try {
			
			String request = "countByFloor-Sensor-"+floor2;
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
		    	if(name.equals("nSensors"))
		    		nSensors=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nSensors;
	}

	public int countSensorsByWing(int wingInt) throws IOException {
		getFlux();
		int nSensors = 0;
		try {
			
			String request = "countByWing-Sensor-"+wingInt;
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
		    	if(name.equals("nSensors"))
		    		nSensors=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nSensors;
	}

	public int countSensors() throws IOException {
		getFlux();
		int nSensors = 0;
		try {
			
			String request = "countAll-Sensor";
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
		    	if(name.equals("nSensors"))
		    		nSensors=reader.nextInt();
		    	else
		    		reader.skipValue();
		    }
		} catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
		return nSensors;
	}
	
	public List<Sensor> searchSensorWithPositionToServer() throws IOException {
		//connections
     	getFlux();
		List<Sensor> list=new ArrayList<>(); 
		try {
			//Type class
			String request="selectPosition-Sensor";
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n");
		    //response
		  //response
		    reader.beginObject();
		    while (reader.hasNext()) {
			    String name= reader.nextName();
			    if(name.equals("null")) {
			    	System.out.println(reader.nextString());
			    }else {
				   String objectJson=reader.nextString();
				   	list.add(new Gson().fromJson(objectJson, Sensor.class));
				 }
		    }
		    reader.endObject();
		    System.out.println(list);
		    return list;
		    
		    
	      } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
		
	}

	public Type_Sensor selectSensorType(String Name) throws IOException {
		//connections
     	getFlux();
		Type_Sensor ts =new Type_Sensor();
		try {
			//Type class
			String request="select-Type_Sensor"+Name;
			//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n");
		    //response
		  //response
		    reader.beginObject();
		    while (reader.hasNext()) {
			    String name= reader.nextName();
			    if(name.equals("null")) {
			    	System.out.println(reader.nextString());
			    }else {
				   String objectJson=reader.nextString();
				   	ts=new Gson().fromJson(objectJson, Type_Sensor.class);
				 }
		    }
		    reader.endObject();
		    System.out.println(ts);
		    return ts;
		 } 
	    catch (IOException ioe) { 
	    	throw new IOException("Error communication to server ");
		}
	    finally {
	    	stopFlux();
	    }
	}
	public ArrayList<Sensor> selectSensorInRoom(int id) throws IOException {
		//connections
     	getFlux();
     	ArrayList<Sensor> list=new ArrayList<Sensor>(); 
		try {
			//Type class
			String request="selectRoom-Sensor-"+id;
			
	     	//Creation request Json
		    writer.setIndent("	");
		    writer.beginObject();
		    writer.name("request").value(request);
		    writer.endObject();
		    writer.flush();
		    System.out.println("request:"+request+"\n");
		    //response
		  //response
		    reader.beginObject();
		    while (reader.hasNext()) {
			    String name= reader.nextName();
			    
			    if(name.equals("null")) {
			    	System.out.println(reader.nextString());
			    }else {
				   String objectJson=reader.nextString();
				   	list.add(new Gson().fromJson(objectJson, Sensor.class));
				 }
		    }
		    reader.endObject();
		    System.out.println(list);
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
