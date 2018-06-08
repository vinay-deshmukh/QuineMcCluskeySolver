package Code;

import Table.StepTable;

import java.util.Set;

public class Driver {
    public static void main(String args[]) {

        QuineMcCluskey qm = new QuineMcCluskey();

        qm.doQuineMcCluskey("4 8 10 11 12 15","9 14");
        //System.out.println(res1);

        // 2 inputs which give wrong answer
        //"0 1 8 10 11 12 20 21 30 + 14 19 = 19 ; 0,1 ; 10,11 ; 20,21 ; 8,10,12,14",
        //"1 2 3 6 7 10 12 14 = 1,3 ; 12,14 ; 2,6,10,14",

        //qm.doQuineMcCluskey("0 1 8 10 11 12 20 21 30", "14 19");



        for(StepTable s: qm.getListStepTables()){
            System.out.println(s);
        }

        System.out.println(qm.getUntickedTermsTable());

        System.out.println(qm.getPrimeImplicantTable());

        System.out.println(qm.getNumbersWithOneXTable());

        System.out.println(qm.getEssentialPrimeImplicantTable());

        System.out.println("\n\nHTML:\n\n");

        for(StepTable s: qm.getListStepTables()){
            System.out.println(s.toHTMLString());
        }

        System.out.println(qm.getUntickedTermsTable().toHTMLString());

        System.out.println(qm.getPrimeImplicantTable().toHTMLString());

        System.out.println(qm.getNumbersWithOneXTable().toHTMLString());

        System.out.println(qm.getEssentialPrimeImplicantTable().toHTMLString());


    }
}
