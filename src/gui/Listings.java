package gui;

import javax.swing.JPanel;

import controller.Controller;
import controller.MainFrame;
import data.Car;
import data.Customer;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.Collections;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Listings extends JPanel {
    private MainFrame main;
    private JTable listingsTable;
    private DefaultTableModel tableModel;
    private Vector<Car> carvec;
    private JScrollPane scrollPane;
    private JLabel title;
    private JLabel sortBy;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JButton bookingSummary;
    private JButton logout;	
	private JButton sortByCost;
    private int sort;
    private JLabel img;


    public Listings(MainFrame main) {
        this.main = main;       
        setBounds(0, 0, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));
        
        String[] columnNames = {"Brand", "Model", "Cost per Day ($)"};
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10;

        tableModel = new DefaultTableModel(columnNames, 0);
        listingsTable = new JTable(tableModel);
        listingsTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
        listingsTable.setRowHeight(desiredRowHeight);
        listingsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = listingsTable.getSelectedRow();
                    updateImage(selectedRow);
                }
            }
        });
        
        scrollPane = new JScrollPane(listingsTable);
        scrollPane.setBounds(378, 97, 470, 211);
        add(scrollPane);
        
        title = new JLabel("Listings", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 25, 900, 60);
        add(title);

        add = new JButton("Add Listings");
        add.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add.setBounds(58, 125, 262, 52);
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.showAddListing();
            }
        });
        add(add);

        edit = new JButton("Edit Listing");
        edit.setFont(new Font("Tahoma", Font.PLAIN, 24));
        edit.setBounds(58, 225, 262, 52);
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index from the listings table
                int numRows = listingsTable.getSelectedRow();
                if (numRows == -1)
                    return;

                // Retrieve the corresponding car object based on the selected row
                Car car = carvec.get(numRows);

                // Show the edit listing view with the selected car's information
                main.showEditListing(numRows, car);
            }
        });
        add(edit);

        delete = new JButton("Delete Listing");
        delete.setFont(new Font("Tahoma", Font.PLAIN, 24));
        delete.setBounds(58, 325, 262, 52);   
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index from the listings table
                int numRows = listingsTable.getSelectedRow();
                if (numRows == -1)
                    return;

                // Remove the selected row from the table model
                tableModel.removeRow(numRows);

                // Retrieve the corresponding car object based on the selected row
                Car car = carvec.get(numRows);

                // Delete the selected car from the controller
                main.getController().deleteCar(numRows, car);
                
                String currentDirectory = System.getProperty("user.dir");
		        
		        String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";

				main.getController().savecars(carsFilePath);            }
        });
        add(delete);

        bookingSummary = new JButton("Booking Summary");
        bookingSummary.setFont(new Font("Tahoma", Font.PLAIN, 24));
        bookingSummary.setBounds(58, 425, 262, 52);
        bookingSummary.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	     main.showBookingSummary();        	}
        });
        add(bookingSummary);

        logout = new JButton("Logout");
        logout.setFont(new Font("Tahoma", Font.PLAIN, 24));
        logout.setBounds(58, 525, 262, 52);
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                main.showMenu();
            }
        });
        add(logout);
        
        sortBy = new JLabel("Sort By:");
        sortBy.setFont(new Font("Tahoma", Font.PLAIN, 24));
        sortBy.setBounds(378, 321, 108, 40);
        add(sortBy);
        
        sortByCost = new JButton("Cost");       
        sortByCost.setFont(new Font("Tahoma", Font.PLAIN, 24));
        sortByCost.setBounds(497, 321, 163, 42);
        sortByCost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Toggle sorting direction based on the current state
                if (sort == 0) {
                    // Sort carvec in ascending order by cost
                    Collections.sort(carvec, new Controller());
                    sort = 1;
                } else if (sort == 1) {
                    // Sort carvec in descending order by cost
                    Collections.sort(carvec, Collections.reverseOrder(new Controller()));
                    sort = 0;
                }

                // Refresh the table to reflect the sorted order
                refreshTable();
            }
        });
        add(sortByCost);
        
        img = new JLabel("", SwingConstants.CENTER);
        img.setBounds(378, 370, 470, 193);
        add(img);
        
        this.populateCalculationList();
    }

 // Populates the calculation list with car data
    public void populateCalculationList() {
        // Retrieve all car objects from the controller
        this.carvec = this.main.getController().getAllCars();

        // Populate the table model with car data
        for (int i = 0; i < carvec.size(); i++) {
            Car op = carvec.get(i);
            tableModel.insertRow(listingsTable.getRowCount(), new Object[]{op.getBrand(), op.getModel(), op.getCostPerDay()});
        }

        // Set the populated table model to the listings table
        this.listingsTable.setModel(tableModel);
    }

    // Refreshes the table with updated car data
    private void refreshTable() {
        tableModel.setRowCount(0); 
        for (int i = 0; i < carvec.size(); i++) {
            Car op = carvec.get(i);
            tableModel.addRow(new Object[]{op.getBrand(), op.getModel(), op.getCostPerDay()});
        }
        listingsTable.setModel(tableModel);
        tableModel.fireTableDataChanged(); 
    }

    // Updates the displayed image based on the selected row
    private void updateImage(int selectedRow) {
        if (carvec != null && !carvec.isEmpty() && selectedRow >= 0 && selectedRow < carvec.size()) {
            // Retrieve the image path for the selected car
            String imagePath = carvec.get(selectedRow).getImagePath();

            // Set the image icon to the JLabel
            ImageIcon imageIcon = new ImageIcon(imagePath);
            img.setIcon(imageIcon);
        } else {
            // Clear the image icon if no car is selected
            img.setIcon(null);
        }
    }
}
