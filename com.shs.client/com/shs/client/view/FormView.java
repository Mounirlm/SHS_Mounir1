package com.shs.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.shs.commons.model.LBTitle;
import com.shs.commons.model.Type_Room;
import com.shs.commons.model.Wing_Room;

public class FormView extends JPanel {
	private JTextField jtf;
	private JLabel lbVal;
	private JButton validateButton;
	private int sizeTitle=30, sizeLB=15, sizeVal=11, sizeButton=20;
	private Map<String, String> pArgs;
	private List<JButton> buttons;
	private LBTitle lbTitle;
	List<JTextField> jtfs;
	List<JLabel> lbVals;
	private List<String> labs;
	private JPanel pForm;
	private List<String> butLB;
	private JPanel pValidate;
	private int idTuple;
	private JPasswordField jPass;
	private boolean isPass;
	private JComboBox<Type_Room> combo_type;
	private JComboBox<Wing_Room> combo_wing;
	
	public FormView(String titleView,Map<String, String> tuples, List<String> but, List<String> labels,
			List<Type_Room> listT, List<Wing_Room> listW, String alignement, boolean title, boolean isPassW) {
		super();
		this.setLayout(new BorderLayout());
		pArgs = tuples;
		jtfs = new ArrayList<>();
		lbVals = new ArrayList<>();
		buttons = new ArrayList<>();
		labs=labels;
		butLB=but;
		this.isPass=isPassW;
		//if(isInteger(pArgs.get("ID"))) {idTuple = Integer.valueOf(pArgs.get("ID"));}
		
		
		if(title) {
			//title
			lbTitle =new LBTitle(titleView);
			this.add(lbTitle, BorderLayout.NORTH);
		}
		
		
		//Form
		if(alignement.equals("h") && listT.isEmpty() && listW.isEmpty()) 
			pForm = new JPanel(new GridLayout(1,tuples.size()+but.size()));
		else if (alignement.equals("v") && listT.isEmpty() && listW.isEmpty())
			pForm = new JPanel(new GridLayout(tuples.size()+but.size(),1));
		else if(alignement.equals("h") && !listT.isEmpty() && !listW.isEmpty()) 
			pForm = new JPanel(new GridLayout(1,tuples.size()+but.size()+2));//plus 2 combobox
		else if (alignement.equals("v") && !listT.isEmpty() && !listW.isEmpty())
			pForm = new JPanel(new GridLayout(tuples.size()+but.size()+2,1));
		
		
		createCols(listT,listW);
		
	}



