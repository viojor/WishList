package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameInfoController {

    private GameInfo _gameInfoView;
    private GameDAO _gameDAO;
    private Game _gameModel;

    public GameInfoController(GameInfo gameInfoView, int idGameToShow) {

        _gameInfoView = gameInfoView;
        _gameDAO = new GameDAO();
        _gameModel = _gameDAO.getGameById(idGameToShow);

        setGameInfoIntoView();
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
                    .getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        }
    }
}
