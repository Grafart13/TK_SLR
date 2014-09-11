import heart.ParserArray;
import heart.Symbol;
import heart.symulator.ParseStep;
import heart.symulator.StackItem;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dpjar_000 on 2014-09-11.
 */
public class MyTableModel extends AbstractTableModel {

    private String[] columnNames;
    private String[][] data;
    private int column_size;
    private int data_size;
    private Map<String, Integer> columnMap;

    public MyTableModel (int _column_size, int _data_size) {
        column_size = _column_size;
        data_size = _data_size;
        columnNames = new String[column_size];
        data = new String[column_size][data_size];
        columnMap = new HashMap<String, Integer>();
    }
    public void setValueAt(String value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }


    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    public void setColumnNames(String [] _columnNames) {
        if (_columnNames.length != column_size)  {
            System.out.println("BLAD");
        }

        int i = 0;
        for (String columnName : _columnNames) {
            columnNames[i] = columnName;
            columnMap.put(columnName,i);
            i++;
        }
    }

    public void setData (ParserArray parserArray) {
        int i;
        for (i = 0; i < getRowCount(); i++) {
            data[0][i] = "T"+i;
        }

        for (i=0; i<getRowCount(); i++) {
            for (String symbol : columnMap.keySet()) {
                if (parserArray.getAction(i,symbol) != null) {
                    data[columnMap.get(symbol)][i] = parserArray.getAction(i, symbol).toString();
                }
            }
        }
    }

    public void setData( List<ParseStep> parseStepList) {

        int i=0;
        for (ParseStep ps : parseStepList) {
            String stack = "";
            String input = "";
            String prods = "";

            for (StackItem si : ps.getStack()) {
                stack += si.toString();
            }
            for (String in : ps.getInput()) {
                in += in;
            }
            for (String p : ps.getProds()) {
                prods += p;
            }

            data[0][i] = stack;
            data[1][i] = input;
            data[2][i] = prods;
            i++;
        }


    }

    @Override
    public int getRowCount() {
        return data_size;
    }

    @Override
    public int getColumnCount() {
        return column_size;
    }


    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        System.out.println(getColumnCount() + " : " + getRowCount() + "|" + columnIndex + " : " + rowIndex);

        if ((rowIndex < getRowCount()) && (columnIndex < getColumnCount())) {

            System.out.println("PLE");
            return data[columnIndex][rowIndex];
        } else {

            return null;
        }
    }
}
