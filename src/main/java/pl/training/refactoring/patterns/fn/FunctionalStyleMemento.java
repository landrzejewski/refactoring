package pl.training.refactoring.patterns.fn;

import java.util.function.Supplier;

public class FunctionalStyleMemento {
    static class Originator {
        private String state;

        public void setState(String state) {
            this.state = state;
            System.out.println("Set state: " + state);
        }

        public Supplier<String> save() {
            String snapshot = state;
            return () -> snapshot; // closure capturing state
        }

        public void restore(Supplier<String> memento) {
            this.state = memento.get();
            System.out.println("Restored: " + state);
        }
    }

    public static void main(String[] args) {
        Originator o = new Originator();
        o.setState("A");
        var saved = o.save();
        o.setState("B");
        o.restore(saved);
    }
}
