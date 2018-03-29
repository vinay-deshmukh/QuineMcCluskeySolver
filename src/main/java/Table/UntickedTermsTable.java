package Table;

public class UntickedTermsTable extends BaseTable{

    public UntickedTermsTable() {
        String [] a = {"Unticked Terms", "Prime Implicants"};
        // "Prime Implicants" refers to the column heading of
        // A B c D or A B C
        super.setColumnTitles(a);
    }
}
