package com.shs.commons.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sensor implements Transferable {

	private Integer id;
	private String sensor_name;
	private String ip_address;
	private String mac_address;
	private Date date_setup;
	private Boolean status;
	private Boolean installed;
	private Wing_Room fk_position;
	private Float price;
	private Room fk_room;
	private Type_Sensor fk_type_sensor;
	private Integer scope_sensor;
	private String date_setup_formatted;

	// adding coordinate of sensor
	private Integer x;
	private Integer y;

	private Integer fk_room_id;
	private Room room;

	public static enum SensorState {
		Marche, Arret, Alert;
	}

	private SensorState sensorState = SensorState.Marche;
	

	// transferable : Classe qui permet a des composants swing d'échanger des
	// transferables par Dragndrop

	public static final DataFlavor SENSOR_DATA_FLAVOR = new DataFlavor(Sensor.class, "java/ListItem");

	public Sensor() {
		// TODO Auto-generated constructor stub
	}

	public Sensor(Integer id, String sensor_name, String ip_address, String mac_address, Date date_setup,
			Boolean status, Boolean installed, Wing_Room fk_position, Float price, Room fk_room,
			Type_Sensor fk_type_sensor, Integer scope_sensor) {
		super();
		this.id = id;
		this.sensor_name = sensor_name;
		this.ip_address = ip_address;
		this.mac_address = mac_address;
		this.date_setup = date_setup;
		this.status = status;
		this.installed = installed;

		this.price = price;
		this.fk_room = fk_room;
		this.fk_type_sensor = fk_type_sensor;
		this.scope_sensor = scope_sensor;

	}

	public Sensor(Integer id, String sensor_name, String ip_address, String mac_address, Boolean status,
			Boolean installed, Wing_Room fk_position, Float price, Room fk_room, Type_Sensor fk_type_sensor,
			Integer scope_sensor, Integer x, Integer y,String state) {
		super();
		this.id = id;
		this.sensor_name = sensor_name;
		this.ip_address = ip_address;
		this.mac_address = mac_address;

		this.status = status;
		this.installed = installed;
		this.fk_position = fk_position;
		this.price = price;
		this.fk_room = fk_room;
		this.fk_type_sensor = fk_type_sensor;
		this.scope_sensor = scope_sensor;
		this.x = x;
		this.y = y;
		this.setStringToState(state);

	}

	public Sensor(Integer id, String sensor_name, String ip_address, String mac_address, Boolean status,
			Boolean installed, Wing_Room fk_position, Float price, Type_Sensor fk_type_sensor, Integer scope_sensor,
			Integer x, Integer y) {
		super();
		this.id = id;
		this.sensor_name = sensor_name;
		this.ip_address = ip_address;
		this.mac_address = mac_address;

		this.status = status;
		this.installed = installed;
		this.fk_position = fk_position;
		this.price = price;
		this.fk_type_sensor = fk_type_sensor;
		this.scope_sensor = scope_sensor;
		this.x = x;
		this.y = y;
		

	}

	public Sensor(Boolean installed, Integer x, Integer y) {
		this.installed = installed;

		this.x = x;
		this.y = y;
	}

	public Sensor(Integer id, String sensor_name, String ip_address, String mac_address, Boolean installed, Integer x,
			Integer y) {
		super();
		this.id = id;
		this.sensor_name = sensor_name;
		this.ip_address = ip_address;
		this.mac_address = mac_address;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSensor_name() {
		return sensor_name;
	}

	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public Date getDate_setup() {
		return date_setup;
	}

	public void setDate_setup(Date date_setup) {
		this.date_setup = date_setup;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getInstalled() {
		return installed;
	}

	public void setInstalled(Boolean installed) {
		this.installed = installed;
	}

	public Wing_Room getFk_position() {
		return fk_position;
	}

	public void setFk_position(Wing_Room fk_position) {
		this.fk_position = fk_position;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Room getFk_room() {
		return fk_room;
	}

	public void setFk_room(Room fk_room) {
		this.fk_room = fk_room;
	}

	public Type_Sensor getFk_type_sensor() {
		return fk_type_sensor;
	}

	public void setFk_type_sensor(Type_Sensor fk_type_sensor) {
		this.fk_type_sensor = fk_type_sensor;
	}

	public Integer getScope_sensor() {
		return scope_sensor;
	}

	public void setScope_sensor(Integer scope_sensor) {
		this.scope_sensor = scope_sensor;
	}

	public String getDate_setup_formatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return date_setup == null ? null : dateFormat.format(date_setup);

	}

	public Object[] getAsArray() {
		Object[] array = { id, sensor_name, ip_address, mac_address, date_setup, status, installed, fk_position, price,
				fk_room, fk_type_sensor, scope_sensor };
		return array;
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

	// Les données glissées sont identifiées par des données de type DataFlavor

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

		if (!isDataFlavorSupported(flavor)) {
			throw new UnsupportedFlavorException(flavor);
		}
		return this;

	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors = new DataFlavor[1];
		flavors[0] = SENSOR_DATA_FLAVOR;
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.equals(SENSOR_DATA_FLAVOR));
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return " Sensor_ID : " + id + " Type : " + fk_type_sensor;
	}

	public Integer getFk_room_id() {
		return fk_room_id;
	}

	public void setFk_room_id(Integer fk_room_id) {
		this.fk_room_id = fk_room_id;
	}

	public SensorState getState() {
		return sensorState;
	}
	
	public void setState(SensorState ss) {
		
		this.sensorState=ss;
		
		// Log 
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();

				File f = new File("..\\ressources\\log.txt");

				Log.AddLog("L'état du capteur a changé vers :" + ss.toString(), String.valueOf(getId()),
						dateFormat.format(date));
				try {
					Log.saveToFile(f);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
		
		
	}

	// From string in Base to state
	public void setStringToState(String ss) {

		if(ss.equals("Marche")) {
			this.sensorState= SensorState.Marche;
		}

		else if(ss.equals("Alert")) {
			this.sensorState= SensorState.Alert;
		}
		else {
			this.sensorState= SensorState.Arret;
		}
		
		
	}
	
	// Convert state to string in base
	public String getStringFromState(SensorState ss) {

		if(ss.equals(SensorState.Marche)) {
			return "Marche";
		}

		else if(ss.equals(SensorState.Alert)) {
			return "Alert";
		}
		else {
			return "Arret";
		}
	}
	

}
