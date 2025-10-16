package pl.training.refactoring.patterns.behavioral.chainofresponsibility;

import lombok.extern.java.Log;

@Log
public class Logger extends Handler {

    public Logger(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(String request) {
        log.info(request);
        nextHandler.handleRequest(request);
    }

}
