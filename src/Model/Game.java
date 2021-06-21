package Model;

import java.util.Objects;

public class Game extends PurchasableItem{

    private String _gender;
    private String _releaseDate;
    private String _estimatedHours;
    private String _totalsHours;

    public Game() {

    }

    public Game(int id, String name, String price, String cover, String state, String gender, String releaseDate,
                String estimatedHours, String totalsHours) {

        super._id = id;
        super._name = name;
        super._price = price;
        super._cover = cover;
        super._state = state;

        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
    }

    public Game(String name, String price, String cover, String state, String gender, String releaseDate,
                String estimatedHours, String totalsHours) {

        super._name = name;
        super._price = price;
        super._cover = cover;
        super._state = state;

        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
    }

    //Getters
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

    //toString
    public String toString() {

        return super._name;
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
        return super._id == game._id && Objects.equals(super._name, game._name) && Objects.equals(super._price, game._price)
                && Objects.equals(super._state, game._state) && Objects.equals(super._cover, game._cover)
                && Objects.equals(_gender, game._gender) && Objects.equals(_releaseDate, game._releaseDate)
                && Objects.equals(_estimatedHours, game._estimatedHours) && Objects.equals(_totalsHours, game._totalsHours);
    }

    //hashCode
    public int hashCode() {

        return Objects.hash(super._id, super._name, super._price, super._state, super._cover, _gender, _releaseDate,
                _estimatedHours, _totalsHours);
    }
}
