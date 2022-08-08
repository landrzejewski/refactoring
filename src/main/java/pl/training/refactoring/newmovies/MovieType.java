package pl.training.refactoring.newmovies;

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

}
