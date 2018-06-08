package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupSubEntries {
    static List<String> alphabets = new ArrayList<>();
    List<Integer> binaryRepresentation;
    Set<Integer> minterms;
    boolean ticked;

    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }

    @Override
    public String toString() {
        return "GroupSubEntries{" +
                "binaryRepresentation=" + binaryRepresentation +
                ", minterms=" + minterms +
                ", ticked=" + ticked +
                '}';
    }

    GroupSubEntries(List<Integer> binaryRepresentation, Set<Integer> minterms) {
        this.binaryRepresentation = binaryRepresentation;
        this.minterms = minterms;
        this.ticked = false;
    }

    static void initializeAlphabets(int maxBits) {
        alphabets.clear();
        char c = 'A';

        // for loop determines how many alphabets to be used as column headers for binary representation
        // ie A B C D for 4 bit numbers
        //    A B C D E for 5 bit numbers
        for (int i = 0; i < maxBits; i++, c++) {
            alphabets.add(String.valueOf(c));
        }
    }

    static String correctString(List l1) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < l1.size(); i++, result.append(" ")) {

            if ((l1.get(i) instanceof Integer)) {
                if ((int) l1.get(i) == Step.DASH)
                    result.append("-");
                else
                    result.append(Integer.toString((int) l1.get(i)));
            } else
                result.append(l1.get(i).toString());
            //result+= Integer.toString(l1.get(i));

        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }

    static String correctString(Set<Integer> s1) {
        StringBuilder result = new StringBuilder();
        for (Integer i : s1) {
            result.append(Integer.toString(i)).append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));

        return result.toString();
    }


}
