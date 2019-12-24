package com.shs.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
	
	private final String[] entetes= {"Name", "Type","Price","Mac_address","IP_address"};
	
	private SensorController sensorService;
	

	
	public FormStockView(Building b)  
	{
		 super();
		 try {
			sensorService= new SensorController();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
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
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null,"Probleme Bdd Lecture"+e2.getMessage());
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
				 
	 		
		 nameField= new JTextField();                                
		 nameField.setBounds(140, 100, 160, 30); 
		
		
		 cb_typeSensor = new JComboBox<String>();
		 cb_typeSensor.setBounds(140, 135, 160, 30);;
		 
		 
		 cb_typeSensor.addItem("smoke_sensor");
		 cb_typeSensor.addItem("door_sensor");
		 cb_typeSensor.addItem("window_sensor");
		 cb_typeSensor.addItem("temperature_sensor");

		 String selectedType = (String) cb_typeSensor.getSelectedItem();

		
		
		 typeField= new JTextField(); 
		 typeField.setBounds(140, 170, 160, 30);
		 typeField.setColumns(20);
		 
		 
		 priceField = new JTextField(); 
		 priceField.setBounds(140, 205, 160, 30); 
		
		
		 
		 priceField.addKeyListener(new java.awt.event.KeyAdapter() {
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
		 
		
		 	 
		 ip_addressField= new JFormattedTextField(getMaskIP());
		 ip_addressField.setBounds(140, 240, 160, 30);
		
		 	
		 mac_addressField= new JTextField();
		 mac_addressField.setBounds(140, 275, 160, 30);
		
		
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
					 	 Sensor sensor= new Sensor();
					 	 Type_Sensor ts=null;
					 	 
					 	 try {
					 		 
							ts=BuildingController.getSensorTypeByName(cb_typeSensor.getSelectedItem().toString());
							sensor. setFk_type_sensor(ts);
							
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					 	 
					 	 
				    	 sensor.setMac_address(mac_addressField.getText().trim());
				    	 sensor.setIp_address(ip_addressField.getText().trim());
				    	
				    	 sensor.setInstalled(false);
				    	 
				    	 try {
				    		 System.out.println("test sensor");
							 try {
								sensorService.create(sensor);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							 JOptionPane.showMessageDialog(null,"Successfully Registered");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							 JOptionPane.showMessageDialog(null,"Probleme Bdd Création");
						}
				    	 try {
							initTableStock() ;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null,"Probleme Bdd lecture");
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
	   //list =(ArrayList<Sensor>) sensorService.getAllSensors();
	   String id;	
	   String type,name, price, ip_ad,mac_ad;
	     
	   model=new DefaultTableModel();
	   
	   for (int i=0;i<entetes.length;i++)
	   {
	    	 model.addColumn(entetes[i]);
	   }
	    
       for (int j = 0; j < list.size(); j++)
       {
    	   //id = String.valueOf(list.get(j).getId());
    	   name = list.get(j).getSensor_name();
    	   type = list.get(j).getFk_type_sensor().getName().toString();
    	   price = list.get(j).getPrice().toString();
	       ip_ad= list.get(j).getIp_address();
	       mac_ad = list.get(j).getMac_address();
	       
	       Object[] data = {name,type, price,ip_ad,mac_ad};
	       model.addRow(data);
       }
       
       table.setModel(model);
		
	}	
		
	public Sensor getResult()
	{
		return result;
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
	
	
	public MaskFormatter getMaskIP() 
	{
		MaskFormatter mask=null;
		try {
			 mask=new MaskFormatter("###.###.###.###");
			 mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 
		
		return mask;
		
	}
		

}
	   
	

