import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.sql.*;
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
        ArrayList<Person> arrayListOfPersons = new ArrayList<>();
        ArrayList<Person> newPersonArrayList = new ArrayList<>();

        String databasePath = "jdbc:sqlite:family_heritage.db";

        try {
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

                    //adding persons to graph
                    testGraph().addVertex(newPerson);

                    //adding persons to arraylist (to test if it works)
                    arrayListOfPersons.add(newPerson);


                    //for (Person eachPerson : arrayListOfPersons) {
                    //int id = eachPerson.getId();
                    //String sqlStatement4 = "SELECT * FROM relationships WHERE person_1_id = " + id +  "
                    String query = "SELECT * FROM relationships";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet rs = preparedStatement.executeQuery(query);
                    Person personSource = null;
                    Person personTarget = null;
                    while (rs.next()) {
                        RelationshipEdge edge = new RelationshipEdge(RelationshipLabels.valueOf(rs.getString("relationship_type")));
                        for (Person person1 : arrayListOfPersons) {
                            if (person1.getId() == rs.getInt("person_1_id")) {
                                personSource = person1;
                            }
                            for (Person person2 : arrayListOfPersons) {
                                if (person2.getId() == rs.getInt("person_2_id")) {
                                    personTarget = person2;
                                }
                            }

                        }
                        familyTree.addEdge(personSource, personTarget, edge);

                        newPersonArrayList.add(personSource);
                        newPersonArrayList.add(personTarget);
                    }


                }
            }


        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }


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
//                    System.out.println("Here is a list of every person in the Family Tree: ");
//                    for (Person eachPerson : familyTree.vertexSet()) {
//                        System.out.println(eachPerson);
//                    }
                    //System.out.println(arrayListOfPersons.toString());
                    System.out.println(newPersonArrayList);
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
                    }
                    if (counter == 0) {
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

        return graph;
    }

}


