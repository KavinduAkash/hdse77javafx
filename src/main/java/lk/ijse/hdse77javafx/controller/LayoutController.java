package lk.ijse.hdse77javafx.controller;

import java.io.IOException;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hdse77javafx.Hdse77javafx;

public class LayoutController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    
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
    void clickNavBtnCustomer(ActionEvent event) throws IOException {
        Parent customerFxml = Hdse77javafx.loadFxml("Customer");
        
        setAnchorAlignments(customerFxml);
        
        mainPane.getChildren().setAll(customerFxml);
    }

    @FXML
    void clickNavBtnItem(ActionEvent event) throws IOException {
        Parent itemFxml = Hdse77javafx.loadFxml("Item");
        
        setAnchorAlignments(itemFxml);
        
        mainPane.getChildren().setAll(itemFxml);
    }

    @FXML
    void clickNavBtnOrder(ActionEvent event) throws IOException {
        Parent orderFxml = Hdse77javafx.loadFxml("Order");
        
        setAnchorAlignments(orderFxml);
        
        mainPane.getChildren().setAll(orderFxml);
    }
    
    private void setAnchorAlignments(Parent fxml) {
        AnchorPane.setTopAnchor(fxml, 0.0);
        AnchorPane.setBottomAnchor(fxml, 0.0);
        AnchorPane.setRightAnchor(fxml, 0.0);
        AnchorPane.setLeftAnchor(fxml, 0.0);
    }
    
}
