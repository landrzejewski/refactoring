package pl.training.refactoring.patterns.behavioral.observer;

public class Application {

    public static void main(String[] args) {
        var subject = new EventsBus();
        var observer = new Logger();
        subject.addConsumer(observer);
        subject.publish(new ServerEvent("Started"));
    }

}
