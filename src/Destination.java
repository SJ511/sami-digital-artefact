import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Destination {
    private String name;
    private double priceThere;
    private double priceBack;

    public Destination(String name, double priceThere, double priceBack) {
        this.name = name;
        this.priceThere = priceThere;
        this.priceBack = priceBack;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPriceThere() {
        return priceThere;
    }

    public double getPriceBack() {
        return priceBack;
    }

    // Calculate total price based on class type and dates
    public double calculateTotalPrice(String flightClass, String startDate, String endDate) {
        double basePrice = priceThere + priceBack;
        int days = getDaysBetween(startDate, endDate);
        switch (flightClass.toLowerCase()) {
            case "first":
                return basePrice + (basePrice * 1.0 / 3) * days;
            case "business":
                return basePrice + (basePrice * 1.0 / 4) * days;
            default:  // Economy class has no additional charge
                return basePrice * days;
        }
    }

    private int getDaysBetween(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            long difference = end.getTime() - start.getTime();
            return (int) (difference / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
}