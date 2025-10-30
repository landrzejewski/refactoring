package pl.training.patterns.creational.factorymethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class Service {

    private final IdGeneratorFactory idGeneratorFactory;

    public void run() {
        var idGenerator = idGeneratorFactory.create();
        log.info("Id: " + idGenerator.getNext());
    }

}
