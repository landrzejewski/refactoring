package pl.training.patterns.creational.factorymethod;

public class ProductionIdGeneratorFactory implements IdGeneratorFactory {

    private final UuidGenerator uuidGenerator = new UuidGenerator();

    @Override
    public IdGenerator create() {
        return uuidGenerator;
    }

}
