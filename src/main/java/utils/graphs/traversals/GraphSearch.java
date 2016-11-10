package utils.graphs.traversals;

import utils.graphs.UnlabelledGraph;

import java.util.Map;

/**
 * Represents a strategy for graph search.
 *
 * @param <V> data type of vertex.
 */
public interface GraphSearch<V> {
    /**
     * Traverses the graph starting from the source vertex to find the destination vertex, up to a given max distance
     * from the source vertex.
     *
     * @param graph       {@link UnlabelledGraph} object to be traversed
     * @param source      vertex from which graph traversal starts
     * @param destination vertex to search for
     * @param maxDist     maximum distance to be considered from the source vertex
     * @return {@code int} value indicating distance of destination vertex from source vertex if
     * found within given max distance, {@code Integer.MAX_VALUE} otherwise.
     */
    int search(UnlabelledGraph<V> graph, V source, V destination, int maxDist);

    /**
     * Traverses the graph starting from the source vertex to find all reachable vertices, up to a given max distance
     * from the source vertex.
     *
     * @param graph   {@link UnlabelledGraph} object to be traversed
     * @param source  vertex from which graph traversal starts
     * @param maxDist maximum distance to be considered from the source vertex
     * @return Map of reachable vertices and their corresponding distances from the source vertex.
     */
    Map<V, Integer> search(UnlabelledGraph<V> graph, V source, int maxDist);
}
