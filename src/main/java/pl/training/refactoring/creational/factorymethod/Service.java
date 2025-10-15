package pl.training.refactoring.creational.factorymethod;

public class Service {

    private final IdGeneratorFactory idGeneratorFactory;

    public Service(IdGeneratorFactory idGeneratorFactory) {
        this.idGeneratorFactory = idGeneratorFactory;
    }

    public void run() {
        var idGenerator = idGeneratorFactory.create();
        System.out.println("Id: " + idGenerator.getNext());
    }

}
