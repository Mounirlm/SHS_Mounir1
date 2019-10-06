package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.shs.commons.model.LBTitle;
import com.shs.commons.model.Wing_Room;

public class AnalyzeWingView extends JPanel implements AnalyzePanel{
	public JComboBox<String> wingComboBox;
	public JComboBox<String> monthComboBox;
	public JComboBox<Integer> yearComboBox;
	private JLabel labelAlert2;
	private JLabel labelSensor2;
	private JLabel labelResident2;
	private JLabel labelRoom2;

	public AnalyzeWingView() {
		super();
		this.setLayout(new BorderLayout());
		LBTitle lbTitle = new LBTitle("Analyze Wing");
		this.add(lbTitle, BorderLayout.NORTH);
		JPanel pCenter = new JPanel(new GridLayout(6, 1));
		
		this.add(pCenter);
		//pFloor
		JPanel pSelect = new JPanel(new GridLayout(1, 3));
		String[] monthList = {"All","January","February","March","April","May","June","July","August","September","October","November","December"};
		Integer[] yearList = {2019};
		wingComboBox = new JComboBox<String>();
		monthComboBox = new JComboBox<String>(monthList);
		yearComboBox = new JComboBox<Integer>(yearList);
		pSelect.add(wingComboBox);
		pSelect.add(monthComboBox);
		pSelect.add(yearComboBox);
		pCenter.add(pSelect);
		
		//pAlert
		JPanel pAlert = new JPanel(new GridLayout(1,2));
		JLabel labelAlert1 = new JLabel("Alerts",SwingConstants.CENTER);
		labelAlert2 = new JLabel("",SwingConstants.LEFT);
		pAlert.add(labelAlert1);
		pAlert.add(labelAlert2);
		pCenter.add(pAlert);
		
		//pSensor
		JPanel pSensor = new JPanel(new GridLayout(1,2));
		JLabel labelSensor1 = new JLabel("Sensors",SwingConstants.CENTER);
		labelSensor2 = new JLabel("",SwingConstants.LEFT);
		pSensor.add(labelSensor1);
		pSensor.add(labelSensor2);
		pCenter.add(pSensor);
				
		//pResident
		JPanel pResident = new JPanel(new GridLayout(1,2));
		JLabel labelResident1 = new JLabel("Residents",SwingConstants.CENTER);
		labelResident2 = new JLabel("",SwingConstants.LEFT);
		pResident.add(labelResident1);
		pResident.add(labelResident2);
		pCenter.add(pResident);
		
		//pRoom
		JPanel pRoom = new JPanel(new GridLayout(1,2));
		JLabel labelRoom1 = new JLabel("Room",SwingConstants.CENTER);
		labelRoom2 = new JLabel("",SwingConstants.LEFT);
		pRoom.add(labelRoom1);
		pRoom.add(labelRoom2);
		pCenter.add(pRoom);
	}
	
	@Override
	public void updateInfoGUI(Map<String, Integer> indicators) {
		labelAlert2.setText(indicators.get("nAlerts") + " alerts");
		labelSensor2.setText(indicators.get("nSensors") + " sensors");
		labelResident2.setText(indicators.get("nResidents") + " residents");
		labelRoom2.setText(indicators.get("nRooms") + " rooms");
	}

	@Override
	public void addSelectComboBoxListener(ActionListener actionComboBox) {
		wingComboBox.addActionListener(actionComboBox);
		monthComboBox.addActionListener(actionComboBox);
		yearComboBox.addActionListener(actionComboBox);
		
	}

	public void setWingComboBox(Map<Integer, String> wingRooms) {	
		for (String wingRoomName : wingRooms.values()) {
			wingComboBox.addItem(wingRoomName);
		}
		
	}
}
