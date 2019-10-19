package com.shs.client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Sensor;


public class FormStockView extends JDialog  
	{
			
		private JPanel p1;
		
		private JTextField idField, typeField, xField, yField, roomIDField ;
		
		private JLabel idLabel, typeLabel, xLabel, yLabel, roomIDLabel  ;
		
		private JButton addButton, removeButton, exitButton; 

		private JLabel title;

		private DefaultTableModel model;

		private JTable table;

		private JScrollPane scrollpane;
	   
		private Building b;
		
		private Sensor result = null;
		
		private List<Type_Sensor> sensorsType= null; // TODO recuperer
		
		private final String[] entetes= {"ID", "Type Sensor","X", "Y", "RoomID"};
		
		private BuildingController bc;
		private SensorController   sensorService;
		
		int x, y ,idFloor;
		Integer idRoom;
		
		MapSHS frameSHS;
		
		
		public FormStockView(Building b) {
			super();
			this.b = b;
		}

		
		public FormStockView(Building b, int x , int  y, int idRoom, int idFloor) 
		{
			 super();
			 this.b = b;
			 setModal(true); // modal
			
			 this.setTitle("SHS AutonHome");
			 this.setLocationRelativeTo(null);
		     this.setPreferredSize(new Dimension(1000, 500));
			 this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			 this.setLayout(null);
			 p1=new JPanel();
			 
			this.x=x;
			this.y=y;
			this.idRoom=idRoom;
			this.idFloor=idFloor;
			
		    try {
				sensorService =new SensorController();
			} catch (IOException e4) {
				
				e4.printStackTrace();
			}
				
			
			 
			
			 // Defining Model for table
					 
			 table = new JTable();  
		     table.setEnabled(true);
		    
		     initTableStock();
		 	 
		     
		     title = new JLabel("FORM SENSOR");
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
		     		
			 idLabel = new JLabel("ID");
			 idLabel.setBounds(30, 100, 100, 30);
			 	 
			 typeLabel = new JLabel("Type");  
			 typeLabel.setBounds(30, 135, 100, 30);
				 		 
			 xLabel =new JLabel("X");                               
			 xLabel.setBounds(30,205, 100, 30); 
			 
			 yLabel =new JLabel("Y"); 
			 yLabel.setBounds(30, 240, 100, 30); 
			 	 	 
			 roomIDLabel = new JLabel("idRoom");  
			 roomIDLabel.setBounds(30, 275, 100, 30);
					 
			 		
			 idField = new JTextField();                                
			 idField.setBounds(140, 100, 160, 30); 
			
		
			 typeField= new JTextField(); 
			 typeField.setBounds(140, 135, 160, 30);
			 typeField.setColumns(20);
			
			
			
			 xField = new JTextField();  
			 xField.setBounds(140, 205, 160, 30);
			 
			 
			 
			 
			 yField = new JTextField(); 
			 yField.setBounds(140, 240, 160, 30);	 

			 roomIDField= new JTextField();
			 roomIDField.setBounds(140, 275, 160, 30);
			
			 	
			 xField.setText(String.valueOf(x));
		     yField.setText(String.valueOf(y));
		     roomIDField.setText(String.valueOf(idRoom));
		     
			
			
			 addButton = new JButton("Add");
			 addButton.setBounds(30, 390, 115, 30); 
			
			 removeButton = new JButton("RemoveSelectRow");
			 removeButton.setBounds( 160, 390, 115, 30); 
			
			 exitButton = new JButton("EXIT");
			 exitButton.setBounds( 305, 390, 115, 30);
			 

			this.getContentPane().add(idLabel);
			this.getContentPane().add(typeLabel);
			this.getContentPane().add(xLabel);
			this.getContentPane().add(roomIDLabel);
			this.getContentPane().add(yLabel);
			
			this.getContentPane().add(idField);
			this.getContentPane().add(typeField);	
			this.getContentPane().add(xField);
			this.getContentPane().add(yField);
			this.getContentPane().add(roomIDField);
			
			this.getContentPane().add(removeButton);
			this.getContentPane().add(addButton);
			this.getContentPane().add(exitButton);
			this.getContentPane().add(title);		 
			
			
			 
			
			
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
			    	
			      
			      result=new Sensor();
			      
				  if(e.getSource()==addButton) 
				  {                        

					if(idField.getText().equals("") || typeField.getText().equals(""))                            
					{
					  JOptionPane.showMessageDialog(FormStockView.this, "Fields ID and TYPE must not be blank"); 
					}
					else 
					{ 
				       Boolean res=true;
					   for (Sensor s: b.getStock().getSensors()) 
					   { 
						   if (s.getId()==(Integer.parseInt(idField.getText())))
						    {
						    	res=false;
						    }
					   }	
					   
					   if(res)
					   {
						     Type_Sensor ts=null;
						     
						    System.out.println(typeField.getText().trim());
						     
						     try {
								ts=BuildingController.getSensorTypeByName(typeField.getText().trim());
							} catch (IOException e3) {
								System.out.println("pb de type sensor");
								e3.printStackTrace();
							} catch (SQLException e3) {
								System.out.println("pb type_sensor Sql");
								e3.printStackTrace();
							}
						   	 
						     result.setFk_type_sensor(ts);
					    	 result.setId(Integer.parseInt(idField.getText().trim()));
					    	 
					    	 
					    	Room room = null;
							try {
								
								room = BuildingController.getRoom(Integer.parseInt(roomIDField.getText().trim()),idFloor);
								
							} catch (NumberFormatException e2) {
								
								e2.printStackTrace();
							} catch (IOException e2) {
								
								e2.printStackTrace();
							}
					    	 
					    	 
					    	 Date dte =new Date();
					    	 result.setDate_setup(dte);
					    	 result.setDate_setup(dte);
					    	 result.setFk_room(room);
					    	 result.setFk_room_id(Integer.parseInt(roomIDField.getText().trim()));
					    	 
					    	 
					    	 result.setX(Integer.parseInt(xField.getText().trim()));
					    	 result.setY(Integer.parseInt(yField.getText().trim()));
					    	 
					    	 
					    	 
					    	 result.setInstalled(true);
					    	 
					    	 try {
								bc.create(result);
								
					    	 	} catch (IOException e1) {
								
								e1.printStackTrace();
					    	 	} catch (SQLException e1) {
								
								e1.printStackTrace();
					    	 	}
					    	 
					    	 System.out.println(result);
					    	 b.getStock().getSensors().add(result);
					    	 
					    	 JOptionPane.showMessageDialog(null,"Successfully Registered");
					    	  initTableStock() ;
										    	 	
					   }
						   
					   else 
					   {
						    JOptionPane.showMessageDialog(FormStockView.this,"ID already exists. Please enter another ID");						 
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
							todelete.setX(15);
							todelete.setY(165);
							todelete.setFk_room_id(1);
							b.getStock().getSensors().remove(todelete);
							try {
								sensorService.update(todelete)	;
							} catch (IOException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}		           
						}
						
						model.fireTableDataChanged();									
					}
				}
			});
			
			                    		
			//
			this.pack();
			//this.setVisible(true);
	 }
		
		

		


		public void initTableStock() 
		{   
			
		   List<Sensor> list=null;
		   list= b.getStock().getSensors();
		   System.out.println(list);
		   int id;	
		   String type;
		   int x,y;
		   Integer idRoom = null;
		   
		   
		   model=new DefaultTableModel();
		   
		   for (int i=0;i<entetes.length;i++)
		   {
		    	 model.addColumn(entetes[i]);
		   }
		    
	       for (int j = 0; j < list.size(); j++)
	       {
	    	   id = list.get(j).getId();
	    	   type = list.get(j).getFk_type_sensor().getName().toString();
		       x = list.get(j).getX();
		       y= list.get(j).getY();
		       //idRoom = list.get(j).getFk_room().getId();
		       
		       Object[] data = {id,type,x,y,idRoom};
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

		


		public int getY() {
			return y;
		}


		public void setY(int y) {
			this.y = y;
		}
		
		public int getX() {
			return x;
		}


		public void setX(int x) {
			this.x = x;
		}





		public Integer getIdRoom() {
			return idRoom;
		}





		public void setIdRoom(Integer idRoom) {
			this.idRoom = idRoom;
		}





		public JButton getAddButton() {
			return addButton;
		}





		public void setAddButton(JButton addButton) {
			this.addButton = addButton;
		}





		public JButton getRemoveButton() {
			return removeButton;
		}





		public void setRemoveButton(JButton removeButton) {
			this.removeButton = removeButton;
		}





		public JButton getExitButton() {
			return exitButton;
		}





		public void setExitButton(JButton exitButton) {
			this.exitButton = exitButton;
		}





		public void setResult(Sensor result) {
			this.result = result;
		}



		public JLabel getRoomIDLabel() {
			return roomIDLabel;
		}





		public void setRoomIDLabel(JLabel roomIDLabel) {
			this.roomIDLabel = roomIDLabel;
		}





		public JTextField getIdField() {
			return idField;
		}





		public void setIdField(JTextField idField) {
			this.idField = idField;
		}





		public JTextField getRoomIDField() {
			return roomIDField;
		}





		public void setRoomIDField(JTextField roomIDField) {
			this.roomIDField = roomIDField;
		}





		public JTextField getTypeField() {
			return typeField;
		}





		public void setTypeField(JTextField typeField) {
			this.typeField = typeField;
		}





		public int getIdFloor() {
			return idFloor;
		}





		public void setIdFloor(int idFloor) {
			this.idFloor = idFloor;
		}





		
		
		
			
}
