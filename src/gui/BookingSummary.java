package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.MainFrame;
import data.DataStorage;
import data.LoyaltySummary;
import data.Summary;
import javax.swing.SwingConstants;

public class BookingSummary extends JPanel {
    private MainFrame main;
    private Vector<Summary> summaries; // Stores booking summaries
    private Vector<LoyaltySummary> loyaltysummaries; // Stores loyalty booking summaries
    private JTable bookingsTable;
    private JScrollPane bookingScrollPane;
    private DefaultTableModel bookingsTableModel;
    private JTable loyaltyTable;
    private JScrollPane loyaltyScrollPane;
    private DefaultTableModel loyaltyTableModel;
    private JLabel title;
    private JLabel totalEarned;
    private JLabel total;
    private JLabel normalBooking;
    private JLabel loyaltyBooking;
    private JLabel loyaltyPoints;
    private JLabel totalLoyaltyPoints;
    private JButton back;
    private JButton viewIndividual;

    public BookingSummary(MainFrame main) {
        this.main = main;
        setBounds(100, 100, 900, 650);
        setLayout(null);
        setBackground(new Color(173, 216, 230));

        // Table column names and desired row height for consistent layout
        String[] columnNames = {"Customer ID", "Brand", "Model", "Pick-Up", "Drop-Off", "Cost per Day ($)", "No. of Days", "Total Cost"};
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10;

        // Create and configure the booking table
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
        bookingScrollPane.setBounds(22, 104, 854, 167);
        add(bookingScrollPane);

        // Create and configure the loyalty booking table
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
        loyaltyScrollPane.setBounds(22, 325, 854, 167);
        add(loyaltyScrollPane);

        // Labels and buttons for UI
        title = new JLabel("Booking Summary", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setBounds(0, 11, 900, 51);
        add(title);

        normalBooking = new JLabel("Normal Booking");
        normalBooking.setFont(new Font("Tahoma", Font.PLAIN, 18));
        normalBooking.setBounds(22, 69, 151, 22);
        add(normalBooking);

        loyaltyBooking = new JLabel("Loyalty Booking");
        loyaltyBooking.setFont(new Font("Tahoma", Font.PLAIN, 18));
        loyaltyBooking.setBounds(22, 284, 141, 28);
        add(loyaltyBooking);

        // Labels to display total earnings and total loyalty points
        totalEarned = new JLabel("Total Earned:", JLabel.RIGHT);
        totalEarned.setFont(new Font("Tahoma", Font.PLAIN, 24));
        totalEarned.setBounds(12, 512, 250, 29);
        add(totalEarned);

        total = new JLabel("amount stated here", SwingConstants.LEFT);
        total.setFont(new Font("Tahoma", Font.PLAIN, 24));
        total.setBounds(274, 513, 147, 29);
        add(total);

        loyaltyPoints = new JLabel("Total No. of Points Used:");
        loyaltyPoints.setHorizontalAlignment(SwingConstants.RIGHT);
        loyaltyPoints.setFont(new Font("Tahoma", Font.PLAIN, 24));
        loyaltyPoints.setBounds(433, 512, 275, 28);
        add(loyaltyPoints);

        totalLoyaltyPoints = new JLabel("0");
        totalLoyaltyPoints.setFont(new Font("Tahoma", Font.PLAIN, 24));
        totalLoyaltyPoints.setBounds(715, 512, 161, 28);
        add(totalLoyaltyPoints);

        // Back button to return to previous view
        back = new JButton("Back");
        back.setFont(new Font("Tahoma", Font.PLAIN, 24));
        back.setBounds(197, 563, 240, 52);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent args0) {
                main.showListings();
            }
        });
        add(back);

        // Button to view individual booking details
        viewIndividual = new JButton("View Individual");
        viewIndividual.setFont(new Font("Tahoma", Font.PLAIN, 24));
        viewIndividual.setBounds(493, 563, 240, 52);
        viewIndividual.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent args0) {
                int numRows = bookingsTable.getSelectedRow();
                int numRows2 = loyaltyTable.getSelectedRow();

                if (numRows != -1) {
                    Summary sum = summaries.get(numRows);
                    main.showIndividualView(numRows, sum);
                } else if (numRows2 != -1) {
                    LoyaltySummary loyaltysum = loyaltysummaries.get(numRows2);
                    main.showLoyaltyIndividualView(numRows2, loyaltysum);
                }
            }
        });
        add(viewIndividual);

        // Update tables and calculate total values
        updateSummaryTable();
        updateLoyaltySummaryTable();
        calculateTotalAmount();
        calculateTotalPointsSpent();
    }

 // Update the booking summary table with data from DataStorage
    public void updateSummaryTable() {
        bookingsTableModel.setRowCount(0);

        // Get booking summaries from DataStorage
        this.summaries = DataStorage.getInstance().getBookingSummaries();
        
        // Formatter for date display
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Populate the booking summary table
        for (Summary summary : summaries) {
            String username = summary.getUserID(); 
            String brand = summary.getBrand();
            String carModel = summary.getModel();
            String pickUp = summary.getPickUpDate().format(dateFormatter);
            String dropOff = summary.getDropOffDate().format(dateFormatter);
            int costPerDay = summary.getCost();
            int noOfDay = summary.getNoOfDay();
            int totalCost = summary.getTotalCost();

            Object[] row = { username, brand, carModel, pickUp, dropOff, costPerDay, noOfDay, totalCost };
            bookingsTableModel.addRow(row);
        }
    }

    // Update the loyalty booking summary table with data from DataStorage
    public void updateLoyaltySummaryTable() {
        loyaltyTableModel.setRowCount(0);

        // Get loyalty booking summaries from DataStorage
        this.loyaltysummaries = DataStorage.getInstance().getLoyaltyBookingSummaries();
        
        // Formatter for date display
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Populate the loyalty booking summary table
        for (LoyaltySummary loyaltysummary : loyaltysummaries) {
            String username = loyaltysummary.getUserID(); 
            String brand = loyaltysummary.getBrand();
            String carModel = loyaltysummary.getModel();
            String pickUp = loyaltysummary.getPickUpDate().format(dateFormatter);
            String dropOff = loyaltysummary.getDropOffDate().format(dateFormatter);
            int costPerDay = loyaltysummary.getCost();
            int noOfDay = loyaltysummary.getNoOfDay();
            int totalCost = loyaltysummary.getTotalCost();

            Object[] row = {username, brand, carModel, pickUp, dropOff, costPerDay, noOfDay, totalCost};
            loyaltyTableModel.addRow(row);
        }
    }

    // Calculate and display the total amount earned from normal bookings
    private void calculateTotalAmount() {
        int totalAmount = 0;
        for (Summary summary : summaries) {
            totalAmount += summary.getTotalCost();
        }
        total.setText(Integer.toString(totalAmount));
    }

    // Calculate and display the total loyalty points spent
    private void calculateTotalPointsSpent() {
        int totalpoint = 0;
        for (LoyaltySummary loyaltysummary: loyaltysummaries) {
            totalpoint += (loyaltysummary.getTotalCost() * 10);
        }
        totalLoyaltyPoints.setText(Integer.toString(totalpoint));
    }

}
