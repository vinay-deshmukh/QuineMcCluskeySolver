package Code;

import Table.*;

import java.util.*;

public class QuineMcCluskey {

    // CONSTANT fields to decide what kind of string input.
    private final static int NUMS = 3;
    private final static int DONT = 4;

    // Constant for X and " " (blank) in PrimeImplicantTable
    public final static String X_PI = "X";
    public final static String BLANK = "$";

    private List<StepTable> listStepTables = new ArrayList<>();
    private PrimeImplicantTable primeImplicantTable = new PrimeImplicantTable();
    private NumbersWithOneXTable numbersWithOneXTable = new NumbersWithOneXTable();
    private UntickedTermsTable untickedTermsTable = new UntickedTermsTable();
    private EssentialPrimeImplicantTable essentialPrimeImplicantTable = new EssentialPrimeImplicantTable();
    private List<Integer> minterms = new ArrayList<>();
    private List<Integer> dontCare = new ArrayList<>();
    private Set<Set<Integer>> resultEssentialPIs = new HashSet<>();

    public List<StepTable> getListStepTables() {
        return listStepTables;
    }

    public PrimeImplicantTable getPrimeImplicantTable() {
        return primeImplicantTable;
    }

    public NumbersWithOneXTable getNumbersWithOneXTable() {
        return numbersWithOneXTable;
    }

    public UntickedTermsTable getUntickedTermsTable() {
        return untickedTermsTable;
    }

    public EssentialPrimeImplicantTable getEssentialPrimeImplicantTable() {
        return essentialPrimeImplicantTable;
    }

    public Set<Set<Integer>> getResultEssentialPIs() {
        return resultEssentialPIs;
    }

    private void generatePrimeImplicantTable(int[] numbers, List<Integer> dontCare) {
        primeImplicantTable.setHeader("Prime Implicant Table");
        primeImplicantTable.setColumnTitles(new String[]{"Prime Implicants", "Decimal minterms", "Given Minterms"});

        int max = numbers[0];
        for (int n : numbers)
            if (max < n)
                max = n;

        int noOfDigits = noOfDigits(max);

        String formatForOneChar = "%" + noOfDigits + "s";

        String mintermsInARow = "";
        for (int a : numbers) {
            if (dontCare.contains(a))
                continue;
            else
                mintermsInARow += " " + String.format(formatForOneChar, a);
        }


        primeImplicantTable.setRowZero(new String[]{"", "", mintermsInARow});

        String[][] arows = new String[Step.untickedTerms.size()][3];

        //Printing the data rows
        int i = 0;
        for (Set<Integer> s : Step.untickedTerms) {
            String[] oneRow = CreateSingleRowInPITable(s, numbers, dontCare, formatForOneChar);
            arows[i][0] = oneRow[0];
            arows[i][1] = oneRow[1];
            arows[i][2] = oneRow[2];

            i++;
        }

        primeImplicantTable.setnRows(arows);

    }

    //misc
    private String[] CreateSingleRowInPITable(Set<Integer> s, int[] numbers,
                                              List<Integer> dontCare, String formatForOneChar) {

        //Result of first column
        List<Integer> binaryRepOfMinterm = Step.primeImplicantHashMap.get(s);
        String PIalphabentForm = PI_Table.binaryRepToPIForm(binaryRepOfMinterm);

        //Result of second column
        String decimalNumbers = GroupSubEntries.correctString(s);

        //Result of third column
        String Xformat = "";

        for (int i = 0; i < numbers.length; i++) {

            int digits = 1;
            int n = numbers[i];
            while (n > 9) {
                n = n / 10;
                digits++;
            }


            if (!dontCare.contains(numbers[i])) {
                Xformat += " ";
                Integer prevNoOfX = PI_Table.noOfXHashMap.get(numbers[i]);
                if (s.contains(numbers[i])) {
                    Xformat += String.format(formatForOneChar, X_PI);
                    PI_Table.noOfXHashMap.put(numbers[i], new Integer(prevNoOfX + 1));
                } else {
                    Xformat += String.format(formatForOneChar, BLANK);
                    // Using $ instead of " " since parsing a blank space is not possible
                    // A special TableLayout is needed for PrimeImplicantTable as
                    // it has another table strucutre for it's X values
                }
            }

        }

        return new String[]{
                PIalphabentForm,
                decimalNumbers,
                Xformat};
    }

    private void parseStringToList(String string, int id) {

        if (id == DONT) {
            if (string.length() < 1) {
                // no dontcare terms
                return;
            }
        }

        // id can be one of two values
        // NUMS = Passed string contains minterms
        // ie add it to minterms
        // DONT = Passed string contains dont care terms
        // ie add it to both minterms and dontCare

        String[] s_arr = string.split(" ");
        for (String s : s_arr) {
            if (id == NUMS) {
                minterms.add(Integer.parseInt(s));
            } else if (id == DONT) {
                minterms.add(Integer.parseInt(s));
                dontCare.add(Integer.parseInt(s));
            }
        }

        // Remove duplicates from the list whilst preserving order
        // https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist/204004#204004

        Set<Integer> temp = new LinkedHashSet<>(minterms);
        minterms = new ArrayList<>(temp);

        temp = new LinkedHashSet<>(dontCare);
        dontCare = new ArrayList<>(temp);

        // Sort the lists:
        Collections.sort(minterms);
        Collections.sort(dontCare);
    }


