package Model;

import java.util.Objects;

public class Book extends PurchasableItem{

    private String _author;
    private String _publicationDate;
    private String _pagesNumber;
    private String _ISBN;

    public Book(){

    }

    public Book(int id, String name, String price, String state, String cover, String author, String publicationDate,
                String pagesNumber, String ISBN){

        super._id = id;
        super._name = name;
        super._price = price;
        super._state = state;
        super._cover = cover;

        _author = author;
        _publicationDate = publicationDate;
        _pagesNumber = pagesNumber;
        _ISBN = ISBN;
    }

    public Book(String name, String price, String state, String cover, String author, String publicationDate,
                String pagesNumber, String ISBN){

        super._name = name;
        super._price = price;
        super._state = state;
        super._cover = cover;

        _author = author;
        _publicationDate = publicationDate;
        _pagesNumber = pagesNumber;
        _ISBN = ISBN;
    }

    //Getters
    public String getAuthor() {

        return _author;
    }

    public String getPublicationDate() {

        return _publicationDate;
    }

    public String getPagesNumber() {

        return _pagesNumber;
    }

    public String getISBN() {

        return _ISBN;
    }

    //toString

    @Override
    public String toString() {

        return super._name + " (" + _author + ")";
    }

    //equals
    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {

            return false;
        }

        Book book = (Book) o;
        return super._id == book._id && Objects.equals(super._name, book._name) && Objects.equals(super._price, book._price)
                && Objects.equals(super._state, book._state) && Objects.equals(super._cover, book._cover)
                && Objects.equals(_author, book._author) && Objects.equals(_publicationDate, book._publicationDate)
                && Objects.equals(_pagesNumber, book._pagesNumber) && Objects.equals(_ISBN, book._ISBN);
    }

    //hashCode
    @Override
    public int hashCode() {

        return Objects.hash(super._id, super._name, super._price, super._state, super._cover, _author, _publicationDate,
                _pagesNumber, _ISBN);
    }
}
