package pl.training.refactoring.creational.abstractfactory.ftp;

import pl.training.refactoring.creational.abstractfactory.Connection;
import pl.training.refactoring.creational.abstractfactory.ConnectionAbstractFactory;
import pl.training.refactoring.creational.abstractfactory.SecuredConnection;

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
