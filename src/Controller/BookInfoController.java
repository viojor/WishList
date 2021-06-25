package Controller;

import Model.*;
import View.BookInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BookInfoController implements ActionListener {

    private final BookInfo _bookInfoView;
    private final BookDAO _bookDAO;
    private final Book _bookModel;
    private DefaultListModel<PurchasableItem> _defaultListModel;

    public BookInfoController(BookInfo bookInfoView, int idBookToShow) {

        _bookInfoView = bookInfoView;
        _bookDAO = new BookDAO();
        _bookModel = _bookDAO.getById(idBookToShow);

        _bookInfoView.purchasedB.addActionListener(this);

        setBookInfoIntoView();
        setPurchasedButtonVisibility();
    }

    public void setDefaultListModel(DefaultListModel<PurchasableItem> defaultListModel){

        _defaultListModel = defaultListModel;
    }

    private void setBookInfoIntoView() {

        loadCoverInLabel();
        _bookInfoView.nameValueL.setText(_bookModel.getName());
        _bookInfoView.authorValueL.setText(_bookModel.getAuthor());
        _bookInfoView.priceValueL.setText(_bookModel.getPrice());
        _bookInfoView.publicationDateValueL.setText(_bookModel.getPublicationDate());
        _bookInfoView.pagesNumberValueL.setText(String.valueOf(_bookModel.getPagesNumber()));
        _bookInfoView.isbnValueL.setText(_bookModel.getISBN());
    }

    private void loadCoverInLabel() {

        BufferedImage coverImage = null;
        try {

            coverImage = ImageIO.read(new File(_bookModel.getCover()));
        } catch (IOException e) {

            System.out.println("Error loading the cover on the label (BookInfo): " + e);
        }

        if (coverImage != null) {

            _bookInfoView.coverImage.setIcon(new ImageIcon(new ImageIcon(coverImage).getImage()
                    .getScaledInstance(200, 250, Image.SCALE_SMOOTH)));
        }
    }

    private void setPurchasedButtonVisibility() {

        if (_bookModel.getState().equals(PurchasableItem.ItemState.Purchased.toString())) {

            _bookInfoView.purchasedB.setVisible(false);
            _bookInfoView.purchasedB.setEnabled(false);
        } else { //PurchasableItem.ItemState.Pending

            _bookInfoView.purchasedB.setVisible(true);
            _bookInfoView.purchasedB.setEnabled(true);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == _bookInfoView.purchasedB) {

            int defaultListModelElementIndex = _defaultListModel.indexOf(_bookModel);

            _bookModel.setState(PurchasableItem.ItemState.Purchased.toString());
            _bookDAO.updateState(_bookModel.getId());

            //Remove the element from the list cause we are changing the attribute we use to get them (moved to other tab)
            _defaultListModel.remove(defaultListModelElementIndex);

            _bookInfoView.dispose();
        }
    }
}
