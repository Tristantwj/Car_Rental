package gui;

import javax.swing.JPanel;

import controller.MainFrame;
import data.LoyaltySummary;
import data.Summary;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoyaltyIndividualView extends JPanel{
	private MainFrame main;
	private JLabel lblUserID;
	private JLabel lblcar;
	private JLabel lblCost;
	private JLabel lblPickupDate;
	private JLabel lblDropoffDate;
	private JLabel lblNumberOfDays;
	private JLabel lblTotalCostShown;
	private LoyaltySummary loyaltySum;
	private int index;
	
	public LoyaltyIndividualView(MainFrame main, int ind, LoyaltySummary loyaltysum){
		this.main = main;
		this.loyaltySum = loyaltysum;
		this.index = ind;
		setBounds(100, 100, 900, 650);
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		
		
		JLabel lblBookingSummary = new JLabel("Loyalty Individual View", JLabel.CENTER);
		lblBookingSummary.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblBookingSummary.setBounds(0, 41, 900, 42);
		add(lblBookingSummary);
		
		JLabel lbla = new JLabel("Username:", JLabel.RIGHT);
		lbla.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lbla.setBounds(225, 124, 120, 22);
		add(lbla);
		
		JLabel lblb = new JLabel("Car:", JLabel.RIGHT);
		lblb.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblb.setBounds(245, 173, 100, 22);
		add(lblb);
		
		JLabel lblc = new JLabel("Pick-up:", JLabel.RIGHT);
		lblc.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblc.setBounds(245, 258, 100, 42);
		add(lblc);
		
		JLabel lbld = new JLabel("Drop-off:", JLabel.RIGHT);
		lbld.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lbld.setBounds(245, 313, 100, 32);
		add(lbld);
		
		JLabel lble = new JLabel("Cost:", JLabel.RIGHT);
		lble.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lble.setBounds(245, 218, 100, 32);
		add(lble);
		
		this.lblUserID = new JLabel("User ID shown here", JLabel.LEFT);
		lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblUserID.setBounds(357, 121, 251, 29);
		add(lblUserID);
		lblUserID.setText(loyaltysum.getUserID());
		
		this.lblcar = new JLabel("model/colour", JLabel.LEFT);
		lblcar.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblcar.setBounds(357, 170, 297, 29);
		add(lblcar);
		lblcar.setText(loyaltysum.getBrand() + " " + loyaltysum.getModel());
		
		this.lblPickupDate = new JLabel("pick-update shown here", JLabel.LEFT);
		lblPickupDate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblPickupDate.setBounds(357, 265, 287, 29);
		add(lblPickupDate);
		lblPickupDate.setText(loyaltysum.getPickUpDate().toString());
		
		this.lblDropoffDate = new JLabel("drop-off date shown here", JLabel.LEFT);
		lblDropoffDate.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblDropoffDate.setBounds(357, 315, 287, 29);
		add(lblDropoffDate);
		lblDropoffDate.setText(loyaltysum.getDropOffDate().toString());
		
		this.lblCost = new JLabel("cost shown here", JLabel.LEFT);
		lblCost.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCost.setBounds(357, 220, 214, 29);
		add(lblCost);
		lblCost.setText(Integer.toString(loyaltysum.getCost()));
	
		JButton back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(357, 466, 186, 52);
        back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				showBookingSummary();
			}
		});
		add(back);
		
		JLabel lblf = new JLabel("No. of days:");
		lblf.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblf.setBounds(215, 363, 130, 32);
		add(lblf);
		
		JLabel lblg = new JLabel("Total Cost:");
		lblg.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblg.setBounds(225, 408, 120, 32);
		add(lblg);
		
		this.lblNumberOfDays = new JLabel("Number of days shown here");
		lblNumberOfDays.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNumberOfDays.setBounds(357, 363, 287, 32);
		add(lblNumberOfDays);
		lblNumberOfDays.setText(Integer.toString(loyaltysum.getNoOfDay()));
		
		this.lblTotalCostShown = new JLabel("Total cost shown here");
		lblTotalCostShown.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTotalCostShown.setBounds(357, 407, 251, 34);
		add(lblTotalCostShown);
		lblTotalCostShown.setText(Integer.toString(loyaltysum.getTotalCost()));
	}
	public void showBookingSummary(){
		this.main.showBookingSummary();
	}
}
