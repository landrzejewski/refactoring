package pl.training.patterns.structural.composite;

public class Application {

    public static void main(String[] args) {
        var mainDepartment = new Department("Main Department");
        var marketingDepartment = new Department("Marketing Department");
        var itDepartment = new Department("IT Department");

        mainDepartment.addChild(marketingDepartment);
        mainDepartment.addChild(itDepartment);

        itDepartment.addChild(new Employee("Kowalski"));
        marketingDepartment.addChild(new Employee("Nowak"));
        itDepartment.addChild(new Employee("Karlsson"));
        //----------------------------------------------------------

        itDepartment.printInfo();
    }

}
