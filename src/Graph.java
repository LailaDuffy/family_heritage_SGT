import org.apache.commons.math3.geometry.spherical.twod.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Person, List<Person>> relatedPersons = new HashMap<>();

    public Graph(Map<Person, List<Person>> relatedPersons) {
        this.relatedPersons = relatedPersons;
    }

    public Graph() {
    }

    public Map<Person, List<Person>> getRelatedPersons() {
        return relatedPersons;
    }

    public void setRelatedPersons(Map<Person, List<Person>> relatedPersons) {
        this.relatedPersons = relatedPersons;
    }

    void addPerson(String name) {
        relatedPersons.putIfAbsent(new Person(name), new ArrayList<>());
    }

    void removePerson(String name) {
        Person person = new Person(name);
        relatedPersons.values().stream().forEach(e -> e.remove(person));
        relatedPersons.remove(new Person(name));
    }

    void addRelationshipEdge(String label1, String label2) {
        Person person1 = new Person(label1);
        Person person2 = new Person(label2);
        relatedPersons.get(person1).add(person2);
        relatedPersons.get(person2).add(person1);
    }

    void removeRelationshipEdge(String label1, String label2) {
        Person person1 = new Person(label1);
        Person person2 = new Person(label2);
        List<Person> edgesPerson1 = relatedPersons.get(person1);
        List<Person> edgesPerson2 = relatedPersons.get(person2);
        if (edgesPerson1 != null)
            edgesPerson1.remove(person2);
        if (edgesPerson2 != null)
            edgesPerson2.remove(person1);
    }

    List<Person> getRelatedPersons(String name) {
        return relatedPersons.get(new Person(name));
    }

}
