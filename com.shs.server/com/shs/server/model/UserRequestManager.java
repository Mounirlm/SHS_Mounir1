package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.User;

public class UserRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private User user;
	UserManager userManager;
	
	public UserRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, User object, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.user = object;
		this.request = request;
		this.userManager = userManager= new UserManager(connDB);
	}

	
	public String requestManager() throws SQLException, IOException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "insert-User":
				try{
					response=userManager.create(user);
				}
			    catch(SQLException e) {
			    	error="Error insertion "+e;
			    }
				break;
			case "update-User":
				try{
					response=userManager.update((user));
				}
			    catch(SQLException e) {
			    	error="Error updating "+e;
			    }
				break;
			case "delete-User":
				try{
					response=userManager.deleteUser((user));
				}
			    catch(SQLException e) {
			    	error="Error delete "+e;
			    }
				break;
			case "deleteAll-User":
				try{
					response=userManager.deleteAll();
				}
			    catch(SQLException e) {
			    	error="Error delete all "+e;
			    }				
				break;	
			case "select-User":
					try{
						User sendUser=null;
						String reqDB=null;
						List<User> users=new ArrayList<>();
						if(user.getId()!=null) {
							sendUser= UserManager.getUser(user.getId());
						}else {
							if(user.getFirst_name()!=null) {
								reqDB="first_name = '"+user.getFirst_name()+"'";
								if(user.getLast_name()!=null)
									reqDB+="and last_name = '"+user.getLast_name()+"'";
								if(user.getEmail()!=null)
									reqDB+="and email = '"+user.getEmail()+"'";
								if(user.getPassword()!=null)
									reqDB+="and password = '"+user.getPassword()+"'";
								if(user.getFunction()!=null)
									reqDB+="and function = '"+user.getFunction()+"'";
							}
							else if(user.getLast_name()!=null) {
									reqDB="last_name = '"+user.getLast_name()+"'";
								if(user.getEmail()!=null)
									reqDB+="and email = '"+user.getEmail()+"'";
								if(user.getPassword()!=null)
									reqDB+="and password = '"+user.getPassword()+"'";
								if(user.getFunction()!=null)
									reqDB+="and function = '"+user.getFunction()+"'";
							}
							else if(user.getEmail()!=null) {
								reqDB="email = '"+user.getEmail()+"'";
								if(user.getPassword()!=null)
									reqDB+="and password = '"+user.getPassword()+"'";
								if(user.getFunction()!=null)
									reqDB+="and function = '"+user.getFunction()+"'";
							}
							else if(user.getPassword()!=null) {
									reqDB="password = '"+user.getPassword()+"'";
								if(user.getFunction()!=null)
									reqDB+="and function = '"+user.getFunction()+"'";
							}
							else if(user.getFunction()!=null) {
								reqDB="function = '"+user.getFunction()+"'";
							}
							reqDB+=";";
							users=UserManager.getUsersBy(reqDB);
						}
						writer.beginObject();
						if(sendUser!=null) {
							response=true;
							Gson gson = new Gson();
							writer.name("user").value(gson.toJson(sendUser));System.out.println(sendUser);
						}
						else if(!users.isEmpty()) {
							response=true;
							Gson gson = new Gson();
							for (User u : users) {System.out.println(u);
								writer.name("user").value(gson.toJson(u));
							}
						}
						else {
							writer.name("null").value("null");	
						}
						writer.endObject();
					}catch(SQLException e) {
			        	error="Error select "+e;
			        }
						
				break;	
			case "selectAll-User":
				try{
					List<User> users;
					users= userManager.getUsers();
					writer.beginObject();
					if(!users.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (User user : users) {
							writer.name("user").value(gson.toJson(user));
						}
					}else {
						writer.name("null").value("null");	
					}
					writer.endObject();
				}catch(SQLException e) {
		        	error="Error select all "+e;
		        }	
					
				break;
			default:
				break;
			}
			
		if(response)
			message=request+"-succusful";
		else
			message=request+"-failed: "+error;
		
		//Creation response Json
		if(!res[0].equals("select") && !res[0].equals("selectAll")) {
			writer.beginObject();
			writer.name("response").value(message);
			writer.endObject();	
		}
		writer.flush();
		
		return message;
	}

	
	
}
