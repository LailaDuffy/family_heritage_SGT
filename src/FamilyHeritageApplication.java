import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import javax.management.relation.Relation;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.*;

public class FamilyHeritageApplication {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int choice;

        Graph<Person, RelationshipEdge> familyTree = testGraph();

        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Hello! Please choose your option: ");
            System.out.println("1. The list of persons");
            System.out.println("2. Info about a person (write name)");
            System.out.println("3. The oldest person in the list");
            System.out.println("4. The youngest person in the list");
            System.out.println("5. Add a person");
            System.out.println("6. Delete a person");
            System.out.println("0. - EXIT");

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            System.out.print("Please enter your choice: ");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Here is a list of every person in the Family Tree: ");
                    for (Person eachPerson : familyTree.vertexSet()) {
                        System.out.println(eachPerson.getName() + " " + eachPerson.getSurname());
                    }
                    System.out.println();
                    break;

                case 2:
                    System.out.print("Type in a name of person: ");
                    Scanner scan = new Scanner(System.in);
                    String inputChoice = scan.nextLine();
                    int counter = 0;
                    for (Person eachPerson : familyTree.vertexSet()) {
                        if (eachPerson.getName().equalsIgnoreCase(inputChoice)) {
                            System.out.println("Here is the information about people called " + inputChoice + ": ");
                            System.out.println(eachPerson.toString());
                            counter++;
                        }
                    } if (counter == 0) {
                    System.out.println("A person with this name does not exist in the family tree");
                }
                    System.out.println();
                    break;

                case 3:
                    System.out.println("");
                    break;

                case 4:
                    System.out.println("");
                    break;

                default:
                    System.out.println("Invalid choice. Please type a number from the given choices.");
            }
        } while (choice != 0);
    }

    static Graph testGraph() {

        PersonGraph graph = new PersonGraph(RelationshipEdge.class);
        graph.addPerson("Olivia", "Williams");
        graph.addPerson("Jack", "Williams");
        graph.addPerson("Thomas", "Williams");
        graph.addPerson("Sophia", "Williams");

        graph.addRelationshipEdge("Olivia", "Williams", "Jack", "Williams", new RelationshipEdge(EdgeNames.wife));
        graph.addRelationshipEdge("Jack", "Williams", "Olivia", "Williams", new RelationshipEdge(EdgeNames.husband));
        graph.addRelationshipEdge("Olivia", "Williams", "Thomas", "Williams", new RelationshipEdge(EdgeNames.mother));
        graph.addRelationshipEdge("Thomas", "Williams", "Olivia", "Williams", new RelationshipEdge(EdgeNames.son));
        graph.addRelationshipEdge("Jack", "Williams", "Thomas", "Williams", new RelationshipEdge(EdgeNames.father));
        graph.addRelationshipEdge("Thomas", "Williams", "Jack", "Williams", new RelationshipEdge(EdgeNames.son));
        graph.addRelationshipEdge("Olivia", "Williams", "Sophia", "Williams", new RelationshipEdge(EdgeNames.mother));
        graph.addRelationshipEdge("Sophia", "Williams", "Olivia", "Williams", new RelationshipEdge(EdgeNames.daughter));
        graph.addRelationshipEdge("Sophia", "Williams", "Jack", "Williams", new RelationshipEdge(EdgeNames.daughter));
        graph.addRelationshipEdge("Jack", "Williams", "Sophia", "Williams", new RelationshipEdge(EdgeNames.father));
        graph.addRelationshipEdge("Sophia", "Williams", "Thomas", "Williams", new RelationshipEdge(EdgeNames.sister));
        graph.addRelationshipEdge("Thomas", "Williams", "Sophia", "Williams", new RelationshipEdge(EdgeNames.brother));

        return graph;
    }

    static class EdgeNames extends DefaultEdge {

        private final String label;

        private static final RelationshipLabels mother = RelationshipLabels.mother;
        private static final RelationshipLabels father = RelationshipLabels.father;
        private static final RelationshipLabels grandmother = RelationshipLabels.grandmother;
        private static final RelationshipLabels grandfather = RelationshipLabels.grandfather;
        private static final RelationshipLabels husband = RelationshipLabels.husband;
        private static final RelationshipLabels wife = RelationshipLabels.wife;
        private static final RelationshipLabels son = RelationshipLabels.son;
        private static final RelationshipLabels daughter = RelationshipLabels.daughter;
        private static final RelationshipLabels sister = RelationshipLabels.sister;
        private static final RelationshipLabels brother = RelationshipLabels.brother;
        private static final RelationshipLabels grandson = RelationshipLabels.grandson;
        private static final RelationshipLabels granddaughter = RelationshipLabels.granddaughter;
        private static final RelationshipLabels cousin = RelationshipLabels.cousin;
        private static final RelationshipLabels uncle = RelationshipLabels.uncle;
        private static final RelationshipLabels aunt = RelationshipLabels.aunt;
        private static final RelationshipLabels nephew = RelationshipLabels.nephew;
        private static final RelationshipLabels niece = RelationshipLabels.niece;

        public EdgeNames(String label) {
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


}


