package com.shs.server.connection.pool;

import java.sql.Connection;

import java.sql.SQLException;

// Data source permet d'utiliser la poll

public class DataSource {
	private static JDBCConnectionPool pool = null;  

	
	public DataSource() throws SQLException, ClassNotFoundException{
		initPool();
	}
	
	//instanciation of the pool if it was'nt the case
	public JDBCConnectionPool initPool() throws SQLException, ClassNotFoundException {
		if(pool==null)
			pool = new JDBCConnectionPool();
		
		return pool;
	}
		
	public static Connection getConnection() throws SQLException{
		return pool.getConnection();
	}
	
	public static void releaseConnection(Connection c) throws SQLException {
		pool.releaseConnection(c);
	}
	
	public static void shutdown() throws SQLException {
		pool.shutdownConnections();
	}

	public static int getSize() {
		return pool.getSize();
	}
	
}
