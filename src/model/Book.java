package model;

import java.util.Objects;

public class Book extends PurchasableItem{

    private String _author;
    private int _pagesNumber;
    private String _ISBN;
    private String _publicationDate;

    public Book(){

    }

    public Book(int id, String name, String price, String cover, String state, String author, int pagesNumber,
                String ISBN, String publicationDate){

        super._id = id;
        super._name = name;
        super._price = price;
        super._cover = cover;
        super._state = state;

        _author = author;
        _pagesNumber = pagesNumber;
        _ISBN = ISBN;
        _publicationDate = publicationDate;
    }

    //Getters
    public String getAuthor() {

        return _author;
    }

    public int getPagesNumber() {

        return _pagesNumber;
    }

    public String getISBN() {

        return _ISBN;
    }

    public String getPublicationDate() {

        return _publicationDate;
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
                && Objects.equals(_author, book._author) && _pagesNumber == book._pagesNumber
                && Objects.equals(_ISBN, book._ISBN)&& Objects.equals(_publicationDate, book._publicationDate);
    }

    //hashCode
    @Override
    public int hashCode() {

        return Objects.hash(super._id, super._name, super._price, super._state, super._cover, _author, _pagesNumber, _ISBN,
                _publicationDate);
    }
}
