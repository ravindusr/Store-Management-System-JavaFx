package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardFormController implements Initializable {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();

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
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent fxml1 = FXMLLoader.load(getClass().getResource("/view/Customer_Mange_Form.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().addAll(fxml1);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/Employee_Manage_Form.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().addAll(fxml);
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

    private void loadDateAndTime(){

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline timeline= new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour()+":"+ now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
