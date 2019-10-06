package com.shs.commons.model;

public class Wing_Room {
	private int id;
	private String name;
	
	public Wing_Room() {
		// TODO Auto-generated constructor stub
	}

	public Wing_Room(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}


