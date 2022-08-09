package pl.training.refactoring.patterns.behavioral.strategy;

public class IncrementalIdGenerator implements IdGenerator {

    private static final String PATTERN = "%020d";

    private int counter;

    @Override
    public String getNext() {
        return PATTERN.formatted(++counter);
    }

}
