import java.io.*;
import java.util.*;

public class SJAirlineBookingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER_DATA_FILE = "users.csv";
    private static final String QUOTE_DATA_FILE = "quotes.csv";
    private static final List<String> DESTINATIONS = Arrays.asList("Milan", "Bucharest", "Madrid", "Istanbul", "Santorini");
    private static final Map<String, Double> BASE_PRICES = Map.of(
            "First Class", 300.0,
            "Business Class", 200.0,
            "Economy Class", 100.0
    );

    public static void main(String[] args) throws IOException {
        System.out.println("Hi, welcome to SJ Airlines! Would you like to make an account or log into an existing account?");
        System.out.println("1: Create Account\n2: Login");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String username;
        if (choice == 1) {
            username = createAccount();
        } else {
            username = login();
        }

        if (username == null) {
            System.out.println("Login failed.");
            return;
        }

        System.out.println("Choose your destination:");
        for (int i = 0; i < DESTINATIONS.size(); i++) {
            System.out.printf("%d: %s\n", i + 1, DESTINATIONS.get(i));
        }
        int destinationChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        String destination = DESTINATIONS.get(destinationChoice - 1);

        System.out.println("Enter departure date (YYYY-MM-DD):");
        String departureDate = scanner.nextLine();
        System.out.println("Enter return date (YYYY-MM-DD):");
        String returnDate = scanner.nextLine();

        System.out.println("Choose class of service:");
        System.out.println("1: First Class\n2: Business Class\n3: Economy Class");
        int classChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        String classType = classChoice == 1 ? "First Class" : classChoice == 2 ? "Business Class" : "Economy Class";
        double basePrice = BASE_PRICES.get(classType);

        double totalPrice = calculatePrice(basePrice);
        System.out.printf("Total price for your trip: $%.2f\n", totalPrice);

        saveQuote(username, destination, departureDate, returnDate, classType, totalPrice);
        System.out.println("Your booking quote has been saved.");
    }

    private static double calculatePrice(double basePrice) {
        return basePrice * 1.20; // including a 20% fee for flight services
    }

    private static String createAccount() throws IOException {
        System.out.println("Enter a username:");
        String username = scanner.nextLine();
        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
            writer.write(username + "," + password + "\n");
        }
        return username;
    }

    private static String login() throws IOException {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return username;
                }
            }
        }
        return null;
    }

    private static void saveQuote(String username, String destination, String departureDate, String returnDate, String classType, double totalPrice) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUOTE_DATA_FILE, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s,$%.2f\n", username, destination, departureDate, returnDate, classType, totalPrice));
        }
    }
}
