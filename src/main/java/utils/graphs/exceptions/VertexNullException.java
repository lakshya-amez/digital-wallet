package utils.graphs.exceptions;

/**
 * {@link Exception} thrown when the <b>vertex</b> passed as an argument is {@code null}.
 */
public class VertexNullException extends NullPointerException {
    public VertexNullException() {
        super("Vertex is null!");
    }
}
