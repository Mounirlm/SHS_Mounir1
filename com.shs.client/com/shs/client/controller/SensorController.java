package com.shs.client.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.shs.commons.model.Sensor;
import com.shs.commons.model.SensorClientHandler;

public class SensorController {
private SensorClientHandler sensorService;

public SensorController() throws UnknownHostException, IOException {
	sensorService = new SensorClientHandler();
}

public List<Sensor> getAllSensors() throws IOException {
	
	return sensorService.searchAllSensors();
}

public List<Sensor> getAllSensorsWithPosition()throws IOException {
	  return sensorService.searchSensorWithPositionToServer();
}

public ArrayList<Sensor> getSensorsInRoom(int idFloor)throws IOException {
	  return sensorService.selectSensorInRoom(idFloor);
}
}