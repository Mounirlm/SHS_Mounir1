package com.shs.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shs.commons.model.User;
import com.shs.server.connection.pool.DataSource;


public class UserManager {
	private static Connection conn;
	
	public UserManager(Connection c){
		this.conn = c;
	}
	
	public static boolean create(User user) throws SQLException{
		PreparedStatement pStmt = conn.prepareStatement("INSERT INTO users (first_name,last_name,email, password,function) VALUES (?,?,?,?,?)");
		pStmt.setString(1, user.getFirst_name());
		pStmt.setString(2, user.getLast_name());
		pStmt.setString(3, user.getEmail());
		pStmt.setString(4, user.getPassword());
		pStmt.setString(5, user.getFunction());
		
		int n = pStmt.executeUpdate();
		// Closing
        pStmt.close();
        
        return n==1;
	}
	
	public static ArrayList<User> getUsers() throws SQLException {	
		Statement Stmt = conn.createStatement();
        ResultSet RS = Stmt.executeQuery("SELECT * FROM users");      
        ArrayList<User> userList = new ArrayList<User>();
        while(RS.next()){         
        	userList.add(new User(RS.getInt("id"), RS.getString("first_name"), RS.getString("last_name"), RS.getString("email"), RS.getString("password"), RS.getString("function")));           
        }
		
	    // Closing
	    RS.close();
	    Stmt.close();
	    
		
		return userList;
	
	}
	
	public static User getUser(int id) throws SQLException{
		Statement Stmt = conn.createStatement();
        ResultSet RS = Stmt.executeQuery("SELECT * FROM users WHERE id="+id);
        RS.next();
        User user = new User(RS.getInt("id"), RS.getString("first_name"), RS.getString("last_name"), RS.getString("email"), RS.getString("password"), RS.getString("function"));
        // closing
        RS.close();
        Stmt.close();
        
		return user;
	}
	
	public static List<User> getUsersBy(String req) throws SQLException{
		Statement Stmt = conn.createStatement();System.out.println(Stmt.isClosed());
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet rs=null;
        try{
	        rs = Stmt.executeQuery("SELECT * FROM users WHERE "+req);      
	        while(rs.next()){         
	        	userList.add(new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"), rs.getString("function")));           
	        }
		
		}finally{// Closing
			
		    rs.close();
		    Stmt.close();
		    
	    }
		
		return userList;
	}
	
	public static boolean update(User user) throws SQLException{
		PreparedStatement pStmt = conn.prepareStatement("UPDATE FROM users SET first_name=?, last_name=?, email=?, login=?, password=?, function=? WHERE id=?");
		pStmt.setString(1, user.getFirst_name());
		pStmt.setString(2, user.getLast_name());
		pStmt.setString(3, user.getEmail());
		pStmt.setString(4, user.getPassword());
		pStmt.setString(5, user.getFunction());
		pStmt.setInt(6, user.getId());
		int n = pStmt.executeUpdate();
		// Closing
        pStmt.close();
        
        return n==1;
	}
	
	public static boolean deleteUser(User user) throws SQLException{
		Statement Stmt = conn.createStatement();
		int n = Stmt.executeUpdate("DELETE FROM users WHERE id=" + user.getId());
		
        Stmt.close();
        
        return n==1;
	}
	
	public static boolean deleteAll() throws SQLException{
		Statement Stmt = conn.createStatement();
		int n = Stmt.executeUpdate("DELETE FROM users");
		//Closing
        Stmt.close();
        
        return n>0;
	}
	
}