package service.Custom.Impl;

import Repository.Custom.EmployeeDao;
import Repository.Custom.ItemDao;
import Repository.DaoFactory;
import dto.Employee;
import entity.EmployeeEntity;
import entity.ItemEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import service.Custom.EmployeeService;
import util.DaoType;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public boolean addEmployee(Employee employee) {
        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);

        EmployeeDao repository = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        return repository.save(entity);
    }

    @Override
    public ObservableList<Employee> getAllEmployee() {
        EmployeeDao daoType = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        return daoType.findAllEmployee();
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);
        EmployeeDao daoType = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        return daoType.update(entity);
    }

    @Override
    public boolean deleteEmployee(String id) {
        EmployeeDao daoType = DaoFactory.getInstance().getDaoType(DaoType.EMPLOYEE);
        return daoType.delete(id);
    }
}
