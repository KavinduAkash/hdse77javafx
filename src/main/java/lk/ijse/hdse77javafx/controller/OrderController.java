package lk.ijse.hdse77javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hdse77javafx.db.DBConnection;
import lk.ijse.hdse77javafx.model.Customer;
import lk.ijse.hdse77javafx.model.Order;
import lk.ijse.hdse77javafx.model.OrderDetials;
import lk.ijse.hdse77javafx.model.tbl.CartTbl;

public class OrderController implements Initializable {

    @FXML
    private ComboBox<String> customerIdComboBox;

    @FXML
    private ComboBox<String> itemIdComboBox;

    
    @FXML
    private Label customerEmailValueLbl;

    @FXML
    private Label customerNameValueLbl;

    @FXML
    private Label customerPhoneValueLbl;

    
    @FXML
    private Label ItemNameValueLbl;
    
    @FXML
    private Label itemQtyValueLbl;

    @FXML
    private Label itemUnitPriceValueLbl;
    
    
    @FXML
    private TextField orderQtyTextField;
    
    @FXML
    private Button addToCartBtn;
    
    
    @FXML
    private TableColumn<CartTbl, Integer> noCol;
    
    @FXML
    private TableColumn<CartTbl, String> itemIdCol;

    @FXML
    private TableColumn<CartTbl, String> itemNameCol;

    @FXML
    private TableColumn<CartTbl, Integer> orderQtyCol;
    
    @FXML
    private TableColumn<CartTbl, Void> removeCol;

    @FXML
    private TableColumn<CartTbl, Double> totalCol;

    @FXML
    private TableColumn<CartTbl, Double> unitPriceCol;
    
    
    @FXML
    private TableView<CartTbl> cartTbl;
    
    @FXML
    private Label totalBilllbl;
    
    private final String QTY_REGEX = "^[0-9]+$";
    
    private List<CartTbl> cartTblList = new ArrayList<>();
    
    private double totalBill = 0.0;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        noCol.setCellValueFactory(new PropertyValueFactory<>("no"));
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        orderQtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        // ==========================================
        
