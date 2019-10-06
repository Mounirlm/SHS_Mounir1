package com.shs.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.shs.commons.model.Alert;
import com.shs.server.connection.pool.DataSource;

public class AlertManager {
	private static Connection conn;

	public AlertManager(Connection con) {
		this.conn=con;
	}


	public static ArrayList<Alert> getAlerts() throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();

		ArrayList<Alert> alertList = new ArrayList<Alert>();
		ResultSet RS=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			RS = Stmt.executeQuery("SELECT * FROM alert");

			while(RS.next()) {
				alertList.add(new Alert(RS.getInt("id"),dateFormat.parse(RS.getString("date_alert")), 
						Time.valueOf(RS.getString("hour_alert")),RS.getString("description"),
						RS.getInt("fk_users"),RS.getInt("fk_sensor"), RS.getBoolean("status")));
			}	
		}
		finally {
			// Closing
			
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			 
		}
		return alertList;
	}
	
	
	public static Alert getAlert(int id) throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();
		Alert alert=null;
		ResultSet RS=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			RS = Stmt.executeQuery("SELECT * FROM Alert where id="+id);

			while(RS.next()) {

				alert = new Alert(RS.getInt("id"),dateFormat.parse(RS.getString("date_alert")), 
						Time.valueOf(RS.getString("hour_alert")),RS.getString("description"),
						RS.getInt("fk_users"),RS.getInt("fk_sensor"), RS.getBoolean("status"));

			}	
		}
		finally {
			// Closing
			
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			 
		}
		return alert;
	}
	
	public static boolean create(Alert alert) throws SQLException{
		Statement Stmt = conn.createStatement();
		
		String req = "insert into alert (date_alert, hour_alert, description, fk_sensor, fk_users, status)"
				+ " values ('"+alert.getDate_alert_formatted()+"','"+alert.getHour_alert()+"',"
						+ "'"+alert.getDescription()+"',"+alert.getFk_sensor()+","+alert.getFk_user()+","+alert.getStatus()+");";
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
	
	public static boolean update(Alert alert) throws SQLException {
		Statement Stmt = conn.createStatement();
		
		String req= "update Alert set date_alert='"+alert.getDate_alert_formatted()+"', hour_alert='"+alert.getHour_alert()+"', "
				+ "description='"+alert.getDescription()+"',"
				+ " fk_sensor="+alert.getFk_sensor()+", fk_users="+alert.getFk_user()+", status="+alert.getStatus()+" where id="+alert.getId()+"";
		
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
		public static boolean delete(Alert alert) throws SQLException{
			Statement Stmt = conn.createStatement();
			int n=0;
			try{
			n = Stmt.executeUpdate("DELETE FROM alert WHERE id=" + alert.getId());}
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
			n = Stmt.executeUpdate("DELETE FROM alert");System.out.println(n);
			}finally {
				//Closing
				
				if(Stmt!=null)
		        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} ;
		        
	        }
	        return n>0;
		}


		public static int countByFloorMonthYear(int floor, int month, int year) throws SQLException {
			PreparedStatement pStmt;
			if(month==0) {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_room = r.id  WHERE r.floor=? and DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, floor);
				pStmt.setInt(2, year);
			}
			else {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_room = r.id  WHERE r.floor=? and DATE_PART('month', date_alert)=? and DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, floor);
				pStmt.setInt(2, month);
				pStmt.setInt(3, year);
			}
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}


		public static int countByWingMonthYear(int wing, int month, int year) throws SQLException {
			PreparedStatement pStmt;
			if(month==0) {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_room = r.id WHERE r.fk_wing_room=? and DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, wing);
				pStmt.setInt(2, year);
			}
			else {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_room = r.id WHERE r.fk_wing_room=? and DATE_PART('month', date_alert)=? and DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, wing);
				pStmt.setInt(2, month);
				pStmt.setInt(3, year);
			}
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		
		public static int count(int month, int year) throws SQLException {
			PreparedStatement pStmt;
			if(month==0) {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_wing_room = r.id  WHERE DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, year);
			}
			else {
				pStmt = conn.prepareStatement("SELECT COUNT(*) FROM alert INNER JOIN sensor s ON fk_sensor = s.id INNER JOIN room r ON fk_wing_room = r.id  WHERE DATE_PART('month', date_alert)=? and DATE_PART('year', date_alert)=?;");
				pStmt.setInt(1, month);
				pStmt.setInt(2, year);
			}
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
}