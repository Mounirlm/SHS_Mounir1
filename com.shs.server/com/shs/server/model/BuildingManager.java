package com.shs.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;

public class BuildingManager {


	private static Connection conn;
	
	public BuildingManager(Connection con) {
		this.conn=con;
	}
	
	public static ArrayList<Building> getBuilding() throws SQLException{
		
		Statement Stmt = conn.createStatement();
		
		
        ArrayList<Building> buildingList = new ArrayList<Building>();
       
        ResultSet RS=null;
       	
        try {
        	//
        RS = Stmt.executeQuery("SELECT * FROM building");
        
        while(RS.next()) {
        	
        	buildingList.add(new Building(RS.getInt("id"),RS.getString("name"),RS.getString("type")));
        	
        	}
        }	
        
        finally {
        // Closing
        
        if(RS!=null)
	        try{RS.close();}catch(Exception e){e.printStackTrace();} 
		
        if(Stmt!=null)
        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
        
        
        }
	    return buildingList;
	}
	
	

	
}
