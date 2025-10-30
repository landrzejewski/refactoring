package pl.training.patterns.structural.composite;

public class Employee extends Node {

    public Employee(String name) {
        super(name);
    }

    @Override
    public void addChild(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printInfo() {
        System.out.println(" - Employee " + name);
    }

}
