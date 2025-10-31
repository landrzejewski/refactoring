package pl.training.refactorings.newmovies;

import pl.training.refactorings.newmovies.rental.Rental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {

    private final String name;
    private final List<Rental> rentals;

    public Customer(String name) {
        this.name = name;
        this.rentals = new ArrayList<>();
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

}