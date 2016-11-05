package data;

import model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class UserTransactionGraphTest {

    private UserTransactionGraph graph;
    private Date date = new Date();

    @Before
    public void setUp() {
        graph = UserTransactionGraph.getInstance();
    }

    @After
    public void tearDown() {
        graph.clear();
    }

    @Test
    public void testUpdateGraph() {
        Transaction t = getMockTransaction(0, 1);
        assertTrue(graph.updateGraph(t));
    }

    @Test
    public void testGetFriendCircle() {
        graph.updateGraph(getMockTransaction(0, 1));
        graph.updateGraph(getMockTransaction(0, 2));
        graph.updateGraph(getMockTransaction(1, 0));
        graph.updateGraph(getMockTransaction(1, 1));
        graph.updateGraph(getMockTransaction(2, 2));
        graph.updateGraph(getMockTransaction(2, 3));
        graph.updateGraph(getMockTransaction(2, 9));
        graph.updateGraph(getMockTransaction(3, 4));
        graph.updateGraph(getMockTransaction(3, 5));
        graph.updateGraph(getMockTransaction(4, 7));
        graph.updateGraph(getMockTransaction(5, 6));
        graph.updateGraph(getMockTransaction(6, 7));
        graph.updateGraph(getMockTransaction(7, 6));
        graph.updateGraph(getMockTransaction(7, 8));

        assertEquals(4, graph.isInFriendCircle(getMockTransaction(0, 6), 4));
        assertEquals(Integer.MAX_VALUE, graph.isInFriendCircle(getMockTransaction(0, 8), 4));
    }

    private Transaction getMockTransaction(long senderUID, long recipientUID) {
        return new Transaction(date, senderUID, recipientUID, 123.0, "Message");
    }

}