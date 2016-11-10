package utils.graphs;

/**
 * Represents a graph data structure.
 *
 * @param <V> data type of vertices
 */
public interface Graph<V> {

    /**
     * Add a vertex to the graph.
     *
     * @param v vertex to be added to the graph
     * @return {@code boolean} value indicating whether the vertex was successfully added to the graph or not.
     */
    boolean addVertex(V v);

    /**
     * Determine whether vertex is present in the graph.
     *
     * @param v vertex to be searched
     * @return {@code boolean} value indicating the presence of the vertex within the graph.
     */
    boolean containsVertex(V v);
}
