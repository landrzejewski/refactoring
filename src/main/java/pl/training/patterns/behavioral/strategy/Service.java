package pl.training.patterns.behavioral.strategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Service {

    private final IdGenerator idGenerator;

    public void run() {
        var id = idGenerator.getNext();
    }

}
