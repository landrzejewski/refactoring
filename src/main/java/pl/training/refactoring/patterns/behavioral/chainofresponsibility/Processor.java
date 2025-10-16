package pl.training.refactoring.patterns.behavioral.chainofresponsibility;

import lombok.extern.java.Log;

@Log
public class Processor extends Handler {

    @Override
    public void handleRequest(String request) {
        log.info("Processing: " + request);
    }

}
