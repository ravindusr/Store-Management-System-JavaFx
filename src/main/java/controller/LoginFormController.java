package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginFormController {

    @FXML
    private Label lblMassage;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    public void btnLoginOnAction(ActionEvent event) {

        if(txtUsername.getText().isBlank()==false && txtPassword.getText().isBlank()==false){
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
                    lblMassage.setText("Login Successfull .");

                }else {
                    lblMassage.setText("Invalid Login.Please try Again.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
