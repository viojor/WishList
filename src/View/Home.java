package View;

import Controller.HomeController;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    public JTabbedPane tabbedPanel;
    public JPanel mainP;
    public JScrollPane pendingTab;
    public JScrollPane purchasedTab;
    public JButton addGameB;

    public Home(String title) {

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }

    public static void main(String[] args) {

        Home home = new Home("Home");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        home.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 2 / 3);
        
        HomeController controller = new HomeController(home);
        controller.loadTab();

        home.setVisible(true);
    }
}
