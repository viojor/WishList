package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameFormController implements ActionListener {

    private static final String[] GENRES_GAMES = {"Action", "Adventure", "Platform", "Shooter", "Fighting", "Beat 'em up",
            "Survival Horror", "Visual Novel", "RPG", "Roguelikes", "Simulation", "Real-time strategy",
            "Turn-based strategy", "Sports", "MMO", "Other"};
    private static final String NO_ASSIGNED_GENRE = "No Assigned";

    private final GameForm _viewGameForm;
    private final GameDAO _gameDAO;
    private Game _gameModel;
    private DefaultListModel<Game> _defaultListGameModel;

    private String genreSelected;
    private String urlGameCover;

    public GameFormController(GameForm viewGameForm) {

        _viewGameForm = viewGameForm;
        _gameDAO = new GameDAO();

        _viewGameForm.CreateB.addActionListener(this);
        _viewGameForm.CoverB.addActionListener(this);

        DefaultComboBoxModel<String> genresGamesDefaultModel = new DefaultComboBoxModel<>(GENRES_GAMES);
        _viewGameForm.GenreCB.setModel(genresGamesDefaultModel);
        _viewGameForm.GenreCB.addActionListener(this);
    }

    public void setDefaultListModel(DefaultListModel<Game> defaultListModel){

        _defaultListGameModel = defaultListModel;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _viewGameForm.CreateB) {

            createGameWithInputs();
            _defaultListGameModel.addElement(_gameModel);
            _viewGameForm.dispose();
        } else if (e.getSource() == _viewGameForm.CoverB) {

            urlGameCover = getUrlCoverSelected();
            loadCoverInLabel();
        }
        else if(e.getSource() == _viewGameForm.GenreCB){

            Object objectSelected = _viewGameForm.GenreCB.getSelectedItem();
            if(objectSelected != null){

                genreSelected = objectSelected.toString();
            }
            else{

                genreSelected = NO_ASSIGNED_GENRE;
            }
        }
    }

    private void createGameWithInputs() {

        int id = _gameDAO.getCurrentMaxId() + 1;

        String name = _viewGameForm.NameTF.getText();
        String price = _viewGameForm.PriceTF.getText();
        String gender = genreSelected;

        Date releaseDate = _viewGameForm.ReleaseDateDP.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String releaseDateFormatDDMMYYYY = formatter.format(releaseDate);

        String estimatedHours = _viewGameForm.EstimatedHoursTF.getText();
        String totalHours = _viewGameForm.TotalHoursTF.getText();
        String cover = urlGameCover;

        _gameModel = new Game(id, name, price, gender, releaseDateFormatDDMMYYYY, estimatedHours, totalHours, cover, Game.GameStatus.Pending.toString());

        addGameToDB();
    }

    private void addGameToDB() {

        _gameDAO.insertGame(_gameModel);
    }

    private String getUrlCoverSelected() {

        String fileUrl = null;
        JFileChooser chooser = new JFileChooser();

        String[] imageSuffixes = ImageIO.getReaderFileSuffixes();
        for(String suffix : imageSuffixes){

            FileFilter filter = new FileNameExtensionFilter(suffix + " files", suffix);
            chooser.addChoosableFileFilter(filter);
        }

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
