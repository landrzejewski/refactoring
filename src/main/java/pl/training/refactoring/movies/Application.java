package pl.training.refactoring.movies;

public class Application {

    public static void main(String[] args) {
        Customer c = new Customer("Jan Kowalski");
        c.addRental(new Rental(new Movie("movie 1", 0), 5));
        c.addRental(new Rental(new Movie("movie 2", 1), 15));
        c.addRental(new Rental(new Movie("movie 3", 2), 7));
        System.out.println(c.statement());
    }

}
