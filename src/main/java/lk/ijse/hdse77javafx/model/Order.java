package lk.ijse.hdse77javafx.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private String customerId;
    private Date date; // java.util.Date
    private List<OrderDetials> orderDetailsList;

    public Order() {
    }

    public Order(int orderId, String customerId, Date date, List<OrderDetials> orderDetailsList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
        this.orderDetailsList = orderDetailsList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderDetials> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetials> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customerId=" + customerId + ", date=" + date + ", orderDetailsList=" + orderDetailsList + '}';
    }
}