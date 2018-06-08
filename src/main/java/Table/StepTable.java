package Table;

public class StepTable extends BaseTable {

    public StepTable() {
        super();
        String[] a = {"Group", "Minterms", "Binary Representation", "Ticked"};
        super.setColumnTitles(a);
        String[] row0 = {"", "", "", ""};
        // Last element should contain
        // A or A B or A B C D
        // so it is initialized as empty
        super.setRowZero(row0);
    }
}
