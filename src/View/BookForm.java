package View;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

public class BookForm extends JFrame {

    public JPanel MainP;
    public JPanel BookDataP;
    public JTextField NameTF;
    public JTextField PriceTF;
    public JLabel NameL;
    public JLabel AuthorL;
    public JLabel PriceL;
    public JLabel PublicationDateL;
    public JLabel PagesNumberL;
    public JLabel IsbnL;
    public JTextField PagesNumberTF;
    public JTextField IsbnTF;
    public JXDatePicker PublicationDateDP;
    public JPanel CoverP;
    public JButton CoverB;
    public JLabel CoverImage;
    public JButton CreateB;
    public JTextField AuthorTF;

    public BookForm(String title) {

        super(title);

        NameTF.setName("Name");
        PriceTF.setName("Price");
        PagesNumberTF.setName("Pages Number");
        IsbnTF.setName("Isbn");
        AuthorTF.setName("Author");
        PublicationDateDP.getEditor().setName("Publication Date");
        PublicationDateDP.getEditor().setEditable(false);

        setColorJXDatePicker(PublicationDateDP);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(MainP);
        this.pack();
    }

    private void setColorJXDatePicker(JXDatePicker datePicker){

        datePicker.getEditor().setBackground(new Color(156, 143, 231));
        datePicker.getEditor().setForeground(new Color(255, 255, 255));
    }
}
