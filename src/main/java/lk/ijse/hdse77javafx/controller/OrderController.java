package lk.ijse.hdse77javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hdse77javafx.db.DBConnection;
import lk.ijse.hdse77javafx.model.Customer;
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
    private TableColumn<?, ?> noCol;
    
    @FXML
    private TableColumn<?, ?> itemIdCol;

    @FXML
    private TableColumn<?, ?> itemNameCol;

    @FXML
    private TableColumn<?, ?> orderQtyCol;
    
    @FXML
    private TableColumn<?, ?> removeCol;

    @FXML
    private TableColumn<?, ?> totalCol;

    @FXML
    private TableColumn<?, ?> unitPriceCol;
    
    
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
    
}
