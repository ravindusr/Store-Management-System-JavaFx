package Repository;

import Repository.Custom.Impl.CustomerDaoImpl;
import Repository.Custom.Impl.EmployeeDaoImpl;
import Repository.Custom.Impl.ItemDaoImpl;
import Repository.Custom.Impl.SupplierDaoImpl;
import util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao>T getDaoType(DaoType type){

        switch (type){
            case ITEM : return (T)new ItemDaoImpl();
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case EMPLOYEE:return (T)new EmployeeDaoImpl();
            case SUPPLIER:return (T)new SupplierDaoImpl();
        }
        return null;

    }
}
