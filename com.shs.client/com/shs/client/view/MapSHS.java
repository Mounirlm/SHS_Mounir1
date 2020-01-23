package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import com.shs.client.controller.BuildingController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Sensor.SensorState;
import com.shs.commons.model.Stock;

public class MapSHS extends JFrame implements IUpdatable {

	private JPanel panN;
	private JPanel panW;
	private JPanel panE;
	private JPanel panS;

	private JLabel jLS;

	private JPanel panFloor;
	private JLabel floorLabel;
	private JComboBox jcomboFloor;
	private ArrayList<Floor> floors;
	private JList itemsListStock;

	private JButton addSensorToStock;
	private JLabel StockList;
	private JLabel mapTitle;
	private JLabel jLSensorId;
	private JTextField jtxSensorId;
	private JButton jbRemoveToStockSensor;
	private JButton jbRefresh;
	private JPanel panInfoSensorInstalledInFloor;
	private JButton jbSearchInfoSensorInFloor;
	private JLabel jLInfoSensor;

	private final String[] entetes = { "Name", "Type", "X_position", "Y_position", "Price", "@Mac", "@IP", "State",
			"Room_Number", "Square_Meter", "Windows", "Doors" };

	private JLabel labels[] = new JLabel[12];

	MapPanelView plan;
	Building building;
	BuildingController buildingController;
	Floor floor;
	Stock stock;

	public MapSHS(String name) {
		super(name);
	}

