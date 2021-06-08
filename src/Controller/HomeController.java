package Controller;

import Model.Game;
import Model.GameDAO;
import View.Home;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.LinkedList;
import java.util.List;

public class HomeController implements ChangeListener {

    private static final String PENDING_TAB = "Pending";
    private static final String PURCHASED_TAB = "Purchased";

    private Home _viewHome;
    private GameDAO _gameDAO;

    public HomeController(Home viewHome) {

        _viewHome = viewHome;
        _gameDAO = new GameDAO();

        _viewHome.tabbedPanel.addChangeListener(this);
        
        if (_viewHome.tabbedPanel.getSelectedIndex() != -1) {

            loadTab();
        }
    }

    private void loadTab() {

        DefaultListModel<String> gamesDefaultList = new DefaultListModel<>();
        List<Game> gameList = getGamesList();
        for (Game game : gameList) {

            gamesDefaultList.addElement(game.get_name());
        }

        JList<String> gameJList = new JList<>(gamesDefaultList);
        JScrollPane jsp = (JScrollPane) _viewHome.tabbedPanel.getSelectedComponent();
        jsp.setViewportView(gameJList);
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

        return _gameDAO.getPendingGames();
    }

    private List<Game> loadPurchasedGames() {

        return _gameDAO.getPurchasedGames();
    }

    public void stateChanged(ChangeEvent e) {

        loadTab();
    }
}
