package pl.training.patterns.creational.abstractfactory;

public interface ConnectionAbstractFactory {

    Connection createConnection();

    SecuredConnection createSecuredConnection();

}
