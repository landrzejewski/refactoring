package pl.training.refactoring.patterns.behavioral.interpreter;

public class Minus implements BiOperator {

    @Override
    public double apply(double a, double b) {
        return a - b;
    }

}
