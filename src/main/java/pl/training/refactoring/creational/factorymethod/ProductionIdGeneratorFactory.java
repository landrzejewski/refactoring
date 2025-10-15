package pl.training.refactoring.creational.factorymethod;

public class ProductionIdGeneratorFactory implements IdGeneratorFactory {

    @Override
    public IdGenerator create() {
        return new UuidGenerator();
    }

}
