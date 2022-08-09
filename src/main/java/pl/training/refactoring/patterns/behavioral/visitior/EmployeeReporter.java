package pl.training.refactoring.patterns.behavioral.visitior;

import lombok.Getter;

public class EmployeeReporter implements Visitor {

    @Getter
    private int count;

    @Override
    public void visit(Employee employee) {
        count++;
    }

}
