package lk.ijse.hdse77javafx.model;

public class OrderDetials {
       private String orderDetailsId;
       private String itemId;
       private int qty;
       private double totalPrice;

       public String getOrderDetailsId() {
            return orderDetailsId;
        }

        public void setOrderDetailsId(String orderDetailsId) {
            this.orderDetailsId = orderDetailsId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "OrderDetials{" + "orderDetailsId=" + orderDetailsId + ", itemId=" + itemId + ", qty=" + qty + ", totalPrice=" + totalPrice + '}';
        }
   }
