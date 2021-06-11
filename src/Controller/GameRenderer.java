package Controller;

import Model.Game;

import javax.swing.*;
import java.awt.*;

public class GameRenderer extends JLabel implements ListCellRenderer<Game> {

    public Component getListCellRendererComponent(JList<? extends Game> list, Game game, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        ImageIcon coverIcon = new ImageIcon(new ImageIcon(game.get_cover()).getImage()
                .getScaledInstance(80, 100, Image.SCALE_DEFAULT));
        String name = game.get_name();

        setIcon(coverIcon);
        setText(name);

        return this;
    }
}
