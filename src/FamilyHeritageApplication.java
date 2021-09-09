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
            System.out.println("6. Update a person in the Family Tree");
            System.out.println("7. Remove a person from the Family Tree");
            System.out.println("8. Find out who have birthdays in chosen month");
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
                    addPerson();
                    System.out.println();
                    break;

                case 6:
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
                                    String newName = in.next();

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

                case 7:
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

                            // finding the person in database by name and surname
                            PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons " +
                                    "WHERE name = ? AND surname = ?");
                            prpStatement.setString(1, nameToDelete);
                            prpStatement.setString(2, surnameToDelete);
                            ResultSet rs = prpStatement.executeQuery();

                            // getting and printing person's information
                            int counter = 0;
                            while (rs.next()) {
                                String name = rs.getString("name");
                                String surname = rs.getString("surname");
                                String gender = rs.getString("gender");
                                String birthDate = rs.getString("birth_date");
                                String deathDate = rs.getString("death_date");

                                System.out.println("Person found: ");
                                System.out.println("*** " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                        + birthDate + ", death date: " + deathDate + ")" + " ***");
                                System.out.println();

                                counter++;
                            }

                            // if at least one person is found with given name and surname, asking the user to
                            // confirm deletion
                            if (counter > 0) {
                                System.out.println("Is this the person you want to remove from the family tree?");
                                System.out.println("Enter the number: ");
                                System.out.println("1. Yes / 2. No");
                                int input = in.nextInt();

                                if (input == 1) {
                                    // finding the persons id
                                    PreparedStatement prpStatement1 = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ?");
                                    prpStatement1.setString(1, nameToDelete);
                                    prpStatement1.setString(2, surnameToDelete);
                                    ResultSet rs1 = prpStatement1.executeQuery();

                                    int idToDelete = 0;
                                    while (rs1.next()) {
                                        idToDelete = rs1.getInt("person_id");
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

                                } else {
                                    System.out.println("This person won't be removed");
                                }

                            } else {    // if counter still 0, person was not found
                                System.out.println("This person was not found in the family tree");
                            }


                        }
                    } catch (SQLException exception) {
                        System.out.println("Error: " + exception);
                    }

                    break;

                case 8:
                    // Find out birthdays based on month
                    peopleBornInSameMonth(familyTree);
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

    public static void DBConnection(Graph<Person, RelationshipEdge> familyTree) {

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

                    // adding persons to arraylist (to test if it works)
                    //arrayListOfPersons.add(newPerson);

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

    }

    public static void showFamilyTreeMembersList(Graph<Person, RelationshipEdge> familyTree) {
        DBConnection(familyTree);
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

    public static void calculateOldestPerson() {
        String databasePath = "jdbc:sqlite:family_heritage.db";

        // OLDEST PERSON DEAD OR ALIVE
        try {
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
        String databasePath = "jdbc:sqlite:family_heritage.db";

        // YOUNGEST LIVING PERSON
        try {
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

    public static void addPerson() {
        Scanner scanner = new Scanner(System.in);
        String databasePath = "jdbc:sqlite:family_heritage.db";

        try {
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                //System.out.println("Connected to database");
                Statement statement = connection.createStatement();

                System.out.print("Enter the name: ");
                String name = scanner.nextLine();
                System.out.print("Enter the surname: ");
                String surname = scanner.nextLine();


                String query = "SELECT * FROM persons WHERE name = ? AND surname = ?";
                PreparedStatement prpStatement = connection.prepareStatement(query);
                prpStatement.setString(1, name);
                prpStatement.setString(2, surname);
                ResultSet resultSet = prpStatement.executeQuery(query);

                if (resultSet.next() == false) {

                    System.out.print("Enter the gender (female/male/other): ");
                    String gender = scanner.next().toLowerCase(Locale.ROOT);
                    System.out.print("Enter birth date DD/MM/YYYY: ");
                    String birth_date = scanner.next();
                    System.out.print("Enter death date DD/MM/YYYY (enter '-' if not applicable): ");
                    String death_date = scanner.next();

                    String query1 = "INSERT INTO persons (name, surname, gender, birth_date, death_date) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query1);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, surname);
                    preparedStatement.setString(3, gender);
                    preparedStatement.setString(4, birth_date);
                    preparedStatement.setString(5, death_date);

                    preparedStatement.execute();

                }
            } else {
                System.out.println("There already is such a person");
            }

            System.out.println("Now you will have to input information about one of  the following: 1. Mother " +
                    "2. Father 3. Spouse");
            int relationship = scanner.nextInt();

            switch (relationship) {
                case 1: //mother
                    RelationshipEdge mother = new RelationshipEdge(RelationshipLabels.mother);


                    System.out.println("Please write name of mother: ");
                    String motherName = scanner.next();
                    System.out.println("Please write surname of mother: ");
                    String motherSurname = scanner.next();


                    break;

                case 2: //father
                    RelationshipEdge father = new RelationshipEdge(RelationshipLabels.father);
                    break;

                case 3:
                    RelationshipEdge spouse = new RelationshipEdge(RelationshipLabels.spouses);
                    break;

                default:
                    System.out.println("Invalid choice");
            }

            System.out.println();


        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }

    public static void peopleBornInSameMonth(Graph<Person, RelationshipEdge> familyTree) throws ParseException {
        System.out.print("Type the month as a number (e.g. 1 = January, 2 = February, etc): ");
        Scanner sc = new Scanner(System.in);
        // get an input from the user as an integer
        int inputMonth = sc.nextInt();
        String inputMonthToString = String.valueOf(inputMonth);

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
}




