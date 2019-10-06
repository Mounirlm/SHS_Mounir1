package com.shs.server.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.shs.commons.model.Type_Sensor;

public class Type_SensorManager {
private static Connection conn;
	
	public Type_SensorManager(Connection con) {
		System.out.println("ok"+con);
		this.conn=con;
	}
	
	public static ArrayList<Type_Sensor> getAllType_Sensor() throws SQLException{
		Statement Stmt = conn.createStatement();
        ArrayList<Type_Sensor> wing_roomList = new ArrayList<Type_Sensor>();
        ResultSet RS=null;
        
        try {
        RS = Stmt.executeQuery("SELECT * FROM type_sensor");
        
        while(RS.next()) {
        	wing_roomList.add(new Type_Sensor(RS.getInt("id"), RS.getString("name"), 
        			RS.getInt("trigger_point_min"), RS.getInt("trigger_point_max"),RS.getInt("nb_alerts")));
        }	
        }
        finally {
        // Closing
        
	    RS.close();
	    Stmt.close();
	    
        }
	    return wing_roomList;
	}
	
   public static Type_Sensor getType_Sensor(String name) throws SQLException {
		
		Statement Stmt = conn.createStatement();
		Type_Sensor ts = new Type_Sensor();
	       
        ResultSet RS=null;
       	
        try {
        	//
        RS = Stmt.executeQuery("SELECT * FROM type_sensor WHERE name="+name);
        
        while(RS.next()) {
        	
        	ts=new Type_Sensor(RS.getInt("id"),RS.getString("name"),RS.getInt("trigger_point_min"), RS.getInt("Integer trigger_point_max"), RS.getInt("Integer nb_alerts"));
        	}
        }	
        
        finally {
        // Closing
        
        if(RS!=null)
	        try{RS.close();}catch(Exception e){e.printStackTrace();} 
		
        if(Stmt!=null)
        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
        
        
        }
	    return ts;
		
	}
}
