package model.been;

import java.io.Serializable;

public class SupplierBeen implements Serializable {
	private int id;
	private String name;
	private int leadTimeDays;
	private String phone;
	private String email;
	
	public SupplierBeen(int id, String name, int leadTimeDays, String phone, String email) {
		this.id = id;
		this.name = name;
		this.leadTimeDays = leadTimeDays;
		this.phone = phone;
		this.email = email;
	}
	
	public SupplierBeen() {	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeadTimeDays() {
		return leadTimeDays;
	}

	public void setLeadTimeDays(int leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
