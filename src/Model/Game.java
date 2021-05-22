package Model;

import java.awt.*;

public class Game {

    private enum GameStatus {

        Purchased,
        Pending
    }

    private final int _id;

    private final String _name;
    private final String _price;
    private final String _gender;
    private final String _releaseDate;
    private final String _estimatedHours;
    private final String _totalsHours;

    private final Image _cover;

    private final GameStatus _state;

    public Game(int id, String name, String price, String gender, String releaseDate, String estimatedHours,
                String totalsHours, Image cover) {

        _id = id;
        _name = name;
        _price = price;
        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
        _cover = cover;
        _state = GameStatus.Pending;
    }
}
