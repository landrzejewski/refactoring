package pl.training.refactorings.newmovies.rental;

public class ChildrenRentalCalculator implements RentalCalculator {
    
    private static final double BASE_PRICE = 1.5;
    private static final double EXTRA_DAY_PRICE = 1.5;
    private static final int FREE_DAYS = 3;

    @Override
    public double calculatePrice(int daysRented) {
        return BASE_PRICE + Math.max(0, daysRented - FREE_DAYS) * EXTRA_DAY_PRICE;
    }

    @Override
    public int calculateFrequentRenterPoints(int daysRented) {
        return 1;
    }

}