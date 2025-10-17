package pl.training.refactoring.patterns.fn;

import java.util.function.Consumer;

public class FunctionalStyleTemplate {
    static class Processor {
        void process(Consumer<String> ... step) {
            System.out.println("Start processing...");
            step[0].accept("data");
            System.out.println("Finish processing!");
        }
    }

    public static void main(String[] args) {
        Processor p = new Processor();

        // hook behavior supplied by lambda
        p.process(d -> System.out.println("Custom step with: " + d));
    }
}
