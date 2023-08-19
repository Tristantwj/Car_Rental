package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.Controller;
import controller.MainFrame;
import data.Car;
import data.Customer;


public class BookCar extends JPanel {
	private MainFrame main;
	private Controller controller;
    private Customer loggedInCustomer;
    private Vector<Car> carvec;
    private JTable listingsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JDateChooser pickUpDateChooser;
    private JDateChooser dropOffDateChooser;
    private JLabel title;
    private JLabel pickUp;
    private JLabel dropOff;
    private JLabel userNotif; 
	private JLabel img;
    private JLabel sortBy;
    private JButton book;
    private JButton bookinglist;
    private JButton logout;
    private JButton sortbycost;
    private JButton loyalty;
    private Timer timer;
    private int sort;
    
    public BookCar(MainFrame main, Controller controller, Customer loggedInCustomer) {
        this.main = main;
        this.controller = controller;
        this.loggedInCustomer = loggedInCustomer;
        setBounds(100, 100, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));
        populateCarVector();
        this.sort = 0;
        
        title = new JLabel("Book Your Car", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 25, 900, 49);  
        add(title);

        String[] columnNames = { "Brand", "Model", "Cost per Day ($)" };
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10; // Adjust 10 as needed

        tableModel = new DefaultTableModel(columnNames, 0);
        listingsTable = new JTable(tableModel);
        listingsTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
        listingsTable.setRowHeight(desiredRowHeight);
        listingsTable.getTableHeader().setReorderingAllowed(false);
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
        
        pickUp = new JLabel("Pick-Up Date:", JLabel.LEFT);
        pickUp.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pickUp.setBounds(58, 86, 163, 51);
        add(pickUp);

        pickUpDateChooser = new JDateChooser();
        pickUpDateChooser.setDateFormatString("dd/MM/yyyy");
        pickUpDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pickUpDateChooser.setBounds(58, 139, 262, 52);
        add(pickUpDateChooser);

        dropOff = new JLabel("Drop-Off Date:", JLabel.LEFT);
        dropOff.setFont(new Font("Tahoma", Font.PLAIN, 24));
        dropOff.setBounds(58, 193, 262, 52);
        add(dropOff);

