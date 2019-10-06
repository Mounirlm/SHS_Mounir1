package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.shs.commons.model.LBTitle;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;

public class CreateRoomView extends JPanel {
	private FormView formView;
	private LBTitle lbTitle;
	
	public CreateRoomView() {
		super();
		this.setLayout(new BorderLayout());
		lbTitle = new LBTitle("New Secured Room");
		this.add(lbTitle, BorderLayout.NORTH);
		
	}
	
	public void createCols(List<Type_Room> listT, List<Wing_Room> listW) {
		Map<String, String> cols = new LinkedHashMap<>();
		cols.put("FLOOR","");cols.put("ROOM NUMBER","");cols.put("M²","");
		cols.put("DOORS","");cols.put("WINDOWS","");		
		ArrayList<String> buttons = new ArrayList<>();
		buttons.add("INSERT");
		
		formView = new FormView("New Secured Room", cols, buttons, new ArrayList<String>(),listT,listW, "v", false,false, 20,20,20);
		formView.setPreferredSize(new Dimension(ColorsApp.getWIDTH()-900, ColorsApp.getHEIGHT()-300));
		this.add(formView, BorderLayout.CENTER);
	}
	public JTextField getJtf(int index) {
		return formView.getJtf(index);
	}
	
	public void setTitle(String text) {
		lbTitle.setText(text);
	}
	public void addJBInsertListner(ActionListener act) {
		formView.addJBListner(act);
	}
	public FormView getFormView() {
		return formView;
	}
}
