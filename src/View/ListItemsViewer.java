package View;

import javax.swing.*;

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
}
