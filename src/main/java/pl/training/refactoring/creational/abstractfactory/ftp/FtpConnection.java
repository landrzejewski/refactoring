package pl.training.refactoring.creational.abstractfactory.ftp;

import pl.training.refactoring.creational.abstractfactory.Connection;

public class FtpConnection implements Connection {

    @Override
    public int getPort() {
        return 50;
    }

}
