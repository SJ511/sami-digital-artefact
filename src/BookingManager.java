import java.io.*;
import java.util.*;

public class BookingManager {
    private AccountManager accountManager;
    private DestinationManager destinationManager;
    private Map<String, List<String>> bookings;

    public BookingManager(AccountManager accountManager, DestinationManager destinationManager) {
        this.accountManager = accountManager;
        this.destinationManager = destinationManager;
        this.bookings = new HashMap<>();
        loadBookings();
    }

    private void loadBookings() {
        try (Scanner scanner = new Scanner(new File("bookings.csv"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length < 5) {
                    System.out.println("Invalid booking data.");
                    continue;
                }
                List<String> bookingsList = bookings.get(data[0]);
                if (bookingsList == null) {
                    bookingsList = new ArrayList<>();
                    bookings.put(data[0], bookingsList);
                }
                bookingsList.add(data[1] + " on " + data[2] + " class from " + data[3] + " to " + data[4]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Booking file not found, starting fresh.");
        }
    }

    private void saveBookings() {
        try (PrintWriter out = new PrintWriter("bookings.csv")) {
            for (Map.Entry<String, List<String>> entry : bookings.entrySet()) {
                for (String detail : entry.getValue()) {
                    out.println(entry.getKey() + "," + detail);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving bookings.");
        }
    }

    public void handleBooking(Account account, Scanner scanner) {
        System.out.println("Do you want to (1) book a flight, or (2) view your bookings?");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice == 1) {
            bookFlight(account, scanner);
        } else {
            viewBookings(account);
        }
    }

    private void bookFlight(Account account, Scanner scanner) {
        destinationManager.showDestinations();
        Destination destination = destinationManager.chooseDestination(scanner);
        System.out.println("Available classes: 1. Economy, 2. Business, 3. First");
        System.out.print("Choose class (1-3): ");
        int classChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String flightClass = classChoice == 1 ? "economy" : classChoice == 2 ? "business" : "first";

        System.out.print("Enter start date (dd/MM/yyyy): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date (dd/MM/yyyy): ");
        String endDate = scanner.nextLine();

        double price = destination.calculateTotalPrice(flightClass, startDate, endDate);
        System.out.println("Your booking to " + destination.getName() + " in " + flightClass + " class from " + startDate + " to " + endDate + " will cost: £" + price);
        System.out.print("Confirm booking? (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            List<String> userBookings = bookings.computeIfAbsent(account.getUsername(), k -> new ArrayList<>());
            userBookings.add(destination.getName() + "," + flightClass + "," + startDate + "," + endDate + "," + price);
            saveBookings();
            System.out.println("Booking confirmed and saved.");
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    private void viewBookings(Account account) {
        List<String> userBookings = bookings.get(account.getUsername());
        if (userBookings != null && !userBookings.isEmpty()) {
            System.out.println("Your bookings:");
            userBookings.forEach(System.out::println);
        } else {
            System.out.println("No bookings found.");
        }
    }
}