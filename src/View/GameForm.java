package View;

import javax.swing.*;

public class GameForm extends JFrame {

    public JTextField NameTF;
    public JTextField GenderTF;
    public JTextField PriceTF;
    public JTextField ReleaseDateTF;
    public JButton CoverB;
    public JButton CreateB;
    public JTextField EstimatedHoursTF;
    public JTextField TotalHoursTF;
    public JPanel GameDataP;
    public JLabel NameL;
    public JLabel GenderL;
    public JLabel PriceL;
    public JLabel ReleaseDateL;
    public JLabel EstimatedHoursL;
    public JLabel TotalHoursL;
    public JPanel CoverP;
    public JPanel MainP;
    public JLabel CoverImage;

    public GameForm(String title) {

        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }
}
