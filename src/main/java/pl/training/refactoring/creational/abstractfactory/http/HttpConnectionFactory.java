package pl.training.refactoring.creational.abstractfactory.http;

import pl.training.refactoring.creational.abstractfactory.Connection;
import pl.training.refactoring.creational.abstractfactory.ConnectionAbstractFactory;
import pl.training.refactoring.creational.abstractfactory.SecuredConnection;

public class HttpConnectionFactory implements ConnectionAbstractFactory {

    @Override
    public Connection createConnection() {
        return new HttpConnection();
    }

    @Override
    public SecuredConnection createSecuredConnection() {
        return new HttpsConnection();
    }

}
