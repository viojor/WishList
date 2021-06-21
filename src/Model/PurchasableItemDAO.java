package Model;

import java.util.List;

public interface PurchasableItemDAO<T> {

    void createTable();
    void insert(T itemToAdd);
    void update(T itemToUpdate);
    void delete(int idItemRemove);
    List<T> getByState(String stateOfItem);
    T getById(int itemId);
    int getCurrentMaxId();
    void updateState(int itemId);
}
