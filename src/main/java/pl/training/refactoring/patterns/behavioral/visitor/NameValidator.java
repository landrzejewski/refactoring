package pl.training.refactoring.patterns.behavioral.visitor;

public class NameValidator implements Visitor {

    @Override
    public void visit(Department department) {
        if (department.name.length() < 3) {
            System.out.println("Department name too short: " + department.name);
        }
    }

}
