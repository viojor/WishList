package Controller;

import Model.Game;
import Model.GameDAO;
import View.GameForm;
import View.GameInfo;
import View.Home;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class HomeController implements ChangeListener {

    private static final String PENDING_TAB = "Pending";
    private static final String PURCHASED_TAB = "Purchased";

    private final Home _viewHome;
    private final GameDAO _gameDAO;

    private DefaultListModel<Game> _defaultListGamesModel;

    public HomeController(Home viewHome) {

        _viewHome = viewHome;
        _gameDAO = new GameDAO();

        _viewHome.tabbedPanel.addChangeListener(this);
        _viewHome.addGameB.addActionListener(e -> openGameForm());
    }

    public void loadTab() {

        if (_viewHome.tabbedPanel.getSelectedIndex() != -1) {

            _defaultListGamesModel = new DefaultListModel<>();
            List<Game> gameList = getGamesList();
            for (Game game : gameList) {

                _defaultListGamesModel.addElement(game);
            }

            JList<Game> gameJList = new JList<>(_defaultListGamesModel);
            gameJList.setCellRenderer(new GameRenderer());
            gameJList.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {

                    GameInfo gi = new GameInfo("GameInfo");
                    int selectedGameId = gameJList.getSelectedValue().get_id();

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    gi.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 2 / 3);

                    GameInfoController gameInfoController = new GameInfoController(gi, selectedGameId);
                    gameInfoController.setDefaultListModel(_defaultListGamesModel);

                    gi.setVisible(true);
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });

            gameJList.setBackground(new Color(156, 143, 231));
            JScrollPane jsp = (JScrollPane) _viewHome.tabbedPanel.getSelectedComponent();
            jsp.setViewportView(gameJList);
        }
    }

    private List<Game> getGamesList() {

        List<Game> gameList = new LinkedList<>();
        if (_viewHome.tabbedPanel.getTitleAt(_viewHome.tabbedPanel.getSelectedIndex()).equals(PENDING_TAB)) {

            gameList = loadPendingGames();
        } else if (_viewHome.tabbedPanel.getTitleAt(_viewHome.tabbedPanel.getSelectedIndex()).equals(PURCHASED_TAB)) {

            gameList = loadPurchasedGames();
        }
        return gameList;
    }

    private List<Game> loadPendingGames() {

        return _gameDAO.getGamesByStatus(Game.GameStatus.Pending.toString());
    }

    private List<Game> loadPurchasedGames() {

        return _gameDAO.getGamesByStatus(Game.GameStatus.Purchased.toString());
    }

    public void stateChanged(ChangeEvent e) {

        loadTab();
    }

    private void openGameForm() {

        GameForm gf = new GameForm("AddGame");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gf.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 2 / 3);

        GameFormController controller = new GameFormController(gf);
        controller.setDefaultListModel(_defaultListGamesModel);

        gf.setVisible(true);
    }
}
