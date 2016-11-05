package utils.graphs.traversals;

import org.junit.Test;

import static org.junit.Assert.*;

public class GraphSearchFactoryTest {

    @Test
    public void testGetGraphTraversalImplementation() {
        assertTrue(GraphSearchFactory.getGraphTraversalImplementation(GraphSearchAlgorithm.BFS) instanceof BreadthFirstSearch);
    }

}