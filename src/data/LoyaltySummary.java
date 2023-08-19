package data;

import java.time.LocalDate;

public class LoyaltySummary {
    private String userid;
    private String brand;
    private String model;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private int cost;
    private int NoOfDay;
    private int totalcost;
    
    public LoyaltySummary(String id, String b, String m, LocalDate pd, LocalDate dd, int c, int nd, int tc) {
        this.userid = id;
        this.brand = b;
        this.model = m;
        this.pickUpDate = pd;
        this.dropOffDate = dd;
        this.cost = c;
        this.NoOfDay = nd;
        this.totalcost = tc;
    }

	public String getUserID() {
        return userid;
    }

    public void setUserID(String id) {
        this.userid= id;
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

    public void setCost(Integer c) {
        this.cost = c;
    }
    
    public int getTotalCost() {
    	return totalcost;
    }
    public void setTotalCost(Integer tc){
    	this.totalcost = tc;
    }
    
    public int getNoOfDay() {
    	return NoOfDay;
    }
    public void setNoOfDay(Integer nd){
    	this.NoOfDay = nd;
    }
}
