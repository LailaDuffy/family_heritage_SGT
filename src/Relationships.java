import org.jgrapht.graph.DefaultEdge;

class RelationshipEdge extends DefaultEdge {

    private final String label;

    private static final String parent = "is parent of";
    private static final String grandparent = "is grandparent of";
    private static final String spouse = "is spouse of";
    private static final String child = "is child of";
    private static final String sibling = "is sibling of";
    private static final String cousin = "is cousin of";
    private static final String grandchild = "is grandchild of";
    private static final String aunt = "is aunt of";
    private static final String uncle = "is uncle of";
    private static final String niece = "is niece of";
    private static final String nephew = "is nephew of";

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
