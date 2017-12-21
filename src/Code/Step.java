package Code;

import java.util.*;

public class Step implements Cloneable
{
    public static final int NONE = -99;
    public static final int DASH = 9;
    //Group group[];
    ArrayList<GroupSubEntries> group[];

    int maxBits=1;


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

    Step(int noOfGroups)
    {

        group = new Group[noOfGroups];
        for(int i =0; i<noOfGroups; i++)
            group[i] = new Group();
    }

    Step(int [] nums)
    {
        maxBits = 1;

        Arrays.sort(nums);
        /*
        for(int i =0; i<= nums.length/2;i++)
        {
            int t = nums[i];
            nums[i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = t;
        }
        */

        maxBits = MintermToBinary(nums[nums.length -1], maxBits).size();

        List<List> listBinNum = new ArrayList<>();
        //System.out.println("before conversion for loop");
        for(int num : nums)
        {
            List currentNumBin = MintermToBinary(num, maxBits);
            listBinNum.add(currentNumBin);
            //System.out.println(num +" = " + currentNumBin);
            //if(currentNumBin.size() > maxBits)
              //  maxBits = currentNumBin.size();
        }

        // find no of max bits
        // Create Group array of that size

        group = new Group[maxBits];
        for(int i =0; i<group.length; i++)
            group[i] = new Group();

        // new try
        for(int i=0;i<nums.length;i++)
        {
            int ones = Collections.frequency(listBinNum.get(i), 1);
            //System.out.println("ones ="  +ones);
            Set <Integer> tempMintermsSet = new HashSet<Integer>();
            tempMintermsSet.add(nums[i]);
            group[ones].add( new GroupSubEntries(
                                listBinNum.get(i),tempMintermsSet
                                ));
        }


    }


    Step(Step s)
    {
        this.group = s.group;
        for(int i =0; i<s.group.length;i++)
        {
            for(int j = 0; j<s.group[i].size(); j++)
            {
                this.group[i].add(s.group[i].get(j));
            }

        }

    }


    Step copyOfStep()
    {
        //create field for field copy of step object
        Step temp = new Step(this.group.length);


        for(int i =0; i<this.group.length;i++)
        {
            for(int j = 0; j<this.group[i].size(); j++)
            {
                GroupSubEntries gs = this.group[i].get(j);
                temp.group[i].add(new GroupSubEntries(gs));
            }

        }

        return temp;
    }

    //found issue
    //this is being overwritten, before creation of next step
    Step createNextStep()
    {
        Step newStep = new Step(this.group.length);
        Step tempStep;
        for(int i=0;i<this.group.length - 1; i++)
        {
            //Resetting temp group before checking each group
            // As set method may change the values
            tempStep = this.copyOfStep();
            //tempStep = new Step(this);

            /*
            System.out.println("\n\n    PRINTING TEMP GROUP "+i);
            tempStep.display();

            System.out.println("\n\n    PRINTING THIS GROUP "+i);
            this.display();

            System.out.println("\n\nTHIS==TEMP = "+ (tempStep==this));
            System.out.println("THIS EQUALS TEMP = "+tempStep.equals(this));
            */
            //System.out.println("\tInside i= "+i+", loop for group "+i);

            if(tempStep.group[i].isEmpty())
                continue;

            for(int j =0;j < tempStep.group[i].size();j++)//counter for group i groupSubEntries
            {
              //  System.out.println("\t\tInside j= "+j+", loop for group "+i);
                for(int k =0; k< tempStep.group[i+1].size();k++)//counter for group i+1 groupSubEntries
                {
                //    System.out.println("\t\t\tInside k= "+k+", loop for group "+ i+"+1");




                    int changedBit = oneBitDifference(tempStep.group[i].get(j).binaryRepresentation,
                            tempStep.group[i + 1].get(k).binaryRepresentation);

                    System.out.println(tempStep.group[i].get(j).minterms
                                +" & " + tempStep.group[i+1].get(k).minterms
                                +" = changedBit = "+changedBit);

                    if (changedBit == NONE)
                        continue;
                    else {
                        //System.out.println("\t\t\t\tChanged Bit is "+changedBit);
                        //if there is a suitable pair present
                        //change the different bit to dash, and store it in the correct groupSubEntries object

                        // Setting the newStep binaryRepresentation with the changed Bit
                        //int originalBitBeforeChanging = tempStep.group[i].get(j).binaryRepresentation.get(changedBit);
                        //tempStep.group[i].get(j).binaryRepresentation.set(changedBit, DASH);

                        List<Integer> newBinRep = new ArrayList<>();
                        for(Integer integer :tempStep.group[i].get(j).binaryRepresentation)
                        {
                            newBinRep.add(integer);
                        }

                        newBinRep.set(changedBit, DASH);

                        //when creating new groupSubEntries row
                        //create new groupSubEntries object, and add that to group[i]



                        Set<Integer> newMinterms = new HashSet<>();
                        //Add the minterms used in group[i] and group[i+1]
                        for (Integer min : tempStep.group[i].get(j).minterms)
                            newMinterms.add(min);
                        for (Integer min : tempStep.group[i + 1].get(k).minterms)
                            newMinterms.add(min);

                        GroupSubEntries gsToBeAdded = new GroupSubEntries(newBinRep, newMinterms);
                        newStep.group[i].add( gsToBeAdded);
                        gsToBeAdded.displayGroupSubEntries();

                        //Since tempStep points to this
                        // Or since changes in tempStep show up in this
                        //resetting value of changed field
                        //tempStep.group[i].get(j).binaryRepresentation.set(changedBit, originalBitBeforeChanging);



                    }// else

                }//K loop, for the group[i+1]

            }
        }


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
        int differences = 0;
        int changedBit = -1;
        int tempBitHolder;
        //System.out.println("Original binRep are "+bin0+"; "+bin1);

        List<Integer> xorList = EXOR(bin0, bin1);
        int freqOfOnes = Collections.frequency(xorList, new Integer(1));
        //System.out.println("\n\n"+bin0 + " ^ " + bin1 +" = " + xorList);
        //System.out.println("Frequency of ones is "+freqOfOnes);

        if(freqOfOnes == 1)
        {
            changedBit = xorList.indexOf(new Integer(1));
            //System.out.println("CHANGED BIT IS "+changedBit);
            return changedBit;
        }
        else
            return NONE;


        /* NEW TRY WITH EXOR
        if(freqOfOnes == 1)
        {
            for( int i =0;i<bin0.size();i++)
                if(bin0.get(i)!=bin1.get(i))
                {
                    System.out.println("Bits "+i+", are "+bin0.get(i)+" and "+bin1.get(i));
                    changedBit=i;
                    break;
                }

            return changedBit;
        }
        else
            return NONE;
        */


        /* Original
        for(int i =0; i<bin0.size(); i++)
            if(bin0.get(i) != bin1.get(i))
            {
                System.out.println("Bits "+i+", are "+bin0.get(i)+" and "+bin1.get(i));
                differences++;
                if(differences == 1)
                    changedBit = i;
            }
        System.out.println("oneBitDifference is at "+changedBit);



        if(differences == 1)
            return changedBit;
        else
            return NONE;
        */
    }

    void display()
    {
        System.out.println("Group Minterms BinaryRepresentation");
        for(int i=0;i<group.length; i++)
        {
           // System.out.println("gs entries present in group " + i + " = " +  group[i].size());
            for(GroupSubEntries gs : group[i])
            {
               // System.out.println("inside gs loop");
                System.out.println(i + gs.minterms.toString() + gs.binaryRepresentation);
            }
        }

    }
}
