package model.been;

import java.io.Serializable;

public class PurchaseLineBeen implements Serializable {
	private int id;
	private int purchaseId;
	private int productId;
	private int orderedQty;
	private int receivedQty;
	
	public PurchaseLineBeen(int purcaseId, int productId, int orderedQty) {
		this.purchaseId = purcaseId;
		this.productId = productId;
		this.orderedQty = orderedQty;
	}
	
	public PurchaseLineBeen(int id, int productId, int orderedQty, int receivedQty) {
		this.id = id;
		this.productId = productId;
		this.orderedQty = orderedQty;
		this.receivedQty = receivedQty;
	}
	
	public int getNotReceivedQty() {
		return orderedQty - receivedQty; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOrderedQty() {
		return orderedQty;
	}

	public void setOrderedQty(int orderedQty) {
		this.orderedQty = orderedQty;
	}

	public int getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(int receivedQty) {
		this.receivedQty = receivedQty;
	}
	

}
