package pl.training.patterns.creational.builder;

public class PostgresSqlConnectionUrlBuilder implements ConnectionUrlBuilder {

    private static final String PROTOCOL = "postgres";
    private static final int PORT = 5432;

    private final GenericConnectionUrlBuilder builder = new GenericConnectionUrlBuilder();

    public PostgresSqlConnectionUrlBuilder() {
        builder.protocol(PROTOCOL);
        builder.port(PORT);
    }

    @Override
    public ConnectionUrl build() {
        return  builder.build();
    }

    public GenericConnectionUrlBuilder host(String host) {
        return builder.host(host);
    }

    public GenericConnectionUrlBuilder port(int port) {
        return builder.port(port);
    }

    public GenericConnectionUrlBuilder protocol(String protocol) {
        return builder.protocol(protocol);
    }

    public GenericConnectionUrlBuilder database(String database) {
        return builder.database(database);
    }

    public GenericConnectionUrlBuilder encoding(String encoding) {
        return builder.encoding(encoding);
    }

}
