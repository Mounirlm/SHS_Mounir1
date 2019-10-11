package com.shs.commons.model;

import java.util.ArrayList;
import java.util.List;

public class Stock {

	
	private static String name = "stock1";
	private List<Sensor>sensors ;
	
	

	public Stock() {
		super();
		sensors = new ArrayList<Sensor>();
		Stock.name=getName();
	}
	
	private String getName() {
		// TODO Auto-generated method stub
		return Stock.name;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}
	
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	public Sensor[] getSensorArray()
	{
		Sensor[] sa = new Sensor[sensors.size()];
		for(int i =0 ; i<sensors.size();i++)
		{
			sa[i] = sensors.get(i);
		}
		return sa;
	}

	

	
}
