package com.shs.server.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.shs.commons.model.Room;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;
import com.shs.server.connection.pool.DataSource;

public class Type_RoomManager {
	private static Connection conn;
	
	public Type_RoomManager(Connection con) {
		this.conn=con;
	}
	
	public static ArrayList<Type_Room> getAlType_Rooms() throws SQLException{
		Statement Stmt = conn.createStatement();
        ArrayList<Type_Room> type_roomList = new ArrayList<Type_Room>();
        ResultSet RS=null;
        
        try {
        RS = Stmt.executeQuery("SELECT * FROM type_room");
        
        while(RS.next()) {

        	type_roomList.add(new Type_Room(RS.getInt("id"), RS.getString("name")));
        }	
        }
        finally {
        // Closing
        
	    RS.close();
	    Stmt.close();
	   
        }
	    return type_roomList;
	}
}
