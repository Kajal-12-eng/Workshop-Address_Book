package bridgelabz;


import java.util.Scanner;

public class UserInputOutput {

    public static int menu() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nSelect option: ");
        System.out.println("1. Add a new contact in the address book.");
        System.out.println("2. Edit contact in the address book.");
        System.out.println("3. Delete contact in the address book.");
        System.out.println("4. Display all values from address book.");
        System.out.println("5. Search Address Book based on City or State.");
        System.out.println("6. Sort Address Book.");
        System.out.println("7. Quit.");
        int option = input.nextInt();

        return option;
    }
}