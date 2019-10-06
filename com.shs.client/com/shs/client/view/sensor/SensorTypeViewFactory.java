package com.shs.client.view.sensor;

import java.io.IOException;
import java.sql.SQLException;

import com.shs.commons.model.Type_Sensor;

public class SensorTypeViewFactory {

	public static SensorTypeGenericView getInstance(Type_Sensor sensorType) throws IOException, SQLException, Exception {
		String type = sensorType.getName();
		switch (type) {
		case "smoke_sensor":
			return new SmokeSensorView(sensorType);
		case "temperature_sensor":
			return new TemperatureSensorView(sensorType);
		case "door_sensor":
			return new DoorSensorView(sensorType);
		case "window_sensor":
			return new WindowSensorView(sensorType);
		case "fall_sensor":
			return new FallSensorView(sensorType);
		default:
			return new SmokeSensorView(sensorType);
		}
	}
}
