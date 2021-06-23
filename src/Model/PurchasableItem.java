package Model;

public abstract class PurchasableItem {

    public static final String URL_IMAGE_NOT_AVAILABLE = "C:\\Cover_Not_Available.jpg";

    public enum ItemState {

        Purchased,
        Pending
    }

    protected int _id;
    protected String _name;
    protected String _cover;
    protected String _price;
    protected String _state;

    public int getId() {

        return _id;
    }

    public String getName() {

        return _name;
    }

    public String getCover() {

        return _cover;
    }

    public String getPrice() {

        return _price;
    }

    public String getState() {

        return _state;
    }

    public void setState(String state) {

        _state = state;
    }
}
