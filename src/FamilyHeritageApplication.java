import java.util.Scanner;

public class FamilyHeritageApplication {

    public static void main(String[] args) {
        // write your code here

        Scanner in = new Scanner(System.in);
        int menuItem = 1;

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Hello! Please choose your option: ");
        System.out.println("1. The list of persons");
        System.out.println("2. Info about a person (write name)");
        System.out.println("3. The oldest person in the list");
        System.out.println("4. The youngest person in the list");
        System.out.println("5. Add a person");
        System.out.println("6. Delete a person");
        System.out.println("0. - EXIT");

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        System.out.println("Please enter your choice: ");

        int choice = in.nextInt();

        switch (choice) {
            case 1:
                System.out.println("");
                break;

            case 2:
                System.out.println("");
                break;

            case 3:
                System.out.println("");
                break;

            case 4:
                System.out.println("");
            default:
                System.out.println("Invalid choice");


        }
    }
}


