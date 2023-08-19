package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataStorage{
	// Define data storage containers
    private static Vector<Staff> staffs = new Vector<>();
    private static Vector<Car> cars = new Vector<>();
    private static Vector<Customer> customers = new Vector<>();
    private static Map<Customer, Vector<Booking>> bookings = new HashMap<>();
    private static Map<Customer, Vector<LoyaltyBooking>> loyaltybookings = new HashMap<>();
    
    // Singleton instance for DataStorage
    private static DataStorage instance;

    // Function to save cars vector to an Excel file
    public static void saveCarsToExcel(String excelFilePath) {
        // Create a new workbook and sheet for cars
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Cars");

        // Loop through cars and populate the sheet
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            XSSFRow row = sheet.createRow(i);
            // Set cell values for brand, model, cost per day, and image path
            row.createCell(0).setCellValue(car.getBrand());
            row.createCell(1).setCellValue(car.getModel());
            row.createCell(2).setCellValue(car.getCostPerDay());
            row.createCell(3).setCellValue(car.getImagePath());
        }

        // Write the workbook to the output stream
        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Function to load cars vector from an Excel file
    public static void loadCarsFromExcel(String excelFilePath) {
        try {
            XSSFWorkbook work = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = work.getSheet("Cars");

            if (sheet == null) {
                System.out.println("Sheet 'Cars' not found in the Excel file.");
                return;
            }

            cars.clear(); // Clear the existing cars vector

            int numRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                    String brand = row.getCell(0).getStringCellValue();
                    String model = row.getCell(1).getStringCellValue();
                    int costPerDay = (int) row.getCell(2).getNumericCellValue();
                    String images = row.getCell(3).getStringCellValue();
                    
                    Car car = new Car(brand, model, costPerDay, images);
                    cars.add(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Function to save customers to an Excel file
    public static void saveCustomersToExcel(String excelFilePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Customer");

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(customer.getCName());
            row.createCell(1).setCellValue(customer.getCPwd());
            row.createCell(2).setCellValue(customer.getPoint());
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Function to load customers from an Excel file
    public static void loadCustomersFromExcel(String excelFilePath) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = workbook.getSheet("Customer");

            if (sheet == null) {
                System.out.println("Sheet 'Customers' not found in the Excel file.");
                return;
            }

            customers.clear(); // Clear the existing customers vector

            int numRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                    String cn = row.getCell(0).getStringCellValue();
                    String cp = row.getCell(1).getStringCellValue();
                    int p = (int) row.getCell(2).getNumericCellValue();

                    Customer customer = new Customer(cn, cp, p);
                    customers.add(customer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 // Function to save staff members to an Excel file
    public static void saveStaffToExcel(String excelFilePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Staff");

        for (int i = 0; i < staffs.size(); i++) {
            Staff staff = staffs.get(i);
            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(staff.getSName());
            row.createCell(1).setCellValue(staff.getSPwd());
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Function to load staff members from an Excel file
    public static void loadStaffFromExcel(String excelFilePath) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = workbook.getSheet("Staff");

            if (sheet == null) {
                System.out.println("Sheet 'Staff' not found in the Excel file.");
                return;
            }

            staffs.clear(); // Clear the existing staffs vector

            int numRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null && row.getCell(1) != null) {
                    String sn = row.getCell(0).getStringCellValue();
                    String sp = row.getCell(1).getStringCellValue();

                    Staff staff = new Staff(sn, sp);
                    staffs.add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 // Function to save bookings to an Excel file
    public static void saveBookingsToExcel(String excelFilePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Booking");

        for (Map.Entry<Customer, Vector<Booking>> entry : bookings.entrySet()) {
            Customer customer = entry.getKey();
            Vector<Booking> customerBookings = entry.getValue();

            for (int i = 0; i < customerBookings.size(); i++) {
                Booking booking = customerBookings.get(i);
                XSSFRow row = sheet.createRow(sheet.getPhysicalNumberOfRows());
                row.createCell(0).setCellValue(customer.getCName());
                row.createCell(1).setCellValue(booking.getBrand());
                row.createCell(2).setCellValue(booking.getModel());
                row.createCell(3).setCellValue(booking.getImagePath());
                row.createCell(4).setCellValue(booking.getPickUpDate().toString());
                row.createCell(5).setCellValue(booking.getDropOffDate().toString());
                row.createCell(6).setCellValue(booking.getCost());
                row.createCell(7).setCellValue(booking.getNoOfDay());
                row.createCell(8).setCellValue(booking.getTotalCost());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Function to load bookings from an Excel file
    public static void loadBookingsFromExcel(String excelFilePath) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = workbook.getSheet("Booking");

            if (sheet == null) {
                System.out.println("Sheet 'Bookings' not found in the Excel file.");
                return;
            }

            bookings.clear(); // Clear the existing bookings map

            int numRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                    String customerName = row.getCell(0).getStringCellValue();
                    String brand = row.getCell(1).getStringCellValue();
                    String model = row.getCell(2).getStringCellValue();
                    String images = row.getCell(3).getStringCellValue();
                    LocalDate pickUpDate = LocalDate.parse(row.getCell(4).getStringCellValue());
                    LocalDate dropOffDate = LocalDate.parse(row.getCell(5).getStringCellValue());
                    int cost = (int) row.getCell(6).getNumericCellValue();
                    int noOfDay = (int) row.getCell(7).getNumericCellValue();
                    int totalCost = (int) row.getCell(8).getNumericCellValue();

                    Customer customer = getCustomers(customerName); // Assuming you have a getCustomers method
                    if (customer != null) {
                        Booking booking = new Booking(customer, brand, model, images, pickUpDate, dropOffDate, cost, noOfDay, totalCost);
                        Vector<Booking> customerBookings = bookings.getOrDefault(customer, new Vector<>());
                        customerBookings.add(booking);
                        bookings.put(customer, customerBookings);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    // Function to save bookings to an Excel file
    public static void saveLoyaltyBookingsToExcel(String excelFilePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Loyalty");

        for (Map.Entry<Customer, Vector<LoyaltyBooking>> entry : loyaltybookings.entrySet()) {
            Customer customer = entry.getKey();
            Vector<LoyaltyBooking> customerLoyaltyBookings = entry.getValue();

            for (int i = 0; i < customerLoyaltyBookings.size(); i++) {
                LoyaltyBooking loyaltybooking = customerLoyaltyBookings.get(i);
                XSSFRow row = sheet.createRow(sheet.getPhysicalNumberOfRows());
                row.createCell(0).setCellValue(customer.getCName());
                row.createCell(1).setCellValue(loyaltybooking.getBrand());
                row.createCell(2).setCellValue(loyaltybooking.getModel());
                row.createCell(3).setCellValue(loyaltybooking.getImagePath());
                row.createCell(4).setCellValue(loyaltybooking.getPickUpDate().toString());
                row.createCell(5).setCellValue(loyaltybooking.getDropOffDate().toString());
                row.createCell(6).setCellValue(loyaltybooking.getCost());
                row.createCell(7).setCellValue(loyaltybooking.getNoOfDay());
                row.createCell(8).setCellValue(loyaltybooking.getTotalCost());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 // Function to load bookings from an Excel file
    public static void loadLoyaltyBookingsFromExcel(String excelFilePath) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = workbook.getSheet("Loyalty");

            if (sheet == null) {
                System.out.println("Sheet 'LoyaltyBookings' not found in the Excel file.");
                return;
            }

            loyaltybookings.clear(); // Clear the existing bookings map

            int numRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numRows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null && row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null) {
                	String customerName = row.getCell(0).getStringCellValue();
                    String brand = row.getCell(1).getStringCellValue();
                    String model = row.getCell(2).getStringCellValue();
                    String images = row.getCell(3).getStringCellValue();
                    LocalDate pickUpDate = LocalDate.parse(row.getCell(4).getStringCellValue());
                    LocalDate dropOffDate = LocalDate.parse(row.getCell(5).getStringCellValue());
                    int cost = (int) row.getCell(6).getNumericCellValue();
                    int noOfDay = (int) row.getCell(7).getNumericCellValue();
                    int totalCost = (int) row.getCell(8).getNumericCellValue();

                    Customer customer = getCustomers(customerName); // Assuming you have a getCustomers method
                    if (customer != null) {
                        LoyaltyBooking loyaltybooking = new LoyaltyBooking(customer, brand, model, images, pickUpDate, dropOffDate, cost, noOfDay, totalCost);
                        Vector<LoyaltyBooking> customerLoyaltyBookings = loyaltybookings.getOrDefault(customer, new Vector<>());
                        customerLoyaltyBookings.add(loyaltybooking);
                        loyaltybookings.put(customer, customerLoyaltyBookings);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Static block to initialize the bookings map
    static {
        bookings = new HashMap<>();
    }
    static {
    	loyaltybookings = new HashMap<>();
    }

    public static void storeStaffs(Staff sta) {
        staffs.add(sta);
    }

    public static Staff getStaff(String sn) {
        for (int i = 0; i < staffs.size(); i++) {
            Staff temp = staffs.get(i);
            if (temp.getSName().equals(sn)) {
                return temp;
            }
        }
        return null;
    }

    public static void storeCustomers(Customer cus) {
        customers.add(cus);
    }

    public static Customer getCustomers(String cn) {
        for (int i = 0; i < customers.size(); i++) {
            Customer temp = customers.get(i);
            if (temp.getCName().equals(cn)) {
                return temp;
            }
        }
        return null;
    }

    public static void storeCar(Car cc) {
        cars.add(cc);
    }

    public static Vector<Car> getAllCars() {
        return cars;
    }

    public static void editCar(int index, Car newcar) {
        cars.setElementAt(newcar, index);
    }

    public static void deleteCar(int numRows, Car s) {
        cars.remove(numRows);
        cars.remove(s);
    }

    public static void storeBooking(Booking book, Customer loggedInCustomer) {
        Customer customerName = loggedInCustomer;
        Vector<Booking> customerBookingsList = bookings.getOrDefault(customerName, new Vector<>());
        customerBookingsList.add(book);
        bookings.put(customerName, customerBookingsList);
    }
    
    public static void storeLoyaltyBooking(LoyaltyBooking book, Customer loggedInCustomer) {
        Customer customerName = loggedInCustomer;
        Vector<LoyaltyBooking> customerloyaltyBookingsList = loyaltybookings.getOrDefault(customerName, new Vector<>());
        customerloyaltyBookingsList.add(book);
        loyaltybookings.put(customerName, customerloyaltyBookingsList);
    }

    public static Booking[] getAllBooking(Customer customer) {
        if (bookings.containsKey(customer)) {
            Vector<Booking> customerBookingsList = bookings.get(customer);
            return customerBookingsList.toArray(new Booking[0]);
        }
        return new Booking[0];
    }
    
    
    public static LoyaltyBooking[] getAllLoyaltyBooking(Customer customer) {
        if (loyaltybookings.containsKey(customer)) {
            Vector<LoyaltyBooking> customerBookingsList = loyaltybookings.get(customer);
            return customerBookingsList.toArray(new LoyaltyBooking[0]);
        }
        return new LoyaltyBooking[0];
    }

    public static void editBooking(int index, Booking newBooking, Customer loggedInCustomer) {
        Customer customerName = loggedInCustomer;
        Vector<Booking> customerBookingsList = bookings.getOrDefault(customerName, new Vector<>());
        customerBookingsList.setElementAt(newBooking, index);
        bookings.put(customerName, customerBookingsList);
    }

    public static void deleteBooking(Booking booking, Customer customer) {
        if (bookings.containsKey(customer)) {
            bookings.get(customer).remove(booking);
        }
    }
    public static void deleteLoyaltyBooking(LoyaltyBooking loyaltybooking, Customer customer) {
        if (loyaltybookings.containsKey(customer)) {
        	loyaltybookings.get(customer).remove(loyaltybooking);
        }
    }

    public static void updateBooking(Booking booking, Customer customer) {
        if (bookings.containsKey(customer)) {
            Vector<Booking> customerBookingsList = bookings.get(customer);
            int index = customerBookingsList.indexOf(booking);
            if (index != -1) {
                customerBookingsList.set(index, booking);
            }
        }
    }

	public static void updateTableWithBookingData(DefaultTableModel tableModel, Customer loggedInCustomer) {
        tableModel.setRowCount(0);
        Booking[] allBookings = getAllBooking(loggedInCustomer);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Booking booking : allBookings) {
            String carBrand = booking.getBrand();
            String carModel = booking.getModel();
            String pickUpDate = booking.getPickUpDate().format(formatter); // Format pickUpDate to dd/MM/yyyy
            String dropOffDate = booking.getDropOffDate().format(formatter); // Format dropOffDate to dd/MM/yyyy
            int costPerDay = booking.getCost();
            int NoOfDay = booking.getNoOfDay();
            int totalCost = booking.getTotalCost();
            

            Vector<String> rowData = new Vector<>();
            rowData.add(carBrand);
            rowData.add(carModel);
            rowData.add(pickUpDate);
            rowData.add(dropOffDate);
            rowData.add(Integer.toString(costPerDay));
            rowData.add(Integer.toString(NoOfDay));
            rowData.add(Integer.toString(totalCost));
            
            tableModel.addRow(rowData);
        }
    }
	public static void updateTableWithLoyaltyBookingData(DefaultTableModel tableModel, Customer loggedInCustomer) {
        tableModel.setRowCount(0);
        LoyaltyBooking[] allLoyaltyBookings = getAllLoyaltyBooking(loggedInCustomer);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (LoyaltyBooking loyaltybooking : allLoyaltyBookings) {
            String carBrand = loyaltybooking.getBrand();
            String carModel = loyaltybooking.getModel();
            String pickUpDate = loyaltybooking.getPickUpDate().format(formatter); // Format pickUpDate to dd/MM/yyyy
            String dropOffDate = loyaltybooking.getDropOffDate().format(formatter); // Format dropOffDate to dd/MM/yyyy
            int costPerDay = loyaltybooking.getCost();
            int NoOfDay = loyaltybooking.getNoOfDay();
            int totalCost = loyaltybooking.getTotalCost();

            Vector<String> rowData = new Vector<>();
            rowData.add(carBrand);
            rowData.add(carModel);
            rowData.add(pickUpDate);
            rowData.add(dropOffDate);
            rowData.add(Integer.toString(costPerDay));
            rowData.add(Integer.toString(NoOfDay));
            rowData.add(Integer.toString(totalCost));

            tableModel.addRow(rowData);
        }
    }
	
	
    public static DataStorage getInstance() {
        if (instance == null) {
            synchronized (DataStorage.class) {
                if (instance == null) {
                    instance = new DataStorage();
                }
            }
        }
        return instance;
    }
	
    public Vector<Summary> getBookingSummaries() {
        Vector<Summary> summaries = new Vector<>();

        for (Customer customer : bookings.keySet()) {
            String customerId = customer.getCName();
            Vector<Booking> customerBookingsList = bookings.get(customer);

            for (Booking booking : customerBookingsList) {
                String username = customerId;
                String brand = booking.getBrand();
                String model = booking.getModel();
                LocalDate pickUpDate = booking.getPickUpDate();
                LocalDate dropOffDate = booking.getDropOffDate();
                int cost = booking.getCost();
                int noOfDay = booking.getNoOfDay();
                int totalCost = booking.getTotalCost();

                // Create a Summary object with the booking details
                Summary summary = new Summary(username, brand, model, pickUpDate, dropOffDate, cost, noOfDay, totalCost);
                summaries.add(summary);
            }
        }

        return summaries;
    }
    
    public Vector<LoyaltySummary> getLoyaltyBookingSummaries() {
        Vector<LoyaltySummary> loyaltysummaries = new Vector<>();

        for (Customer customer : loyaltybookings.keySet()) {
            String customerId = customer.getCName();
            Vector<LoyaltyBooking> customerLoyaltyBookingsList = loyaltybookings.get(customer);

            for (LoyaltyBooking loyaltybooking : customerLoyaltyBookingsList) {
                String username = customerId;
                String brand = loyaltybooking.getBrand();
                String model = loyaltybooking.getModel();
                LocalDate pickUpDate = loyaltybooking.getPickUpDate();
                LocalDate dropOffDate = loyaltybooking.getDropOffDate();
                int cost = loyaltybooking.getCost();
                int noOfDay = loyaltybooking.getNoOfDay();
                int totalCost = loyaltybooking.getTotalCost();

                // Create a Summary object with the booking details
                LoyaltySummary loyaltysummary = new LoyaltySummary(username, brand, model, pickUpDate, dropOffDate, cost, noOfDay, totalCost);
                loyaltysummaries.add(loyaltysummary);
            }
        }

        return loyaltysummaries;
    }
    
}