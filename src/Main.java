import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        DestinationManager destinationManager = new DestinationManager();
        BookingManager bookingManager = null;

        try {
            bookingManager = new BookingManager(accountManager, destinationManager);
        } catch (Exception e) {
            System.out.println("Failed to initialize BookingManager: " + e.getMessage());
            return;
        }

        Account currentAccount = null;
        System.out.println("Hi, welcome to SJ Airlines! Would you like to (1) make an account or (2) log into an existing account?");

        while (true) {
            System.out.print("Enter your choice (1 or 2), press 3 to exit: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                currentAccount = accountManager.createAccount(scanner);
            } else if (choice.equals("2")) {
                currentAccount = accountManager.login(scanner);
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid choice, please restart the application.");
                break;
            }

            if (currentAccount != null) {
                bookingManager.handleBooking(currentAccount, scanner);
            } else {
                System.out.println("Login or account creation failed, please try again.");
            }
        }
    }
}