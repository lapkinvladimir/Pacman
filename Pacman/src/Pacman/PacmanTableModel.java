package Pacman;

import javax.swing.table.AbstractTableModel;


public class PacmanTableModel extends AbstractTableModel {
    private static final int FIELD_ROWS = 20;
    private static final int FIELD_COLUMNS = 20;

    private static Object[][] data;

    public PacmanTableModel() {
        this.data = new Object[FIELD_ROWS][FIELD_COLUMNS];
    }

    public Object[][] getData() {
        return data;
    }

    @Override
    public int getRowCount() {
        return FIELD_ROWS;
    }

    @Override
    public int getColumnCount() {
        return FIELD_COLUMNS;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


}



