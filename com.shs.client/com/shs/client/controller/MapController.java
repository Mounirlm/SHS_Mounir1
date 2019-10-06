package com.shs.client.controller;

import java.io.IOException;
import java.util.List;
import com.shs.client.model.RoomClientHandler;
import com.shs.client.view.MapView;
import com.shs.client.view.SHSView;
import com.shs.commons.model.Building;
import com.shs.commons.model.Room;
import com.shs.commons.model.SensorClientHandler;

public class MapController {
	
	private SHSView view;
	private MapView mapView;

	private RoomClientHandler servR;
	private SensorClientHandler servS;
	
	Building bg;
	
	public MapController(SHSView appWindow) 
	{
	
			view= appWindow;	
			mapView = view.getpApp().getMapView();
			
	}
	
	private List<Room>getRooms() throws IOException {
		//List<Room> rooms = servR.selecRoom();
		
		return null;
	}
}
