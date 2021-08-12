package Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TableImageIconRenderer extends DefaultTableCellRenderer {

    private ImageIcon _imageIcon;
    private final String _iconRoute;

    public TableImageIconRenderer(String iconRoute){

        super();

        _iconRoute = iconRoute;
    }

    public void setValue(Object value){

        if(_imageIcon == null){

            _imageIcon = new ImageIcon(new ImageIcon(_iconRoute).getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        }
        setHorizontalAlignment(CENTER);
        setIcon(_imageIcon);
    }
}
