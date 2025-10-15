package pl.training.refactoring.patterns.creational.builder;

public class GenericConnectionUrlBuilder implements ConnectionUrlBuilder {

    protected static final String ENCODING = "UTF-8";

    protected final ConnectionUrl connectionUrl = new ConnectionUrl();

    public GenericConnectionUrlBuilder() {
        connectionUrl.encoding = ENCODING;
    }

    @Override
    public ConnectionUrl build() {
        return connectionUrl;
    }

    protected GenericConnectionUrlBuilder host(String host) {
        connectionUrl.host = host;
        return this;
    }

    protected GenericConnectionUrlBuilder port(int port) {
        connectionUrl.port = port;
        return this;
    }

    protected GenericConnectionUrlBuilder protocol(String protocol) {
        connectionUrl.protocol = protocol;
        return this;
    }

    protected GenericConnectionUrlBuilder database(String database) {
        connectionUrl.database = database;
        return this;
    }

    protected GenericConnectionUrlBuilder encoding(String encoding) {
        connectionUrl.encoding = encoding;
        return this;
    }

}
