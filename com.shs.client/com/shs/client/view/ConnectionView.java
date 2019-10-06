package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;

public class ConnectionView extends JPanel{
	private FormView formView;
	private JPanel pCon;
	private JLabel lImage;
	private JLabel message;
	public ConnectionView() {
		super();
		this.setLayout(new GridBagLayout());
	
		pCon=new JPanel(new BorderLayout());
		pCon.setBackground(ColorsApp.getBgApp());
		//Image
		lImage = new JLabel(new ImageIcon("ressources\\shs-logo.png"));
		
		//FormConstruction
		Map<String, String> cols = new LinkedHashMap<>();
		cols.put("Email","");cols.put("Password","");
		
		ArrayList<String> buttons = new ArrayList<>();
		buttons.add("LOGIN");
		
		ArrayList<Type_Room> list1 = new ArrayList<>();
		ArrayList<Wing_Room> list2 = new ArrayList<>();
		
		formView = new FormView("Connection", cols, buttons, new ArrayList<String>(),list1, list2, "v", true,true, 20,20,20);
		//formView.setBackground(cdApp.getBgApp());
		//add in view
		pCon.add(lImage, BorderLayout.NORTH);
		pCon.add(formView, BorderLayout.CENTER);
		pCon.setPreferredSize(new Dimension(ColorsApp.getWIDTH()-1200, ColorsApp.getHEIGHT()-500));
		pCon.setBorder(new LineBorder(ColorsApp.getBgTitle(), 2));
		
		//message
		message=new JLabel("Enter your email and password");
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setForeground(ColorsApp.getBgTitle());
		pCon.add(message, BorderLayout.SOUTH);
		this.add(pCon);
		
	}
	public FormView getFormView() {
		return formView;
	}
	public JLabel getMessage() {
		return message;
	}
	
}
