package Model;

import java.net.URL;

public class Game {

    public enum GameStatus {

        Purchased,
        Pending
    }

    private int _id;

    private String _name;
    private String _price;
    private String _gender;
    private String _releaseDate;
    private String _estimatedHours;
    private String _totalsHours;
    private String _state;

    private URL _cover;

    public Game() {

    }

    public Game(int id, String name, String price, String gender, String releaseDate, String estimatedHours,
                String totalsHours, URL cover, String state) {

        _id = id;
        _name = name;
        _price = price;
        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
        _cover = cover;
        _state = state;
    }

    public Game(String name, String price, String gender, String releaseDate, String estimatedHours,
                String totalsHours, URL cover, String state) {

        _name = name;
        _price = price;
        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
        _cover = cover;
        _state = state;
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

    public String get_state() {

        return _state;
    }
}
