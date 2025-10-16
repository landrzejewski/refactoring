package pl.training.refactoring.patterns.behavioral.comman;

public class Application {

    public static void main(String[] args) {
        var invoker = new Invoker();
        invoker.invoke(new PrintTime());
        invoker.invoke(new ConnectTopServer());
    }

}
