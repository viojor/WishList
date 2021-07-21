package Controller;

import View.Home;
import View.ListItemsViewer;

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

        ListItemsViewer listItemsViewer = new ListItemsViewer("List Data");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        listItemsViewer.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        ListItemsViewerController controller = new ListItemsViewerController(listItemsViewer, typeOfItemSelected);
        controller.loadTab();

        listItemsViewer.setLocationRelativeTo(null);
        listItemsViewer.setVisible(true);
    }
}
