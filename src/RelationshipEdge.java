import org.jgrapht.graph.DefaultEdge;

public class RelationshipEdge extends DefaultEdge {

    private final RelationshipLabels label;

    public RelationshipEdge(RelationshipLabels label) {
        this.label = label;
    }

    public RelationshipLabels getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return String.valueOf(label);
    }

}
