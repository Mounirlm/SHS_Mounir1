package com.shs.client.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;

import com.shs.client.model.UserClientHandler;
import com.shs.client.view.SHSView;
import com.shs.commons.model.User;


public class LoginController implements ActionListener{
	private SHSView view;
	private UserClientHandler servH;
	public LoginController(SHSView v) throws UnknownHostException, IOException {
		this.view = v;
		this.servH = new UserClientHandler();
		view.getConnectionPanel().getFormView().addJBListner(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton ){
			String[] form = new String[2];
			form[0] = view.getConnectionPanel().getFormView().getJtf(0).getText();
			form[1] = new String(view.getConnectionPanel().getFormView().getjPass().getPassword());
			List<User> users = new ArrayList<>();
			String message=null;
			try {
				users= login(form);
				
				if(!users.isEmpty()) {
					message = "Connection Succesful !";
					view.getConnectionPanel().getMessage().setText(message);
					view.getConnectionPanel().getMessage().setForeground(Color.GREEN);
					view.getCd().show(view.getFrame().getContentPane(), "appPanel");
				}else {
					message = "Connection failed : no user was found with this email and password";
					view.getConnectionPanel().getMessage().setText(message);
					view.getConnectionPanel().getMessage().setForeground(Color.RED);
				}
			} catch (Exception e1) {
				message =e1.getMessage();
				view.getConnectionPanel().getMessage().setText(message);
				view.getConnectionPanel().getMessage().setForeground(Color.RED);
			}
			
			
		}
		
	}
	private List<User> login(String[] form) throws Exception {
		if(form[0].isEmpty() || form[1].isEmpty())
			throw new Exception("Empty");
		if(!isEmail(form[0]))
			throw new Exception("Email field must be an email");
		
		User user = new User();
		user.setEmail(form[0]);
		user.setPassword(form[1]);
		return servH.searchUserToServer(user);
	}
	private boolean isEmail(String email) {
		if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$", email.toLowerCase()) == true) {
			return true;
		}else {
			return false;
		}
	}
}
