package com.shs.client.view;

import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

public interface AnalyzePanel {

	void updateInfoGUI(Map<String, Integer> indicators);

	void addSelectComboBoxListener(ActionListener actionComboBox);
	
	
}
