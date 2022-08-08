package pl.training.refactoring.newmovies;

import lombok.Value;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Value
public class Rental {

    MovieType type;
    LocalDate startDate;

    boolean isNewRelease() {
        return type == MovieType.NEW_RELEASE;
    }

    long periodInDays() {
        return DAYS.between(LocalDate.now(), startDate);
    }

}
