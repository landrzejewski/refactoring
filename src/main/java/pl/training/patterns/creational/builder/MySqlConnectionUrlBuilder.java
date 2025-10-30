package pl.training.patterns.creational.builder;

public class MySqlConnectionUrlBuilder extends GenericConnectionUrlBuilder {

    private static final String PROTOCOL = "mysql";
    private static final int PORT = 3306;

    public MySqlConnectionUrlBuilder() {
        connectionUrl.protocol = PROTOCOL;
        connectionUrl.port = PORT;
    }

}
