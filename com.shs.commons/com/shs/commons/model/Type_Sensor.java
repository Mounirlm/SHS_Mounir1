package com.shs.commons.model;

public class Type_Sensor {
	private int id;
	private String name;
	private Integer trigger_point_min;
	private Integer trigger_point_max;
	private Integer nb_alerts;
	
	public Type_Sensor() {
		// TODO Auto-generated constructor stub
	}

	public Type_Sensor(int id, String name, Integer trigger_point_min, Integer trigger_point_max, Integer nb_alerts) {
		super();
		this.id = id;
		this.name = name;
		this.trigger_point_min = trigger_point_min;
		this.trigger_point_max = trigger_point_max;
		this.nb_alerts = nb_alerts;
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

	

	public Integer getTrigger_point_min() {
		return trigger_point_min;
	}

	public void setTrigger_point_min(Integer trigger_point_min) {
		this.trigger_point_min = trigger_point_min;
	}

	public Integer getTrigger_point_max() {
		return trigger_point_max;
	}

	public void setTrigger_point_max(Integer trigger_point_max) {
		this.trigger_point_max = trigger_point_max;
	}

	public Integer getNb_alerts() {
		return nb_alerts;
	}

	public void setNb_alerts(Integer nb_alerts) {
		this.nb_alerts = nb_alerts;
	}


	
	public String toString2() {
		return "[name=" + name + "| trigger_point_min=" + trigger_point_min
				+ "| trigger_point_max=" + trigger_point_max + "| nb_alerts=" + nb_alerts + "]";
	}


	@Override
	public String toString() {//For combobox in GUI
		return this.name;
	}
	
	
	public int getSensorTypeID(String name) {
		 
		return this.getId();
	}
	
}
