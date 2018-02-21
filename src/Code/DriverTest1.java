package Code;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static Code.Driver.doQuineMcCluskey;
import static org.junit.Assert.*;

public class DriverTest1 {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private DriverTest1 terminalview;

    // Stream redirection is done since doQuineMcCluskey spits out lots of
    // text which is irrelevant here
    @Before
    public void setUpStreams() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() throws Exception {
        System.setOut(stdout);
        System.out.println("test");
    }

    @Test
    public void output_3()throws Exception{
        System.out.print("3");
        assertEquals("3", outContent.toString());

    }

    @Test
    public void outputs() throws Exception{
        /*
        0 1 3 7 8 9 11 15 =  0,1,8,9 ; 3,7,11,15
        0 1 2 3 5 7 8 9 11 14 = 14 ; 0,1,2,3 ; 0,1,8,9 ; 1,3,5,7 ; 1,3,9,11
        1 5 6 12 13 14 + 2 4 = 1,5 ; 4,5,12,13 ; 4,6,12,14
        0 1 3 4 5 6 11 13 14 15 = 3,11 ; 6,14 ; 13,15 ; 0,1,4,5
        4 5 8 9 11 12 13 15 = 4,5,12,13 ; 8,9,12,13 ; 9,11,13,15
        1 4 5 10 11 14 15 = 1,5 ; 4,5 ; 10,11,14,15
        1 3 7 9 11 13 15 + 2 4 = 1,3,9,11 ; 3,7,11,15 ; 9,11,13,15
        0 1 8 10 11 12 20 21 30 + 14 19 = 19 ; 0,1 ; 10,11 ; 20,21 ; 8,10,12,14
        1 2 3 6 7 10 12 14 = 1,3 ; 12,14 ; 2,6,10,14
        1 3 7 9 10 11 13 15 = 10,11 ; 1,3,9,11 ; 3,7,11,15 ; 9,11,13,15
        0 1 2 8 10 11 14 15 = 0,1 ; 0,2,8,10 ; 10,11,14,15
         */

        Set result = (Set)doQuineMcCluskey("0 1 3 7 8 9 11 15","");
        Set<Set> expected = new HashSet<>();

        Set<Integer> a = new HashSet<>();
        a.add(0); a.add(1); a.add(8); a.add(9);

        expected.add(a);
        a = new HashSet<>();
        a.add(3); a.add(7); a.add(11); a.add(15);
        expected.add(a);
        stdout.println("Result: "+ result);
        stdout.println("Expected: "+ expected);

        assertEquals(expected, result);

    }

}