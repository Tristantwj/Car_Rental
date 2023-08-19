package gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import controller.Controller;
import controller.MainFrame;
import data.Customer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;

public class CustomerRegister extends JPanel {
    private MainFrame main;
    private Controller controller;
    private JLabel title;
    private JLabel user;
    private JLabel pass;
    private JLabel userNotif;
    private static JTextField username;
    private JPasswordField password;
    private JButton register;
    private JButton back;
	private JRadioButton showPassword;
	private JLabel lblComfirmPassword;
	private JPasswordField confirmpassword;
	private Timer timer;

    public CustomerRegister(MainFrame main, Controller controller) {
        this.main = main;
        this.controller = controller;
        setBounds(0, 0, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));

        title = new JLabel("Customer Register", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 92, 900, 60);
        add(title);

        user = new JLabel("Username:", JLabel.LEFT);
        user.setFont(new Font("Tahoma", Font.PLAIN, 24));
        user.setBounds(235, 184, 138, 47);
        add(user);

        pass = new JLabel("Password:", JLabel.LEFT);
        pass.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pass.setBounds(235, 275, 120, 47);
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

        register = new JButton("Register");
        register.setFont(new Font("Tahoma", Font.PLAIN, 24));
        register.setBounds(357, 481, 186, 52);
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ars0) {
                String cn = username.getText();
                char[] passwordChars = password.getPassword();
		        String cp = new String(passwordChars);
		        char[] ConpasswordChars = confirmpassword.getPassword();
		        String cpp = new String(ConpasswordChars);
                int p = 0;
                
                if (cp.equals(cpp)){
                	if (main.getController().isCustomerUsernameAvailable(cn)) {
                		
                		if (cp.length() >= 8) {
                			userNotif.setVisible(false);
                			controller.addCustomer(cn, cp, p);
                			main.showCustomerLogin();
                			
                	        String currentDirectory = System.getProperty("user.dir");

                	        String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";

                			controller.savecustomers(customerFilePath);
                			
                		}
                		else {
                			userNotif.setText("Minimum 8 character password");
                            userNotif.setForeground(Color.BLACK);
                        	userNotif.setVisible(true);
                        	timer.start();
                			username.setText("");
                			password.setText("");
                			confirmpassword.setText("");
                		} 
                		
                	} else {
                		userNotif.setText("Username already exists, Try Again.");
                        userNotif.setForeground(Color.BLACK);
                    	userNotif.setVisible(true);
                    	timer.start();
                		username.setText("");
                		password.setText("");
                		confirmpassword.setText("");
                	}
                }
                else{
                	userNotif.setText("Password does not match, try again.");
                    userNotif.setForeground(Color.BLACK);
                	userNotif.setVisible(true);
                	timer.start();
                	username.setText("");
        			password.setText("");
        			confirmpassword.setText("");
                }
            }
        });
        add(register);

        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(357, 555, 186, 52);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.showCustomerLogin();
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
                    confirmpassword.setEchoChar((char) 0); 
                } else {
                    password.setEchoChar('\u25cf'); 
                    confirmpassword.setEchoChar('\u25cf'); 
                }
            }
        });
		add(showPassword);

        userNotif = new JLabel("", JLabel.CENTER);
        userNotif.setFont(new Font("Tahoma", Font.BOLD, 30));
        userNotif.setBounds(0, 423, 900, 45);
        userNotif.setVisible(false);
        add(userNotif);
        
        lblComfirmPassword = new JLabel("Comfirm Password: ");
        lblComfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblComfirmPassword.setBounds(135, 363, 220, 47);
        add(lblComfirmPassword);
        
        confirmpassword = new JPasswordField();
        confirmpassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
        confirmpassword.setBounds(377, 361, 299, 49);
        add(confirmpassword);
    }
}
