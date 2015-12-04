package com.epam.zhukovsd.graphs.dual;

import com.epam.zhukovsd.graphs.Vertex;
import com.epam.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.epam.zhukovsd.graphs.embedded.EmbeddedVertex;
import com.epam.zhukovsd.graphs.embedded.Face;

/**
 * Vertex of dual graph.
 * @param <T> self-referential type used as type parameter inner fields
 */
public class DualVertex<T extends EmbeddedVertex<T>> extends Vertex<DualVertex<T>> {
    /**
     * Corresponding {@link Face face} of outer {@link EmbeddedGraph embedded graph}.
     */
    public final Face<T> face;

    /**
     * Create dual graph vertex inside given {@link Face face}.
     * @param face given face
     */
    public DualVertex(Face<T> face) {
        this.face = face;
    }
}
