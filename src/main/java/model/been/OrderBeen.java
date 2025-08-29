package model.been;

public class OrderBeen {
	int id;
	private String jan;
	private int qty;
	private ProductBeen product;
	
	public OrderBeen(int id, String jan, int qty) {
		this.id = id;
		this.jan = jan;
		this.qty = qty;
	}

	

	public ProductBeen getProduct() {
		return product;
	}



	public void setProduct(ProductBeen product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}


	public String getJan() {
		return jan;
	}

	public void setJan(String jan) {
		this.jan = jan;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
