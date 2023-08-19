package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.MainFrame;
import data.Booking;
import data.Car;
import data.Customer;
import data.DataStorage;
import data.LoyaltyBooking;

public class Bookings extends JPanel {
    private MainFrame main;
    private Customer loggedInCustomer;
    private JTable bookingsTable;
	private JScrollPane bookingScrollPane;
    private DefaultTableModel bookingsTableModel;
    private JTable loyaltyTable;
	private JScrollPane loyaltyScrollPane;
    private DefaultTableModel loyaltyTableModel;
    private JLabel title;
    private JLabel loyaltyBooking;
	private JLabel normalBooking;
    private JLabel userNotif;
    private JButton edit;
    private JButton back;
    private JButton delete;
    private Car deletedCar;
	private Timer timer;

    public Bookings(MainFrame main, Customer loggedInCustomer) {
        this.loggedInCustomer = loggedInCustomer;
        this.main = main;
        setBounds(100, 100, 910, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));

        String[] columnNames = {"Brand", "Model", "Pick-Up", "Drop-Off", "Cost per Day ($)", "No. of Days", "Total Cost"};
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10; // Adjust 10 as needed

        bookingsTableModel = new DefaultTableModel(columnNames, 0);
        bookingsTable = new JTable(bookingsTableModel);
        bookingsTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookingsTable.setRowHeight(desiredRowHeight);
        bookingsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bookingsTable.getSelectedRow() != -1) {
            	loyaltyTable.clearSelection();
            }
        });
        
        bookingScrollPane = new JScrollPane(bookingsTable);
        bookingScrollPane.setBounds(22, 90, 854, 167);
        add(bookingScrollPane);
        
        
        loyaltyTableModel = new DefaultTableModel(columnNames, 0);
        loyaltyTable = new JTable(loyaltyTableModel);
        loyaltyTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
        loyaltyTable.setRowHeight(desiredRowHeight);
        loyaltyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && loyaltyTable.getSelectedRow() != -1) {
                bookingsTable.clearSelection();
            }
        });
        
        loyaltyScrollPane = new JScrollPane(loyaltyTable);
        loyaltyScrollPane.setBounds(22, 294, 854, 167);
        add(loyaltyScrollPane);
        
        title = new JLabel("Bookings", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 11, 910, 51);
        add(title);
        
        normalBooking = new JLabel("Normal Booking");
        normalBooking.setFont(new Font("Tahoma", Font.PLAIN, 18));
        normalBooking.setBounds(22, 62, 151, 29);
        add(normalBooking);
        
        loyaltyBooking = new JLabel("Loyalty Booking");
        loyaltyBooking.setFont(new Font("Tahoma", Font.PLAIN, 18));
        loyaltyBooking.setBounds(22, 267, 141, 29);
        add(loyaltyBooking);

        edit = new JButton("Edit");
        edit.setFont(new Font("Tahoma", Font.PLAIN, 24));
        edit.setBounds(223, 524, 186, 47);
        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (main != null) {
                    main.showEditBooking(loggedInCustomer);
                }
            }
        });
        add(edit);

        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(353, 584, 186, 47);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (main != null) {
                    main.showBookCar(loggedInCustomer);
                    // Pass the deleted car back to BookCar
                    Car deletedCar = getDeletedCar();
                    if (deletedCar != null && main.getBookCarPanel() != null) {
                        main.getBookCarPanel().restoreDeletedCar(deletedCar);
                        setDeletedCar(null); // Reset the deletedCar variable after restoration
                    }
                }
            }
        });
        add(back);
        
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the "confirm" label and stop the timer
                userNotif.setVisible(false);
                timer.stop();
            }
        });
        
        userNotif = new JLabel("", SwingConstants.CENTER);
        userNotif.setFont(new Font("Tahoma", Font.BOLD, 30));
        userNotif.setBounds(0, 472, 910, 51);
        userNotif.setVisible(false);
        add(userNotif);
        
        delete = new JButton("Delete");
        delete.setFont(new Font("Tahoma", Font.PLAIN, 24));
        delete.setBounds(483, 524, 186, 47);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int selectedBooking = bookingsTable.getSelectedRow();
                int selectedLoyaltyBooking = loyaltyTable.getSelectedRow();
                
                // If a normal booking is selected
                if (selectedBooking != -1) {
                    // Extract booking details from the selected row
                    String selectedBrand = (String) bookingsTable.getValueAt(selectedBooking, 0);
                    String selectedModel = (String) bookingsTable.getValueAt(selectedBooking, 1);
                    String costStr = (String) bookingsTable.getValueAt(selectedBooking, 4);
                    String totalcostStr = (String) bookingsTable.getValueAt(selectedBooking, 6);
                    
                    int cost = Integer.parseInt(costStr);
                    int totalcost = Integer.parseInt(totalcostStr);
                    
                    // Get the selected booking data
                    Booking selectedBookingData = main.getController().getAllBookings(loggedInCustomer)[selectedBooking];
                    String imagePath = selectedBookingData.getImagePath();
                    
                    // Calculate points to return to the customer
                    int returnpoints = totalcost;
                    int customerPoints = loggedInCustomer.getPoint();
                    int newPoints = customerPoints - returnpoints;
                    
                    // Check if customer has enough points for return
                    if (newPoints < 0) {
                        JOptionPane.showMessageDialog(null, "Invalid: not enough points\nDelete loyalty booking first");
                    } 
                    else {
                        // Update customer points
                        updateCustomerPoints(newPoints);
                        
                        // Create a deletedCar instance to restore
                        deletedCar = new Car(selectedBrand, selectedModel, cost, imagePath);
                        
                        // Add car back into listings
                        main.getController().addCar(selectedBrand, selectedModel, cost, imagePath);
                        
                        // Remove the selected booking from the table
                        bookingsTableModel.removeRow(selectedBooking);
                        
                        // Delete the booking data from DataStorage
                        DataStorage.deleteBooking(selectedBookingData, loggedInCustomer);
                        
                        // Save updated data to files
                        
                        String currentDirectory = System.getProperty("user.dir");

                        String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
                        String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
                        String bookingFilePath = currentDirectory + "\\excel_data\\Booking.xlsx";
                        
                        main.getController().savebookings(bookingFilePath);
                        main.getController().savecars(carsFilePath);
                        main.getController().savecustomers(customerFilePath);
                        // Show a confirmation message
                        if (main != null && main.getBookCarPanel() != null) {
                            userNotif.setText("Booking confirmed!");
                            userNotif.setVisible(true);
                            timer.start();
                        }
                    }
                }
                
                // If a loyalty booking is selected
                else if (selectedLoyaltyBooking != -1) {
                    // Extract booking details from the selected row
                    String selectedBrand = (String) loyaltyTable.getValueAt(selectedLoyaltyBooking, 0);
                    String selectedModel = (String) loyaltyTable.getValueAt(selectedLoyaltyBooking, 1);
                    String costStr = (String) loyaltyTable.getValueAt(selectedLoyaltyBooking, 4);
                    String totalcostStr = (String) loyaltyTable.getValueAt(selectedLoyaltyBooking, 6);
                    
                    int cost = Integer.parseInt(costStr);
                    int totalcost = Integer.parseInt(totalcostStr);
                    
                    // Calculate points to return to the customer
                    int returnpoints = totalcost * 10;
                    int customerPoints = loggedInCustomer.getPoint();
                    int newPoints = customerPoints + returnpoints;
                    
                    // Update customer points
                    updateCustomerPoints(newPoints);
                    
                    // Retrieve the selected loyalty booking object from DataStorage
                    LoyaltyBooking selectedLoyaltyBookingData = DataStorage.getAllLoyaltyBooking(loggedInCustomer)[selectedLoyaltyBooking];
                    // Get the imagePath from the selected loyalty booking object
                    String imagePath = selectedLoyaltyBookingData.getImagePath();
                    
                    // Create a deletedCar instance to restore
                    deletedCar = new Car(selectedBrand, selectedModel, cost, imagePath);
                    
                    // Add car back into listings
                    main.getController().addCar(selectedBrand, selectedModel, cost, imagePath);
                    
                    // Remove the selected loyalty booking from the table
                    loyaltyTableModel.removeRow(selectedLoyaltyBooking);
                    
                    // Delete the loyalty booking data from DataStorage
                    DataStorage.deleteLoyaltyBooking(selectedLoyaltyBookingData, loggedInCustomer);
                    
                    // Save updated data to files
                    
                    String currentDirectory = System.getProperty("user.dir");
                    
                    String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
                    String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
                    String loyaltyBookingFilePath = currentDirectory + "\\excel_data\\LoyaltyBooking.xlsx";
                    
                    main.getController().saveloyaltybookings(loyaltyBookingFilePath);
                    main.getController().savecars(carsFilePath);
                    main.getController().savecustomers(customerFilePath);
                } 
                else {
                    // If no row is selected, show a message
                    userNotif.setText("Please select a row to remove");
                    userNotif.setVisible(true);
                    timer.start();
                }
            }
        });

        add(delete);
        
        updateTables();
    }

    // Method to get the car that was deleted from a booking
    public Car getDeletedCar() {
        return deletedCar;
    }

    // Method to set the car that was deleted from a booking
    public void setDeletedCar(Car car) {
        this.deletedCar = car;
    }

    // Method to update the tables with booking data
    public void updateTables() {
        DataStorage.updateTableWithBookingData(bookingsTableModel, loggedInCustomer);
        DataStorage.updateTableWithLoyaltyBookingData(loyaltyTableModel, loggedInCustomer);
    }

    
    // Method to update customer loyalty points
    private void updateCustomerPoints(int newPoints) {
        loggedInCustomer.setPoint(newPoints);
    }
}

