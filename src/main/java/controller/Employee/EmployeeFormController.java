package controller.Employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import dto.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Custom.EmployeeService;
import service.ServiceFactory;
import util.ServiceType;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<Employee> tblEmp;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmpId;

    @FXML
    private JFXTextField txtEmpName;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtxAddress;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);


    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (employeeService.addEmployee(
                new Employee(
                        txtEmpId.getText(),
                        txtEmpName.getText(),
                        cmbGender.getValue(),
                        txtxAddress.getText(),
                        txtContact.getText(),
                        txtNic.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Employee Added Successfully !").show();
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
        if (employeeService.deleteEmployee(txtEmpId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Employee Deleted Successfully ! ").show();
            loadTable();
            clearInputs();
            genId();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (employeeService.updateEmployee(
                new Employee(
                        txtEmpId.getText(),
                        txtEmpName.getText(),
                        cmbGender.getValue(),
                        txtxAddress.getText(),
                        txtContact.getText(),
                        txtNic.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Employee Updated Successfully !").show();
            loadTable();
            clearInputs();
            genId();
        }else {
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

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));


        tblEmp.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {

            if(newVal != null){
                addValueTpText(newVal);
            }
        });

        genId();
        loadTable();

    }

    private void loadTable(){
        tblEmp.setItems(employeeService.getAllEmployee());
    }

    private void addValueTpText(Employee newVal) {
        txtEmpId.setText(newVal.getEmpId());
        txtEmpName.setText(newVal.getEmpName());
        cmbGender.setValue(newVal.getGender());
        txtxAddress.setText(newVal.getAddress());
        txtContact.setText(newVal.getContact());
        txtNic.setText(newVal.getNic());
        txtSalary.setText(newVal.getSalary().toString());

    }

    private void clearInputs(){
        txtEmpId.setText("");
        txtEmpName.setText("");
        cmbGender.setValue("");
        txtxAddress.setText("");
        txtNic.setText("");
        txtSalary.setText("");
        txtContact.setText("");
    }

    private void genId(){
        String sql = "SELECT empId FROM employee ORDER BY empId DESC LIMIT 1";
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()){
                int empId = resultSet.getInt(1);
                int n = empId+1;
                txtEmpId.setText(Integer.toString(n));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
