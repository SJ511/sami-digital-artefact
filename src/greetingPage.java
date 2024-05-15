import java.util.Scanner;

public class greetingPage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect user information
        System.out.println("Hello and welcome to the programme. Before we start, let's collect some data about you.");

        System.out.print("What is your first name? ");
        String firstName = scanner.nextLine();

        System.out.print("What is your surname? ");
        String surname = scanner.nextLine();

        System.out.print("Where do you work? ");
        String companyName = scanner.nextLine();

        System.out.print("How many years have you worked for " + companyName + "? ");
        int yearsAtCompany = scanner.nextInt(); // Assuming the user inputs a valid integer

        // Display the welcome message
        System.out.println("\nOk, all information has been gathered.");
        System.out.println("Welcome to the programme " + firstName + " " + surname + ". Congratulations on working for "
                + companyName + " for " + yearsAtCompany + " years.");
        System.out.println("Now, let’s begin...");

        // Close the scanner
        scanner.close();
    }
}
