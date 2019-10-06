package com.shs.client.view.sensor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.shs.commons.model.Type_Sensor;




public class SmokeSensorView extends SensorTypeGenericView {

	public SmokeSensorView(Type_Sensor typeSensor) throws IOException, SQLException, Exception {
		super(typeSensor);
	}
	  
	@Override
	public void display() {
		
		Type_Sensor received = getSensorToUpdate();
	
		JTextField textField = new JTextField();
        textField.setBounds(128, 28, 86, 20);
        textField.setColumns(10);
        if (received != null) {
        	textField.setText(received.getTrigger_point_max().toString());
        }
         
        JLabel lblSeuilMax = new JLabel("Seuil maximal");
        lblSeuilMax.setBounds(65, 31, 46, 14);
        this.add(textField);
		this.add(lblSeuilMax);
		
        JLabel lblAlerte = new JLabel("Nombre d'alertes");
        lblAlerte.setBounds(65, 68, 46, 14);
        this.add(lblAlerte);
         
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("1");
        comboBox.addItem("2");
        comboBox.addItem("3");
        comboBox.addItem("4");
        comboBox.addItem("5");
       
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox.setBounds(128, 65, 86, 20);
        this.add(comboBox);
         
         
        JButton btnConf = new JButton("Configurer");
         
        btnConf.setBackground(Color.BLUE);
        btnConf.setForeground(Color.MAGENTA);
        btnConf.setBounds(65, 387, 89, 23);
        btnConf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	int alert = Integer.parseInt(comboBox.getSelectedItem().toString());
            	int text = Integer.parseInt(textField.getText());
            	Type_Sensor typeSensor = getSensorToUpdate();
            	typeSensor.setNb_alerts(alert);
            	typeSensor.setTrigger_point_max(text);
            	try {
					boolean updated = update(typeSensor);
					if (updated) {
						
						 
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "Capteur fumé configuré", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        this.add(btnConf);
		   
		 
		
	}

}
