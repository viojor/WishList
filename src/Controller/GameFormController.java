package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFormController implements ActionListener {

    private GameForm _viewGameForm;
    private GameDAO _gameDAO;
    private Game _gameModel;

    public GameFormController(GameForm viewGameForm) {

        _viewGameForm = viewGameForm;
        _gameDAO = new GameDAO();

        _viewGameForm.CreateB.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _viewGameForm.CreateB) {

            addGameToDB();
        }
    }

    private void addGameToDB() {

        String name = _viewGameForm.NameTF.getText();
        String price = _viewGameForm.PriceTF.getText();
        String gender = _viewGameForm.GenderTF.getText();
        String releaseDate = _viewGameForm.ReleaseDateTF.getText();
        String estimatedHours = _viewGameForm.EstimatedHoursTF.getText();
        String totalHours = _viewGameForm.TotalHoursTF.getText();
        //String cover = null;

        _gameModel = new Game(name, price, gender, releaseDate, estimatedHours, totalHours, null, Game.GameStatus.Pending.toString());
        _gameDAO.insertGame(_gameModel);
    }
}
