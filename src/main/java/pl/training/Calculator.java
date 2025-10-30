package pl.training;

public class Calculator {

    public int add(int leftHandSide, int rightHandSide) {
        return leftHandSide + rightHandSide;
    }

    public int substract(int leftHandSide, int rightHandSide) {
        return leftHandSide - rightHandSide;
    }

    public int divide(int leftHandSide, int rightHandSide) {
        if (rightHandSide == 0) {
            throw new IllegalArgumentException();
        }
        return leftHandSide / rightHandSide;
    }

}
