package pl.training.patterns.creational.abstractfactory.ftp;

import pl.training.patterns.creational.abstractfactory.SecuredConnection;

public class SftpConnection implements SecuredConnection {

    @Override
    public int getPort() {
        return 22;
    }

    @Override
    public String getEncryptionAlgorithm() {
        return "AES";
    }

}
