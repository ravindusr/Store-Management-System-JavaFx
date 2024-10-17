package controller.Supplier;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Custom.SupplierService;
import service.ServiceFactory;
import util.ServiceType;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbMeterial;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colMeterial;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableView<Supplier> tblSuppliers;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtSupId;

    @FXML
    private JFXTextField txtSupName;

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (supplierService.addSupplier(
                new Supplier(
                        txtSupId.getText(),
                        txtSupName.getText(),
                        cmbMeterial.getValue(),
                        txtDescription.getText(),
                        txtContact.getText()
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Supplier Added Successfully !").show();
            loadTable();
            clearInputs();
            genId();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearInputs();
        genId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (supplierService.deleteSupplier(txtSupId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted Successfully !").show();
            loadTable();
            clearInputs();
            genId();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (supplierService.updateSupplier(
                new Supplier(
                        txtSupId.getText(),
                        txtSupName.getText(),
                        cmbMeterial.getValue(),
                        txtDescription.getText(),
                        txtContact.getText()
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Supplier Updated Successfully !").show();
            loadTable();
            clearInputs();
            genId();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> meterialList = FXCollections.observableArrayList();
        meterialList.add("Clothes");
        meterialList.add("Hardware Items");
        cmbMeterial.setItems(meterialList);

        colSupId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colMeterial.setCellValueFactory(new PropertyValueFactory<>("meterial"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {

            if(newVal != null){
                addValueTpText(newVal);
            }
        });

        genId();
        loadTable();
    }

    private void loadTable(){
        tblSuppliers.setItems(supplierService.getAllSuppliers());
    }

    private void addValueTpText(Supplier newVal){
        txtSupId.setText(newVal.getId());
        txtSupName.setText(newVal.getSupName());
        cmbMeterial.setValue(newVal.getMeterial());
        txtDescription.setText(newVal.getDescription());
        txtContact.setText(newVal.getContact());
    }

    private void clearInputs(){
        txtSupId.setText("");
        txtSupName.setText("");
        cmbMeterial.setValue("");
        txtDescription.setText("");
        txtContact.setText("");

    }

    private void genId(){
        String sql = "SELECT id FROM suppliers ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                int n = id+1;
                txtSupId.setText(Integer.toString(n));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
