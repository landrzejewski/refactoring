package pl.training.patterns.creational.abstractfactory.http;

import pl.training.patterns.creational.abstractfactory.Connection;

public class HttpConnection implements Connection {

    @Override
    public int getPort() {
        return 80;
    }

}
