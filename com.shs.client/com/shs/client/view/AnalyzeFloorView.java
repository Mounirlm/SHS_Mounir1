package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.shs.commons.model.LBTitle;

public class AnalyzeFloorView extends JPanel implements AnalyzePanel{
	
	public JComboBox<Integer> floorComboBox;
	public JComboBox<String> monthComboBox;
	public JComboBox<Integer> yearComboBox;
	private JLabel labelAlert2;
	private JLabel labelSensor2;
	private JLabel labelResident2;
	private JLabel labelRoom2;

	public AnalyzeFloorView() {
		super();
		this.setLayout(new BorderLayout());
		LBTitle lbTitle = new LBTitle("Analyze Floor");
		this.add(lbTitle, BorderLayout.NORTH);
		JPanel pCenter = new JPanel(new GridLayout(6, 1));
		this.add(pCenter);
		
		//pFloor
		JPanel pSelect = new JPanel(new GridLayout(1, 3));
		Integer[] floorList = {0,1};
		String[] monthList = {"All","January","February","March","April","May","June","July","August","September","October","November","December"};
		Integer[] yearList = {2019};
		floorComboBox = new JComboBox<Integer>(floorList);
		monthComboBox = new JComboBox<String>(monthList);
		yearComboBox = new JComboBox<Integer>(yearList);
		pSelect.add(floorComboBox);
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
	
	public void addSelectComboBoxListener(ActionListener actionComboBox) {
		floorComboBox.addActionListener(actionComboBox);
		monthComboBox.addActionListener(actionComboBox);
		yearComboBox.addActionListener(actionComboBox);
	}

	@Override
	public void updateInfoGUI(Map<String, Integer> indicators) {
		labelAlert2.setText(indicators.get("nAlerts") + " alerts");
		labelSensor2.setText(indicators.get("nSensors") + " sensors");
		labelResident2.setText(indicators.get("nResidents") + " residents");
		labelRoom2.setText(indicators.get("nRooms") + " rooms");	
	}
}
