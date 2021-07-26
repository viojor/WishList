package Model;

import MySQL.DBConnector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookDAO extends PurchasableItemDAO<Book> {

    public static final String BOOKS_TABLE_NAME = "BOOKS";

    public BookDAO(){

        Connection connection = DBConnector.getConnection();
        if (!DBConnector.existTable(BOOKS_TABLE_NAME, connection)) {

            createTable();
        }
    }

    @Override
    public void createTable() {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE " + BOOKS_TABLE_NAME + " (id INT PRIMARY KEY auto_increment, cover VARCHAR(255)," +
                    " name VARCHAR(255), price VARCHAR(255), author VARCHAR(255), pages_number INT," +
                    " ISBN VARCHAR(255), publication_date VARCHAR(255), state VARCHAR(255))");

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error creating the table '" + BOOKS_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public void insert(Book newBook) {

        try {

            Connection connection = DBConnector.getConnection();

            String cover = newBook.getCover();
            String name = newBook.getName();
            String price = newBook.getPrice();
            String author = newBook.getAuthor();
            int pagesNumber = newBook.getPagesNumber();
            String ISBN = newBook.getISBN();
            String publicationDate = newBook.getPublicationDate();
            String state = newBook.getState();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + BOOKS_TABLE_NAME +
                    " (cover, name, price, author, pages_number, ISBN, publication_date, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cover);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, price);
            preparedStatement.setString(4, author);
            preparedStatement.setInt(5, pagesNumber);
            preparedStatement.setString(6, ISBN);
            preparedStatement.setString(7, publicationDate);
            preparedStatement.setString(8, state);

            preparedStatement.execute();

            preparedStatement.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error inserting a new element on '" + BOOKS_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public void updatePriceWithId(String newPrice, int idElement){

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE " + BOOKS_TABLE_NAME +
                    " SET price = '" + newPrice + "'"  +
                    " WHERE id = " + idElement);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the price of an element from '" + BOOKS_TABLE_NAME + "' using a given Id: " + e);
        }
    }

    @Override
    public void update(Book upgradedBook) {

        try {

            Connection connection = DBConnector.getConnection();

            int id = upgradedBook.getId();
            String cover = upgradedBook.getCover();
            String name = upgradedBook.getName();
            String price = upgradedBook.getPrice();
            String author = upgradedBook.getAuthor();
            int pagesNumber = upgradedBook.getPagesNumber();
            String ISBN = upgradedBook.getISBN();
            String publicationDate = upgradedBook.getPublicationDate();
            String state = upgradedBook.getState();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE " + BOOKS_TABLE_NAME +
                    " SET cover = '" + cover + "', name = '" + name + "', price = '" + price +
                    "', author = '" + author + "', pages_number = " + pagesNumber + ", ISBN = '" + ISBN +
                    "', publication_date = '" + publicationDate + "', state = '" + state + "'" +
                    " WHERE id = " + id);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating an element from '" + BOOKS_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public void delete(int idBookRemove) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM " + BOOKS_TABLE_NAME + " WHERE id = " + idBookRemove);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error removing an entry from '" + BOOKS_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public List<PurchasableItem> getByState(String stateOfBook) {

        List<PurchasableItem> bookList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + BOOKS_TABLE_NAME + " WHERE state = '" + stateOfBook + "'");
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String cover = rs.getString("cover");
                String state = rs.getString("state");
                String author = rs.getString("author");
                int pagesNumber = rs.getInt("pages_number");
                String ISBN = rs.getString("ISBN");
                String publicationDate = rs.getString("publication_date");

                Book bookSelected = new Book(id, name, price, cover, state, author, pagesNumber, ISBN, publicationDate);
                bookList.add(bookSelected);
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting books with status " + stateOfBook + " from '" + BOOKS_TABLE_NAME + "': " + e);
        }
        return bookList;
    }

    @Override
    public Book getById(int bookId) {

        Book bookWithId = new Book();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + BOOKS_TABLE_NAME + " WHERE id = " + bookId + "");
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String cover = rs.getString("cover");
                String state = rs.getString("state");
                String author = rs.getString("author");
                int pagesNumber = rs.getInt("pages_number");
                String ISBN = rs.getString("ISBN");
                String publicationDate = rs.getString("publication_date");

                bookWithId = new Book(id, name, price, cover, state, author, pagesNumber, ISBN, publicationDate);
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the book with the id " + bookId + " from '" + BOOKS_TABLE_NAME + "': " + e);
        }
        return bookWithId;
    }

    @Override
    public int getCurrentMaxId() {

        int maxId = 0;
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM " + BOOKS_TABLE_NAME);
            while (rs.next()) {
                if (rs.getInt(1) > maxId) {

                    maxId = rs.getInt(1);
                }
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the max id from '" + BOOKS_TABLE_NAME + "': " + e);
        }
        return maxId;
    }

    @Override
    public void updateState(int bookId) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            //We can only change from Pending to Purchased
            st.executeUpdate("UPDATE " + BOOKS_TABLE_NAME + " SET state = '" + PurchasableItem.ItemState.Purchased + "' WHERE id = " + bookId);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the state of an element from '" + BOOKS_TABLE_NAME + "': " + e);
        }
    }
}
