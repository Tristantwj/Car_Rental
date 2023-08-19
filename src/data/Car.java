package data;

public class Car {
	private String Brand;
	private String Model;
	private Integer CostPerDay;
	private String imagePath;  
	
	public Car(String b, String m , int cpd, String imagePath) {
		this.Brand = b;
		this.Model = m;
		this.CostPerDay = cpd;
		this.imagePath = imagePath; 
	}
	
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String b) {
		this.Brand = b;
	}
	
	public String getModel() {
		return Model;
	}
	public void setModel(String m){
		this.Model = m;
	}

	public int getCostPerDay(){
		return CostPerDay;
	}
	public void setCostPerDay(Integer cpd){
		this.CostPerDay = cpd;
	}
	
	public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String img) {
        this.imagePath = img;
    }
}
