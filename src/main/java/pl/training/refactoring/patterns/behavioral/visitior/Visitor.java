package pl.training.refactoring.patterns.behavioral.visitior;

public interface Visitor {

    default void visit(Department department) {
    }

    default void visit(Employee employee) {
    }

}
