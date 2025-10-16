package pl.training.refactoring.patterns.behavioral.interpreter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Operation implements Expression {

    private final Expression left;
    private final Expression right;
    private final BiOperator operator;

    @Override
    public double evaluate(Map<String, Double> context) {
        return operator.apply(left.evaluate(context), right.evaluate(context));
    }

}
