package model.been;

import java.io.Serializable;

public class ProductBeen implements Serializable {
    private int id;
    private String jan;
    private String name;
    private double stdCost;
    private double stdPrice;
    private int reorderPoint;
    private int orderLot;
    private boolean isDiscontinued;

    public ProductBeen() {    
    }

    public ProductBeen(int id, String jan, String name, double stdCost, double stdPrice, 
                       int reorderPoint, int orderLot, boolean isDiscontinued) {
        this.id = id;
        this.jan = jan;
        this.name = name;
        this.stdCost = stdCost;
        this.stdPrice = stdPrice;
        this.reorderPoint = reorderPoint;
        this.orderLot = orderLot;
        this.isDiscontinued = isDiscontinued;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStdCost() {
        return stdCost;
    }

    public void setStdCost(double stdCost) {
        this.stdCost = stdCost;
    }

    public double getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(double stdPrice) {
        this.stdPrice = stdPrice;
    }

    public int getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(int reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public int getOrderLot() {
        return orderLot;
    }

    public void setOrderLot(int orderLot) {
        this.orderLot = orderLot;
    }

    public boolean isDiscontinued() {
        return isDiscontinued;
    }

    public void setDiscontinued(boolean isDiscontinued) {
        this.isDiscontinued = isDiscontinued;
    }
}
