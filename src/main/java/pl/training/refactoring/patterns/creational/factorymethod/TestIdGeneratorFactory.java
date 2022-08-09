package pl.training.refactoring.patterns.creational.factorymethod;

public class TestIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator create() {
        return new IncrementalIdGenerator();
    }

}
