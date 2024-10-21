package service.Custom;

import dto.Item;
import dto.OrderDetail;
import javafx.collections.ObservableList;
import service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    boolean addItem(Item item);

    ObservableList<Item> getAllItems();

    boolean updateItem(Item item);

    boolean deleteItem(String id);

    Item searchItem(String id);

    List<String> getAllItemCodes();

    boolean updateStock(List<OrderDetail> orderDetails);
}
