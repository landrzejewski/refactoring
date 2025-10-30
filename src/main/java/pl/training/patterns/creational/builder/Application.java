package pl.training.patterns.creational.builder;

public class Application {

    public static void main(String[] args) {
        var builder = new PostgresSqlConnectionUrlBuilder()
                .host("localhost")
                .database("test");
        var director = new Director(builder);
        //------------------------------------------------------------------
        director.run();
    }

}
