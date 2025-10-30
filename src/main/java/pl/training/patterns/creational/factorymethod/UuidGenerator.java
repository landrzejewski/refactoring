package pl.training.patterns.creational.factorymethod;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {

    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }

}
