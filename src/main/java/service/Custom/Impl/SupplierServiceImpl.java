package service.Custom.Impl;

import Repository.Custom.ItemDao;
import Repository.Custom.SupplierDao;
import Repository.DaoFactory;
import Repository.SuperDao;
import dto.Supplier;
import entity.SupplierEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import service.Custom.SupplierService;
import util.DaoType;

public class SupplierServiceImpl implements SupplierService {
    @Override
    public boolean addSupplier(Supplier supplier) {
        SupplierEntity entity = new ModelMapper().map(supplier, SupplierEntity.class);

        SupplierDao repository = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        return repository.save(entity);
    }

    @Override
    public ObservableList<Supplier> getAllSuppliers() {
        SupplierDao daoType = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        return daoType.findAllSuppliers();
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        SupplierEntity entity = new ModelMapper().map(supplier, SupplierEntity.class);
        SupplierDao daoType = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        return daoType.update(entity);
    }

    @Override
    public boolean deleteSupplier(String id) {
        SupplierDao daoType = DaoFactory.getInstance().getDaoType(DaoType.SUPPLIER);
        return daoType.delete(id);
    }
}
