package pl.training.refactoring.patterns.fn;

import java.util.function.Function;

public class FunctionalStyleChain {
    public static void main(String[] args) {
        Function<String, String> step1 = s -> s + " -> validate";
        Function<String, String> step2 = s -> s + " -> authenticate";
        Function<String, String> step3 = s -> s + " -> log";

        Function<String, String> chain = step1.andThen(step2).andThen(step3);

        System.out.println(chain.apply("Request"));
    }
}
