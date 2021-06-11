package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameFormController implements ActionListener {

    private GameForm _viewGameForm;
    private GameDAO _gameDAO;
    private Game _gameModel;
    private DefaultListModel<Game> _jListGameModel;

    private String urlGameCover;

    public GameFormController(GameForm viewGameForm, DefaultListModel<Game> jListGameModel) {

        _viewGameForm = viewGameForm;
        _gameDAO = new GameDAO();
        _jListGameModel = jListGameModel;

        _viewGameForm.CreateB.addActionListener(this);
        _viewGameForm.CoverB.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _viewGameForm.CreateB) {

            createGameWithInputs();
            _jListGameModel.addElement(_gameModel);
            _viewGameForm.dispose();
        } else if (e.getSource() == _viewGameForm.CoverB) {

            urlGameCover = getUrlCoverSelected();
            loadCoverInLabel();
        }
    }

    private void createGameWithInputs() {

        int id = _gameDAO.getCurrentMaxId() + 1;

        String name = _viewGameForm.NameTF.getText();
        String price = _viewGameForm.PriceTF.getText();
        String gender = _viewGameForm.GenderTF.getText();
        String releaseDate = _viewGameForm.ReleaseDateTF.getText();
        String estimatedHours = _viewGameForm.EstimatedHoursTF.getText();
        String totalHours = _viewGameForm.TotalHoursTF.getText();
        String cover = urlGameCover;

        _gameModel = new Game(id, name, price, gender, releaseDate, estimatedHours, totalHours, cover, Game.GameStatus.Pending.toString());

        addGameToDB();
    }

    private void addGameToDB() {

        _gameDAO.insertGame(_gameModel);
    }

    private String getUrlCoverSelected() {

        String fileUrl = null;
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(_viewGameForm);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();
            fileUrl = selectedFile.getAbsolutePath();
        }
        return fileUrl;
    }

    private void loadCoverInLabel() {

        BufferedImage coverImage = null;
        try {

            coverImage = ImageIO.read(new File(urlGameCover));
        } catch (IOException e) {

            System.out.println("Error loading the cover on the label (GameForm): " + e);
        }

        if (coverImage != null) {

            _viewGameForm.CoverImage.setIcon(new ImageIcon(new ImageIcon(coverImage).getImage()
                    .getScaledInstance(150, 200, Image.SCALE_DEFAULT)));
        }
    }
}
