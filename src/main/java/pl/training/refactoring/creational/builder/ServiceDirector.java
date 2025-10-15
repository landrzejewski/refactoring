package pl.training.refactoring.creational.builder;

public class ServiceDirector {

    private final ConnectionUrlBuilder connectionUrlBuilder;

    public ServiceDirector(ConnectionUrlBuilder connectionUrlBuilder) {
        this.connectionUrlBuilder = connectionUrlBuilder;
    }

    public void run() {
        var connectionUrl = connectionUrlBuilder.build();
        System.out.println("Connection url: " + connectionUrl);
    }

}
