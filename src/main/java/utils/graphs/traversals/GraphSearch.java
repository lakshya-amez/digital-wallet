package utils.graphs.traversals;

import utils.graphs.Graph;

/**
 * Represents a strategy for graph search.
 * @param <V> data type of vertex.
 */
public interface GraphSearch<V> {
    /**
     * Traverses the graph starting from the root to find the destination, up to a given max distance from the root.
     * @param graph {@link Graph} object to be traversed
     * @param source vertex from which graph traversal starts
     *  @param destination vertex to search for
     * @param maxDist maximum distance to be considered from the source vertex
     * @return {@code int} value indicating distance of destination vertex from source vertex if
     * found within given max distance, {@code Integer.MAX_VALUE} otherwise.
     */
    int search(Graph<V> graph, V source, V destination, int maxDist);
}
