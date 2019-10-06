package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Wing_Room;

public class Wing_RoomRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	Wing_RoomManager wing_roomManager;
	
	public Wing_RoomRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.request = request;
		this.wing_roomManager = new Wing_RoomManager(connDB);
	}
	
	public String requestManager() throws SQLException, IOException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "selectAll-Wing_Room":
				try{
					List<Wing_Room> wings;
					wings= Wing_RoomManager.getAllWing_Rooms();
					writer.beginObject();
					if(!wings.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Wing_Room room : wings) {
							writer.name("wings").value(gson.toJson(room));
						}
					}else {
						writer.name("null").value("null");	
					}
					writer.endObject();
				}catch(SQLException e) {
		        	error="Error select all "+e;
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
