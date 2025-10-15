package pl.training.refactoring.creational.builder;

public class Director {

    private final ConnectionUrlBuilder connectionUrlBuilder;

    public Director(ConnectionUrlBuilder connectionUrlBuilder) {
        this.connectionUrlBuilder = connectionUrlBuilder;
    }

    public void run() {
        var connectionUrl = connectionUrlBuilder.build();
        System.out.println("Connection url: " + connectionUrl);
    }

}
