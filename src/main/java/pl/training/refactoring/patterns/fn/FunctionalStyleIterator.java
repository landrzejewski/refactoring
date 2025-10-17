package pl.training.refactoring.patterns.fn;

import java.util.Arrays;

public class FunctionalStyleIterator {
    public static void main(String[] args) {
        Arrays.asList("one", "two", "three")
              .stream()
              .map(String::toUpperCase)
              .forEach(System.out::println);
    }
}
