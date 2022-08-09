package pl.training.refactoring.patterns.creational.abstractfactory;

public interface ConnectionAbstractFactory {

    Connection createConnection();

    SecuredConnection createSecuredConnection();

}
