package Controller;

import Model.*;
import View.BookForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class BookFormController implements ActionListener {

    private static final String ERROR_BOOK_FORM_TITLE = "Error creating Book";

    private final BookForm _viewBookForm;
    private final BookDAO _bookDAO;
    private Book _bookModel;
    private DefaultTableModel _defaultTableBookModel;

    private String urlBookCover;

    private final FormValidator _formValidation;

    public BookFormController(BookForm viewBookForm) {

        _viewBookForm = viewBookForm;
        _bookDAO = new BookDAO();

        _viewBookForm.CreateB.addActionListener(this);
        _viewBookForm.CoverB.addActionListener(this);

        _formValidation = new FormValidator();
    }

    public void setDefaultListModel(DefaultTableModel defaultTableModel){

        _defaultTableBookModel = defaultTableModel;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _viewBookForm.CreateB) {
            if(_formValidation.isFieldNotEmpty(_viewBookForm.NameTF) & _formValidation.isFieldNotEmpty(_viewBookForm.AuthorTF) &
                    _formValidation.isFieldNotEmpty(_viewBookForm.PriceTF) & _formValidation.isFieldNotEmpty(_viewBookForm.PagesNumberTF) &
                    _formValidation.isFieldNotEmpty(_viewBookForm.PublicationDateDP.getEditor()) & _formValidation.isFieldNotEmpty(_viewBookForm.IsbnTF)
                    && _formValidation.isFieldNumericInteger(_viewBookForm.PagesNumberTF) & _formValidation.isFieldNumericReal(_viewBookForm.PriceTF)){

                createBookWithInputs();

                Vector<PurchasableItem> itemDataArray = new Vector<>(1);
                itemDataArray.add(_bookModel);
                _defaultTableBookModel.addRow(itemDataArray);

                _viewBookForm.dispose();
            }
            else{

                String errorMsg = _formValidation.getFinalErrorMsg();
                _formValidation.resetFinalErrorMsg();

                JOptionPane.showMessageDialog(null, errorMsg, ERROR_BOOK_FORM_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == _viewBookForm.CoverB) {

            urlBookCover = getUrlCoverSelected();
            loadCoverInLabel();
        }
    }

    private void createBookWithInputs() {

        int id = _bookDAO.getCurrentMaxId() + 1;

        String name = _viewBookForm.NameTF.getText();
        String price = _viewBookForm.PriceTF.getText();
        String author = _viewBookForm.AuthorTF.getText();

        Date releaseDate = _viewBookForm.PublicationDateDP.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String releaseDateFormatDDMMYYYY = formatter.format(releaseDate);

        int pagesNumber = Integer.parseInt(_viewBookForm.PagesNumberTF.getText());
        String ISBN = _viewBookForm.IsbnTF.getText();
        if(urlBookCover == null){

            urlBookCover = PurchasableItem.URL_IMAGE_NOT_AVAILABLE;
            loadCoverInLabel();
        }
        String cover = urlBookCover;

        _bookModel = new Book(id, name, price, cover, PurchasableItem.ItemState.Pending.toString(), author,
                pagesNumber, ISBN, releaseDateFormatDDMMYYYY);

        addBookToDB();
    }

    private void addBookToDB() {

        _bookDAO.insert(_bookModel);
    }

    private String getUrlCoverSelected() {

        String fileUrl;
        JFileChooser chooser = new JFileChooser();

        String[] imageSuffixes = ImageIO.getReaderFileSuffixes();
        for(String suffix : imageSuffixes){

            FileFilter filter = new FileNameExtensionFilter(suffix + " files", suffix);
            chooser.addChoosableFileFilter(filter);
        }

        int returnVal = chooser.showOpenDialog(_viewBookForm);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();
            fileUrl = selectedFile.getAbsolutePath();
        }
        else{

            fileUrl = urlBookCover;
        }
        return fileUrl;
    }

    private void loadCoverInLabel() {

        BufferedImage coverImage = null;
        try {

            coverImage = ImageIO.read(new File(urlBookCover));
        } catch (IOException e) {

            System.out.println("Error loading the cover on the label (BookForm): " + e);
        }

        if (coverImage != null) {

            _viewBookForm.CoverImage.setIcon(new ImageIcon(new ImageIcon(coverImage).getImage()
                    .getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
        }
    }
}
