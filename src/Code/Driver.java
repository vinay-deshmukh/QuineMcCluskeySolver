package Code;

import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    public static void main(String args[])
    {
        System.out.println("Enter 4 bit minterms");
        Scanner sc = new Scanner(System.in);

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
        }

        System.out.println("main, before step constructor");
        // Create a Step Object
        Step step = new Step(numbers);
        System.out.println("Step 0");
        step.display();

        System.out.println("\nStep 1\n");
        System.out.println("Creating new step");
        Step step1 = step.createNextStep();

        System.out.println("\n\n   DISPLAYING STEP 1");
        step1.display();


    }
}
