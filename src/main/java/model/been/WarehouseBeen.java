package model.been;

import java.io.Serializable;

public class WarehouseBeen implements Serializable {
	private int id;
	private String name;
	private String location;
	
	public WarehouseBeen(int id, String name, String location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}
	
	public WarehouseBeen() {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
