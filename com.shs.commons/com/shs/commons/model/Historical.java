package com.shs.commons.model;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Historical implements Comparable<Historical> {
	private int id;
	private Date date_signal;
	private Time hour_signal;
	private String message;
	private int fk_sensor;

	public Historical(int id, Date date_signal, Time hour_signal, String message, int fk_sensor) {
		super();
		this.id = id;
		this.date_signal = date_signal;
		this.hour_signal = hour_signal;
		this.message = message;
		this.fk_sensor = fk_sensor;
	}

	public Historical() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate_signal() {
		return date_signal;
	}

	public void setDate_signal(Date date_signal) {
		this.date_signal = date_signal;
	}

	public Time getHour_signal() {
		return hour_signal;
	}

	public void setHour_signal(Time hour_signal) {
		this.hour_signal = hour_signal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getFk_sensor() {
		return fk_sensor;
	}

	public void setFk_sensor(int fk_sensor) {
		this.fk_sensor = fk_sensor;
	}

	public String getDate_signal_formatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date_signal);
	}

	@Override
	public String toString() {
		return "Historical [id=" + id + ", date_signal=" + getDate_signal_formatted() + ", hour_signal=" + hour_signal + ", message="
				+ message + ", fk_sensor=" + fk_sensor + "]";
	}

	@Override
	public int compareTo(Historical o) {
		if(this.getHour_signal().getTime()< o.getHour_signal().getTime())
			return -1;
		else if(this.getId()>o.getId())
			return 1;
		else
			return 0;
	}





}
