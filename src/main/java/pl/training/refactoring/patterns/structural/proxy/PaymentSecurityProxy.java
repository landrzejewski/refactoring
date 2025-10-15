package pl.training.refactoring.patterns.structural.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;

@Log
@RequiredArgsConstructor
public class PaymentSecurityProxy implements PaymentsService {

    private final PaymentsService paymentsService;

    @Override
    public void pay(Map<String, String> properties) {
        log.info("Checking security");
        paymentsService.pay(properties);
    }

}
