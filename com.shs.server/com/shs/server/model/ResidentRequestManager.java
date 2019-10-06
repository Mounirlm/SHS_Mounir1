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
import com.shs.commons.model.Resident;
import com.shs.commons.model.User;

public class ResidentRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Resident resident;
	ResidentManager residentManager;
	
	public ResidentRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Resident resident, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.request = request;
		this.resident = resident;
		residentManager = new ResidentManager(connDB);
	}
	
	
	
	public String requestManager() throws SQLException, IOException, ParseException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		if(request.startsWith("countByFloor-Resident")) {
			response=true;
			int nResidents = ResidentManager.countByFloor(Integer.valueOf(res[2]));
			writer.beginObject();
			writer.name("nResidents").value(nResidents);
			writer.endObject();
		}else if(request.startsWith("countByWing-Resident")) {
			response=true;
			int nResidents = ResidentManager.countByWing(Integer.valueOf(res[2]));
			writer.beginObject();
			writer.name("nResidents").value(nResidents);
			writer.endObject();
		}else if(request.startsWith("countAll-Resident")) {
			response=true;
			int nResidents = ResidentManager.count();
			writer.beginObject();
			writer.name("nResidents").value(nResidents);
			writer.endObject();
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
