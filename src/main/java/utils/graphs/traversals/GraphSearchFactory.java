package utils.graphs.traversals;

/**
 * Factory for obtaining a {@link GraphSearch} implementation for a given {@link GraphSearchAlgorithm}.
 */
public class GraphSearchFactory {
    public static <V> GraphSearch<V> getGraphTraversalImplementation(GraphSearchAlgorithm algorithm) {
        switch (algorithm) {
            case BFS:
                return new BreadthFirstSearch<>();
        }
        throw new UnsupportedOperationException("Unrecognized algorithm for graph traversal");
    }
}
