package Repository;

import dto.Customer;
import dto.Employee;
import dto.Item;
import dto.Supplier;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    ObservableList<Item> findAll();
    ObservableList<Customer> findAllCustomers();
    ObservableList<Employee>findAllEmployee();
    ObservableList<Supplier>findAllSuppliers();
    boolean delete(String id);
}
