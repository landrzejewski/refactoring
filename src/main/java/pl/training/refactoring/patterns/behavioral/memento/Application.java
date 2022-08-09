package pl.training.refactoring.patterns.behavioral.memento;

import lombok.extern.java.Log;
import pl.training.refactoring.patterns.behavioral.memento.accounts.Account;
import pl.training.refactoring.patterns.behavioral.memento.accounts.AccountOriginator;

import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.TEN;

@Log
public class Application {

    public static void main(String[] args) {
        var originator = new AccountOriginator();
        var account = new Account(UUID.randomUUID());
        var snapshot = originator.takeSnapshot(account);
        var caretaker = List.of(snapshot);

        account.deposit(TEN);
        log.info("Actual: " + account);
        originator.restoreSnapshot(caretaker.get(0), account);
        log.info("Actual: " + account);
    }

}
