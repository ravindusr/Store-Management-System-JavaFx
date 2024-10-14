package service.Custom.Impl;

import Repository.Custom.CustomerDao;
import Repository.DaoFactory;
import dto.Customer;
import entity.CustomerEntity;

import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import service.Custom.CustomerService;
import util.DaoType;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        CustomerDao repository = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return repository.save(entity);
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        CustomerDao daoType = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return daoType.findAllCustomers();
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        CustomerDao daoType = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return daoType.update(entity);
    }

    @Override
    public boolean deleteCustomer(String id) {
        CustomerDao daoType = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);
        return daoType.delete(id);
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public List<String> getAllCustomerIDs() {
        return List.of();
    }
}
