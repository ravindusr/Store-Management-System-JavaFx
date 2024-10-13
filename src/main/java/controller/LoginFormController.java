package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;

public class LoginFormController {

    @FXML
    private Label lblMassage;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    public void btnLoginOnAction(ActionEvent event) {

        if(!txtUsername.getText().isBlank() && !txtPassword.getText().isBlank()){
            validateLogin();

        }else {
            lblMassage.setText("Please Enter Your UserName and Password !");
        }
    }

    public void validateLogin(){
        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String verifyLogin = "SELECT count(1) FROM useaccounts WHERE Username = '"+txtUsername.getText()+"' AND Password = '"+txtPassword.getText()+"'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while (resultSet.next()){
                if (resultSet.getInt(1)==1){
                    lblMassage.setText("Login Successfully .");
                    Stage stage = new Stage();
                    AtomicReference<Double> x = new AtomicReference<>((double) 0);
                    AtomicReference<Double> y = new AtomicReference<>((double) 0);
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml"));
                        stage.initStyle(StageStyle.DECORATED);

                        root.setOnMousePressed(event ->{
                            x.set(event.getSceneX());
                            y.set(event.getSceneY());
                        });

                        root.setOnMouseDragged(event ->{
                            stage.setX(event.getScreenX()- x.get());
                            stage.setY(event.getScreenY()- y.get());
                        });
                        stage.setScene(new Scene(root,1300,800));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    stage.setTitle("DashBoard");
                    stage.show();
                }else {
                    lblMassage.setText("Invalid Login.Please try Again.");
                    clearFields();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields(){
        txtUsername.setText("");
        txtPassword.setText("");
    }

}
