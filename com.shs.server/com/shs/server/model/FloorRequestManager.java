package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;


public class FloorRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Floor floor;
	FloorManager floorManager;
	
	public FloorRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Floor object,String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.floor= object;
		this.request = request;
		this.floorManager = new FloorManager(connDB);
	}
	

	public String requestManager() throws SQLException, IOException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
		case "select-Floor":
			try{
				List<Floor> floors;
				floors= FloorManager.getFloor();
				writer.beginObject();
				if(!floors.isEmpty()) {
					response=true;
					Gson gson = new Gson();
					for (Floor f : floors) {
						writer.name("floor").value(gson.toJson(f));
					}
				}else {
					writer.name("null").value("null");	
				}
				writer.endObject();
			}catch(SQLException e) {
	        	error="Error select  "+e;
	        	writer.name("null").value(error);
	        }	
				
			break;
		default:
			break;
		}
		
	if(response)
		message=request+"-succusful";
	else
		message=request+"-failed: "+error;
	
	//send to client
	writer.flush();
	
	return message;
}
		
		
}
