package Code;

import java.util.*;

public class Driver {
    public static void main(String args[]) {


        //start
        System.out.println("Enter minterms");
        Scanner sc = new Scanner(System.in);

        List<Integer> dontCare = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();

        String line = sc.nextLine();
        String numStr [] = line.split(" ");
        //int numbers[] = new int[numStr.length];
        int i = 0;
        //System.out.println(numbers);
        for(String s : numStr) {
            try {
                numsList.add(Integer.parseInt(s));
                i++;
            } catch (Exception e) {
                System.out.println(s + " is invalid");
            }
        }

        System.out.println("Enter don't care terms:");
        line = sc.nextLine();
        numStr = line.split(" ");
        //int numbers[] = new int[numStr.length];
        i = 0;
        //System.out.println(numbers);
        for(String s : numStr) {
            try {
                numsList.add(Integer.parseInt(s));
                dontCare.add(Integer.parseInt(s));
                i++;
            } catch (Exception e) {
                System.out.println(s + " is invalid");
            }
        }

        //Converting the list to array
        int numbers[] = new int[numsList.size()];
        for(int j = 0; j< numsList.size(); j++)
        {
            numbers[j] = numsList.get(j);
        }


        /*
        //ORIGINAL
        String line = sc.nextLine();
        String numStr [] = line.split(" ");
        int numbers[] = new int[numStr.length];
        int i = 0;
        System.out.println(numbers);
        for(String s : numStr){
            try
            {
                numbers[i] = Integer.parseInt(s);
                i++;
            }
            catch(Exception e)
            {
                System.out.println(s + " is invalid");
            }
        */



        /*
        //Testing block
        // Create a Step Object
        Step step = new Step(numbers);
        System.out.println("Step 0");
        step.display();

        System.out.println("\nStep 1\n");
        Step step1 = step.createNextStep();

        System.out.println("\n\n   DISPLAYING STEP 0");
        step.display();

        System.out.println("\n\n   DISPLAYING STEP 1");
        step1.display();

        Step step2 = step1.createNextStep();
        System.out.println("\n\n   DISPLAYING STEP 2");
        step2.display();

        Step step3 = step2.createNextStep();
        System.out.println("\n\n   DISPLAYING STEP 3");
        step3.display();

        System.out.println("\n\n Unticked terms");
        System.out.println(Step.untickedTerms);
        */

        //AUTO METHOD
        //Initiliazing no of X as zero for all numbers
        for(int j=0;j<numbers.length;j++)
        {
            PrimeImplicantTable.noOfXHashMap.put(new Integer(numbers[j]), new Integer(0));
        }


        Step stepN0 = new Step(numbers);
        Step stepN1;
        int stepNumber = 0;
        do {
            System.out.println("\n\n   DISPLAYING STEP "+stepNumber);
            stepN0.display();
            stepN1 = stepN0.createNextStep();
            stepN0 = stepN1;
        }while(!stepN1.IsStepEmpty());
        System.out.println("\n\nUnticked terms");
        //System.out.println(Step.untickedTerms);
        for( Set s: Step.untickedTerms)
        {
            System.out.println(GroupSubEntries.correctString(s));
        }



        Driver.displayPITable(numbers,dontCare);

        Set<Integer> essentialMinterm = new HashSet<>();
        System.out.println("\n\nNumbers with only 1 X");
        for(Integer key : PrimeImplicantTable.noOfXHashMap.keySet())
        {
            //System.out.println(key+" = "+PrimeImplicantTable.noOfXHashMap.get(key));
            if(PrimeImplicantTable.noOfXHashMap.get(key) == 1)
            {
                System.out.println(key);
                essentialMinterm.add(key);
            }
        }

        System.out.println("\n\nThe Essential Prime Implicants are:");
        Set<Set<Integer>> essentialPrimeImplicant = new HashSet<>();
        for(Set<Integer> u : Step.untickedTerms)
        {
            //if u and essentialMinterm have common
            // then u is ess PI
            Set<Integer> hold = new HashSet<>(u);
            hold.retainAll(essentialMinterm);
            if(!hold.isEmpty())
            {
                essentialPrimeImplicant.add(u);
            }

        }

        for( Set<Integer> s : essentialPrimeImplicant)
        {
            System.out.format("%16s %20s\n",
                    GroupSubEntries.correctString(s)
                    ,PrimeImplicantTable.binaryRepToPIForm(Step.primeImplicantHashMap.get(s)));
        }



    }

    static  void displayPITable(int [] numbers, List<Integer> dontCare)
    {
        System.out.println("\n\nPRIME IMPLICANT TABLE");

        //First Line
        System.out.format("%16s %16s %20s\n",
                "Prime Implicants",
                "Decimal minterms",
                "Minterms Given");

        String mintermsInARow = "";
        for( int a : numbers)
        {
            if(dontCare.contains(a))
                continue;
            else
                mintermsInARow+=" "+a;
        }

        //Second Line
        System.out.format("%16s %16s %20s\n",
                "",
                "",
                mintermsInARow);

        //Printing the data rows
        for(Set<Integer> s :Step.untickedTerms)
        {
            PrimeImplicantTable.CreateSingleRowInPITable(s, numbers,dontCare);
        }




    }

}
