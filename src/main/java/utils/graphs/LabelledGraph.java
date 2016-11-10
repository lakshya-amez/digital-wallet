package utils.graphs;

import java.util.Map;

/**
 * Represents a graph data structure <b><with</b> edge labels.
 * @param <V> data type of vertices
 * @param <E> data type of edges
 */
public interface LabelledGraph<V, E> {

    /**
     * Add an edge to the graph.
     * @param v1 from-vertex
     * @param v2 to-vertex
     * @param e edge label
     * @return {@code boolean} value indicating whether the edge was successfully added to the graph or not.
     */
    boolean addEdge(V v1, V v2, E e);

    /**
     * Determine whether the edge represented by the 2 input vertices is present in the graph or not.
     * @param v1 source vertex
     * @param v2 destination vertex
     * @return {@code E} edge label if edge exists in the graph, {@code null} otherwise.
     */
    E containsEdge(V v1, V v2);

    /**
     * Get directly connected neighbors of a given source vertex in the graph.
     * @param v source vertex
     * @return map of vertices that are directly connected the the source vertex along with their edge labels.
     */
    Map<V, E> getNeighbors(V v);
}
