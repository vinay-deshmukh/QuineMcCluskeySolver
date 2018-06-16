package Code;

import Table.BaseTable;
import Table.PrimeImplicantTable;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HTMLTable {

    private String tagStyle;
    private String tagScript;

    HTMLTable(){

        try {
            tagStyle  = Jsoup.parse(
                                new File("src/main/java/Code/table.html"), null)
                                .select("head style").outerHtml();
            tagScript = Jsoup.parse(
                                new File("src/main/java/Code/table.html"), null)
                                .select("head script").outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getHTMLFile(List<BaseTable> listOfTablesToConvertToHTML) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <title>Page Title</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");

        // Insert CSS and JavaScript tags here
        sb.append(tagStyle);
        sb.append(tagScript);

        sb.append("</head>\n");
        // End Head

        // Begin Body
        sb.append("<body>\n");

        // Get max width of PrimeImplicantTable
        int maxWidth = 10;
        for(BaseTable bt: listOfTablesToConvertToHTML){
            if(bt instanceof PrimeImplicantTable){
                maxWidth = bt.getWideLengthGreaterThanPI_Table();
            }
        }

        // Add massive string here,
        // Which is wider than PrimeImplicantTable
        String nbsp = "F"; //using F since nbsp size is unreliable
        sb.append("<div id=\"blankLine\">");
            // create a string made up of n copies of string s
            String result = String.join("", Collections.nCopies((int)(maxWidth*1.40), nbsp));
            sb.append(result);
        sb.append("</div>\n");


        // Insert all tables here
        for (BaseTable bt : listOfTablesToConvertToHTML) {
            sb.append(bt.toHTMLString());

            // For spacing between tables
            sb.append("<br/>");
        }

        // End body
        sb.append("</body>\n");

        // End HTML
        sb.append("</html>\n");

        return sb.toString();
    }

    public void setTagStyle(String tagStyle) {
        // Setter method is to be used only if customisation is really needed.
        // This method expects the passed String to be syntactically and functionally correct
        this.tagStyle = tagStyle;
    }

    public void setTagScript(String tagScript) {
        // Setter method is to be used only if customisation is really needed.
        // This method expects the passed String to be syntactically and functionally correct
        this.tagScript = tagScript;
    }
}
