package Repository;

import Repository.Custom.Impl.CustomerDaoImpl;
import Repository.Custom.Impl.ItemDaoImpl;
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
        }
        return null;

    }
}
