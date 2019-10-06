package com.shs.client.view.sensor;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JPanel;

import com.shs.client.controller.TypeSensorController;
import com.shs.commons.model.Type_Sensor;

public abstract class SensorTypeGenericView extends JPanel {
	
	private TypeSensorController typeSensorController;
	
	private Type_Sensor sensorToUpdate;
	
	public SensorTypeGenericView(Type_Sensor sensor) throws Exception, IOException, SQLException {
		super();
		sensorToUpdate = sensor;
		typeSensorController = new TypeSensorController();
		display();
	}
	
	public boolean update(Type_Sensor typeSensor) throws IOException, SQLException {
		return typeSensorController.update(typeSensor);
	}
	
	public Type_Sensor getSensorToUpdate() {
		return sensorToUpdate;
	}
	
	public abstract void display();
}
