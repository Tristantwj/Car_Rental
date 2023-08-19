package data;

public class Staff {
	
	private String Sname;
	private String Spwd;

	public Staff(String sn, String sp) {
		this.Sname =sn;
		this.Spwd = sp;
	}

	public String getSName() {
	 	 return Sname; 
	}
	
	public void setSName(String sn) { 
		 this.Sname = sn; 
	}

	public String getSPwd() {
	 	 return Spwd; 
	}

	public void setSPwd(String sp) { 
		 this.Spwd = sp; 
	} 
}
