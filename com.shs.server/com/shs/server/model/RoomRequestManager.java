package com.shs.server.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.User;

public class RoomRequestManager {
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private String  request;
	private Room room;
	RoomManager roomManager;
	
	public RoomRequestManager(Connection connDB, JsonReader reader, JsonWriter writer, Room object, String request) {
		super();
		this.connDB = connDB;
		this.reader = reader;
		this.writer = writer;
		this.room = object;
		this.request = request;
		this.roomManager = new RoomManager(connDB);
	}

	
	public String requestManager() throws SQLException, IOException {		
		boolean response = false;
		String message=null, error="no row(s)";
		String[] res=request.split("-");
		switch (request) {
			case "insert-Room":
				try{
					response=RoomManager.create(room);
				}
			    catch(SQLException e) {
			    	error="Error insertion "+e;
			    }
				break;
			case "update-Room":
				try{
					response=RoomManager.update((room));
				}
			    catch(SQLException e) {
			    	error="Error updating "+e;
			    }
				break;
			case "delete-Room":
				try{
					response=RoomManager.delete((room));
				}
			    catch(SQLException e) {
			    	error="Error delete "+e;
			    }
				break;
			case "deleteAll-Room":
				try{
					response=RoomManager.deleteAll();
				}
			    catch(SQLException e) {
			    	error="Error delete all "+e;
			    }				
				break;	
			case "select-Room":
				try{
					Room sendRoom=null;
					String reqDB="";
					List<Room> rooms=new ArrayList<>();
					if(room.getId()!=null) {
						sendRoom= RoomManager.getRoom(room.getId());
					}else {
						if(room.getFloor()!=null) {
							reqDB+="floor = "+room.getFloor();
							if(room.getRoom_number()!=null)
								reqDB+="and room_number = "+room.getRoom_number();
							if(room.getM2()!=null)
								reqDB+="and m2 = "+room.getM2();
							if(!room.getType_room().getName().equals("null"))
								reqDB+="and fk_type_room = "+room.getType_room().getId();
							if(!room.getWing_room().getName().equals("null"))
								reqDB+="and fk_wing_room = "+room.getWing_room().getId();
							if(room.getNb_doors()!= null)
								reqDB+="and nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(room.getRoom_number()!=null) {
							reqDB+="room_number = "+room.getRoom_number();
							if(room.getM2()!=null)
								reqDB+="and m2 = "+room.getM2();
							if(!room.getType_room().getName().equals("null"))
								reqDB+="and fk_type_room = "+room.getType_room().getId();
							if(!room.getWing_room().getName().equals("null"))
								reqDB+="and fk_wing_room = "+room.getWing_room().getId();
							if(room.getNb_doors()!= null)
								reqDB+="and nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(room.getM2()!=null) {
							reqDB+="m2 = "+room.getM2();
							if(!room.getType_room().getName().equals("null"))
								reqDB+="and fk_type_room = "+room.getType_room().getId();
							if(!room.getWing_room().getName().equals("null"))
								reqDB+="and fk_wing_room = "+room.getWing_room().getId();
							if(room.getNb_doors()!= null)
								reqDB+="and nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(!room.getType_room().getName().equals("null")) {
								reqDB+="fk_type_room = "+room.getType_room().getId();
							if(!room.getWing_room().getName().equals("null"))
								reqDB+="and fk_wing_room = "+room.getWing_room().getId();
							if(room.getNb_doors()!= null)
								reqDB+="and nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(!room.getWing_room().getName().equals("null")) {
							reqDB+="fk_wing_room = "+room.getWing_room().getId();
							if(room.getNb_doors()!= null)
								reqDB+="and nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(room.getNb_doors()!= null) {
								reqDB+="nb_doors = "+room.getNb_doors();
							if(room.getNb_windows()!= null)
								reqDB+="and nb_windows = "+room.getNb_windows();
						}
						else if(room.getNb_windows()!= null) {
								reqDB+="nb_windows = "+room.getNb_windows();
						}
						System.out.println(room.getWing_room());
						reqDB+=";";
						rooms=RoomManager.getRoomsBy(reqDB);
					}
					writer.beginObject();
					if(sendRoom!=null) {
						response=true;
						Gson gson = new Gson();
						writer.name("room").value(gson.toJson(sendRoom));System.out.println(sendRoom);
					}
					else if(!rooms.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Room r : rooms) {System.out.println(r);
							writer.name("room").value(gson.toJson(r));
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
			case "selectAll-Room":
				try{
					List<Room> rooms;
					rooms= RoomManager.getRooms();
					writer.beginObject();
					if(!rooms.isEmpty()) {
						response=true;
						Gson gson = new Gson();
						for (Room room : rooms) {
							writer.name("room").value(gson.toJson(room));
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
				if(request.startsWith("selectPosition-Room")) {
					try{
						List<Room> rooms;
						
						
							
						rooms=RoomManager.getRoomsWithPostion(Integer.valueOf(res[2]));
						writer.beginObject();
						if(!rooms.isEmpty()) {
							response=true;
							Gson gson = new Gson();
							for (Room r : rooms) {
								writer.name("room").value(gson.toJson(r));
							}
						}else {
							writer.name("null").value("null");	
						}
						writer.endObject();
					}catch(SQLException e) {
			        	error="Error select Room position "+e;
			        }	
				}
				
				
				
				else if(request.startsWith("countByFloor-Room")) {
					response=true;
					int nRooms = RoomManager.countByFloorMonthYear(Integer.valueOf(res[2]));
					writer.beginObject();
					writer.name("nRooms").value(nRooms);
					writer.endObject();
				}else if(request.startsWith("countByWing-Room")) {
					response=true;
					int nRooms = RoomManager.countByWing(Integer.valueOf(res[2]));
					writer.beginObject();
					writer.name("nRooms").value(nRooms);
					writer.endObject();
				}else if(request.startsWith("countAll-Room")) {
					response=true;
					int nRooms = RoomManager.count();
					writer.beginObject();
					writer.name("nRooms").value(nRooms);
					writer.endObject();
				}
				break;
			}
			
		if(response)
			message=request+"-succusful";
		else
			message=request+"-failed: "+error;
		
		//Creation response Json
		if(!res[0].equals("select") && !res[0].equals("selectAll") && !res[0].startsWith("count") &&!res[0].equals("selectPosition")) {
			writer.beginObject();
			writer.name("response").value(message);
			writer.endObject();	
		}
		writer.flush();
		
		return message;
	}

	
	
}
