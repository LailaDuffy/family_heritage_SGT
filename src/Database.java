import org.jgrapht.Graph;

import java.sql.*;

public class Database {

    public static Graph<Person, RelationshipEdge> DBConnection(Graph<Person, RelationshipEdge> familyTree) {

        try {
            String databasePath = "jdbc:sqlite:family_heritage.db";
            Connection connection = DriverManager.getConnection(databasePath);

            if (connection != null) {
                connection.getMetaData();
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

}
