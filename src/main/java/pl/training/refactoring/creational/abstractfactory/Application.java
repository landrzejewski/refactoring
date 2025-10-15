package pl.training.refactoring.creational.abstractfactory;

import pl.training.refactoring.creational.abstractfactory.http.HttpConnectionFactory;

public class Application {

    public static void main(String[] args) {
        var connectionFactory = new HttpConnectionFactory();
        var service = new Service(connectionFactory);
        //------------------------------------------------------------------
        service.run();
    }

}
