package utils.graphs;

import java.util.Set;

/**
 * Represents a graph data structure <b>without</b> edge labels.
 *
 * @param <V> data type of vertices
 */
public interface UnlabelledGraph<V> extends Graph<V> {
    /**
     * Add an edge to the graph.
     *
     * @param v1 from-vertex
     * @param v2 to-vertex
     * @return {@code boolean} value indicating whether the edge was successfully added to the graph or not.
     */
    boolean addEdge(V v1, V v2);

    /**
     * Determine whether the edge represented by the 2 input vertices is present in the graph or not.
     *
     * @param v1 source vertex
     * @param v2 destination vertex
     * @return {@code true} if edge exists in the graph, {@code false} otherwise.
     */
    boolean containsEdge(V v1, V v2);


    /**
     * Get directly connected neighbors of the given source vertex in the graph.
     *
     * @param v source vertex
     * @return set of vertices that are directly connected to the source vertex.
     */
    Set<V> getNeighbors(V v);
}
