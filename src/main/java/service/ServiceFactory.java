package service;

import Repository.Custom.Impl.EmployeeDaoImpl;
import service.Custom.Impl.CustomerServiceImpl;
import service.Custom.Impl.EmployeeServiceImpl;
import service.Custom.Impl.ItemServiceImpl;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance==null?instance=new ServiceFactory():instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType type){
        switch (type){
            case ITEM : return (T) new ItemServiceImpl();
            case CUSTOMER:return (T)new CustomerServiceImpl();
            case EMPLOYEE:return (T)new EmployeeServiceImpl();

        }
        return null;
    }

}
