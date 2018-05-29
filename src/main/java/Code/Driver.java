package Code;

import Table.StepTable;

import java.util.Set;

public class Driver {
    public static void main(String args[]) {

        QuineMcCluskey qm = new QuineMcCluskey();

        qm.doQuineMcCluskey("4 8 10 11 12 15","9 14");
        //System.out.println(res1);


        for(StepTable s: qm.getListStepTables()){
            System.out.println(s);
        }

        System.out.println(qm.getUntickedTermsTable());

        System.out.println(qm.getPrimeImplicantTable());

        System.out.println(qm.getNumbersWithOneXTable());

        System.out.println(qm.getEssentialPrimeImplicantTable());



    }
}
