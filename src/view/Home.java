package view;

import controller.HomeController;
import controller.LockFile;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame{

    private static final double WIDTH_SCREEN_CONST = 2.0/3.0;
    private static final double HEIGHT_SCREEN_CONST = 3.0/4.0;

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

        if(LockFile.lockInstance(LockFile.MAIN_LOCK_FILE_NAME)){

            Home home = new Home("Home");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            home.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

            new HomeController(home);

            home.setLocationRelativeTo(null);
            home.setVisible(true);
        }
        else{

            JOptionPane.showMessageDialog(null, "The app is already running, cant have more than one instance open",
                    "App already executing", JOptionPane.ERROR_MESSAGE);
        }
    }
}
