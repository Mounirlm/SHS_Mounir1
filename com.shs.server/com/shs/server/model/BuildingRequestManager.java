package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;



public class BuildingRequestManager {
	
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Building building;
	
	BuildingManager buildingManager;
	
	public BuildingRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.request = request;
		
		this.buildingManager = new BuildingManager(connDB);
	}

	public String requestManager() throws SQLException, IOException, ParseException {
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
		 
		
		case "select-Building": 
			try{
				List<Building> buildings;
				buildings= BuildingManager.getBuilding();
				
				
				writer.beginObject();
				
				if(!buildings.isEmpty()) {
					response=true;
					Gson gson = new Gson();
					for (Building b : buildings) {
						writer.name("building").value(gson.toJson(b));
					}
					
					
				}else {
					writer.name("null").value("null");	
				}
				
				writer.endObject();
				
				}catch(SQLException e) {
			    	error="Error select "+e;
			    
				}
				break;
				}
		
				if(response) 
						message=request+"-succusful";
				else 
						message=request+"-failed: "+error;
	
	//send to client
	
	
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
