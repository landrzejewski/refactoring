package pl.training.patterns.fn;

import java.util.stream.Stream;

public class FunctionalStyleIterator {
    public static void main(String[] args) {
        Stream.of("one", "two", "three")
              .map(String::toUpperCase)
              .forEach(System.out::println);
    }
}
