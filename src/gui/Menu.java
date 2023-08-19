package gui;

import javax.swing.JPanel;

import controller.MainFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
public class Menu extends JPanel{
	private MainFrame main;
	private JLabel title;
	private JRadioButton customer, staff;
	private ButtonGroup btnGroup;
	private JButton enter;
	
	public Menu (MainFrame main) {
		this.main = main;
		setBounds(0, 0, 900, 650);
		setLayout(null);
        setBackground(new Color(173, 216, 230));

		
		title = new JLabel("Fast Motor Rentals", JLabel.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 60, 900, 58);
		add(title);
		
		this.btnGroup = new ButtonGroup();		
			
		customer = new JRadioButton("  Customer");
		customer.setFont(new Font("Tahoma", Font.PLAIN, 24));
        customer.setBounds(357, 209, 186, 52);
        customer.setBackground(new Color(173, 216, 230));

		add(customer);
		this.btnGroup.add(customer);
		
		staff = new JRadioButton("  Staff");
		staff.setFont(new Font("Tahoma", Font.PLAIN, 24));
        staff.setBounds(357, 304, 186, 52);
        staff.setBackground(new Color(173, 216, 230));
		add(staff);
		this.btnGroup.add(staff);
		
		enter = new JButton("Enter");
		enter.setFont(new Font("Tahoma", Font.PLAIN, 24));
        enter.setBounds(357, 426, 186, 52);
        
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					if(customer.isSelected()) {
						main.showCustomerLogin();
					}
					
					if(staff.isSelected()) {
						main.showStaffLogin();
					}
			}
		});
		add(enter);
	};
}
