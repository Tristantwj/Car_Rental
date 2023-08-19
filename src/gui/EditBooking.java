package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controller.Controller;
import controller.MainFrame;
import data.Booking;
import data.Customer;
import data.DataStorage;

public class EditBooking extends JPanel {
    private MainFrame main;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTable bookingsTable;
    private Customer loggedInCustomer;
    private JLabel title;
    private JLabel pickUp;
    private JLabel dropOff;
    private JLabel userNotif;
    private JDateChooser pickupdateChooser; 
    private JDateChooser dropoffdateChooser; 
    private JButton update;
    private JButton back;
    private Timer timer;

    public EditBooking(MainFrame main, Customer loggedInCustomer) {
        this.main = main;
        this.loggedInCustomer = loggedInCustomer;
        
        setBounds(100, 100, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));

        String[] columnNames = {"Brand", "Model", "Pick-Up", "Drop-Off", "Cost per Day ($)"};
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10;

        tableModel = new DefaultTableModel(columnNames, 0);
        bookingsTable = new JTable(tableModel);
        bookingsTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
        bookingsTable.setRowHeight(desiredRowHeight);
        bookingsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int selectedRow = bookingsTable.getSelectedRow();
                if (selectedRow != -1) {
                    Booking selectedBooking = DataStorage.getAllBooking(loggedInCustomer)[selectedRow];
                    
                    pickupdateChooser.setDate(Date.from(selectedBooking.getPickUpDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    pickupdateChooser.setDateFormatString("dd/MM/yyyy");
                    dropoffdateChooser.setDate(Date.from(selectedBooking.getDropOffDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    dropoffdateChooser.setDateFormatString("dd/MM/yyyy");
                }
            }
        });

        scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBounds(12, 142, 878, 208);
        add(scrollPane);

        title = new JLabel("Edit Booking", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 69, 890, 42);
        add(title);

        pickUp = new JLabel("Pick-Up Date:", JLabel.RIGHT);
        pickUp.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pickUp.setBounds(242, 363, 174, 42);
        add(pickUp);

        pickupdateChooser = new JDateChooser();
        pickupdateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
        pickupdateChooser.setBounds(426, 363, 206, 42);
        add(pickupdateChooser);

        dropOff = new JLabel("Drop-Off Date:", JLabel.RIGHT);
        dropOff.setFont(new Font("Tahoma", Font.PLAIN, 24));
        dropOff.setBounds(242, 418, 174, 42);
        add(dropOff);

        dropoffdateChooser = new JDateChooser(); 
        dropoffdateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
        dropoffdateChooser.setBounds(426, 418, 206, 42);
        add(dropoffdateChooser);

        userNotif = new JLabel("", JLabel.CENTER);
        userNotif.setFont(new Font("Tahoma", Font.BOLD, 29));
        userNotif.setBounds(0, 471, 900, 42);
        userNotif.setVisible(false);
        add(userNotif);

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userNotif.setVisible(false);
                timer.stop();
            }
        });
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        update = new JButton("Update");
        update.setFont(new Font("Tahoma", Font.PLAIN, 24));
        update.setBounds(357, 524, 186, 52);
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Get the selected row index from the bookingsTable
                int selectedRow = bookingsTable.getSelectedRow();
                
                // Check if a row is selected
                if (selectedRow != -1) {
                    // Get the new pick-up and drop-off dates from the date choosers
                    Date newPickUpDate = pickupdateChooser.getDate();
                    Date newDropOffDate = dropoffdateChooser.getDate();     

                    // Get the selected booking from the data storage
                    Booking selectedBooking = DataStorage.getAllBooking(loggedInCustomer)[selectedRow];
                    
                    // Store old values for comparison
                    int oldnumDays = selectedBooking.getNoOfDay();
                    int oldpoint = loggedInCustomer.getPoint();
                    int oldtotalcost = selectedBooking.getTotalCost();
                    
                    // Update the pick-up and drop-off dates of the booking
                    selectedBooking.setPickUpDate(newPickUpDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    selectedBooking.setDropOffDate(newDropOffDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    // Convert Date objects to LocalDate
                    LocalDate pickUpDatea = newPickUpDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate dropOffDatea = newDropOffDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    // Calculate the new number of days and total cost
                    int numDays = Controller.daysBetween(pickUpDatea, dropOffDatea) + 1;
                    int cost = selectedBooking.getCost();
                    int totalcost = Controller.calculateCost(cost, numDays);
                    selectedBooking.setNoOfDay(numDays);
                    selectedBooking.setTotalCost(totalcost);

                    // Update the booking in the data storage
                    DataStorage.updateBooking(selectedBooking, loggedInCustomer);
                    
                    // Update customer points based on changes in booking
                    if (oldnumDays > numDays) {
                        int minuspoints = (oldtotalcost - totalcost);
                        int newpoints = oldpoint - minuspoints;
                        loggedInCustomer.setPoint(newpoints);
                    }
                    if (oldnumDays < numDays) {
                        int addpoints = (totalcost - oldtotalcost);
                        int newpoints = oldpoint + addpoints;
                        loggedInCustomer.setPoint(newpoints);
                    }
                    
                    // Save updated customer and booking data to files
                    
                    String currentDirectory = System.getProperty("user.dir");
                    
                    // Construct the file paths using the current directory
                    String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
                    String bookingFilePath = currentDirectory + "\\excel_data\\Booking.xlsx";
                    
                    main.getController().savecustomers(customerFilePath);
                    main.getController().savebookings(bookingFilePath);

                    // Refresh the JTable to reflect the changes
                    updateBookingTable();

                    // Show the "Updates saved!" label and start timer to hide it
                    userNotif.setText("Updates saved");
                    userNotif.setVisible(true);
                    timer.start();
                } else {
                    // If no row is selected, show a notification and start timer to hide it
                    userNotif.setText("Please select a booking to update");
                    userNotif.setVisible(true);
                    timer.start();
                }
            }
        });
        add(update);

        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(357, 587, 186, 52);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                main.showBookings(loggedInCustomer);
                clearDateChoosers();
            }
        });
        add(back);

        updateBookingTable();
    }

    public void updateBookingTable() {
        DataStorage.updateTableWithBookingData(tableModel, loggedInCustomer);
    }

    public void clearDateChoosers() {
        pickupdateChooser.setDate(null);
        dropoffdateChooser.setDate(null);
    }
}