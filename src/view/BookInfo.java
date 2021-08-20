package view;

import javax.swing.*;

public class BookInfo extends JFrame {

    public JPanel mainP;
    public JPanel coverP;
    public JLabel coverImage;
    public JPanel bookDataP;
    public JLabel nameL;
    public JLabel nameValueL;
    public JLabel AuthorL;
    public JLabel authorValueL;
    public JLabel priceL;
    public JLabel priceValueL;
    public JLabel publicationDateL;
    public JLabel publicationDateValueL;
    public JLabel pagesNumberL;
    public JLabel pagesNumberValueL;
    public JLabel isbnL;
    public JLabel isbnValueL;

    public BookInfo(String title) {

        super(title);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(mainP);
        this.pack();
    }
}
