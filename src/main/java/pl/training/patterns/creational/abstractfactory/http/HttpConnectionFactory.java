package pl.training.patterns.creational.abstractfactory.http;

import pl.training.patterns.creational.abstractfactory.Connection;
import pl.training.patterns.creational.abstractfactory.ConnectionAbstractFactory;
import pl.training.patterns.creational.abstractfactory.SecuredConnection;

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
