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

        Graph<Person, RelationshipEdge> familyTree = familyTree();
        DBConnection(familyTree);

        // The Main Menu
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello and welcome to the Family Heritage Application! ");
        do {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Please choose your option from the Menu below: ");
            System.out.println();
            System.out.println("1. The list of all people in the Family Tree");
            System.out.println("2. Info about a person (write name)");
            System.out.println("3. The oldest person in the Family Tree");
            System.out.println("4. The youngest person in the Family Tree");
            System.out.println("5. Add a person to the Family Tree");
            System.out.println("6. Add relationships between persons in the Family Tree");
            System.out.println("7. Update a person in the Family Tree");
            System.out.println("8. Remove a person from the Family Tree");
            System.out.println("9. Find out who have birthdays in chosen month");
            System.out.println("0. - EXIT");
            System.out.println();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

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
                    // The oldest person in the family tree
                    calculateOldestPerson();
                    System.out.println();
                    break;

                case 4:
                    // The youngest person in the family tree
                    calculateYoungestPerson();
                    System.out.println();
                    break;

                case 5:
                    // Add a person to the Family Tree
                    addPerson(familyTree);
                    System.out.println();
                    break;

                case 6:
                    // Add relationships to persons in the Family Tree
                    addRelationships();
                    System.out.println();
                    break;
                case 7:
                    // Update a person in the Family Tree
                    System.out.println("Input the name of the person you wish to update: ");
                    String personToUpdateName = in.next();
                    System.out.println("Input the surname of the person you wish to update: ");
                    String personToUpdateSurname = in.next();

                    try {
                        String databasePath = "jdbc:sqlite:family_heritage.db";
                        Connection connection = DriverManager.getConnection(databasePath);

                        if (connection != null) {
                            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();

                            PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ?");
                            prpStatement.setString(1, personToUpdateName);
                            prpStatement.setString(2, personToUpdateSurname);
                            ResultSet rs = prpStatement.executeQuery();


                            while (rs.next()) {
                                String name = rs.getString("name");
                                String surname = rs.getString("surname");
                                String gender = rs.getString("gender");
                                String birthDate = rs.getString("birth_date");
                                String deathDate = rs.getString("death_date");

                                System.out.println(name + " " + surname + " (" + gender + ") " + " (birth date: "
                                        + birthDate + ", death date: " + deathDate + ")");
                            }

                            System.out.println("Which part of information do you want to update: ");
                            System.out.println("1. Name   2. Surname   3. Gender   4. Birth date   5. Death date");
                            System.out.println("Please input the corresponding number: ");
                            int input = in.nextInt();

                            switch (input) {
                                case 1:
                                    System.out.println("Please input the new name of the person: ");
                                    String newName = in.nextLine();
                                    in.nextLine();

                                    PreparedStatement prpStatement1 = connection.prepareStatement("UPDATE persons " +
                                            "SET name = ? WHERE name = ? AND surname = ?");
                                    prpStatement1.setString(1, newName);
                                    prpStatement1.setString(2, personToUpdateName);
                                    prpStatement1.setString(3, personToUpdateSurname);
                                    prpStatement1.execute();

                                    break;

                                case 2:
                                    System.out.println("Please input the new surname of the person: ");
                                    String newSurname = in.nextLine();
                                    in.nextLine();

                                    PreparedStatement prpStatement2 = connection.prepareStatement("UPDATE persons " +
                                            "SET name = ? WHERE name = ? AND surname = ?");
                                    prpStatement2.setString(1, newSurname);
                                    prpStatement2.setString(2, personToUpdateName);
                                    prpStatement2.setString(3, personToUpdateSurname);
                                    prpStatement2.execute();

                                    break;

                                case 3:
                                    System.out.println("Please input the new gender of the person: ");
                                    String newGender = in.nextLine();
                                    in.nextLine();

                                    PreparedStatement prpStatement3 = connection.prepareStatement("UPDATE persons " +
                                            "SET name = ? WHERE name = ? AND surname = ?");
                                    prpStatement3.setString(1, newGender);
                                    prpStatement3.setString(2, personToUpdateName);
                                    prpStatement3.setString(3, personToUpdateSurname);
                                    prpStatement3.execute();

                                    break;

                                case 4:
                                    System.out.println("Please input the new birth date of the person DD/MM/YYYY: ");
                                    String newBirthDate = in.nextLine();
                                    in.nextLine();

                                    PreparedStatement prpStatement4 = connection.prepareStatement("UPDATE persons " +
                                            "SET name = ? WHERE name = ? AND surname = ?");
                                    prpStatement4.setString(1, newBirthDate);
                                    prpStatement4.setString(2, personToUpdateName);
                                    prpStatement4.setString(3, personToUpdateSurname);
                                    prpStatement4.execute();

                                    break;

                                case 5:
                                    System.out.println("Please input the death date of the person DD/MM/YYYY: ");
                                    String newDeathDate = in.nextLine();
                                    in.nextLine();

                                    PreparedStatement prpStatement5 = connection.prepareStatement("UPDATE persons " +
                                            "SET name = ? WHERE name = ? AND surname = ?");
                                    prpStatement5.setString(1, newDeathDate);
                                    prpStatement5.setString(2, personToUpdateName);
                                    prpStatement5.setString(3, personToUpdateSurname);
                                    prpStatement5.execute();

                                    break;

                                default:
                                    System.out.println("Invalid number. Please type a number between 1-5");

                            }

                        }
                    } catch (SQLException exception) {
                        System.out.println("Error: " + exception);
                    }


                    System.out.println();
                    break;

                case 8:
                    // Remove a person from the Family Tree

                    // asking the user for name and surname of person to delete
                    System.out.println("Enter the name of person you want to delete: ");
                    String nameToDelete = in.next();
                    System.out.println("Enter the surname of person you want to delete: ");
                    String surnameToDelete = in.next();

                    try {
                        String databasePath = "jdbc:sqlite:family_heritage.db";
                        Connection connection = DriverManager.getConnection(databasePath);

                        if (connection != null) {
                            DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();

                            int personID = 0;
                            int idToDelete = 0;
                            // finding the person in database by name and surname
                            PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons " +
                                    "WHERE name = ? AND surname = ?");
                            prpStatement.setString(1, nameToDelete);
                            prpStatement.setString(2, surnameToDelete);
                            ResultSet rs = prpStatement.executeQuery();

                            // getting and printing person's information
                            int counter = 0;
                            System.out.println("Person(s) found: ");
                            while (rs.next()) {
                                String name = rs.getString("name");
                                String surname = rs.getString("surname");
                                String gender = rs.getString("gender");
                                String birthDate = rs.getString("birth_date");
                                String deathDate = rs.getString("death_date");
                                personID = rs.getInt("person_id");

                                System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                        + birthDate + ", death date: " + deathDate + ")");

                                counter++;
                            }

                            // if there is only one person (counter == 1)
                            if (counter == 1) {
                                // upon confirmation set the id to be deleted
                                System.out.println("Is this the person you want to remove from the family tree?");
                                System.out.println("Please choose betweeen numbers: 1. Yes | 2. No");
                                System.out.println("Enter the number: ");
                                int input = in.nextInt();

                                if (input == 1) {
                                    idToDelete = personID;
                                } else {
                                    System.out.println("This person won't be removed");
                                }

                                // if there is more than one person (counter > 1)
                            } else if (counter > 1) {
                                System.out.println("There were multiple persons found with this name, please enter " +
                                        "the date of birth for person you wish to delete (DD/MM/YYYY): ");
                                String dateToDelete = in.next();

                                // finding the person bu name, surname, birth date
                                PreparedStatement prpStatement4 = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ? AND birth_date = ?");
                                prpStatement4.setString(1, nameToDelete);
                                prpStatement4.setString(2, surnameToDelete);
                                prpStatement4.setString(3, dateToDelete);
                                ResultSet rs2 = prpStatement4.executeQuery();

                                // getting and printing person data for this person
                                while (rs2.next()) {
                                    String name = rs2.getString("name");
                                    String surname = rs2.getString("surname");
                                    String gender = rs2.getString("gender");
                                    String birthDate = rs2.getString("birth_date");
                                    String deathDate = rs2.getString("death_date");
                                    personID = rs2.getInt("person_id");

                                    // upon confirmation set id to be deleted
                                    System.out.println("Is this the person you wish to remove from the family tree?");
                                    System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                            + birthDate + ", death date: " + deathDate + ")");
                                    System.out.println();
                                    System.out.println("Enter the number: ");
                                    System.out.println("1. Yes / 2. No");
                                    int input = in.nextInt();

                                    if (input == 1) {
                                        idToDelete = personID;
                                    } else {
                                        System.out.println("This person won't be removed");
                                    }
                                }

                            } else {    // if counter still 0, person was not found
                                System.out.println("This person was not found in the family tree");
                            }

                            // removing the person from persons table based on id
                            PreparedStatement prpStatement2 = connection.prepareStatement("DELETE FROM persons WHERE person_id = ?");
                            prpStatement2.setInt(1, idToDelete);
                            prpStatement2.execute();

                            // removing the person from relationships table based on id
                            PreparedStatement prpStatement3 = connection.prepareStatement("DELETE FROM relationships WHERE person_1_id = ? OR person_2_id = ?");
                            prpStatement3.setInt(1, idToDelete);
                            prpStatement3.setInt(2, idToDelete);
                            prpStatement3.execute();

                            System.out.println("Person removed");
                        }

                    } catch (SQLException exception) {
                        System.out.println("Error: " + exception);
                    }

                    break;

                case 9:
                    // Find out birthdays based on month
                    peopleBornInTheSameMonth(familyTree);
                    System.out.println();
                    break;

                default:
                    System.out.println("Invalid choice. Please type a number from the given Menu.");
            }
        } while (choice != 0);
    }

    static Graph familyTree() {

        PersonGraph graph = new PersonGraph(RelationshipEdge.class);

        return graph;
    }

    public static Graph<Person, RelationshipEdge> DBConnection(Graph<Person, RelationshipEdge> familyTree) {

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database");

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

                }

                // getting relationship labels from the relationship database table
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
        return familyTree;
    }

    public static void showFamilyTreeMembersList(Graph<Person, RelationshipEdge> familyTree) {

        familyTree = DBConnection(familyTree);
        System.out.println("Here is a list of every person in the Family Tree: ");
        for (Person eachPerson : familyTree.vertexSet()) {
            System.out.println(eachPerson.getName() + " " + eachPerson.getSurname() + " (born in " + eachPerson.getBirthDate() + ")");
        }
        System.out.println();
    }

    public static void printInfoAboutPerson(Graph<Person, RelationshipEdge> familyTree) {

        familyTree = DBConnection(familyTree);

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

    public static void calculateOldestPerson() {

        // OLDEST PERSON DEAD OR ALIVE
        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                // System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                //         " " + metaData.getDatabaseProductVersion());

                Statement statement = connection.createStatement();

                String sqlStatement =
                        "SELECT * FROM persons";
                ResultSet rs = statement.executeQuery(sqlStatement);

                // initializing variables to store name, surname, age
                String nameOldest = null;
                String surnameOldest = null;
                String birthDateOldest = null;
                int oldest = 0;

                // getting information from database
                while (rs.next()) {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String birthDate = rs.getString("birth_date");
                    String deathDate = rs.getString("death_date");

                    // converting string to LocalDate to do the calculations
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDateBirth = LocalDate.parse(birthDate, dtf);

                    // if death date for person is present and matches regex (i.e., is not a string or other input, it calculates how long the person lived
                    if (deathDate != null && deathDate.matches("(([0][1-9])|([12][0-9])|([3][0-1]))[/]" +
                            "(([0][1-9])|([1][012]))[/](([1][0-9][0-9][0-9])|([2][0][0-9][0-9]))")) {
                        LocalDate localDateDeath = LocalDate.parse(deathDate, dtf);
                        Period age = Period.between(localDateBirth, localDateDeath);    // age is calculated
                        int ageYears = age.getYears();

                        // checking if it's the oldest age so far
                        if (ageYears > oldest) {
                            oldest = ageYears;
                            nameOldest = name;
                            surnameOldest = surname;
                        }

                        // if person is alive, it calculates persons age
                    } else {
                        LocalDate today = LocalDate.now();
                        Period age = Period.between(localDateBirth, today);     // age is calculated
                        int age2 = age.getYears();

                        // checking if it's the oldest age so far
                        if (age2 > oldest) {
                            oldest = age2;
                            nameOldest = name;
                            surnameOldest = surname;
                            birthDateOldest = birthDate;
                        }
                    }
                }
                System.out.println("The person who has lived the longest is " + nameOldest + " " + surnameOldest + ": " + oldest + " years (born in " + birthDateOldest + ")");
            }

        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }

    public static void calculateYoungestPerson() {

        // YOUNGEST LIVING PERSON
        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                //        " " + metaData.getDatabaseProductVersion());

                Statement statement = connection.createStatement();

                String sqlStatement =
                        "SELECT * FROM persons";
                ResultSet rs = statement.executeQuery(sqlStatement);

                // initializing variables to store name, surname, age
                String nameYoungest = null;
                String surnameYoungest = null;
                String birthDateYoungest = null;
                int youngest = 120;

                // getting information from database
                while (rs.next()) {
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String birthDate = rs.getString("birth_date");
                    String deathDate = rs.getString("death_date");

                    // converting string to LocalDate to do the calculations
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDateBirth = LocalDate.parse(birthDate, dtf);

                    // if death date for person is present and matches regex (i.e., is not a string or other input, it calculates how long the person lived
                    if (deathDate != null && deathDate.matches("(([0][1-9])|([12][0-9])|([3][0-1]))[/]" +
                            "(([0][1-9])|([1][012]))[/](([1][0-9][0-9][0-9])|([2][0][0-9][0-9]))")) {
                        LocalDate localDateDeath = LocalDate.parse(deathDate, dtf);
                        Period age = Period.between(localDateBirth, localDateDeath);    // age is calculated
                        int ageYears = age.getYears();
                        ageYears = 100;

                        // checking if it's the youngest age so far
                        if (ageYears < youngest) {
                            youngest = ageYears;
                            nameYoungest = name;
                            surnameYoungest = surname;
                            birthDateYoungest = birthDate;
                        }

                        // if person is alive, it calculates persons age
                    } else {
                        LocalDate today = LocalDate.now();
                        Period age = Period.between(localDateBirth, today);     // age is calculated
                        int age2 = age.getYears();

                        // checking if it's the youngest age so far
                        if (age2 < youngest) {
                            youngest = age2;
                            nameYoungest = name;
                            surnameYoungest = surname;
                            birthDateYoungest = birthDate;
                        }
                    }
                }
                System.out.println("The youngest person is " + nameYoungest + " " + surnameYoungest + ": " + youngest + " year(s) old (born in " + birthDateYoungest + ")");
            }

        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }

    }

    public static void addPerson(Graph<Person, RelationshipEdge> familyTree) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new person's name only: ");
        String nameNewPerson = scanner.nextLine();
        System.out.print("Enter the new person's surname: ");
        String surnameNewPerson = scanner.nextLine();
        int idNewPerson = 0;
        int userInputNumber = 0;

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database");
                Statement statement = connection.createStatement();

                // checking if the new person already exists in the family tree
                String query = "SELECT * FROM persons WHERE name = ? AND surname = ?";
                PreparedStatement prpStatement = connection.prepareStatement(query);
                prpStatement.setString(1, nameNewPerson);
                prpStatement.setString(2, surnameNewPerson);
                ResultSet resultSet = prpStatement.executeQuery();


                if (resultSet.next() == false) { // if they do not exist

                    System.out.print("Enter the gender (female/male/other): ");
                    String genderNewPerson = scanner.next().toLowerCase(Locale.ROOT);
                    System.out.print("Enter birth date DD/MM/YYYY: ");
                    String birth_dateNewPerson = scanner.next();
                    System.out.print("Enter death date DD/MM/YYYY (enter '-' if not applicable): ");
                    String death_dateNewPerson = scanner.next();

                    String query1 = "INSERT INTO persons (name, surname, gender, birth_date, death_date) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setString(1, nameNewPerson);
                    preparedStatement.setString(2, surnameNewPerson);
                    preparedStatement.setString(3, genderNewPerson);
                    preparedStatement.setString(4, birth_dateNewPerson);
                    preparedStatement.setString(5, death_dateNewPerson);

                    preparedStatement.executeUpdate();

                    // finding the newly added person in database by name and surname
                    PreparedStatement prpStatementNewPerson = connection.prepareStatement("SELECT * FROM persons " +
                            "WHERE name = ? AND surname = ?");
                    prpStatementNewPerson.setString(1, nameNewPerson);
                    prpStatementNewPerson.setString(2, surnameNewPerson);

                    ResultSet rs1 = prpStatementNewPerson.executeQuery();

                    // getting new persons id
                    while (rs1.next()) {
                        idNewPerson = rs1.getInt("person_id");
                    }

                    // confirming that the person has been added
                    if (idNewPerson != 0) {
                        System.out.println(nameNewPerson + " " + surnameNewPerson + " has been added to the Family Tree.");
                        System.out.println("To add relationships please choose the 'Add relationsips' option from the Menu!");
                        System.out.println();
                    }

                } else {
                    System.out.println("This person already exists in the family tree");
                    PreparedStatement prpStatement10 = connection.prepareStatement("SELECT * FROM persons " +
                            "WHERE name = ? AND surname = ?");
                    prpStatement10.setString(1, nameNewPerson);
                    prpStatement10.setString(2, surnameNewPerson);

                    ResultSet rs10 = prpStatement10.executeQuery();

                    // getting and printing person's information
                    int existingPersonID = 0;
                    int count = 0;
                    System.out.print("Person(s) found: ");
                    while (rs10.next()) {
                        String name = rs10.getString("name");
                        String surname = rs10.getString("surname");
                        String gender = rs10.getString("gender");
                        String birthDate = rs10.getString("birth_date");
                        String deathDate = rs10.getString("death_date");

                        System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                + birthDate + ", death date: " + deathDate + ")");
                        count++;
                    }
                    // if there is only one person (counter == 1)
                    if (count == 1) {
                        // upon confirmation set the id to be set as related person
                        System.out.println("Is this the person you wanted to add to the Family Tree?");
                        System.out.println("Please choose between numbers 1. Yes | 2. No");
                        System.out.print("Enter the number: ");
                        int input = scanner.nextInt();

                        if (input == 1) {
                            System.out.println("No further action required.");
                            System.out.println("To add relationships please choose the 'Add relationsips' option from the Menu!");
                            return;
                        } else {
                            System.out.print("Enter the gender (female/male/other): ");
                            String genderNewPerson = scanner.next().toLowerCase(Locale.ROOT);
                            System.out.print("Enter birth date DD/MM/YYYY: ");
                            String birth_dateNewPerson = scanner.next();
                            System.out.print("Enter death date DD/MM/YYYY (enter '-' if not applicable): ");
                            String death_dateNewPerson = scanner.next();

                            String query2 = "INSERT INTO persons (name, surname, gender, birth_date, death_date) " +
                                    "VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                            preparedStatement2.setString(1, nameNewPerson);
                            preparedStatement2.setString(2, surnameNewPerson);
                            preparedStatement2.setString(3, genderNewPerson);
                            preparedStatement2.setString(4, birth_dateNewPerson);
                            preparedStatement2.setString(5, death_dateNewPerson);

                            preparedStatement2.execute();

                            // finding the newly added person in database by name and surname
                            PreparedStatement prpStatementNewPersonCheck = connection.prepareStatement("SELECT * FROM persons " +
                                    "WHERE name = ? AND surname = ?");
                            prpStatementNewPersonCheck.setString(1, nameNewPerson);
                            prpStatementNewPersonCheck.setString(2, surnameNewPerson);

                            ResultSet rs2 = prpStatementNewPersonCheck.executeQuery();

                            // getting new persons id
                            while (rs2.next()) {
                                idNewPerson = rs2.getInt("person_id");
                            }

                            // confirming that the person has been added
                            if (idNewPerson != 0) {
                                System.out.println(nameNewPerson + " " + surnameNewPerson + " has been added to the Family Tree.");
                                System.out.println("To add relationships please choose the 'Add relationsips' option from the Menu!");
                                System.out.println();
                            }
                        }

                        // if there is more than one person (counter > 1)
                    } else if (count > 1) {
                        System.out.println("There were multiple persons found with this name, please enter " +
                                "the date of birth for related person (DD/MM/YYYY): ");
                        String personDOB = scanner.next();

                        // finding the person by name, surname, birth date
                        PreparedStatement prpStatement4 = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ? AND birth_date = ?");
                        prpStatement4.setString(1, nameNewPerson);
                        prpStatement4.setString(2, surnameNewPerson);
                        prpStatement4.setString(3, personDOB);
                        ResultSet rs4 = prpStatement4.executeQuery();

                        // getting and printing person data for this person
                        while (rs4.next()) {
                            String name = rs4.getString("name");
                            String surname = rs4.getString("surname");
                            String gender = rs4.getString("gender");
                            String birthDate = rs4.getString("birth_date");
                            String deathDate = rs4.getString("death_date");
                            existingPersonID = rs4.getInt("person_id");

                            // upon confirmation set id to be related person
                            System.out.println("Is this the person you wanted to add to the Family Tree?");
                            System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                    + birthDate + ", death date: " + deathDate + ")");
                            System.out.println();
                            System.out.println("Please choose between numbers: 1. Yes | 2. No");
                            System.out.println("Enter the number: ");
                            int input = scanner.nextInt();

                            if (input == 1) {
                                System.out.println("No further action required.");
                                System.out.println("To add relationships please choose the 'Add relationsips' option from the Menu!");
                                return;
                            } else {
                                System.out.print("Enter the gender (female/male/other): ");
                                String genderNewPerson = scanner.next().toLowerCase(Locale.ROOT);
                                System.out.print("Enter birth date DD/MM/YYYY: ");
                                String birth_dateNewPerson = scanner.next();
                                System.out.print("Enter death date DD/MM/YYYY (enter '-' if not applicable): ");
                                String death_dateNewPerson = scanner.next();

                                String query3 = "INSERT INTO persons (name, surname, gender, birth_date, death_date) " +
                                        "VALUES (?, ?, ?, ?, ?)";

                                PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
                                preparedStatement3.setString(1, nameNewPerson);
                                preparedStatement3.setString(2, surnameNewPerson);
                                preparedStatement3.setString(3, genderNewPerson);
                                preparedStatement3.setString(4, birth_dateNewPerson);
                                preparedStatement3.setString(5, death_dateNewPerson);

                                preparedStatement3.execute();

                                // finding the newly added person in database by name and surname
                                PreparedStatement prpStatementNewPersonCheck2 = connection.prepareStatement("SELECT * FROM persons " +
                                        "WHERE name = ? AND surname = ?");
                                prpStatementNewPersonCheck2.setString(1, nameNewPerson);
                                prpStatementNewPersonCheck2.setString(2, surnameNewPerson);

                                ResultSet rs3 = prpStatementNewPersonCheck2.executeQuery();

                                // getting new persons id
                                while (rs3.next()) {
                                    idNewPerson = rs3.getInt("person_id");
                                }

                                // confirming that the person has been added
                                if (idNewPerson != 0) {
                                    System.out.println(nameNewPerson + " " + surnameNewPerson + " has been added to the Family Tree.");
                                    System.out.println("To add relationships please choose the 'Add relationsips' option from the Menu!");
                                    System.out.println();
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }

        DBConnection(familyTree);
    }

    public static void peopleBornInTheSameMonth(Graph<Person, RelationshipEdge> familyTree) throws ParseException {

        familyTree = DBConnection(familyTree);

        System.out.print("Type the month as a number (e.g. 1 = January, 2 = February, etc): ");
        Scanner sc = new Scanner(System.in);
        // get an input from the user as an integer
        int inputMonth = sc.nextInt();
        // convert it to string so can apply regex for validation
        String inputMonthToString = String.valueOf(inputMonth);
        // formatting method to convert string birth date to date data type
        DateFormat fromStringToDate = new SimpleDateFormat("dd/MM/yyyy");

        if (inputMonthToString != null && inputMonthToString.matches("^(1[0-2]|[1-9])$")) {

            System.out.println("Here is everyone that is born in the given month: ");
            // counter for the case when there is nobody born in the given month
            int counter = 0;
            // loop trhough the family tree persons list
            for (Person eachPerson : familyTree.vertexSet()) {
                Date birthDate = fromStringToDate.parse(eachPerson.getBirthDate());
                // find all the birthdays that match the user input month number
                if (birthDate.getMonth() + 1 == inputMonth) {
                    System.out.println(eachPerson.getName() + " " + eachPerson.getSurname() + ", born in " + eachPerson.getBirthDate() + ", age " + eachPerson.calculateAge());
                    counter++;
                }
            }
            if (counter == 0) {
                System.out.println("There is nobody born in the given month.");
            }
        } else {
            System.out.println("Invalid month number, the number must be between 1 and 12. Plese check input and try again!");
        }
    }

    public static void createRelationship(String namePerson1, String surnamePerson1, String namePerson2, String surnamePerson2, int idPerson1, int idPerson2) {

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database");
                Statement statement = connection.createStatement();

                Scanner scanner = new Scanner(System.in);

                System.out.println(namePerson1 + " " + surnamePerson1 + " is <choose the correct relationship from the options below> of " + namePerson2 + " " + surnamePerson2 + ".");
                System.out.println("1. Mother | 2. Father | 3. Spouse | 4. Daughter | 5. Son | 6. Divorced");
                System.out.print("Enter the number: ");
                int relationshipNumber = scanner.nextInt();

                switch (relationshipNumber) {
                    case 1: //mother
                        RelationshipLabels relationshipLabelMother = RelationshipLabels.mother;

                        String queryMother = "UPDATE relationships SET relationship_type = ? WHERE person_1_id = ? AND person_2_id = ?";

                        PreparedStatement prpStatementMother = connection.prepareStatement(queryMother);
                        prpStatementMother.setString(1, String.valueOf(relationshipLabelMother));
                        prpStatementMother.setInt(2, idPerson1);
                        prpStatementMother.setInt(3, idPerson2);

                        prpStatementMother.execute();

                        break;

                    case 2: //father
                        RelationshipLabels relationshipLabelFather = RelationshipLabels.father;

                        String queryFather = "UPDATE relationships SET relationship_type = ? WHERE person_1_id = ? AND person_2_id = ?";

                        PreparedStatement prpStatementFather = connection.prepareStatement(queryFather);
                        prpStatementFather.setString(1, String.valueOf(relationshipLabelFather));
                        prpStatementFather.setInt(2, idPerson1);
                        prpStatementFather.setInt(3, idPerson2);

                        prpStatementFather.execute();


                        break;

                    case 3: // spouse
                        RelationshipLabels relationshipLabelSpouse = RelationshipLabels.spouses;

                        String querySpouse = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)"; // query that works with SQLite only

                        PreparedStatement prpStatementSpouse = connection.prepareStatement(querySpouse);
                        prpStatementSpouse.setInt(1, idPerson1);
                        prpStatementSpouse.setInt(2, idPerson2);
                        prpStatementSpouse.setString(3, String.valueOf(relationshipLabelSpouse));
                        prpStatementSpouse.setString(4, "");
                        prpStatementSpouse.setString(5, "");

                        prpStatementSpouse.execute();

                        break;

                    case 4:
                        RelationshipLabels relationshipLabelDaughter = RelationshipLabels.daughter;

                        String queryDaughter = "UPDATE relationships SET relationship_type = ? WHERE person_1_id = ? AND person_2_id = ?";

                        PreparedStatement prpStatementDaughter = connection.prepareStatement(queryDaughter);
                        prpStatementDaughter.setString(1, String.valueOf(relationshipLabelDaughter));
                        prpStatementDaughter.setInt(2, idPerson1);
                        prpStatementDaughter.setInt(3, idPerson2);

                        prpStatementDaughter.execute();

                        break;

                    case 5:
                        RelationshipLabels relationshipLabelSon = RelationshipLabels.son;

                        String querySon = "UPDATE relationships SET relationship_type = ? WHERE person_1_id = ? AND person_2_id = ?";

                        PreparedStatement prpStatementSon = connection.prepareStatement(querySon);
                        prpStatementSon.setString(1, String.valueOf(relationshipLabelSon));
                        prpStatementSon.setInt(2, idPerson1);
                        prpStatementSon.setInt(3, idPerson2);

                        prpStatementSon.execute();

                        break;

                    case 6: //divorced
                        RelationshipLabels relationshipLabelDivorced = RelationshipLabels.divorced;

                        String queryDivorced = "UPDATE relationships SET relationship_type = ? WHERE person_1_id = ? AND person_2_id = ?";

                        PreparedStatement prpStatementDivorced = connection.prepareStatement(queryDivorced);
                        prpStatementDivorced.setString(1, String.valueOf(relationshipLabelDivorced));
                        prpStatementDivorced.setInt(2, idPerson1);
                        prpStatementDivorced.setInt(3, idPerson2);

                        prpStatementDivorced.execute();

                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addRelationships() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first related person's name only: ");
        String namePerson1 = scanner.nextLine();
        System.out.print("Enter the first related person's surname: ");
        String surnamePerson1 = scanner.nextLine();
        int idPerson1 = 0;
        int idPerson2 = 0;

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database");
                Statement statement = connection.createStatement();

                // finding the person1 in database by name and surname
                PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons " +
                        "WHERE name = ? AND surname = ?");
                prpStatement.setString(1, namePerson1);
                prpStatement.setString(2, surnamePerson1);

                ResultSet rs = prpStatement.executeQuery();

                // getting persons1 information and printing it out
                int count = 0;
                while (rs.next()) {
                    System.out.print("Person(s) found: ");
                    int personId1 = rs.getInt("person_id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String gender = rs.getString("gender");
                    String birthDate = rs.getString("birth_date");
                    String deathDate = rs.getString("death_date");

                    System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                            + birthDate + ", death date: " + deathDate + ")");
                    System.out.println();
                    count++;

                    // if there is only one person (counter == 1)
                    if (count == 1) {
                        // upon confirmation set the id to be set as related person
                        System.out.println("Is this the first person you want to add a relationship to?");
                        System.out.println("Please choose between numbers 1. Yes | 2. No");
                        System.out.print("Enter the number: ");
                        int input = scanner.nextInt();
                        scanner.nextLine();

                        if (input == 1) {
                            idPerson1 = personId1;
                            System.out.println("The first person has been selected.");
                        } else {
                            System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu");
                            return;
                        }

                        // if there is more than one person (counter > 1)
                    } else if (count > 1) {
                        System.out.println("There were multiple persons found with this name, please enter " +
                                "the date of birth for related person (DD/MM/YYYY): ");
                        String person1DOB = scanner.next();

                        // finding the person by name, surname, birth date
                        PreparedStatement prpStatement4 = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ? AND birth_date = ?");
                        prpStatement4.setString(1, namePerson1);
                        prpStatement4.setString(2, surnamePerson1);
                        prpStatement4.setString(3, person1DOB);
                        ResultSet rs4 = prpStatement4.executeQuery();

                        // getting and printing person data for this person
                        while (rs4.next()) {
                            String name1 = rs4.getString("name");
                            String surname1 = rs4.getString("surname");
                            String gender1 = rs4.getString("gender");
                            String birthDate1 = rs4.getString("birth_date");
                            String deathDate1 = rs4.getString("death_date");
                            int personId11 = rs4.getInt("person_id");

                            System.out.println("--- " + name1 + " " + surname1 + " (" + gender1 + ") " + " (birth date: "
                                    + birthDate1 + ", death date: " + deathDate1 + ")");
                            System.out.println();

                            System.out.println("Is this the first person you want to add a relationship to?");
                            System.out.println("Please choose between numbers: 1. Yes | 2. No");
                            System.out.print("Enter the number: ");
                            int input1 = scanner.nextInt();
                            scanner.nextLine();

                            if (input1 == 1) {
                                idPerson1 = personId11;
                                System.out.println("The first person has been selected.");
                            } else {
                                System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu");
                                return;
                            }
                        }
                    }
                }
                System.out.println("Enter information about a person related to " + namePerson1 + " " + surnamePerson1);
                System.out.print("Enter the second related person's name only: ");
                String namePerson2 = scanner.nextLine();

                System.out.print("Enter the second related person's surname: ");
                String surnamePerson2 = scanner.nextLine();


                // finding the person2 in database by name and surname
                PreparedStatement prpStatement1 = connection.prepareStatement("SELECT * FROM persons " +
                        "WHERE name = ? AND surname = ?");
                prpStatement1.setString(1, namePerson2);
                prpStatement1.setString(2, surnamePerson2);

                ResultSet rs0 = prpStatement1.executeQuery();

                // getting persons2 information and printing it out
                int counter = 0;
                while (rs0.next()) {
                    System.out.print("Person(s) found: ");
                    int personId2 = rs0.getInt("person_id");
                    String name = rs0.getString("name");
                    String surname = rs0.getString("surname");
                    String gender = rs0.getString("gender");
                    String birthDate = rs0.getString("birth_date");
                    String deathDate = rs0.getString("death_date");

                    System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                            + birthDate + ", death date: " + deathDate + ")");
                    System.out.println();
                    counter++;

                    // if there is only one person (counter == 1)
                    if (counter == 1) {
                        // upon confirmation set the id to be set as related person
                        System.out.println("Is this the second person you want to add a relationship to?");
                        System.out.println("Please choose between numbers 1. Yes | 2. No");
                        System.out.print("Enter the number: ");
                        int input2 = scanner.nextInt();
                        scanner.nextLine();

                        if (input2 == 1) {
                            idPerson2 = personId2;
                            System.out.println("The second person has been selected.");
                        } else {
                            System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu");
                            return;
                        }

                        // if there is more than one person (counter > 1)
                    } else if (counter > 1) {
                        System.out.println("There were multiple persons found with this name, please enter " +
                                "the date of birth for related person (DD/MM/YYYY): ");
                        String person2DOB = scanner.next();

                        // finding the person by name, surname, birth date
                        PreparedStatement prpStatement5 = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ? AND birth_date = ?");
                        prpStatement5.setString(1, namePerson2);
                        prpStatement5.setString(2, surnamePerson2);
                        prpStatement5.setString(3, person2DOB);
                        ResultSet rs5 = prpStatement5.executeQuery();

                        // getting and printing person data for this person
                        while (rs5.next()) {
                            String name2 = rs5.getString("name");
                            String surname2 = rs5.getString("surname");
                            String gender2 = rs5.getString("gender");
                            String birthDate2 = rs5.getString("birth_date");
                            String deathDate2 = rs5.getString("death_date");
                            int personId22 = rs5.getInt("person_id");

                            System.out.println("--- " + name2 + " " + surname2 + " (" + gender2 + ") " + " (birth date: "
                                    + birthDate2 + ", death date: " + deathDate2 + ")");
                            System.out.println();

                            System.out.println("Is this the second person you want to add a relationship to?");
                            System.out.println("Please choose between numbers: 1. Yes | 2. No");
                            System.out.print("Enter the number: ");
                            int input3 = scanner.nextInt();
                            scanner.nextLine();

                            if (input3 == 1) {
                                idPerson2 = personId22;
                                System.out.println("The second person has been selected.");
                            } else {
                                System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu");
                                return;
                            }
                        }
                    }
                }
                // add the relationship between first person and second person
                createRelationship(namePerson1, surnamePerson1, namePerson2, surnamePerson2, idPerson1, idPerson2);

                System.out.println("Action aborted.");
                System.out.println();
            }

        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }
}

