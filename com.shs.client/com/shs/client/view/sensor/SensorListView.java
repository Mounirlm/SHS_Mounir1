package com.shs.client.view.sensor;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.shs.commons.model.Sensor;
import com.shs.commons.model.Type_Sensor;

public class SensorListView extends JPanel {
	
	private List<Sensor> sensors;
	
	public SensorListView(List<Sensor> pSensors) {
      super(new GridLayout(1,0));
// 
//        String[] columnNames = {"ID",
//        						"Sensor Name",
//                                "IP address",
//                                "MAC address",
//                                "Date setup",
//                                "Status",
//                                "Installed",
//                                "Position",
//                                "Price",
//                                "Room",
//                                "Type Sensor",
//                                "Scope Sensor"};
//        
//        sensors = pSensors != null ? pSensors : new ArrayList<>();
//        List<Object []> data = sensors.stream().map(s -> s.getAsArray()).collect(Collectors.toList());
// 
//        final JTable table = new JTable(data.toArray(new Object[][] {}), columnNames);
//        table.setPreferredScrollableViewportSize(new Dimension(900, 500));
//        table.setFillsViewportHeight(true);
// 
//       
//        table.setCellSelectionEnabled(true);
//        ListSelectionModel cellSelectionModel = table.getSelectionModel();
//        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//            int sensorId = 0;
//
//              int[] selectedRow = table.getSelectedRows();
//              int[] selectedColumns = table.getSelectedColumns();
//
//              for (int i = 0; i < selectedRow.length; i++) {
//                for (int j = 0; j < selectedColumns.length; j++) {
//                	sensorId = (int) table.getValueAt(selectedRow[i], 0);
//                }
//              }
//              Type_Sensor type = getSensorType(sensorId);
//              SensorTypeView sensorConfigurationView;
//			try {
//				
//				sensorConfigurationView = new SensorTypeView(type);
//				sensorConfigurationView.setVisible(true);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//              
//            }
//
//          });
//        
//        //Create the scroll pane and add the table to it.
//        JScrollPane scrollPane = new JScrollPane(table);
// 
//        //Add the scroll pane to this panel.
//        add(scrollPane);
//    }
//	
//	private Type_Sensor getSensorType(int sensorId) {
//		return sensors.stream().filter(s -> s.getId() == sensorId).findFirst().get().getFk_type_sensor();
//	}
      //test
      sensors = pSensors != null ? pSensors : new ArrayList<>();
      for(Sensor s: sensors) {
		System.out.println("s");
      }
	}
}
