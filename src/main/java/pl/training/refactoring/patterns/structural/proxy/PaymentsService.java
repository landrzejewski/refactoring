package pl.training.refactoring.patterns.structural.proxy;

import java.util.Map;

public interface PaymentsService {

    void pay(Map<String, String> properties);

}
