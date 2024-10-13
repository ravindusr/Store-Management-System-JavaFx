package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardFormController implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().addAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(DashboardFormController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    @FXML
    public void btnHomeOnAction(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().addAll(fxml);
    }

    @FXML
    public void btnItemOnAction(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Item_Manage_Form.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().addAll(fxml);

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplyOnAction(ActionEvent event) {

    }
}
