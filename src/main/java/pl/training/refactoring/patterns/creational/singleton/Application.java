package pl.training.refactoring.patterns.creational.singleton;

import lombok.extern.java.Log;

@Log
public class Application {

    public static void main(String[] args) {
        var logger = Logger.getInstance();
        log.info("Is same: " + logger.equals(Logger.getInstance()));
        logger.log("Success");
    }

}
