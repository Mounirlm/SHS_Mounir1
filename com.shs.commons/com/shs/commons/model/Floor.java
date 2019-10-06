package com.shs.commons.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Floor {
	private int id;
	String name;
	String imagePath; 
	static ArrayList<Room> room;
	transient BufferedImage floorcache =null;
	private Building fk_building;
	static ArrayList<Sensor> ret = new ArrayList<Sensor>();
	
	public Floor()
	{
		super();
		room=new ArrayList<Room>();
	}
	
	public Floor(int id,String name, String path, Building fk_building){
		this.id=id;
		this.name=name;
		imagePath=path;
		this.fk_building=fk_building;
	}
	
	public Floor(String imagePath) throws IOException 
	{
		this();
		this.imagePath=imagePath;
		File imageFile= new File(imagePath);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name;
	}

	public static ArrayList<Room> getRoom() {
		return room;
	}

	public void setRoom(List<Room> list) {
		this.room = (ArrayList<Room>) list;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	public static ArrayList<Sensor> getSensors()
	{	
//		ArrayList<Sensor> ret = new ArrayList<>();
//		
//		for (Room r : getRoom()) {
//			ret.addAll(r.getSensors());
//		}
		return ret;
	}

	public void setSensors(ArrayList<Sensor> arrayList) {
		ret = arrayList;
	}
	
	public static Sensor[] getSensorsArray(ArrayList<Sensor> sensors2) {
		Sensor[] sArray=new Sensor[sensors2.size()];
		for (int i=0; i<sensors2.size();i++)
		{
			sArray[i]=sensors2.get(i);
		}
		
		return sArray;
		
	}
	
	public BufferedImage getFloorImage()
	{
		if(floorcache==null)
		{
			try 
			{
				File bmpFile = new File(getImagePath());
				floorcache = ImageIO.read(bmpFile);
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
			}
		}
		return floorcache;
		
	}
	
	

	public Building getFk_building() {
		return fk_building;
	}

	public void setFk_building(Building fk_building) {
		this.fk_building = fk_building;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Floor [id=" + id + ", name=" + name + "]";
	}

	
	
		
	
}
