package utils.graphs;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import utils.graphs.exceptions.VertexNotPresentException;
import utils.graphs.exceptions.VertexNullException;

import java.util.*;

import static org.junit.Assert.*;

public class UndirectedGraphTest {

    private UndirectedGraph<Integer> graph;

    @Before
    public void setUp() throws Exception {
        graph = new UndirectedGraph<>();
    }

    @Test(expected = VertexNullException.class)
    public void testAddNullVertex() {
        graph.addVertex(null);
    }

    @Test
    public void testAddNewVertex() {
        assertTrue(graph.addVertex(0));
    }

    @Test
    public void testAddDuplicateVertex() {
        graph.addVertex(0);
        assertFalse(graph.addVertex(0));
    }

    @Test(expected = VertexNullException.class)
    public void addEdgeNullVertex() {
        graph.addEdge(null, 0);
    }

    @Test(expected = VertexNotPresentException.class)
    public void addEdgeAbsentVertex() {
        graph.addEdge(0, 1);
    }

    @Test
    public void addEdgeValidCase() {
        graph.addVertex(0);
        graph.addVertex(1);
        assertTrue(graph.addEdge(0, 1));
    }

    @Test
    public void containsVertex() {
        graph.addVertex(0);
        assertTrue(graph.containsVertex(0));
        assertFalse(graph.containsVertex(1));
        assertFalse(graph.containsVertex(null));
    }

    @Test
    public void getNeighbors() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        Set<Integer> expected = ImmutableSet.of(1, 2);
        assertEquals(expected, graph.getNeighbors(0));
    }

    @Test(expected = VertexNullException.class)
    public void getNeighborsNullVertex() {
        graph.getNeighbors(null);
    }

    @Test(expected = VertexNotPresentException.class)
    public void getNeighborsAbsentVertex() {
        graph.getNeighbors(0);
    }

    @Test(expected = VertexNullException.class)
    public void checkVertexExistsNullVertex() {
        graph.assertVertexExists(null);
    }

    @Test(expected = VertexNotPresentException.class)
    public void checkVertexExistsAbsentVertex() {
        graph.assertVertexExists(0);
    }

    @Test
    public void checkVertexExistsPresentVertex() {
        graph.addVertex(0);
        assertTrue(graph.assertVertexExists(0));
    }

}