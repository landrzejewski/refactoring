package pl.training.refactoring.patterns.behavioral.visitor;

public class Department extends Node {

    public Department(String name) {
        super(name);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }

}
