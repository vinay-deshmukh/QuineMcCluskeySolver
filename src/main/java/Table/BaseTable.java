package Table;

public class BaseTable {

    private String header = null;
    // Title to be placed at the first row of the table
    // Initialized as null, since some tables don't need it

    private String[] columnTitles = null;
    // Column headings

    private String[][] nRows = null;
    // The content which is printed as table, in 2D array form
    // The second dimension, is same length as the length of column titles

    private String[] rowZero = null;
    // Sub column heading
    // For eg, we need "A B C D" as a sub ColumnTitle under "BinaryRepresentation"
    // Initialized as null, since only some tables (StepTable) will use it, and others don't need it

    public BaseTable() {
    }

    public BaseTable(String header, String[] columnTitles, String[][] nRows) {
        this.header = header;
        this.columnTitles = columnTitles;
        this.nRows = nRows;
    }

    private int noOfColumns(){
        int noOfColumns = 1;
        if (null != columnTitles)
            noOfColumns = columnTitles.length;
        else if (null != rowZero)
            noOfColumns = rowZero.length;
        else if (null != nRows)
            noOfColumns = nRows[0].length;
        return noOfColumns;
    }

    public String toHTMLString(){
        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");

        // Header
        if(null != header) {
            sb.append("\t<tr>\n");
            if(this instanceof PrimeImplicantTable) {
                int noOfMins = rowZero[rowZero.length - 1].trim().split("\\s+").length;
                sb.append(String.format("\t\t<th colspan=\"%d\">", noOfColumns() - 1 + noOfMins));
            }
            else
                sb.append(String.format("\t\t<th colspan=\"%d\">", noOfColumns()));

                    sb.append(String.format("%s", header));
                sb.append("</th>\n");
            sb.append("\t</tr>\n");
        }// Header

        // Column Titles
        if(null != columnTitles) {
            sb.append("\t<tr>\n");
            // Don't type the last columnTitles entry
            for (int ci =0; ci < columnTitles.length - 1 ; ci++) {
                sb.append("\t\t<th>");
                sb.append(String.format("%s", columnTitles[ci]));
                sb.append("</th>\n");
            }

            // For last entry
            // If PrimeImplicantTable,
            // colspan="no of numbers in minterms array"
            if(this instanceof PrimeImplicantTable){
                int noOfMins = rowZero[rowZero.length - 1].trim().split("\\s+").length;
                sb.append(String.format("\t\t<th colspan=\"%d\">", noOfMins));
                    sb.append(String.format("%s", columnTitles[columnTitles.length - 1]));
                sb.append("</th>\n");
            }
            else{
                // If not PrimeImplicantTable
                // Just write it normally
                sb.append("\t\t<th>");
                sb.append(String.format("%s", columnTitles[columnTitles.length - 1]));
                sb.append("</th>\n");
            }

            sb.append("\t</tr>\n");
        }// Column Titles

        // Row Zero
        if(null != rowZero){

            if(this instanceof PrimeImplicantTable){
                sb.append("\t<tr>\n");
                for(int ri=0; ri < rowZero.length - 1; ri++) {
                    sb.append("\t\t<th>");
                        sb.append(rowZero[ri]);
                    sb.append("</th>\n");
                }

                String [] sArr = rowZero[rowZero.length - 1].trim().split("\\s+");
                for(String s: sArr){
                    sb.append("\t\t<th>");
                        sb.append(String.format("%s", s));
                    sb.append("\t\t</th>\n");
                }


                sb.append("\t</tr>\n");
            }
            else {
                sb.append("\t<tr>\n");
                for (String s : rowZero) {
                    sb.append("\t\t<th>");
                    sb.append(String.format("%s", s));
                    sb.append("</th>\n");
                }
                sb.append("\t</tr>\n");
            }
        }// Row Zero

        // nRows
        if(null != nRows){
            for(String [] sArray: nRows){
                sb.append("\t<tr>\n");

                // Don't type the last nRows entry
                for (int ci =0; ci < sArray.length - 1 ; ci++) {
                    sb.append("\t\t<td>");
                        sb.append(String.format("%s", sArray[ci]));
                    sb.append("</td>\n");
                }

                // For last entry
                // If PrimeImplicantTable,
                // then split last entry by " " and use those as <td> elements
                if(this instanceof PrimeImplicantTable){

                    // We convert it to charArray after replacing each " " with a "",
                    // split didn't work as intended when there are multiple spaces present
                    char[] cArray = sArray[sArray.length - 1].replaceAll(" ", "").toCharArray();
                    for(char n: cArray){
                        sb.append("\t\t<td>");
                            sb.append(String.format("%c", n));
                        sb.append("</td>\n");
                    }

                }
                else{
                    // if this is not PrimeImplicantTable
                    // just append it like usual
                    sb.append("\t\t<td>");
                        sb.append(String.format("%s", sArray[sArray.length - 1]));
                    sb.append("</td>\n");
                }

                sb.append("\t</tr>\n");
            }
        }// nRows

        sb.append("</table>\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();

        int maxLenRow;      // Length of the longest row, will be found by using maxLenWord * noOfColumns
        int maxLenWord = 0; // Length of the longest word

        int noOfColumns = noOfColumns();


        if (null != nRows)
            for (String[] a : nRows) {
                for (String as : a) {
                    if (maxLenWord < as.length())
                        maxLenWord = as.length();
                }
            }


        if (null != rowZero)
            for (String s : rowZero) {
                if (maxLenWord < s.length())
                    maxLenWord = s.length();
            }

        if (null != columnTitles)
            for (String s : columnTitles) {
                if (maxLenWord < s.length())
                    maxLenWord = s.length();
            }

        if (null != header) {
            if (maxLenWord < header.length())
                maxLenWord = header.length();
        }

        maxLenRow = maxLenWord * noOfColumns;

        if (null != header) {
            // To center align the header string
            int blank = maxLenRow / 2 - header.length() / 2;
            int lenForHeaderFormat = blank + header.length();
            ans.append(String.format("%" + lenForHeaderFormat + "s\n", header));
        }

        String formatString = "%" + maxLenRow / noOfColumns + "s | ";

        if (null != columnTitles) {
            for (String s : columnTitles) {
                ans.append(String.format(formatString, s));
            }
            ans.append("\n");
        }
        if (null != rowZero) {
            for (String s : rowZero) {
                ans.append(String.format(formatString, s));
            }
            ans.append("\n");
        }
        if (null != nRows) {
            for (String a[] : nRows) {
                for (String s : a) {
                    ans.append(String.format(formatString, s));
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
