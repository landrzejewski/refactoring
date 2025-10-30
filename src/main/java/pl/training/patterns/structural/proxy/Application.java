package pl.training.patterns.structural.proxy;

import static java.util.Collections.emptyMap;

public class Application {

    public static void main(String[] args) {
        var paymentsService = new PaymentLoggerProxy(new PaymentSecurityProxy(new RemotePaymentsService()));
        //----------------------------------------------------------------
        paymentsService.pay(emptyMap());
    }

}
