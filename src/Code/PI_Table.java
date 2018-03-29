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

}
