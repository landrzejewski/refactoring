package pl.training.patterns.creational.abstractfactory;

public interface SecuredConnection extends Connection {

    String getEncryptionAlgorithm();

}