        removeCol.setCellFactory(col -> new TableCell<CartTbl,Void>() {
        
            private Button btn = new Button("Delete");
            
            {
                btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            }
            
            {
            
                btn.setOnAction(event -> {
                
                    CartTbl cartTbl = getTableView().getItems().get(getIndex());
                    cartTblList.remove(cartTbl);
                    loadCartTbl();
                    
                    totalBill = totalBill - cartTbl.getTotalPrice();
                    totalBilllbl.setText(String.valueOf(totalBill));
                    
                });
                
            }
            
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty?null:btn);
            }
        
        });
        
        // ==========================================
        
        
        loadCustomerIdComboBox();
        loadItemIdComboBox();
        
    }
    
    private void loadCustomerIdComboBox() {
       List<String> customerIdList = new ArrayList<String>();
       
       ObservableList<String> customerIdObList = FXCollections.observableArrayList();

       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM customer ORDER BY id DESC";

            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                String id = result.getString("id");
                customerIdList.add(id);
            }
            
            customerIdObList.addAll(customerIdList);

            customerIdComboBox.setItems(customerIdObList);
            
            stmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }
    }
    
    private void loadItemIdComboBox() {
       List<String> itemIdList = new ArrayList<String>();
       
       ObservableList<String> itemIdObList = FXCollections.observableArrayList();

       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM item ORDER BY id DESC";

            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                String id = result.getString("id");
                itemIdList.add(id);
            }
            
            itemIdObList.addAll(itemIdList);

            itemIdComboBox.setItems(itemIdObList);
            
            stmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }
    }
    
    
    @FXML
    void selectCustomerIdComboBox(ActionEvent event) {
        
        // Step 01
        String id = customerIdComboBox.getSelectionModel().getSelectedItem();
        
        // Step 02
        try {

             Connection conn = DBConnection.getInstance().getConnection();

             String query = "SELECT * FROM customer WHERE id=?";

             PreparedStatement pstmt = conn.prepareStatement(query);

             pstmt.setString(1, id);

             ResultSet result = pstmt.executeQuery();

             if(result.next()) {

                 String name = result.getString("name");                 
                 String phone = result.getString("phone");
                 String email = result.getString("email");

                 // Step 03
                 customerNameValueLbl.setText(name);
                 customerPhoneValueLbl.setText(phone);
                 customerEmailValueLbl.setText(email);

             } else {
                 System.out.println("Customer not found!");
             }

             pstmt.close();

        } catch(SQLException e) {
             e.printStackTrace();
             System.out.println("Something went wrong!");
        }
    }

    @FXML
    void selectItemIdComboBox(ActionEvent event) {
        // Step 01
        String id = itemIdComboBox.getSelectionModel().getSelectedItem();
        
        // Step 02
        try {

             Connection conn = DBConnection.getInstance().getConnection();

             String query = "SELECT * FROM item WHERE id=?";

             PreparedStatement pstmt = conn.prepareStatement(query);

             pstmt.setString(1, id);

             ResultSet result = pstmt.executeQuery();

             if(result.next()) {

                 String name = result.getString("name");                 
                 int qty = result.getInt("qty");
                 double unitPrice = result.getDouble("unitPrice");

                 // Step 03
                 ItemNameValueLbl.setText(name);
                 
                 // itemQtyValueLbl.setText(qty + "");
                 // itemQtyValueLbl.setText(Integer.toString(qty));
                 itemQtyValueLbl.setText(String.valueOf(qty));
                 
                 // itemUnitPriceValueLbl.setText(unitPrice + "");
                 // itemUnitPriceValueLbl.setText(Double.toString(unitPrice));
                 itemUnitPriceValueLbl.setText(String.valueOf(unitPrice));

             } else {
                 System.out.println("Customer not found!");
             }

             pstmt.close();

        } catch(SQLException e) {
             e.printStackTrace();
             System.out.println("Something went wrong!");
        }
    }
    
    
    @FXML
    void handleClickAddToCart(ActionEvent event) {
        
        String id = itemIdComboBox.getSelectionModel().getSelectedItem();
        String itemName = ItemNameValueLbl.getText();
        
        int availableQty = Integer.parseInt(itemQtyValueLbl.getText());
        double unitPrice = Double.parseDouble(itemUnitPriceValueLbl.getText());
        
        String orderQty = orderQtyTextField.getText();
        
        if(orderQty.matches(QTY_REGEX)) {
            
            int ordQty = Integer.parseInt(orderQty);
            
            if(!(ordQty<1)) {
                
                CartTbl cartTbl = new CartTbl(
                        cartTblList.size() + 1, 
                        id, 
                        itemName, 
                        ordQty, 
                        unitPrice, 
                        ordQty * unitPrice
                );
                
                cartTblList.add(cartTbl);
                
                loadCartTbl();
                
                totalBill = totalBill + (ordQty * unitPrice);
                totalBilllbl.setText(String.valueOf(totalBill));
                
                cleanItemForm();
                
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Order Qty").show();      
            }
            
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Order Qty").show();      
        }
        
    }
    
    
    private void loadCartTbl() {
        
        ObservableList<CartTbl> obList = FXCollections.observableArrayList(cartTblList);
        cartTbl.setItems(obList);
        
    }
    
    
    @FXML
    void clickPlaceOrderBtn(ActionEvent event) {
        
        String cusId = customerIdComboBox.getSelectionModel().getSelectedItem();
        Date orderDate = new Date();
        List<OrderDetials> orderDetailsList = new ArrayList<>();
        for (CartTbl cartTbl1 : cartTblList) {
            
            OrderDetials od = new OrderDetials();
            od.setItemId(cartTbl1.getItemId());
            od.setQty(cartTbl1.getOrderQty());
            od.setTotalPrice(cartTbl1.getTotalPrice());
            
            orderDetailsList.add(od);
            
        }
        
        Order order = new Order();
        order.setCustomerId(cusId);
        order.setDate(orderDate);
        order.setOrderDetailsList(orderDetailsList);
        
        placeOrder(order);
        
    }
    
    public void addOrderDetails(Connection conn, Order order) throws Exception {
            for (OrderDetials orderDetials : order.getOrderDetailsList()) {
                // INSERT INTO orders_detail VALUES(1, 'O001', 'I001', 10, 100.00);
                String query = "INSERT INTO order_detail (order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)";
                
                PreparedStatement pstmt = conn.prepareStatement(query);
                
                pstmt.setInt(1, order.getOrderId());
                pstmt.setString(2, orderDetials.getItemId());
                pstmt.setInt(3, orderDetials.getQty());
                
                String itemQuery = "SELECT * FROM item WHERE id=?";
                PreparedStatement itemPstmt = conn.prepareStatement(itemQuery);
                itemPstmt.setString(1, orderDetials.getItemId());
                
                ResultSet rs = itemPstmt.executeQuery();
                
                if(rs.next()) {
                    pstmt.setDouble(4, rs.getDouble("unitPrice"));
                } else {
                    throw new SQLException("Invalid Item!");
                }
                
                int result = pstmt.executeUpdate();
                
                if(!(result>0)) {
                   throw new SQLException("Something went wrong!");
                }
            }
   }
    
    
    public void placeOrder(Order order) {
       Connection conn = null;
       
       try {
       
           final String DB_URL = "jdbc:mysql://localhost:3306/pos";
           final String DB_USERNAME = "root";
           final String DB_PASSWORD = "ijse";
           
           conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
           
           conn.setAutoCommit(false);
           
           String query = "INSERT INTO orders (customer_id, order_date) VALUES(?, ?)";
           
           PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
           
           pstmt.setString(1, order.getCustomerId());
           pstmt.setDate(2, new java.sql.Date(order.getDate().getTime()));         
           
           int result = pstmt.executeUpdate();
           
           if(result > 0) {
               ResultSet rs = pstmt.getGeneratedKeys();
               
               if(rs.next()) {
                   
                   if(rs.getInt(1)!=0) {
                        order.setOrderId(rs.getInt(1));
                        addOrderDetails(conn, order);
                        new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                        cleanFullOrderForm();
                   } else {
                       throw new SQLException("Something went wrong!");
                   }
                   
               } else {
                   throw new SQLException("Something went wrong!");
               }
            
           } else {
               throw new SQLException("Something went wrong!");
           }
           
           conn.commit();
           
       } catch(Exception e) {
            
           try {
               conn.rollback();
           } catch(SQLException ex) {
               ex.printStackTrace();
               System.out.println(ex.getMessage());
           }
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
       
       } finally {
           try {
               conn.setAutoCommit(true);
           } catch(SQLException ex) {
               ex.printStackTrace();
               System.out.println(ex.getMessage());
           }
       }
       
   }
    
    
   private void cleanItemForm() {
       itemIdComboBox.setValue(null);
       // itemIdComboBox.getSelectionModel().clearSelection();
       ItemNameValueLbl.setText("-");
       itemQtyValueLbl.setText("-");
       itemUnitPriceValueLbl.setText("-");
       orderQtyTextField.setText("");
   } 
   
   private void cleanFullOrderForm() {
       customerIdComboBox.setValue(null);
       customerNameValueLbl.setText("-");
       customerPhoneValueLbl.setText("-");
       customerEmailValueLbl.setText("-");
       cleanItemForm();
       cartTbl.getItems().clear();
       totalBilllbl.setText("0.0");
   }
    
   @FXML
   void clickResetItemForm(ActionEvent event) {
        cleanItemForm();
   }
   
   @FXML
   void clickResetFullOrderForm(ActionEvent event) {
        cleanFullOrderForm();
   }
   
}
