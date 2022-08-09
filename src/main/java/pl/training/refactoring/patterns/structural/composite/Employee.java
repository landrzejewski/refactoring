package pl.training.refactoring.patterns.structural.composite;

public class Employee extends Node {

    public Employee(String name) {
        super(name);
    }

    @Override
    public void add(Node node) {
        throw new IllegalStateException();
    }

    @Override
    public void printInfo() {
        System.out.println("Employee:" + name);
    }

}
