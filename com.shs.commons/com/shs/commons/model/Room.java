package com.shs.commons.model;

import java.awt.Point;
import java.util.ArrayList;

public class Room implements Comparable<Room>{

	private Integer id;
	private Integer floor;
	private Integer room_number;
	private Integer m2;
	private Type_Room type_room;
	private Wing_Room wing_room;
	private Integer nb_doors;
	private Integer nb_windows;
	private Integer x;
	private Integer y;
	private Integer width;
	private Integer height;
	private Floor fk_floor_map;
	
	
	private ArrayList<Sensor> sensors;

	public Room(Integer id, Integer floor, Integer room_number, Integer m2, Type_Room type_room, Wing_Room wing_room,
			int nb_doors, int nb_windows) {
		super();
		this.id = id;
		this.floor = floor;
		this.room_number = room_number;
		this.m2 = m2;
		this.type_room = type_room;
		this.wing_room = wing_room;
		this.nb_doors = nb_doors;
		this.nb_windows = nb_windows;
		
	}

	public Room(Integer id, Integer floor, Integer room_number, Integer m2, Type_Room type_room, Wing_Room wing_room,
			int nb_doors, int nb_windows,Integer x,Integer y,Integer width, Integer height,Floor fk_floor) {
		super();
		this.id = id;
		this.floor = floor;
		this.room_number = room_number;
		this.m2 = m2;
		this.type_room = type_room;
		this.wing_room = wing_room;
		this.nb_doors = nb_doors;
		this.nb_windows = nb_windows;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.fk_floor_map=fk_floor;
		
	}
	
	

	public Room(Integer x,Integer y,Integer width, Integer height,Floor fk_floor) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.fk_floor_map=fk_floor;
	}
	
	public Room() {
		
	}
	
	public boolean isPointInRoom(Point p)
	{
		return (p.getX()>this.x && p.getY()>this.y && p.getX()<this.x+this.width && p.getY()<this.y+this.height);
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}


	public Integer getRoom_number() {
		return room_number;
	}

	public void setRoom_number(Integer room_number) {
		this.room_number = room_number;
	}

	public Integer getM2() {
		return m2;
	}

	public void setM2(int m2) {
		this.m2 = m2;
	}

	public Type_Room getType_room() {
		return type_room;
	}

	public void setType_room(Type_Room type_room) {
		this.type_room = type_room;
	}

	public Wing_Room getWing_room() {
		return wing_room;
	}

	public void setWing_room(Wing_Room wing_room) {
		this.wing_room = wing_room;
	}


	public Integer getNb_doors() {
		return nb_doors;
	}



	public void setNb_doors(int nb_doors) {
		this.nb_doors = nb_doors;
	}



	public Integer getNb_windows() {
		return nb_windows;
	}



	public void setNb_windows(int nb_windows) {
		this.nb_windows = nb_windows;
	}



	public Integer getX() {
		return x;
	}



	public void setX(Integer x) {
		this.x = x;
	}



	public Integer getY() {
		return y;
	}



	public void setY(Integer y) {
		this.y = y;
	}



	public Integer getWidth() {
		return width;
	}



	public void setWidth(Integer width) {
		this.width = width;
	}



	public Integer getHeight() {
		return height;
	}



	public void setHeight(Integer height) {
		this.height = height;
	}

	public ArrayList<Sensor> getSensors()
	{
		return sensors;
	}
	
	public void setSensors(ArrayList<Sensor> sensors) {
		this.sensors = sensors;
	}

	public Floor getFk_floor() {
		return fk_floor_map;
	}

	public void setFk_floor(Floor fk_floor) {
		this.fk_floor_map = fk_floor;
	}


	@Override
	public int compareTo(Room o) {
		if(this.getId()<o.getId())
			return -1;
		else if(this.getId()>o.getId())
			return 1;
		else
			return 0;
	}



	@Override
	public String toString() {
		return "Room [id=" + id + ", floor=" + floor + ", room_number=" + room_number + ", m2=" + m2 + ", type_room="
				+ type_room + ", wing_room=" + wing_room + ", nb_doors=" + nb_doors + ", nb_windows=" + nb_windows
				+ "]";
	}

}