package controller;

import javax.swing.JFrame;

import data.Car;
import data.Customer;
import data.LoyaltySummary;
import data.Summary;

import java.awt.CardLayout;
import java.awt.Component;

import gui.Menu;
import gui.CustomerLogin;
import gui.CustomerRegister;
import gui.StaffLogin;
import gui.StaffReg;
import gui.BookCar;
import gui.Bookings;
import gui.EditBooking;
import gui.Listings;
import gui.Loyalty;
import gui.LoyaltyIndividualView;
import gui.AddListing;
import gui.EditListing;
import gui.BookingSummary;
import gui.IndividualView;


public class MainFrame extends JFrame {
    private CardLayout card;
    private Controller controller;
    private Customer customer;
    private BookCar bookCarPanel;
    
    public MainFrame() {
        this.card = new CardLayout();
        this.controller = new Controller();
        this.setSize(915, 700);
        this.setTitle("Fast Motor Rentals");
        getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(this.card);
        this.showMenu();
        this.setVisible(true);
    }
    
    // Getting the controller instance
    public Controller getController() {
        return controller;
    }
    
    // Showing the main menu panel
    public void showMenu() {
        Menu p1 = new Menu(this);
        this.add(p1, "Panel1");
        this.card.show(this.getContentPane(), "Panel1");
    }
    
    // Showing the add listing panel
    public void showAddListing() {
        AddListing p2 = new AddListing(this);
        this.add(p2, "Panel2");
        this.card.show(this.getContentPane(), "Panel2");
    } 
    
    // Showing the book car panel
    public void showBookCar(Customer loggedInCustomer) {
    	BookCar p3 = new BookCar(this, controller, loggedInCustomer);
        this.add(p3, "Panel3");
        this.card.show(this.getContentPane(), "Panel3");
    }
    
    // Showing the bookings panel
    public void showBookings(Customer loggedInCustomer) {
        Bookings p4 = new Bookings(this, loggedInCustomer);
        this.add(p4, "Panel4");
        this.card.show(this.getContentPane(), "Panel4");
    } 
    
    // Showing the booking summary panel
    public void showBookingSummary() {
        BookingSummary p5 = new BookingSummary(this);
        this.add(p5, "Panel5");
        this.card.show(this.getContentPane(), "Panel5");
    } 
    
    // Showing the customer login panel
    public void showCustomerLogin() {
        CustomerLogin p6 = new CustomerLogin(this);
        this.add(p6, "Panel6");
        this.card.show(this.getContentPane(), "Panel6");
    } 
    
    // Showing the customer registration panel
    public void showCustomerRegister() {
        CustomerRegister p7 = new CustomerRegister(this, controller);
        this.add(p7, "Panel7");
        this.card.show(this.getContentPane(), "Panel7");
    }
    
    // Showing the edit booking panel
    public void showEditBooking(Customer loggedInCustomer) {
        EditBooking p8 = new EditBooking(this, loggedInCustomer);
        this.add(p8, "Panel8");
        this.card.show(this.getContentPane(), "Panel8");
    } 
    
    // Showing the edit listing panel
    public void showEditListing(int ind, Car car) {
        EditListing p9 = new EditListing(this, ind, car);
        this.add(p9, "Panel9");
        this.card.show(this.getContentPane(), "Panel9");
    } 
    
    // Showing the individual view panel
    public void showIndividualView(int ind, Summary sum) {
        IndividualView p10 = new IndividualView(this, ind, sum);
        this.add(p10, "Panel10");
        this.card.show(this.getContentPane(), "Panel10");
    } 
    
    // Showing the listings panel
    public void showListings() {
        Listings p11 = new Listings(this);
        this.add(p11, "Panel11");
        this.card.show(this.getContentPane(), "Panel11");
    } 
    
    // Showing the staff login panel
    public void showStaffLogin() {
        StaffLogin p12 = new StaffLogin(this);
        this.add(p12, "Panel12");
        this.card.show(this.getContentPane(), "Panel12");
    } 
    
    // Showing the staff registration panel
    public void showStaffReg() {
        StaffReg p13 = new StaffReg(this);
        this.add(p13, "Panel13");
        this.card.show(this.getContentPane(), "Panel13");
    }
    
    // Showing the loyalty panel
    public void showLoyalty(Customer loggedInCustomer) {
    	Loyalty p14 = new Loyalty(this, controller, loggedInCustomer);
    	this.add(p14, "Panel14");
    	this.card.show(this.getContentPane(), "Panel14");
    }
    
    // Showing the loyalty individual view panel
    public void showLoyaltyIndividualView(int ind, LoyaltySummary loyaltysum) {
        LoyaltyIndividualView p15 = new LoyaltyIndividualView(this, ind, loyaltysum);
        this.add(p15, "Panel15");
        this.card.show(this.getContentPane(), "Panel15");
    } 

    // Getting the Bookings panel
    public Bookings getBookingsPanel() {
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof Bookings) {
                return (Bookings) component;
            }
        }
        return null;
    }
  
    public BookCar getBookCarPanel() {
        return bookCarPanel;
    }
    
    public static void main(String[] args) {
        // Get the current working directory
        String currentDirectory = System.getProperty("user.dir");
        
        // Construct the file paths using the current directory
        String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
        String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
        String staffFilePath = currentDirectory + "\\excel_data\\Staff.xlsx";
        String bookingFilePath = currentDirectory + "\\excel_data\\Booking.xlsx";
        String loyaltyBookingFilePath = currentDirectory + "\\excel_data\\LoyaltyBooking.xlsx";
        
        // Loading data from Excel files
        Controller.loadcars(carsFilePath);
        Controller.loadcustomer(customerFilePath);
        Controller.loadstaff(staffFilePath);
        Controller.loadbooking(bookingFilePath);
        Controller.loadloyaltybooking(loyaltyBookingFilePath);
    	
    	
        // Creating and displaying the main GUI frame
        MainFrame gui = new MainFrame();
    }
}
