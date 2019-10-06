package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Historical;
import com.shs.commons.model.User;

public class HistoricalRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Historical historical;
	HistoricalManager historicalManager;
	
	public HistoricalRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Historical historical, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.request = request;
		this.historical = historical;
		historicalManager = new HistoricalManager(connDB);
	}
	
	
	
	public String requestManager() throws SQLException, IOException, ParseException {		
		boolean response = false;
		String message="empty", error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "insert-Historical":
				try{
					response=HistoricalManager.create(historical);
				}
			    catch(SQLException e) {
			    	error="Error insertion "+e;
			    }
				break;
			case "update-Historical":
				try{
					response=HistoricalManager.update(historical);
				}
			    catch(SQLException e) {
			    	error="Error updating "+e;
			    }
				break;
			case "delete-Historical":
				try{
					response=HistoricalManager.delete(historical);
				}
			    catch(SQLException e) {
			    	error="Error delete "+e;
			    }
				break;
			case "deleteAll-Historical":
				try{
					response=HistoricalManager.deleteAll();
				}
			    catch(SQLException e) {
			    	error="Error delete all "+e;
			    }				
				break;	
			case "select-Historical":
					Historical sendHist=null;
					try{
						sendHist=HistoricalManager.getHistorical(historical.getId());
						response=true;
					}
				    catch(SQLException e) {
				    	error="Error delete "+e;
				    } catch (ParseException e) {
						error+=" and Date Parse error "+e;
					}
					break;
			case "selectAll-Historical":
				try{
					List<Historical> historicals = HistoricalManager.getHistoricals("");
					writer.beginObject();
					if(!historicals.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Historical historical : historicals) {
							writer.name("historical").value(gson.toJson(historical));
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
				break;
			}
			
		if(response)
			message=request+"-succusful";
		else
			message=request+"-failed: "+error;
		
		//Creation response Json
		if(!res[0].equals("select") && !res[0].equals("selectAll")) {
			writer.beginObject();
			writer.name("response").value(message);
			writer.endObject();	
		}
		writer.flush();
		
		return message;
	}





}
