package lk.ijse.hdse77javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import lk.ijse.hdse77javafx.db.DBConnection;
import lk.ijse.hdse77javafx.model.Customer;

public class OrderController implements Initializable {

    @FXML
    private ComboBox<String> customerIdComboBox;

    @FXML
    private ComboBox<String> itemIdComboBox;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    
}
