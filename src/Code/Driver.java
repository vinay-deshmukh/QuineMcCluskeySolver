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


        System.out.println(step.IsStepEmpty());
        System.out.println(step1.IsStepEmpty());
        System.out.println(step2.IsStepEmpty());
        System.out.println(step3.IsStepEmpty());
        //AUTO METHOD

        Step stepN0 = new Step(numbers);
        Step stepN1;
        do {
        stepN1 = stepN0.createNextStep();
        stepN0 = stepN1;
        }while(!stepN1.IsStepEmpty());
        System.out.println("\n\n Unticked terms");
        System.out.println(Step.untickedTerms);




    }
}
