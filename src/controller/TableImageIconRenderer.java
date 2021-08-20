package controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableImageIconRenderer extends DefaultTableCellRenderer {

    private static final int WIDTH_CELL_ICON = 50;
    private static final int HEIGHT_CELL_ICON = 50;

    private ImageIcon _imageIcon;
    private final String _iconRoute;

    public TableImageIconRenderer(String iconRoute){

        super();

        _iconRoute = iconRoute;
    }

    public void setValue(Object value){

        if(_imageIcon == null){

            _imageIcon = new ImageIcon(new ImageIcon(_iconRoute).getImage()
                    .getScaledInstance(WIDTH_CELL_ICON, HEIGHT_CELL_ICON, Image.SCALE_SMOOTH));
        }
        setHorizontalAlignment(CENTER);
        setIcon(_imageIcon);
    }
}
