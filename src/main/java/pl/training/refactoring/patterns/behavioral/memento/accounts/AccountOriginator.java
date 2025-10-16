package pl.training.refactoring.patterns.behavioral.memento.accounts;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountOriginator {

    private static final String SEPARATOR = ";";

    public Memento createMemento(Account account){
        var state = account.number + SEPARATOR + account.balance;
        return new Memento(state);
    }

    public void restoreMemento(Memento memento, Account account){
        var values = memento.state.split(SEPARATOR);
        account.number = UUID.fromString(values[0]);
        account.balance = new BigDecimal(values[1]);
    }

}
