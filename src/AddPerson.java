import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class AddPerson {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String databasePath = "jdbc:sqlite:family_heritage.db";

        try {
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                System.out.println("Connected to database");
                Statement statement = connection.createStatement();

                System.out.println("Enter the name: ");
                String name = scanner.next();
                scanner.nextLine();
                System.out.println("Enter the surname:");
                String surname = scanner.nextLine();
                System.out.println("Enter the gender (female/male/other)");
                String gender = scanner.next().toLowerCase(Locale.ROOT);
                System.out.println("Enter birth date YYYY/MM/DD: ");
                String birth_date = scanner.next();
                System.out.println("Enter death date YYYY/MM/DD (enter '-' if not applicable): ");
                String death_date = scanner.next();


                String query = "INSERT INTO persons (name, surname, gender, birth_date, death_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, birth_date);
                preparedStatement.setString(5, death_date);


                preparedStatement.execute();
            }


        } catch (SQLException exception) {
            System.out.println("There was an error: " + exception);
        }


    }
}
