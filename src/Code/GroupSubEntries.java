package Code;

import java.util.*;

public class GroupSubEntries {
    List<Integer> binaryRepresentation = new ArrayList<>();
    Set<Integer> minterms = new HashSet<>();

    static List<String> alphabets = new ArrayList<String>();
    static void initializeAlphabets(int maxBits)
    {
        alphabets.clear();
        char c ='A';

        // for loop determines how many alphabets to be used as column headers for binary representation
        // ie A B C D for 4 bit numbers
        //    A B C D E for 5 bit numbers
        for( int i =0;i<maxBits;i++,c++ )
        {
            alphabets.add( String.valueOf(c) );
        }
    }

    GroupSubEntries(List binaryRepresentation, Set minterms)
    {
        this.binaryRepresentation = binaryRepresentation;
        this.minterms = minterms;
    }



    static String correctString(List l1)
    {
        String result = "";
        for(int i = 0;i<l1.size(); i++, result+= " " )
        {

            if((l1.get(i) instanceof  Integer))
            {
                if((int)l1.get(i) == Step.DASH)
                    result+="-";
                else
                    result+=Integer.toString((int)l1.get(i));
            }
            else
                result+=l1.get(i).toString();
                    //result+= Integer.toString(l1.get(i));

        }
        result = result.substring(0, result.length() - 1);
        return  result;
    }

    static String correctString(Set <Integer> s1)
    {
        String result = "";
        for(Integer i : s1 )
        {
            result+= Integer.toString(i) + ", ";
        }
        result = result.substring(0, result.length() - 2);

        return  result;
    }


}
