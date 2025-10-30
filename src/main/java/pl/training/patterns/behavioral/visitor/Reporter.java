package pl.training.patterns.behavioral.visitor;

public class Reporter implements Visitor {

    @Override
    public void visit(Department department) {
        System.out.println("Department: " + department.name);
    }

    @Override
    public void visit(Employee employee) {
        System.out.println(" - Employee " + employee.name);
    }

}