	private void createCols(List<Type_Room> listT, List<Wing_Room> listW) {

		for (Map.Entry<String, String> entry : pArgs.entrySet()) {
			
				JLabel lb =new JLabel(entry.getKey());
				lb.setOpaque(true);
				lb.setBackground(ColorsApp.getBgThem());
				lb.setForeground(ColorsApp.getBgApp());
				lb.setHorizontalAlignment(JLabel.CENTER);
				lb.setFont(new Font("Arial", Font.BOLD, sizeLB));
				lb.setBorder(new LineBorder(Color.BLACK, 1));
				JPanel pCol = new JPanel(new BorderLayout());
				pCol.add(lb, BorderLayout.NORTH);
				boolean labelFound = false;
				for (int j = 0; j < labs.size(); j++) {
					if(labs.get(j)==entry.getKey()) {
						labelFound=true;
					}
				}
					if(labelFound) {
						lbVal = new JLabel(entry.getValue());
						lbVal.setFont(new Font("Arial", Font.BOLD, sizeVal));
						lbVal.setOpaque(true);
						lbVal.setBackground(ColorsApp.getBgApp());
						lbVal.setBorder(new LineBorder(Color.BLACK, 1));
						pCol.add(lbVal, BorderLayout.CENTER);
						lbVals.add(lbVal);
					}else {
						if(isPass && !jtfs.isEmpty()) {
							jPass = new JPasswordField(entry.getValue());
							jPass.setFont(new Font("Arial", Font.BOLD, sizeVal));
							pCol.add(jPass, BorderLayout.CENTER);
							isPass=false;
						}else {							
							jtf = new JTextField(entry.getValue());
							jtf.setFont(new Font("Arial", Font.BOLD, sizeVal));
							jtfs.add(jtf);
							pCol.add(jtf, BorderLayout.CENTER);
						}
					
				}
				pForm.add(pCol);
		}
		//fill combobox
		if(!listT.isEmpty() && !listW.isEmpty()) {
			combo_type = new JComboBox<Type_Room>();
			combo_wing = new JComboBox<Wing_Room>();
			combo_type.addItem(new Type_Room(0, "null"));
			combo_wing.addItem(new Wing_Room(0, "null"));
			
			for (Type_Room type_Room : listT) {
				combo_type.addItem(type_Room);
			}
			
			for (Wing_Room wing_Room : listW) {
				combo_wing.addItem(wing_Room);
			}
			//add comboboxes in form
			pForm.add(combo_type);
			pForm.add(combo_wing);
		}
		
		
		//add buttons in form
		for (int i = 0; i < butLB.size(); i++) {
			//Panel Valider
			pValidate = new JPanel();
			validateButton = new JButton(butLB.get(i));
			buttons.add(validateButton);
			validateButton.setBackground(ColorsApp.getBgThem());
			validateButton.setForeground(ColorsApp.getBgApp());
			validateButton.setHorizontalAlignment(JLabel.CENTER);
			validateButton.setFont(new Font("Arial", Font.BOLD, sizeButton));
			pValidate.add(butLB.get(i),validateButton);
			pForm.add(pValidate);
		}
		this.add(pForm, BorderLayout.CENTER);
	}





	public FormView(String title, Map<String, String> cols, ArrayList<String> buttons2, ArrayList<String> labels2,List<Type_Room> list1, List<Wing_Room> list2,
			String alignement2, boolean b,boolean isPassW,int i, int j, int k) {
		this(title,cols, buttons2,labels2, list1, list2, alignement2, b, isPassW);
		setSizeArgs(i, j, k);
	}
	
	

	public JTextField getJtf(int index) {
		return jtfs.get(index);
	}
	
	public void setSizeArgs(int lb, int val, int but) {
		sizeVal=val;
		for (int i = 0; i < jtfs.size() ;i++) {
			jtfs.get(i).setFont(new Font("Arial", Font.BOLD, sizeVal));
		}
		for (int i = 0; i < lbVals.size() ;i++) {
			lbVals.get(i).setFont(new Font("Arial", Font.BOLD, sizeVal));
		}
	}



	public void setTitle(String string) {
		this.lbTitle.setText(string);
		
	}



	public void addJBListner(ActionListener act) {
		for (int i = 0; i < buttons.size() ;i++) {
			buttons.get(i).addActionListener(act);
		}
		
	}



	public Map<String, String> getpArgs() {
		return pArgs;
	}



	public void setpArgs(Map<String, String> pArgs) {
		this.pArgs = pArgs;
		//pForm.removeAll();
		//createCols();
		lbVals.get(0).setText(pArgs.get("ID"));int i=0;
		for (Map.Entry<String, String> entry : pArgs.entrySet()) {
			if(i==0) {
				i++;
				continue;
			}
			if(i<jtfs.size()) {
				jtfs.get(i).setText(entry.getValue());
				i++;
			}
		}
		
	}



	public int getIDtuple() {
		// TODO Auto-generated method stub
		return idTuple;
	}
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}



	public List<JButton> getButtons() {
		return buttons;
	}



	public LBTitle getTitle() {
		return lbTitle;
	}



	public JPasswordField getjPass() {
		return jPass;
	}



	public JComboBox<Type_Room> getCombo_type() {
		return combo_type;
	}



	public JComboBox<Wing_Room> getCombo_wing() {
		return combo_wing;
	}
	
	
	
}
