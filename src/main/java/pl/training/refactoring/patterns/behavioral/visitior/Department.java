package pl.training.refactoring.patterns.behavioral.visitior;

public class Department extends Node {

    public Department(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }

}
