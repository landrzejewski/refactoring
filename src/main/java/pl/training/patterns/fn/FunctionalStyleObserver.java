package pl.training.patterns.fn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FunctionalStyleObserver {
    static class EventSource {
        private final List<Consumer<String>> observers = new ArrayList<>();

        public void subscribe(Consumer<String> observer) {
            observers.add(observer);
        }

        public void publish(String event) {
            observers.forEach(o -> o.accept(event));
        }
    }

    public static void main(String[] args) {
        EventSource source = new EventSource();

        source.subscribe(msg -> System.out.println("Observer 1 received: " + msg));
        source.subscribe(msg -> System.out.println("Observer 2 received: " + msg.toUpperCase()));

        source.publish("Functional Observer pattern with lambdas!");
    }
}
