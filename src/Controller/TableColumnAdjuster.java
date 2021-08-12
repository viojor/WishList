package Controller;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class TableColumnAdjuster implements PropertyChangeListener, TableModelListener {

    private final JTable table;
    private final int spacing;
    private boolean isColumnHeaderIncluded;
    private boolean isColumnDataIncluded;
    private boolean isOnlyAdjustLarger;
    private boolean isDynamicAdjustment;
    private final Map<TableColumn, Integer> columnSizes = new HashMap<>();

    public TableColumnAdjuster(JTable table) {

        this(table, 6);
    }

    public TableColumnAdjuster(JTable table, int spacing) {

        this.table = table;
        this.spacing = spacing;
        setColumnHeaderIncluded( true );
        setColumnDataIncluded( true );
        setOnlyAdjustLarger( false );
        setDynamicAdjustment( false );
        installActions();
    }

    public void adjustColumns() {

        TableColumnModel tcm = table.getColumnModel();

        for (int i = 0; i < tcm.getColumnCount(); i++) {

            adjustColumn(i);
        }
    }

    public void adjustColumn(final int column) {

        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        if (! tableColumn.getResizable()) return;

        int columnHeaderWidth = getColumnHeaderWidth( column );
        int columnDataWidth   = getColumnDataWidth( column );
        int preferredWidth	= Math.max(columnHeaderWidth, columnDataWidth);

        updateTableColumn(column, preferredWidth);
    }

    private int getColumnHeaderWidth(int column) {
        if (! isColumnHeaderIncluded) return 0;

        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        Object value = tableColumn.getHeaderValue();
        TableCellRenderer renderer = tableColumn.getHeaderRenderer();

        if (renderer == null) {

            renderer = table.getTableHeader().getDefaultRenderer();
        }

        Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);
        return c.getPreferredSize().width;
    }

    private int getColumnDataWidth(int column) {

        if (! isColumnDataIncluded) return 0;

        int preferredWidth = 0;
        int maxWidth = table.getColumnModel().getColumn(column).getMaxWidth();

        for (int row = 0; row < table.getRowCount(); row++) {

            preferredWidth = Math.max(preferredWidth, getCellDataWidth(row, column));

            //  We've exceeded the maximum width, no need to check other rows

            if (preferredWidth >= maxWidth)
                break;
        }
        return preferredWidth;
    }

    private int getCellDataWidth(int row, int column) {

        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
        Component c = table.prepareRenderer(cellRenderer, row, column);

        return c.getPreferredSize().width + table.getIntercellSpacing().width;
    }

    private void updateTableColumn(int column, int width) {

        final TableColumn tableColumn = table.getColumnModel().getColumn(column);

        if (! tableColumn.getResizable()) return;

        width += spacing;

        if (isOnlyAdjustLarger) {

            width = Math.max(width, tableColumn.getPreferredWidth());
        }

        columnSizes.put(tableColumn, tableColumn.getWidth());

        table.getTableHeader().setResizingColumn(tableColumn);
        tableColumn.setWidth(width);
    }

    public void restoreColumns() {

        TableColumnModel tcm = table.getColumnModel();

        for (int i = 0; i < tcm.getColumnCount(); i++) {

            restoreColumn(i);
        }
    }

    private void restoreColumn(int column) {

        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        Integer width = columnSizes.get(tableColumn);

        if (width != null) {

            table.getTableHeader().setResizingColumn(tableColumn);
            tableColumn.setWidth(width);
        }
    }

    public void setColumnHeaderIncluded(boolean isColumnHeaderIncluded) {

        this.isColumnHeaderIncluded = isColumnHeaderIncluded;
    }

    public void setColumnDataIncluded(boolean isColumnDataIncluded) {

        this.isColumnDataIncluded = isColumnDataIncluded;
    }

    public void setOnlyAdjustLarger(boolean isOnlyAdjustLarger) {

        this.isOnlyAdjustLarger = isOnlyAdjustLarger;
    }

    public void setDynamicAdjustment(boolean isDynamicAdjustment) {

        if (this.isDynamicAdjustment != isDynamicAdjustment) {
            if (isDynamicAdjustment) {

                table.addPropertyChangeListener( this );
                table.getModel().addTableModelListener( this );
            }
            else {

                table.removePropertyChangeListener( this );
                table.getModel().removeTableModelListener( this );
            }
        }
        this.isDynamicAdjustment = isDynamicAdjustment;
    }

    public void propertyChange(PropertyChangeEvent e) {

        if ("model".equals(e.getPropertyName())) {

            TableModel model = (TableModel)e.getOldValue();
            model.removeTableModelListener( this );

            model = (TableModel)e.getNewValue();
            model.addTableModelListener( this );
            adjustColumns();
        }
    }

    public void tableChanged(TableModelEvent e) {

        if (! isColumnDataIncluded) return;

        SwingUtilities.invokeLater(() -> {

            int column = table.convertColumnIndexToView(e.getColumn());

            if (e.getType() == TableModelEvent.UPDATE && column != -1) {
                if (isOnlyAdjustLarger) {

                    int	row = e.getFirstRow();
                    TableColumn tableColumn = table.getColumnModel().getColumn(column);

                    if (tableColumn.getResizable()) {

                        int width =	getCellDataWidth(row, column);
                        updateTableColumn(column, width);
                    }
                }
                else {

                    adjustColumn( column );
                }
            }
            else {

                adjustColumns();
            }
        });
    }

    private void installActions() {

        installColumnAction(true,  true,  "adjustColumn",   "control ADD");
        installColumnAction(false, true,  "adjustColumns",  "control shift ADD");
        installColumnAction(true,  false, "restoreColumn",  "control SUBTRACT");
        installColumnAction(false, false, "restoreColumns", "control shift SUBTRACT");

        installToggleAction(true,  false, "toggleDynamic",  "control MULTIPLY");
        installToggleAction(false, true,  "toggleLarger",   "control DIVIDE");
    }

    private void installColumnAction(boolean isSelectedColumn, boolean isAdjust, String key, String keyStroke) {

        Action action = new ColumnAction(isSelectedColumn, isAdjust);
        KeyStroke ks = KeyStroke.getKeyStroke( keyStroke );
        table.getInputMap().put(ks, key);
        table.getActionMap().put(key, action);
    }

    private void installToggleAction(boolean isToggleDynamic, boolean isToggleLarger, String key, String keyStroke) {

        Action action = new ToggleAction(isToggleDynamic, isToggleLarger);
        KeyStroke ks = KeyStroke.getKeyStroke( keyStroke );
        table.getInputMap().put(ks, key);
        table.getActionMap().put(key, action);
    }

    class ColumnAction extends AbstractAction {

        private final boolean isSelectedColumn;
        private final boolean isAdjust;

        public ColumnAction(boolean isSelectedColumn, boolean isAdjust) {

            this.isSelectedColumn = isSelectedColumn;
            this.isAdjust = isAdjust;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (isSelectedColumn) {

                int[] columns = table.getSelectedColumns();

                for (int column : columns) {
                    if (isAdjust)
                        adjustColumn(column);
                    else
                        restoreColumn(column);
                }
            }
            else {
                if (isAdjust)
                    adjustColumns();
                else
                    restoreColumns();
            }
        }
    }

    class ToggleAction extends AbstractAction {

        private final boolean isToggleDynamic;
        private final boolean isToggleLarger;

        public ToggleAction(boolean isToggleDynamic, boolean isToggleLarger) {

            this.isToggleDynamic = isToggleDynamic;
            this.isToggleLarger = isToggleLarger;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (isToggleDynamic) {

                setDynamicAdjustment(! isDynamicAdjustment);
                return;
            }

            if (isToggleLarger) {

                setOnlyAdjustLarger(! isOnlyAdjustLarger);
            }
        }
    }
}