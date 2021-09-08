import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


import java.sql.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

    public class Oldest_Youngest {
        public static void main(String[] args) {

            String databasePath = "jdbc:sqlite:family_heritage.db";

            // OLDEST PERSON DEAD OR ALIVE
            try {
                Connection connection = DriverManager.getConnection(databasePath);

                if (connection != null) {
                    DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                    System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                            " " + metaData.getDatabaseProductVersion());

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


            // OLDEST LIVING PERSON
            try {
                Connection connection = DriverManager.getConnection(databasePath);

                if (connection != null) {
                    DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                    System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                            " " + metaData.getDatabaseProductVersion());

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
                            ageYears = 0;

                            // checking if it's the oldest age so far
                            if (ageYears > oldest) {
                                oldest = ageYears;
                                nameOldest = name;
                                surnameOldest = surname;
                                birthDateOldest = birthDate;
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
                            }
                        }
                    }

                    System.out.println("Oldest living person is " + nameOldest + " " + surnameOldest + ": " + oldest + " years(born in " + birthDateOldest + ")");
                }

            } catch (SQLException exception) {
                System.out.println("There was an error: " + exception);
            }

            // YOUNGEST PERSON DEAD OR ALIVE
            try {
                Connection connection = DriverManager.getConnection(databasePath);

                if (connection != null) {
                    DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                    System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                            " " + metaData.getDatabaseProductVersion());

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

                    System.out.println("The person who has lived the shortest is " + nameYoungest + " " + surnameYoungest + ": " + youngest + " years (born in " + birthDateYoungest + ")");
                }

            } catch (SQLException exception) {
                System.out.println("There was an error: " + exception);
            }

            // YOUNGEST LIVING PERSON
            try {
                Connection connection = DriverManager.getConnection(databasePath);

                if (connection != null) {
                    DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                    System.out.println("Connected to database: " + metaData.getDatabaseProductName() +
                            " " + metaData.getDatabaseProductVersion());

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

                    System.out.println("The youngest living person is " + nameYoungest + " " + surnameYoungest + ": " + youngest + " years (born in " + birthDateYoungest + ")");
                }

            } catch (SQLException exception) {
                System.out.println("There was an error: " + exception);
            }

        }
    }






