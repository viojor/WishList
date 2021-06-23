package Model;

import java.util.List;

public abstract class PurchasableItemDAO<T> {

    public abstract void createTable();

    public abstract void insert(T newElement);

    public abstract void update(T upgradedElement);

    public abstract void delete(int idElementRemove);

    public abstract List<T> getByState(String stateOfElement);

    public abstract T getById(int elementId);

    public abstract int getCurrentMaxId();

    public abstract void updateState(int elementId);
}
