package utils.graphs;

import utils.graphs.exceptions.VertexNotPresentException;
import utils.graphs.exceptions.VertexNullException;

/**
 * Provides a skeletal implementation of {@link Graph}. Implements functionality that is independent of the exact
 * implementation of the graph.
 * @param <V> data type of vertices
 */
public abstract class AbstractGraph<V> implements Graph<V> {
    /**
     * Asserts the presence of a vertex within a graph.
     * @param v vertex to be examined
     * @return {@code true} if vertex is present in the graph.
     * <ul>
     *     <li> throws {@link VertexNullException} if the vertex passed as an argument is {@code null}. </li>
     *     <li> throws {@link VertexNotPresentException} if the vertex passed as an argument is not present in the graph. </li>
     * </ul>
     */
    protected boolean assertVertexExists(V v) {
        if (v == null) {
            throw new VertexNullException();
        }
        if (!containsVertex(v)) {
            throw new VertexNotPresentException();
        }
        return true;
    }
}
