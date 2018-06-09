package Code;

import Table.BaseTable;

import java.util.List;

public class HTMLTable {

    // All HTML Strings are copied from table.html file

    //region String tagStyle
    private static String tagStyle = "<style>\n" +
            "        table, th, td {\n" +
            "            width: fit-content;\n" +
            "            border: 1px solid black;\n" +
            "            /* this 1 px combines to form 2px\n" +
            "            since border-spacing is 0px\n" +
            "            and hence sepBorderWidth is set to 4px ie double 2px */\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        table {\n" +
            "            /* border-collapse: collapse; */\n" +
            "            border-spacing: 0px;\n" +
            "        }\n" +
            "        th, td {\n" +
            "            padding: 0px 10px 0px 10px;\n" +
            "            /* top right bottom left */\n" +
            "        }\n" +
            "    </style>";
    //endregion

    //region String tagScript
    private static String tagScript = "<script  type=\"text/javascript\">\n" +
            "    window.onload = function(){\n" +
            "        var sepBorderWidth = \"4px\";\n" +
            "\n" +
            "        var allTables = document.getElementsByTagName(\"table\");\n" +
            "        for(var i=0; i < allTables.length; i++){\n" +
            "            var table = allTables[i];\n" +
            "            var header = table.getElementsByTagName(\"tr\")[0].getElementsByTagName(\"th\")[0];\n" +
            "\n" +
            "            if(!header.innerHTML.startsWith(\"Step\")){\n" +
            "                console.log(\"Not step table: Step\" + i)\n" +
            "                continue;\n" +
            "            }\n" +
            "            // If it's a step table\n" +
            "\n" +
            "            var listOfTR = table.getElementsByTagName(\"tr\");\n" +
            "            // start with 3 since\n" +
            "            // 0 is table header\n" +
            "            // 1 is column titles\n" +
            "            // 2 is row zero\n" +
            "\n" +
            "            // get bold line between row zero and nRows start\n" +
            "            var row0 = listOfTR[2].getElementsByTagName(\"td\");\n" +
            "            var nRow0 = listOfTR[3].getElementsByTagName(\"td\");\n" +
            "            for(var ri=0; ri<row0.length; ri++){\n" +
            "                row0[ri].style.borderBottomWidth = \"0px\";\n" +
            "                nRow0[ri].style.borderTopWidth = sepBorderWidth;\n" +
            "            }\n" +
            "\n" +
            "            for(var tri=3; tri < listOfTR.length - 1 ; tri++){\n" +
            "                \n" +
            "                var rowN = listOfTR[tri].getElementsByTagName(\"td\");\n" +
            "                var rowN1 = listOfTR[tri+1].getElementsByTagName(\"td\");\n" +
            "                \n" +
            "                // if value of first td of rowN and rowN1 is same\n" +
            "                // make borderBottomWidth of rowN to be 1px\n" +
            "                if(rowN[0].innerHTML == rowN1[0].innerHTML){\n" +
            "                    // if values are not same\n" +
            "                    // then don't do anything\n" +
            "                    console.log(rowN[0].innerHTML+'\\n');\n" +
            "                    continue;\n" +
            "                }    \n" +
            "\n" +
            "                for(var ci=0; ci < rowN.length; ci++){\n" +
            "                    rowN[ci].style.borderBottomWidth = sepBorderWidth;\n" +
            "                    // rowN[ci].style.borderBottomColor = \"blue\";\n" +
            "\n" +
            "                    // rowN1[ci].style.borderTopColor = \"\";\n" +
            "                    rowN1[ci].style.borderTopWidth = \"0px\";\n" +
            "                }\n" +
            "                \n" +
            "                \n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "    </script> ";
    //endregion

    public static String getHTMLFile(List<BaseTable> listOfTablesToConvertToHTML) {
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

    public static void setTagStyle(String tagStyle) {
        // Setter method is to be used only if customisation is really needed.
        // This method expects the passed String to be syntactically and functionally correct
        HTMLTable.tagStyle = tagStyle;
    }

    public static void setTagScript(String tagScript) {
        // Setter method is to be used only if customisation is really needed.
        // This method expects the passed String to be syntactically and functionally correct
        HTMLTable.tagScript = tagScript;
    }
}
