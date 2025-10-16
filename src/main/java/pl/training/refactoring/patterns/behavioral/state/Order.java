package pl.training.refactoring.patterns.behavioral.state;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Order {

    private final MovieType movieType;

    public double getTotalValue(long periodInDays) {
        return movieType.getValueFor(periodInDays);
    }

}
