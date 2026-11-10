/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.hdse77javafx.model.tbl;

/**
 *
 * @author kavinduakash
 */
public class CartTbl {
    private int no;
    private String itemId;
    private String itemName;
    private int orderQty;
    private double unitPrice;
    private double totalPrice;

    public CartTbl() {
    }

    public CartTbl(int no, String itemId, String itemName, int orderQty, double unitPrice, double totalPrice) {
        this.no = no;
        this.itemId = itemId;
        this.itemName = itemName;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartTbl{" + "no=" + no + ", itemId=" + itemId + ", itemName=" + itemName + ", orderQty=" + orderQty + ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + '}';
    }
}
