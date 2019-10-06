package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.shs.client.controller.MenuController;
import com.shs.client.controller.RoomController;

// La vue s'occupe de l'afffichage
public class SHSView {
 
	public JFrame frame;
	
	private MenuView pMenu;
	private AppView pApp;
	private JPanel appPanel;
	private ConnectionView connectionPanel;
	private CardLayout cd;
	
	public SHSView() {
		
		//window properties
		frame = new JFrame("SHS AutonHome");
		frame.setSize(ColorsApp.getWIDTH(),ColorsApp.getHEIGHT());
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(ColorsApp.getBgApp());
		
		//main layout
		cd = new CardLayout();
		frame.setLayout(cd);
		
		//appPanel
		appPanel=new JPanel(new BorderLayout());
		//Panel Menu
		pMenu = new MenuView();
		pApp= new AppView();
		appPanel.add(pMenu, BorderLayout.WEST);
		appPanel.add(pApp, BorderLayout.CENTER);
		
		
		//connectionPanel
		connectionPanel= new ConnectionView();
		
		//Window
		frame.getContentPane().add("appPanel", appPanel);
		frame.getContentPane().add("connectionPanel", connectionPanel);
		cd.show(frame.getContentPane(), "connectionPanel");
		show();
	}
	
	private void show() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	
	public MenuView getpMenu() {
		return pMenu;
	}

	public AppView getpApp() {
		return pApp;
	}


	public ConnectionView getConnectionPanel() {
		return connectionPanel;
	}

	public CardLayout getCd() {
		return cd;
	}

	public JFrame getFrame() {
		return frame;
	}

	


	
}