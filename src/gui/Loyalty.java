package gui;

import javax.swing.JPanel;

import controller.Controller;
import controller.MainFrame;
import data.Car;
import data.Customer;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;

public class Loyalty extends JPanel{
	private MainFrame main;
	private Controller controller;
    private Customer loggedInCustomer;
    private JTable listingsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private Vector<Car> carvec;
    private JDateChooser pickUpDateChooser;
    private JDateChooser dropOffDateChooser;
    private JLabel title;
    private JLabel pickUp;
    private JLabel dropOff;
    private JLabel userNotif;
    private JLabel sortBy;
    private JLabel insufficient;
    private JLabel loyaltyPoints;
    private JLabel points;
    private JLabel conversion;
    private JLabel img;
    private JButton sortByCost;
    private JButton back;
    private JButton book;
    private Timer timer;
    private int sort;
    
	public Loyalty (MainFrame main, Controller controller, Customer loggedInCustomer){
		this.main = main;
		this.controller = controller;
        this.loggedInCustomer = loggedInCustomer;
        setBounds(100, 100, 900, 650);
		setLayout(null);
		setBackground(new Color(173, 216, 230));
		populateCarVector();
        this.sort = 0;
        
        String[] columnNames = { "Brand", "Model", "Cost per Day ($)" };
        int desiredRowHeight = new JTable().getFontMetrics(new Font("Tahoma", Font.PLAIN, 18)).getHeight() + 10; // Adjust 10 as needed

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
        
		scrollPane = new JScrollPane();
		scrollPane.setBounds(378, 97, 470, 211);
		scrollPane.setViewportView(listingsTable);
		add(scrollPane);
        
		title = new JLabel("Loyalty Program", JLabel.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBounds(0, 25, 900, 49);
		add(title);
		
		pickUp = new JLabel("Pick-Up Date:", SwingConstants.LEFT);
		pickUp.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pickUp.setBounds(58, 86, 163, 51);
		add(pickUp);
		
		pickUpDateChooser = new JDateChooser();
		pickUpDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
		pickUpDateChooser.setDateFormatString("dd/MM/yyyy");
		pickUpDateChooser.setBounds(58, 139, 262, 52);
		add(pickUpDateChooser);
		
		dropOff = new JLabel("Drop-Off Date:", SwingConstants.LEFT);
		dropOff.setFont(new Font("Tahoma", Font.PLAIN, 24));
		dropOff.setBounds(58, 193, 262, 52);
		add(dropOff);
		
		dropOffDateChooser = new JDateChooser();
		dropOffDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 24));
		dropOffDateChooser.setDateFormatString("dd/MM/yyyy");
		dropOffDateChooser.setBounds(58, 243, 262, 52);
		add(dropOffDateChooser);
		
