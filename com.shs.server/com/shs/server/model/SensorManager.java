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

import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Type_Sensor;
import com.shs.commons.model.Wing_Room;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.server.connection.pool.DataSource;

public class SensorManager {
	private static Connection conn;

	public SensorManager(Connection con) {
		this.conn=con;
	}


	public static ArrayList<Sensor> getSensors(String req) throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();
		Statement Stmt2 = conn.createStatement();
		Statement Stmt3 = conn.createStatement();

		ArrayList<Sensor> sensorsList = new ArrayList<Sensor>();
		ResultSet RS=null;
		ResultSet rswing_room=null;
		ResultSet rstype_sensor=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (req.isEmpty()) {
				RS = Stmt.executeQuery("SELECT * FROM sensor");
			}else {
				RS = Stmt.executeQuery(req);
			}
			

			while(RS.next()) {
				rswing_room=Stmt2.executeQuery("SELECT * FROM wing_room WHERE id="+RS.getInt("fk_position"));
				rstype_sensor=Stmt3.executeQuery("SELECT * FROM type_sensor WHERE id="+RS.getInt("fk_type_sensor"));


				if ( rswing_room.next()  && rstype_sensor.next()) {
					Room room = new Room();
					RoomManager room_manager = new RoomManager(conn);
					room = RoomManager.getRoom(RS.getInt("fk_room"));

					sensorsList.add(new Sensor(RS.getInt("id"),RS.getString("sensor_name"), RS.getString("ip_address"), RS.getString("mac_address"),
							dateFormat.parse(RS.getString("date_setup")), RS.getBoolean("status"), RS.getBoolean("installed"),
							new Wing_Room(rswing_room.getInt("id"), rswing_room.getString("name")),
							RS.getFloat("price"),
							room,
							new Type_Sensor(rstype_sensor.getInt("id"), rstype_sensor.getString("name"),
									rstype_sensor.getInt("trigger_point_min"),rstype_sensor.getInt("trigger_point_max"),
									rstype_sensor.getInt("nb_alerts")),
							RS.getInt("scope_sensor")));
				
				}
			}	
		}
		finally {
			// Closing
			
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(rstype_sensor!=null)
				try{rstype_sensor.close();}catch(Exception e){e.printStackTrace();} 
			if(rswing_room!=null)
				try{rswing_room.close();}catch(Exception e){e.printStackTrace();}  
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt2!=null)
				try{Stmt2.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt3!=null)
				try{Stmt3.close();}catch(Exception e){e.printStackTrace();}  
		}
		return sensorsList;
	}

	public static Sensor getSensor(int id) throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();
		Statement Stmt2 = conn.createStatement();
		Statement Stmt3 = conn.createStatement();

		Sensor sensor = null;
		ResultSet RS=null;
		ResultSet rswing_room=null;
		ResultSet rstype_sensor=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			RS = Stmt.executeQuery("SELECT * FROM sensor WHERE id="+id);

			while(RS.next()) {
				rswing_room=Stmt2.executeQuery("SELECT * FROM wing_room WHERE id="+RS.getInt("fk_position"));
				rstype_sensor=Stmt3.executeQuery("SELECT * FROM type_sensor WHERE id="+RS.getInt("fk_type_sensor"));


				if ( rswing_room.next()  && rstype_sensor.next()) {
					Room room = new Room();
					room.setId(RS.getInt("fk_room"));
				
					sensor = new Sensor(RS.getInt("id"),RS.getString("sensor_name"), RS.getString("ip_address"), RS.getString("mac_address"),
							dateFormat.parse(RS.getString("date_setup")), RS.getBoolean("status"), RS.getBoolean("installed"),
							new Wing_Room(rswing_room.getInt("id"), rswing_room.getString("name")),
							RS.getFloat("price"),
							room,
							new Type_Sensor(rstype_sensor.getInt("id"), rstype_sensor.getString("name"),
									rstype_sensor.getInt("trigger_point_min"),rstype_sensor.getInt("trigger_point_max"),
									rstype_sensor.getInt("nb_alerts")),
							RS.getInt("scope_sensor"));

				}
			}	
		}
		finally {
			// Closing
			
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(rstype_sensor!=null)
				try{rstype_sensor.close();}catch(Exception e){e.printStackTrace();} 
			if(rswing_room!=null)
				try{rswing_room.close();}catch(Exception e){e.printStackTrace();}  
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt2!=null)
				try{Stmt2.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt3!=null)
				try{Stmt3.close();}catch(Exception e){e.printStackTrace();}  
		}
		return sensor;
	}

	public static boolean create(Sensor sensor) throws SQLException{
		Statement Stmt = conn.createStatement();
		String req = "insert into sensor (sensor_name, ip_address, mac_address,"
				+ " date_setup, status, installed, fk_position, price, fk_room, fk_type_sensor, scope_sensor)"
				+ " values ('"+sensor.getSensor_name()+"', '"+sensor.getIp_address()+"', "
				+ "'"+sensor.getMac_address()+"', '"+sensor.getDate_setup_formatted()+"', "
				+ ""+sensor.getStatus()+", "+sensor.getInstalled()+", "+sensor.getFk_position().getId()+","
				+ " "+sensor.getPrice()+", "+sensor.getFk_room().getId()+", "
				+ ""+sensor.getFk_type_sensor().getId()+", "+sensor.getScope_sensor()+");";
		int n=0;
		try {
			n = Stmt.executeUpdate(req);}
		finally {
			// Closing
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();}  
			
		}
		return n==1;
	}


	public static boolean update(Sensor sensor) throws SQLException {
		Statement Stmt = conn.createStatement();

		String req = "update sensor set sensor_name='"+sensor.getSensor_name()+"', ip_address='"+sensor.getIp_address()+"', "
				+ "mac_address='"+sensor.getMac_address()+"',"
				+ " date_setup='"+sensor.getDate_setup_formatted()+"', status="+sensor.getStatus()+", installed="+sensor.getInstalled()+", "
				+ "fk_position="+sensor.getFk_position().getId()+", price="+sensor.getPrice()+", "
				+ "fk_room="+sensor.getFk_room().getId()+", fk_type_sensor="+sensor.getFk_type_sensor().getId()+", "
				+ "scope_sensor="+sensor.getScope_sensor()+" where id="+sensor.getId()+";";
		int n=0;
		try {
			n = Stmt.executeUpdate(req);}
		finally {
			// Closing
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();}  
			
		}
		return n==1;
	}
	//TODO ADD CASCADE CONSTRAINTS ON DELETE IN SCRIPT
	public static boolean delete(Sensor sensor) throws SQLException{
		Statement Stmt = conn.createStatement();
		int n=0;
		try{
			n = Stmt.executeUpdate("DELETE FROM sensor WHERE id=" + sensor.getId());}
		finally {
			//Closing
			
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
		}
		return n==1;
	}

	public static boolean deleteAll() throws SQLException{
		Statement Stmt = conn.createStatement();
		int n=0;
		try{
			n = Stmt.executeUpdate("DELETE FROM sensor");System.out.println(n);
		}finally {
			//Closing
			
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} ;

		}
		return n>0;
	}


	public static int countByFloor(Integer floor) throws SQLException {
		PreparedStatement pStmt = conn.prepareStatement("SELECT COUNT(*) FROM sensor INNER JOIN room r ON fk_room = r.id  WHERE r.floor=?;");
		pStmt.setInt(1, floor);
		ResultSet rs = pStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}


	public static int countByWing(Integer wing) throws SQLException {
		PreparedStatement pStmt = conn.prepareStatement("SELECT COUNT(*) FROM sensor INNER JOIN room r ON fk_room = r.id  WHERE r.fk_wing_room=?;");
		pStmt.setInt(1, wing);
		ResultSet rs = pStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
	
	public static int count() throws SQLException {
		PreparedStatement pStmt = conn.prepareStatement("SELECT COUNT(*) FROM sensor");
		ResultSet rs = pStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}
	
	public static ArrayList<Sensor> getSensorsWithPosition() throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();
		Statement Stmt2 = conn.createStatement();
		Statement Stmt3 = conn.createStatement();
		Statement Stmt4 = conn.createStatement();
		Statement Stmt5 = conn.createStatement();
		Statement Stmt6 = conn.createStatement();
		Statement Stmt7 = conn.createStatement();

		ArrayList<Sensor> sensorsList = new ArrayList<Sensor>();
		ResultSet RS=null;
		ResultSet rswing_room=null;
		ResultSet rstype_sensor=null;
		ResultSet rsroom=null;
		ResultSet rsfloor =null;
		ResultSet rstype_room=null;
		ResultSet  rsbuilding = null;
	
		
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			
				RS = Stmt.executeQuery("SELECT * FROM sensor");
			
			

			while(RS.next()) {
				rswing_room=Stmt2.executeQuery("SELECT * FROM wing_room ");
				rstype_sensor=Stmt3.executeQuery("SELECT * FROM type_sensor");
				rsroom=Stmt4.executeQuery("SELECT * FROM room ");
				rsfloor=Stmt5.executeQuery("SELECT * FROM floor_map");
				rstype_room=Stmt6.executeQuery("SELECT * FROM type_room");
				rsbuilding=Stmt7.executeQuery("SELECT * FROM building");
				

				if ( rswing_room.next()  && rstype_sensor.next()&& rsroom.next()&& rsfloor.next()&& rsbuilding.next()&& rstype_room.next()) {
					
					Room room = new Room();
					RoomManager room_manager = new RoomManager(conn);
					room = RoomManager.getRoom(RS.getInt("fk_room")); 
					
					
					
					sensorsList.add(new Sensor(RS.getInt("id"),RS.getString("sensor_name"), RS.getString("ip_address"), RS.getString("mac_address"),
							dateFormat.parse(RS.getString("date_setup")), RS.getBoolean("status"), RS.getBoolean("installed"),
							new Wing_Room(rswing_room.getInt("id"), rswing_room.getString("name")),
							RS.getFloat("price"),
							new Room(rsroom.getInt("id"),rsroom.getInt("floor"), rsroom.getInt("room_number"), rsroom.getInt("m2"),
									new Type_Room(rstype_room.getInt("id"), rstype_room.getString("name")),
									new Wing_Room(rswing_room.getInt("id"), rswing_room.getString("name")),
									rsroom.getInt("nb_doors"), rsroom.getInt("nb_windows"),rsroom.getInt("x"),rsroom.getInt("y"),rsroom.getInt("width"),rsroom.getInt("height"),	 
				        			
									new Floor(rsfloor.getInt("id"),rsfloor.getString("name"),rsfloor.getString("image_path"),
				                			new Building(rsbuilding.getInt("id"),rsbuilding.getString("name"),rsbuilding.getString("type")))),
							
							new Type_Sensor(rstype_sensor.getInt("id"), rstype_sensor.getString("name"),
									rstype_sensor.getInt("trigger_point_min"),rstype_sensor.getInt("trigger_point_max"),
									rstype_sensor.getInt("nb_alerts")),
							RS.getInt("scope_sensor"),RS.getInt("x"),RS.getInt("y")));
				
				}
			}	
		}
		finally {
			// Closing
			
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(rstype_sensor!=null)
				try{rstype_sensor.close();}catch(Exception e){e.printStackTrace();} 
			if(rswing_room!=null)
				try{rswing_room.close();}catch(Exception e){e.printStackTrace();}  
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt2!=null)
				try{Stmt2.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt3!=null)
				try{Stmt3.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt4!=null)
				try{Stmt4.close();}catch(Exception e){e.printStackTrace();}
		}
		return sensorsList;
	}
	
	public static ArrayList<Sensor> getSensorsInRoom(int idRoom) throws SQLException, ParseException{
		
		Statement Stmt = conn.createStatement();
		Statement Stmt2 = conn.createStatement();
		Statement Stmt3 = conn.createStatement();
		
		
		ArrayList<Sensor> sensorsList = new ArrayList<Sensor>();
		ResultSet RS=null;
		ResultSet rswing_room=null;
		ResultSet rstype_sensor=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String SQL_SELECT = "SELECT * FROM  sensor WHERE fk_room="+ idRoom;
		try {
		
	    RS = Stmt.executeQuery(SQL_SELECT);
		
		while (RS.next()) {
			
			rswing_room=Stmt2.executeQuery("SELECT * FROM wing_room ");
			rstype_sensor=Stmt3.executeQuery("SELECT * FROM type_sensor");
			
			if ( rswing_room.next()  && rstype_sensor.next()){
			
			Room room = new Room();
			room.setId(idRoom);
			
			
			sensorsList.add(new Sensor(RS.getInt("id"),RS.getString("sensor_name"), RS.getString("ip_address"), RS.getString("mac_address"),
					dateFormat.parse(RS.getString("date_setup")), RS.getBoolean("status"), RS.getBoolean("installed"),
					new Wing_Room(rswing_room.getInt("id"), rswing_room.getString("name")),
					RS.getFloat("price"),
					room,
					new Type_Sensor(rstype_sensor.getInt("id"), rstype_sensor.getString("name"),
							rstype_sensor.getInt("trigger_point_min"),rstype_sensor.getInt("trigger_point_max"),
							rstype_sensor.getInt("nb_alerts")),
					RS.getInt("scope_sensor"),RS.getInt("x"),RS.getInt("y")));
			 }	
        			
		 }
	 }   
	finally {
		// Closing
		
		if(RS!=null)
			try{RS.close();}catch(Exception e){e.printStackTrace();} 
		if(rstype_sensor!=null)
			try{rstype_sensor.close();}catch(Exception e){e.printStackTrace();} 
		if(rswing_room!=null)
			try{rswing_room.close();}catch(Exception e){e.printStackTrace();}  
		if(Stmt!=null)
			try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
		if(Stmt2!=null)
			try{Stmt2.close();}catch(Exception e){e.printStackTrace();} 
		if(Stmt3!=null)
			try{Stmt3.close();}catch(Exception e){e.printStackTrace();} 
		
			}		
		
		return sensorsList;
	}
	
}