package pl.training.refactoring.patterns.structural.composite;

public class Application {

    public static void main(String[] args) {
        var company = new Node("Sages");
        var marketimngDepartment = new Department("Marketing");
        var itDepartment = new Department("IT");

        itDepartment.add(new Employee("Jan"));
        marketimngDepartment.add(new Employee("Ania"));
        marketimngDepartment.add(new Employee("Marta"));

        company.add(itDepartment);
        company.add(marketimngDepartment);
        //--------------------------------------------------------------
        company.printInfo();
        itDepartment.printInfo();
    }

}
