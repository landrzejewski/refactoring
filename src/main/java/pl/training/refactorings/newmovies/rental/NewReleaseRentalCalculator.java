package pl.training.refactorings.newmovies.rental;

public class NewReleaseRentalCalculator implements RentalCalculator {
    
    private static final double DAILY_PRICE = 3.0;
    private static final int BONUS_POINT_THRESHOLD = 1;

    @Override
    public double calculatePrice(int daysRented) {
        return daysRented * DAILY_PRICE;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return daysRented > BONUS_POINT_THRESHOLD ? 2 : 1;
    }

}