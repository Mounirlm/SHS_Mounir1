
package com.shs.client.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;

import com.shs.client.controller.BuildingController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;


public class BuildingListView extends JPanel{
	
	private BuildingController buildingController;
	

	private List<Building> buildings;
	private JList itemsListBuilding;
	private List<Floor> floors;
	private JList itemsListFloor;
	private List<Room> rooms;
	private JList itemsListRoom;
	private List<Sensor> sensors;
	private JList itemsListSensor;
	
	Floor floor ;
	
	JLabel b;
	

	public BuildingListView() throws IOException, ClassNotFoundException, SQLException {
		super();
		//buildingController=new BuildingController();
		
		this.buildings = buildingController.getBuildingList();
		this.floors= buildingController.getBuildingFloorList();
		
		
		this.rooms=buildingController.getRoomListInFloor(1);
		this.sensors=buildingController.getSensorWithPosition();
		
		
		System.out.println(buildingController.getBuilding().toString());

		
	itemsListBuilding = new JList<Object>(this.getBuildingArray(buildings));
    JScrollPane scrollPane = new JScrollPane(itemsListBuilding,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(itemsListBuilding);
    

	itemsListFloor = new JList<Object>(this.getFloorArray(floors));
    JScrollPane scrollPane2 = new JScrollPane(itemsListFloor,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(itemsListFloor);
    
	
	
	itemsListRoom = new JList<Object>(this.getRoomArray(rooms));
    JScrollPane scrollPane3 = new JScrollPane(itemsListRoom,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(itemsListRoom);
    
	
	
	itemsListSensor = new JList<Object>(this.getSensorArray(sensors));
    JScrollPane scrollPane4 = new JScrollPane(itemsListSensor,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(itemsListSensor);
    
	}
	
	public Building[] getBuildingArray(List<Building> buildings)
	{
		Building[] bArray = new Building[buildings.size()];
		for(int i =0 ; i<buildings.size();i++)
		{
			bArray[i] = buildings.get(i);
		}
		return bArray;
	}

	public Floor[] getFloorArray(List<Floor> floors)
	{
		Floor[] fArray=new Floor[floors.size()];
		for (int i=0; i<floors.size();i++)
		{
			fArray[i]=floors.get(i);
		}
		
		return fArray;
	}
	
	public Room[] getRoomArray(List<Room> rooms)
	{
		Room[] rArray=new Room[rooms.size()];
		for (int i=0; i<rooms.size();i++)
		{
			rArray[i]=rooms.get(i);
		}
		
		return rArray;
	}
	
	public Sensor[] getSensorArray(List<Sensor> sensors2) {
		Sensor[] sArray=new Sensor[sensors2.size()];
		for (int i=0; i<sensors2.size();i++)
		{
			sArray[i]=sensors2.get(i);
		}
		
		return sArray;
		
	}
}
