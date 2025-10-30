package pl.training.patterns.creational.abstractfactory.http;

import pl.training.patterns.creational.abstractfactory.SecuredConnection;

public class HttpsConnection implements SecuredConnection {

    @Override
    public int getPort() {
        return 443;
    }

    @Override
    public String getEncryptionAlgorithm() {
        return "AES";
    }

}
