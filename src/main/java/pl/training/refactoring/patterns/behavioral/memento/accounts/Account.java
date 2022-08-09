package pl.training.refactoring.patterns.behavioral.memento.accounts;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;

@ToString
@RequiredArgsConstructor
public class Account {

    @NonNull
    UUID number;
    @Getter
    @Setter
    BigDecimal balance = ZERO;

    public void deposit(BigDecimal value) {
        balance = balance.add(value);
    }

}
