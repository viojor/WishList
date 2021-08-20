package model;

import java.util.List;

public abstract class PurchasableItemDAO<T> {

    public static final int STRINGS_MAX_SIZE = 255;

    public abstract void createTable();

    public abstract void insert(T newElement);

    public abstract void updatePriceWithId(String newPrice, int idElement);

    public abstract void update(T upgradedElement);

    public abstract void delete(int idElementRemove);

    public abstract List<PurchasableItem> getByState(String stateOfElement);

    public abstract T getById(int elementId);

    public abstract T getByOrder(int orderItemList, String stateOfElement);

    public abstract int getCurrentMaxId();

    public abstract void updateState(int elementId);
}
