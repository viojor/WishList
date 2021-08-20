package view;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

public class GameForm extends JFrame {

    public JTextField NameTF;
    public JTextField PriceTF;
    public JButton CoverB;
    public JButton CreateB;
    public JTextField EstimatedHoursTF;
    public JTextField TotalHoursTF;
    public JPanel GameDataP;
    public JLabel NameL;
    public JLabel GenreL;
    public JLabel PriceL;
    public JLabel ReleaseDateL;
    public JLabel EstimatedHoursL;
    public JLabel TotalHoursL;
    public JPanel CoverP;
    public JPanel MainP;
    public JLabel CoverImage;
    public JComboBox<String> GenreCB;
    public JXDatePicker ReleaseDateDP;
    public JRadioButton Edition_NormalRB;
    public JRadioButton Edition_CollectorRB;
    public JLabel EditionL;

    public GameForm(String title) {

        super(title);

        NameTF.setName("Name");
        PriceTF.setName("Price");
        EstimatedHoursTF.setName("Estimated Hours");
        TotalHoursTF.setName("Total Hours");
        GenreCB.getEditor().getEditorComponent().setName("Genre");
        ReleaseDateDP.getEditor().setName("Release Date");
        Edition_NormalRB.setName("Normal Edition");
        Edition_CollectorRB.setName("Collector Edition");

        ReleaseDateDP.getEditor().setEditable(false);

        ButtonGroup editionButtons = new ButtonGroup();
        editionButtons.add(Edition_NormalRB);
        editionButtons.add(Edition_CollectorRB);
        Edition_NormalRB.setSelected(true);
        Edition_CollectorRB.setSelected(false);

        setColorJXDatePicker(ReleaseDateDP);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }

    private void setColorJXDatePicker(JXDatePicker datePicker){

        datePicker.getEditor().setBackground(new Color(156, 143, 231));
        datePicker.getEditor().setForeground(new Color(255, 255, 255));
    }
}
