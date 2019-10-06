package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.shs.client.controller.SensorController;
import com.shs.client.view.sensor.SensorListView;

public class MapView extends JPanel {
	
	
private SensorListView sensorList;
private SensorController sensorController;

// apres 
private BuildingListView buildingList;

private JButton jbMap;


public MapView(){
	super();
//	try {
//	    sensorController = new SensorController();
//		List<Sensor> allSensors = sensorController.getAllSensors();
//		sensorList = new SensorListView(allSensors);
//	} catch (IOException e) {
//		sensorList = new SensorListView(null);
//		e.printStackTrace();
//	}
//	this.add(sensorList, BorderLayout.CENTER);
//	
	
	//  A partir d'ici c'est mounir
	//adding button where if we click open map windows (MapSHS)
	
	jbMap=new JButton("MAP");
	
	jbMap.addActionListener(new ActionListener() 
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			MapSHS m =null;
			m = new MapSHS();
			m.setVisible(true);
	
		}
	});
	
	
	this.setLayout(new BorderLayout());	
    this.add(jbMap,BorderLayout.CENTER);
	
	
//	try {
//		
//		try {
//			buildingList= new BuildingListView();
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.add(buildingList,BorderLayout.CENTER);
//		
//	} catch (UnknownHostException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		//buildingList = new BuildingListView(null);
//		e.printStackTrace();
//	}
//	
	
	
}

 
}
