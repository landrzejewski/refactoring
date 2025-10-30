package pl.training.patterns.creational.singleton;

public class Logger {

    private static Logger LOGGER;

    private Logger() {
    }

    public synchronized static Logger getInstance() {
        if (LOGGER == null) {
            LOGGER = new Logger();
        }
        return LOGGER;
    }

    public void log(String message) {
        System.out.println("Info: " + message);
    }

}
