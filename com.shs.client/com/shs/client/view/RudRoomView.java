package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.shs.commons.model.LBTitle;
import com.shs.commons.model.Room;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;

public class RudRoomView extends JPanel {
	private JPanel pSearchUpd;
	private SearchView searchView;
	private UpdateView updateView;
	private	DeleteView deleteView;
	private	ReadView readView;
	
	public RudRoomView() {
		super();
		this.setLayout(new BorderLayout());
		
		//Search and Update view
		pSearchUpd = new JPanel(new GridLayout(3, 1));
		searchView = new SearchView();
		deleteView = new DeleteView();
		updateView = new UpdateView();
		
		readView = new ReadView();
		
		pSearchUpd.add(searchView);
		pSearchUpd.add(updateView);
		pSearchUpd.add(deleteView);
		
		
		this.add(pSearchUpd, BorderLayout.NORTH);
		this.add(readView, BorderLayout.CENTER);
	}
	
	public class SearchView extends JPanel{
		
		private FormView formView;
		public SearchView() {
			super();
			this.setLayout(new BorderLayout());
			
			
		}
		
		public void createCols(List<Type_Room> listT, List<Wing_Room> listW) {
			Map<String, String> cols = new LinkedHashMap<>();
			cols.put("ONLY BY ID","");cols.put("BY FLOOR","");cols.put("BY ROOM NUMBER","");cols.put("BY M²","");
			cols.put("BY DOORS","");cols.put("BY WINDOWS","");
			ArrayList<String> buttons = new ArrayList<>();
			buttons.add("RESEARCH");buttons.add("RESEARCH ALL");
			
			formView = new FormView("Research Secured Room", cols, buttons, new ArrayList<String>(),listT, listW, "h", true,false);
			this.add(formView, BorderLayout.CENTER);
		}	
		public FormView getFormView() {
			return this.formView;
		}
		
	}
	
	public class UpdateView extends JPanel{
		private FormView formView;
		
		public UpdateView() {
			super();
			this.setLayout(new BorderLayout());
			
		}
		
		public void createCols(List<Type_Room> listT, List<Wing_Room> listW) {
			Map<String, String> cols = new LinkedHashMap<>();
			cols.put("ID","");cols.put("FLOOR","");cols.put("ROOM NUMBER","");cols.put("M²","");
			cols.put("DOORS","");cols.put("WINDOWS","");
			ArrayList<String> buttons = new ArrayList<>();
			buttons.add("UPDATE");
			
			ArrayList<String> labels = new ArrayList<>();
			
			formView = new FormView("Update Secured Room", cols, buttons,labels,listT, listW, "h",true,false);
			this.add(formView, BorderLayout.CENTER);
		}
		
		public FormView getFormView() {
			return formView;
		}
	}
	
	public class DeleteView extends JPanel{
		private FormView formView;
		
		public DeleteView() {
			super();
			this.setLayout(new BorderLayout());
			Map<String, String> cols = new LinkedHashMap<>();
			cols.put("ID","");
			
			ArrayList<String> buttons = new ArrayList<>();
			buttons.add("DELETE");buttons.add("DELETE ALL");
			
			ArrayList<String> labels = new ArrayList<>();
			ArrayList<Type_Room> list1 = new ArrayList<>();
			ArrayList<Wing_Room> list2 = new ArrayList<>();
			formView = new FormView("Delete Secured Room", cols, buttons,labels,list1, list2, "h",true,false);
			this.add(formView, BorderLayout.CENTER);
		}

		public FormView getFormView() {
			return formView;
		}
	}
	
	public class ReadView extends JPanel{
		private List<ElementRead> elems;
		private LBTitle titleRead;
		private JPanel pElem;
		private JScrollPane scrollElem;
		public ReadView() {
			super();
			this.setLayout(new BorderLayout());
			//title			
			titleRead =new LBTitle("Display Secured Room");
			this.add(titleRead, BorderLayout.NORTH);
			
			//Elements
			pElem = new JPanel();
			pElem.setLayout(new GridLayout(200, 1));
			elems = new ArrayList<>();
			scrollElem = new JScrollPane(pElem,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			this.add(scrollElem, BorderLayout.CENTER);
			
		}
		
		public void setView(List<Room> rooms) {System.out.println("ok"+rooms);
			Collections.sort(rooms);
			elems.clear();
			pElem.removeAll();
			scrollElem.remove(pElem);
			this.remove(scrollElem);
			
			if(rooms.size()>1) {
				for (int i = 0; i < rooms.size(); i++) {
					elems.add(new ElementRead(rooms.get(i),false));
					pElem.add(elems.get(i));
				}
			}
			else {
				elems.add(new ElementRead(rooms.get(0),false));
				pElem.add(elems.get(0));
			}
			scrollElem = new JScrollPane(pElem,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(scrollElem);
			this.revalidate();
		}
		
		public class ElementRead extends JPanel{
			private FormView formView;
			private Map<String, String> cols;
			String room_num;
			public ElementRead(Room room, boolean title) {
				super();
				this.setLayout(new BorderLayout());
				cols = new LinkedHashMap<>();
				cols.put("ID",""+room.getId());cols.put("FLOOR",""+room.getFloor());
				cols.put("ROOM NUMBER",""+room.getRoom_number());
				cols.put("TYPE",""+room.getType_room().getName());
				cols.put("WING",""+room.getWing_room().getName());
				cols.put("M²",""+room.getM2());
				cols.put("DOORS",""+room.getNb_doors());
				cols.put("WINDOWS",""+room.getNb_windows());
				
				ArrayList<String> buttons = new ArrayList<>();
				ArrayList<String> labels = new ArrayList<>();
				ArrayList<Type_Room> list1 = new ArrayList<>();
				ArrayList<Wing_Room> list2 = new ArrayList<>();
				labels.add("ID");labels.add("FLOOR");labels.add("ROOM NUMBER");
				labels.add("TYPE");labels.add("WING");
				labels.add("M²");labels.add("DOORS");labels.add("WINDOWS");
				
				formView = new FormView("Read Secured Room", cols, buttons,labels,list1, list2, "h",title,false,15,18,20);
				this.add(formView, BorderLayout.CENTER);
			}
			
			public FormView getFormView() {
				return formView;
			}
			public Map<String, String> getCols(){
				return cols;
			}
		}
		
		public void AddJBDeleteListnerReadView(ActionListener act) {
			for (int i = 0; i < elems.size(); i++) {
				elems.get(i).getFormView().addJBListner(act);
			}
		}

		public List<ElementRead> getElems() {
			return elems;
		}

		public LBTitle getTitle() {
			return titleRead;
		}

		public int getIdTupleByButton(Object source) {
			int id=0;
			for (int i = 0; i < elems.size(); i++) {
				List<JButton> buttons= elems.get(i).getFormView().getButtons();
				for  (JButton b : buttons) {
					if(b==source) {
						id=elems.get(i).getFormView().getIDtuple();
					}
				}
			}
			return id;
		}
		
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public UpdateView getUpdateView() {
		return updateView;
	}

	public ReadView getReadView() {
		return readView;
	}

	public DeleteView getDeleteView() {
		return deleteView;
	}



	
}
