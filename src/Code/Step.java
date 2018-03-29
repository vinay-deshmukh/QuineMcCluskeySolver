package Code;

import Table.StepTable;

import java.util.*;

public class Step implements Cloneable
{
    public static final int NONE = -99;
    public static final int DASH = -99;
    ArrayList<GroupSubEntries> [] group;

    int maxBits=1;


    static Set<Set<Integer>> untickedTerms = new HashSet<>();
    static HashMap< Set<Integer>, List<Integer> > primeImplicantHashMap = new HashMap<>();

    Boolean IsStepEmpty()
    {
        Boolean result = true;
        for(int i = 0; i < this.group.length; i++)
            for(int j = 0; j < this.group[i].size(); j++)
                if(!group[i].isEmpty())
                    result = false;
        return result;
    }

    List MintermToBinary(int num, int maxBits){
        List<Integer> binary = new ArrayList<Integer>();

        int n = num;
        while(n !=0)
        {
            binary.add(new Integer(n%2));
            n=n/2;
        }

        while(binary.size()<maxBits)
            binary.add(new Integer(0));

        Collections.reverse(binary);
        return binary;
    }

    @SuppressWarnings("unchecked")
    // Used for the line:
    // group = (ArrayList<GroupSubEntries>[]) new ArrayList[noOfGroups];
    Step(int noOfGroups)
    {
        group = (ArrayList<GroupSubEntries>[]) new ArrayList[noOfGroups];
        for(int i =0; i<noOfGroups; i++)
            group[i] = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // Used for the line:
    // group = (ArrayList<GroupSubEntries>[]) new ArrayList[maxBits + 1];
    Step(int [] nums)
    {
        //Initializing the set
        untickedTerms.clear();
        primeImplicantHashMap.clear();


        maxBits = 1;

        Arrays.sort(nums);

        // Find no of max bits,
        // By using the last(greatest) element of nums
        maxBits = MintermToBinary(nums[nums.length -1], maxBits).size();

        //Initiliazing the alphabets
        GroupSubEntries.initializeAlphabets(maxBits);

        List<List> listBinNum = new ArrayList<>();
        for(int num : nums)
        {
            List currentNumBin = MintermToBinary(num, maxBits);
            listBinNum.add(currentNumBin);
        }

        group = (ArrayList<GroupSubEntries>[]) new ArrayList[maxBits + 1];
        for(int i =0; i<group.length; i++)
            group[i] = new ArrayList<>();

        for(int i=0;i<nums.length;i++)
        {
            int ones = Collections.frequency(listBinNum.get(i), 1);
            Set <Integer> tempMintermsSet = new HashSet<>();
            tempMintermsSet.add(nums[i]);
            group[ones].add( new GroupSubEntries(
                                listBinNum.get(i),tempMintermsSet
                                ));
        }


    }

    Step createNextStep()
    {
        Step newStep = new Step(this.group.length);

        //adding all terms of previous step to untickedTerms
        for(int i = 0; i < this.group.length ; i++)
            for(int j = 0; j< this.group[i].size(); j++ )
            {
                untickedTerms.add( this.group[i].get(j).minterms);
                primeImplicantHashMap.put(this.group[i].get(j).minterms,
                        this.group[i].get(j).binaryRepresentation);
            }




        for(int i=0;i<this.group.length - 1; i++)
        {
            if(this.group[i].isEmpty())
                continue;





            for(int j =0;j < this.group[i].size();j++)//counter for group i groupSubEntries
            {



                for(int k =0; k< this.group[i+1].size();k++)//counter for group i+1 groupSubEntries
                {
                    int changedBit = oneBitDifference(this.group[i].get(j).binaryRepresentation,
                            this.group[i + 1].get(k).binaryRepresentation);

                    if (changedBit == NONE)
                        continue;
                    else {

                        //Copying list from this, and then making changes
                        List<Integer> newBinRep = new ArrayList<>();
                        for(Integer integer :this.group[i].get(j).binaryRepresentation)
                        {
                            newBinRep.add(integer);
                        }

                        // Setting the changed bit to "-"
                        newBinRep.set(changedBit, DASH);

                        // Removing a "ticked" term
                        if(untickedTerms.contains(this.group[i].get(j).minterms))
                        {
                            untickedTerms.remove(this.group[i].get(j).minterms);
                            primeImplicantHashMap.remove(this.group[i].get(j).minterms);
                        }

                        if(untickedTerms.contains(this.group[i + 1].get(k).minterms))
                        {
                            untickedTerms.remove(this.group[i + 1].get(k).minterms);
                            primeImplicantHashMap.remove(this.group[i + 1].get(k).minterms);
                        }



                        Set<Integer> newMinterms = new HashSet<>();
                        //Add the minterms used in group[i] and group[i+1]
                        for (Integer min : this.group[i].get(j).minterms)
                            newMinterms.add(min);
                        for (Integer min : this.group[i + 1].get(k).minterms)
                            newMinterms.add(min);

                        GroupSubEntries gsToBeAdded = new GroupSubEntries(newBinRep, newMinterms);
                        newStep.group[i].add( gsToBeAdded);
                    }// else
                }//K loop, for gs[k] in the group[i+1]
            }//J loop, for gs[j] in the group[i]
        }//I loop, for group[i]
        return newStep;
    }

    List<Integer> EXOR(List<Integer>l1, List<Integer>l2)
    {
        List<Integer>xor = new ArrayList<>();
        for(int i =0; i<l1.size();i++)
        {
            if(l1.get(i).equals(l2.get(i)))
            {
                xor.add(new Integer(0));
            }
            else
            {
                xor.add(new Integer(1));
            }
        }
        return xor;
    }

    int oneBitDifference(List<Integer> bin0, List<Integer> bin1)
    {
        int changedBit;

        List<Integer> xorList = EXOR(bin0, bin1);
        int freqOfOnes = Collections.frequency(xorList, new Integer(1));

        if(freqOfOnes == 1)
        {
            changedBit = xorList.indexOf(new Integer(1));
            return changedBit;
        }
        else
            return NONE;
    }

    void display(List <StepTable> listStepTable, String head)
    {
        StepTable stepTable = new StepTable();

        stepTable.setHeader(head);

        //System.out.println("Group Minterms BinaryRepresentation");
        System.out.format("%5s | %15s | %20s\n",
                "Group",
                "Minterms",
                "BinaryRepresentation");
        // Column Titles is already included in definition, so no need to set it here

        System.out.format("%5s | %15s | %20s\n","","",
        GroupSubEntries.correctString(GroupSubEntries.alphabets));

        // Adding row Zero
        String [] a = {"", "", GroupSubEntries.correctString(GroupSubEntries.alphabets)};
        stepTable.setRowZero(a);

        int noOfRows = 0;
        for(int i=0;i<group.length;i++){
            noOfRows += group[i].size();
        }

        String [][] arows = new String[noOfRows][3];
        int rowNo = 0;
        for(int i=0;i<group.length; i++)
        {
            for(GroupSubEntries gs : group[i])
            {
                System.out.format("%5s | %15s | %20s\n",
                        i,
                        GroupSubEntries.correctString(gs.minterms),
                        GroupSubEntries.correctString(gs.binaryRepresentation));

                arows[rowNo][0] = String.valueOf(i);
                arows[rowNo][1] = GroupSubEntries.correctString(gs.minterms);
                arows[rowNo][2] = GroupSubEntries.correctString(gs.binaryRepresentation);
                rowNo++;
            }
        }

        stepTable.setnRows(arows);
        listStepTable.add(stepTable);

    }
}
