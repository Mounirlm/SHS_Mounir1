package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AnalyzeView extends JPanel {
	
	private JPanel pCenter;
	private JPanel pTopB;
	private JButton FloorTopBarButton;
	private JButton WingTopBarButton;
	private JButton TotalTopBarButton;
	private CardLayout centerCardLayout;
	private AnalyzeFloorView pFloorView;
	private AnalyzeWingView pWing;
	private AnalyzeTotalView pTotal;

	public AnalyzeView() {
		super();
		
		this.setLayout(new BorderLayout());	
		pCenter = new JPanel();
		
		//pTop
		JPanel pTop = new JPanel();
		pTop.setLayout(new BorderLayout());
		JLabel title = new JLabel("Analyze indicators");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		title.setBorder(new LineBorder(ColorsApp.getBgThem()));
		title.setForeground(ColorsApp.getBgThem());
		title.setOpaque(true);
		title.setBackground(ColorsApp.getBgApp());
		//pTopb
		pTopB = new JPanel();
		pTopB.setLayout(new GridLayout(1, 3));
		FloorTopBarButton = addTopBarButton("Floor");
		WingTopBarButton = addTopBarButton("Wing");
		TotalTopBarButton = addTopBarButton("Total");
		pTop.add(title, BorderLayout.NORTH);
		pTop.add(pTopB, BorderLayout.SOUTH);
		
		//pCenter
		pCenter.setBackground(ColorsApp.getBgApp());
		centerCardLayout = new CardLayout();
		pCenter.setLayout(centerCardLayout);
		
		pFloorView = new AnalyzeFloorView();
		pCenter.add("floor", pFloorView);
		pWing = new AnalyzeWingView();
		pCenter.add("wing", pWing);
		pTotal = new AnalyzeTotalView();
		pCenter.add("total", pTotal);
		//End
		this.add(pTop, BorderLayout.NORTH);
		this.add(pCenter, BorderLayout.CENTER);
		
	}

	private JButton addTopBarButton(String buttonTitle) {
		JButton button = new JButton(buttonTitle);
		button.setBackground(ColorsApp.getBgThem());
		button.setForeground(ColorsApp.getBgApp());
		button.setFont(new Font("Arial", Font.BOLD, 25));
		button.setBorder(new LineBorder(ColorsApp.getBgTitle(),2));
		pTopB.add(button);
		
		return button;
	}

	public void addTopBarButtonListener(ActionListener action) {
		FloorTopBarButton.addActionListener(action);
		WingTopBarButton.addActionListener(action);
		TotalTopBarButton.addActionListener(action);
	}
	
	public void setCard(String name) {
		centerCardLayout.show(pCenter, name);
		
	}
	
	public CardLayout getCenterCardLayout() {
		return centerCardLayout;
	}

	public AnalyzeFloorView getFloorView() {
		return pFloorView;
	}

	public AnalyzeWingView getWingView() {
		return pWing;
	}

	public AnalyzeTotalView getTotalView() {
		return pTotal;
	}

}
