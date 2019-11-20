package com.shs.client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.shs.client.controller.BuildingController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;




public class MapPanelView extends JPanel implements MouseListener, MouseMotionListener{
	
	ArrayList<IUpdatable>updatables;
	Building building;
	ArrayList<Sensor> sensors= new ArrayList<Sensor>();
	Thread thread;
	boolean isActive =false;
	Floor current_floor=null;
	BuildingController bc;
	//Log log;
	
	Sensor sensorToUpdate;
	
	FormStockView fs=null;
	
	
	

	public MapPanelView( Building bg) 
	{
		this.building=bg;
		updatables = new ArrayList<>();
		this.addMouseListener(this);
		this.setActive(true);
		this.addMouseMotionListener(this);
		
		
		//DropTarget est un objet associé à un composant indiquant que celui-ci peut recevoir un DnD
				// on active le drop sur le PLAN
				this.setTransferHandler(new MyTransferHandler());
				
				this.setDropTarget(new DropTarget() 
				{			
					public void drop(DropTargetDropEvent dtde) 
					{  
						//dtde:Appelé lors d'un drop
						dtde.acceptDrop(dtde.getDropAction());
						Sensor selected = null;
						Room room=null;
						try 
						{
							selected = (Sensor)dtde.getTransferable().getTransferData(Sensor.SENSOR_DATA_FLAVOR);
							
						} 
						catch (Exception ex)
						{
							
						}
						if(selected==null) return;
						
						for(Room r:current_floor.getRoom()) 
						{
						
							if(r.isPointInRoom(dtde.getLocation()))
							{	room=r;
								r.getSensors().add(selected);
//								selected.setRoom(r);
								selected.setX((int)dtde.getLocation().getX());
								selected.setY((int)dtde.getLocation().getY());
								selected.setFk_room_id(r.getId());
								selected.setInstalled(true);
								Date dte =new Date();
						    	selected.setDate_setup(dte);
						    	
						    	
							}
						 }
						
						 try {
								bc.update(selected);
								
							} catch (IOException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 selected.setFk_room(room);
						 MapPanelView.this.building.getStock().getSensors().remove(selected);
						MapPanelView.this.getCurrent_floor().getSensors().add(selected);
						activateListener();
					}
				});		
			  
		   }
		
	
	
	public void setActive(boolean active)
	{
		if( active==this.isActive)return; //
		
		this.isActive=active;
		if(active==true) 
		{
			thread  =new Thread()
			{
			    @Override
				public void run() 
				{
					super.run();
					while(MapPanelView.this.isActive)
					{
						MapPanelView.this.validate();
						MapPanelView.this.repaint();
						try 
						{
							Thread.sleep((long) 100);
						} 
						catch (InterruptedException e) 
						{
							
							e.printStackTrace();
						}
					}
					
				}
					  
			};
			thread.start();
		}
		
	}
	
	public void paint(Graphics g) 
	{
		super.paintComponent(g);
		
		if(current_floor==null)return;
		
		g.drawImage(current_floor.getFloorImage(), 0, 0, null);
		
		for(Room r : current_floor.getRoom())
		{
			
			for(Sensor s: r.getSensors()) 
			{
				
				g.drawRect(s.getX(), s.getY(), 30, 30);
				
				
					int millis = (int) (System.currentTimeMillis()/1000);
					if(millis%2==0)
					{
						g.setColor(Color.GREEN);	
						
						
					}
					else
					{
						g.setColor(Color.DARK_GRAY);
					}
					
				
				
				g.fillRect(s.getX()+1, s.getY()+1, 29, 29);
			}
		}
		
		
	}
	
  @Override	
  public void mouseClicked(MouseEvent e) {

	  Sensor selected = null;
	  Room roomSelected= null;
	
	  boolean b=false;
	
	  	
	  	for(Room rs: current_floor.getRoom())
	  	{
	  		if(rs.isPointInRoom(e.getPoint()))
	  		{
	  			roomSelected=rs;
	  		
	  			for(Sensor ss: roomSelected.getSensors()) 
	  			{
	  				
	  				if(e.getX()>ss.getX() && e.getX()<ss.getX()+30 && e.getY()>ss.getY() && e.getY()<ss.getY()+30)
	  				{
	  					selected=ss;
	  					b=true;
	  					break;	 
	  				}
	  			}
	  				
	  		}
	  	}
	  	
	  	if(!b) {
	  		JOptionPane.showMessageDialog(this, 
	  		         " Please click on sensor to remove it",
	  		         " Sensor ",
	  		         JOptionPane.WARNING_MESSAGE);
	  		return;
	  	}
	  
  
	  	roomSelected.getSensors().remove(selected);
		selected.setX(null);
		selected.setY(null);
		selected.setFk_room_id(null);
		selected.setInstalled(false);
		//selected.setDate_setup(null);
		Room ro=new Room();
		ro=null ;
		selected.setFk_room(ro);	
		try {
			bc.update(selected);

		} catch (IOException | SQLException e1) {
			
			e1.printStackTrace();
		}
		MapPanelView.this.getCurrent_floor().getSensors().remove(selected);
		MapPanelView.this.building.getStock().getSensors().add(selected);
		activateListener();
		//return;
	  	
	 
		
	}	
	 
	
	public Date getDate() {
		DateFormat dateformat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d=new Date();
		
		return d;
	}

	@Override	
	public void mouseMoved(MouseEvent e) 
	{
		
		
		for(Room r : current_floor.getRoom())
		{
			
			for(Sensor s: r.getSensors()) 
			{
		
				if(e.getX()>s.getX() && e.getX()<s.getX()+30 && e.getY()>s.getY() && e.getY()<s.getY()+30) 
				{
					setToolTipText(s.toString() + " , IdRoom : " + r.getId());
					
				}
			}
		}
	}

	public Floor getCurrent_floor() {
		return current_floor;
	}

	public void setCurrent_floor(Floor current_floor) {
		this.current_floor = current_floor;
	}
	
	
	public void addUpdatableListener(IUpdatable listener)
	{
		updatables.add(listener);
	}
	
	public void removeUpdatableListener(IUpdatable listener)
	{
		updatables.remove(listener);
	}

	public void activateListener() 
	{
		for(IUpdatable i : updatables)
		{
			i.update();
		}
	}
	
	

	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
