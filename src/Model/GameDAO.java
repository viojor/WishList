package Model;

import MySQL.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class GameDAO implements PurchasableItemDAO<Game>{

    public GameDAO() {

        Connection connection = DBConnector.getConnection();
        if (!DBConnector.existTable("GAMES", connection)) {

            createTable();
        }
    }

    @Override
    public void createTable() {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE GAMES (id INT PRIMARY KEY auto_increment, cover VARCHAR(255)," +
                    " name VARCHAR(255), price VARCHAR(255), gender VARCHAR(255), release_date VARCHAR(255)," +
                    " estimated_hours VARCHAR(255), total_hours VARCHAR(255), state VARCHAR(255))");

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error creating the table 'GAMES': " + e);
        }
    }

    @Override
    public void insert(Game newGame) {

        try {

            Connection connection = DBConnector.getConnection();

            String cover = newGame.getCover();
            String name = newGame.getName();
            String price = newGame.getPrice();
            String gender = newGame.getGender();
            String releaseDate = newGame.getReleaseDate();
            String estimatedHours = newGame.getEstimatedHours();
            String totalHours = newGame.getTotalsHours();
            String state = newGame.getState();

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

            preparedStatement.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error inserting a new element on 'GAMES': " + e);
        }
    }

    @Override
    public void update(Game upgradedGame) {

        try {

            Connection connection = DBConnector.getConnection();

            int id = upgradedGame.getId();
            String cover = upgradedGame.getCover();
            String name = upgradedGame.getName();
            String price = upgradedGame.getPrice();
            String gender = upgradedGame.getGender();
            String releaseDate = upgradedGame.getReleaseDate();
            String estimatedHours = upgradedGame.getEstimatedHours();
            String totalHours = upgradedGame.getTotalsHours();
            String state = upgradedGame.getState();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE GAMES " +
                    "SET cover = '" + cover + "', name = '" + name + "', price = '" + price +
                    "', gender = '" + gender + "', release_date = '" + releaseDate + "', estimated_hours = '" +
                    estimatedHours + "', total_hours = '" + totalHours + "', state = '" + state + "'" +
                    " WHERE id = " + id);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating an element from 'GAMES': " + e);
        }
    }

    @Override
    public void delete(int idGameRemove) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM GAMES WHERE id = " + idGameRemove);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error removing an entry from 'GAMES': " + e);
        }
    }

    @Override
    public List<Game> getByState(String stateOfGame) {

        List<Game> gameList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE state = '" + stateOfGame + "'");
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String cover = rs.getString("cover");
                String state = rs.getString("state");
                String gender = rs.getString("gender");
                String releaseDate = rs.getString("release_date");
                String estimatedHours = rs.getString("estimated_hours");
                String totalHours = rs.getString("total_hours");

                Game gameSelected = new Game(id, name, price, cover, state, gender, releaseDate, estimatedHours, totalHours);
                gameList.add(gameSelected);
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting games with status " + stateOfGame + " from 'GAMES': " + e);
        }
        return gameList;
    }

    @Override
    public Game getById(int gameId) {

        Game gameWithId = new Game();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES WHERE id = " + gameId + "");
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String cover = rs.getString("cover");
                String state = rs.getString("state");
                String gender = rs.getString("gender");
                String releaseDate = rs.getString("release_date");
                String estimatedHours = rs.getString("estimated_hours");
                String totalHours = rs.getString("total_hours");

                gameWithId = new Game(id, name, price, cover, state, gender, releaseDate, estimatedHours, totalHours);
            }

            st.close();
            rs.close();
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
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the max id from 'GAMES': " + e);
        }
        return maxId;
    }

    @Override
    public void updateState(int gameId) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            //We can only change from Pending to Purchased
            st.executeUpdate("UPDATE GAMES SET state = '" + PurchasableItem.ItemState.Purchased + "' WHERE id = " + gameId);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the state of an element from 'GAMES': " + e);
        }
    }
}
