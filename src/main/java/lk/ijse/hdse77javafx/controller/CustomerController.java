/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.ijse.hdse77javafx.controller;

import lk.ijse.hdse77javafx.model.Customer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, String> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPhone;
    
    @FXML
    private TableView<Customer> tableCustomer;
    
    
    private final String CUSTOMER_NAME_REGEX = "^[A-Za-z]+(?:\\s+[A-Za-z]+)*$";
    private final String CUSTOMER_ID_REGEX = "^[A-Z0-9]+$";
    private final String CUSTOMER_PHONE_REGEX = "^(?:(?:\\+94|0094|94)[\\s.-]?(?:0)?[1-9]\\d{8}|0(?:[1-9]\\d{8}))$";
    private final String CUSTOMER_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String CUSTOMER_ADDRESS_REGEX = "^[A-Za-z0-9,\\/\\\\]+$";
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        loadCustomerTable();
    }
    
    @FXML
    void clickSaveCustomerBtn(ActionEvent event) {
        String customerId = customerIdTextField.getText().trim();
        String customerName = customerNameTextField.getText().trim();
        String customerPhone = customerPhoneTextField.getText().trim();
        String customerEmail = customerEmailTextField.getText().trim();
        String customerAddress = customerAddressTextArea.getText().trim();
        
        // boolean r = Pattern.matches(CUSTOMER_NAME_REGEX, customerName);
        // boolean r = customerName.matches(CUSTOMER_NAME_REGEX);
        
        if(!customerId.matches(CUSTOMER_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID").show();
        } else if(!customerName.matches(CUSTOMER_NAME_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name").show();
        } else if(!customerPhone.matches(CUSTOMER_PHONE_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Phone").show();
        } else if(!customerEmail.matches(CUSTOMER_EMAIL_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer E-Mail").show();  
        } else if(!customerAddress.matches(CUSTOMER_ADDRESS_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Address").show();      
        } else {
            Customer customerModel = new Customer(customerId, customerName, customerAddress, customerPhone, customerEmail);
            addCustomer(customerModel);
        }
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
                 loadCustomerTable();
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
    
    
   private void loadCustomerTable() {
       
      List<Customer> cusList = getAllCustomers();
      ObservableList<Customer> obList = FXCollections.observableArrayList(cusList);
      tableCustomer.setItems(obList);
       
   } 
    
   public List<Customer> getAllCustomers() {

       List<Customer> cusList = new ArrayList<Customer>();

       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM customer ORDER BY id DESC";

            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String address = result.getString("address");
                String phone = result.getString("phone");
                String email = result.getString("email");

                Customer cus = new Customer(id, name, address, phone, email);
                cusList.add(cus);
            }

            stmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }

       return cusList;

   } 
}
