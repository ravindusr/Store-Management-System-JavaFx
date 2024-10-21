package Repository;

import dto.*;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    ObservableList<Item> findAll();
    ObservableList<Customer> findAllCustomers();
    ObservableList<Employee>findAllEmployee();
    ObservableList<Supplier>findAllSuppliers();
    Customer searchCustomer(String name);
    Item searchItem(String name);
    boolean delete(String id);

    boolean updatestock(OrderDetail orderDetail);
}
