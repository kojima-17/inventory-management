package model.been;

import java.io.Serializable;

public class StockBeen implements Serializable {
	private ProductBeen product;
	private WarehouseBeen warehouse;
	private int qty;
	
	public StockBeen(ProductBeen product, WarehouseBeen warehouse, int qty) {
		this.product = product;
		this.warehouse = warehouse;
		this.qty = qty;
	}
	
	public StockBeen() {
	}

	public ProductBeen getProduct() {
		return product;
	}

	public void setProduct(ProductBeen product) {
		this.product = product;
	}

	public WarehouseBeen getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(WarehouseBeen warehouse) {
		this.warehouse = warehouse;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	

}
