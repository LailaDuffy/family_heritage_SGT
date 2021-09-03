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
                    for (Person eachPerson : familyTree.vertexSet()) {
                        if (eachPerson.getName().equalsIgnoreCase(inputChoice)) {
                            System.out.println("Here is the information about people called " + inputChoice + ": ");
                            System.out.println(eachPerson.toString());
                        } // need to add a statement in case of the name is not in the family tree
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

        Person person1 = new Person("Olivia", "Williams");
        Person person2 = new Person("Jack", "Williams");
        Person person3 = new Person("Thomas", "Williams");
        Person person4 = new Person("Sophia", "Williams");

        Graph<Person, RelationshipEdge> graph = new DefaultDirectedGraph<Person, RelationshipEdge>(RelationshipEdge.class);
        graph.addVertex(person1);
        graph.addVertex(person2);
        graph.addVertex(person3);
        graph.addVertex(person4);
        graph.addEdge(person1, person2, new RelationshipEdge(RelationshipEdge.spouse));
        graph.addEdge(person2, person1, new RelationshipEdge(RelationshipEdge.spouse));
        graph.addEdge(person1, person3, new RelationshipEdge(RelationshipEdge.parent));
        graph.addEdge(person3, person1, new RelationshipEdge(RelationshipEdge.child));
        graph.addEdge(person2, person3, new RelationshipEdge(RelationshipEdge.parent));
        graph.addEdge(person3, person2, new RelationshipEdge(RelationshipEdge.child));
        graph.addEdge(person1, person4, new RelationshipEdge(RelationshipEdge.parent));
        graph.addEdge(person4, person1, new RelationshipEdge(RelationshipEdge.child));
        graph.addEdge(person2, person3, new RelationshipEdge(RelationshipEdge.parent));
        graph.addEdge(person3, person2, new RelationshipEdge(RelationshipEdge.child));

        return graph;
    }

    static class RelationshipEdge extends DefaultEdge {

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


}


