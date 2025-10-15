package pl.training.refactoring.creational.builder;

public class Application {

    public static void main(String[] args) {
        var builder = new MySqlConnectionUrlBuilder()
                .host("localhost")
                .database("test");
        var director = new ServiceDirector(builder);
        //------------------------------------------------------------------
        director.run();
    }

}
