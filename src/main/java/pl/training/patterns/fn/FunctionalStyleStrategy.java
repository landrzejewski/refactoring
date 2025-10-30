package pl.training.patterns.fn;

import java.util.function.Function;

public class FunctionalStyleStrategy {
    public static void main(String[] args) {
        Function<String, String> upperCaseStrategy = String::toUpperCase;
        Function<String, String> lowerCaseStrategy = String::toLowerCase;
        Function<String, String> reverseStrategy = s -> new StringBuilder(s).reverse().toString();

        String input = "Functional Patterns";

        executeStrategy("UPPER", input, upperCaseStrategy);
        executeStrategy("LOWER", input, lowerCaseStrategy);
        executeStrategy("REVERSE", input, reverseStrategy);
    }

    static void executeStrategy(String name, String input, Function<String, String> strategy) {
        System.out.println(name + ": " + strategy.apply(input));
    }
}
