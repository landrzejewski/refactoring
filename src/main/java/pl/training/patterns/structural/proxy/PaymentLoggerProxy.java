package pl.training.patterns.structural.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;

@Log
@RequiredArgsConstructor
public class PaymentLoggerProxy implements PaymentsService {

    private final PaymentsService paymentsService;

    @Override
    public void pay(Map<String, String> properties) {
        paymentsService.pay(properties);
        log.info("Payment completed");
    }

}
