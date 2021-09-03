import org.jgrapht.graph.DefaultEdge;

import java.util.List;

class RelationshipEdge extends DefaultEdge {

    private final String label;

    public RelationshipEdge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Relationships{" +
                "label='" + label + '\'' +
                '}';
    }

}
