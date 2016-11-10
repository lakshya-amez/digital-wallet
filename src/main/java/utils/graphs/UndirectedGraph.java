package utils.graphs;

import utils.graphs.exceptions.VertexNullException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An undirected graph implementation of {@link Graph}. Stores the graph in adjacency list form as: <br/>
 * <b><i> V<sub>source</sub> --> V<sub>destination</sub></i></b> <br/>
 * The vertices passed as arguments may not be {@code null}.
 *
 * @param <V> data type of vertices
 */
public class UndirectedGraph<V> extends AbstractGraph<V> implements UnlabelledGraph<V> {

    protected Map<V, Set<V>> adjacencyList;

    public UndirectedGraph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param v vertex to be added to the graph
     * @return <ul>
     * <li> {@code true} if the vertex was not found in the graph and was added as a new vertex to the graph. </li>
     * <li> {@code false} if the vertex was already found in the graph. </li>
     * <li> throws {@link VertexNullException} if the vertex passed was {@code null}. </li>
     * </ul>
     * It is O(1) operation.
     */
    @Override
    public boolean addVertex(V v) {
        if (v == null) {
            throw new VertexNullException();
        }
        if (adjacencyList.containsKey(v)) {
            return false;
        }
        adjacencyList.put(v, new HashSet<>());

        return true;
    }

    /**
     * Adds an edge to the graph. Asserts that both the vertices are present in the graph.
     *
     * @param v1 from-vertex
     * @param v2 to-vertex
     * @return {@code true} if the edge was successfully added to the graph.
     * It is O(1) operation.
     */
    @Override
    public boolean addEdge(V v1, V v2) {
        assertVertexExists(v1);
        assertVertexExists(v2);
        adjacencyList.get(v1).add(v2);
        adjacencyList.get(v2).add(v1);

        return true;
    }

    /**
     * Determine whether vertex is present in the graph.
     *
     * @param v vertex to be searched
     * @return {@code true}, if vertex is present in the graph, {@code false} otherwise.
     * It is O(1) operation.
     */
    @Override
    public boolean containsVertex(V v) {
        return adjacencyList.containsKey(v);
    }

    /**
     * Determine whether edge is present in the graph.
     *
     * @param v1 source vertex
     * @param v2 destination vertex
     * @return {@code true}, if edge is present in the graph, {@code false} otherwise.
     * It is O(1) operation.
     */
    @Override
    public boolean containsEdge(V v1, V v2) {
        return containsVertex(v1) && adjacencyList.get(v1).contains(v2);
    }

    /**
     * Get directly connected neighbors of a given source vertex in the graph. Asserts the presence of the vertex in the graph.
     *
     * @param v source vertex
     * @return set of vertices that are directly connected the the source vertex.
     */
    @Override
    public Set<V> getNeighbors(V v) {
        assertVertexExists(v);
        return adjacencyList.get(v);
    }

}
