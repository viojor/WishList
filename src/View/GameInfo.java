package View;

import javax.swing.*;

public class GameInfo extends JFrame {

    public JLabel nameL;
    public JLabel nameValueL;
    public JLabel genreL;
    public JLabel genreValueL;
    public JLabel priceL;
    public JLabel priceValueL;
    public JLabel releaseDateL;
    public JLabel releaseDateValueL;
    public JLabel estimatedHoursL;
    public JLabel estimatedHoursValueL;
    public JLabel totalHoursL;
    public JLabel totalHoursValueL;
    public JPanel coverP;
    public JPanel gameDataP;
    public JPanel mainP;
    public JLabel coverImage;
    public JRadioButton editionNormalRB;
    public JRadioButton editionCollectorRB;
    public JLabel editionL;
    private JLabel editionNormalL;
    private JLabel editionCollectorL;

    public GameInfo(String title) {

        super(title);

        ButtonGroup editionButtons = new ButtonGroup();
        editionButtons.add(editionNormalRB);
        editionButtons.add(editionCollectorRB);
        editionNormalRB.setSelected(false);
        editionCollectorRB.setSelected(false);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }
}
