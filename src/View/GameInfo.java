package View;

import javax.swing.*;

public class GameInfo extends JFrame {

    public JLabel nameL;
    public JLabel nameValueL;
    public JLabel genderL;
    public JLabel genderValueL;
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
    public JButton purchasedB;

    public GameInfo(String title) {

        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }
}
