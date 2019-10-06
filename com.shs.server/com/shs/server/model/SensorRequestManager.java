package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.User;

public class SensorRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Sensor sensor;
	SensorManager sensorManager;
	
	public SensorRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Sensor object, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.sensor = object;
		this.request = request;
		this.sensorManager = new SensorManager(connDB);
	}

	
	public String requestManager() throws SQLException, IOException, ParseException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "insert-Sensor":
				try{
					response=SensorManager.create(sensor);
				}
			    catch(SQLException e) {
			    	error="Error insertion "+e;
			    }
				break;
			case "update-Sensor":
				try{
					response=SensorManager.update(sensor);
				}
			    catch(SQLException e) {
			    	error="Error updating "+e;
			    }
				break;
			case "delete-Sensor":
				try{
					response=SensorManager.delete(sensor);
				}
			    catch(SQLException e) {
			    	error="Error delete "+e;
			    }
				break;
			case "deleteAll-Sensor":
				try{
					response=SensorManager.deleteAll();
				}
			    catch(SQLException e) {
			    	error="Error delete all "+e;
			    }				
				break;	
			case "select-Sensor":
					Sensor sendSensor=null;
					try{
						sendSensor=SensorManager.getSensor(sensor.getId());
						writer.beginObject();
						if (sendSensor!=null) {
							response=true;
							writer.name("sensor").value(new Gson().toJson(sendSensor));
						}
						else {
							writer.name("null").value("null");
						}
						writer.endObject();
					}
				    catch(SQLException e) {
				    	error="Error select "+e;
				    } catch (ParseException e) {
						error+=" and Date Parse error "+e;
					}
					break;
			case "selectAll-Sensor":
				try{
					List<Sensor> sensors;
					sensors= SensorManager.getSensors("");
					writer.beginObject();
					if(!sensors.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Sensor sensor : sensors) {
							writer.name("Sensor").value(gson.toJson(sensor));
						}
					}else {
						writer.name("null").value("null");	
					}
					writer.endObject();
				}catch(SQLException e) {
		        	error="Error select all "+e;
		        }catch (ParseException e) {
					error+=" and Date Parse error "+e;
				}	
					
				break;
			
			default:
				if (request.startsWith("selectPosition-Sensor")) {
				try{
					List<Sensor> sensors;
					sensors= SensorManager.getSensorsWithPosition();
					writer.beginObject();
					if(!sensors.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Sensor sensor : sensors) {
							writer.name("sensor").value(gson.toJson(sensor));
						}
					}else {
						writer.name("null").value("null");	
					}
					writer.endObject();
				}catch(SQLException e) {
		        	error="Error select all "+e;
		        }catch (ParseException e) {
					error+=" and Date Parse error "+e;
				}	
				}	
				
				else if(request.startsWith("selectRoom-Sensor")) {
					try{
						List<Sensor> sensors =new ArrayList<Sensor>();
						
						
							
						sensors=SensorManager.getSensorsInRoom(Integer.valueOf(res[2]));
						
						writer.beginObject();
						if(!sensors.isEmpty()) {
							response=true;
							Gson gson = new Gson();
							for (Sensor s : sensors) {
								writer.name("sensor").value(gson.toJson(s));
							}
						}else {
							writer.name("null").value("null");	
						}
						writer.endObject();
					}catch(SQLException e) {
			        	error="Error select sensor in room  "+e;
			        }	
				}
				
				else if(request.startsWith("countByFloor-Sensor")) {
					response=true;
					int nSensors = SensorManager.countByFloor(Integer.valueOf(res[2]));
					writer.beginObject();
					writer.name("nSensors").value(nSensors);
					writer.endObject();
				} else if(request.startsWith("countByWing-Sensor")) {
					response=true;
					int nSensors = SensorManager.countByWing(Integer.valueOf(res[2]));
					writer.beginObject();
					writer.name("nSensors").value(nSensors);
					writer.endObject();
				}else if(request.startsWith("countAll-Sensor")) {
					response=true;
					int nSensors = SensorManager.count();
					writer.beginObject();
					writer.name("nSensors").value(nSensors);
					writer.endObject();
				}
				break;
			}
			
		if(response)
			message=request+"-succusful";
		else
			message=request+"-failed: "+error;
		
		//Creation response Json
		if(!res[0].equals("select") && !res[0].equals("selectAll") && !res[0].startsWith("count")&& !res[0].startsWith("selectPosition")&& !res[0].startsWith("selectRoom")) {
			writer.beginObject();
			writer.name("response").value(message);
			writer.endObject();	
		}
		writer.flush();
		
		return message;
	}

	
	
}
