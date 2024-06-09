package model;

public class Account {
	private String name;
	private String pass;
	private String email;
	private String Address;
	public Account() {
		
	}
	public Account(String name, String email, String pass, String address) {
		super();
		this.name = name;
		this.pass = pass;
		this.email = email;
		Address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	@Override
	public String toString() {
		return "Account [name=" + name + ", pass=" + pass + ", email=" + email + ", Address=" + Address + "]";
	}
	
}
