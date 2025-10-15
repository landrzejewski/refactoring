package pl.training.refactoring.creational.builder;

public class ConnectionUrl {

    String host;
    int port;
    String protocol;
    String database;
    String encoding;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDatabase() {
        return database;
    }

    public String getEncoding() {
        return encoding;
    }

    @Override
    public String toString() {
        return "jdbc:%s://%s:%d/%s?encoding=%s".formatted(protocol, host, port, database, encoding);
    }

}
