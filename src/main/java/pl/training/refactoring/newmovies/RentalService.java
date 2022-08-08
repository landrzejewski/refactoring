package pl.training.refactoring.newmovies;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RentalService {

    private final PriceCalculator priceCalculator;
    private final LoyaltyPointsCalculator loyaltyPointsCalculator;

    public Statement createStatement(List<Rental> rentals) {
        var totalValue = getTotalValue(rentals);
        int loyaltyPoints = getLoyaltyPoints(rentals);
        return new Statement(totalValue, loyaltyPoints);
    }

    private double getTotalValue(List<Rental> rentals) {
        return rentals.stream()
                .mapToDouble(rental -> priceCalculator.getValueFor(rental.getType(), rental.periodInDays()))
                .sum();
    }

    private int getLoyaltyPoints(List<Rental> rentals) {
        return rentals.stream()
                .mapToInt(rental -> loyaltyPointsCalculator.getPoints(rental.isNewRelease(), rental.periodInDays()))
                .sum();
    }

}
