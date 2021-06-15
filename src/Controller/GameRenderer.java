package Controller;

import Model.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameRenderer extends JLabel implements ListCellRenderer<Game> {

    public Component getListCellRendererComponent(JList<? extends Game> list, Game game, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        ImageIcon coverIcon = new ImageIcon(new ImageIcon(game.get_cover()).getImage()
                .getScaledInstance(80, 100, Image.SCALE_DEFAULT));
        String name = game.get_name();

        setIcon(coverIcon);
        setText(name);
        setForeground(new Color(255, 255, 255));
        setBorder(new EmptyBorder(5, 10, 5, 0));

        return this;
    }
}
