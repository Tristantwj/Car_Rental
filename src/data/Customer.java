package data;

public class Customer{
    private String Cname;
    private String Cpwd;
    private int point;
    
    public Customer(String cn, String cp, int p){
        this.Cname = cn;
        this.Cpwd = cp;
        this.point = p;
    }

    public String getCName(){
        return Cname;
    }

    public void setCName(String cn){
        this.Cname = cn;
    }

    public String getCPwd(){
        return Cpwd;
    }

    public void setCPwd(String cp){
        this.Cpwd = cp;
    }      
    
    public int getPoint() {
    	return point;
    }
    
    public void setPoint(int p) {
    	this.point = p;
    }
}
