package Code;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PI_Table {

    static HashMap<Integer, Integer> noOfXHashMap = new HashMap<>();

    //misc
    static String binaryRepToPIForm(List<Integer> l1)
    {
        String result = "";
        for(int i =0; i<l1.size(); i++)
        {
            if(l1.get(i) == Step.DASH)
            {
                continue;
            }
            else
            {
                result+=" ";
                if(l1.get(i) == 1)
                    result+=" "+ GroupSubEntries.alphabets.get(i);
                else
                    result+="~"+ GroupSubEntries.alphabets.get(i);
            }
        }

        return result;
    }

    //misc
    static void CreateSingleRowInPITable(Set<Integer> s, int [] numbers, List<Integer> dontCare)
    {
        String result="";

        //Result of first column
        List<Integer> binaryRepOfMinterm = Step.primeImplicantHashMap.get(s);
        String PIalphabentForm = binaryRepToPIForm(binaryRepOfMinterm);

        //Result of second column
        String decimalNumbers = GroupSubEntries.correctString(s);

        //Result of third column
        String Xformat = "";

        for(int i =0; i<numbers.length; i++)
        {

            int digits = 1;
            int n = numbers[i];
            while(n>9)
            {
                n=n/10;
                digits++;
            }

            //Find if extra space needed before X
            for(int j =0; j<digits-1; j++)
                Xformat+=" ";

            if(!dontCare.contains(numbers[i]))
            {
                Xformat+=" ";
                Integer prevNoOfX = PI_Table.noOfXHashMap.get(numbers[i]);
                if (s.contains(numbers[i]))
                {
                    Xformat += "X";
                    PI_Table.noOfXHashMap.put(numbers[i], new Integer(prevNoOfX+1));
                }
                else
                    Xformat += " ";
            }

        }


        System.out.format("%16s | %16s | %20s\n",
                PIalphabentForm,
                decimalNumbers,
                Xformat);

        //return result;
    }
}
