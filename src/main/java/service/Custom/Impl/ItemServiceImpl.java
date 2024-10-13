package service.Custom.Impl;

import Repository.Custom.ItemDao;
import Repository.DaoFactory;
import Repository.SuperDao;
import dto.Item;
import entity.ItemEntity;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import service.Custom.ItemService;
import util.DaoType;

import java.util.List;

public class ItemServiceImpl implements ItemService {
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
    public Item searchItem(String id) {
        return null;
    }

    @Override
    public List<String> getAllItemCodes() {
        return List.of();
    }
}
