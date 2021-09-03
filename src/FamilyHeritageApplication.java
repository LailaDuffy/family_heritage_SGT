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
                    // List of persons
                    System.out.println("Here is a list of every person in the Family Tree: ");
                    for (Person eachPerson : familyTree.vertexSet()) {
                        System.out.println(eachPerson.getName() + " " + eachPerson.getSurname());
                    }
                    System.out.println();
                    break;

                case 2:
                    // Info about a person (name from user input)
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
                    // The oldest person in the list (Graph method)

                    System.out.println("The oldest person in the family tree is: ");
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

        graph.addRelationshipEdge("Olivia", "Williams", "Jack", "Williams", RelationshipLabels.wife);
        graph.addRelationshipEdge("Jack", "Williams", "Olivia", "Williams", RelationshipLabels.husband);
        graph.addRelationshipEdge("Olivia", "Williams", "Thomas", "Williams", RelationshipLabels.mother);
        graph.addRelationshipEdge("Thomas", "Williams", "Olivia", "Williams", RelationshipLabels.son);
        graph.addRelationshipEdge("Jack", "Williams", "Thomas", "Williams", RelationshipLabels.father);
        graph.addRelationshipEdge("Thomas", "Williams", "Jack", "Williams", RelationshipLabels.son);
        graph.addRelationshipEdge("Olivia", "Williams", "Sophia", "Williams", RelationshipLabels.mother);
        graph.addRelationshipEdge("Sophia", "Williams", "Olivia", "Williams", RelationshipLabels.daughter);
        graph.addRelationshipEdge("Sophia", "Williams", "Jack", "Williams", RelationshipLabels.daughter);
        graph.addRelationshipEdge("Jack", "Williams", "Sophia", "Williams", RelationshipLabels.father);
        graph.addRelationshipEdge("Sophia", "Williams", "Thomas", "Williams", RelationshipLabels.sister);
        graph.addRelationshipEdge("Thomas", "Williams", "Sophia", "Williams", RelationshipLabels.brother);

        return graph;
    }

}


