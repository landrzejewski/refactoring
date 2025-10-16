package pl.training.refactoring.patterns.behavioral.chainofresponsibility;

public class Application {

    public static void main(String[] args) {
        var chain = new Validator(new Logger(new Processor()));
        chain.handleRequest("Test");


        new Thread();
    }

}
