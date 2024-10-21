package service.Custom;

import dto.Customer;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {

    boolean addCustomer(Customer customer);

    ObservableList<Customer> getAllCustomers();

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String id);

    Customer searchCustomer(String name);

    List<String> getAllCustomerIDs();
}
