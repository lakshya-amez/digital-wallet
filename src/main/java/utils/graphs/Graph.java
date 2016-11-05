package utils.graphs;

import java.util.Set;

/**
 * Represents a graph data structure <b><without</b> edge labels.
 * @param <V> data type of vertices
 */
public interface Graph<V> {

    /**
     * Add a vertex to the graph.
     * @param v vertex to be added to the graph
     * @return {@code boolean} value indicating whether the vertex was successfully added to the graph or not.
     */
    boolean addVertex(V v);

    /**
     * Add an edge to the graph.
     * @param v1 from-vertex
     * @param v2 to-vertex
     * @return {@code boolean} value indicating whether the edge was successfully added to the graph or not.
     */
    boolean addEdge(V v1, V v2);

    /**
     * Determine whether vertex is present in the graph.
     * @param v vertex to be searched
     * @return {@code boolean} value indicating the presence of the vertex within the graph.
     */
    boolean containsVertex(V v);

    /**
     * Determine whether the edge represented by the 2 input vertices is present in the graph or not.
     * @param v1 source vertex
     * @param v2 destination vertex
     * @return {@code boolean} value indicating presence of edge in the graph.
     */
    boolean containsEdge(V v1, V v2);

    /**
     * Get directly connected neighbors of a given source vertex in the graph.
     * @param v source vertex
     * @return set of vertices that are directly connected the the source vertex.
     */
    Set<V> getNeighbors(V v);
}
