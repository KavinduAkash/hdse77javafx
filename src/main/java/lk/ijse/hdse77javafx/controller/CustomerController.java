/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.hdse77javafx.controller;

import lk.ijse.hdse77javafx.model.Customer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.hdse77javafx.db.DBConnection;

/**
 *
 * @author kavinduakash
 */
public class CustomerController implements Initializable {

    @FXML
    private TextArea customerAddressTextArea;

    @FXML
    private Button customerDeleteBtn;

    @FXML
    private TextField customerEmailTextField;

    @FXML
    private TextField customerIdTextField;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerPhoneTextField;

    @FXML
    private Button customerPrintBtn;

    @FXML
    private Button customerResetBtn;

    @FXML
    private Button customerSaveBtn;

    @FXML
    private Button customerUpdateBtn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    void clickSaveCustomerBtn(ActionEvent event) {
        String customerId = customerIdTextField.getText().trim();
        String customerName = customerNameTextField.getText().trim();
        String customerPhone = customerPhoneTextField.getText().trim();
        String customerEmail = customerEmailTextField.getText().trim();
        String customerAddress = customerAddressTextArea.getText().trim();
        
        Customer customerModel = new Customer(customerId, customerName, customerAddress, customerPhone, customerEmail);
        addCustomer(customerModel);
    }
    
    
    public void addCustomer(Customer cus) {
       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "INSERT INTO customer VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1 , cus.getId());
            pstmt.setString(2 , cus.getName());
            pstmt.setString(3 , cus.getAddress());
            pstmt.setString(4 , cus.getPhone());
            pstmt.setString(5 , cus.getEmail());

            int result = pstmt.executeUpdate();

            if(result>0) {
                 // System.out.println("Customer saved successfully!");
                 new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
            } else {
                // System.out.println("Something went wrong!");
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

            pstmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            // System.out.println("Something went wrong!");
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
       }

   }
    
}
