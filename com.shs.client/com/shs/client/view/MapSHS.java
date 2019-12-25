package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.shs.client.controller.BuildingController;
import com.shs.client.model.RoomClientHandler;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Stock;

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
    private JLabel mapTitle;
    
    private JLabel jLSensorId;
    private JTextField jtxSensorId;
    private JPanel pGenerate;
    private JButton jbRemoveToStockSensor;
    private JButton jbDefaultSensor;
    
    
	MapPanelView plan;
	Building building;
	BuildingController buildingController;
	Floor floor;
	Stock stock;
	
		
	public MapSHS(String name) {
        super(name);
    }
	
	public MapSHS() throws Exception 
	{
		
		
		super("SHS AutonHome");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	    this.setSize(1200, 650);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		try {
			buildingController=new BuildingController();
		
			building=buildingController.getBuilding();
			
			//add sensor to stock
			
			stock=new Stock();
			stock.setSensors(BuildingController.getSensorsNotInstalled());
			building.setStock(stock);
			
		
			
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
		
		StockList=new JLabel("STOCK_SENSOR",SwingConstants.CENTER);
		StockList.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		StockList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    StockList.setBackground(Color.BLUE);
		
		addSensorToStock= new JButton("View_Sensor");
		
		addSensorToStock.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				FormStockView fs = new FormStockView(building);
				fs.setVisible(true);
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
				
				
				try {
					
					
					f.setRoom((buildingController.getRoomListInFloor(f.getId())));
					buildingController.setSensorInROOM(f);

					ArrayList<Sensor> sensor2 = new ArrayList<>();
					for (Room r: f.getRoom()) 
					{
						
						sensor2.addAll(r.getSensors());
					}
						
					f.setSensors(sensor2);
					
					itemsListSensor.setListData(f.getSensors().toArray());
					plan.setCurrent_floor(f);
					
					
				} catch (IOException e2) {
					
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
        
        jLSensorId = new JLabel("ID_SENSOR",SwingConstants.CENTER);
        Border bord = BorderFactory.createLineBorder(Color.BLACK);
        jLSensorId.setBorder(bord);
		jLSensorId.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        jLSensorId.setHorizontalAlignment(JLabel.CENTER);
        jLSensorId.setBackground(Color.BLUE);
        
        
        
        jLSensorId.setPreferredSize(new Dimension(40, 40));
        jtxSensorId=new JTextField();
        jtxSensorId.setPreferredSize(new Dimension(40, 40));
        jbRemoveToStockSensor= new JButton("RemoveToStock");
        jbRemoveToStockSensor.setPreferredSize(new Dimension(40, 40));
        jbDefaultSensor=new JButton("DEFAULT");
        jbDefaultSensor.setPreferredSize(new Dimension(40, 40));
        pGenerate=new JPanel();
        pGenerate.setLayout(new GridLayout(2,1));
        pGenerate.add(jbRemoveToStockSensor);
        pGenerate.add(jbDefaultSensor);
        
        
        jtxSensorId.addKeyListener(new java.awt.event.KeyAdapter() {
			  public void keyTyped(java.awt.event.KeyEvent evt) {
				  char c=evt.getKeyChar();
				  if(!(Character.isDigit(c)||
				      (c==KeyEvent.VK_BACK_SPACE)||c==KeyEvent.VK_DELETE||evt.getKeyChar() == ',')){
				  //  evt.getKeyChar() == '.' does accept point when jtextfield accepts decimal number
				    evt.consume();
				    getToolkit().beep();
				  }
			  }
			  });
        
       
        
			
        jbRemoveToStockSensor.addActionListener(new ActionListener()  
		{
        	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Floor f = (Floor)itemsListFloor.getSelectedValue();
				Sensor selected=null;
				Room roomSelected=null;
				                    
				if(jtxSensorId.getText().equals("") )                           
				{
					  JOptionPane.showMessageDialog(MapSHS.this, "Field ID must not be blank"); 
				}
				
				else 
				{
					try {
						
					
						if(jtxSensorId.getText()!= "") 
						{
					
						for (Sensor s : f.getSensors() )
						{
							
							if (s.getId()== Integer.parseInt(jtxSensorId.getText())) 
							{
								selected = s;
								System.out.println(selected.getFk_room().getId());
								jtxSensorId.setText("");
								
								break;
							}	
								
						}	
						
						for(Room r : f.getRoom())
						{
									
									if (r.getId().equals(selected.getFk_room().getId()) )
									{
										roomSelected=r;
										System.out.println(roomSelected.getId());
										break;
									}
								   
						}
						
						
						
					}
						
						
					} catch (NumberFormatException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					} catch (HeadlessException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
							
					roomSelected.getSensors().remove(selected);
					selected.setX(null);
					selected.setY(null);
					selected.setFk_room_id(null);
					selected.setInstalled(false);
					
					Room ro=new Room();
					ro=null ;
					selected.setFk_room(ro);	
					try {
						buildingController.update(selected);

					} catch (IOException | SQLException e1) {
						
						e1.printStackTrace();
					}
					plan.getCurrent_floor().getSensors().remove(selected);
					plan.building.getStock().getSensors().add(selected);
					update();							
					}
				
				
			}
		
		});
        
        
        itemsListStock = new JList<Sensor>(building.getStock().getSensorArray());
        JScrollPane scrollPane3 = new JScrollPane(itemsListStock,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        itemsListStock.setDragEnabled(true);
        itemsListStock.setTransferHandler(new MyTransferHandler());
        
        
        
        panE.add(StockList);
        panE.add(scrollPane3);
        panE.add(addSensorToStock);
        
        panE.add(jLSensorId);
        panE.add(jtxSensorId);
        panE.add(pGenerate);
       
        
        
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

		    	
		          
		    }
		});
		
	}
		
	
	
	public Date getDate() {
		DateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d=new Date();
		
		return d;
	}
	
	// MapSHS implements IUpdatable
	public void update()  
	{
		
		Floor f = (Floor)itemsListFloor.getSelectedValue();
		
		itemsListSensor.setListData(f.getSensors().toArray());
		
		itemsListStock.setListData(building.getStock().getSensorArray());

	}

	
	
	
	
}



