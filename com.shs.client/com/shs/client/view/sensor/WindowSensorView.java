package com.shs.client.view.sensor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.shs.commons.model.Type_Sensor;

public class WindowSensorView extends SensorTypeGenericView {

	public WindowSensorView(Type_Sensor typeSensor) throws IOException, SQLException, Exception {
		super(typeSensor);
	}
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
		JLabel lblHeureDeb = new JLabel("Heure début");
		lblHeureDeb.setBounds(65, 31, 46, 14);
        this.add(lblHeureDeb);
         
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("16");
        comboBox.addItem("17");
        comboBox.addItem("18");
        comboBox.addItem("19");
        comboBox.addItem("20");
        comboBox.addItem("21");
        comboBox.addItem("22");
        comboBox.addItem("23");
        comboBox.addItem("00");
       
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox.setBounds(128, 28, 86, 20);
        this.add(comboBox);
         
        
        JLabel lblHeureFin = new JLabel("Heure fin");
        lblHeureFin.setBounds(65, 68, 46, 14);
        this.add(lblHeureFin);
         
        JComboBox comboBox_2 = new JComboBox();
      
        comboBox_2.addItem("6");
        comboBox_2.addItem("7");
        comboBox_2.addItem("8");
        comboBox_2.addItem("9");
        comboBox_2.addItem("10");
        comboBox_2.addItem("11");
        comboBox_2.addItem("12");
 
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox_2.setBounds(128, 65, 86, 20);
        this.add(comboBox_2);
		
		JLabel lblAlerte = new JLabel("Nombre d'alertes");
        lblAlerte.setBounds(65, 115, 46, 14);
        this.add(lblAlerte);
         
        JComboBox comboBox_3 = new JComboBox();
        comboBox_3.addItem("1");
        comboBox_3.addItem("2");
        comboBox_3.addItem("3");
        comboBox_3.addItem("4");
        comboBox_3.addItem("5");
       
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        comboBox_3.setBounds(128, 112, 247, 17);
        this.add(comboBox_3);
         
         
        JButton btnConf = new JButton("Configurer");
         
        btnConf.setBackground(Color.BLUE);
        btnConf.setForeground(Color.MAGENTA);
        btnConf.setBounds(65, 387, 89, 23);
        
        btnConf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	int alert = Integer.parseInt(comboBox_3.getSelectedItem().toString());
            	int heureDeb = Integer.parseInt(comboBox.getSelectedItem().toString());
            	int heureFin = Integer.parseInt(comboBox_2.getSelectedItem().toString());
            	Type_Sensor typeSensor = getSensorToUpdate();
            	typeSensor.setNb_alerts(alert);
            	typeSensor.setTrigger_point_max(heureDeb);
            	typeSensor.setTrigger_point_min(heureFin);
            	try {
					boolean updated = update(typeSensor);
					if (updated) {
						
						 
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "Capteur porte configuré", "Information", JOptionPane.INFORMATION_MESSAGE);
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
