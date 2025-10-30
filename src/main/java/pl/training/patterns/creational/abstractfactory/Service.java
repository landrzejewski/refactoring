package pl.training.patterns.creational.abstractfactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class Service {

    private final ConnectionAbstractFactory connectionAbstractFactory;

    public void run() {
        var connection = connectionAbstractFactory.createConnection();
        var securedConnection = connectionAbstractFactory.createSecuredConnection();
        log.info("Connection: " + connection.getPort());
        log.info("Secured connection: "+ securedConnection.getPort());
    }

}
