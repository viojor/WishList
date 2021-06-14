package Model;

import MySQL.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

            String cover = elementToInsert.get_cover();
            String name = elementToInsert.get_name();
            String price = elementToInsert.get_price();
            String gender = elementToInsert.get_gender();
            String releaseDate = elementToInsert.get_releaseDate();
            String estimatedHours = elementToInsert.get_estimatedHours();
            String totalHours = elementToInsert.get_totalsHours();
            String state = elementToInsert.get_state();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO GAMES (cover, name, price, gender, " +
                    "release_date, estimated_hours, total_hours, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cover);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, price);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, releaseDate);
            preparedStatement.setString(6, estimatedHours);
            preparedStatement.setString(7, totalHours);
            preparedStatement.setString(8, state);

            preparedStatement.execute();

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error inserting a new element on 'GAMES': " + e);
        }
    }

    public void updateGame(Game upgradedElement) {

        try {

            Connection connection = DBConnector.getConnection();

            int id = upgradedElement.get_id();
            String cover = upgradedElement.get_cover();
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

    public List<Game> getGamesByStatus(String statusOfGame) {

        List<Game> gameList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE state = '" + statusOfGame + "'");
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

                Game gameSelected = new Game(id, name, price, gender, releaseDate, estimatedHours, totalHours, cover, state);
                gameList.add(gameSelected);
            }

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting games with status " + statusOfGame + " from 'GAMES': " + e);
        }
        return gameList;
    }

    public Game getGameById(int gameId) {

        Game gameWithId = new Game();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE id = " + gameId + "");
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

                gameWithId = new Game(id, name, price, gender, releaseDate, estimatedHours, totalHours, cover, state);
            }

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the game with the id " + gameId + " from 'GAMES': " + e);
        }
        return gameWithId;
    }

    public int getCurrentMaxId() {

        int maxId = 0;
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM GAMES");
            while (rs.next()) {
                if (rs.getInt(1) > maxId) {

                    maxId = rs.getInt(1);
                }
            }
            rs.close();
        } catch (SQLException e) {

            System.out.println("Error getting the max id from 'GAMES': " + e);
        }
        return maxId;
    }

    public void updateStateGame(int gameId) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            //We can only change from Pending to Purchased
            st.executeUpdate("UPDATE GAMES SET state = '" + Game.GameStatus.Purchased + "' WHERE id = " + gameId);

            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the state of an element from 'GAMES': " + e);
        }
    }
}
