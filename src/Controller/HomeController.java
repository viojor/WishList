package Controller;

import MySQL.DBConnector;
import View.Home;
import View.ListItemsViewer;
import View.TableItemsViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private final Home _viewHome;

    public HomeController(Home viewHome){

        _viewHome = viewHome;

        _viewHome.BooksB.addActionListener(this);
        _viewHome.GamesB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String typeOfItemSelected;
        if(e.getSource() == _viewHome.BooksB){

            typeOfItemSelected = "BOOKS";
        }
        else{ //_viewHome.GamesB

            typeOfItemSelected = "GAMES";
        }

        TableItemsViewer tableItemsViewer = new TableItemsViewer("My Wish List");
        //ListItemsViewer listItemsViewer = new ListItemsViewer("List Data");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //listItemsViewer.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);
        tableItemsViewer.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        if(DBConnector.getConnection() == null){

            JOptionPane.showMessageDialog(null, "Cant connect with the database. Look if MySQL service is running",
                    "Cant connect with the database", JOptionPane.ERROR_MESSAGE);
        }
        /*ListItemsViewerController controller = new ListItemsViewerController(listItemsViewer, typeOfItemSelected);
        controller.loadTab();*/
        TableItemsViewerController controller = new TableItemsViewerController(tableItemsViewer, typeOfItemSelected);
        controller.loadTab();

        /*listItemsViewer.setLocationRelativeTo(null);
        listItemsViewer.setVisible(true);*/
        tableItemsViewer.setLocationRelativeTo(null);
        tableItemsViewer.setVisible(true);
    }
}
