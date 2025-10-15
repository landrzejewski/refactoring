package pl.training.refactoring.patterns.creational.builder;

import lombok.Getter;

@Getter
public class ConnectionUrl {

    String host;
    int port;
    String protocol;
    String database;
    String encoding;

    @Override
    public String toString() {
        return "jdbc:%s://%s:%d/%s?encoding=%s".formatted(protocol, host, port, database, encoding);
    }

}
