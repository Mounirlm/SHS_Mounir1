package com.shs.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.Month;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.shs.client.model.RoomClientHandler;
import com.shs.client.view.AnalyzeFloorView;
import com.shs.client.view.AnalyzePanel;
import com.shs.client.view.AnalyzeTotalView;
import com.shs.client.view.AnalyzeView;
import com.shs.client.view.AnalyzeWingView;
import com.shs.client.view.SHSView;
import com.shs.commons.model.AlertClientHandler;
import com.shs.commons.model.ResidentClientHandler;
import com.shs.commons.model.SensorClientHandler;
import com.shs.commons.model.Wing_Room;

public class AnalyzeController{
	
	private SHSView view;
	private AnalyzeView analyzeView;
	private AnalyzeFloorView analyzeFloorView;
	private int floor;
	private String month;
	private int year;
	private AlertClientHandler alertH;
	private SensorClientHandler sensorH;
	private ResidentClientHandler residentH;
	private RoomClientHandler roomH;
	protected AnalyzePanel currentView;
	private AnalyzeWingView analyzeWingView;
	private String wing;
	private Map<Integer, String> wingRooms;
	private AnalyzeTotalView analyzeTotalView;
	
	public AnalyzeController(SHSView v) throws UnknownHostException, IOException {
		this.view = v;
		
		alertH = new AlertClientHandler();
		sensorH = new SensorClientHandler();
		residentH = new ResidentClientHandler();
		roomH = new RoomClientHandler();
		analyzeView = view.getpApp().getAnalyzeView();
		analyzeView.addTopBarButtonListener(actionTopBarButton);
		
		analyzeFloorView = analyzeView.getFloorView();
		analyzeFloorView.addSelectComboBoxListener(actionComboBox);
		
		analyzeWingView = analyzeView.getWingView();
		wingRooms = getWingRooms();
		analyzeWingView.setWingComboBox(wingRooms);
		analyzeWingView.addSelectComboBoxListener(actionComboBox);
		
		analyzeTotalView = analyzeView.getTotalView();
		analyzeTotalView.addSelectComboBoxListener(actionComboBox);
		currentView = analyzeFloorView;
		updateInfoFromComboBox();
		Map<String, Integer> indicators = searchFloorIndicators(floor,month,year);
		currentView.updateInfoGUI(indicators);
	}

	private Map<Integer, String> getWingRooms() throws IOException {
		List<Wing_Room> allWingRoom = roomH.selectAllWingRoom();
		Map<Integer,String> wingRooms = new HashMap<Integer,String>();
		for (Wing_Room wing_Room : allWingRoom) {
			wingRooms.put(wing_Room.getId(),wing_Room.getName());
		}
		return wingRooms;
	}
	
	
	ActionListener actionTopBarButton = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton ){
				String choice = e.getActionCommand();
				switch (choice) {
				case "Floor":
					analyzeView.setCard("floor");
					currentView = analyzeView.getFloorView();
					break;
					
				case "Wing":
					analyzeView.setCard("wing");
					currentView = analyzeView.getWingView();
					break;
				case "Total":
					analyzeView.setCard("total");
					currentView = analyzeView.getTotalView();
				default:
					break;
				}
			}
			
		}
	};
	
	ActionListener actionComboBox = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			updateInfoFromComboBox();
			Map<String,Integer> indicators = null;
			if (currentView.equals(analyzeFloorView))
				indicators=searchFloorIndicators(floor,month,year);
			else if(currentView.equals(analyzeWingView))
				indicators =searchWingIndicators(wing,month,year);
			else if(currentView.equals(analyzeTotalView))
				indicators =searchTotalIndicators(month,year);
	        
	        currentView.updateInfoGUI(indicators);
		}
	};
	

	protected Map<String, Integer> searchFloorIndicators(int floor2, String month2, int year2) {
		Map<String, Integer> indicators = new HashMap<String,Integer>();
		int nAlerts=0;
		int nSensors=0;
		int nResidents=0;
		int nRooms=0;
		try {
			int monthValue = (month2.equals("All")) ? 0 : Month.valueOf(month2.toUpperCase()).getValue();
			nAlerts = alertH.countAlertByFloor(floor2,monthValue,year2);
			nSensors = sensorH.countSensorsByFloor(floor2);
			nResidents = residentH.countResidentByFloor(floor2);
			nRooms= roomH.countRoomByFloor(floor2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		indicators.put("nAlerts", nAlerts);
		indicators.put("nSensors", nSensors);
		indicators.put("nResidents", nResidents);
		indicators.put("nRooms", nRooms);
		return indicators;
	}

	protected Map<String, Integer> searchTotalIndicators(String month2, int year2) {
		Map<String, Integer> indicators = new HashMap<String,Integer>();
		int nAlerts=0;
		int nSensors=0;
		int nResidents=0;
		int nRooms=0;
		try {
			int monthValue = (month2.equals("All")) ? 0 : Month.valueOf(month2.toUpperCase()).getValue();
			nAlerts = alertH.countAlert(monthValue,year2);
			nSensors = sensorH.countSensors();
			nResidents = residentH.countResident();
			nRooms= roomH.countRoom();
		} catch (IOException e) {
			e.printStackTrace();
		}
		indicators.put("nAlerts", nAlerts);
		indicators.put("nSensors", nSensors);
		indicators.put("nResidents", nResidents);
		indicators.put("nRooms", nRooms);
		return indicators;
	}

	protected Map<String, Integer> searchWingIndicators(String wing2, String month2, int year2) {
		Map<String, Integer> indicators = new HashMap<String,Integer>();
		int nAlerts=0;
		int nSensors=0;
		int nResidents=0;
		int nRooms=0;
		try {
			int wingInt = getWingId(wing2);
			int monthValue = (month2.equals("All")) ? 0 : Month.valueOf(month2.toUpperCase()).getValue();
			nAlerts = alertH.countAlertByWing(wingInt,monthValue,year2);
			nSensors = sensorH.countSensorsByWing(wingInt);
			nResidents = residentH.countResidentByWing(wingInt);
			nRooms= roomH.countRoomByWing(wingInt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		indicators.put("nAlerts", nAlerts);
		indicators.put("nSensors", nSensors);
		indicators.put("nResidents", nResidents);
		indicators.put("nRooms", nRooms);
		return indicators;
	}

	private int getWingId(String wing2) {
		for (Map.Entry<Integer, String>  wng : wingRooms.entrySet()) {
			if (wng.getValue().equals(wing2))
				return wng.getKey();
		}
		return 0;
	}

	protected void updateInfoFromComboBox() {
		if(currentView.equals(analyzeFloorView)) {
			month = (String) analyzeFloorView.monthComboBox.getSelectedItem();
			year = (int) analyzeFloorView.yearComboBox.getSelectedItem();
			floor = (int) analyzeFloorView.floorComboBox.getSelectedItem();
		}
		else if(currentView.equals(analyzeWingView)) {
			month = (String) analyzeWingView.monthComboBox.getSelectedItem();	
			year = (int) analyzeWingView.yearComboBox.getSelectedItem();
			wing = (String) analyzeWingView.wingComboBox.getSelectedItem();
		}
		else if(currentView.equals(analyzeTotalView)) {
			month = (String) analyzeTotalView.monthComboBox.getSelectedItem();	
			year = (int) analyzeTotalView.yearComboBox.getSelectedItem();
		} 

	}
	

}
