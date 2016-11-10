package utils.graphs.traversals;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utils.graphs.UnlabelledGraph;
import utils.graphs.exceptions.VertexNullException;

import java.util.*;

/**
 * Breadth First Search implementation of {@link GraphSearch}.
 * @param <V> data type of vertices
 */
public class BreadthFirstSearch<V> implements GraphSearch<V> {

    /**
     * Performs BFS from the source vertex up to a max distance of {@code maxDist} to try and reach destination vertex.
     */
    @Override
    public int search(UnlabelledGraph<V> graph, V source, V destination, int maxDist) {

        if (source == null || destination == null) {
            throw new VertexNullException();
        }

        if (!graph.containsVertex(source) || !graph.containsVertex(destination)) {
            return Integer.MAX_VALUE;
        }

        if (source.equals(destination)) {
            return 0;
        }

        if (graph.containsEdge(source, destination)) {
            return 1;
        }

        Queue<Pair<V, Integer>> queue = new ArrayDeque<>();
        Set<V> visited = new HashSet<>();

        queue.offer(new ImmutablePair<>(source, 0));
        visited.add(source);

        while (!queue.isEmpty()) {
            Pair<V, Integer> pair = queue.poll();
            V vertex = pair.getLeft();
            int dist = pair.getRight();

            int newDist = dist + 1;
            if (newDist > maxDist) {
                continue;
            }

            Set<V> neighbors = graph.getNeighbors(vertex);
            for (V neighbor : neighbors) {
                if (neighbor.equals(destination)) {
                    return newDist;
                }
                if (!visited.contains(neighbor)) {
                    queue.offer(new ImmutablePair<>(neighbor, newDist));
                    visited.add(neighbor);
                }
            }

        }

        return Integer.MAX_VALUE;
    }

    @Override
    public Map<V, Integer> search(UnlabelledGraph<V> graph, V source, int maxDist) {
        if (source == null) {
            throw new VertexNullException();
        }

        if (!graph.containsVertex(source)) {
            return Collections.EMPTY_MAP;
        }

        Queue<Pair<V, Integer>> queue = new ArrayDeque<>();
        Set<V> visited = new HashSet<>();
        Map<V, Integer> reachableVertices = new HashMap<>();

        queue.offer(new ImmutablePair<>(source, 0));
        visited.add(source);

        while (!queue.isEmpty()) {
            Pair<V, Integer> pair = queue.poll();
            V vertex = pair.getLeft();
            int dist = pair.getRight();
            reachableVertices.put(vertex, dist);

            int newDist = dist + 1;
            if (newDist > maxDist) {
                continue;
            }

            Set<V> neighbors = graph.getNeighbors(vertex);
            for (V neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    queue.offer(new ImmutablePair<>(neighbor, newDist));
                    visited.add(neighbor);
                }
            }

        }

        return reachableVertices;
    }

}
