package lk.ijse.hdse77javafx.model;

public class Item {
    private String id;
    private String name;
    private double unitPrice;
    private int qty;

    public Item() {
    }

    public Item(String id, String name, double unitPrice, int qty) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + ", qty=" + qty + '}';
    }
}
