package utils.graphs.traversals;

import org.junit.Before;
import org.junit.Test;
import utils.graphs.UndirectedGraph;
import utils.graphs.UnlabelledGraph;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BreadthFirstSearchTest {

    private UnlabelledGraph<Integer> graph;
    private GraphSearch<Integer> searchAlgorithm;

    @Before
    public void setUp() throws Exception {
        graph = new UndirectedGraph<>();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);
        graph.addEdge(1, 1);
        graph.addEdge(2, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 9);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 7);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 6);
        graph.addEdge(7, 8);

        searchAlgorithm = GraphSearchFactory.getGraphTraversalImplementation(GraphSearchAlgorithm.BFS);
    }

    @Test
    public void testSearchDestination() {
        assertEquals(3, searchAlgorithm.search(graph, 0, 5, 4));
        assertEquals(Integer.MAX_VALUE, searchAlgorithm.search(graph, 0, 8, 4));
    }

    @Test
    public void testSearchReachable() {
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(0, 0);
        expected.put(1, 1);
        expected.put(2, 1);
        expected.put(3, 2);
        expected.put(9, 2);
        expected.put(4, 3);
        expected.put(5, 3);
        expected.put(7, 4);
        expected.put(6, 4);
        assertEquals(expected, searchAlgorithm.search(graph, 0, 4));
    }

}