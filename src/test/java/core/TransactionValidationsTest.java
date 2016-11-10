package core;

import data.UserTransactionGraph;
import data.UserTransactionHistory;
import model.Transaction;
import model.TransactionStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionValidationsTest {

    private UserTransactionGraph userTransactionGraph;
    private UserTransactionHistory userTransactionHistory;
    private TransactionValidations transactionValidations;
    private Date date = new Date();

    @Before
    public void setUp() {
        userTransactionGraph = UserTransactionGraph.getInstance();
        userTransactionHistory = UserTransactionHistory.getInstance();
        transactionValidations = TransactionValidations.getInstance();

        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 1));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 1));
        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 2));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 2));
        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 3));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 3));
        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 4));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 4));
        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 5));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 5));
        userTransactionGraph.updateGraph(getMockTransaction(0, 1, 6));
        userTransactionHistory.addTransaction(getMockTransaction(0, 1, 6));
        userTransactionGraph.updateGraph(getMockTransaction(1, 2, 4));
        userTransactionHistory.addTransaction(getMockTransaction(1, 2, 4));
    }

    @After
    public void tearDown() {
        userTransactionGraph.clear();
        userTransactionHistory.clear();
    }

    @Test
    public void testIsVerifiedReceiver() {
        assertEquals(TransactionStatus.TRUSTED, transactionValidations.isVerifiedReceiver(getMockTransaction(0, 1, 3), 1));
        assertEquals(TransactionStatus.UNVERIFIED, transactionValidations.isVerifiedReceiver(getMockTransaction(0, 2, 3), 1));
        assertEquals(TransactionStatus.TRUSTED, transactionValidations.isVerifiedReceiver(getMockTransaction(0, 2, 3), 2));
    }

    @Test
    public void testHasReceivedTooMany() {
        assertEquals(TransactionStatus.UNVERIFIED, transactionValidations.hasReceivedTooMany(getMockTransaction(0, 1, 8)));
        assertEquals(TransactionStatus.TRUSTED, transactionValidations.hasReceivedTooMany(getMockTransaction(0, 3, 8)));
    }

    @Test
    public void testIsAmountInconsistent() {
        assertEquals(TransactionStatus.TRUSTED, transactionValidations.isAmountInconsistent(getMockTransaction(0, 1, 8)));
        assertEquals(TransactionStatus.UNVERIFIED, transactionValidations.isAmountInconsistent(getMockTransaction(0, 1, 9)));
    }

    private Transaction getMockTransaction(long senderUID, long recipientUID, double amount) {
        return new Transaction(date, senderUID, recipientUID, amount, "message");
    }

}