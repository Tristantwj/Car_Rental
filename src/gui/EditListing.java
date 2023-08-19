package gui;

import javax.swing.JPanel;

import controller.MainFrame;
import data.Car;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditListing extends JPanel{
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
	private JButton edit;
	private JButton back;
	private Car car;
	private int index;
	
	public EditListing(MainFrame main, int ind, Car car) {
		this.main = main;
		this.index = ind;
		this.car = car;
		
		setBounds(0, 0, 900, 650);
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		
		title = new JLabel("Edit Listing");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 36, 900, 37);
		add(title);
		
		carBrand = new JLabel("Brand:");
		carBrand.setHorizontalAlignment(SwingConstants.RIGHT);
		carBrand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		carBrand.setBounds(0, 132, 432, 50);
		add(carBrand);
		
		carModel = new JLabel("Model:");
		carModel.setHorizontalAlignment(SwingConstants.RIGHT);
		carModel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		carModel.setBounds(0, 218, 432, 50);
		add(carModel);
		
		costPerDay = new JLabel("Cost / Day:");
		costPerDay.setHorizontalAlignment(SwingConstants.RIGHT);
		costPerDay.setFont(new Font("Tahoma", Font.PLAIN, 24));
		costPerDay.setBounds(0, 297, 432, 50);
		add(costPerDay);
		
		imagePath = new JLabel("Image Path (.png / .jpg / .jpeg):");
		imagePath.setHorizontalAlignment(SwingConstants.RIGHT);
		imagePath.setFont(new Font("Tahoma", Font.PLAIN, 24));
		imagePath.setBounds(0, 377, 432, 50);
		add(imagePath);
		
		brand = new JTextField();
		brand.setFont(new Font("Tahoma", Font.PLAIN, 24));
		brand.setBounds(462, 133, 305, 50);
		brand.setText(car.getBrand());
		add(brand);
		
		model = new JTextField();
		model.setFont(new Font("Tahoma", Font.PLAIN, 24));
		model.setBounds(462, 218, 305, 50);
		model.setText(car.getModel());
		add(model);
		
		cost = new JTextField();
		cost.setFont(new Font("Tahoma", Font.PLAIN, 24));
		cost.setBounds(462, 297, 305, 50);
		cost.setText(Integer.toString(car.getCostPerDay()));
		add(cost);
		
		image = new JTextField();
		image.setFont(new Font("Tahoma", Font.PLAIN, 24));
		image.setColumns(10);
		image.setBounds(462, 378, 305, 50);
		image.setText(car.getImagePath());
		add(image);

		edit = new JButton("Edit");
		edit.setFont(new Font("Tahoma", Font.PLAIN, 24));
		edit.setBounds(511, 476, 210, 50);
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String m = model.getText();
				String b = brand.getText();
				String i = image.getText();
				int c = Integer.valueOf(cost.getText());
				Car newcar = new Car(b, m, c, i);
				main.getController().editCar(ind, newcar);
				
		        String currentDirectory = System.getProperty("user.dir");
		        
		        String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";

				main.getController().savecars(carsFilePath);
				main.showListings();
			}
		});
		
		add(edit);
		
		back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 24));
		back.setBounds(181, 476, 210, 50);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showListings();
			}
		});	
		add(back);
	}
}
