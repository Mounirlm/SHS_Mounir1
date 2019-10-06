package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Alert;
import com.shs.commons.model.User;

public class AlertRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Alert alert;
	AlertManager alertManager;
	
	public AlertRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Alert alert, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.request = request;
		this.alert = alert;
		alertManager = new AlertManager(connDB);
	}
	
	
	
	public String requestManager() throws SQLException, IOException, ParseException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "insert-Alert":
				try{
					response=AlertManager.create(alert);
				}
			    catch(SQLException e) {
			    	error="Error insertion "+e;
			    }
				break;
			case "update-Alert":
				try{
					response=AlertManager.update(alert);
				}
			    catch(SQLException e) {
			    	error="Error updating "+e;
			    }
				break;
			case "delete-Alert":
				try{
					response=AlertManager.delete(alert);
				}
			    catch(SQLException e) {
			    	error="Error delete "+e;
			    }
				break;
			case "deleteAll-Alert":
				try{
					response=AlertManager.deleteAll();
				}
			    catch(SQLException e) {
			    	error="Error delete all "+e;
			    }				
				break;	
			case "select-Alert":
					Alert sendAlert=null;
					try{
						sendAlert=AlertManager.getAlert(alert.getId());
						response=true;
					}
				    catch(SQLException e) {
				    	error="Error delete "+e;
				    } catch (ParseException e) {
						error+=" and Date Parse error "+e;
					}
					break;
			case "selectAll-Alert":
				try{
					List<Alert> alerts = AlertManager.getAlerts();
					writer.beginObject();
					if(!alerts.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Alert alert : alerts) {
							writer.name("alert").value(gson.toJson(alert));
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
				if(request.startsWith("countByFloor-Alert")) {
					response=true;
					int nAlerts = AlertManager.countByFloorMonthYear(Integer.valueOf(res[2]),Integer.valueOf(res[3]),Integer.valueOf(res[4]));
					writer.beginObject();
					writer.name("nAlerts").value(nAlerts);
					writer.endObject();
				}else if(request.startsWith("countByWing-Alert")){
					response=true;
					int nAlerts = AlertManager.countByWingMonthYear(Integer.valueOf(res[2]),Integer.valueOf(res[3]),Integer.valueOf(res[4]));
					writer.beginObject();
					writer.name("nAlerts").value(nAlerts);
					writer.endObject();
				}else if(request.startsWith("countAll-Alert")){
					response=true;
					int nAlerts = AlertManager.count(Integer.valueOf(res[2]),Integer.valueOf(res[3]));
					writer.beginObject();
					writer.name("nAlerts").value(nAlerts);
					writer.endObject();
				}
				break;
			}
			
		if(response)
			message=request+"-succusful";
		else
			message=request+"-failed: "+error;
		
		//Creation response Json
		if(!res[0].equals("select") && !res[0].equals("selectAll") && !res[0].startsWith("count")) {
			writer.beginObject();
			writer.name("response").value(message);
			writer.endObject();	
		}
		writer.flush();
		
		return message;
	}





}
