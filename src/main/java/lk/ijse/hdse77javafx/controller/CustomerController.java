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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.hdse77javafx.db.DBConnection;
import lk.ijse.hdse77javafx.model.tbl.CustomerTbl;

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
    private TableColumn<CustomerTbl, String> colAddress;

    @FXML
    private TableColumn<CustomerTbl, String> colEmail;

    @FXML
    private TableColumn<CustomerTbl, String> colId;

    @FXML
    private TableColumn<CustomerTbl, String> colName;

    @FXML
    private TableColumn<CustomerTbl, String> colPhone;
    
    @FXML
    private TableColumn<CustomerTbl, String> colRefNo;
    
    @FXML
    private TableView<CustomerTbl> tableCustomer;
    
    
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
        colRefNo.setCellValueFactory(new PropertyValueFactory<>("refNo"));
        
        loadCustomerTable();
    }
    
    // ======================= Save Customer =======================
    
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
                 cleanCustomerForm();
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
    
   
   // ======================= Search Customer =======================
    
   @FXML
   void pressSearchCustomer(KeyEvent event) {
       
       if(event.getCode() == KeyCode.ENTER) {
           
           String id = customerIdTextField.getText();
           
           Customer cusObj = searchCustomer(id); // Customer Object / null
           
           if(cusObj != null) {
           
               customerNameTextField.setText(cusObj.getName());
               customerAddressTextArea.setText(cusObj.getAddress());
               customerPhoneTextField.setText(cusObj.getPhone());
               customerEmailTextField.setText(cusObj.getEmail());
               
           } else {
               new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
           }
           
       }
       
   }
    
   public Customer searchCustomer(String id) {
       Customer cus = null;
       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "SELECT * FROM customer WHERE id=?";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()) {

                String cid = result.getString("id");
                String name = result.getString("name");
                String address = result.getString("address");
                String phone = result.getString("phone");
                String email = result.getString("email");

                cus = new Customer(cid, name, address, phone, email);

            } else {
                System.out.println("Customer not found!");
            }

            pstmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }
       
       return cus;

   } 
    
   // ======================= Load Customer Table =======================
    
   private void loadCustomerTable() {
       
      List<Customer> cusList = getAllCustomers();
      
      List<CustomerTbl> cusTblList = new ArrayList<CustomerTbl>();
      
       for (Customer customer : cusList) {
           String refNo = customer.getId() + customer.getPhone();
           CustomerTbl cusTbl = new CustomerTbl(customer.getId(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getEmail(), refNo);
           cusTblList.add(cusTbl);
       }
      
      ObservableList<CustomerTbl> obList = FXCollections.observableArrayList(cusTblList);
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
   
   
   // ======================= Update Customer =======================
   
    @FXML
    void clickUpdateCustomerBtn(ActionEvent event) {
        String customerId = customerIdTextField.getText().trim();
        String customerName = customerNameTextField.getText().trim();
        String customerPhone = customerPhoneTextField.getText().trim();
        String customerEmail = customerEmailTextField.getText().trim();
        String customerAddress = customerAddressTextArea.getText().trim();
        
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
            updateCustomer(customerModel);
        }
    }
    
    
    public void updateCustomer(Customer cus) {
       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "UPDATE customer SET name=?, address=?, phone=?, email=? WHERE id=?";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1 , cus.getName());
            pstmt.setString(2 , cus.getAddress());
            pstmt.setString(3 , cus.getPhone());
            pstmt.setString(4 , cus.getEmail());
            pstmt.setString(5 , cus.getId());

            int result = pstmt.executeUpdate();

            if(result>0) {
                 new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                 loadCustomerTable();
                 cleanCustomerForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

            pstmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }

   }
    
    
    @FXML
    void clickDeleteCustomerBtn(ActionEvent event) {
        String customerId = customerIdTextField.getText().trim();
        
        if(!customerId.matches(CUSTOMER_ID_REGEX)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID").show();
        } else {
            deleteCustomer(customerId);
        }
    }
    
    
    public void deleteCustomer(String id) {
       try {

            Connection conn = DBConnection.getInstance().getConnection();

            String query = "DELETE FROM customer WHERE id=?";

            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, id);

            int result = pstmt.executeUpdate();

            if(result>0) {
                 new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                 loadCustomerTable();
                 cleanCustomerForm();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

            pstmt.close();

       } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong!");
       }

   }
    
    
    @FXML
    void clickResetCustomerBtn(ActionEvent event) {
        cleanCustomerForm();
    }
    
    private void cleanCustomerForm() {
        customerIdTextField.setText("");
        customerNameTextField.setText("");
        customerAddressTextArea.setText("");
        customerPhoneTextField.setText("");
        customerEmailTextField.setText("");
    }
}
