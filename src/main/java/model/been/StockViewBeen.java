package model.been;

import java.io.Serializable;

public class StockViewBeen implements Serializable {
	private String jan;
	private String productName;
	private String warehouseName;
	private int qty;
	
	public StockViewBeen(String productName, String jan, String warehouseName, int qty) {
		this.productName = productName;
		this.jan = jan;
		this.warehouseName = warehouseName;
		this.qty = qty;
	}

	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
