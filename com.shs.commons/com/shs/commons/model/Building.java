package com.shs.commons.model;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private int id;
	private String name, type;
	private ArrayList<Floor> floors;
	private Stock stock;
	
	
	
	public Building(int id,String name,String type) 
	{
		super();
		this.id=id;
		this.name=name;
		this.type=type;
	}
   
	
	public Building() 
	{
		super();
		floors=new ArrayList<Floor>();
		stock=new Stock();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		type = type;
	}

	public ArrayList<Floor> getFloor() {
		return floors;
	}

	public void setFloor(List<Floor> list) {
		this.floors = (ArrayList<Floor>) list;
	}
	
	public Stock getStock() {
		return stock;
	}


	public void setStock(Stock stock) {
		this.stock = stock;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Building [id=" + id + ", name=" + name + ", type=" + type + ", floors=" + floors + "]";
	}
	
}
