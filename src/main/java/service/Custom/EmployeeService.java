package service.Custom;

import dto.Employee;
import javafx.collections.ObservableList;
import service.SuperService;

public interface EmployeeService extends SuperService {

    boolean addEmployee(Employee employee);

    ObservableList<Employee> getAllEmployee();

    boolean updateEmployee(Employee employee);

    boolean deleteEmployee(String id);

}
