package pl.training.refactorings.newmovies;

import pl.training.refactorings.newmovies.rental.Rental;

import java.util.List;

public class Statement {

    private static final String LINE_BREAK = "\n";
    private static final String INDENT = "\t";

    public String create(Customer customer) {
        var result = new StringBuilder();
        result.append("Rental Record for ").append(customer.getName()).append(LINE_BREAK);

        for (Rental rental : customer.getRentals()) {
            result.append(INDENT)
                    .append(rental.movie().title())
                    .append(INDENT)
                    //.append(rental.calculatePrice())
                    .append(LINE_BREAK);
        }

        var summary = createSummary(customer.getRentals());
        result.append("Amount owed is ").append(summary.totalAmount()).append(LINE_BREAK);
        result.append("You earned ")
                .append(summary.frequentRenterPoints())
                .append(" frequent renter points");

        return result.toString();
    }

    private RentalSummary createSummary(List<Rental> rentals) {
        double totalAmount = 0;
        int frequentRenterPoints = 0;

        for (Rental rental : rentals) {
            //totalAmount += rental.calculatePrice();
            //frequentRenterPoints += rental.calculateFrequentRenterPoints();
        }

        return new RentalSummary(totalAmount, frequentRenterPoints);
    }

    private record RentalSummary(double totalAmount, int frequentRenterPoints) {
    }

}