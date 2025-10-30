package pl.training.patterns.creational.abstractfactory.ftp;

import pl.training.patterns.creational.abstractfactory.Connection;
import pl.training.patterns.creational.abstractfactory.ConnectionAbstractFactory;
import pl.training.patterns.creational.abstractfactory.SecuredConnection;

public class FtpConnectionFactory implements ConnectionAbstractFactory {

    @Override
    public Connection createConnection() {
        return new FtpConnection();
    }

    @Override
    public SecuredConnection createSecuredConnection() {
        return new SftpConnection();
    }

}
