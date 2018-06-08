package Code;

import Table.StepTable;

import java.util.*;

public class Step implements Cloneable {
    public static final int NONE = -99;
    public static final int DASH = -99;
    public static final String TICK = "T";
    public static final String NO_TICK = "N";
    static Set<Set<Integer>> untickedTerms = new HashSet<>();
    static HashMap<Set<Integer>, List<Integer>> primeImplicantHashMap = new HashMap<>();
    ArrayList<GroupSubEntries>[] groupArray;
    int maxBits = 1;

    @SuppressWarnings("unchecked")
    // Used for the line:
    // groupArray = (ArrayList<GroupSubEntries>[]) new ArrayList[noOfGroups];
    private Step(int noOfGroups) {
        groupArray = (ArrayList<GroupSubEntries>[]) new ArrayList[noOfGroups];
        for (int i = 0; i < noOfGroups; i++)
            groupArray[i] = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
        // Used for the line:
        // groupArray = (ArrayList<GroupSubEntries>[]) new ArrayList[maxBits + 1];
    Step(int[] nums) {
        //Initializing the set
        untickedTerms.clear();
        primeImplicantHashMap.clear();


        maxBits = 1;

        Arrays.sort(nums);

        // Find no of max bits,
        // By using the last(greatest) element of nums
        maxBits = MintermToBinary(nums[nums.length - 1], maxBits).size();

        //Initiliazing the alphabets
        GroupSubEntries.initializeAlphabets(maxBits);

        // List of the input numbers, in their binary form
        // ie List<Integer> where each Integer is a bit of the number
        List<List> listBinNum = new ArrayList<>();
        for (int num : nums) {
            List currentNumBin = MintermToBinary(num, maxBits);
            listBinNum.add(currentNumBin);
        }

        // Initialize the groupArray
        groupArray = (ArrayList<GroupSubEntries>[]) new ArrayList[maxBits + 1];
        for (int i = 0; i < groupArray.length; i++)
            groupArray[i] = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int ones = Collections.frequency(listBinNum.get(i), 1);
            Set<Integer> tempMintermsSet = new HashSet<>();
            tempMintermsSet.add(nums[i]);

            // Convert every number to it's GroupSubEntries form
            // Add it to the array of ArrayList<GroupSubEntries>
            // The array is indexed by the number of '1's present in it's binary number,
            // which is present in listBinNum
            groupArray[ones].add(new GroupSubEntries(
                    listBinNum.get(i), tempMintermsSet
            ));
        }


    }

    Boolean IsStepEmpty() {
        Boolean result = true;
        for (int i = 0; i < this.groupArray.length; i++)
            for (int j = 0; j < this.groupArray[i].size(); j++)
                if (!groupArray[i].isEmpty())
                    result = false;
        return result;
    }

    private List MintermToBinary(int num, int maxBits) {
        List<Integer> binary = new ArrayList<>();

        int n = num;
        while (n != 0) {
            binary.add(n % 2);
            n = n / 2;
        }

        while (binary.size() < maxBits)
            binary.add(0);

        Collections.reverse(binary);
        return binary;
    }

