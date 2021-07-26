package Controller;

import Model.Game;
import Model.GameDAO;
import Model.PurchasableItem;
import View.GameInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class GameInfoController implements ActionListener {

    private final GameInfo _gameInfoView;
    private final GameDAO _gameDAO;
    private final Game _gameModel;
    private DefaultListModel<PurchasableItem> _defaultListModel;

    public GameInfoController(GameInfo gameInfoView, int idGameToShow) {

        _gameInfoView = gameInfoView;
        _gameDAO = new GameDAO();
        _gameModel = _gameDAO.getById(idGameToShow);

        _gameInfoView.purchasedB.addActionListener(this);

        setGameInfoIntoView();
        setPurchasedButtonVisibility();
    }

    public void setDefaultListModel(DefaultListModel<PurchasableItem> defaultListModel){

        _defaultListModel = defaultListModel;
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

    private void setPurchasedButtonVisibility() {

        if (_gameModel.getState().equals(PurchasableItem.ItemState.Purchased.toString())) {

            _gameInfoView.purchasedB.setVisible(false);
            _gameInfoView.purchasedB.setEnabled(false);
        } else { //PurchasableItem.ItemState.Pending

            _gameInfoView.purchasedB.setVisible(true);
            _gameInfoView.purchasedB.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _gameInfoView.purchasedB) {

            int defaultListModelElementIndex = _defaultListModel.indexOf(_gameModel);

            String newPrice = (String) JOptionPane.showInputDialog(null,"Indicate purchase price",
                    "Purchase game", JOptionPane.PLAIN_MESSAGE, null, null, "0");

            if(newPrice == null || newPrice.isEmpty()){ // Clicked close (x) or cancel button.

                showMessageDialog("The input field cant be empty", "Purchase game", JOptionPane.ERROR_MESSAGE);
            }
            else{
                if(Pattern.matches(Regex.WHITESPACE_REGEX, newPrice) || !Pattern.matches(Regex.ONLY_REAL_NUMBERS_REGEX,
                        newPrice)){

                    showMessageDialog("Only numeric values are accepted (i.e: 22.45)", "Purchase game",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{

                    _gameModel.setState(PurchasableItem.ItemState.Purchased.toString());
                    _gameDAO.updateState(_gameModel.getId());
                    _gameDAO.updatePriceWithId(newPrice, _gameModel.getId());

                    //Remove the element from the list cause we are changing the attribute we use to get them (moved to other tab)
                    _defaultListModel.remove(defaultListModelElementIndex);

                    _gameInfoView.dispose();
                }
            }
        }
    }

    private void showMessageDialog(String message, String title, int dialogType){

        JOptionPane.showMessageDialog(null,message, title, dialogType);
    }
}
