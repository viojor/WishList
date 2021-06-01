package Controller;

import Model.Game;
import MySQL.DBConnector;

public class example {

    public static void main(String[] args) {

        DBConnector dbConnector = new DBConnector();

        Game gameA = new Game(4, "gameA", "34.99€", "action", "03/04/05", "2h", "4h", null);
        dbConnector.insertGame(gameA);

        Game gameB = new Game(2, "gameB", "34.99€", "action", "03/04/05", "2h", "4h", null);
        dbConnector.updateGame(gameB);

        dbConnector.disconnectDB();
    }
}
