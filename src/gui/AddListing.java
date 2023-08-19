package gui;

import javax.swing.JPanel;

import controller.MainFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AddListing extends JPanel{
	private MainFrame main;
	private JLabel title;
	private JLabel carBrand;
	private JLabel carModel;
	private JLabel costPerDay;
	private JLabel imagePath;
	private JTextField brand;
	private JTextField model;
	private JTextField cost;
	private JTextField image;
	private JButton add;
	private JButton back;
	
	public AddListing(MainFrame main) {
		this.main = main;
		
        setBounds(0, 0, 900, 650);
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		
		title = new JLabel("Add Listing", JLabel.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 36, 900, 37);
		add(title);
		
		carBrand = new JLabel("Brand:", JLabel.RIGHT);
		carBrand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		carBrand.setBounds(0, 132, 432, 50);
		add(carBrand);
		
		carModel = new JLabel("Model:", JLabel.RIGHT);
		carModel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		carModel.setBounds(0, 218, 432, 50);
		add(carModel);
		
		costPerDay = new JLabel("Cost / Day:", JLabel.RIGHT);
		costPerDay.setFont(new Font("Tahoma", Font.PLAIN, 24));
		costPerDay.setBounds(0, 297, 432, 50);
		add(costPerDay);
		
		imagePath = new JLabel("Image Path (.png / .jpg / .jpeg):", JLabel.RIGHT);
		imagePath.setFont(new Font("Tahoma", Font.PLAIN, 24));
		imagePath.setBounds(0, 377, 432, 50);
		add(imagePath);
		
		brand = new JTextField();
		brand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		brand.setBounds(462, 133, 305, 50);
		add(brand);
		
		model = new JTextField();
		model.setFont(new Font("Tahoma", Font.PLAIN, 24));
		model.setBounds(462, 218, 305, 50);
		add(model);
		
		cost = new JTextField();
		cost.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cost.setBounds(462, 297, 305, 50);
		add(cost);
		
		image = new JTextField();
		image.setFont(new Font("Tahoma", Font.PLAIN, 24));
		image.setColumns(10);
		image.setBounds(462, 378, 305, 50);
		add(image);
		
		add = new JButton("Add");
		add.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add.setBounds(511, 476, 210, 50);
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String b = brand.getText();
				String m = model.getText();
				String i = image.getText();
				int c = Integer.valueOf(cost.getText());
				main.getController().addCar(b, m, c, i);
				
				String currentDirectory = System.getProperty("user.dir");
				String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
				main.getController().savecars(carsFilePath);
				main.showListings();
			}
		});
		add(add);
		
		back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 24));
		back.setBounds(181, 476, 210, 50);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.showListings();
			}
		});
		add(back);
		
		
		
		
	}
}
