package pl.training.refactoring.patterns.behavioral.memento.accounts;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
public class Account {

    @NonNull
    UUID number;
    @Getter
    @Setter
    BigDecimal balance =  BigDecimal.ZERO;

    public void deposit(BigDecimal amount){
        balance = balance.add(amount);
    }

}
