package pl.training.refactoring.patterns.structural.composite;

public class Department extends Node {

    public Department(String name) {
        super(name);
    }

    public void printInfo() {
        System.out.println("Department: " + name);
        children.forEach(Node::printInfo);
    }

}
