package com.shs.client.view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AppView extends JPanel{
	private CardLayout cdViewApp;
	private ArrayList<JPanel> appView;
	private SupRoomView supRoomView;
	private IndexView indexView;
	private MapView mapView;
	private AnalyzeView analyzeView;
	private EquipmentNeed equipmentNeed;
	
	
	public AppView()  {
		super();
		this.setBackground(ColorsApp.getBgApp());
		
		cdViewApp = new CardLayout();
		this.setLayout(cdViewApp);
		//new views
		appView = new ArrayList<>(5);
		indexView = new IndexView();
		mapView = new MapView();
		analyzeView = new AnalyzeView();
		equipmentNeed = new EquipmentNeed();
		supRoomView = new SupRoomView();
	
		
		//add views in list views
		appView.add(indexView);
		appView.add(mapView);
		appView.add(analyzeView);
		appView.add(equipmentNeed);
		appView.add(supRoomView);
			
		
		//fill cardLayout
		this.add("indexView", appView.get(0));
			
		//add other vieew for R3
		this.add("mapView", appView.get(1));
		this.add("analyzeView", appView.get(2));
		this.add("equipmentView", appView.get(3));
		this.add("supRoomView", appView.get(4));
		
		
		cdViewApp.show(this, "indexView");			
		
	}
	

	public void setCard(String name) {
		cdViewApp.show(this, name);
		
	}


	public SupRoomView getSupRoomView() {
		return supRoomView;
	}


	public AnalyzeView getAnalyzeView() {
		return analyzeView;
	}

	public MapView getMapView() {
		return mapView;
	}

}
