package pl.training.refactoring.creational.abstractfactory;

public interface ConnectionAbstractFactory {

    Connection createConnection();

    SecuredConnection createSecuredConnection();

}
