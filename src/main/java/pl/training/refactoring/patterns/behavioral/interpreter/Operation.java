package pl.training.refactoring.patterns.behavioral.interpreter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Operation implements Expression {

    private final Expression firstExpression;
    private final Expression secondExpression;
    private final BiOperator biOperator;

    @Override
    public double evaluate(Map<String, Double> context) {
        return biOperator.apply(firstExpression.evaluate(context), secondExpression.evaluate(context));
    }

}
