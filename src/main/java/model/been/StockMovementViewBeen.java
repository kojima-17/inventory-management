package model.been;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StockMovementViewBeen implements Serializable {
	private String productName;
	private String jan;
	private String warehouseName;
	private String type;
	private int qty;
	private LocalDateTime movedAt;
	
	public StockMovementViewBeen(String productName, String jan, String warehouseName, String type, int qty, LocalDateTime movedAt) {
		this.productName = productName;
		this.jan = jan;
		this.warehouseName = warehouseName;
		this.type = type;
		this.qty = qty;
		this.movedAt = movedAt;
	}
	
	public StockMovementViewBeen() {
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public LocalDateTime getMovedAt() {
		return movedAt;
	}

	public void setMovedAt(LocalDateTime movedAt) {
		this.movedAt = movedAt;
	}
	
	
}
