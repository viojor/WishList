package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameInfoController implements ActionListener {

    private final GameInfo _gameInfoView;
    private final GameDAO _gameDAO;
    private final Game _gameModel;
    private DefaultListModel<Game> _defaultListModel;

    public GameInfoController(GameInfo gameInfoView, int idGameToShow) {

        _gameInfoView = gameInfoView;
        _gameDAO = new GameDAO();
        _gameModel = _gameDAO.getGameById(idGameToShow);

        _gameInfoView.purchasedB.addActionListener(this);

        setGameInfoIntoView();
        setPurchasedButtonVisibility();
    }

    public void setDefaultListModel(DefaultListModel<Game> defaultListModel){

        _defaultListModel = defaultListModel;
    }

    private void setGameInfoIntoView() {

        loadCoverInLabel();
        _gameInfoView.nameValueL.setText(_gameModel.get_name());
        _gameInfoView.genderValueL.setText(_gameModel.get_gender());
        _gameInfoView.priceValueL.setText(_gameModel.get_price());
        _gameInfoView.releaseDateValueL.setText(_gameModel.get_releaseDate());
        _gameInfoView.estimatedHoursValueL.setText(_gameModel.get_estimatedHours());
        _gameInfoView.totalHoursValueL.setText(_gameModel.get_totalsHours());
    }

    private void loadCoverInLabel() {

        BufferedImage coverImage = null;
        try {

            coverImage = ImageIO.read(new File(_gameModel.get_cover()));
        } catch (IOException e) {

            System.out.println("Error loading the cover on the label (GameInfo): " + e);
        }

        if (coverImage != null) {

            _gameInfoView.coverImage.setIcon(new ImageIcon(new ImageIcon(coverImage).getImage()
                    .getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
        }
    }

    private void setPurchasedButtonVisibility() {

        if (_gameModel.get_state().equals(Game.GameStatus.Purchased.toString())) {

            _gameInfoView.purchasedB.setVisible(false);
            _gameInfoView.purchasedB.setEnabled(false);
        } else { //Game.GameStatus.Pending

            _gameInfoView.purchasedB.setVisible(true);
            _gameInfoView.purchasedB.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _gameInfoView.purchasedB) {

            int defaultListModelElementIndex = _defaultListModel.indexOf(_gameModel);

            _gameModel.set_state(Game.GameStatus.Purchased.toString());
            _gameDAO.updateStateGame(_gameModel.get_id());

            //Remove the element from the list cause we are changing the attribute we use to get them (moved to other tab)
            _defaultListModel.remove(defaultListModelElementIndex);

            _gameInfoView.dispose();
        }
    }
}
