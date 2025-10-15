package pl.training.refactoring.creational.factorymethod;

public class TestIdGeneratorFactory implements IdGeneratorFactory {

    private final int counter;

    public TestIdGeneratorFactory(int counter) {
        this.counter = counter;
    }

    @Override
    public IdGenerator create() {
        return new IncrementalIdGenerator(counter);
    }

}
