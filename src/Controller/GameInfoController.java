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

    private final GameInfo _gameInfoView;
    private final Game _gameModel;

    public GameInfoController(GameInfo gameInfoView, int idGameToShow) {

        _gameInfoView = gameInfoView;
        GameDAO _gameDAO = new GameDAO();
        _gameModel = _gameDAO.getById(idGameToShow);

        setGameInfoIntoView();
    }

    private void setGameInfoIntoView() {

        loadCoverInLabel();

        _gameInfoView.nameValueL.setText(_gameModel.getName());
        _gameInfoView.genreValueL.setText(_gameModel.getGender());
        _gameInfoView.priceValueL.setText(_gameModel.getPrice());
        _gameInfoView.releaseDateValueL.setText(_gameModel.getReleaseDate());
        _gameInfoView.estimatedHoursValueL.setText(_gameModel.getEstimatedHours());
        _gameInfoView.totalHoursValueL.setText(_gameModel.getTotalsHours());

        if (_gameModel.isCollectorEdition()) {

            _gameInfoView.editionCollectorRB.setSelected(true);
        }
        else {

            _gameInfoView.editionNormalRB.setSelected(true);
        }
    }

    private void loadCoverInLabel() {

        BufferedImage coverImage = null;
        try {

            coverImage = ImageIO.read(new File(_gameModel.getCover()));
        } catch (IOException e) {

            System.out.println("Error loading the cover on the label (GameInfo): " + e);
        }

        if (coverImage != null) {

            _gameInfoView.coverImage.setIcon(new ImageIcon(new ImageIcon(coverImage).getImage()
                    .getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
        }
    }
}
