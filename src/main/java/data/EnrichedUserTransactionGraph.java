package data;

import com.google.common.annotations.VisibleForTesting;
import model.Transaction;
import utils.graphs.WeightedUndirectedGraph;
import utils.graphs.traversals.GraphSearch;
import utils.graphs.traversals.GraphSearchAlgorithm;
import utils.graphs.traversals.GraphSearchFactory;

import java.util.Map;

/**
 * Stores an enriched version of {@link UserTransactionGraph} in which vertices are connected directly up to a given
 * max distance. This results in O(1) lookups as against O(n) in {@link UserTransactionGraph} which does lookups using
 * BFS. This graph is populated using the batch data and is not updated until the next batch is run. This is used as a
 * cache, if found then users are definitely friends, else we need to lookup the same in {@link UserTransactionGraph}.
 * <b>NOTE: </b> This has been disabled as the graph becomes too big and JVM goes out of memory.
 */
public class EnrichedUserTransactionGraph extends WeightedUndirectedGraph<Long> {

    private static final int MAX_DEGREE = 2;

    private UserTransactionGraph userTransactionGraph;
    private GraphSearch<Long> graphSearch;

    private EnrichedUserTransactionGraph() {
        userTransactionGraph = UserTransactionGraph.getInstance();
        graphSearch = GraphSearchFactory.getGraphTraversalImplementation(GraphSearchAlgorithm.BFS);
    }

    public static EnrichedUserTransactionGraph getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Enriches the {@link UserTransactionGraph} by running BFS on each node and adding edges upto {@code MAX_DEGREE}.
     */
    public void enrichUserTransactionGraph() {
        for (Long userId : userTransactionGraph.getAllUserIDs()) {
            Map<Long, Integer> friendCircle = graphSearch.search(userTransactionGraph, userId, MAX_DEGREE);
            for (Map.Entry<Long, Integer> friend : friendCircle.entrySet()) {
                addVertex(userId);
                addVertex(friend.getKey());
                addEdge(userId, friend.getKey(), new Double(friend.getValue()));
            }
        }
    }

    /**
     * Check whether recipient is in sender's friend circle up to given max degree.
     *
     * @param transaction The user for which to fetch the friend circle.
     * @param maxDegree   The max degree of friendship to be considered.
     * @return {@code int} value indicating degree of friendship if recipient is in sender's friend circle, {@code Integer.MAX_VALUE} otherwise.
     */
    public int isInFriendCircle(Transaction transaction, int maxDegree) {
        Double degree = containsEdge(transaction.getSenderUID(), transaction.getRecipientUID());
        if (degree != null && degree.intValue() <= maxDegree) {
            return degree.intValue();
        }
        return Integer.MAX_VALUE;
    }

    @VisibleForTesting
    public void clear() {
        super.adjacencyList.clear();
    }

    private static class LazyHolder {
        private static final EnrichedUserTransactionGraph INSTANCE = new EnrichedUserTransactionGraph();
    }

}
