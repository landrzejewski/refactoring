package pl.training.refactoring.patterns.structural.facade;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Facade {

    private final FirstService firstService;
    private final SecondService secondService;

    public void run() {
        firstService.run();
        secondService.run();
    }

}
