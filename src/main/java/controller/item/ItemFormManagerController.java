package controller.item;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
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
import service.Custom.ItemService;
import service.ServiceFactory;
import util.ServiceType;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ItemFormManagerController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtID;

    @FXML
    private TableView<Item> tblItems;

    ItemService itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if(itemService.addItem(
                new Item(
                        txtID.getText(),
                        txtName.getText(),
                        txtDescription.getText(),
                        cmbCategory.getValue(),
                        Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtPrice.getText())

                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Item Added !").show();
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
        if (itemService.deleteItem(txtID.getText())) {
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted !").show();
            loadTable();
            clearInput();
            genId();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (itemService.updateItem(
                new Item(
                        txtID.getText(),
                        txtName.getText(),
                        txtDescription.getText(),
                        cmbCategory.getValue(),
                        Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtPrice.getText())
                )
        )) {
            new Alert(Alert.AlertType.INFORMATION,"Item Updated !").show();
            loadTable();
            clearInput();
            genId();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Male");
        categoryList.add("Female");
        categoryList.add("Kids");
        cmbCategory.setItems(categoryList);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {

            if(newVal != null){
                addValueTpText(newVal);
            }
        });

        genId();
        loadTable();
    }

    private void loadTable(){
        tblItems.setItems(itemService.getAllItems());
    }

    private void addValueTpText(Item newVal) {
        txtID.setText(newVal.getId());
        txtName.setText(newVal.getName());
        txtDescription.setText(newVal.getDescription());
        cmbCategory.setValue(newVal.getCategory());
        txtQty.setText(newVal.getQty().toString());
        txtPrice.setText(newVal.getPrice().toString());

    }

    private void clearInput(){
        txtID.setText("");
        txtName.setText("");
        txtDescription.setText("");
        cmbCategory.setValue("");
        txtQty.setText("");
        txtPrice.setText("");
    }

    private void genId(){
        String sql = "SELECT id FROM items ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                int n = id+1;
                txtID.setText(Integer.toString(n));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
