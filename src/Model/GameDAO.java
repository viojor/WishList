package Model;

import MySQL.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class GameDAO {

    public GameDAO() {

        Connection connection = DBConnector.getConnection();
        if (!DBConnector.existTable("GAMES", connection)) {

            createGamesTable();
        }
    }

    private void createGamesTable() {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE GAMES (id INT PRIMARY KEY auto_increment, cover VARCHAR(255)," +
                    " name VARCHAR(255), price VARCHAR(255), gender VARCHAR(255), release_date VARCHAR(255)," +
                    " estimated_hours VARCHAR(255), total_hours VARCHAR(255), state VARCHAR(255))");

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error creating the table 'GAMES': " + e);
        }
    }

    public void insertGame(Game elementToInsert) {

        try {

            Connection connection = DBConnector.getConnection();

            String cover = null;//elementToInsert.get_cover().toString();
            String name = elementToInsert.get_name();
            String price = elementToInsert.get_price();
            String gender = elementToInsert.get_gender();
            String releaseDate = elementToInsert.get_releaseDate();
            String estimatedHours = elementToInsert.get_estimatedHours();
            String totalHours = elementToInsert.get_totalsHours();
            String state = elementToInsert.get_state();

            Statement st = connection.createStatement();
            st.executeUpdate("INSERT INTO GAMES (cover, name, price, gender, release_date, estimated_hours, " +
                    "total_hours, state) " +
                    "VALUES ('" + cover + "', '" + name + "', '" + price + "', '" + gender + "', '" + releaseDate +
                    "', '" + estimatedHours + "', '" + totalHours + "', '" + state + "')");

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error inserting a new element on 'GAMES': " + e);
        }
    }

    public void updateGame(Game upgradedElement) {

        try {

            Connection connection = DBConnector.getConnection();

            int id = upgradedElement.get_id();
            String cover = null;//upgradedElement.get_cover().toString();
            String name = upgradedElement.get_name();
            String price = upgradedElement.get_price();
            String gender = upgradedElement.get_gender();
            String releaseDate = upgradedElement.get_releaseDate();
            String estimatedHours = upgradedElement.get_estimatedHours();
            String totalHours = upgradedElement.get_totalsHours();
            String state = upgradedElement.get_state();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE GAMES " +
                    "SET cover = '" + cover + "', name = '" + name + "', price = '" + price +
                    "', gender = '" + gender + "', release_date = '" + releaseDate + "', estimated_hours = '" +
                    estimatedHours + "', total_hours = '" + totalHours + "', state = '" + state + "'" +
                    " WHERE id = " + id);

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating an element from 'GAMES': " + e);
        }
    }

    public void deleteGame(int idElementToRemove) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM GAMES WHERE id = " + idElementToRemove + ")");

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error removing an entry from 'GAMES': " + e);
        }
    }

    public List<Game> getPendingGames() {

        List<Game> pendingGamesList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE state = 'Pending'");
            while (rs.next()) {

                int id = rs.getInt("id");
                String cover = rs.getString("cover");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String gender = rs.getString("gender");
                String releaseDate = rs.getString("release_date");
                String estimatedHours = rs.getString("estimated_hours");
                String totalHours = rs.getString("total_hours");
                String state = rs.getString("state");

                Game gameSelected = new Game(id, name, price, gender, releaseDate, estimatedHours, totalHours, null, state);
                pendingGamesList.add(gameSelected);
            }

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting pending games from 'GAMES': " + e);
        }
        return pendingGamesList;
    }

    public List<Game> getPurchasedGames() {

        List<Game> purchasedGamesList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE state = 'Purchased'");
            while (rs.next()) {

                int id = rs.getInt("id");
                String cover = rs.getString("cover");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String gender = rs.getString("gender");
                String releaseDate = rs.getString("release_date");
                String estimatedHours = rs.getString("estimated_hours");
                String totalHours = rs.getString("total_hours");
                String state = rs.getString("state");

                Game gameSelected = new Game(id, name, price, gender, releaseDate, estimatedHours, totalHours, null, state);
                purchasedGamesList.add(gameSelected);
            }

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting purchased games from 'GAMES': " + e);
        }
        return purchasedGamesList;
    }
}
