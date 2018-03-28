package Code;

import java.util.*;

public class Driver {
    public static void main(String args[]) {

        //doQuineMcCluskey("4 8 10 11 12 15","9 14");

        Set res1 = (Set)doQuineMcCluskey("4 8 10 11 12 15","9 14");
        System.out.println(res1);
        //region Original Main
        /*
        System.out.println("Enter minterms");
        Scanner sc = new Scanner(System.in);

        List<Integer> dontCare = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();

        String line = sc.nextLine();
        String numStr [] = line.split(" ");

        int i=0;

        for(String s : numStr) {
            try {
                // Adding the input numbers to a list containing the numbers
                numsList.add(Integer.parseInt(s));
                i++;
            } catch (Exception e) {
                System.out.println(s + " is invalid");
            }
        }

        System.out.println("Enter don't care terms:");
        line = sc.nextLine();
        numStr = line.split(" ");
        i = 0;
        for(String s : numStr) {

            //for no terms
            if(numStr.length == 1)
                break;

            try {
                // Adding the dont care numbers to the list containing the numbers
                // and also in the list that hols dont care numbers
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


        //Initiliazing no of X as zero for all numbers
        for(int j=0;j<numbers.length;j++)
        {
            // For each number, assign the value of number of X's as zero
            // This value is assigned such that the key to refer to the value is
            // the number itself, ie the number whose x are needed.
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
            stepNumber++;
        }while(!stepN1.IsStepEmpty());
        System.out.println("\n\nUnticked terms");
        for( Set<Integer> s : Step.untickedTerms)
        {
            System.out.format("%16s %20s\n",
                    GroupSubEntries.correctString(s)
                    ,PrimeImplicantTable.binaryRepToPIForm(Step.primeImplicantHashMap.get(s)));
        }

        //Displaying the entire PI Table
        Driver.displayPITable(numbers,dontCare);

        Set<Integer> essentialMinterm = new HashSet<>();
        System.out.println("\n\nNumbers with only 1 X");
        for(Integer key : PrimeImplicantTable.noOfXHashMap.keySet())
        {
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
            //if u and essentialMinterm have common elements
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

    */
        //endregion

    }

    static  void displayPITable(int [] numbers, List<Integer> dontCare)
    {
        System.out.println("\n\nPRIME IMPLICANT TABLE");

        //First Line
        System.out.format("%16s | %16s | %20s\n",
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
        System.out.format("%16s | %16s | %20s\n",
                "",
                "",
                mintermsInARow);

        //Printing the data rows
        for(Set<Integer> s :Step.untickedTerms)
        {
            PrimeImplicantTable.CreateSingleRowInPITable(s, numbers,dontCare);
        }

    }


    static Object doQuineMcCluskey(String nums, String donts){

        List<Integer> dontCare = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();

        String line = nums;
        String numStr [] = line.split(" ");

        int i=0;

        for(String s : numStr) {
            try {
                // Adding the input numbers to a list containing the numbers
                numsList.add(Integer.parseInt(s));
                i++;
            } catch (Exception e) {
                System.out.println(s + " is invalid");
            }
        }

        System.out.println("Enter don't care terms:");
        line = donts;
        numStr = line.split(" ");
        i = 0;
        for(String s : numStr) {

            //for no terms
            if(numStr.length == 1)
                break;

            try {
                // Adding the dont care numbers to the list containing the numbers
                // and also in the list that hols dont care numbers
                numsList.add(Integer.parseInt(s));
                dontCare.add(Integer.parseInt(s));
                i++;
            } catch (Exception e) {
                System.out.println(s + " is invalid");
            }
        }

        //Converting the list to array
        // Can't use inbuilt function since that would give a
        // Integer[], and not int[]
        // And int[] has been used in a lot of places, which is inconvenient to rewrite
        int numbers[] = new int[numsList.size()];
        for(int j = 0; j< numsList.size(); j++)
        {
            numbers[j] = numsList.get(j);
        }


        //Initiliazing no of X as zero for all numbers
        for(int j=0;j<numbers.length;j++)
        {
            // For each number, assign the value of number of X's as zero
            // This value is assigned such that the key to refer to the value is
            // the number itself, ie the number whose x are needed.
            PrimeImplicantTable.noOfXHashMap.put(new Integer(numbers[j]), new Integer(0));
        }


        // Actual Working started
        Step stepN0 = new Step(numbers);
        Step stepN1;
        int stepNumber = 0;
        do {
            System.out.println("\n\n   DISPLAYING STEP "+stepNumber);
            stepN0.display();
            stepN1 = stepN0.createNextStep();
            stepN0 = stepN1;
            stepNumber++;
        }while(!stepN1.IsStepEmpty());
        // We check for if step is empty since
        // Step is generated from BinaryRepresentation where
        // we have a pair of minterms like
        // Pair 1 and Pair 2
        // 0 1 0 0 or 0 1 - 0
        // 0 1 1 0 or 0 0 - 0
        // ie a one bit difference
        // But when there exist no such pairs, then the step generated will be empty
        // An empty step signifies end of the calculation process

        System.out.println("\n\nUnticked terms");
        for( Set<Integer> s : Step.untickedTerms)
        {
            System.out.format("%16s %20s\n",
                    GroupSubEntries.correctString(s)
                    ,PrimeImplicantTable.binaryRepToPIForm(Step.primeImplicantHashMap.get(s)));
        }

        //Displaying the entire PI Table
        Driver.displayPITable(numbers,dontCare);

        Set<Integer> essentialMinterm = new HashSet<>();
        System.out.println("\n\nNumbers with only 1 X");
        for(Integer key : PrimeImplicantTable.noOfXHashMap.keySet())
        {
            // Find minterm which has only 1 X
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
            //if u and essentialMinterm have common elements
            // then u is ess PI

            // Create set hold to contain elements of set u
            Set<Integer> hold = new HashSet<>(u);

            // boolean retainAll(Collection<?> c)
            // Retains only the elements in this set that are contained in the specified collection
            // (optional operation). In other words, removes from this set all of its elements that are not contained
            // in the specified collection. If the specified collection is also a set, this operation effectively
            // modifies this set so that its value is the intersection of the two sets.

            // Seeing if set hold has any common terms with set essentialMinterm
            // This function will modify set hold to contain the intersection of the two sets.
            hold.retainAll(essentialMinterm);

            // If there are some common elements, set hold will not be empty.
            // ie u is a essential Prime Implicannt, since hold is a clone of u.
            if(!hold.isEmpty())
            {
                essentialPrimeImplicant.add(u);
            }

        }

        for( Set<Integer> s : essentialPrimeImplicant)
        {
            System.out.format("%16s | %20s\n",
                    GroupSubEntries.correctString(s)
                    ,PrimeImplicantTable.binaryRepToPIForm(Step.primeImplicantHashMap.get(s)));
        }

        return essentialPrimeImplicant;
    }

}
