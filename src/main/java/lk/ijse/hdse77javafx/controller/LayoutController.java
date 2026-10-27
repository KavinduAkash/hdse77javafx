package lk.ijse.hdse77javafx.controller;

import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LayoutController implements Initializable {

    @FXML
    private Button navBtnCustomer;

    @FXML
    private Button navBtnItem;

    @FXML
    private Button navBtnOrder;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    void clickNavBtnCustomer(ActionEvent event) {
        System.out.println("Customer Nav Btn Clicked!");
    }

    @FXML
    void clickNavBtnItem(ActionEvent event) {
        System.out.println("Item Nav Btn Clicked!");
    }

    @FXML
    void clickNavBtnOrder(ActionEvent event) {
        System.out.println("Order Nav Btn Clicked!");
    }
    
}
