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

import com.shs.commons.model.Historical;
import com.shs.server.connection.pool.DataSource;

public class HistoricalManager {

	private static Connection conn;

	public HistoricalManager(Connection con) {
		this.conn=con;
	}


	public static ArrayList<Historical> getHistoricals(String req) throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();

		ArrayList<Historical>historicalList = new ArrayList<Historical>();
		ResultSet RS=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if(req.isEmpty())
				RS = Stmt.executeQuery("SELECT * FROM historical");
			else
				RS = Stmt.executeQuery(req);

			while(RS.next()) {
				historicalList.add(new Historical(RS.getInt("id"),dateFormat.parse(RS.getString("date_signal")), 
						Time.valueOf(RS.getString("hour_signal")),RS.getString("message"),
						RS.getInt("fk_sensor")));


			}	
		}
		finally {
			// Closing
			DataSource.releaseConnection(conn);
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			 
		}
		return historicalList;
	}
	
	
	public static Historical getHistorical(int id) throws SQLException, ParseException{
		Statement Stmt = conn.createStatement();
		Historical Historical=null;
		ResultSet RS=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			RS = Stmt.executeQuery("SELECT * FROM historical where id="+id);

			while(RS.next()) {

				Historical = new Historical(RS.getInt("id"),dateFormat.parse(RS.getString("date_signal")), 
						Time.valueOf(RS.getString("hour_signal")),RS.getString("msessage"),
						RS.getInt("fk_sensor"));

			}	
		}
		finally {
			// Closing
			DataSource.releaseConnection(conn);
			if(RS!=null)
				try{RS.close();}catch(Exception e){e.printStackTrace();} 
			if(Stmt!=null)
				try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			 
		}
		return Historical;
	}
	
	public static boolean create(Historical historical) throws SQLException{
		Statement Stmt = conn.createStatement();
		
		String req = "insert into historical (date_signal, hour_signal, message, fk_sensor)"
				+ " values ('"+historical.getDate_signal_formatted()+"','"+historical.getHour_signal()+"','"+historical.getMessage()+"',"
						+ ""+historical.getFk_sensor()+");";
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
	
	public static boolean update(Historical historical) throws SQLException {
		Statement Stmt = conn.createStatement();
		
		String req = "update historical set date_signal='"+historical.getDate_signal_formatted()+"', hour_signal='"+historical.getHour_signal()+"',"
				+ " message='"+historical.getMessage()+"',"
				+ " fk_sensor="+historical.getFk_sensor()+", where id="+historical.getId()+";";
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
	
	//TODO ADD CASCADE CONSTRAINTS ON DELETE IN SCRIPT
		public static boolean delete(Historical historical) throws SQLException{
			Statement Stmt = conn.createStatement();
			int n=0;
			try{
			n = Stmt.executeUpdate("DELETE FROM historical WHERE id=" + historical.getId());}
			finally {
			//Closing
			DataSource.releaseConnection(conn);
			if(Stmt!=null)
	        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} 
			}
	        return n==1;
		}

		public static boolean deleteAll() throws SQLException{
			Statement Stmt = conn.createStatement();
			int n=0;
			try{
			n = Stmt.executeUpdate("DELETE FROM historical");
			}finally {
				//Closing
				DataSource.releaseConnection(conn);
				if(Stmt!=null)
		        	try{Stmt.close();}catch(Exception e){e.printStackTrace();} ;
		        
	        }
	        return n>0;
		}
}