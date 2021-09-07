import java.util.Objects;

public class Person {

    private int id;
    private String name;
    private String surname;
    private Gender gender;
    private String birthDate;
    private String deathDate;


    public Person() {}

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
}
