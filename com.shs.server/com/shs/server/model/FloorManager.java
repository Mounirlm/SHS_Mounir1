package com.shs.server.model;


import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;


public class FloorManager {
	
	
	private static Connection conn;
	
	
	
	public FloorManager(Connection con) {
		this.conn=con;
	}
	
	
public static ArrayList<Floor> getFloor() throws SQLException{
		
		Statement Stmt = conn.createStatement();
		Statement Stmt2 = conn.createStatement();
		
		
        ArrayList<Floor> floorList = new ArrayList<Floor>();
       
        ResultSet RS=null;
        ResultSet rsbuilding=null;
        
             	
        try {
        	//
        RS = Stmt.executeQuery("SELECT * FROM floor_map");
        
        while(RS.next()) {
        	       	
        	rsbuilding=Stmt2.executeQuery ("SELECT * FROM building WHERE id="+RS.getInt("fk_building"));
        	
        	if (rsbuilding.next()){
        		
        	floorList.add(new Floor(RS.getInt("id"),RS.getString("name"),RS.getString("image_path"),
        			new Building(rsbuilding.getInt("id"),rsbuilding.getString("name"),rsbuilding.getString("type"))));
        		}
        	}
        }	
        
        finally {
        // Closing
        
        if(RS!=null)
	        try{RS.close();}catch(Exception e){e.printStackTrace();} 
        
        if(rsbuilding!=null)
        	try{rsbuilding.close();}catch(Exception e){e.printStackTrace();}
		
        if(Stmt!=null)
        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
        if(Stmt2!=null)
        	try{Stmt2.close();}catch(Exception e){e.printStackTrace();}
        
        }
	    return floorList;
	}


	
	
	
	
}
