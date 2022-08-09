package pl.training.refactoring.patterns.behavioral.visitior;

public class Employee extends Node {

    public Employee(String name) {
        super(name);
    }

    @Override
    public void add(Node node) {
        throw new IllegalStateException();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
