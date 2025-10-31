package pl.training.refactorings.newmovies.rental;

public interface RentalCalculator {
    
    double calculatePrice(int daysRented);
    
    int calculateFrequentRenterPoints(int daysRented);

}