package controller;

import mySQL.DBConnector;
import view.Home;
import view.TableItemsViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private static final double WIDTH_SCREEN_CONST = 2.0/3.0;
    private static final double HEIGHT_SCREEN_CONST = 3.0/4.0;

    private static final String GAMES_TYPE = "GAMES";
    private static final String BOOKS_TYPE = "BOOKS";

    private final Home _viewHome;

    public HomeController(Home viewHome){

        _viewHome = viewHome;

        _viewHome.BooksB.addActionListener(this);
        _viewHome.GamesB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == _viewHome.BooksB){

            checkLockFileAndOpenTableViewer(BOOKS_TYPE);
        }
        else{ //_viewHome.GamesB

            checkLockFileAndOpenTableViewer(GAMES_TYPE);
        }
    }

    private void checkLockFileAndOpenTableViewer(String typeOfItemSelected){

        if(LockFile.lockInstance(typeOfItemSelected + LockFile.ITEM_LOCK_FILE_EXTENSION)){

            openTableItemsViewer(typeOfItemSelected);
        }
        else{

            JOptionPane.showMessageDialog(null, "Cant have more than a single table instance of the same item's type",
                    "Table already opened", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTableItemsViewer(String typeOfItemSelected){

        TableItemsViewer tableItemsViewer = new TableItemsViewer("My Wish List");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        tableItemsViewer.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

        if(DBConnector.getConnection() == null){

            JOptionPane.showMessageDialog(null, "Cant connect with the database. Look if MySQL service is running",
                    "Cant connect with the database", JOptionPane.ERROR_MESSAGE);
        }

        TableItemsViewerController controller = new TableItemsViewerController(tableItemsViewer, typeOfItemSelected);
        controller.loadTab();

        tableItemsViewer.setLocationRelativeTo(null);
        tableItemsViewer.setVisible(true);
    }
}
