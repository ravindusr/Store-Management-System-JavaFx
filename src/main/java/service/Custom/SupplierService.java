package service.Custom;

import dto.Supplier;
import javafx.collections.ObservableList;
import service.SuperService;

public interface SupplierService extends SuperService {

    boolean addSupplier(Supplier supplier);

    ObservableList<Supplier> getAllSuppliers();

    boolean updateSupplier(Supplier supplier);

    boolean deleteSupplier(String id);
}
