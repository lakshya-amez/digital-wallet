package data;

import com.google.common.annotations.VisibleForTesting;
import model.Transaction;
import utils.graphs.UndirectedGraph;
import utils.graphs.traversals.GraphSearch;
import utils.graphs.traversals.GraphSearchAlgorithm;
import utils.graphs.traversals.GraphSearchFactory;

/**
 * An undirected graph of users connected via previous transactions. The vertices are userIDs. <br/>
 * <b>NOTE: </b> Ideally this could have been stored in a graph database like <a href="https://neo4j.com/">Neo4j</a>.
 */
public class UserTransactionGraph extends UndirectedGraph<Long> {

    private GraphSearch<Long> searchAlgorithm;

    private UserTransactionGraph() {
        searchAlgorithm = GraphSearchFactory.getGraphTraversalImplementation(GraphSearchAlgorithm.BFS);
    }

    private static class LazyHolder {
        private static final UserTransactionGraph INSTANCE = new UserTransactionGraph();
    }

    public static UserTransactionGraph getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Updates the graph with a {@link Transaction}:
     * <ol>
     *     <li> Add {@code senderUID} as a vertex. </li>
     *     <li> Add {@code recipientUID} as a vertex. </li>
     *     <li> Add an edge between the two user IDs. </li>
     * </ol>
     * @param transaction The {@link Transaction} with which to update the graph.
     * @return {@code boolean} value indicating whether transaction was successfully added to the graph.
     */
    public boolean updateGraph(Transaction transaction) {
        addVertex(transaction.getSenderUID());
        addVertex(transaction.getRecipientUID());
        return addEdge(transaction.getSenderUID(), transaction.getRecipientUID());
    }

    /**
     * Check whether recipient is in sender's friend circle up to given max degree.
     * @param transaction The user for which to fetch the friend circle.
     * @param maxDegree The max degree of friendship to be considered.
     * @return {@code int} value indicating degree of friendship if recipient is in sender's friend circle, {@code Integer.MAX_VALUE} otherwise.
     */
    public int isInFriendCircle(Transaction transaction, int maxDegree) {
        return searchAlgorithm.search(this, transaction.getSenderUID(), transaction.getRecipientUID(), maxDegree);
    }

    @VisibleForTesting
    public void clear() {
        super.adjacencyList.clear();
    }

}
