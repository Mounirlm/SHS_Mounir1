package com.shs.commons.model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.shs.client.view.ColorsApp;

public class LBTitle extends JLabel{

	public LBTitle(String title) {
		super();
		//title
		this.setText(title);
		this.setOpaque(true);
		this.setBackground(Color.decode("#afabab"));
		this.setForeground(ColorsApp .getBgApp());
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 30));
		this.setBorder(new LineBorder(ColorsApp.getBgTitle(), 2));
	}
}
