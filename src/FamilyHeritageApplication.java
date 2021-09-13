import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import java.util.Date;

public class FamilyHeritageApplication {


    public static void main(String[] args) throws ParseException {

        Scanner in = new Scanner(System.in);
        int choice;

        Graph<Person, RelationshipEdge> familyTree = PersonGraph.familyTree();
        Database.DBConnection(familyTree);

        // The Main Menu
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello and welcome to the Family Heritage Application! ");
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Please choose your option from the Menu below: ");
            System.out.println();
            System.out.println("1. The list of all people in the Family Tree.");
            System.out.println("2. Info about a person (write name).");
            System.out.println("3. The oldest person in the Family Tree.");
            System.out.println("4. The youngest person in the Family Tree.");
            System.out.println("5. Find out who have birthdays in chosen month.");
            System.out.println("6. Add a person to the Family Tree.");
            System.out.println("7. Add relationships between persons in the Family Tree.");
            System.out.println("8. Update a person in the Family Tree.");
            System.out.println("9. Remove a person from the Family Tree.");

            System.out.println("0. - EXIT");
            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            System.out.print("Please enter the number: ");
            choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    // List of persons
                    PersonGraph.showFamilyTreeMembersList(familyTree);
                    System.out.println();
                    break;

                case 2:
                    // Info about a person (name from user input)
                    Person.printInfoAboutPerson(familyTree);
                    System.out.println();
                    break;

                case 3:
                    // The oldest person in the family tree
                    Person.calculateOldestPerson();
                    System.out.println();
                    break;

                case 4:
                    // The youngest person in the family tree
                    Person.calculateYoungestPerson();
                    System.out.println();
                    break;

                case 5:
                    // Find out birthdays based on month
                    Person.peopleBornInTheSameMonth(familyTree);
                    System.out.println();
                    break;

                case 6:
                    // Add a person to the Family Tree
                    Person.addPerson(familyTree);
                    System.out.println();
                    break;

                case 7:
                    // Add relationships to persons in the Family Tree
                    Person.addRelationships(familyTree);
                    System.out.println();
                    break;

                case 8:
                    // Update a person in the Family Tree
                    Person.updatePerson(in, familyTree);
                    System.out.println();
                    break;

                case 9:
                    // Remove a person from the Family Tree
                    Person.removePerson(in, familyTree);
                    System.out.println();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from the given Menu.");
            }
        } while (choice != 0);
    }
}

