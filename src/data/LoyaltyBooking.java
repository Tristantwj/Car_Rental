package data;

import java.time.LocalDate;

public class LoyaltyBooking {
    private Customer customer;
    private String brand;
    private String model;
    private String imagePath;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private int cost;
    private int noOfDay;
    private int totalCost;
    
    public LoyaltyBooking(Customer cus, String b, String m, String i, LocalDate pd, LocalDate dd, int c, int nd, int tc) {
        this.customer = cus;
        this.brand = b;
        this.model = m;
        this.imagePath = i;
        this.pickUpDate = pd;
        this.dropOffDate = dd;
        this.cost = c;
        this.noOfDay = nd;
        this.totalCost = tc;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer cus) {
        this.customer = cus;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String b) {
        this.brand = b;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String m) {
        this.model = m;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String i) {
        this.imagePath = i;
    }


    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pd) {
        this.pickUpDate = pd;
    }

    public LocalDate getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(LocalDate dd) {
        this.dropOffDate = dd;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int c) {
        this.cost = c;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int tc) {
        this.totalCost = tc;
    }

    public int getNoOfDay() {
        return noOfDay;
    }

    public void setNoOfDay(int nd) {
        this.noOfDay = nd;
    }
}
