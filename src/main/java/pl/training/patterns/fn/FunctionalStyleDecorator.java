package pl.training.patterns.fn;

import java.util.function.Function;

public class FunctionalStyleDecorator {
    public static void main(String[] args) {
        Function<String, String> base = s -> s;
        Function<String, String> trim = String::trim;
        Function<String, String> upper = String::toUpperCase;
        Function<String, String> addBrackets = s -> "[" + s + "]";

        Function<String, String> decorated =
                base.andThen(trim)
                    .andThen(upper)
                    .andThen(addBrackets);

        System.out.println(decorated.apply("   decorator pattern   "));
    }
}
