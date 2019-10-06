package com.shs.server.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.shs.commons.model.Wing_Room;
import com.shs.server.connection.pool.DataSource;

public class Wing_RoomManager {
private static Connection conn;
	
	public Wing_RoomManager(Connection con) {
		System.out.println("ok"+con);
		this.conn=con;
	}
	
	public static ArrayList<Wing_Room> getAllWing_Rooms() throws SQLException{
		Statement Stmt = conn.createStatement();
        ArrayList<Wing_Room> wing_roomList = new ArrayList<Wing_Room>();
        ResultSet RS=null;
        
        try {
        RS = Stmt.executeQuery("SELECT * FROM wing_room");
        
        while(RS.next()) {

        	wing_roomList.add(new Wing_Room(RS.getInt("id"), RS.getString("name")));
        }	
        }
        finally {
        // Closing
        
	    RS.close();
	    Stmt.close();
	    
        }
	    return wing_roomList;
	}
}
