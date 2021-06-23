package Controller;

import Model.PurchasableItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ItemRenderer extends JLabel implements ListCellRenderer<PurchasableItem> {

    public Component getListCellRendererComponent(JList<? extends PurchasableItem> list, PurchasableItem item, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        ImageIcon coverIcon = new ImageIcon(new ImageIcon(item.getCover()).getImage()
                .getScaledInstance(120, 150, Image.SCALE_SMOOTH));
        String name = item.toString();

        setIcon(coverIcon);
        setText(name);
        setFont(new Font(name, Font.PLAIN, 20));
        setForeground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 10, 5, 0));

        return this;
    }
}
