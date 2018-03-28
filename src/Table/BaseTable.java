package Table;

import java.util.Arrays;

public class BaseTable {

    private String header = null;
    // Title to be placed at the first row of the table
    // Initialized as null, since some tables don't need it

    private String [] columnTitles = null;
    // Column headings

    private String [][] nRows = null;
    // The content which is printed as table, in 2D array form
    // The second dimension, is same length as the length of column titles

    private String [] rowZero = null;
    // Sub column heading
    // For eg, we need "A B C D" as a sub ColumnTitle under "BinaryRepresentation"
    // Initialized as null, since only some tables (StepTable) will use it, and others don't need it

    public BaseTable(){}

    public BaseTable(String header, String[] columnTitles, String[][] nRows) {
        this.header = header;
        this.columnTitles = columnTitles;
        this.nRows = nRows;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        //TODO Change %15s, %10s to a calculated value
        // ie find max length of characters for each column, add that up, and then center format
        // the header

        if(null != header)
            ans.append(String.format("%10s\n", header));
        if(null != columnTitles){
            for(String s: columnTitles){
                ans.append(String.format("%10s ", s));
            }
            ans.append("\n");
        }
        if(null != rowZero){
            for(String s: rowZero){
                ans.append(String.format("%10s ", s));
            }
            ans.append("\n");
        }
        if(null != nRows){
            for(String a[]: nRows){

                for(String s: a){
                    ans.append(String.format("%10s ", s));
                }
                ans.append("\n");
            }
            ans.append("\n");
        }


        return ans.toString();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String[] getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(String[] columnTitles) {
        this.columnTitles = columnTitles;
    }

    public String[][] getnRows() {
        return nRows;
    }

    public void setnRows(String[][] nRows) {
        this.nRows = nRows;
    }

    public String[] getRowZero() {
        return rowZero;
    }

    public void setRowZero(String[] rowZero) {
        this.rowZero = rowZero;
    }

}
