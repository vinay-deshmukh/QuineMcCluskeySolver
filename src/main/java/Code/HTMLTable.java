package Code;

import Table.BaseTable;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
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
