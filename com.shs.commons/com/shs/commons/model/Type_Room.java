package com.shs.commons.model;

public class Type_Room {
	private int id;
	private String name;
	
	public Type_Room() {
		// TODO Auto-generated constructor stub
	}

	public Type_Room(int id, String name) {
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
		return this.name;
	}
	
	
}
