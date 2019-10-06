package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.shs.client.controller.BuildingController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;

public class MapSHS extends JFrame implements IUpdatable{
	
	private JPanel panN;
	private JPanel panW;	
	private JPanel panE;
	private JPanel panS;
	
	private JList itemsListFloor;
	private JList itemsListSensor;
	private JList itemsListStock;
	
    private JButton addSensorToStock;
    private JLabel StockList;
	
	JLabel mapTitle;
	MapPanelView plan;
	Building building;
	BuildingController buildingController;
	Floor floor;
	
	
	
	public MapSHS() 
	{
		
		
		super("SHS AutonHome");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	    this.setSize(1200, 650);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		try {
			buildingController=new BuildingController();
		
			building=buildingController.getBuilding();
			//
			
		} catch (IOException | ClassNotFoundException | SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		plan = new MapPanelView(building);
		
		
		plan.addUpdatableListener(this);// pattern Observer Observable
		// plan ne doit pas avoir de lien avec ihm donc ihm s'inscrit comme listener du plan
		
		panN=new JPanel();
		panW=new JPanel();
		panE=new JPanel();
		panS=new JPanel();
		
		
		panN.setBackground(Color.BLUE);
		mapTitle = new JLabel("SHS_MAP");
		panN.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panN.add(mapTitle);
      
	   
		panW.setLayout(new GridLayout(2,1));
		
		panE.setLayout(new GridLayout(7,1));
		
		StockList=new JLabel("StockList",SwingConstants.CENTER);
		StockList.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		
		addSensorToStock= new JButton("Add_Sensor_To_Stock");
		
		addSensorToStock.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					FormStockView fs = new FormStockView(building);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				update();
			}
		});
		
        
        itemsListFloor = new JList<Object>(building.getFloor().toArray());
        JScrollPane scrollPane = new JScrollPane(itemsListFloor,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
        
        itemsListFloor.addListSelectionListener(new ListSelectionListener() 
        {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				
				Floor f = (Floor)itemsListFloor.getSelectedValue();
				//if()
				System.out.println(f.getId());
				
				try {
					
					
					f.setRoom((buildingController.getRoomListInFloor(f.getId())));
					buildingController.getSensorInROOM(f);
					f.getSensors();
					
					
					itemsListSensor.setListData(f.getSensors().toArray());
					
					
					
					plan.setCurrent_floor(f);
						
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        
				

						
				
				
			}
		});
        
        panW.add(scrollPane);
        
        itemsListSensor = new JList<Object>();
        JScrollPane scrollPane2 = new JScrollPane(itemsListSensor,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        panW.add(scrollPane2);
        
        
        
        itemsListStock = new JList<Sensor>(building.getStock().getSensorArray());
        JScrollPane scrollPane3 = new JScrollPane(itemsListStock,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        itemsListStock.setDragEnabled(true);
        itemsListStock.setTransferHandler(new MyTransferHandler());
        
        
        
        panE.add(StockList);
        panE.add(scrollPane3);
        panE.add(addSensorToStock);
       
        
        
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panN,BorderLayout.PAGE_START);
		this.getContentPane().add(panW,BorderLayout.LINE_START);
		this.getContentPane().add(plan,BorderLayout.CENTER);
		this.getContentPane().add(panE,BorderLayout.LINE_END);
		this.getContentPane().add(panS,BorderLayout.PAGE_END);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//		        try {
//		        	// TODO
//					//Writer.writeJSON(".//save.json",IHM.this.building);
//				
//				} catch (IOException e) {
//					
//					e.printStackTrace();
//				}
		    }
		});
		
	}

	
	
	
	public Date getDate() {
		DateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d=new Date();
		
		return d;
	}


	
	public void update() 
	{
		itemsListStock.setListData(building.getStock().getSensorArray());
		
		Floor f = (Floor)itemsListFloor.getSelectedValue();
		itemsListSensor.setListData(f.getSensors().toArray());
		
		for(Sensor s : f.getSensors())
		{
			s.setInstalled(true);
			s.setDate_setup(this.getDate());
			//System.out.println (s.getDate_setup());	
		}
		
		
	}
}
