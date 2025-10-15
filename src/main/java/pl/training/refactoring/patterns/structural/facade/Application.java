package pl.training.refactoring.patterns.structural.facade;

public class Application {

    public static void main(String[] args) {
        var library = new Facade(new FirstService(), new SecondService());
        //----------------------------------------------------------------
        library.run();
    }

}
