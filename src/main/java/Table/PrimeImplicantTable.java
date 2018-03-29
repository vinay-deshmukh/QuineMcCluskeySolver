package Table;

public class PrimeImplicantTable extends BaseTable {

    public PrimeImplicantTable() {
        String [] a = {"Prime Implicants", "Decimal Minterms", "Given Minterms"};

        String [] row0 = {"", "", ""};
        // The last element should hold the minterms
        // ie 1 4 6 12 15
        // This will be given during run time, so it's initialised as empty

        super.setColumnTitles(a);
        super.setRowZero(row0);
    }
}
