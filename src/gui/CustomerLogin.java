package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import controller.Controller;
import controller.MainFrame;
import data.Customer;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JButton;

public class CustomerLogin extends JPanel {
    private MainFrame main;
    private Controller controller;
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

    public CustomerLogin(MainFrame main) {
        this.main = main;
        this.controller = main.getController();
        setBounds(0, 0, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));
        
        title = new JLabel("Customer Login", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 92, 900, 60);
        add(title);

        user = new JLabel("Username:", JLabel.LEFT);
        user.setFont(new Font("Tahoma", Font.PLAIN, 24));
		user.setBounds(235, 185, 130, 47);
        add(user);

        
        pass = new JLabel("Password:", JLabel.LEFT);
        pass.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pass.setBounds(235, 275, 130, 47);
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
		login.setBounds(244, 389, 186, 52);
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				String user = username.getText();
				char[] passwordChars = password.getPassword();
		        String sp = new String(passwordChars);
                
                if (main.getController().verifyCustomer(user, sp)){
    				Controller.clearFields(username, password);
                    userNotif.setVisible(false);
                    Customer loggedInCustomer = main.getController().getCustomerByUsername(user);
                    main.showBookCar(loggedInCustomer);
                } 
                else{
                	userNotif.setText("Wrong Username or Password");
                	userNotif.setVisible(true);
                	timer.start();
                }
            }
        });
        add(login);

        register = new JButton("Register");
        register.setFont(new Font("Tahoma", Font.PLAIN, 24));
		register.setBounds(476, 389, 186, 52);
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Controller.clearFields(username, password);
                main.showCustomerRegister();
            }
        });
        add(register);

        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(357, 457, 186, 52);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Controller.clearFields(username, password);
                main.showMenu();
            }
        });
        add(back);
        
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
