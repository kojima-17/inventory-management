package model.been;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PurchaseBeen implements Serializable{
	private int id;
	private int supplierId;
	private LocalDateTime orderedAt;
	private LocalDateTime receivedAt;
	private String status;
	
	public PurchaseBeen(int id, int supplierId, LocalDateTime orderedAt, LocalDateTime receivedAt, String status) {
		super();
		this.id = id;
		this.supplierId = supplierId;
		this.orderedAt = orderedAt;
		this.receivedAt = receivedAt;
		this.status = status;
	}
	
	public PurchaseBeen() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}

	public LocalDateTime getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(LocalDateTime receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
