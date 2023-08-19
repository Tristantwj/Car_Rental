package gui;

import javax.swing.JPanel;
import controller.MainFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StaffReg extends JPanel {
    private MainFrame main;
    private JLabel title;
    private JLabel user;
    private JLabel pass;
    private JLabel userNotif;
    private JTextField username;
    private JPasswordField password; 
    private JButton register;
    private JButton back;
	private Timer timer;
	private JRadioButton showPassword;
	private JLabel lblAdminCode;
	private JPasswordField confirmpassword;

    public StaffReg(MainFrame main) {
        this.main = main;
        setBounds(0, 0, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));

        title = new JLabel("Staff Register", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 92, 900, 60);
        add(title);

        user = new JLabel("Username:", JLabel.LEFT);
        user.setFont(new Font("Tahoma", Font.PLAIN, 24));
        user.setBounds(235, 184, 133, 47);
        add(user);

        pass = new JLabel("Password:", JLabel.LEFT);
        pass.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pass.setBounds(235, 275, 133, 47);
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
            public void actionPerformed(ActionEvent e) {
                String sn = username.getText();
                String sp = new String(password.getPassword());
                String csp = new String(confirmpassword.getPassword());
                
                if(csp.equals("staff")){
                	if (main.getController().isStaffUsernameAvailable(sn)) {
                		if (sp.length() >= 8) {
                			userNotif.setVisible(false);
                			main.getController().addStaff(sn, sp);
                			
                	        String currentDirectory = System.getProperty("user.dir");
                	        
                	        String staffFilePath = currentDirectory + "\\excel_data\\Staff.xlsx";

                			main.getController().savestaffs(staffFilePath);
                			main.showStaffLogin();
                			
                		}
                		else{
                			userNotif.setText("Minimum 8 character password");
                            userNotif.setForeground(Color.BLACK);
                        	userNotif.setVisible(true);
                			username.setText("");
                			password.setText("");
                			confirmpassword.setText("");
                			timer.start();
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
                	userNotif.setText("Admin code is wrong");
                    userNotif.setForeground(Color.RED);
                	userNotif.setVisible(true);
            		username.setText("");
            		password.setText("");
            		confirmpassword.setText("");
            		timer.start();
                }
            }
        });
        add(register);

        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(357, 555, 186, 52);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.showStaffLogin();
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
        
        lblAdminCode = new JLabel("Admin Code: ");
        lblAdminCode.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblAdminCode.setBounds(209, 362, 142, 47);
        add(lblAdminCode);
        
        confirmpassword = new JPasswordField();
        confirmpassword.setFont(new Font("Tahoma", Font.PLAIN, 24));
        confirmpassword.setBounds(377, 361, 299, 49);
        add(confirmpassword);
    }
}
