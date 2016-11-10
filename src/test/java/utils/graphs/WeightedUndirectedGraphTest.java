package utils.graphs;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import utils.graphs.exceptions.VertexNotPresentException;
import utils.graphs.exceptions.VertexNullException;

import java.util.*;

import static org.junit.Assert.*;

public class WeightedUndirectedGraphTest {

    private WeightedUndirectedGraph<Integer> graph;

    @Before
    public void setUp() throws Exception {
        graph = new WeightedUndirectedGraph<>();
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
    public void testAddEdgeNullVertex() {
        graph.addEdge(null, 0, 1.0);
    }

    @Test(expected = VertexNotPresentException.class)
    public void testAddEdgeAbsentVertex() {
        graph.addEdge(0, 1, 1.0);
    }

    @Test
    public void testAddEdgeValidCase() {
        graph.addVertex(0);
        graph.addVertex(1);
        assertTrue(graph.addEdge(0, 1, 1.0));
    }

    @Test
    public void testContainsVertex() {
        graph.addVertex(0);
        assertTrue(graph.containsVertex(0));
        assertFalse(graph.containsVertex(1));
        assertFalse(graph.containsVertex(null));
    }

    @Test
    public void testContainsEdge() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1, 1.0);
        assertNull(graph.containsEdge(0, 2));
        assertNull(graph.containsEdge(2, 0));
        assertEquals(1.0, graph.containsEdge(1, 0), 1e-6);
        assertEquals(1.0, graph.containsEdge(0, 1), 1e-6);
    }

    @Test
    public void testGetNeighbors() {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(0, 1, 1.0);
        graph.addEdge(0, 2, 2.0);

        Map<Integer, Double> expected = ImmutableMap.of(1, 1.0, 2, 2.0);
        assertEquals(expected, graph.getNeighbors(0));
    }

    @Test(expected = VertexNullException.class)
    public void testGetNeighborsNullVertex() {
        graph.getNeighbors(null);
    }

    @Test(expected = VertexNotPresentException.class)
    public void testGetNeighborsAbsentVertex() {
        graph.getNeighbors(0);
    }

    @Test(expected = VertexNullException.class)
    public void testCheckVertexExistsNullVertex() {
        graph.assertVertexExists(null);
    }

    @Test(expected = VertexNotPresentException.class)
    public void testCheckVertexExistsAbsentVertex() {
        graph.assertVertexExists(0);
    }

    @Test
    public void testCheckVertexExistsPresentVertex() {
        graph.addVertex(0);
        assertTrue(graph.assertVertexExists(0));
    }

}