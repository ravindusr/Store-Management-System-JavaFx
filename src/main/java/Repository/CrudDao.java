package Repository;

import dto.Customer;
import dto.Item;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    ObservableList<Item> findAll();
    ObservableList<Customer> findAllCustomers();
    boolean delete(String id);
}
