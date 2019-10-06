package com.shs.server.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.shs.commons.model.Alert;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Historical;
import com.shs.commons.model.Resident;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.User;
import com.shs.server.connection.pool.AccessConfig;
import com.shs.server.connection.pool.DataSource;
import com.shs.server.model.AlertManager;
import com.shs.server.model.AlertRequestManager;
import com.shs.server.model.BuildingRequestManager;
import com.shs.server.model.FloorRequestManager;
import com.shs.server.model.HistoricalManager;
import com.shs.server.model.HistoricalRequestManager;
import com.shs.server.model.ResidentRequestManager;
import com.shs.server.model.RoomManager;
import com.shs.server.model.RoomRequestManager;
import com.shs.server.model.SensorManager;
import com.shs.server.model.SensorRequestManager;
import com.shs.server.model.Type_RoomRequestManager;
import com.shs.server.model.Type_SensorRequestManager;
import com.shs.server.model.UserManager;
import com.shs.server.model.UserRequestManager;
import com.shs.server.model.Wing_RoomRequestManager;


public class RequestHandler implements Runnable {
	private static int cpt=0;
	private int num;
	private Socket client= null;
	private Connection connDB;
	private JsonReader reader;
	private JsonWriter writer;
	private static Map<Integer, ArrayList<Historical>> CACHE = new HashMap<>();


	public RequestHandler(Socket client, Connection connDB) {
		num=cpt++;
		this.client = client;
		this.connDB = connDB;


		try {
			reader = new JsonReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			writer = new JsonWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
		} catch (IOException e) {}

	}

	@Override
	public void run(){
		//Communication Json
		try {
			System.out.println("Thread:"+num+" "+requestHandler(reader));
			if(!taskBrokenSensors.isAlive()) {
				taskBrokenSensors.start();
			}
		} catch (IOException e) {
			System.out.println("Error communication to client "+e);
		} catch (SQLException e) {
			System.out.println("Error DB "+e);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finally{
			try {
				stopConnection();
			} catch (IOException e) {}
		}

	}

	public String requestHandler(JsonReader reader) throws IOException, SQLException, ParseException {
		String request=null;
		Object object=null;
		String className=null;
		String[] res=null;
		String message=null;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("request")) {
				request = reader.nextString();
				res=request.split("-");
				className=res[1];
			}
			else if (name.equals("object")) {//TODO ADD CLASS FOR R3
				String objectJson = reader.nextString();
				if(className.equals("Room"))
					object = new Gson().fromJson(objectJson, Room.class);
				if(className.equals("User"))
					object = new Gson().fromJson(objectJson, User.class);
				if(className.equals("Sensor"))
					object = new Gson().fromJson(objectJson, Sensor.class);
				if(className.equals("Alert"))
					object = new Gson().fromJson(objectJson, Alert.class);
				if(className.equals("Historical"))
					object = new Gson().fromJson(objectJson, Historical.class);
				if(className.equals("Building"))
					object = new Gson().fromJson(objectJson, Building.class);
				if(className.equals("Floor"))
					object = new Gson().fromJson(objectJson, Floor.class);
				


			}else {
				reader.skipValue();
			}
		}
		reader.endObject();

