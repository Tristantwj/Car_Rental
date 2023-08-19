package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import controller.MainFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class StaffLogin extends JPanel{
	private MainFrame main;
	private JLabel title;
	private JLabel user;
	private JLabel pass;
	private JLabel userNotif;
	private JTextField username;
	private JPasswordField password;
	private JButton login;
	private JButton register;
	private JButton back;
	private Timer timer;
	private JRadioButton showPassword;
	
	public StaffLogin(MainFrame main) {
		this.main = main;
		setBounds(0, 0, 900, 650);
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		
		title = new JLabel("Staff Login");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 92, 900, 60);
		add(title);
		
		user = new JLabel("Username:");
		user.setFont(new Font("Tahoma", Font.PLAIN, 24));
		user.setBounds(235, 184, 127, 47);
		add(user);
		
		pass = new JLabel("Password:");
		pass.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pass.setBounds(235, 275, 135, 47);
		add(pass);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 24));
		username.setBounds(377, 184, 299, 49);
		add(username);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 24));
		password.setBounds(377, 274, 299, 49);
		add(password);
		
		timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNotif.setVisible(false);
                timer.stop();
            }
        });
		
		login = new JButton("Login");
		login.setFont(new Font("Tahoma", Font.PLAIN, 24));
		login.setBounds(235, 389, 186, 52);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = username.getText();
	            String pass = new String(password.getPassword());

	            if (main.getController().verifyStaff(user, pass)) {
	            	main.showListings();
	            	} else {
	                	userNotif.setText("Wrong Username or Password");
	                    userNotif.setVisible(true);
	                    timer.start();
	                }
	            }
	        });
		add(login);
		
		back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 24));
		back.setBounds(357, 454, 186, 52);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showMenu();
			}
		});
		add(back);
		
		register = new JButton("Register");
		register.setFont(new Font("Tahoma", Font.PLAIN, 24));
		register.setBounds(490, 389, 186, 52);
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.showStaffReg();
			}
		});
		add(register);
		
		showPassword = new JRadioButton("Show Password");
        showPassword.setBounds(684, 280, 127, 47);
        showPassword.setBackground(new Color(173, 216, 230));
        showPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPassword.isSelected()) {
                    password.setEchoChar((char) 0); 
                } else {
                    password.setEchoChar('\u25cf'); 
                }
            }
        });
		add(showPassword);
		
		userNotif = new JLabel("", JLabel.CENTER);
        userNotif.setFont(new Font("Tahoma", Font.BOLD, 30));
        userNotif.setBounds(0, 334, 900, 45);
        userNotif.setVisible(false);
        add(userNotif);
	}
}
