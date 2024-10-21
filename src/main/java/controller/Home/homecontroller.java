package controller.Home;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import service.Custom.Impl.CustomerServiceImpl;
import service.Custom.Impl.ItemServiceImpl;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class homecontroller implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomer;

    @FXML
    private JFXComboBox<String> cmbItems;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Text lblItemCount;

    @FXML
    private Text lblLastTotal;

    @FXML
    private Text lblOrderID;


    @FXML
    private Text lblTotal;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private Text lblItemCode;


    @FXML
    private Text lblPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private Text lblStock;

    @FXML
    private Text lbldate;

    ObservableList<CartTm> cartTms = FXCollections.observableArrayList();

    @FXML
    void btnAddtoCartOnAction(ActionEvent event) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        String itemCode = lblItemCode.getText();
        String itemName = cmbItems.getValue();
        Double unitPrice = Double.valueOf(lblPrice.getText());
        Integer qty = Integer.valueOf(txtQty.getText());
        Double total = unitPrice * qty ;

        Integer itemStock = Integer.valueOf(lblStock.getText());

        if (itemStock<qty){
            new Alert(Alert.AlertType.WARNING,"Invalid Qty !");
        }else {
            cartTms.add(new CartTm(itemCode,itemName,unitPrice,qty,total));
            tblCart.setItems(cartTms);
            calcTotal();
        }

    }

    @FXML
    void btnCheckOutOnAction(ActionEvent event) {
       if (tblCart!=null){
           String orderId = lblOrderID.getText();
           LocalDate date = LocalDate.parse(lbldate.getText());
           String customerId = lblCustomerID.getText();

           ArrayList<OrderDetail>orderDetails = new ArrayList<>();

           cartTms.forEach(obj->{
               orderDetails.add(
                       new OrderDetail(
                               lblOrderID.getText(),
                               obj.getItemCode(),
                               obj.getQty(),
                               0.0,
                               obj.getTotal())
               );
           });
           Order order = new Order(orderId, date, customerId, orderDetails);
           try {
               new homeOrderController().placeOrder(order);
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }else {
           new Alert(Alert.AlertType.WARNING,"Cart Empty");
       }

    }

    @FXML
    void btnClearCartOnAction(ActionEvent event) {
        cartTms.clear();
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
           CartTm selectedItem = tblCart.getSelectionModel().getSelectedItem();
           if (selectedItem != null){
               cartTms.remove(selectedItem);
           }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerNames();

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if (t1!=null){
                setCustomerDetails(t1);
            }
        }));

        loadItemNames();

        cmbItems.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            if (t1!=null){
                setItemDetails(t1);
            }
        }));

        loadDate();
        genId();
    }

    private void loadCustomerNames(){
        List<String> allCustomerNames = CustomerServiceImpl.getInstance().getAllCustomerIDs();
        ObservableList<String> objects = FXCollections.observableArrayList();
        allCustomerNames.forEach(id->{
            objects.add(id);
        });
        cmbCustomer.setItems(objects);
    }

    private void loadItemNames(){
        List<String> allItemCodes = ItemServiceImpl.getInstance().getAllItemCodes();
        ObservableList<String> objects = FXCollections.observableArrayList();

        allItemCodes.forEach(id->{
            objects.add(id);
        });
        cmbItems.setItems(objects);
    }

    private void setCustomerDetails(String name){
        Customer customer = CustomerServiceImpl.getInstance().searchCustomer(name);
        lblCustomerID.setText(customer.getId());
        txtCustomerName.setText(customer.getName());
        txtContact.setText(customer.getContact());
        txtAddress.setText(customer.getAddress());
    }

    private void setItemDetails(String name){
        Item item = ItemServiceImpl.getInstance().searchItem(name);
        lblItemCode.setText(item.getId());
        lblPrice.setText(String.valueOf(item.getPrice()));
        lblStock.setText(String.valueOf(item.getQty()));
    }

    private void calcTotal(){
        Double netTotal = 0.0;
        for (CartTm cartTm : cartTms){
            netTotal+= cartTm.getTotal();
        }
        lblTotal.setText(netTotal.toString());
    }

    private void loadDate(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lbldate.setText(f.format(date));

    }

    private void genId(){
        String sql = "SELECT OrderId FROM orders ORDER BY OrderId DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt(1);
                int n = id+1;
                lblOrderID.setText(Integer.toString(n));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
