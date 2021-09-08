import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        Graph<Person, RelationshipEdge> familyTree = familyTree();

        DBConnection(familyTree);


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
                    showFamilyTreeMembersList(familyTree);
                    break;

                case 2:
                    // Info about a person (name from user input)
                    printInfoAboutPerson(familyTree);
                    break;

                case 3:
                    // The oldest person in the list (Graph method)
                    Person oldestPerson = new Person();
                    for (Person eachPerson : familyTree.vertexSet()) {
                        Date birthDay = new Date();
                        Date oldestPersonBirthDay = new Date();
                        oldestPerson.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").format(oldestPersonBirthDay));

                        try {
                            birthDay = new SimpleDateFormat("dd/MM/yyyy").parse(eachPerson.getBirthDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (birthDay.before(oldestPersonBirthDay)) {
                            oldestPerson = eachPerson;
                        }
                    }
                    int age = oldestPerson.calculateAge();
                    System.out.println("The oldest person in the family tree is: " + oldestPerson.getName() + " " + oldestPerson.getSurname());
                    System.out.println("They are " + age + " years old");
                    System.out.println();
                    break;

                case 4:
                    // The youngest person in the list (Graph method)
                    System.out.println("The youngest person in the family tree is: ");
                    break;

                default:
                    System.out.println("Invalid choice. Please type a number from the given choices.");
            }
        } while (choice != 0);
    }

    static Graph familyTree() {

        PersonGraph graph = new PersonGraph(RelationshipEdge.class);

        return graph;
    }

    public static void DBConnection(Graph<Person, RelationshipEdge> familyTree) {

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Connected to database");

                Statement statement = connection.createStatement();
                String sqlStatement1 =
                        "CREATE TABLE IF NOT EXISTS persons" +
                                "(person_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "name TEXT NOT NULL," +
                                "surname TEXT NOT NULL," +
                                "gender TEXT NOT NULL," +
                                "birth_date TEXT NOT NULL," +
                                "death_date TEXT)";

                statement.execute(sqlStatement1);

                String sqlStatement2 =
                        "CREATE TABLE IF NOT EXISTS relationships" +
                                "(relationship_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "person_1 TEXT NOT NULL," +
                                "person_1_id INTEGER," +
                                "person_2 TEXT NOT NULL," +
                                "person_2_id INTEGER," +
                                "relationship_type TEXT NOT NULL," +
                                "FOREIGN KEY(person_1_id) REFERENCES persons(person_id)," +
                                "FOREIGN KEY(person_2_id) REFERENCES persons(person_id))";

                statement.execute(sqlStatement2);

                // getting all the persons from the database persons table
                String sqlStatement3 = "SELECT * FROM persons";
                ResultSet resultSet = statement.executeQuery(sqlStatement3);

                while (resultSet.next()) {
                    int person_id = resultSet.getInt("person_id");
                    String person_name = resultSet.getString("name");
                    String person_surname = resultSet.getString("surname");
                    String gender = resultSet.getString("gender");
                    String birth_date = resultSet.getString("birth_date");
                    String death_date = resultSet.getString("death_date");

                    Gender genderCastedToEnum = Gender.valueOf(gender);

                    // creating person objects
                    Person newPerson = new Person();
                    newPerson.setId(person_id);
                    newPerson.setName(person_name);
                    newPerson.setSurname(person_surname);
                    newPerson.setGender(genderCastedToEnum);
                    newPerson.setBirthDate(birth_date);
                    newPerson.setDeathDate(death_date);

                    // adding persons to graph
                    familyTree.addVertex(newPerson);

                    // adding persons to arraylist (to test if it works)
                    //arrayListOfPersons.add(newPerson);

                }

                // getting relatonship labels from the relationship database table
                String sqlStatementToGraph = "SELECT * FROM relationships";
                ResultSet rs = statement.executeQuery(sqlStatementToGraph);

                Person personSource = new Person();
                Person personTarget = new Person();
                while (rs.next()) {
                    RelationshipEdge edge = new RelationshipEdge(RelationshipLabels.valueOf(rs.getString("relationship_type")));

                    for (Person person1 : familyTree.vertexSet()) {
                        if (person1.getId() == rs.getInt("person_1_id")) {
                            personSource = person1;
                        }
                    }
                    for (Person person2 : familyTree.vertexSet()) {
                        if (person2.getId() == rs.getInt("person_2_id")) {
                            personTarget = person2;
                        }
                    }
                    // adding relationships to the graph
                    familyTree.addEdge(personSource, personTarget, edge);

                }

            }


        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }

    }

    public static void showFamilyTreeMembersList(Graph<Person, RelationshipEdge> familyTree) {
        System.out.println("Here is a list of every person in the Family Tree: ");
        for (Person eachPerson : familyTree.vertexSet()) {
            System.out.println(eachPerson.getName() + " " + eachPerson.getSurname() + " (born in " + eachPerson.getBirthDate() + ")");
        }
        System.out.println();
    }

    public static void printInfoAboutPerson(Graph<Person, RelationshipEdge> familyTree) {
        System.out.print("Type in a name of person: ");
        Scanner scan = new Scanner(System.in);
        String inputChoice = scan.nextLine();
        int counter = 0; // to check how many people are in the database with the input name
        System.out.println("Here is the information about people called " + inputChoice + ": ");
        // check if the input name can be found in the database
        for (Person eachPerson : familyTree.vertexSet()) {
            if (eachPerson.getName().equalsIgnoreCase(inputChoice)) {
                System.out.println(eachPerson.toString());
                counter++;

                // find all the relationships that the given person
                for (RelationshipEdge eachRelationship : familyTree.edgesOf(eachPerson)) {
                    if (eachPerson == familyTree.getEdgeSource(eachRelationship)) {
                        Person targetPerson = familyTree.getEdgeTarget(eachRelationship);
                        System.out.println(inputChoice + " is " + RelationshipLabels.valueOf(String.valueOf(eachRelationship.getLabel())) + " of " + targetPerson.getName() + " " + targetPerson.getSurname());
                    }
                }
            }
        }
        if (counter == 0) { // if no people with the input name can be found in the database
            System.out.println("A person with this name does not exist in the family tree");
        }
        System.out.println();
    }
}




