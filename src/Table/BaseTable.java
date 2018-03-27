package Table;

public class BaseTable {

    private String header = null;
    // Title to be placed at the first row of the table
    // Initialized as null, since some tables don't need it

    private String [] columnTitles;
    // Column headings

    private String [][] nRows;
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
