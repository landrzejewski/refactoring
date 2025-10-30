package pl.training.patterns.behavioral.chainofresponsibility;

public abstract class Handler {

    protected Handler nextHandler;

    public abstract void handleRequest(String request);

}
