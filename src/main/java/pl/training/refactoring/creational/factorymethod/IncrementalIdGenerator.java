package pl.training.refactoring.creational.factorymethod;

public class IncrementalIdGenerator implements IdGenerator {

    private final String PATTERN = "%020d";

    private int counter;

    public IncrementalIdGenerator(int counter) {
        this.counter = counter;
    }

    @Override
    public String getNext() {
        return PATTERN.formatted(++counter);
    }

}
