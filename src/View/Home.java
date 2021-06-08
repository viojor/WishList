package View;

import Controller.HomeController;

import javax.swing.*;

public class Home extends JFrame {

    public JTabbedPane tabbedPanel;
    public JPanel MainP;
    public JScrollPane pendingTab;
    public JScrollPane purchasedTab;

    public Home(String title) {

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }

    public static void main(String[] args) {

        Home home = new Home("Home");
        HomeController controller = new HomeController(home);

        home.setVisible(true);
    }
}
