package com.shs.client.view.sensor;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.shs.commons.model.Type_Sensor;

public class SensorTypeView extends JFrame {

	public SensorTypeView(Type_Sensor typeSensor) throws IOException, SQLException, Exception {
		super();
		this.setTitle("Sensor Configuration");
		this.setBounds(100, 100, 730, 489);
		SensorTypeGenericView content = SensorTypeViewFactory.getInstance(typeSensor);
		this.add(content);
	}
}
