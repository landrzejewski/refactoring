package pl.training.refactoring.patterns.fn;

import java.util.function.Supplier;

public class FunctionalStyleProxy {
    public static void main(String[] args) {
        Supplier<String> realService = () -> "Real data";

        Supplier<String> proxy = () -> {
            System.out.println("Checking access...");
            String result = realService.get();
            System.out.println("Logging access.");
            return result;
        };

        System.out.println(proxy.get());
    }
}
