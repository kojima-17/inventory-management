package model.been;

import java.io.Serializable;

public class StockBeen implements Serializable {
	private int productId;
	private int warehouseId;
	private int qty;
	
	public StockBeen(int productId, int warehouseId, int qty) {
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.qty = qty;
	}
	
	public StockBeen() {
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
