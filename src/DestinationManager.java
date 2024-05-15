import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DestinationManager {
    private Map<String, Destination> destinations;

    public DestinationManager() {
        destinations = new HashMap<>();
        loadDestinations();
    }

    private void loadDestinations() {
        // Hardcoded destinations
        destinations.put("Milan", new Destination("Milan", 60, 80));
        destinations.put("Bucharest", new Destination("Bucharest", 80, 90));
        destinations.put("Madrid", new Destination("Madrid", 40, 35));
        destinations.put("Istanbul", new Destination("Istanbul", 100, 170));
        destinations.put("Santorini", new Destination("Santorini", 160, 130));
    }

    public Destination chooseDestination(Scanner scanner) {
        System.out.println("Available destinations:");
        destinations.forEach((key, value) -> System.out.println(key));
        System.out.print("Choose your destination: ");
        String choice = scanner.next();
        return destinations.get(choice);
    }

    public void showDestinations() {
        destinations.values().forEach(d ->
                System.out.println(d.getName() + ": £" + d.getPriceThere() + " to go, £" + d.getPriceBack() + " to return"));
    }
}
