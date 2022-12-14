package pl.training.refactoring.patterns.creational.abstractfactory;

import pl.training.refactoring.patterns.creational.abstractfactory.ftp.FtpConnectionFactory;

public class Application {

    public static void main(String[] args) {
        var connectionFactory = new FtpConnectionFactory();
        var service = new Service(connectionFactory);
        //------------------------------------------------------------------
        service.run();
    }

}