        dropOffDateChooser = new JDateChooser();
        dropOffDateChooser.setDateFormatString("dd/MM/yyyy");
        dropOffDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
        dropOffDateChooser.setBounds(58, 243, 262, 52);
        add(dropOffDateChooser);
     
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNotif.setVisible(false);
                timer.stop();
            }
        });
        
        userNotif = new JLabel("", JLabel.CENTER);
        userNotif.setFont(new Font("Tahoma", Font.BOLD, 29));
        userNotif.setBounds(0, 568, 900, 51);
        userNotif.setVisible(false);
        add(userNotif);
        
        sortBy = new JLabel("Sort By: ");
        sortBy.setFont(new Font("Tahoma", Font.PLAIN, 24));
        sortBy.setBounds(378, 321, 108, 40);
        add(sortBy);
        
        sortbycost = new JButton ("Cost");
        sortbycost.setFont(new Font("Tahoma", Font.PLAIN, 24));
        sortbycost.setBounds(497, 321, 163, 42);
        sortbycost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	if(sort == 0){
        			Collections.sort(carvec, new Controller());
        			sort = 1;
        		}
        		else if(sort == 1){
        			Collections.sort(carvec, Collections.reverseOrder(new Controller()));
        			sort = 0;
        		}
                refreshTable(listingsTable.getSelectedRow());
            }
        });
        add(sortbycost);
        
        book = new JButton("Book");
        book.setFont(new Font("Tahoma", Font.PLAIN, 24));
        book.setBounds(58, 316, 262, 52);
        book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int selectedRow = listingsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedBrand = (String) tableModel.getValueAt(selectedRow, 0);
                    String selectedModel = (String) tableModel.getValueAt(selectedRow, 1);

                    Date pickUpDate = pickUpDateChooser.getDate();
                    Date dropOffDate = dropOffDateChooser.getDate();
                    Date currentDate = new Date(); 
                    
                    if (pickUpDate == null || dropOffDate == null) {
                        userNotif.setText("Fill in pick-up and drop-off dates");
                        userNotif.setForeground(Color.RED);
                    	userNotif.setVisible(true);
                        timer.start();
                        return;
                    }

                    if (pickUpDate.after(dropOffDate) || pickUpDate.equals(dropOffDate)) {
                        userNotif.setText("Invalid drop-off date. Must be after the pick-up date.");
                        userNotif.setForeground(Color.RED);
                    	userNotif.setVisible(true);
                        timer.start();
                        return;
                    }

                    if (pickUpDate.before(currentDate)) {
                        userNotif.setText("Invalid pick-up date. Must be after today.");
                        userNotif.setForeground(Color.RED);
                    	userNotif.setVisible(true);
                        timer.start();

                        return;
                    }
                    

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String pickUp = dateFormat.format(pickUpDate);
                    String dropOff = dateFormat.format(dropOffDate);
                    int cost = (int) tableModel.getValueAt(selectedRow, 2);
                    
                    String imagePath = carvec.get(selectedRow).getImagePath();
                    
                    
                    LocalDate pickUpDatea = pickUpDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate dropOffDatea = dropOffDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    int NoOfDay = Controller.daysBetween(pickUpDatea, dropOffDatea) + 1;
                    int totalcost = Controller.calculateCost(cost, NoOfDay);
                    
                    int customerPoints = loggedInCustomer.getPoint();
                    int newPoints = customerPoints + totalcost;
                    
                    updateCustomerPoints(newPoints);
                    
                    Car selectedCar = new Car(selectedBrand, selectedModel, cost, imagePath);

                    main.getController().deleteCar(selectedRow, selectedCar);

                    controller.addBooking(loggedInCustomer, selectedBrand, selectedModel, imagePath, pickUpDatea, dropOffDatea, cost, NoOfDay, totalcost);
                    
                    String currentDirectory = System.getProperty("user.dir");
                    String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
                    String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
                    String bookingFilePath = currentDirectory + "\\excel_data\\Booking.xlsx";
                    
                    main.getController().savecustomers(customerFilePath);
                    main.getController().savebookings(bookingFilePath);
                    main.getController().savecars(carsFilePath);
                    
                    userNotif.setText("Booking confirmed!");
                    userNotif.setVisible(true);
                    timer.start();

                    updateCarListingsTable();
                } else {
                    userNotif.setText("No car selected");
                    userNotif.setVisible(true);
                    timer.start();
                }
            }
        });

        	
        add(book);
        bookinglist = new JButton("Booking List");
        bookinglist.setFont(new Font("Tahoma", Font.PLAIN, 24));
        bookinglist.setBounds(58, 444, 262, 52);
        bookinglist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent args0) {
                main.showBookings(loggedInCustomer);
                
                Car deletedCar = main.getBookingsPanel().getDeletedCar();
                
                if (deletedCar != null) {
                    restoreDeletedCar(deletedCar);
                }
            }
        });
        add(bookinglist);
        
        logout = new JButton("Logout");
        logout.setFont(new Font("Tahoma", Font.PLAIN, 24));
        logout.setBounds(58, 508, 262, 52);
        logout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent args0){
                main.showMenu();
            }
        });
        add(logout);
        
        loyalty = new JButton("Loyalty Page");
        loyalty.setFont(new Font("Tahoma", Font.PLAIN, 24));
        loyalty.setBounds(56, 381, 262, 52);
        loyalty.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		main.showLoyalty(loggedInCustomer);
        	}
        });
        add(loyalty);
        
        img = new JLabel("", SwingConstants.CENTER);
        img.setBounds(378, 370, 470, 193);
        img.setBackground(new Color(173, 216, 230));
        add(img);
        
        updateCarListingsTable();
    }
    
    // Method to update the car listings table with available cars for the customer
    public void updateCarListingsTable() {
        // Clear the existing rows in the table model
        tableModel.setRowCount(0);

        // Get a list of available cars for the logged-in customer
        Vector<Car> availableCars = main.getController().getAvailableCarsForCustomer(loggedInCustomer);

        // Populate the table model with the available car data
        for (Car car : availableCars) {
            Object[] row = { car.getBrand(), car.getModel(), car.getCostPerDay() };
            tableModel.addRow(row);
        }
    }

 // Method to refresh the table with updated data
    private void refreshTable(int selectedRow) {
        // Clear the existing rows in the table model
        tableModel.setRowCount(0);

        // Populate the table model with car data from the carvec vector
        for (int i = 0; i < carvec.size(); i++) {
            Car car = carvec.get(i);
            tableModel.addRow(new Object[] { car.getBrand(), car.getModel(), car.getCostPerDay() });
        }

        // Set the updated table model to the listingsTable
        listingsTable.setModel(tableModel);
        tableModel.fireTableDataChanged();

        // Update the car image based on the selected row
        updateImage(selectedRow);
    }

    // Method to update the car image based on the selected row
    private void updateImage(int selectedRow) {
        if (carvec != null && !carvec.isEmpty() && selectedRow >= 0 && selectedRow < carvec.size()) {
            // Get the image path for the selected car and display it in the img JLabel
            String imagePath = carvec.get(selectedRow).getImagePath();
            ImageIcon imageIcon = new ImageIcon(imagePath);
            img.setIcon(imageIcon);
        } else {
            // Clear the img JLabel if no car is selected or available
            img.setIcon(null);
        }
    }

    // Method to restore a deleted car to the table after booking cancellation
    public void restoreDeletedCar(Car car) {
        Object[] row = { car.getBrand(), car.getModel(), car.getCostPerDay() };
        tableModel.addRow(row);
    }

    // Method to populate the carvec vector with available cars for the customer
    private void populateCarVector() {
        carvec = main.getController().getAvailableCarsForCustomer(loggedInCustomer);
    }

    // Method to update customer loyalty points after a booking
    private void updateCustomerPoints(int newPoints) {
        loggedInCustomer.setPoint(newPoints);
    }
}