	public MapSHS() throws Exception {

		super("SHS AutonHome");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setSize(1200, 650);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		try {
			buildingController = new BuildingController();

			building = buildingController.getBuilding();

			// add sensor to stock

			stock = new Stock();
			stock.setSensors(BuildingController.getSensorsNotInstalled());
			building.setStock(stock);

		} catch (IOException | ClassNotFoundException | SQLException e2) {

			e2.printStackTrace();
		}

		plan = new MapPanelView(building);

		plan.addUpdatableListener(this);// pattern Observer Observable
		// plan ne doit pas avoir de lien avec ihm donc ihm s'inscrit comme listener du
		// plan

		panN = new JPanel();
		panW = new JPanel();
		panE = new JPanel();
		panS = new JPanel();

		jLS = new JLabel();
		jLS.setText("AutonHome SHS Copyright");
		jLS.setForeground(Color.WHITE);
		jLS.setHorizontalAlignment(JLabel.CENTER);
		jLS.setFont(new Font("Arial", Font.BOLD, 13));

		panS.setBackground(ColorsApp.getBgThem());
		panS.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panS.add(jLS);

		panFloor = new JPanel();
		panFloor.setLayout(new GridLayout(3, 1));

		jLInfoSensor = new JLabel("SENSOR INFO", SwingConstants.CENTER);
		Border bord = BorderFactory.createLineBorder(Color.BLACK);
		jLInfoSensor.setBorder(bord);
		jLInfoSensor.setForeground(Color.BLACK);
		jLInfoSensor.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		jLInfoSensor.setHorizontalAlignment(JLabel.CENTER);

		mapTitle = new JLabel();
		mapTitle.setText("MAP");
		mapTitle.setForeground(Color.WHITE);
		mapTitle.setHorizontalAlignment(JLabel.CENTER);
		mapTitle.setFont(new Font("Arial", Font.BOLD, 17));

		panN.setBackground(ColorsApp.getBgThem());
		panN.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panN.add(mapTitle);

		panW.setLayout(new GridLayout(2, 1));
		panW.setBorder(BorderFactory.createLineBorder(Color.WHITE));

		panE.setLayout(new GridLayout(8, 1));

		panInfoSensorInstalledInFloor = new JPanel();
		panInfoSensorInstalledInFloor.setLayout(new GridLayout(13, 1));
		panInfoSensorInstalledInFloor.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		jbSearchInfoSensorInFloor = new JButton("SEARCH INFO");

		for (int i = 0; i < entetes.length; i++) {
			labels[i] = new JLabel(" " + entetes[i] + " : ");
			panInfoSensorInstalledInFloor.add(labels[i]);

		}

		StockList = new JLabel("MAPPING BY DRAG AND DROP", SwingConstants.CENTER);
		StockList.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		StockList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		StockList.setBackground(ColorsApp.getBgThem());

		addSensorToStock = new JButton("STOCK VIEW");

		addSensorToStock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				FormStockView fs = new FormStockView(building);
				fs.setVisible(true);
				update();
			}
		});

		floors = new ArrayList<Floor>();

		floors = building.getFloor();

		floorLabel = new JLabel(" FLOOR ", SwingConstants.CENTER);
		floorLabel.setHorizontalAlignment(JLabel.CENTER);
		floorLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		floorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		floorLabel.setForeground(Color.BLACK);
		jcomboFloor = new JComboBox<Floor>();

		for (int i = 0; i < floors.size(); i++) {
			jcomboFloor.addItem(floors.get(i));
		}

		jcomboFloor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Floor f = (Floor) jcomboFloor.getSelectedItem();
				setFloor(f);

				updateFloor();

			}
		});

		jLSensorId = new JLabel("SENSOR BY ID", SwingConstants.CENTER);
		jLSensorId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		jLSensorId.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
		jLSensorId.setHorizontalAlignment(JLabel.CENTER);

		panFloor.add(floorLabel);
		panFloor.add(jcomboFloor);
		panFloor.add(jLInfoSensor);
		panW.add(panFloor);
		panW.add(panInfoSensorInstalledInFloor);

		jtxSensorId = new JTextField();
		jtxSensorId.setToolTipText("Please Enter an Integer as ID sensor.");
		jbRemoveToStockSensor = new JButton("REMOVE SENSOR TO STOCK");
		jbRemoveToStockSensor.setPreferredSize(new Dimension(40, 40));
		jbRefresh = new JButton("REFRESH");
		jbRefresh.setPreferredSize(new Dimension(40, 40));

		jtxSensorId.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char c = evt.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
					evt.consume();
					getToolkit().beep();
					JOptionPane.showMessageDialog(MapSHS.this, "Field ID must be an Integer");
				}
			}
		});

		jbRemoveToStockSensor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Floor f = (Floor) jcomboFloor.getSelectedItem();
				updateFloor();
				Sensor selected = null;
				Room roomSelected = null;

				if (jtxSensorId.getText().equals("")) {
					JOptionPane.showMessageDialog(MapSHS.this, "Field ID must be an Integer and not be blank");
				} else {
					try {

						if (jtxSensorId.getText() != "") {
							boolean b = false;
							for (Sensor s : f.getSensors()) {

								if (s.getId() == Integer.parseInt(jtxSensorId.getText())) {

									if (s.getState().equals(SensorState.Marche)
											|| s.getState().equals(SensorState.Alert)) {
										JOptionPane.showMessageDialog(MapSHS.this, "Please, Stop the sensor before.",
												"", JOptionPane.WARNING_MESSAGE);
										return;

									} else {
										selected = s;
										jtxSensorId.setText("");
										b = true;
										break;
									}
								}

							}

							if (b == false) {

								JOptionPane.showMessageDialog(MapSHS.this,
										"Error ID : this sensor is not present in floor");
								jtxSensorId.setText("");
								return;
							} else {

								for (Room r : f.getRoom()) {
									if (r.getId().equals(selected.getFk_room().getId())) {
										roomSelected = r;
										System.out.println(roomSelected.getId());
										break;
									}

								}

								roomSelected.getSensors().remove(selected);
								selected.setX(null);
								selected.setY(null);
								selected.setFk_room_id(null);
								selected.setInstalled(false);

								Room ro = new Room();
								ro = null;
								selected.setFk_room(ro);
								try {
									buildingController.update(selected);

								} catch (IOException | SQLException e1) {

									e1.printStackTrace();
								}
								plan.getCurrent_floor().getSensors().remove(selected);
								plan.building.getStock().getSensors().add(selected);

								update();

							}
						}

					} catch (NumberFormatException e3) {

						e3.printStackTrace();
					} catch (HeadlessException e3) {

						e3.printStackTrace();
					}

				}
			}

		});

		jbSearchInfoSensorInFloor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Floor f = (Floor) jcomboFloor.getSelectedItem();
				Sensor selected = null;
				Room roomSelected = null;

				if (jtxSensorId.getText().equals("")) {
					JOptionPane.showMessageDialog(MapSHS.this, "Field ID must be an Integer and not be blank");
				} else {

					if (jtxSensorId.getText() != "") {
						boolean b = false;

						for (Sensor s : f.getSensors()) {

							if (s.getId() == Integer.parseInt(jtxSensorId.getText())) {
								selected = s;
								jtxSensorId.setText("");
								b = true;
								break;

							}

						}

						if (b == false) {

							JOptionPane.showMessageDialog(MapSHS.this,
									"Error ID : this sensor is not present in floor");
							jtxSensorId.setText("");
							return;
						} else {

							String info[] = { selected.getSensor_name(), selected.getFk_type_sensor().getName(),
									selected.getX().toString(), selected.getY().toString(),
									selected.getPrice().toString(), selected.getMac_address(), selected.getIp_address(),
									selected.getState().name(), selected.getFk_room().getRoom_number().toString(),
									selected.getFk_room().getM2().toString(),
									selected.getFk_room().getNb_windows().toString(),
									selected.getFk_room().getNb_doors().toString() };

							for (int i = 0; i < info.length; i++) {
								labels[i].setText(" " + entetes[i] + " : " + info[i]);
							}

						}

					}

				}
			}
		});

		jbRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Floor f = (Floor) jcomboFloor.getSelectedItem();

				if (f == null) {

					JOptionPane.showMessageDialog(MapSHS.this, "Please : Select an floor first ");
				} else {

					try {

						building = buildingController.getBuilding();

						f.setRoom((buildingController.getRoomListInFloor(f.getId())));
						buildingController.setSensorInROOM(f);

						ArrayList<Sensor> sensor2 = new ArrayList<>();

						for (Room r : f.getRoom()) {

							sensor2.addAll(r.getSensors());
						}

						itemsListStock.setListData(buildingController.getSensorsNotInstalled().toArray());

						for (int i = 0; i < labels.length; i++) {
							labels[i].setText(" " + entetes[i] + " : " + "");
						}

					} catch (IOException e2) {

						e2.printStackTrace();
					}

				}

			}
		});

		itemsListStock = new JList<Sensor>(building.getStock().getSensorArray());
		JScrollPane scrollPane3 = new JScrollPane(itemsListStock, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		itemsListStock.setDragEnabled(true);
		itemsListStock.setTransferHandler(new MyTransferHandler());

		panE.add(StockList);
		panE.add(scrollPane3);
		panE.add(addSensorToStock);
		panE.add(jLSensorId);
		panE.add(jtxSensorId);
		panE.add(jbSearchInfoSensorInFloor);
		panE.add(jbRemoveToStockSensor);
		panE.add(jbRefresh);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panN, BorderLayout.PAGE_START);
		this.getContentPane().add(panW, BorderLayout.LINE_START);
		this.getContentPane().add(plan, BorderLayout.CENTER);
		this.getContentPane().add(panE, BorderLayout.LINE_END);
		this.getContentPane().add(panS, BorderLayout.PAGE_END);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				MapView m = new MapView();
				m.setVisible(true);
			}
		});

	}

	// MapSHS implements IUpdatable
	public void update() {

		Floor f = (Floor) jcomboFloor.getSelectedItem();
		itemsListStock.setListData(building.getStock().getSensorArray());
	
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	public void updateFloor() {
		Floor f = this.floor;
		try {

			f.setRoom((buildingController.getRoomListInFloor(f.getId())));
			buildingController.setSensorInROOM(f);

			ArrayList<Sensor> sensor2 = new ArrayList<>();
			for (Room r : f.getRoom()) {

				sensor2.addAll(r.getSensors());
			}

			f.setSensors(sensor2);
			plan.setCurrent_floor(f);

		} catch (IOException e2) {

			e2.printStackTrace();
		}
	}
}
