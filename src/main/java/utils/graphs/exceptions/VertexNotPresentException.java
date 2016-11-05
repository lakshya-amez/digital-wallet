package utils.graphs.exceptions;

/**
 * {@link Exception} thrown when the <b>vertex</b> passed as an argument is <b>not present in the graph</b>.
 */
public class VertexNotPresentException extends IllegalArgumentException {
    public VertexNotPresentException() {
        super("Vertex not present in graph!");
    }
}
