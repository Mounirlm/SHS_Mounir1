package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class IndexView extends JPanel {
	public IndexView() {
		super();
		this.setLayout(new BorderLayout());
		this.setBackground(ColorsApp.getBgApp());
		JLabel lbWelcom = new JLabel("Welcome to dashboard !");
		lbWelcom.setHorizontalAlignment(JLabel.CENTER);
		lbWelcom.setFont(new Font("Arial", Font.BOLD, 45));
		lbWelcom.setBorder(new LineBorder(ColorsApp.getBgTitle(), 3));
		lbWelcom.setForeground(ColorsApp.getBgApp());
		lbWelcom.setOpaque(true);
		lbWelcom.setBackground(ColorsApp.getBgTitle());
		this.add(lbWelcom, BorderLayout.NORTH);
		JLabel center = new JLabel();
		center.setIcon(new ImageIcon("ressources\\smart-home.jpg") );
		this.add(center, BorderLayout.CENTER);
		
	}
}
