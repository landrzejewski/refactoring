package pl.training.refactoring.patterns.structural.proxy;

import lombok.extern.java.Log;

import java.util.Map;

@Log
public class RemotePaymentsService implements PaymentsService {

    @Override
    public void pay(Map<String, String> properties) {
        log.info("Payment started");
    }

}
