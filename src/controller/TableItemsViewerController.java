package controller;

import model.BookDAO;
import model.GameDAO;
import model.PurchasableItem;
import model.PurchasableItemDAO;
import view.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public class TableItemsViewerController implements ChangeListener {

    private static final double WIDTH_SCREEN_CONST = 2.0/3.0;
    private static final double HEIGHT_SCREEN_CONST = 3.0/4.0;

    private static final String DELETE_ICON_ROUTE = "C:\\deleteIcon.png";
    private static final String PURCHASE_ICON_ROUTE = "C:\\purchaseIcon.jpg";

    private static final int ROWS_HEIGHT = 120;
    private static final int PURCHASE_COLUMN_INDEX = 1;
    private static final int DELETE_COLUMN_INDEX = 2;

    private static final String COLUMN_ITEMS_NAME = "Item";
    private static final String COLUMN_PURCHASE_ICON_NAME = "Purchase";
    private static final String COLUMN_DELETE_ICON_NAME = "Delete";

    private static final Object[] columns = { COLUMN_ITEMS_NAME, COLUMN_PURCHASE_ICON_NAME, COLUMN_DELETE_ICON_NAME };

    private static final String PENDING_TAB = "Pending";
    private static final String PURCHASED_TAB = "Purchased";

    private final TableItemsViewer _viewTableItemsViewer;
    private final PurchasableItemDAO<?> _itemDAO;

    private DefaultTableModel _defaultTableItemsModel;

    private final String _typeOfItemSelected;

    public TableItemsViewerController(TableItemsViewer viewTableItemsViewer, String typeItemsSelected) {

        _viewTableItemsViewer = viewTableItemsViewer;

        createTableModel();

        _typeOfItemSelected = typeItemsSelected;
        if(_typeOfItemSelected.equals(BookDAO.BOOKS_TABLE_NAME)){

            _itemDAO = new BookDAO();
            _viewTableItemsViewer.addItemB.addActionListener(e -> openBookForm());
        }
        else{ //GAMES

            _itemDAO = new GameDAO();
            _viewTableItemsViewer.addItemB.addActionListener(e -> openGameForm());
        }

        _viewTableItemsViewer.tabbedPanel.addChangeListener(this);

        addMouseListenerTable(_viewTableItemsViewer.tablePendingItems);
        addMouseListenerTable(_viewTableItemsViewer.tablePurchasedItems);
    }

    private void createTableModel(){

        _defaultTableItemsModel = new DefaultTableModel(columns, 0){

            public boolean isCellEditable(int rowIndex, int columnIndex) {

                return columnIndex == PURCHASE_COLUMN_INDEX || columnIndex == DELETE_COLUMN_INDEX;
            }
        };
    }

    private void addMouseListenerTable(JTable table){

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int rowSelected = table.getSelectedRow();
                if(table.columnAtPoint(e.getPoint()) == PURCHASE_COLUMN_INDEX && table.equals(_viewTableItemsViewer.tablePendingItems)){

                    String newPrice = (String) JOptionPane.showInputDialog(null,"Indicate purchase price",
                            "Purchase item", JOptionPane.PLAIN_MESSAGE, null, null, "0");

                    if(newPrice == null || newPrice.isEmpty()){ // Clicked close (x) or cancel button.

                        JOptionPane.showMessageDialog(null,"The input field cant be empty",
                                "Purchase item", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(Pattern.matches(Regex.WHITESPACE_REGEX, newPrice) || !Pattern.matches(Regex.ONLY_REAL_NUMBERS_REGEX,
                            newPrice)) {

                        JOptionPane.showMessageDialog(null, "Only numeric values are accepted (i.e: 22.45)",
                                "Purchase item", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(newPrice.length() > PurchasableItemDAO.STRINGS_MAX_SIZE){

                        JOptionPane.showMessageDialog(null, "The value cant have more than "
                                        + PurchasableItemDAO.STRINGS_MAX_SIZE + " characters","Purchase item", JOptionPane.ERROR_MESSAGE);
                    }
                    else{

                            String stateSelected = _viewTableItemsViewer.tabbedPanel.getTitleAt(_viewTableItemsViewer.tabbedPanel.getSelectedIndex());
                            PurchasableItem itemSelected = (PurchasableItem) _itemDAO.getByOrder(rowSelected, stateSelected);
                            itemSelected.setState(PurchasableItem.ItemState.Purchased.toString());

                            _itemDAO.updateState(itemSelected.getId());
                            _itemDAO.updatePriceWithId(newPrice, itemSelected.getId());


                            _defaultTableItemsModel.removeRow(rowSelected);
                    }
                }
                else if(table.columnAtPoint(e.getPoint()) == DELETE_COLUMN_INDEX){

                    int optionSelected = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the item?",
                            "Deleting data", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    usersDeleteOptionsHandler(optionSelected, rowSelected);
                }
                else{ // Item Data Column

                    openItemDataView(rowSelected);
                }
            }
        });
    }

    public void loadTab() {

        if (_viewTableItemsViewer.tabbedPanel.getSelectedIndex() != -1) {

            clearTableModel();
            loadDataInModel();
            configureTable(_viewTableItemsViewer.tablePendingItems);
            configureTable(_viewTableItemsViewer.tablePurchasedItems);
        }
    }

    private void clearTableModel(){

        if (_defaultTableItemsModel.getRowCount() > 0) {
            for (int i = _defaultTableItemsModel.getRowCount() - 1; i > -1; i--) {

                _defaultTableItemsModel.removeRow(i);
            }
        }
    }

    private void loadDataInModel(){

        List<PurchasableItem> itemsList = getItemsList();
        for (PurchasableItem item : itemsList) {

            Vector<PurchasableItem> itemDataArray = new Vector<>(1);
            itemDataArray.add(item);
            _defaultTableItemsModel.addRow(itemDataArray);
        }
    }

    private void configureTable(JTable table){

        table.setModel(_defaultTableItemsModel);
        table.setRowHeight(ROWS_HEIGHT);

        table.getColumn(COLUMN_ITEMS_NAME).setCellRenderer(new TableItemRenderer());
        // Can't show the purchase icon if the item has already been purchased
        if(table.equals(_viewTableItemsViewer.tablePendingItems)){

            table.getColumn(COLUMN_PURCHASE_ICON_NAME).setCellRenderer(new TableImageIconRenderer(PURCHASE_ICON_ROUTE));
        }
        table.getColumn(COLUMN_DELETE_ICON_NAME).setCellRenderer(new TableImageIconRenderer(DELETE_ICON_ROUTE));

        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        table.getTableHeader().setUI(null);
    }

    private void usersDeleteOptionsHandler(int optionSelected, int rowSelected){

        String stateSelected = _viewTableItemsViewer.tabbedPanel.getTitleAt(_viewTableItemsViewer.tabbedPanel.getSelectedIndex());
        PurchasableItem itemSelected = (PurchasableItem) _itemDAO.getByOrder(rowSelected, stateSelected);
        if (optionSelected == JOptionPane.YES_OPTION) {

            _defaultTableItemsModel.removeRow(rowSelected);
            _itemDAO.delete(itemSelected.getId());
        }
    }

    private List<PurchasableItem> getItemsList() {

        List<PurchasableItem> itemsList = new LinkedList<>();
        if (_viewTableItemsViewer.tabbedPanel.getTitleAt(_viewTableItemsViewer.tabbedPanel.getSelectedIndex()).equals(PENDING_TAB)) {

            itemsList = loadPendingItems();
        } else if (_viewTableItemsViewer.tabbedPanel.getTitleAt(_viewTableItemsViewer.tabbedPanel.getSelectedIndex()).equals(PURCHASED_TAB)) {

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

    private void openItemDataView(int rowSelected){

        String stateSelected = _viewTableItemsViewer.tabbedPanel.getTitleAt(_viewTableItemsViewer.tabbedPanel.getSelectedIndex());
        PurchasableItem itemSelected = (PurchasableItem) _itemDAO.getByOrder(rowSelected, stateSelected);
        if(_typeOfItemSelected.equals(BookDAO.BOOKS_TABLE_NAME)){

            openInfoBook(itemSelected.getId());
        }
        else { //GAMES

            openInfoGame(itemSelected.getId());
        }
    }

    public void stateChanged(ChangeEvent e) {

        loadTab();
    }

    private void openGameForm() {

        GameForm gf = new GameForm("Add Game");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gf.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

        GameFormController controller = new GameFormController(gf);
        controller.setDefaultListModel(_defaultTableItemsModel);

        gf.setLocationRelativeTo(null);
        gf.setVisible(true);
    }

    private void openBookForm() {

        BookForm bf = new BookForm("Add Book");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bf.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

        BookFormController controller = new BookFormController(bf);
        controller.setDefaultListModel(_defaultTableItemsModel);

        bf.setLocationRelativeTo(null);
        bf.setVisible(true);
    }

    private void openInfoGame(int idItemSelected){

        GameInfo gi = new GameInfo("Game Info");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gi.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

        new GameInfoController(gi, idItemSelected);

        gi.setLocationRelativeTo(null);
        gi.setVisible(true);
    }

    private void openInfoBook(int idItemSelected){

        BookInfo bi = new BookInfo("Book Info");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bi.setSize((int) (screenSize.getWidth() * WIDTH_SCREEN_CONST), (int) (screenSize.getHeight() * HEIGHT_SCREEN_CONST));

        new BookInfoController(bi, idItemSelected);

        bi.setLocationRelativeTo(null);
        bi.setVisible(true);
    }
}
