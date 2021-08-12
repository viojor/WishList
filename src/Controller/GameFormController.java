package Controller;

import Model.Game;
import Model.GameDAO;
import Model.PurchasableItem;
import View.GameForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class GameFormController implements ActionListener {

    private static final String ERROR_GAME_FORM_TITLE = "Error creating Game";

    private final GameForm _viewGameForm;
    private final GameDAO _gameDAO;
    private Game _gameModel;
    private DefaultTableModel _defaultTableGameModel;

    private String genreSelected;
    private String urlGameCover;

    private final FormValidator _formValidation;

    public GameFormController(GameForm viewGameForm) {

        _viewGameForm = viewGameForm;
        _gameDAO = new GameDAO();

        _viewGameForm.CreateB.addActionListener(this);
        _viewGameForm.CoverB.addActionListener(this);

        DefaultComboBoxModel<String> genresGamesDefaultModel = new DefaultComboBoxModel<>(Game.GENRES_GAMES);
        _viewGameForm.GenreCB.setModel(genresGamesDefaultModel);
        _viewGameForm.GenreCB.addActionListener(this);

        _formValidation = new FormValidator();
    }

    public void setDefaultListModel(DefaultTableModel defaultTableModel){

        _defaultTableGameModel = defaultTableModel;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _viewGameForm.CreateB) {
            if(_formValidation.isFieldNotEmpty(_viewGameForm.NameTF) & _formValidation.isFieldNotEmpty(_viewGameForm.EstimatedHoursTF) &
                    _formValidation.isFieldNotEmpty(_viewGameForm.TotalHoursTF) & _formValidation.isFieldNotEmpty(_viewGameForm.PriceTF) &
                    _formValidation.isFieldNotEmpty(_viewGameForm.ReleaseDateDP.getEditor())
                    & _formValidation.isComboBoxNotEmpty(_viewGameForm.GenreCB.getEditor())
                    && _formValidation.isFieldNumericReal(_viewGameForm.EstimatedHoursTF) & _formValidation.isFieldNumericReal(_viewGameForm.TotalHoursTF) &
                    _formValidation.isFieldNumericReal(_viewGameForm.PriceTF)){

                createGameWithInputs();

                Vector<PurchasableItem> itemDataArray = new Vector<>(1);
                itemDataArray.add(_gameModel);
                _defaultTableGameModel.addRow(itemDataArray);

                _viewGameForm.dispose();
            }
            else{

                String errorMsg = _formValidation.getFinalErrorMsg();
                _formValidation.resetFinalErrorMsg();

                JOptionPane.showMessageDialog(null, errorMsg, ERROR_GAME_FORM_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == _viewGameForm.CoverB) {

            urlGameCover = getUrlCoverSelected();
            loadCoverInLabel();
        }
        else if(e.getSource() == _viewGameForm.GenreCB){

            Object objectSelected = _viewGameForm.GenreCB.getSelectedItem();
            if(objectSelected != null) {

                genreSelected = objectSelected.toString();
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
        boolean isCollectorEdition = !_viewGameForm.Edition_NormalRB.isSelected();
        if(urlGameCover == null){

            urlGameCover = PurchasableItem.URL_IMAGE_NOT_AVAILABLE;
            loadCoverInLabel();
        }
        String cover = urlGameCover;

        _gameModel = new Game(id, name, price, cover, PurchasableItem.ItemState.Pending.toString() ,gender,
                releaseDateFormatDDMMYYYY, estimatedHours, totalHours, isCollectorEdition);

        addGameToDB();
    }

    private void addGameToDB() {

        _gameDAO.insert(_gameModel);
    }

    private String getUrlCoverSelected() {

        String fileUrl;
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
        else{

            fileUrl = urlGameCover;
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
                    .getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
        }
    }
}
