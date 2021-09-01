import java.sql.Date;
import java.util.Objects;

public class Person {

    private String name;
    private String surname;
    private Gender gender;
    private Date birthDate;
    private Date deathDate;
    private String placeOfBirth;
    private int age;

    public Person() {}

    public Person(String name) {
       this.name = name;
    }

    public Person(String name, String surname, Gender gender, Date birthDate, Date deathDate, String placeOfBirth, int age) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.placeOfBirth = placeOfBirth;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getAge() == person.getAge() && Objects.equals(getName(), person.getName()) && Objects.equals(getSurname(), person.getSurname()) && getGender() == person.getGender() && Objects.equals(getBirthDate(), person.getBirthDate()) && Objects.equals(getDeathDate(), person.getDeathDate()) && Objects.equals(getPlaceOfBirth(), person.getPlaceOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getGender(), getBirthDate(), getDeathDate(), getPlaceOfBirth(), getAge());
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person {" +
                "name: '" + name + '\'' +
                ", surname: '" + surname + '\'' +
                ", gender: " + gender +
                ", birth date: " + birthDate +
                ", death date: " + deathDate +
                ", place of birth: '" + placeOfBirth + '\'' +
                ", age: " + age +
                '}';
    }
}
