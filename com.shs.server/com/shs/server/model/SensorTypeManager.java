package com.shs.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Sensor;
import com.shs.commons.model.Wing_Room;
import com.shs.commons.model.Building;
import com.shs.commons.model.Room;
import com.shs.server.connection.pool.DataSource;

public class SensorTypeManager {
	private static Connection conn;

	public SensorTypeManager(Connection con) {
		this.conn=con;
	}

	public boolean update(Type_Sensor typeSensor) throws SQLException {
		Statement Stmt = conn.createStatement();

		String req = "update Type_Sensor set name='"+typeSensor.getName()+"', trigger_point_min='"+typeSensor.getTrigger_point_min()+"', "
				+ "trigger_point_max='"+typeSensor.getTrigger_point_max()+"',"
				+ "nb_alerts='"+typeSensor.getNb_alerts()+"' where id="+typeSensor.getId()+";";
		int n=0;
		try {
			n = Stmt.executeUpdate(req);}
		finally {
			// Closing
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();}  
			DataSource.releaseConnection(conn);
		}
		return n==1;
	}

	public List<Type_Sensor> select() throws SQLException {
		
		Statement Stmt = conn.createStatement();
		ArrayList<Type_Sensor> sensorTypeList = new ArrayList<Type_Sensor>();
	       
        ResultSet RS=null;
       	
        try {
        	//
        RS = Stmt.executeQuery("SELECT * FROM type_sensor");
        
        while(RS.next()) {
        	
        	sensorTypeList.add(new Type_Sensor(RS.getInt("id"),RS.getString("name"),RS.getInt("trigger_point_min"), RS.getInt("Integer trigger_point_max"), RS.getInt("Integer nb_alerts")));
        	}
        }	
        
        finally {
        // Closing
        
        if(RS!=null)
	        try{RS.close();}catch(Exception e){e.printStackTrace();} 
		
        if(Stmt!=null)
        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
        
        
        }
	    return sensorTypeList;
		
	}
	
    
	
}