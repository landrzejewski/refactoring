package pl.training.patterns.behavioral.interpreter;

public class Multiply implements BiOperator {

    @Override
    public double apply(double a, double b) {
        return a * b;
    }

}
