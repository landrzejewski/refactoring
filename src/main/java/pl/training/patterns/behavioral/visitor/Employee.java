package pl.training.patterns.behavioral.visitor;

public class Employee extends Node {

    public Employee(String name) {
        super(name);
    }

    @Override
    public void addChild(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
