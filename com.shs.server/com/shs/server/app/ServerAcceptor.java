package com.shs.server.app;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.shs.server.connection.pool.AccessConfig;
import com.shs.server.connection.pool.DataSource;

public class ServerAcceptor {
	private static int cpt=0;
	 public static void main (String[] args) throws SQLException, ClassNotFoundException, IOException {
		 //create a serverSocket	
		 	ServerSocket server = null;
		    int port = AccessConfig.getPORT_SERVER();
		    DataSource dt = new DataSource();
		    
		    try {//PORT OF SERVER
			      server = new ServerSocket(port);
			      System.out.println("Server  Ok");
			   }catch(IOException e) { 
				   System.out.println("Error server "+e);
			   }
		    
		    if(server!=null) {
			      while ( true ) {
			    	  Connection connDB=null;
			    	  try {
			    	  connDB=DataSource.getConnection();
			    	  System.out.println("Connection DB ok");
			    	  }catch(SQLException e1) {
			    		  System.out.println("Connection DB Refused "+e1);
			    	  }
			    	  if(connDB != null) {
				        System.out.println("Waiting client") ;
				        Socket client = null;
						try {
							client = server.accept();
						} catch (IOException e) {System.out.println("Error connection client");}
						cpt++;
				        System.out.println("Connection established "+cpt);      
				        //creation RequsetHandlre
				        RequestHandler req = new RequestHandler(client, connDB);
				        Thread service = new Thread(req);
				        service.start();
			    	  }
			      } 
		      }
	 }
		    
	 }



