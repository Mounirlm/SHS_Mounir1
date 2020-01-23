package com.shs.client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.shs.client.controller.BuildingController;
import com.shs.commons.model.Building;
import com.shs.commons.model.Floor;
import com.shs.commons.model.Room;
import com.shs.commons.model.Sensor;
import com.shs.commons.model.Sensor.SensorState;

public class MapPanelView extends JPanel implements MouseListener, MouseMotionListener {

	ArrayList<IUpdatable> updatables;
	Building building;
	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	Thread thread;
	boolean isActive = false;
	Floor current_floor = null;
	BuildingController bc;
	// Log log;
	Sensor sensorToUpdate;

	FormStockView fs = null;
	JOptionPane jop1;

	public MapPanelView(Building bg) {
		this.building = bg;
		updatables = new ArrayList<>();
		this.addMouseListener(this);
		this.setActive(true);
		this.addMouseMotionListener(this);
		jop1 = new JOptionPane();

		// DropTarget est un objet associé à un composant indiquant que celui-ci peut
		// recevoir un DnD
		// on active le drop sur le PLAN

		this.setTransferHandler(new MyTransferHandler());

		this.setDropTarget(new DropTarget() {

			@SuppressWarnings("static-access")
			public void drop(DropTargetDropEvent dtde) {
				// dtde:Appelé lors d'un drop
				dtde.acceptDrop(dtde.getDropAction());
				Sensor selected = null;
				Room room = null;
				try {
					selected = (Sensor) dtde.getTransferable().getTransferData(Sensor.SENSOR_DATA_FLAVOR);

				} catch (Exception ex) {
					System.out.println(ex);
				}

				if (selected == null)
					return;
				boolean b = false;

				for (Room r : current_floor.getRoom()) {

					if (r.isPointInRoom(dtde.getLocation())) {

						// Test if exist two sensors of same type in room <15 m2
						if (r.isTypeSensorInRoom(selected) && r.getM2() <= 15) {
							selected = null;
							JOptionPane.showMessageDialog(MapPanelView.this,
									"Please, You can not install 2 sensors of same type in a room with a surface area less than or equal to 15 square meters",
									"", JOptionPane.WARNING_MESSAGE);

							return;

						}

						// If no window exist in room, avoid mapping window_sensor and displaying
						// message

						else if (r.getNb_windows() == 0
								&& selected.getFk_type_sensor().getName().equals("window_sensor")) {

							selected = null;
							MapPanelView.this.jop1.showMessageDialog(MapPanelView.this,
									" There is no Window in this room.", "", JOptionPane.WARNING_MESSAGE);
							return;

						}

						else {

							room = r;
							room.getSensors().add(selected);
							selected.setX((int) dtde.getLocation().getX());
							selected.setY((int) dtde.getLocation().getY());
							selected.setFk_room_id(r.getId());
							selected.setState(SensorState.Marche);
							selected.setInstalled(true);
							Date dte = new Date();
							selected.setDate_setup(dte);

							try {
								bc.update(selected);

							} catch (IOException | SQLException e) {

								e.printStackTrace();
							}
							selected.setFk_room(room);
							MapPanelView.this.building.getStock().getSensors().remove(selected);
							MapPanelView.this.getCurrent_floor().getSensors().add(selected);
							activateListener();
							b = true;
						}
					}
				}

				if (b == false) {
					selected = null;
					JOptionPane.showMessageDialog(MapPanelView.this, "Please, Map sensor inside room.", "",
							JOptionPane.WARNING_MESSAGE);

					return;
				}

			}

		});

	}

	public void setActive(boolean active) {
		if (active == this.isActive)
			return; //

		this.isActive = active;
		if (active == true) {
			thread = new Thread() {
				@Override
				public void run() {
					super.run();
					while (MapPanelView.this.isActive) {
						MapPanelView.this.validate();
						MapPanelView.this.repaint();
						try {
							Thread.sleep((long) 100);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
					}

				}

			};
			thread.start();
		}

	}

	public void paint(Graphics g) {
		super.paintComponent(g);

		if (current_floor == null)
			return;

		g.drawImage(current_floor.getFloorImage(), 0, 0, null);

		for (Room r : current_floor.getRoom()) {

			for (Sensor s : r.getSensors()) {

				g.drawRect(s.getX(), s.getY(), 30, 30);

				if (s.getState().equals(SensorState.Marche)) {
					g.setColor(Color.GREEN);

				}

				else if (s.getState().equals(SensorState.Alert)) {
					int millis = (int) (System.currentTimeMillis() / 1000);

					if (millis % 2 == 0) {
						g.setColor(Color.RED);

					} else {
						g.setColor(Color.DARK_GRAY);
					}

				} else {
					g.setColor(Color.ORANGE);
				}

				g.fillRect(s.getX() + 1, s.getY() + 1, 29, 29);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		for (Room r : current_floor.getRoom()) {

			for (Sensor s : r.getSensors()) {

				if (e.getX() > s.getX() && e.getX() < s.getX() + 30 && e.getY() > s.getY()
						&& e.getY() < s.getY() + 30) {
					
					
					s.setFk_room_id(r.getId());
					
					if (s.getState() == SensorState.Marche) {
						s.setState(SensorState.Alert);
						
					} else if (s.getState() == SensorState.Alert) {
						s.setState(SensorState.Arret);

					} else {
						s.setState(SensorState.Marche);
					}
					try {
						bc.update(s);
					} catch (IOException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				
				}
			}
		}

	}

	public Date getDate() {
		DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = new Date();

		return d;
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		for (Room r : current_floor.getRoom()) {

			for (Sensor s : r.getSensors()) {

				if (e.getX() > s.getX() && e.getX() < s.getX() + 30 && e.getY() > s.getY()
						&& e.getY() < s.getY() + 30) {
					setToolTipText(s.toString() + " , IdRoom : " + r.getId());

				}

			}
		}
	}

	public Floor getCurrent_floor() {
		return current_floor;
	}

	public void setCurrent_floor(Floor current_floor) {
		this.current_floor = current_floor;
	}

	public void addUpdatableListener(IUpdatable listener) {
		updatables.add(listener);
	}

	public void removeUpdatableListener(IUpdatable listener) {
		updatables.remove(listener);
	}

	public void activateListener() {
		for (IUpdatable i : updatables) {
			i.update();

		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
