package com.shs.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.shs.client.model.RoomClientHandler;
import com.shs.client.view.SHSView;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;

public class MenuController implements ActionListener{
	SHSView view;
	public MenuController(SHSView v) {
		this.view = v;
		view.getpMenu().addMenuListner(this);
		view.getpMenu().addLogoListner(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton ){
			String choix = e.getActionCommand();
			switch (choix) {
			case "index":
				view.getpApp().setCard("indexView");
				break;
				
			case "Map and Visualize Sensors":
				view.getpApp().setCard("mapView");
				break;
			
			case "Analyze indicators":
				view.getpApp().setCard("analyzeView");
				break;
				
			case "Needs Equipment":
				view.getpApp().setCard("equipmentView");
				break;
								
			
			case "Supervised Rooms":			
				view.getpApp().setCard("supRoomView");
				break;
			

			default:
				break;
			}
		}
		
	}
}
