package pl.training.refactoring.patterns.creational.singleton;

public class Logger {

    private static final Logger LOGGER = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return LOGGER;
    }

    public void log(String message) {
        System.out.println("Info: " + message);
    }

}
