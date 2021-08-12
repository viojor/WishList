package Controller;

import Model.PurchasableItem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableItemRenderer extends DefaultTableCellRenderer {

    public TableItemRenderer(){

        super();
    }

    public void setValue(Object value){

        PurchasableItem item = (PurchasableItem) value;
        ImageIcon cover = new ImageIcon(item.getCover());

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setText(item.getName());
        setIcon(new ImageIcon(cover.getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH)));
    }
}
