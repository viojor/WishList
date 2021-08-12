package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

public class ListItemsViewerController implements ChangeListener {

    private static final String PENDING_TAB = "Pending";
    private static final String PURCHASED_TAB = "Purchased";

    private final ListItemsViewer _viewListItemsViewer;
    private final PurchasableItemDAO<?> _itemDAO;

    private final DefaultListModel<PurchasableItem> _defaultListItemsModel;

    private final String _typeOfItemSelected;

    public ListItemsViewerController(ListItemsViewer viewListItemsViewer, String nameTableItemsSelected) {

        _viewListItemsViewer = viewListItemsViewer;
        _defaultListItemsModel = new DefaultListModel<>();
        _typeOfItemSelected = nameTableItemsSelected;
        if(_typeOfItemSelected.equals(BookDAO.BOOKS_TABLE_NAME)){

            _itemDAO = new BookDAO();
            _viewListItemsViewer.addItemB.addActionListener(e -> openBookForm());
        }
        else{ //GAMES

            _itemDAO = new GameDAO();
            _viewListItemsViewer.addItemB.addActionListener(e -> openGameForm());
        }

        _viewListItemsViewer.tabbedPanel.addChangeListener(this);
    }

    public void loadTab() {

        /*if (_viewListItemsViewer.tabbedPanel.getSelectedIndex() != -1) {

            _defaultListItemsModel.clear();
            List<PurchasableItem> itemsList = getItemsList();
            for (PurchasableItem item : itemsList) {

                _defaultListItemsModel.addElement(item);
            }

            JList<PurchasableItem> itemsJList = new JList<>(_defaultListItemsModel);
            itemsJList.setCellRenderer(new TableItemRenderer());
            itemsJList.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if(_typeOfItemSelected.equals(BookDAO.BOOKS_TABLE_NAME)){

                        openInfoBook(itemsJList.getSelectedValue().getId());
                    }
                    else { //GAMES

                        openInfoGame(itemsJList.getSelectedValue().getId());
                    }
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });

            itemsJList.setBackground(new Color(156, 143, 231));
            JScrollPane jsp = (JScrollPane) _viewListItemsViewer.tabbedPanel.getSelectedComponent();
            jsp.setViewportView(itemsJList);
        }*/
    }

    private List<PurchasableItem> getItemsList() {

        List<PurchasableItem> itemsList = new LinkedList<>();
        if (_viewListItemsViewer.tabbedPanel.getTitleAt(_viewListItemsViewer.tabbedPanel.getSelectedIndex()).equals(PENDING_TAB)) {

            itemsList = loadPendingItems();
        } else if (_viewListItemsViewer.tabbedPanel.getTitleAt(_viewListItemsViewer.tabbedPanel.getSelectedIndex()).equals(PURCHASED_TAB)) {

            itemsList = loadPurchasedItems();
        }
        return itemsList;
    }

    private List<PurchasableItem> loadPendingItems() {

        return _itemDAO.getByState(PurchasableItem.ItemState.Pending.toString());
    }

    private List<PurchasableItem> loadPurchasedItems() {

        return _itemDAO.getByState(PurchasableItem.ItemState.Purchased.toString());
    }

    public void stateChanged(ChangeEvent e) {

        loadTab();
    }

    private void openGameForm() {

        GameForm gf = new GameForm("Add Game");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gf.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        GameFormController controller = new GameFormController(gf);
        //controller.setDefaultListModel(_defaultListItemsModel);

        gf.setLocationRelativeTo(null);
        gf.setVisible(true);
    }

    private void openBookForm() {

        BookForm bf = new BookForm("Add Book");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bf.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        BookFormController controller = new BookFormController(bf);
        //controller.setDefaultListModel(_defaultListItemsModel);

        bf.setLocationRelativeTo(null);
        bf.setVisible(true);
    }

    private void openInfoGame(int idItemSelected){

        GameInfo gi = new GameInfo("Game Info");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gi.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        GameInfoController gameInfoController = new GameInfoController(gi, idItemSelected);
        //gameInfoController.setDefaultListModel(_defaultListItemsModel);

        gi.setLocationRelativeTo(null);
        gi.setVisible(true);
    }

    private void openInfoBook(int idItemSelected){

        BookInfo bi = new BookInfo("Book Info");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bi.setSize((int) screenSize.getWidth() * 2 / 3, (int) screenSize.getHeight() * 3 / 4);

        BookInfoController bookInfoController = new BookInfoController(bi, idItemSelected);
        //bookInfoController.setDefaultListModel(_defaultListItemsModel);

        bi.setLocationRelativeTo(null);
        bi.setVisible(true);
    }
}
