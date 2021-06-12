package Model;

import java.util.Objects;

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
    private String _cover;

    public Game() {

    }

    public Game(int id, String name, String price, String gender, String releaseDate, String estimatedHours,
                String totalsHours, String cover, String state) {

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
                String totalsHours, String cover, String state) {

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

    public String get_cover() {

        return _cover;
    }

    public String get_state() {

        return _state;
    }

    //Setters
    public void set_state(String _state) {

        this._state = _state;
    }

    //toString
    public String toString() {

        return _name;
    }

    //equals
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {

            return false;
        }

        Game game = (Game) o;
        return _id == game._id && Objects.equals(_name, game._name) && Objects.equals(_price, game._price)
                && Objects.equals(_gender, game._gender) && Objects.equals(_releaseDate, game._releaseDate)
                && Objects.equals(_estimatedHours, game._estimatedHours) && Objects.equals(_totalsHours, game._totalsHours)
                && Objects.equals(_state, game._state) && Objects.equals(_cover, game._cover);
    }

    //hashCode
    public int hashCode() {

        return Objects.hash(_id, _name, _price, _gender, _releaseDate, _estimatedHours, _totalsHours, _state, _cover);
    }
}
