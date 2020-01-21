package com.shs.commons.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Log {
	
	public static ArrayList<Log> logs = new ArrayList<Log>();
	
	public String text;
	public String sensorName;
	public String date;
	
	public Log(String t, String s, String d) 
	{
		this.text = t;
		this.sensorName = s;
		this.date = d;
	}
	
	public static void AddLog(Log l)
	{
		logs.add(l);
	}
	
	public static void AddLog(String t, String s, String d)
	{
		logs.add(new Log(t,s,d));
		
	}
	
	public static void saveToFile(File fileName) throws FileNotFoundException, UnsupportedEncodingException
	{
		
	        PrintWriter out = new PrintWriter(fileName, "UTF-8");
	      
		    for(Log lg:logs) {
		    	out.println(lg);
		    }
	
          out.close();
	}
			
	@Override
	public String toString() {
		return "Log [text=" + text + ", sensorName=" + sensorName + ", date=" + date + "]";
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	
	
}