		back = new JButton("Back");
		back.setFont(new Font("Tahoma", Font.PLAIN, 24));
		back.setBounds(58, 508, 262, 52);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.showBookCar(loggedInCustomer);
			}
		});
		add(back);
		
		loyaltyPoints = new JLabel("Loyalty Points:");
		loyaltyPoints.setFont(new Font("Tahoma", Font.PLAIN, 24));
		loyaltyPoints.setBounds(58, 396, 164, 36);
		add(loyaltyPoints);
		
		points = new JLabel("Points Shown here");
		points.setFont(new Font("Tahoma", Font.PLAIN, 24));
		points.setBounds(234, 396, 163, 36);
		add(points);
		
		conversion = new JLabel("10 Loyalty Point = $1");
		conversion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		conversion.setBounds(58, 445, 191, 29);
		add(conversion);
		
		timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the "confirm" label and stop the timer
                userNotif.setVisible(false);
                timer.stop();
            }
        });
		
		sortBy = new JLabel("Sort By: ");
		sortBy.setFont(new Font("Tahoma", Font.PLAIN, 24));
		sortBy.setBounds(378, 321, 108, 40);
		add(sortBy);
		
		sortByCost = new JButton("Cost");
		sortByCost.setFont(new Font("Tahoma", Font.PLAIN, 24));
		sortByCost.setBounds(497, 321, 163, 42);
		sortByCost.addActionListener(new ActionListener() {
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
		add(sortByCost);
		
		book = new JButton("Book");		
		book.setFont(new Font("Tahoma", Font.PLAIN, 24));
		book.setBounds(58, 316, 262, 52);
		book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
                    
                    int loyaltycost = totalcost*10;
                    int customerPoints = loggedInCustomer.getPoint();
                    
                    if(customerPoints >= loyaltycost) {
                    	
                    	int newPoints = customerPoints - loyaltycost;
                    	updateCustomerPoints(newPoints);
                    	
                    	
                    	Car selectedCar = new Car(selectedBrand, selectedModel, cost, imagePath);
                    	
                    	main.getController().deleteCar(selectedRow, selectedCar);

                    	controller.addLoyaltyBooking(loggedInCustomer, selectedBrand, selectedModel, imagePath, pickUpDatea, dropOffDatea, cost, NoOfDay, totalcost);
                    
                    	String currentDirectory = System.getProperty("user.dir");
                        String carsFilePath = currentDirectory + "\\excel_data\\Cars.xlsx";
                        String customerFilePath = currentDirectory + "\\excel_data\\Customer.xlsx";
                        String loyaltyBookingFilePath = currentDirectory + "\\excel_data\\LoyaltyBooking.xlsx";
                        
                        main.getController().savecustomers(customerFilePath);
                        main.getController().saveloyaltybookings(loyaltyBookingFilePath);
                        main.getController().savecars(carsFilePath);
                    	userNotif.setText("Booking confirmed!");
                    	userNotif.setVisible(true);
                    	timer.start();

                    	updateCarListingsTable();
                    	} 
                    else if(customerPoints < loyaltycost){
                    	userNotif.setText("Insufficient points");
                        userNotif.setForeground(Color.RED);
                    	userNotif.setVisible(true);
                        timer.start();
                    }
                    	
                    else {
                		userNotif.setText("No car selected");
                		userNotif.setVisible(true);
                		timer.start();
                    	}
                }    
			}
		});
		add(book);
		
		insufficient = new JLabel("");
		insufficient.setFont(new Font("Tahoma", Font.PLAIN, 24));
		insufficient.setBounds(55, 370, 265, 29);
		add(insufficient);
		
		
		userNotif = new JLabel("");
		userNotif.setHorizontalAlignment(SwingConstants.CENTER);
		userNotif.setFont(new Font("Tahoma", Font.BOLD, 29));
		userNotif.setBounds(0, 568, 900, 51);
		add(userNotif);
		
		img = new JLabel("", SwingConstants.CENTER);
		img.setBounds(378, 370, 470, 193);
		add(img);
		
        updatePointsLabel();
		updateCarListingsTable();
	}
	
	public void updateCarListingsTable() {
        tableModel.setRowCount(0);

        Vector<Car> availableCars = main.getController().getAvailableCarsForCustomer(loggedInCustomer);

        for (Car car : availableCars) {
            Object[] row = {car.getBrand(), car.getModel(), car.getCostPerDay()};
            tableModel.addRow(row);
        }
    }
	private void updatePointsLabel() {
        int customerPoints = loggedInCustomer.getPoint();
        points.setText(Integer.toString(customerPoints));
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            updateCarListingsTable();
            updatePointsLabel(); 
        }
    }
    
    private void updateCustomerPoints(int newPoints) {
        loggedInCustomer.setPoint(newPoints);
        updatePointsLabel();
    }
    
    private void populateCarVector() {
        carvec = main.getController().getAvailableCarsForCustomer(loggedInCustomer);
    }
    
    private void updateImage(int selectedRow) {
        if (carvec != null && !carvec.isEmpty() && selectedRow >= 0 && selectedRow < carvec.size()) {
            String imagePath = carvec.get(selectedRow).getImagePath();
            ImageIcon imageIcon = new ImageIcon(imagePath);
            img.setIcon(imageIcon);
        } else {
            img.setIcon(null);
        }
    }
    
    private void refreshTable(int selectedRow) {
        tableModel.setRowCount(0); 
        for (int i = 0; i < carvec.size(); i++) {
            Car op = carvec.get(i);
            tableModel.addRow(new Object[]{op.getBrand(), op.getModel(), op.getCostPerDay()});
        }
        listingsTable.setModel(tableModel);
        tableModel.fireTableDataChanged();
        updateImage(selectedRow);
    }
}
