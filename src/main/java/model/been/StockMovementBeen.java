package model.been;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StockMovementBeen implements Serializable{
	private int id;
	StockBeen stock;
	private int qty;
	private String type;
	private String refType;
	private int refId;
	private LocalDateTime movedAt;
	
	public StockMovementBeen(int id, StockBeen stock, int qty
			, String type, String refType, int refId, LocalDateTime movedAt) {
		this.id = id;
		this.stock = stock;
		this.qty = qty;
		this.type = type;
		this.refType = refType;
		this.refId = refId;
		this.movedAt = movedAt;
	}
	
	public StockMovementBeen() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StockBeen getStock() {
		return stock;
	}

	public void setStock(StockBeen stock) {
		this.stock = stock;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public LocalDateTime getMovedAt() {
		return movedAt;
	}

	public void setMovedAt(LocalDateTime movedAt) {
		this.movedAt = movedAt;
	}
	
}
