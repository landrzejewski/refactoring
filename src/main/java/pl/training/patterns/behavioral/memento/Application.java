package pl.training.patterns.behavioral.memento;

import lombok.extern.java.Log;
import pl.training.patterns.behavioral.memento.accounts.Account;
import pl.training.patterns.behavioral.memento.accounts.AccountOriginator;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Log
public class Application {

    public static void main(String[] args) {
        var originator = new AccountOriginator();
        var account = new Account(UUID.randomUUID());
        var snapshot = originator.createMemento(account);
        var caretaker = List.of(snapshot);
        account.deposit(BigDecimal.TEN);
        log.info("Deposited balance: " + account.getBalance());
        originator.restoreMemento(caretaker.getFirst(), account);
        log.info("Deposited balance: " + account.getBalance());
    }

}
