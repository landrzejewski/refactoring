package pl.training.refactoring.patterns.creational.abstractfactory.ftp;

import pl.training.refactoring.patterns.creational.abstractfactory.Connection;

public class FtpConnection implements Connection {

    @Override
    public int getPort() {
        return 50;
    }

}
