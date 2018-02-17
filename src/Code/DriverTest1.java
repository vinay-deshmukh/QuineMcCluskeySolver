package Code;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DriverTest1 {

    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private DriverTest1 terminalview;

    @Before
    public void setUpStreams() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() throws Exception {
        System.setOut(System.out);
    }

    @Test
    public void output_3()throws Exception{
        System.out.print("3");
        assertEquals("3", outContent.toString());

    }

}