		//DB Traitement TODO ADD CLASS FOR R3
		switch (className) {
		case "Room":
			Room room =(Room) object;
			RoomRequestManager reqRoom = new RoomRequestManager(connDB, reader, writer, room, request);
			message=reqRoom.requestManager();
			break;

		case "User":
			User user =(User) object;
			UserRequestManager reqUser = new UserRequestManager(connDB, reader, writer, user, request);
			message=reqUser.requestManager();
			break;

		case "Sensor":
			Sensor sensor =(Sensor) object;
			SensorRequestManager reqSensor = new SensorRequestManager(connDB, reader, writer, sensor, request);
			message=reqSensor.requestManager();
			break;

		case "Alert":
			Alert alert =(Alert) object;
			AlertRequestManager reqAlert = new AlertRequestManager(connDB, reader, writer, alert, request);
			message=reqAlert.requestManager();
			break;

		case "Type_Room":
			Type_RoomRequestManager reqType_Room = new Type_RoomRequestManager(connDB, reader, writer, request);
			message=reqType_Room.requestManager();
			break;

		case "Wing_Room":
			Wing_RoomRequestManager reqWing_Room = new Wing_RoomRequestManager(connDB, reader, writer, request);
			message=reqWing_Room.requestManager();
			break;

		case "Type_Sensor":
			Type_SensorRequestManager reqType_Sensor = new Type_SensorRequestManager(connDB, reader, writer, request);
			message=reqType_Sensor.requestManager();
			break;

		case "Historical":
			Historical historic =(Historical) object;			
			//add to DB
			HistoricalRequestManager reqHist = new HistoricalRequestManager(connDB, reader, writer, historic, "insert-Historical");
			message=reqHist.requestManager();
			//CACHE
			synchronized (CACHE) {
				this.addToCacheAndTreatment(historic);
			}

			break;
		case "Resident":
			Resident resident =(Resident) object;
			ResidentRequestManager reqResident = new ResidentRequestManager(connDB, reader, writer, resident, request);
			message=reqResident.requestManager();
			break;
		
		case "Building":
			
			System.out.println(request);
			BuildingRequestManager reqBuilding = new BuildingRequestManager(connDB, reader, writer, request);
			message=reqBuilding.requestManager();
			
			break;	
		case "Floor":
			Floor floor =(Floor) object;
			FloorRequestManager reqFloor = new FloorRequestManager(connDB, reader, writer, floor, request );
			message=reqFloor.requestManager();
			break;	
		

		default:
			break;
		}
		return message;
	}

	/*
	 * Check if the signal(historical receive) had a message value upper or lower than trigger points of alerts
	 */
	private boolean isAlertInCache(Historical historic, Sensor sensor) {
		boolean rep = false;
		if (sensor.getFk_type_sensor().getName().equals("door_sensor") || sensor.getFk_type_sensor().getName().equals("window_sensor")) {
			Time hour_min= new Time(60*60*1000*sensor.getFk_type_sensor().getTrigger_point_min());
			Time hour_max= new Time(60*60*1000*sensor.getFk_type_sensor().getTrigger_point_max());
			
			//Check if the hour of today is upper or lower than trigger hours
			if ((todayTime().getTime()<= hour_min.getTime()) && (Integer.parseInt(historic.getMessage())==1)) {
				rep=true;
			}
			if ((todayTime().getTime()>= hour_max.getTime()) && (Integer.parseInt(historic.getMessage())==1)) {
				rep=true;
			}
		}else {//temperature, fall and smoke sensors
			//check if value lower than trigger point min
			if (sensor.getFk_type_sensor().getTrigger_point_min()!=0) {
				if (Integer.parseInt(historic.getMessage())<= sensor.getFk_type_sensor().getTrigger_point_min()) {
					rep=true;
				}
			}
			//check if value upper than max trigger
			else if (sensor.getFk_type_sensor().getTrigger_point_max()!=0) {
				if (Integer.parseInt(historic.getMessage())>= sensor.getFk_type_sensor().getTrigger_point_max()) {
					rep=true;
				}
			}
		}
		return rep;

	}

	/*
	 * Add the signal value in hash map of cache with id sensor in key and historical in value
	 * Check if nb_alert signal is reach
	 * Insert an alert in DB if it is the case
	 */
	private void addToCacheAndTreatment(Historical historic) {
		//get sensor of signal
		SensorManager sensM = new SensorManager(connDB);
		Sensor sensor = null;
		boolean isAlert=false;

		try {//get the sensor of the signal
			sensor = SensorManager.getSensor(historic.getFk_sensor());
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		//add to cache evry signals
		if (!CACHE.containsKey(historic.getFk_sensor())) {
			ArrayList<Historical> a  = new ArrayList<Historical>();
			a.add(historic);
			CACHE.put(historic.getFk_sensor(), a);
		}else {
			ArrayList<Historical> a = CACHE.get(historic.getFk_sensor());
			a.add(historic);
		}

		//check of cache to add alert in DB
		ArrayList<Historical> hitoricals = CACHE.get(sensor.getId());//get all signals of the sensor
		ArrayList<Historical> last = new ArrayList<>();

		//get last signals
		for (int i = hitoricals.size()-1; i+1 >= sensor.getFk_type_sensor().getNb_alerts(); i--) {
			last.add(hitoricals.get(i));
		}

		//check if we have a number of signals of alert of alert high
		int cpt_alert_signals = 0;
		for (int i = 0; i < last.size(); i++) {
			if (isAlertInCache(last.get(i), sensor)) {
				cpt_alert_signals++;
			}
		}
		if (cpt_alert_signals==sensor.getFk_type_sensor().getNb_alerts()) {
			isAlert=true;
		}

		//insert an alert in alert table	
		if (isAlert) {
			Alert alert = new Alert();
			alert.setFk_sensor(sensor.getId());
			alert.setDescription(historic.getMessage());
			alert.setDate_alert(historic.getDate_signal());
			alert.setHour_alert(historic.getHour_signal());
			alert.setFk_user(1);
			alert.setStatus(true);
			try {
				AlertManager alertManager = new AlertManager(connDB);
				SensorManager sensorManager = new SensorManager(connDB);
				AlertManager.create(alert);
				sensor.setStatus(false);
				SensorManager.update(sensor);
			}catch(SQLException ex) {
				System.err.println("Error Cache insert alert or sensor: "+ex.getMessage());
			}
		}
	}




	private static String todayFormatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
	private static Time todayTime() {
		return new java.sql.Time(new Date().getTime());
	}

	//Create static thread to detect broken sensors
	private static Thread taskBrokenSensors = new Thread(new Runnable() {
		public void run() {
			try {//initial delay
				Thread.sleep(AccessConfig.getINITIAL_DELAY());
			}catch(Exception ex) {
				System.err.println(ex.getMessage());
			}
			while(true) {

				Connection conBS=null;
				try {
					try {
						conBS = DataSource.getConnection();
					} catch (SQLException e1) {
						System.err.println(e1.getMessage());
					}

					SensorManager sensorManager = new SensorManager(conBS);
					HistoricalManager historicalManager = new HistoricalManager(conBS);
					AlertManager alertManager = new AlertManager(conBS);

					ArrayList<Sensor> sensors = new ArrayList<>();

					//get installed and status ok sensors
					try {
						sensors = SensorManager.getSensors("select * from sensor where installed=true;");
					} catch (SQLException | ParseException e) {
						System.err.println(e.getMessage());
					}
					//for each sensor, we check historical table if the last message is superior than 10000 mil of actual hour
					for (Sensor sensor : sensors) {
						boolean signal=true;
						//get signals
						ArrayList<Historical> hists = new ArrayList<>();
						try {
							hists = HistoricalManager.getHistoricals("select * from historical where fk_sensor="+sensor.getId());
						} catch (SQLException | ParseException e) {
							System.err.println(e.getMessage());
						}
						//create an alert if no hitoricals
						if(hists.isEmpty()) {
							signal=false;
						}else {//create an alert if last historcal high
							Historical hist = hists.get(hists.size()-1);
							if (hist.getDate_signal_formatted().equals(todayFormatted())) {
								if ((new Date().getTime() - (hist.getDate_signal().getTime()+hist.getHour_signal().getTime()))/1000 > AccessConfig.getLAST_SIGNAL_DELAY()) {
									signal=false;
								}
							}
						}
						if(!signal) {
							Alert alert = new Alert();
							alert.setFk_sensor(sensor.getId());
							alert.setDescription("no signals");
							alert.setDate_alert(new Date());
							alert.setHour_alert(todayTime());
							alert.setFk_user(1);
							alert.setStatus(true);
							try {
								AlertManager.create(alert);
								sensor.setStatus(false);
								SensorManager.update(sensor);
							}catch(SQLException ex) {
								System.err.println("Error Cache insert alert or sensor: "+ex.getMessage());
							}
						}

					}
				}finally {
					//releaseConnection
					try {
						DataSource.releaseConnection(conBS);
					} catch (SQLException e1) {
						System.err.println(e1.getMessage());
					}
				}

				try {//delay
					Thread.sleep(AccessConfig.getDEFAULT_DELAY());
				}catch(Exception ex) {
					System.err.println(ex.getMessage());
				}

			}

		}


	});


	public void stopConnection() throws IOException {
		reader.close();
		writer.close();
		client.close();

		//release DB Connection
		try {
			DataSource.releaseConnection(connDB);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


}
