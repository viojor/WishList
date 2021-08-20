package model;

import java.util.Objects;

public class Game extends PurchasableItem{

    public static final String[] GENRES_GAMES = {"", "Action", "Adventure", "Platform", "Shooter", "Fighting", "Beat 'em up",
            "Survival Horror", "Visual Novel", "RPG", "Roguelike", "Simulation", "Real-time strategy",
            "Turn-based strategy", "Sports", "MMO", "Other"};

    private String _gender;
    private String _releaseDate;
    private String _estimatedHours;
    private String _totalsHours;
    private boolean _isCollectorEdition;

    public Game() {

    }

    public Game(int id, String name, String price, String cover, String state, String gender, String releaseDate,
                String estimatedHours, String totalsHours, boolean isCollectorEdition) {

        super._id = id;
        super._name = name;
        super._price = price;
        super._cover = cover;
        super._state = state;

        _gender = gender;
        _releaseDate = releaseDate;
        _estimatedHours = estimatedHours;
        _totalsHours = totalsHours;
        _isCollectorEdition = isCollectorEdition;
    }

    //Getters
    public String getGender() {

        return _gender;
    }

    public String getReleaseDate() {

        return _releaseDate;
    }

    public String getEstimatedHours() {

        return _estimatedHours;
    }

    public String getTotalsHours() {

        return _totalsHours;
    }

    public boolean isCollectorEdition(){

        return _isCollectorEdition;
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
                && Objects.equals(_estimatedHours, game._estimatedHours) && Objects.equals(_totalsHours, game._totalsHours)
                && _isCollectorEdition == game._isCollectorEdition;
    }

    //hashCode
    public int hashCode() {

        return Objects.hash(super._id, super._name, super._price, super._state, super._cover, _gender, _releaseDate,
                _estimatedHours, _totalsHours, _isCollectorEdition);
    }
}
