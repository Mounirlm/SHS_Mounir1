package com.shs.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shs.client.model.RoomClientHandler;
import com.shs.client.view.AnalyzeView;
import com.shs.client.view.MapView;
import com.shs.client.view.SHSView;
import com.shs.commons.model.Building;
import com.shs.commons.model.BuildingClientHandler;
import com.shs.commons.model.Floor;
import com.shs.commons.model.FloorClientHandler;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Sensor;

public class BuildingController {
	
	
	private MapView mapView;
	
	private static BuildingClientHandler buildingService;
	private static FloorClientHandler    floorService;
	private static RoomClientHandler     roomService;
	private static SensorController     sensorService;
	private static TypeSensorController  typeSensorService;
	
	private  Building building;
	private  Floor    floor;
	private  Sensor    sensor;
	private  Room      room;
		
	
	private SHSView view;
    

	public BuildingController() throws  IOException, ClassNotFoundException, SQLException {
		this.view=view;
		
		//mapView = view.getpApp().getMapView();
		
		building = new Building();
		Room room= new Room();
		floor=new Floor();
		
		
		
		buildingService = new BuildingClientHandler();
		floorService = new FloorClientHandler();
		roomService =new RoomClientHandler();
		sensorService= new SensorController();
		typeSensorService =new TypeSensorController();
		
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
	
	public void BuildingFloorList() throws IOException {
		
		building.setFloor(getBuildingFloorList());
	}
		
		
	public static Integer getFloorId(Floor fl) throws IOException{
			
		    Integer id=null;
			
			
			   for(Floor f: getBuildingFloorList())
			   {
				    if (f==fl)  	id=f.getId();	   
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
			 room.setFk_floor(r.getFk_floor());
			 room.setHeight(r.getHeight());
			 room.setWidth(r.getWidth());
			 room.setX(r.getX());
			 room.setY(r.getY());
			 
			 rooms.add(room);
		  
		}
		
		return rooms;
		     
	}
	
	// Method which returns the sensor associated to room
	
	public List <Sensor> getSensorWithPosition () throws IOException 
	{
       
		  return sensorService.getAllSensorsWithPosition();
				
	}
	
	public List<Type_Sensor> getSensorTypeList () throws IOException, SQLException {
		
		for (Sensor s :  this.getSensorWithPosition()){
		 
			for(Type_Sensor ts : typeSensorService.getSensorType()) {
			
		       if( s.getFk_type_sensor()==ts) s.setFk_type_sensor(ts);
			}
		}
		return typeSensorService.getSensorType();
		
		  
	}
	
	public static Type_Sensor getSensorType(String name) throws IOException{
		 
		 		 
		 return typeSensorService.getSensorType(name);
		 
	 }
	
	
	
	public static void  setSensorInROOM(Floor f) throws IOException {
		
		int cpt=0;
		ArrayList<Room> rooms  =roomService.selectRoomsWithPosition(f.getId());
		
		while (cpt<rooms.size())
		{			
			 
			System.out.println("toto");
			
			rooms.get(cpt).setSensors(sensorService.getSensorsInRoom(rooms.get(cpt).getId()));
			System.out.println(rooms.get(cpt));
			System.out.println( "toti");
			cpt++;
		}
		
	    f.setRoom(rooms);
		
		
	}
	
	
	public static List <Sensor> getSensorsNotInstalled () throws IOException 
	{
//		   Sensor sensor = new Sensor();
//		   ArrayList<Sensor> sensors = new ArrayList<Sensor>();
//		   for (Sensor s :  sensorService.getSensorsNotInstalled()) 
//		   {
//			   sensor.setId(s.getId());
//			   sensor.setInstalled(s.getInstalled());
//			   sensor.setFk_type_sensor(s.getFk_type_sensor());
//			   sensor.setX(s.getX());
//			   sensor.setY(s.getY());
//			   
//			   if (sensor.getInstalled()==false && sensor.getX()==null && sensor.getY()==null)  sensors.add(sensor);
//		   }
			
		  return sensorService.getSensorsNotInstalled();
	}
	
	
	 
	 
		
	 
}