    Step createNextStep() {
        Step newStep = new Step(this.groupArray.length);

        //adding all terms of previous step to untickedTerms
        for (ArrayList<GroupSubEntries> aGroup : this.groupArray)
            for (GroupSubEntries gs : aGroup) {
                untickedTerms.add(gs.minterms);
                primeImplicantHashMap.put(
                        gs.minterms,
                        gs.binaryRepresentation);
            }

        for (int i = 0; i < this.groupArray.length - 1; i++) {
            // Using from i=0 to i<length-1
            // Because j is iterating over element groupArray[i]
            // And     k is iterating over element groupArray[i+1]

            if (this.groupArray[i].isEmpty())
                continue;

            //counter for groupArray i groupSubEntries
            for (int j = 0; j < this.groupArray[i].size(); j++) {

                //counter for groupArray i+1 groupSubEntries
                for (int k = 0; k < this.groupArray[i + 1].size(); k++) {

                    int changedBit = oneBitDifference(this.groupArray[i].get(j).binaryRepresentation,
                            this.groupArray[i + 1].get(k).binaryRepresentation);

                    if (changedBit != NONE) {

                        //Copying list from this, and then making changes
                        List<Integer> newBinRep = new ArrayList<>();
                        for (Integer integer : this.groupArray[i].get(j).binaryRepresentation) {
                            newBinRep.add(integer);
                        }

                        // Setting the changed bit to "-"
                        newBinRep.set(changedBit, DASH);

                        // Removing a "ticked" term
                        if (untickedTerms.contains(this.groupArray[i].get(j).minterms)) {
                            this.groupArray[i].get(j).ticked = true;
                            untickedTerms.remove(this.groupArray[i].get(j).minterms);
                            primeImplicantHashMap.remove(this.groupArray[i].get(j).minterms);
                        }

                        if (untickedTerms.contains(this.groupArray[i + 1].get(k).minterms)) {
                            this.groupArray[i + 1].get(k).ticked = true;
                            untickedTerms.remove(this.groupArray[i + 1].get(k).minterms);
                            primeImplicantHashMap.remove(this.groupArray[i + 1].get(k).minterms);
                        }


                        Set<Integer> newMinterms = new HashSet<>();
                        //Add the minterms used in groupArray[i] and groupArray[i+1]
                        for (Integer min : this.groupArray[i].get(j).minterms)
                            newMinterms.add(min);
                        for (Integer min : this.groupArray[i + 1].get(k).minterms)
                            newMinterms.add(min);

                        GroupSubEntries gsToBeAdded = new GroupSubEntries(newBinRep, newMinterms);
                        newStep.groupArray[i].add(gsToBeAdded);
                    }

                }//K loop, for gs[k] in the groupArray[i+1]
            }//J loop, for gs[j] in the groupArray[i]
        }//I loop, for groupArray[i]
        return newStep;
    }

    private List<Integer> EXOR(List<Integer> l1, List<Integer> l2) {
        List<Integer> xor = new ArrayList<>();
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i).equals(l2.get(i))) {
                xor.add(0);
            } else {
                xor.add(1);
            }
        }
        return xor;
    }

    private int oneBitDifference(List<Integer> bin0, List<Integer> bin1) {
        int changedBit;

        List<Integer> xorList = EXOR(bin0, bin1);
        int freqOfOnes = Collections.frequency(xorList, 1);

        if (freqOfOnes == 1) {
            changedBit = xorList.indexOf(1);
            return changedBit;
        } else
            return NONE;
    }

    void createStepTableFromStep(List<StepTable> listStepTable, String head) {
        StepTable stepTable = new StepTable();

        stepTable.setHeader(head);

        // Adding row Zero
        String[] a = {"", "", GroupSubEntries.correctString(GroupSubEntries.alphabets)};
        stepTable.setRowZero(a);

        int noOfRows = 0;
        for (int i = 0; i < groupArray.length; i++) {
            noOfRows += groupArray[i].size();
        }

        String[][] arows = new String[noOfRows][4];
        int rowNo = 0;
        for (int i = 0; i < groupArray.length; i++) {
            for (GroupSubEntries gs : groupArray[i]) {
                arows[rowNo][0] = String.valueOf(i);
                arows[rowNo][1] = GroupSubEntries.correctString(gs.minterms);
                arows[rowNo][2] = GroupSubEntries.correctString(gs.binaryRepresentation);

                //assign value of ticked here
                if (gs.ticked)
                    arows[rowNo][3] = TICK;
                else
                    arows[rowNo][3] = NO_TICK;

                rowNo++;
            }
        }

        stepTable.setnRows(arows);
        listStepTable.add(stepTable);

    }
}
