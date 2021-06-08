package View;

import Controller.GameFormController;

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
    public JPanel CoverImageP;
    public JPanel CoverP;
    public JPanel MainP;

    public GameForm(String title) {

        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }

    public static void main(String[] args) {

        GameForm gf = new GameForm("Home");
        GameFormController controller = new GameFormController(gf);

        gf.setVisible(true);
    }
}
