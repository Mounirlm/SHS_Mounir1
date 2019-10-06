package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.shs.client.view.RudRoomView.SearchView;

public class SupRoomView extends JPanel{
	private CardLayout cd;
	private JButton jbCreate;
	private JButton jbRUD;
	private CreateRoomView pCreate;
	private RudRoomView pRud;
	private JPanel pCenter;
	
	public SupRoomView() {
		super();
		this.setLayout(new BorderLayout());
		JPanel pTop = new JPanel();
		pCenter = new JPanel();
		
		//pTop
		pTop.setLayout(new BorderLayout());
		JPanel pTopB = new JPanel();
		pTopB.setLayout(new GridLayout(1, 2));
		JLabel title = new JLabel("Suppervised Rooms");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 35));
		title.setBorder(new LineBorder(ColorsApp.getBgThem()));
		title.setForeground(ColorsApp.getBgThem());
		title.setOpaque(true);
		title.setBackground(ColorsApp.getBgApp());
		//pTopb
		jbCreate = new JButton("CREATE");
		jbCreate.setBackground(ColorsApp.getBgThem());
		jbCreate.setForeground(ColorsApp.getBgApp());
		jbCreate.setFont(new Font("Arial", Font.BOLD, 25));
		jbCreate.setBorder(new LineBorder(ColorsApp.getBgTitle(),2));
		
		jbRUD = new JButton("READ - UPDATE - DELETE");
		jbRUD.setBackground(ColorsApp.getBgThem());
		jbRUD.setForeground(ColorsApp.getBgApp());
		jbRUD.setFont(new Font("Arial", Font.BOLD, 25));
		jbRUD.setBorder(new LineBorder(ColorsApp.getBgTitle(), 2));
		
		pTopB.add(jbCreate);
		pTopB.add(jbRUD);
		pTop.add(title, BorderLayout.NORTH);
		pTop.add(pTopB, BorderLayout.SOUTH);
		
		//pCenter
		pCenter.setBackground(ColorsApp.getBgApp());
		cd = new CardLayout();
		pCenter.setLayout(cd);
		
		pCreate = new CreateRoomView();
		pCenter.add("create", pCreate);
		
		pRud = new RudRoomView();
		pCenter.add("rud", pRud);
		
		
		this.add(pTop, BorderLayout.NORTH);
		this.add(pCenter, BorderLayout.CENTER);
		
		cd.show(pCenter, "create");
	}
	
				



	public void setCard(String name) {
		cd.show(pCenter, name);
		
	}

	public void addRoomMenuListner(ActionListener act) {
		jbCreate.addActionListener(act);
		jbRUD.addActionListener(act);
		
	}


	public JTextField getJtf(int index) {
		return pCreate.getJtf(index);
	}



	public void setCreateTitle(String text) {
		pCreate.setTitle(text);
	}


	public RudRoomView getRudView() {
		return pRud;
	}



	public CreateRoomView getCreateView() {
		return pCreate;
	}
	
	
}
