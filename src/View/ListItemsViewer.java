package View;

import Controller.ListItemsViewerController;

import javax.swing.*;
import java.awt.*;

public class ListItemsViewer extends JFrame {

    public JTabbedPane tabbedPanel;
    public JPanel mainP;
    public JScrollPane pendingTab;
    public JScrollPane purchasedTab;
    public JButton addItemB;

    public ListItemsViewer(String title) {

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }

    /*public static void main(String[] args) {

        ListItemsViewer listItemsViewer = new ListItemsViewer("List Data");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        listItemsViewer.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 2 / 3);
        
        ListItemsViewerController controller = new ListItemsViewerController(listItemsViewer);
        controller.loadTab();

        listItemsViewer.setVisible(true);
    }*/
}
