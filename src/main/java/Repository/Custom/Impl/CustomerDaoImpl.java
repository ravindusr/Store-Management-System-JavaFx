package Repository.Custom.Impl;


import Repository.Custom.CustomerDao;
import db.DbConnection;
import dto.Customer;
import dto.Item;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        try {
            String SQL = "INSERT INTO customers values(?,?,?,?,?)";
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,customer.getId());
            psTm.setObject(2,customer.getName());
            psTm.setObject(3,customer.getGender());
            psTm.setObject(4,customer.getAddress());
            psTm.setObject(5,customer.getContact());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CustomerEntity customer) {
        String SQL = "UPDATE customers SET name= ?, gender= ?, address=?, contact=? WHERE id=?";
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,customer.getName());
            psTm.setObject(2,customer.getGender());
            psTm.setObject(3,customer.getAddress());
            psTm.setObject(4,customer.getContact());
            psTm.setObject(5,customer.getId());

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
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM customers";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){

                customerObservableList.add(
                        new Customer(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)

                        ));
            }
            return customerObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return DbConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM customers WHERE id ='" + id + "'") > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
