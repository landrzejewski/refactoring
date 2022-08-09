package pl.training.refactoring.patterns.behavioral.interpreter;

public class Multiply implements BiOperator {

    @Override
    public double apply(double firstValue, double secondValue) {
        return firstValue * secondValue;
    }

}
