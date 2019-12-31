package com.shs.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.shs.client.controller.BuildingController;
import com.shs.client.controller.SensorController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Sensor.SensorState;
import com.shs.commons.model.Type_Sensor;


import java.util.ArrayList;



public class FormStockView extends JDialog 
{
		
	private JPanel p1;
	
	private JComboBox<String> cb_typeSensor;
	
	private JTextField  typeField, nameField,  ip_addressField, mac_addressField, priceField ;
	
	private JLabel  typeLabel, nameLabel, ip_addressLabel, mac_addressLabel,  priceLabel  ;
	
	private JButton addButton, removeButton, exitButton; 

	private JLabel title;

	private DefaultTableModel model;

	private JTable table;

	private JScrollPane scrollpane;
   
	private Building b;
	
	private Sensor result = null;
	
	private final String[] entetes= {"ID","Name", "Type","Price €","IP_address", "Mac_address" };
	
	private SensorController sensorService;
	
	private BuildingController bc;
	
	
	
	public FormStockView(Building b) 
	{
		 super();
		 try {
			sensorService= new SensorController();
		} catch (IOException e3) {
			
			e3.printStackTrace();System.out.println(e3.getMessage());
		}
		 
		 this.b = b;
		 setModal(true); // modal
		
		 this.setTitle("SHS AutonHome");
		 this.setLocationRelativeTo(null);
	     this.setPreferredSize(new Dimension(1000, 500));
		 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 this.setLayout(null);
		 p1=new JPanel();
		
		 
		 // Defining Model for table
				 
		 table = new JTable();  
	     table.setEnabled(true);
	    
	     try {
			initTableStock();
			
		} catch (IOException e4) {
			
			e4.printStackTrace();
		}
	     
	     title = new JLabel("FORM STOCK");
	     title.setOpaque(true);
	     title.setBackground(Color.BLUE);
	     title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	     title.setHorizontalAlignment(JLabel.CENTER);
	     title.setVerticalAlignment(JLabel.CENTER);
	     title.setBounds(0, 0, 1000, 30);;    
	     this.add(title); 
		 
		
	     p1.setLayout(new GridLayout());
	     p1.setBounds(330,75, 500, 300);                         
	     p1.setBorder(BorderFactory.createDashedBorder(Color.blue));
	     this.add(p1);
	     
	     p1.add(GetTable());
	     		
		 nameLabel = new JLabel("Name");
		 nameLabel.setBounds(30, 100, 100, 30);
		 	 
		 typeLabel = new JLabel("Type");  
		 typeLabel.setBounds(30, 135, 100, 30);
			 		 
		 priceLabel =new JLabel("Price");                               
		 priceLabel.setBounds(30,205, 100, 30); 
			 	 
		 mac_addressLabel= new JLabel("Mac_Address");                                
		 mac_addressLabel.setBounds(30, 240, 100, 30); 
		 		 
		 ip_addressLabel= new JLabel("IP_Address");                               
		 ip_addressLabel.setBounds(30, 275, 100, 30);
				 
	 		
		 nameField= new JFormattedTextField(getMaskName());                                
		 nameField.setBounds(140, 100, 160, 30); 
		
		
		 cb_typeSensor = new JComboBox<String>();
		 cb_typeSensor.setBounds(140, 135, 160, 30);
		 
		 
		 cb_typeSensor.addItem("smoke_sensor");
		 cb_typeSensor.addItem("door_sensor");
		 cb_typeSensor.addItem("window_sensor");
		 cb_typeSensor.addItem("temperature_sensor");

		 String selectedType = (String) cb_typeSensor.getSelectedItem();

		
		
		 typeField= new JTextField(); 
		 typeField.setBounds(140, 170, 160, 30);
		 typeField.setColumns(20);
		 
		 priceField = new JFormattedTextField(getMaskPrice());
		 priceField.setBounds(140, 205, 160, 30); 
	 
		
		 	 
		 ip_addressField= new JFormattedTextField(getMaskIP());
		 ip_addressField.setBounds(140, 275, 160, 30);
		 
		 ip_addressField.addKeyListener(new java.awt.event.KeyAdapter() {
			  public void keyTyped(java.awt.event.KeyEvent evt) {
				  char c=evt.getKeyChar();
				  if(!(Character.isDigit(c)||(c==KeyEvent.VK_BACK_SPACE)||c==KeyEvent.VK_DELETE)){
				  
				    evt.consume();
				    getToolkit().beep();
				  }
			  }
			  });
		
		 	
		 mac_addressField= new JFormattedTextField(getMaskMAC());
		 mac_addressField.setBounds(140, 240, 160, 30);
		 
		 mac_addressField.addKeyListener(new java.awt.event.KeyAdapter() {
			  public void keyTyped(java.awt.event.KeyEvent evt) {
				  char c=evt.getKeyChar();
				  if(!(Character.isDigit(c)||
				      c==KeyEvent.VK_BACK_SPACE||c==KeyEvent.VK_DELETE)){
				  
				    evt.consume();
				    getToolkit().beep();
				  }
			  }
			  });
		
		
		
		 addButton = new JButton("CREATE");
		 addButton.setBounds(30, 390, 115, 30); 
		
		 removeButton = new JButton("DELETE");
		 removeButton.setBounds( 160, 390, 115, 30); 
		
		 exitButton = new JButton("EXIT");
		 exitButton.setBounds( 305, 390, 115, 30);
		 

		
		this.getContentPane().add(typeLabel);
		this.getContentPane().add(nameLabel);
		this.getContentPane().add(priceLabel);
		this.getContentPane().add(mac_addressLabel);
		this.getContentPane().add(ip_addressLabel);
		this.getContentPane().add(typeField);
		this.getContentPane().add(cb_typeSensor);
		this.getContentPane().add(nameField);
		this.getContentPane().add(priceField);
		this.getContentPane().add(mac_addressField);
		this.getContentPane().add(ip_addressField);
		this.getContentPane().add(removeButton);
		this.getContentPane().add(addButton);
		this.getContentPane().add(exitButton);
		this.getContentPane().add(title);		 
		
		
		cb_typeSensor.addActionListener(new ActionListener() 
		 {		
			@Override
			public void actionPerformed(ActionEvent e) 
			{			
				JComboBox comboBox = (JComboBox) e.getSource();
                typeField.setText("Sensor type : " +comboBox.getSelectedItem().toString());
			}
		});
		
		
		exitButton.addActionListener(new ActionListener() 
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource() == exitButton) 
				{
					 
					 dispose(); 
				}				
			}
		});
		
		addButton.addActionListener(new ActionListener() 
		{			
		    @Override
			public void actionPerformed(ActionEvent e) 
			{			
			  if(e.getSource() ==addButton) 
			  {                        

				if( typeField.getText().equals(""))                            
				{
				  JOptionPane.showMessageDialog(FormStockView.this, "TYPE must not be blank"); 
				}
				else 
				{ 
					 	 result= new Sensor();
					 	 Type_Sensor ts=null;
					 	 
					 	 			 		 
						try {
							
							ts=BuildingController.getSensorTypeByName(cb_typeSensor.getSelectedItem().toString());
							
						} catch (IOException | SQLException e3) {
							
							e3.printStackTrace();
						}
						
						 result.setFk_type_sensor(ts);
							
						
					 	 
					 	 result.setSensor_name(nameField.getText().trim());							
					 	 result.setPrice(Float.parseFloat((priceField.getText().trim())));
					 	 				 	 
					 	 result.setMac_address(mac_addressField.getText().trim());
				    	 result.setIp_address(ip_addressField.getText().trim());
					 	 
					 	 try {
					 		 
							for(Sensor s : BuildingController.getSensorWithPosition())
							 {
								 if ( s.getIp_address()==result.getIp_address() ) {
									 
									JOptionPane.showMessageDialog(null,"This IP address already exist. Please choose another");
									return;
								 }
										 
										 
								 if (s.getMac_address()==result.getMac_address()) {
									 
									JOptionPane.showMessageDialog(null,"This MAC address already exist. Please choose another");
									return;
								 }
							 }
						} catch (HeadlessException | IOException e2) {
							
							e2.printStackTrace();
						}
				    	
				    	
				    	 result.setInstalled(false);
				    	 result.setState(SensorState.Arret);
				    	 
				    	 try {
				    		 System.out.println("test sensor");
							 try {
								 
								BuildingController.create(result);
								
							} catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							 JOptionPane.showMessageDialog(null,"Successfully Registered");
						} catch (IOException e1) {
							
							e1.printStackTrace();
							 JOptionPane.showMessageDialog(null,"Probleme Bdd Création");
						}
				    	
				    		try {
				    			
								for(Sensor s : BuildingController.getSensorsNotInstalled()) {
									
								    if (s.getSensor_name().equals(result.getSensor_name())) {
								    	
								    	setResult(s);
								    	
								    	b.getStock().getSensors().add(s); 
								    	initTableStock();
								    	break;
								    }
								
								}
								
							} catch (IOException e2) {
								
								e2.printStackTrace();
							}
				    		 	 	
					    					     
			   }
						 			     
		    }
				 
		  }
			
		});
				
		removeButton.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				if(e.getSource()==removeButton) 
				{     
					if (table.getSelectedRow() != -1) 
					{
						// collect id from selected row (id: column=0)
						String valueID = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
						
						// remove selected row from the model
						model.removeRow(table.getSelectedRow());
						
						Sensor todelete = null;
						for (Sensor s: b.getStock().getSensors())
	            		{
		            		if (s.getId()==Integer.parseInt(valueID))
		            		{
		            			todelete=s;
		            			
		            			
		            			break;
		            		}	
	            		}
						
						try {
							sensorService.delete(todelete);
						} catch (IOException | SQLException e1) {
							
							e1.printStackTrace();
						}
						
						JOptionPane.showMessageDialog(null,"Successfully Deleted");
						
						b.getStock().getSensors().remove(todelete); 
		           
					}
					
					model.fireTableDataChanged();									
				}
			}
		});
		
		                    		
		this.pack();
		this.setVisible(true);
     }
	
	

	

	public void initTableStock() throws IOException 
	{   
		
	   ArrayList<Sensor> list=null;
	   list= (ArrayList<Sensor>) b.getStock().getSensors();
	   
	   String id;	
	   String type,name, price, ip_ad,mac_ad;
	     
	   model=new DefaultTableModel();
	   
	   for (int i=0;i<entetes.length;i++)
	   {
	    	 model.addColumn(entetes[i]);
	   }
	    
       for (int j = 0; j < list.size(); j++)
       {
    	   id = String.valueOf(list.get(j).getId());
    	   name = list.get(j).getSensor_name();
    	   type = list.get(j).getFk_type_sensor().getName().toString();
    	   price = list.get(j).getPrice().toString();
	       ip_ad= list.get(j).getIp_address();
	       mac_ad = list.get(j).getMac_address();
	       
	       Object[] data = {id,name,type, price,ip_ad,mac_ad};
	       model.addRow(data);
       }
       
       table.setModel(model);
		
	}	
		
	public Sensor getResult()
	{
		return this.result;
	}
	
	public void setResult(Sensor s) {
		this.result=s;
		
	}
	
	public JScrollPane GetTable() 
	{   
	    // Enable Scrolling on table
	      scrollpane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	    		  		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	                        
	      return scrollpane;
	}
	
	
	
	public MaskFormatter getMaskDate() 
	{
		
		MaskFormatter mask=null;
		try {
			mask = new MaskFormatter("##/##/####");
			mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
		
			e.printStackTrace();
		} 
		mask.setValidCharacters("0123456789"); 
		
		return mask;
	}
	
	public MaskFormatter getMaskName() 
	{
		
		MaskFormatter mask=null;
		try {
			mask = new MaskFormatter("sensor-1g-###");
			mask.setPlaceholderCharacter(' ');
		} catch (ParseException e) {
		
			e.printStackTrace();
		} 
		
		mask.setValidCharacters("0123456789"); 
		return mask;
	}
	
	public MaskFormatter getMaskPrice() 
	{
		
		MaskFormatter mask=null;
		try {
			mask = new MaskFormatter("####.##");
			mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
		
			e.printStackTrace();
		} 
		mask.setValidCharacters("0123456789"); 
		
		return mask;
	}
	
	public MaskFormatter getMaskIP() 
	{
		MaskFormatter mask=null;
		try {
			 mask=new MaskFormatter("192.168.020.###");
			 mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 
		
		mask.setValidCharacters("0123456789"); 
		
		return mask;
		
	}
		
	public MaskFormatter getMaskMAC() 
	{
		MaskFormatter mask=null;
		try {
			 mask=new MaskFormatter("HH:HH:HH:HH:HH");
			 mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 
		
		return mask;
		
	}


}
	   
	

