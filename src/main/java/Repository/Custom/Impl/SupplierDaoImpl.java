package Repository.Custom.Impl;

import Repository.Custom.SupplierDao;
import db.DbConnection;
import dto.Customer;
import dto.Employee;
import dto.Item;
import dto.Supplier;
import entity.SupplierEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public boolean save(SupplierEntity supplier) {
        try {
            String SQL = "INSERT INTO suppliers values(?,?,?,?,?)";
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,supplier.getId());
            psTm.setObject(2,supplier.getSupName());
            psTm.setObject(3,supplier.getMeterial());
            psTm.setObject(4,supplier.getDescription());
            psTm.setObject(5,supplier.getContact());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(SupplierEntity supplier) {
        String SQL = "UPDATE suppliers SET supName= ?, material= ?, description=?, contact=? WHERE id=?";
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,supplier.getSupName());
            psTm.setObject(2,supplier.getMeterial());
            psTm.setObject(3,supplier.getDescription());
            psTm.setObject(4,supplier.getContact());
            psTm.setObject(5,supplier.getId());

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
        return null;
    }

    @Override
    public ObservableList<Supplier> findAllSuppliers() {
        ObservableList<Supplier> supplierObservableList = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM suppliers";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){

                supplierObservableList.add(
                        new Supplier(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5)

                        ));
            }
            return supplierObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return DbConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM suppliers WHERE id ='" + id + "'") > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
