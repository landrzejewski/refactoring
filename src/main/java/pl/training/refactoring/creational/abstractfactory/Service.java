package pl.training.refactoring.creational.abstractfactory;

public class Service {

    private final ConnectionAbstractFactory connectionAbstractFactory;

    public Service(ConnectionAbstractFactory connectionAbstractFactory) {
        this.connectionAbstractFactory = connectionAbstractFactory;
    }

    public void run() {
        var connection = connectionAbstractFactory.createConnection();
        var securedConnection = connectionAbstractFactory.createSecuredConnection();
        System.out.println("Connection: " + connection.getPort());
        System.out.println("Secured connection: "+ securedConnection.getPort());
    }

}
