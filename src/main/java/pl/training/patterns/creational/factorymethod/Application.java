package pl.training.patterns.creational.factorymethod;

public class Application {

    public static void main(String[] args) {
        var idGeneratorFactory = new TestIdGeneratorFactory();
        var service = new Service(idGeneratorFactory);
        //------------------------------------------------------------------
        service.run();
    }

}
