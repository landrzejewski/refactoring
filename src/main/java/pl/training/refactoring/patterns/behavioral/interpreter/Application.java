package pl.training.refactoring.patterns.behavioral.interpreter;

import lombok.extern.java.Log;

import java.util.HashMap;
import java.util.Map;

@Log
public class Application {

    private static final Map<String, BiOperator> operators = new HashMap<>();

    private static BiOperator create(String name) {
        return operators.computeIfAbsent(name, Application::operatorFactory);
    }

    private static BiOperator operatorFactory(String name) {
        return switch (name) {
            case "plus" -> new Plus();
            case "minus" -> new Minus();
            case "multiply" -> new Multiply();
            default -> throw new IllegalArgumentException();
        };
    }

    public static void main(String[] args) {
        // 2a * 3 + 1 gdzie a = 4

        var expression = new Operation(
                new Operation(
                        new Operation(new Literal(2), new Variable("a"), create("multiply")),
                        new Literal(3),
                        create("multiply")),
                new Literal(1),
                create("plus"));

        log.info("Result: " + expression.evaluate(Map.of("a", 4.0)));
    }

}
