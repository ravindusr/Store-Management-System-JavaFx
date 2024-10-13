package Repository;

import dto.Item;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    ObservableList<Item> findAll();
    boolean delete(String id);
}
