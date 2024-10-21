package Repository.Custom.Impl;

import Repository.Custom.EmployeeDao;
import db.DbConnection;
import dto.*;
import entity.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean save(EmployeeEntity employee) {
        try {
            String SQL = "INSERT INTO employee values(?,?,?,?,?,?,?)";
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,employee.getEmpId());
            psTm.setObject(2,employee.getEmpName());
            psTm.setObject(3,employee.getGender());
            psTm.setObject(4,employee.getAddress());
            psTm.setObject(5,employee.getContact());
            psTm.setObject(6,employee.getNic());
            psTm.setObject(7,employee.getSalary());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(EmployeeEntity employee) {
        String SQL = "UPDATE employee SET empName = ?, gender= ?, address=?, contact=?, nic=?, salary=? WHERE empId=?";

        try {

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,employee.getEmpName());
            psTm.setObject(2,employee.getGender());
            psTm.setObject(3,employee.getAddress());
            psTm.setObject(4,employee.getContact());
            psTm.setObject(5,employee.getNic());
            psTm.setObject(6,employee.getSalary());
            psTm.setObject(7,employee.getEmpId());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> findAll() {
        return null;
    }

    @Override
    public ObservableList<Customer> findAllCustomers() {
        return null;
    }

    @Override
    public ObservableList<Employee> findAllEmployee() {
        ObservableList<Employee>employeeObservableList = FXCollections.observableArrayList();
        String Sql = "SELECT * FROM employee";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(Sql);
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){
                employeeObservableList.add(
                        new Employee(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getDouble(7)
                        ));
            }
            return employeeObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Supplier> findAllSuppliers() {
        return null;
    }

    @Override
    public Customer searchCustomer(String name) {
        return null;
    }

    @Override
    public Item searchItem(String name) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        try {
            return DbConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM employee WHERE empId ='" + id + "'")>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatestock(OrderDetail orderDetail) {
        return false;
    }


}
