package View;

import Controller.ListItemsViewerController;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame{

    public JPanel MainP;
    public JPanel ItemsP;
    public JButton BooksB;
    public JButton GamesB;

    public Home(String title){

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }

    public static void main(String[] args) {

        Home home = new Home("Home");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        home.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 2 / 3);

        /*ListItemsViewerController controller = new ListItemsViewerController(listItemsViewer);
        controller.loadTab();*/

        home.setVisible(true);
    }
}
