package Model;

import java.net.URL;

public class Game {

    public enum GameStatus {

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

    private final URL _cover;

    private GameStatus _state;

    public Game(int id, String name, String price, String gender, String releaseDate, String estimatedHours,
                String totalsHours, URL cover) {

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

    //Getters
    public int get_id() {

        return _id;
    }

    public String get_name() {

        return _name;
    }

    public String get_price() {

        return _price;
    }

    public String get_gender() {

        return _gender;
    }

    public String get_releaseDate() {

        return _releaseDate;
    }

    public String get_estimatedHours() {

        return _estimatedHours;
    }

    public String get_totalsHours() {

        return _totalsHours;
    }

    public URL get_cover() {

        return _cover;
    }

    public GameStatus get_state() {

        return _state;
    }
}
