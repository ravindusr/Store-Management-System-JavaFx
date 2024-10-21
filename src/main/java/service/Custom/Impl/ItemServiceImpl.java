package service.Custom.Impl;

import Repository.Custom.ItemDao;
import Repository.DaoFactory;
import Repository.SuperDao;
import dto.Customer;
import dto.Item;
import dto.OrderDetail;
import entity.ItemEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import service.Custom.ItemService;
import util.CrudUtil;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private static ItemServiceImpl instance;

    public ItemServiceImpl(){}

    public static ItemServiceImpl getInstance(){return instance==null?instance=new ItemServiceImpl():instance;}

    @Override
    public boolean addItem(Item item) {
        ItemEntity entity = new ModelMapper().map(item, ItemEntity.class);

        ItemDao repository =DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return repository.save(entity);

    }

    @Override
    public ObservableList<Item> getAllItems() {
        ItemDao daoType = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return daoType.findAll();
    }

    @Override
    public boolean updateItem(Item item) {
        ItemEntity entity = new ModelMapper().map(item, ItemEntity.class);
        ItemDao daoType = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return daoType.update(entity);
    }

    @Override
    public boolean deleteItem(String id) {

        ItemDao daoType = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return daoType.delete(id);
    }

    @Override
    public Item searchItem(String name) {
        ItemDao daoType = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
        return daoType.searchItem(name);
    }

    @Override
    public List<String> getAllItemCodes() {
        ArrayList<String > itemNameList = new ArrayList<>();
        ObservableList<Item> allitems = getAllItems();

        allitems.forEach(obj->{
            itemNameList.add(obj.getName());
        });

        return itemNameList;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails){
            boolean isUpdateStock = updateStock(orderDetail);
            if (!isUpdateStock){
                return false;
            }
        }
        return true;
    }
    public boolean updateStock(OrderDetail orderDetail){
        String SQL = "Update items SET quantity=quantity-? WHERE id=?";
        try {
            return CrudUtil.execute(SQL,orderDetail.getQty(),orderDetail.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
