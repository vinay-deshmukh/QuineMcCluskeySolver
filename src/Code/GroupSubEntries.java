package Code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupSubEntries {
    List<Integer> binaryRepresentation = new ArrayList<>();
    Set<Integer> minterms = new HashSet<>();

    GroupSubEntries(){}
    GroupSubEntries(List binaryRepresentation, Set minterms)
    {
        this.binaryRepresentation = binaryRepresentation;
        this.minterms = minterms;
    }

    GroupSubEntries(GroupSubEntries gs)
    {
        this.minterms = gs.minterms;
        this.binaryRepresentation = gs.binaryRepresentation;
    }

    void displayGroupSubEntries()
    {
        System.out.println(binaryRepresentation + "; " + minterms);
    }
}
