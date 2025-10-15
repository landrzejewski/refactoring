package pl.training.refactoring.creational.factorymethod;

public class Application {

    public static void main(String[] args) {
        var idGeneratorFactory = new ProductionIdGeneratorFactory();
        var service = new Service(idGeneratorFactory);
        //---------------------------------------------------------------
        service.run();
    }

}
