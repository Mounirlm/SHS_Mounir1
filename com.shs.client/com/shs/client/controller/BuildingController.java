package com.shs.client.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shs.client.model.RoomClientHandler;
import com.shs.client.view.FormStockView;
import com.shs.client.view.IUpdatable;
import com.shs.client.view.MapPanelView;
import com.shs.client.view.MapView;
import com.shs.client.view.SHSView;
import com.shs.commons.model.Building;
import com.shs.commons.model.BuildingClientHandler;
import com.shs.commons.model.Floor;
import com.shs.commons.model.FloorClientHandler;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.SensorClientHandler;
import com.shs.commons.model.Type_Sensor;

public class BuildingController {
	
	
	private MapView mapView;
	
	private static BuildingClientHandler buildingService;
	private static FloorClientHandler    floorService;
	private static RoomClientHandler     roomService;
	private static SensorController     sensorService;
	private static TypeSensorController  typeSensorService;
	
	private static SensorClientHandler sc;
	
		
	private static  Building building;
	private  Floor    floor;
	private  Sensor    sensor;
	private  Room      room;
		
	private static MapPanelView mp;
	private SHSView view;
	
	FormStockView fs= null;
	
	
    

	public BuildingController() throws  Exception {
		this.view=view;
		
		building = new Building();
		Room room= new Room();
		floor=new Floor();
				
		mp= new MapPanelView(building);
		
		buildingService = new BuildingClientHandler();
		floorService = new FloorClientHandler();
		roomService =new RoomClientHandler();
		sensorService= new SensorController();
		typeSensorService =new TypeSensorController();
		sc=new SensorClientHandler();
		
		//set building with floorList
		building.setFloor(floorService.getFloorInBuilding());
	
       
		
	}
	// Test if they are several building
	public static List<Building> getBuildingList() throws IOException {
		
		
		return buildingService.getBuilding("select-Building");
	}
	
	// At this case, we have one building with id=1 and name ="Residence-1"
	
	public Building getBuilding() throws IOException{
		   
	
		   for(Building b : buildingService.getBuilding("select-Building"))
		   {
			   if (b.getName()=="Residence-1")   this.building=b;
		   }
		  return building;
	}
	
	// Return list of floor belonging to a building
	
	public static List<Floor> getBuildingFloorList() throws IOException
	{	
			    
		   return  floorService.getFloorInBuilding();
	}
	
	
	public void BuildingFloorList() throws IOException 
	{
		
		building.setFloor(getBuildingFloorList());
	}
		
		
	public static Integer getFloorId(Floor fl) throws IOException{
			
		    Integer id=null;
			
			
		    for(Floor f: getBuildingFloorList())
			 {
				   if (f==fl)  id=f.getId();	   
			 }
			   
			  return id;
		}
	
	
	public static Floor getFloor() throws IOException{
		   
		Floor floor= new Floor();
		
		   for(Floor f: getBuildingFloorList())
		   {
			    floor.setId(f.getId());
			    floor.setName(f.getName());
			    floor.setImagePath(f.getImagePath());
			    floor.setFk_building(f.getFk_building());
			    floor.setRoom(getRoomListInFloor(floor.getId()));
			 
			    
		   }
		   
		  return floor;
	}
	
	
	
	
	//At this case we have one floor, so this method return only rooms belonging to this floor (id=1).
	
	public static List<Room>getRoomListInFloor(int idFloor) throws IOException 
	{
		
		Room room = new Room();
		ArrayList <Room> rooms=new ArrayList<Room>();
	
		for (Room r : roomService.selectRoomsWithPosition(idFloor))
		{

			 room=r;
			 rooms.add(room);
		  
		}
		
		return rooms;
		     
	}
	
	public static Room getRoom (int idRoom, int idFloor) throws IOException {
		
	    Room rSearch=null;
		for (Room r : roomService.selectRoomsWithPosition(idFloor))
		{
			if (idRoom== r.getId())
			{
				  rSearch=r; break;
			}
		}
		
	  return  rSearch;
	}
	// Method which returns the sensor associated to room
	
	public static List <Sensor> getSensorWithPosition () throws IOException 
	{
       
		  return sensorService.getAllSensorsWithPosition();
				
	}
	
	public static List<Type_Sensor> getSensorTypeList () throws IOException, SQLException {
		
		for (Sensor s :  getSensorWithPosition()){
		 
			for(Type_Sensor ts : typeSensorService.getSensorType()) {
			
		       if( s.getFk_type_sensor()==ts) s.setFk_type_sensor(ts);
			}
		}
		return typeSensorService.getSensorType();
		
		  
	}
	
	public static Type_Sensor getSensorTypeByName(String name) throws IOException, SQLException{
		 
		
		 for (Type_Sensor ts : typeSensorService.getSensorType()) {
			 
			 if(name.equals(ts.getName())) return ts;
				 
		 }
		 return null;
		 
	 }
	
	
	
	public static void  setSensorInROOM(Floor f) throws IOException {
		
		int cpt=0;
		ArrayList<Room> rooms  =roomService.selectRoomsWithPosition(f.getId());
		
		while (cpt<rooms.size())
		{			
			rooms.get(cpt).setSensors(sensorService.getSensorsInRoom(rooms.get(cpt).getId()));
			System.out.println(rooms.get(cpt));
			cpt++;
		}
		
	    f.setRoom(rooms);
	}
	
	
	public static List <Sensor> getSensorsNotInstalled () throws IOException 
	{
	
		  return sensorService.getSensorsNotInstalled();
	}
	
	public static String update(Sensor s) throws IOException, SQLException {
			
		return  sensorService.update(s);
		
	}
	
	public static String create(Sensor s) throws IOException, SQLException {
		
		return  sensorService.create(s);
		
	}
	
	
	

	public static void setBuilding(Building building) {
		BuildingController.building = building;
	}
	
		

	
	
	


}
	
	
	
	 
		
	 

