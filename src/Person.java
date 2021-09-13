import org.jgrapht.Graph;

import java.sql.*;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class Person {

    private int id;
    private String name;
    private String surname;
    private Gender gender;
    private String birthDate;
    private String deathDate;


    public Person() {
    }

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person(int id, String name, String surname, Gender gender, String birthDate, String deathDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && gender == person.gender && Objects.equals(birthDate, person.birthDate) && Objects.equals(deathDate, person.deathDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, gender, birthDate, deathDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name = '" + name + '\'' +
                ", surname = '" + surname + '\'' +
                ", gender = " + gender +
                ", birthDate = " + birthDate +
                ", deathDate = " + deathDate +
                '}';
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public int calculateAge() throws ParseException {
        LocalDate currentDate = LocalDate.now();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(getBirthDate());
        LocalDate birthDate = convertToLocalDate(date);
        return Period.between(birthDate, currentDate).getYears();
    }

    public static void printInfoAboutPerson(Graph<Person, RelationshipEdge> familyTree) {

        familyTree = Database.DBConnection(familyTree);

        System.out.print("Please enter the name only of person: ");
        Scanner scan = new Scanner(System.in);
        String inputChoice = scan.nextLine();
        int counter = 0; // to check how many people are in the database with the input name
        System.out.println("Here is the information about people called " + inputChoice + ": ");
        // check if the input name can be found in the database
        for (Person eachPerson : familyTree.vertexSet()) {
            if (eachPerson.getName().equalsIgnoreCase(inputChoice)) {
                System.out.println(eachPerson);
                counter++;

                // find all the relationships that the given person has
                for (RelationshipEdge eachRelationship : familyTree.edgesOf(eachPerson)) {
                    if (eachPerson == familyTree.getEdgeSource(eachRelationship)) {
                        Person targetPerson = familyTree.getEdgeTarget(eachRelationship);
                        System.out.println(inputChoice + " is " + RelationshipLabels.valueOf(String.valueOf(eachRelationship.getLabel())) + " of " + targetPerson.getName() + " " + targetPerson.getSurname());
                    }
                }
            }
        }
        if (counter == 0) { // if no people with the input name can be found in the database
            System.out.println("A person with this name does not exist in the Family Tree.");
        }
    }

    public static void calculateOldestPerson() {

        // OLDEST PERSON DEAD OR ALIVE
        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
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
                rs.close();
                statement.close();
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
                connection.getMetaData();
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
                rs.close();
                statement.close();
            }
        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }

    public static void peopleBornInTheSameMonth(Graph<Person, RelationshipEdge> familyTree) throws ParseException {

        familyTree = Database.DBConnection(familyTree);

        System.out.print("Type the month as a number (e.g. 1 = January, 2 = February, etc): ");
        Scanner sc = new Scanner(System.in);
        // get an input from the user as an integer
        int inputMonth = sc.nextInt();
        sc.nextLine();
        // convert it to string so can apply regex for validation
        String inputMonthToString = String.valueOf(inputMonth);
        // formatting method to convert string birth date to date data type
        DateFormat fromStringToDate = new SimpleDateFormat("dd/MM/yyyy");

        if (inputMonthToString != null && inputMonthToString.matches("^(1[0-2]|[1-9])$")) {

            System.out.println("Here is everyone that is born in the given month: ");
            // counter for the case when there is nobody born in the given month
            int counter = 0;
            // loop through the family tree persons list
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
            System.out.println("Invalid month number, the number must be between 1 and 12. Please check input and try again!");
        }
    }

    public static void addPerson(Graph<Person, RelationshipEdge> familyTree) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new person's name only: ");
        String nameNewPerson = scanner.nextLine();
        System.out.print("Enter the new person's surname: ");
        String surnameNewPerson = scanner.nextLine();
        int idNewPerson = 0;

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
                //System.out.println("Connected to database");
                connection.createStatement();

                // checking if the new person already exists in the family tree
                String query = "SELECT * FROM persons WHERE name = ? AND surname = ?";
                PreparedStatement prpStatement = connection.prepareStatement(query);
                prpStatement.setString(1, nameNewPerson);
                prpStatement.setString(2, surnameNewPerson);
                ResultSet resultSet = prpStatement.executeQuery();

                if (!resultSet.next()) { // if they do not exist

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

                    preparedStatement.execute();
                    preparedStatement.close();

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
                        System.out.println("To add relationships please choose the 'Add relationships' option from the Menu!");
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
                        scanner.nextLine();

                        if (input == 1) {
                            System.out.println("No further action required.");
                            System.out.println("To add relationships please choose the 'Add relationships' option from the Menu!");
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
                            preparedStatement2.close();

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
                                System.out.println("To add relationships please choose the 'Add relationships' option from the Menu!");
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

                            // upon confirmation set id to be related person
                            System.out.println("Is this the person you wanted to add to the Family Tree?");
                            System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                    + birthDate + ", death date: " + deathDate + ")");
                            System.out.println();
                            System.out.println("Please choose between numbers: 1. Yes | 2. No");
                            System.out.println("Enter the number: ");
                            int input = scanner.nextInt();
                            scanner.nextLine();

                            if (input == 1) {
                                System.out.println("No further action required.");
                                System.out.println("To add relationships please choose the 'Add relationships' option from the Menu!");
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
                                preparedStatement3.close();

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
                                    System.out.println("To add relationships please choose the 'Add relationships' option from the Menu!");
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }

    public static void addRelationships(Graph<Person, RelationshipEdge> familyTree) {

        Database.DBConnection(familyTree);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first related person's name only: ");
        String namePerson1 = scanner.nextLine();
        System.out.print("Enter the first related person's surname: ");
        String surnamePerson1 = scanner.nextLine();
        int idPerson1 = 0;
        int personId1 = 0;
        String genderPerson2 = null;
        int idPerson2 = 0;
        int personId2 = 0;
        int count = 0;
        int counter = 0;

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
                //System.out.println("Connected to database");
                connection.createStatement();

                // finding the person1 in database by name and surname
                PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons " +
                        "WHERE name = ? AND surname = ?");
                prpStatement.setString(1, namePerson1);
                prpStatement.setString(2, surnamePerson1);

                ResultSet rs = prpStatement.executeQuery();

                // getting persons1 information and printing it out
                while (rs.next()) {
                    System.out.print("Person(s) found: ");
                    personId1 = rs.getInt("person_id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String gender = rs.getString("gender");
                    String birthDate = rs.getString("birth_date");
                    String deathDate = rs.getString("death_date");

                    System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                            + birthDate + ", death date: " + deathDate + ")");
                    System.out.println();
                    count++;
                }

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
                        System.out.println();
                    } else {
                        System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                        return;
                    }

                    // if there is more than one person (counter > 1)
                } else if (count > 1) {
                    System.out.print("There were multiple persons found with this name, please enter " +
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
                            System.out.println();
                        } else {
                            System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                            return;
                        }
                    }
                } else {
                    System.out.println("The person cannot be found in the Family Tree.");
                    System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                }

                System.out.println("Next please enter information about a person related to " + namePerson1 + " " + surnamePerson1);
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
                while (rs0.next()) {
                    System.out.print("Person(s) found: ");
                    personId2 = rs0.getInt("person_id");
                    String name = rs0.getString("name");
                    String surname = rs0.getString("surname");
                    genderPerson2 = rs0.getString("gender");
                    String birthDate = rs0.getString("birth_date");
                    String deathDate = rs0.getString("death_date");

                    System.out.println("--- " + name + " " + surname + " (" + genderPerson2 + ") " + " (birth date: "
                            + birthDate + ", death date: " + deathDate + ")");
                    System.out.println();
                    counter++;
                }

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
                        System.out.println();
                    } else {
                        System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                        return;
                    }

                    // if there is more than one person (counter > 1)
                } else if (counter > 1) {
                    System.out.print("There were multiple persons found with this name, please enter " +
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
                        genderPerson2 = rs5.getString("gender");
                        String birthDate2 = rs5.getString("birth_date");
                        String deathDate2 = rs5.getString("death_date");
                        int personId22 = rs5.getInt("person_id");

                        System.out.println("--- " + name2 + " " + surname2 + " (" + genderPerson2 + ") " + " (birth date: "
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
                            System.out.println();
                        } else {
                            System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                            return;
                        }
                    }
                } else {
                    System.out.println("The person cannot be found in the Family Tree.");
                    System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu!");
                }

                // add the relationship between first person and second person
                createRelationship(namePerson1, surnamePerson1, namePerson2, surnamePerson2, idPerson1, idPerson2, genderPerson2);
                System.out.println("Relationship added to the Family Tree.");
            }
        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }
    }

    public static void createRelationship(String namePerson1, String surnamePerson1, String namePerson2, String surnamePerson2, int idPerson1, int idPerson2, String genderPerson2) {

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
                //System.out.println("Connected to database");
                connection.createStatement();

                Scanner scanner = new Scanner(System.in);
                System.out.println(namePerson1 + " " + surnamePerson1 + " is <choose the correct relationship from the options below> of " + namePerson2 + " " + surnamePerson2 + ".");
                System.out.println("1. Mother | 2. Father | 3. Spouse | 4. Daughter | 5. Son | 6. Divorced");
                System.out.print("Enter the number: ");
                int relationshipNumber = scanner.nextInt();
                scanner.nextLine();

                switch (relationshipNumber) {
                    case 1: //mother
                        RelationshipLabels relationshipLabelMother = RelationshipLabels.mother;
                        RelationshipLabels relationshipLabelDaughter = RelationshipLabels.daughter;
                        RelationshipLabels relationshipLabelSon = RelationshipLabels.son;

                        String queryMother = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement prpStatementMother = connection.prepareStatement(queryMother);
                        prpStatementMother.setInt(1, idPerson1);
                        prpStatementMother.setInt(2, idPerson2);
                        prpStatementMother.setString(3, String.valueOf(relationshipLabelMother));
                        prpStatementMother.setString(4, "");
                        prpStatementMother.setString(5, "");

                        prpStatementMother.execute();


                        if (genderPerson2.equalsIgnoreCase("female")) {

                            String queryDaughter = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement prpStatementDaughter = connection.prepareStatement(queryDaughter);
                            prpStatementDaughter.setInt(1, idPerson2);
                            prpStatementDaughter.setInt(2, idPerson1);
                            prpStatementDaughter.setString(3, String.valueOf(relationshipLabelDaughter));
                            prpStatementDaughter.setString(4, "");
                            prpStatementDaughter.setString(5, "");

                            prpStatementDaughter.execute();
                            prpStatementDaughter.close();

                        } else if (genderPerson2.equalsIgnoreCase("male")) {

                            String querySon = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement prpStatementSon = connection.prepareStatement(querySon);
                            prpStatementSon.setInt(1, idPerson2);
                            prpStatementSon.setInt(2, idPerson1);
                            prpStatementSon.setString(3, String.valueOf(relationshipLabelSon));
                            prpStatementSon.setString(4, "");
                            prpStatementSon.setString(5, "");

                            prpStatementSon.execute();
                            prpStatementSon.close();
                        }
                        break;

                    case 2: //father
                        RelationshipLabels relationshipLabelFather = RelationshipLabels.father;
                        relationshipLabelDaughter = RelationshipLabels.daughter;
                        relationshipLabelSon = RelationshipLabels.son;

                        String queryFather = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement prpStatementFather = connection.prepareStatement(queryFather);
                        prpStatementFather.setInt(1, idPerson1);
                        prpStatementFather.setInt(2, idPerson2);
                        prpStatementFather.setString(3, String.valueOf(relationshipLabelFather));
                        prpStatementFather.setString(4, "");
                        prpStatementFather.setString(5, "");

                        prpStatementFather.execute();


                        if (genderPerson2.equalsIgnoreCase("female")) {

                            String queryDaughter = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement prpStatementDaughter = connection.prepareStatement(queryDaughter);
                            prpStatementDaughter.setInt(1, idPerson2);
                            prpStatementDaughter.setInt(2, idPerson1);
                            prpStatementDaughter.setString(3, String.valueOf(relationshipLabelDaughter));
                            prpStatementDaughter.setString(4, "");
                            prpStatementDaughter.setString(5, "");

                            prpStatementDaughter.execute();
                            prpStatementDaughter.close();

                        } else if (genderPerson2.equalsIgnoreCase("male")) {

                            String querySon = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            PreparedStatement prpStatementSon = connection.prepareStatement(querySon);
                            prpStatementSon.setInt(1, idPerson2);
                            prpStatementSon.setInt(2, idPerson1);
                            prpStatementSon.setString(3, String.valueOf(relationshipLabelSon));
                            prpStatementSon.setString(4, "");
                            prpStatementSon.setString(5, "");

                            prpStatementSon.execute();
                            prpStatementSon.close();
                        }
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


                        PreparedStatement prpStatementSpouse2 = connection.prepareStatement(querySpouse);
                        prpStatementSpouse2.setInt(1, idPerson2);
                        prpStatementSpouse2.setInt(2, idPerson1);
                        prpStatementSpouse2.setString(3, String.valueOf(relationshipLabelSpouse));
                        prpStatementSpouse2.setString(4, "");
                        prpStatementSpouse2.setString(5, "");

                        prpStatementSpouse2.execute();
                        prpStatementSpouse2.close();
                        break;

                    case 4:
                        relationshipLabelDaughter = RelationshipLabels.daughter;
                        relationshipLabelMother = RelationshipLabels.mother;
                        relationshipLabelFather = RelationshipLabels.father;

                        String queryDaughter = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement prpStatementDaughter = connection.prepareStatement(queryDaughter);
                        prpStatementDaughter.setInt(1, idPerson1);
                        prpStatementDaughter.setInt(2, idPerson2);
                        prpStatementDaughter.setString(3, String.valueOf(relationshipLabelDaughter));
                        prpStatementDaughter.setString(4, "");
                        prpStatementDaughter.setString(5, "");

                        prpStatementDaughter.execute();


                        if (genderPerson2.equalsIgnoreCase("female")) {

                            queryMother = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            prpStatementMother = connection.prepareStatement(queryMother);
                            prpStatementMother.setInt(1, idPerson2);
                            prpStatementMother.setInt(2, idPerson1);
                            prpStatementMother.setString(3, String.valueOf(relationshipLabelMother));
                            prpStatementMother.setString(4, "");
                            prpStatementMother.setString(5, "");

                            prpStatementMother.execute();
                            prpStatementMother.close();

                        } else if (genderPerson2.equalsIgnoreCase("male")) {

                            queryFather = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            prpStatementFather = connection.prepareStatement(queryFather);
                            prpStatementFather.setInt(1, idPerson2);
                            prpStatementFather.setInt(2, idPerson1);
                            prpStatementFather.setString(3, String.valueOf(relationshipLabelFather));
                            prpStatementFather.setString(4, "");
                            prpStatementFather.setString(5, "");

                            prpStatementFather.execute();
                            prpStatementFather.close();
                        }
                        break;

                    case 5:
                        relationshipLabelSon = RelationshipLabels.son;
                        relationshipLabelMother = RelationshipLabels.mother;
                        relationshipLabelFather = RelationshipLabels.father;

                        String querySon = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement prpStatementSon = connection.prepareStatement(querySon);
                        prpStatementSon.setInt(1, idPerson1);
                        prpStatementSon.setInt(2, idPerson2);
                        prpStatementSon.setString(3, String.valueOf(relationshipLabelSon));
                        prpStatementSon.setString(4, "");
                        prpStatementSon.setString(5, "");

                        prpStatementSon.execute();


                        if (genderPerson2.equalsIgnoreCase("female")) {

                            queryMother = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            prpStatementMother = connection.prepareStatement(queryMother);
                            prpStatementMother.setInt(1, idPerson2);
                            prpStatementMother.setInt(2, idPerson1);
                            prpStatementMother.setString(3, String.valueOf(relationshipLabelMother));
                            prpStatementMother.setString(4, "");
                            prpStatementMother.setString(5, "");

                            prpStatementMother.execute();
                            prpStatementMother.close();

                        } else if (genderPerson2.equalsIgnoreCase("male")) {

                            queryFather = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                            prpStatementFather = connection.prepareStatement(queryFather);
                            prpStatementFather.setInt(1, idPerson2);
                            prpStatementFather.setInt(2, idPerson1);
                            prpStatementFather.setString(3, String.valueOf(relationshipLabelFather));
                            prpStatementFather.setString(4, "");
                            prpStatementFather.setString(5, "");

                            prpStatementFather.execute();
                            prpStatementFather.close();
                        }
                        break;

                    case 6: //divorced
                        RelationshipLabels relationshipLabelDivorced = RelationshipLabels.divorced;

                        String queryDivorced = "INSERT INTO relationships (person_1_id, person_2_id, relationship_type, person_1, person_2) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement prpStatementDivorced = connection.prepareStatement(queryDivorced);
                        prpStatementDivorced.setInt(1, idPerson1);
                        prpStatementDivorced.setInt(2, idPerson2);
                        prpStatementDivorced.setString(3, String.valueOf(relationshipLabelDivorced));
                        prpStatementDivorced.setString(4, "");
                        prpStatementDivorced.setString(5, "");

                        prpStatementDivorced.execute();


                        PreparedStatement prpStatementDivorced2 = connection.prepareStatement(queryDivorced);
                        prpStatementDivorced2.setInt(1, idPerson2);
                        prpStatementDivorced2.setInt(2, idPerson1);
                        prpStatementDivorced2.setString(3, String.valueOf(relationshipLabelDivorced));
                        prpStatementDivorced2.setString(4, "");
                        prpStatementDivorced2.setString(5, "");

                        prpStatementDivorced2.execute();
                        prpStatementDivorced2.close();
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void updatePerson(Scanner in, Graph<Person, RelationshipEdge> familyTree) {

        System.out.print("Enter the name only of the person you wish to update: ");
        String personToUpdateName = in.next();
        System.out.print("Enter the surname of the person you wish to update: ");
        String personToUpdateSurname = in.next();

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
                connection.createStatement();

                PreparedStatement prpStatement = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND surname = ?");
                prpStatement.setString(1, personToUpdateName);
                prpStatement.setString(2, personToUpdateSurname);
                ResultSet rs = prpStatement.executeQuery();

                if (!rs.next()) {
                    System.out.println("This person cannot be found in the Family Tree");
                    System.out.println("To add a person to the Family Tree please select option 'Add person' from the Menu");
                } else {

                    while (rs.next()) {
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        String gender = rs.getString("gender");
                        String birthDate = rs.getString("birth_date");
                        String deathDate = rs.getString("death_date");

                        System.out.println(name + " " + surname + " (" + gender + ") " + " (birth date: "
                                + birthDate + ", death date: " + deathDate + ")");
                    }

                    System.out.println("What information would you like to to update: ");
                    System.out.println("1. Name | 2. Surname | 3. Gender | 4. Birth date | 5. Death date");
                    System.out.print("Enter the number: ");
                    int input = in.nextInt();
                    in.nextLine();

                    switch (input) {
                        case 1:
                            System.out.print("Please enter the new name of the person: ");
                            String newName = in.nextLine();

                            PreparedStatement prpStatement1 = connection.prepareStatement("UPDATE persons " +
                                    "SET name = ? WHERE name = ? AND surname = ?");
                            prpStatement1.setString(1, newName);
                            prpStatement1.setString(2, personToUpdateName);
                            prpStatement1.setString(3, personToUpdateSurname);
                            prpStatement1.execute();
                            prpStatement1.close();
                            break;

                        case 2:
                            System.out.print("Please enter the new surname of the person: ");
                            String newSurname = in.nextLine();

                            PreparedStatement prpStatement2 = connection.prepareStatement("UPDATE persons " +
                                    "SET surname = ? WHERE name = ? AND surname = ?");
                            prpStatement2.setString(1, newSurname);
                            prpStatement2.setString(2, personToUpdateName);
                            prpStatement2.setString(3, personToUpdateSurname);
                            prpStatement2.execute();
                            prpStatement2.close();
                            break;

                        case 3:
                            System.out.print("Please enter the new gender of the person: ");
                            String newGender = in.nextLine();

                            PreparedStatement prpStatement3 = connection.prepareStatement("UPDATE persons " +
                                    "SET gender = ? WHERE name = ? AND surname = ?");
                            prpStatement3.setString(1, newGender);
                            prpStatement3.setString(2, personToUpdateName);
                            prpStatement3.setString(3, personToUpdateSurname);
                            prpStatement3.execute();
                            prpStatement3.close();
                            break;

                        case 4:
                            System.out.print("Please enter the correct birth date of the person DD/MM/YYYY: ");
                            String newBirthDate = in.nextLine();

                            PreparedStatement prpStatement4 = connection.prepareStatement("UPDATE persons " +
                                    "SET birth_date = ? WHERE name = ? AND surname = ?");
                            prpStatement4.setString(1, newBirthDate);
                            prpStatement4.setString(2, personToUpdateName);
                            prpStatement4.setString(3, personToUpdateSurname);
                            prpStatement4.execute();
                            prpStatement4.close();
                            break;

                        case 5:
                            System.out.print("Please enter the death date of the person DD/MM/YYYY: ");
                            String newDeathDate = in.nextLine();

                            PreparedStatement prpStatement5 = connection.prepareStatement("UPDATE persons " +
                                    "SET death_date = ? WHERE name = ? AND surname = ?");
                            prpStatement5.setString(1, newDeathDate);
                            prpStatement5.setString(2, personToUpdateName);
                            prpStatement5.setString(3, personToUpdateSurname);
                            prpStatement5.execute();
                            prpStatement5.close();
                            break;

                        default:
                            System.out.println("Invalid number. Please enter a number between 1 and 5!");
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error: " + exception);
        }
    }

    public static void removePerson(Scanner in, Graph<Person, RelationshipEdge> familyTree) {
        // asking the user for name and surname of person to delete
        System.out.print("Enter the name only of person you want to remove from the Family Tree: ");
        String nameToDelete = in.next();
        System.out.print("Enter the surname of person you want to remove from the Family Tree: ");
        String surnameToDelete = in.next();

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
                connection.createStatement();

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
                    System.out.println();
                    counter++;
                }

                // if there is only one person (counter == 1)
                if (counter == 1) {
                    // upon confirmation set the id to be deleted
                    System.out.println("Is this the person you want to remove from the Family Tree?");
                    System.out.println("Please choose between numbers: 1. Yes | 2. No");
                    System.out.print("Enter the number: ");
                    int input = in.nextInt();
                    in.nextLine();

                    if (input == 1) {
                        idToDelete = personID;
                    } else {
                        System.out.println("This person won't be removed.");
                    }

                    // if there is more than one person (counter > 1)
                } else if (counter > 1) {
                    System.out.print("There were multiple persons found with this name, please enter " +
                            "the date of birth for person you wish to remove (DD/MM/YYYY): ");
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
                        System.out.println("Is this the person you wish to remove from the Family Tree?");
                        System.out.println("--- " + name + " " + surname + " (" + gender + ") " + " (birth date: "
                                + birthDate + ", death date: " + deathDate + ")");
                        System.out.println();
                        System.out.println("Please choose between numbers: 1. Yes | 2. No");
                        System.out.print("Enter the number: ");
                        int input = in.nextInt();
                        in.nextLine();

                        if (input == 1) {
                            idToDelete = personID;
                        } else {
                            System.out.println("This person won't be removed.");
                        }
                    }

                } else {    // if counter still 0, person was not found
                    System.out.println("This person was not found in the Family Tree.");
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

                // checking the deleted person in database by name and surname
                PreparedStatement prpStatementDeletedPersonCheck2 = connection.prepareStatement("SELECT * FROM persons " +
                        "WHERE name = ? AND surname = ?");
                prpStatementDeletedPersonCheck2.setString(1, nameToDelete);
                prpStatementDeletedPersonCheck2.setString(2, surnameToDelete);

                ResultSet rs3 = prpStatementDeletedPersonCheck2.executeQuery();

                // getting deleted persons id
                while (rs3.next()) {
                    idToDelete = rs3.getInt("person_id");
                }

                // confirming that the person has been deleted
                if (idToDelete == 0) {
                    System.out.println(nameToDelete + " " + surnameToDelete + " has been removed from the Family Tree.");
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error: " + exception);
        }

        Database.DBConnection(familyTree);
    }

}
