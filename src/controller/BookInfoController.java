package controller;

import model.*;
import view.BookInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BookInfoController {

    private static final int WIDTH_COVER_ICON = 200;
    private static final int HEIGHT_COVER_ICON = 250;

    private final BookInfo _bookInfoView;
    private final Book _bookModel;

    public BookInfoController(BookInfo bookInfoView, int idBookToShow) {

        _bookInfoView = bookInfoView;
        BookDAO _bookDAO = new BookDAO();
        _bookModel = _bookDAO.getById(idBookToShow);

        setBookInfoIntoView();
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
                    .getScaledInstance(WIDTH_COVER_ICON, HEIGHT_COVER_ICON, Image.SCALE_SMOOTH)));
        }
    }
}
