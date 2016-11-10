package data;

import com.google.common.collect.ImmutableList;
import model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserTransactionHistoryTest {

    private UserTransactionHistory history;
    private Date date = new Date();

    @Before
    public void setUp() {
        history = UserTransactionHistory.getInstance();
    }

    @After
    public void tearDown() {
        history.clear();
    }

    @Test
    public void testAddTransaction() {
        assertTrue(history.addTransaction(getMockTransaction(0 ,1)));
    }

    @Test
    public void testGetLastKSentTransactions() {
        history.addTransaction(getMockTransaction(0, 1));
        history.addTransaction(getMockTransaction(0, 2));
        history.addTransaction(getMockTransaction(0, 3));
        history.addTransaction(getMockTransaction(0, 4));
        history.addTransaction(getMockTransaction(0, 5));
        history.addTransaction(getMockTransaction(0, 6));

        List<Transaction> expected = ImmutableList.of(
                getMockTransaction(0, 6),
                getMockTransaction(0, 5),
                getMockTransaction(0, 4),
                getMockTransaction(0, 3),
                getMockTransaction(0, 2)
        );

        assertEquals(expected, history.getLastKSentTransactions(0));
    }

    @Test
    public void getLastKReceivedTransactions() {
        history.addTransaction(getMockTransaction(0, 1));
        history.addTransaction(getMockTransaction(0, 2));
        history.addTransaction(getMockTransaction(2, 1));
        history.addTransaction(getMockTransaction(1, 2));
        history.addTransaction(getMockTransaction(3, 0));

        List<Transaction> expected = ImmutableList.of(getMockTransaction(2, 1), getMockTransaction(0, 1));

        assertEquals(expected, history.getLastKReceivedTransactions(1));
    }

    private Transaction getMockTransaction(long senderUID, long recipientUID) {
        return new Transaction(date, senderUID, recipientUID, 123.0, "message");
    }

}