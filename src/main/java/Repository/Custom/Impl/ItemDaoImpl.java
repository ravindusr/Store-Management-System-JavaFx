package Repository.Custom.Impl;

import Repository.Custom.ItemDao;
import db.DbConnection;
import dto.Customer;
import dto.Employee;
import dto.Item;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity item) {
        try {
            String SQL = "INSERT INTO Items values(?,?,?,?,?,?)";
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,item.getId());
            psTm.setObject(2,item.getName());
            psTm.setObject(3,item.getDescription());
            psTm.setObject(4,item.getCategory());
            psTm.setObject(5,item.getQty());
            psTm.setObject(6,item.getPrice());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ItemEntity item) {

        String SQL = "UPDATE items SET name= ?, description= ?, category=?, quantity=?, price=? WHERE id=?";
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,item.getName());
            psTm.setObject(2,item.getDescription());
            psTm.setObject(3,item.getCategory());
            psTm.setObject(4,item.getQty());
            psTm.setObject(5,item.getPrice());
            psTm.setObject(6,item.getId());

            return psTm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> findAll() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM items";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            while (resultSet.next()){

                itemObservableList.add(
                        new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getDouble(6)

                        ));
            }
            return itemObservableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public boolean delete(String id) {
        try {
            return DbConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM items WHERE id ='" + id + "'") > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
