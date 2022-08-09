package pl.training.refactoring.patterns.creational.abstractfactory;

public interface SecuredConnection extends Connection {

    String getEncryptionAlgorithm();

}
