package pl.training.patterns.behavioral.chainofresponsibility;

public class Validator extends Handler {

    public Validator(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(String request) {
        if (request.length() < 3) {
            return;
        }
        nextHandler.handleRequest(request);
    }

}
