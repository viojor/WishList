package View;

import javax.swing.*;

public class TableItemsViewer extends JFrame{

    public JTable tablePendingItems;
    public JPanel mainP;
    public JTabbedPane tabbedPanel;
    public JScrollPane pendingTab;
    public JScrollPane purchasedTab;
    public JButton addItemB;
    public JTable tablePurchasedItems;

    public TableItemsViewer(String title){

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }
}