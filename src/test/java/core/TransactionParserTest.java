package core;

import model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionParserTest {

    private String input;
    private Transaction expectedOutput;

    @Before
    public void setUp() {
        input = "2016-11-02 09:49:29, 52575, 1120, 25.32, Spam";
        expectedOutput = new Transaction(new Date(1478098169000L), 52575, 1120, 25.32, "Spam");
    }

    @Test
    public void testParseCSV() {
        assertEquals(expectedOutput, TransactionParser.parseCSV(input));
    }

}