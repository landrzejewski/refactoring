package pl.training.refactoring.refactorings.newmovies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MovieType {

    REGULAR(2, 2, 1.5),
    CHILDREN(3, 1.5, 1.5),
    NEW_RELEASE(0, 0, 3);

    private final int freeRentalPeriodInDays;
    private final double initialCost;
    private final double costPerDay;

    double getValueFor(long periodInDays) {
        return getInitialCost() + getValueForPeriod(periodInDays);
    }

    private double getValueForPeriod(long periodInDays) {
        return (periodInDays - getFreeRentalPeriodInDays()) * getCostPerDay();
    }

}
