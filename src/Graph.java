//import java.util.*;
//
//public class Graph {
//
//    private Map<Person, List<Person>> relatedPersons = new HashMap<>();
//
//    public Graph(Map<Person, List<Person>> relatedPersons) {
//        this.relatedPersons = relatedPersons;
//    }
//
//    public Graph() {
//    }
//
//    public Map<Person, List<Person>> getRelatedPersons() {
//        return relatedPersons;
//    }
//
//    public void setRelatedPersons(Map<Person, List<Person>> relatedPersons) {
//        this.relatedPersons = relatedPersons;
//    }
//
//    void addPerson(String name) {
//        relatedPersons.putIfAbsent(new Person(name), new ArrayList<>());
//    }
//
//    void removePerson(String name) {
//        Person person = new Person(name);
//        relatedPersons.values().stream().forEach(e -> e.remove(person));
//        relatedPersons.remove(new Person(name));
//    }
//
//    void addRelationshipEdge(String label1, String label2) {
//        Person person1 = new Person(label1);
//        Person person2 = new Person(label2);
//        relatedPersons.get(person1).add(person2);
//        relatedPersons.get(person2).add(person1);
//    }
//
//    void removeRelationshipEdge(String label1, String label2) {
//        Person person1 = new Person(label1);
//        Person person2 = new Person(label2);
//        List<Person> edgesPerson1 = relatedPersons.get(person1);
//        List<Person> edgesPerson2 = relatedPersons.get(person2);
//        if (edgesPerson1 != null)
//            edgesPerson1.remove(person2);
//        if (edgesPerson2 != null)
//            edgesPerson2.remove(person1);
//    }
//
//    List<Person> getRelatedPersons(String name) {
//        return relatedPersons.get(new Person(name));
//    }
//
//    // explores family tree as deep as possible along each Person's branch before exploring family tree line at the same level
//    Set<String> depthFirstTraversal(Graph graph, String root) {
//        Set<String> visited = new LinkedHashSet<String>();
//        Stack<String> stack = new Stack<String>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            String person = stack.pop();
//            if (!visited.contains(person)) {
//                visited.add(person);
//                for (Person eachPerson : graph.getRelatedPersons(person)) {
//                    stack.push(eachPerson.getName());
//                }
//            }
//        }
//        return visited;
//    }
//
//    // finds all persons in the family tree at the same level before going deeper in the family tree graph
//    Set<String> breadthFirstTraversal(Graph graph, String root) {
//        Set<String> visited = new LinkedHashSet<String>();
//        Queue<String> queue = new LinkedList<String>();
//        queue.add(root);
//        visited.add(root);
//        while (!queue.isEmpty()) {
//            String person = queue.poll();
//            for (Person eachPerson : graph.getRelatedPersons(person)) {
//                if (!visited.contains(eachPerson.getName())) {
//                    visited.add(eachPerson.getName());
//                    queue.add(eachPerson.getName());
//                }
//            }
//        }
//        return visited;
//    }
//
//}
