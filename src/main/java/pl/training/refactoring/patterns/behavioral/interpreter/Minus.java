package pl.training.refactoring.patterns.behavioral.interpreter;

public class Minus implements BiOperator {

    @Override
    public double apply(double firstValue, double secondValue) {
        return firstValue - secondValue;
    }

}