    private int noOfDigits(int number) {
        // Method returns the number of digits in a number

        // Source: http://www.baeldung.com/java-number-of-digits-in-int
        // Divide and conquer implementation is the fastest
        if (number < 100000) {
            if (number < 100) {
                if (number < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (number < 1000) {
                    return 3;
                } else {
                    if (number < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            if (number < 10000000) {
                if (number < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                if (number < 100000000) {
                    return 8;
                } else {
                    if (number < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }

    public void doQuineMcCluskey(String nums, String donts) {

        // Clear the list values before starting new calculations
        // This is so that we can perform multiple calculations with the same object
        minterms.clear();
        dontCare.clear();

        // Clear the tables before starting new calculations
        // This i so that we can perform multiple calculations with the same object
        listStepTables.clear();
        primeImplicantTable.setnRows(null);
        numbersWithOneXTable.setnRows(null);
        untickedTermsTable.setnRows(null);
        essentialPrimeImplicantTable.setnRows(null);


        parseStringToList(nums, QuineMcCluskey.NUMS);
        // Add the values to the list minterms

        parseStringToList(donts, QuineMcCluskey.DONT);
        // Add the values to the list minterms and dontCare


        //Converting the list to array
        // Can't use inbuilt function since that would give a
        // Integer[], and not int[]
        // And int[] has been used in a lot of places, which is inconvenient to rewrite
        int numbers[] = new int[minterms.size()];
        for (int j = 0; j < minterms.size(); j++) {
            numbers[j] = minterms.get(j);
        }


        //Initiliazing no of X as zero for all numbers
        for (int j = 0; j < numbers.length; j++) {
            // For each number, assign the value of number of X's as zero
            // This value is assigned such that the key to refer to the value is
            // the number itself, ie the number whose x are needed.
            PI_Table.noOfXHashMap.put(new Integer(numbers[j]), new Integer(0));
        }


        // Actual Working started
        Step stepN0 = new Step(numbers);
        List<Step> stepList = new ArrayList<>();
        stepList.add(stepN0);
        Step stepN1;

        do {
            //Create the stepTable after all are calculated since
            // we decide the ticked terms after we have calculated the next step
            //stepN0.createStepTableFromStep(listStepTables, "Step " +stepNumber);
            stepN1 = stepN0.createNextStep();
            stepList.add(stepN1);
            stepN0 = stepN1;

        } while (!stepN1.IsStepEmpty());
        // We check for if step is empty since
        // Step is generated from BinaryRepresentation where
        // we have a pair of minterms like
        // Pair 1 and Pair 2
        // 0 1 0 0 or 0 1 - 0
        // 0 1 1 0 or 0 0 - 0
        // ie a one bit difference
        // But when there exist no such pairs, then the step generated will be empty
        // An empty step signifies end of the calculation process

        // Create the StepTable(s) from Step(s)
        for (int stepNumber = 0; stepNumber < stepList.size(); stepNumber++) {
            if(!stepList.get(stepNumber).IsStepEmpty())
                stepList.get(stepNumber).createStepTableFromStep(listStepTables, "Step " + stepNumber);
        }

        untickedTermsTable.setHeader("Unticked Terms");
        untickedTermsTable.setRowZero(null);
        untickedTermsTable.setColumnTitles(new String[]{"Decimal Minterm", "Binary Representation"});
        String[][] arows = new String[Step.untickedTerms.size()][2];
        int k = 0;
        for (Set<Integer> s : Step.untickedTerms) {
            arows[k] = new String[]{GroupSubEntries.correctString(s)
                    , PI_Table.binaryRepToPIForm(Step.primeImplicantHashMap.get(s))};
            k++;
        }
        untickedTermsTable.setnRows(arows);

        //Displaying the entire PI Table
        this.generatePrimeImplicantTable(numbers, dontCare);

        Set<Integer> essentialMinterm = new HashSet<>();
        ArrayList<String> crowList = new ArrayList<>();


        for (Integer key : PI_Table.noOfXHashMap.keySet()) {
            // Find minterm which has only 1 X
            if (PI_Table.noOfXHashMap.get(key) == 1) {
                essentialMinterm.add(key);
                crowList.add(String.valueOf(key));
            }
        }

        String[][] crow = new String[crowList.size()][1];
        int ci = 0;
        for (String as : crowList) {
            crow[ci] = new String[]{as};
            ci++;
        }
        numbersWithOneXTable.setnRows(crow);


        essentialPrimeImplicantTable.setHeader("Essential Implicants");
        essentialPrimeImplicantTable.setColumnTitles(new String[]{"Decimal Minterm", "Binary Representation"});
        essentialPrimeImplicantTable.setRowZero(null);

        resultEssentialPIs = new HashSet<>();
        for (Set<Integer> u : Step.untickedTerms) {
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
            if (!hold.isEmpty()) {
                resultEssentialPIs.add(u);
            }

        }

        String[][] brows = new String[resultEssentialPIs.size()][2];
        int ii = 0;
        for (Set<Integer> s : resultEssentialPIs) {
            brows[ii][0] = GroupSubEntries.correctString(s);
            brows[ii][1] = PI_Table.binaryRepToPIForm(Step.primeImplicantHashMap.get(s));
            ii++;
        }

        essentialPrimeImplicantTable.setnRows(brows);

        // getResultEssentialPIs()
        // Use this to get the answer

    }

}
