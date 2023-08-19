package controller;

import data.Booking;
import data.Car;
import data.Customer;
import data.DataStorage;
import data.LoyaltyBooking;
import data.Staff;
import com.toedter.calendar.JDateChooser;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JTextField;

public class Controller implements Comparator<Car> {

    @Override
    public int compare(Car a, Car b) {
        return Integer.compare(a.getCostPerDay(), b.getCostPerDay());
    }

    private DataStorage dataStorage = new DataStorage();

    // Adding a new staff member
    public void addStaff(String sn, String sp) {
        Staff sta = new Staff(sn, sp);
        sta.setSName(sn);
        sta.setSPwd(sp);
        dataStorage.storeStaffs(sta);
    }

    // Verifying staff credentials
    public boolean verifyStaff(String sn, String sp) {
        Staff t = dataStorage.getStaff(sn);
        return t != null && t.getSPwd().equals(sp);
    }

    // Adding a new customer
    public void addCustomer(String cn, String cp, int p) {
        Customer cus = new Customer(cn, cp, p);
        cus.setCName(cn);
        cus.setCPwd(cp);
        dataStorage.storeCustomers(cus);
    }

    // Verifying customer credentials
    public boolean verifyCustomer(String cn, String cp) {
        Customer t = dataStorage.getCustomers(cn);
        return t != null && t.getCPwd().equals(cp);
    }

    // Adding a new car
    public void addCar(String b, String m, int c, String i) {
        Car cc = new Car(b, m, c, i);
        cc.setBrand(b);
        cc.setModel(m);
        cc.setCostPerDay(c);
        cc.setImagePath(i);
        dataStorage.storeCar(cc);
    }

    // Getting all cars
    public Vector<Car> getAllCars() {
        return dataStorage.getAllCars();
    }

    // Editing car details
    public void editCar(int index, Car newcar) {
        dataStorage.editCar(index, newcar);
    }

    // Deleting a car
    public void deleteCar(int numRows, Car c) {
        dataStorage.deleteCar(numRows, c);
    }

    // Adding a booking
    public void addBooking(Customer cus, String b, String m, String i, LocalDate pd, LocalDate dd, int c, int nd, int tc) {
        Booking book = new Booking(cus, b, m, i, pd, dd, c, nd, tc);
        book.setCustomer(cus);
        book.setBrand(b);
        book.setModel(m);
        book.setImagePath(i);
        book.setPickUpDate(pd);
        book.setDropOffDate(dd);
        book.setCost(c);
        book.setNoOfDay(nd);
        book.setTotalCost(tc);
        dataStorage.storeBooking(book, cus);
    }

    // Getting all bookings for a customer
    public Booking[] getAllBookings(Customer loggedInCustomer) {
        return dataStorage.getAllBooking(loggedInCustomer);
    }

    // Editing a booking
    public void editBooking(int index, Booking newBooking, Customer loggedInCustomer) {
        dataStorage.editBooking(index, newBooking, loggedInCustomer);
    }

    // Deleting a booking
    public void deleteBooking(Booking b, Customer loggedInCustomer) {
        dataStorage.deleteBooking(b, loggedInCustomer);
    }

    // Adding a loyalty booking
    public void addLoyaltyBooking(Customer cus, String b, String m, String i, LocalDate pd, LocalDate dd, int c, int nd, int tc) {
        LoyaltyBooking loyalbook = new LoyaltyBooking(cus, b, m, i, pd, dd, c, nd, tc);
        loyalbook.setCustomer(cus);
        loyalbook.setBrand(b);
        loyalbook.setModel(m);
        loyalbook.setImagePath(i);
        loyalbook.setPickUpDate(pd);
        loyalbook.setDropOffDate(dd);
        loyalbook.setCost(c);
        loyalbook.setNoOfDay(nd);
        loyalbook.setTotalCost(tc);
        dataStorage.storeLoyaltyBooking(loyalbook, cus);
    }

    // Clearing date fields
    public void clearFields(JDateChooser pickupdate, JDateChooser dropoffdate) {
        pickupdate.setCalendar(null);
        dropoffdate.setCalendar(null);
    }

    // Clearing text fields
    public static void clearFields(JTextField username, JTextField password) {
        username.setText("");
        password.setText("");
    }

    // Getting a customer by username
    public Customer getCustomerByUsername(String username) {
        return dataStorage.getCustomers(username);
    }

    // Getting available cars for a customer
    public Vector<Car> getAvailableCarsForCustomer(Customer customer) {
        Vector<Car> allCars = dataStorage.getAllCars();
        Booking[] bookings = dataStorage.getAllBooking(customer);

        for (Booking booking : bookings) {
            Car bookedCar = new Car(booking.getBrand(), booking.getModel(), booking.getCost(), booking.getImagePath());
            allCars.remove(bookedCar);
        }

        return allCars;
    }

    // Calculating cost based on cost per day and number of days
    public static int calculateCost(int costPerDay, long numberOfDays) {
        return costPerDay * (int) numberOfDays;
    }

    // Calculating the number of days between two dates
    public static long calculateNumberOfDays(LocalDate pickUpDate, LocalDate dropOffDate) {
        long numberOfDays = ChronoUnit.DAYS.between(pickUpDate, dropOffDate) + 1;
        return numberOfDays;
    }

    // Calculating cost based on cost per day and number of days (alternate method)
    public static int calculateCost(int costPerDay, int numDays) {
        return costPerDay * numDays;
    }

    // Calculating the number of days between two dates (alternate method)
    public static int daysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    
    // Loading data from Excel files
    public static void loadcars(String excelFilePath) {
        DataStorage.loadCarsFromExcel(excelFilePath);
    }

    public static void loadcustomer(String excelFilePath) {
        DataStorage.loadCustomersFromExcel(excelFilePath);
    }

    public static void loadstaff(String excelFilePath) {
        DataStorage.loadStaffFromExcel(excelFilePath);
    }

    public static void loadbooking(String excelFilePath) {
        DataStorage.loadBookingsFromExcel(excelFilePath);
    }

    public static void loadloyaltybooking(String excelFilePath) {
        DataStorage.loadLoyaltyBookingsFromExcel(excelFilePath);
    }

    // Saving data to Excel files
    public void savecars(String excelFilePath) {
        DataStorage.saveCarsToExcel(excelFilePath);
    }

    public void savecustomers(String excelFilePath) {
        DataStorage.saveCustomersToExcel(excelFilePath);
    }

    public void savestaffs(String excelFilePath) {
        DataStorage.saveStaffToExcel(excelFilePath);
    }

    public void savebookings(String excelFilePath) {
        DataStorage.saveBookingsToExcel(excelFilePath);
    }

    public void saveloyaltybookings(String excelFilePath) {
        DataStorage.saveLoyaltyBookingsToExcel(excelFilePath);
    }

    // Checking if a staff username is available
    public boolean isStaffUsernameAvailable(String username) {
        Staff existingStaff = dataStorage.getStaff(username);
        return existingStaff == null;
    }

    // Checking if a customer username is available
    public boolean isCustomerUsernameAvailable(String username) {
        Customer existingCustomer = dataStorage.getCustomers(username);
        return existingCustomer == null;
    }

}
