package pl.training.refactorings.newmovies.rental;

import pl.training.refactorings.newmovies.Movie;

public record Rental(Movie movie, int daysRented) {

}