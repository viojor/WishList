package Model;

import MySQL.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class GameDAO extends PurchasableItemDAO<Game> {

    public static final String GAMES_TABLE_NAME = "GAMES";

    public GameDAO() {

        Connection connection = DBConnector.getConnection();
        if (!DBConnector.existTable(GAMES_TABLE_NAME, connection)) {

            createTable();
        }
    }

    @Override
    public void createTable() {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("CREATE TABLE " + GAMES_TABLE_NAME + " (id INT PRIMARY KEY auto_increment, cover VARCHAR(255)," +
                    " name VARCHAR(255), price VARCHAR(255), gender VARCHAR(255), release_date VARCHAR(255)," +
                    " estimated_hours VARCHAR(255), total_hours VARCHAR(255), state VARCHAR(255), is_collector_edition BIT)");

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error creating the table '" + GAMES_TABLE_NAME + "': " + e);
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
            boolean isCollectorEdition = newGame.isCollectorEdition();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + GAMES_TABLE_NAME +
                    " (cover, name, price, gender, release_date, estimated_hours, total_hours, state, is_collector_edition)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cover);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, price);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, releaseDate);
            preparedStatement.setString(6, estimatedHours);
            preparedStatement.setString(7, totalHours);
            preparedStatement.setString(8, state);
            preparedStatement.setBoolean(9, isCollectorEdition);

            preparedStatement.execute();

            preparedStatement.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error inserting a new element on '" + GAMES_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public void updatePriceWithId(String newPrice, int idElement){

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE " + GAMES_TABLE_NAME +
                    " SET price = '" + newPrice + "'"  +
                    " WHERE id = " + idElement);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the price of an element from '" + GAMES_TABLE_NAME + "' using a given Id: " + e);
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
            boolean isCollectorEdition = upgradedGame.isCollectorEdition();

            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE " + GAMES_TABLE_NAME +
                    " SET cover = '" + cover + "', name = '" + name + "', price = '" + price +
                    "', gender = '" + gender + "', release_date = '" + releaseDate + "', estimated_hours = '" +
                    estimatedHours + "', total_hours = '" + totalHours + "', state = '" + state +
                    "', is_collector_edition = " + isCollectorEdition +
                    " WHERE id = " + id);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating an element from '" + GAMES_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public void delete(int idGameRemove) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM " + GAMES_TABLE_NAME + " WHERE id = " + idGameRemove);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error removing an entry from '" + GAMES_TABLE_NAME + "': " + e);
        }
    }

    @Override
    public List<PurchasableItem> getByState(String stateOfGame) {

        List<PurchasableItem> gameList = new LinkedList<>();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + GAMES_TABLE_NAME + " WHERE state = '" + stateOfGame + "'");
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
                boolean isCollectorEdition = rs.getBoolean("is_collector_edition");

                Game gameSelected = new Game(id, name, price, cover, state, gender, releaseDate, estimatedHours, totalHours, isCollectorEdition);
                gameList.add(gameSelected);
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting games with status " + stateOfGame + " from '" + GAMES_TABLE_NAME + "': " + e);
        }
        return gameList;
    }

    @Override
    public Game getById(int gameId) {

        Game gameWithId = new Game();
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + GAMES_TABLE_NAME + " WHERE id = " + gameId + "");
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
                boolean isCollectorEdition = rs.getBoolean("is_collector_edition");

                gameWithId = new Game(id, name, price, cover, state, gender, releaseDate, estimatedHours, totalHours, isCollectorEdition);
            }

            st.close();
            rs.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the game with the id " + gameId + " from '" + GAMES_TABLE_NAME + "': " + e);
        }
        return gameWithId;
    }

    public int getCurrentMaxId() {

        int maxId = 0;
        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM " + GAMES_TABLE_NAME);
            while (rs.next()) {
                if (rs.getInt(1) > maxId) {

                    maxId = rs.getInt(1);
                }
            }

            rs.close();
            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error getting the max id from '" + GAMES_TABLE_NAME + "': " + e);
        }
        return maxId;
    }

    @Override
    public void updateState(int gameId) {

        try {

            Connection connection = DBConnector.getConnection();

            Statement st = connection.createStatement();
            //We can only change from Pending to Purchased
            st.executeUpdate("UPDATE " + GAMES_TABLE_NAME + " SET state = '" + PurchasableItem.ItemState.Purchased +
                    "' WHERE id = " + gameId);

            st.close();
            DBConnector.disconnectDB(connection);
        } catch (SQLException e) {

            System.out.println("Error updating the state of an element from '" + GAMES_TABLE_NAME + "': " + e);
        }
    }
}
