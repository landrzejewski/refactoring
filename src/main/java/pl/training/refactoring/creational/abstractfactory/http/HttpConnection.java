package pl.training.refactoring.creational.abstractfactory.http;

import pl.training.refactoring.creational.abstractfactory.Connection;

public class HttpConnection implements Connection {

    @Override
    public int getPort() {
        return 80;
    }

}
