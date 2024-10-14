package controller.Customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.Customer;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Custom.CustomerService;
import service.ServiceFactory;
import util.ServiceType;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCusID;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtCusID;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtxAddress;

    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (customerService.addCustomer(
                new Customer(
                        txtCusID.getText(),
                        txtCusName.getText(),
                        cmbGender.getValue(),
                        txtxAddress.getText(),
                        txtContact.getText()
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added !").show();
            loadTable();
            clearInput();
            genId();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInput();
        genId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (customerService.deleteCustomer(txtCusID.getText())) {
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted !").show();
            loadTable();
            clearInput();
            genId();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (customerService.updateCustomer(
                new Customer(
                        txtCusID.getText(),
                        txtCusName.getText(),
                        cmbGender.getValue(),
                        txtxAddress.getText(),
                        txtContact.getText()
                )
        )) {
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated !").show();
            loadTable();
            clearInput();
            genId();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> genderList = FXCollections.observableArrayList();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        cmbGender.setItems(genderList);

        colCusID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));



        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {

            if(newVal != null){
                addValueTpText(newVal);
            }
        });

        genId();
        loadTable();
    }

    private void loadTable(){
        tblCustomer.setItems(customerService.getAllCustomers());
    }

    private void addValueTpText(Customer newVal) {
        txtCusID.setText(newVal.getId());
        txtCusName.setText(newVal.getName());
        cmbGender.setValue(newVal.getGender());
        txtxAddress.setText(newVal.getAddress());
        txtContact.setText(newVal.getContact());
    }

    private void clearInput(){
        txtCusID.setText("");
        txtCusName.setText("");
        cmbGender.setValue("");
        txtxAddress.setText("");
        txtContact.setText("");
    }

    private void genId(){
        String sql = "SELECT id FROM customers ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                int n = id+1;
                txtCusID.setText(Integer.toString(n));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
