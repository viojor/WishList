package controller;

import model.PurchasableItem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableItemRenderer extends DefaultTableCellRenderer {

    private static final int WIDTH_ITEM_ICON = 80;
    private static final int HEIGHT_ITEM_ICON = 100;

    private static final int TOP_BORDER = 20;
    private static final int LEFT_BORDER = 20;
    private static final int BOTTOM_BORDER = 20;
    private static final int RIGHT_BORDER = 20;

    public TableItemRenderer(){

        super();
    }

    public void setValue(Object value){

        PurchasableItem item = (PurchasableItem) value;
        ImageIcon cover = new ImageIcon(item.getCover());

        setBorder(BorderFactory.createEmptyBorder(TOP_BORDER, LEFT_BORDER, BOTTOM_BORDER, RIGHT_BORDER));
        setText(item.getName());
        setIcon(new ImageIcon(cover.getImage().getScaledInstance(WIDTH_ITEM_ICON, HEIGHT_ITEM_ICON, Image.SCALE_SMOOTH)));
    }
}
