import org.jgrapht.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;

import java.util.*;
import java.util.function.Supplier;

public class PersonGraph extends DefaultDirectedGraph<Person, RelationshipEdge> {

    public Map<Person, List<Person>> relatedPersons = new HashMap<>();

    public PersonGraph(Class<? extends RelationshipEdge> edgeClass, Map<Person, List<Person>> relatedPersons) {
        super(edgeClass);
        this.relatedPersons = relatedPersons;
    }

    public PersonGraph(Class<? extends RelationshipEdge> edgeClass) {
        super(edgeClass);
    }

    public Map<Person, List<Person>> getRelatedPersons() {
        return relatedPersons;
    }

    public void setRelatedPersons(Map<Person, List<Person>> relatedPersons) {
        this.relatedPersons = relatedPersons;
    }

    public void addPerson(Person person) {
        this.addVertex(new Person());
    }

    void removePerson(String name, String surname) {
        Person person = new Person(name, surname);
        relatedPersons.values().stream().forEach(e -> e.remove(person));
        relatedPersons.remove(new Person(name, surname));
    }


    void addRelationshipEdge(String name1, String surname1, String name2, String surname2, RelationshipLabels label1) { //does this really add an edge? how?
        Person person1 = new Person(name1, surname1);
        Person person2 = new Person(name2, surname2);
        this.addEdge(person1, person2, new RelationshipEdge(label1));

    }

    void removeRelationshipEdge(String name1, String surname1, String name2, String surname2) { // does this really remove an edge? how?
        Person person1 = new Person(name1, surname1);
        Person person2 = new Person(name2, surname2);
        List<Person> edgesPerson1 = relatedPersons.get(person1);
        List<Person> edgesPerson2 = relatedPersons.get(person2);
        if (edgesPerson1 != null)
            edgesPerson1.remove(person2);
        if (edgesPerson2 != null)
            edgesPerson2.remove(person1);
    }

    List<Person> getRelatedPersons(String name, String surname) {
        return relatedPersons.get(new Person(name, surname));
    }

    // explores family tree as deep as possible along each Person's branch before exploring family tree line at the same level
    Set<Person> depthFirstTraversal(PersonGraph graph, Person root) {
        Set<Person> visited = new LinkedHashSet<Person>();
        Stack<Person> stack = new Stack<Person>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Person person = stack.pop();
            if (!visited.contains(person)) {
                visited.add(person);
                for (Person eachPerson : graph.getRelatedPersons(person.getName(), person.getSurname())) {
                    stack.push(eachPerson); // need to check if eachPerson works by itself or needs a method added, e.g. eachPerson.getName()
                }
            }
        }
        return visited;
    }

    // finds all persons in the family tree at the same level before going deeper in the family tree graph
    Set<Person> breadthFirstTraversal(PersonGraph graph, Person root) {
        Set<Person> visited = new LinkedHashSet<Person>();
        Queue<Person> queue = new LinkedList<Person>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            Person person = queue.poll();
            for (Person eachPerson : graph.getRelatedPersons(person.getName(), person.getSurname())) {
                if (!visited.contains(eachPerson.getName())) {
                    visited.add(eachPerson); // need to check if eachPerson works by itself or needs a method added, e.g. eachPerson.getName()
                    queue.add(eachPerson); // need to check if eachPerson works by itself or needs a method added, e.g. eachPerson.getName()
                }
            }
        }
        return visited;
    }

    @Override
    public boolean addEdge(Person sourceVertex, Person targetVertex, RelationshipEdge relationshipEdge) {
        return super.addEdge(sourceVertex, targetVertex, relationshipEdge);
    }

    @Override
    public boolean addVertex(Person person) {
        return super.addVertex(person);
    }
}
