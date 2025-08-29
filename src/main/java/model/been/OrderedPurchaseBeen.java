package model.been;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderedPurchaseBeen implements Serializable{
	private int id;
	private String supplierName;
	private LocalDateTime orderedAt;
	
	public OrderedPurchaseBeen(int id, String supplierName, LocalDateTime orderedAt) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.orderedAt = orderedAt;
	}
	
	public OrderedPurchaseBeen() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}

	
	
	
	
}
