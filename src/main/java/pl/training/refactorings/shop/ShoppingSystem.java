package pl.training.refactorings.shop;

import java.util.*;


public class ShoppingSystem {
    
    
    public static OrderProcessor processor = new OrderProcessor();
    public static List<Customer> customers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    
    public static void main(String[] args) {
        
        
        Product p1 = new Product();
        p1.id = "P001";
        p1.name = "Laptop";
        p1.price = 999.99;
        p1.stockQuantity = 50;
        p1.category = "Electronics";
        p1.description = "High performance laptop";
        p1.weight = 2.5;
        p1.supplier = "TechSupply Inc";
        products.add(p1);
        
        Product p2 = new Product();
        p2.id = "P002";
        p2.name = "Mouse";
        p2.price = 29.99;
        p2.stockQuantity = 200;
        p2.category = "Electronics";
        p2.description = "Wireless mouse";
        p2.weight = 0.2;
        p2.supplier = "TechSupply Inc";
        products.add(p2);
        
        Product p3 = new Product();
        p3.id = "P003";
        p3.name = "Desk Chair";
        p3.price = 199.99;
        p3.stockQuantity = 30;
        p3.category = "Furniture";
        p3.description = "Ergonomic office chair";
        p3.weight = 15.0;
        p3.supplier = "Office Furniture Co";
        products.add(p3);
        
        
        Customer c1 = new Customer("John Doe", "john@email.com", "555-1234",
                                  "123 Main St", "New York", "NY", "10001",
                                  500, true);
        customers.add(c1);
        
        Customer c2 = new Customer("Jane Smith", "jane@email.com", "555-5678",
                                  "456 Oak Ave", "Los Angeles", "CA", "90001",
                                  50, false);
        customers.add(c2);
        
        
        System.out.println("=== Processing Orders ===\n");
        
        
        processor.processOrder(
            c1.name, 
            p1.name, 
            1, 
            p1.price, 
            "credit",
            c1.email,
            c1.premium,
            c1.address
        );
        
        
        c1.addOrder("ORD001", p1.price);
        
        System.out.println("\n");
        
        
        processor.processOrder(
            c2.name,
            p2.name,
            5,
            p2.price,
            "debit",
            c2.email,
            c2.premium,
            c2.address
        );
        
        c2.addOrder("ORD002", p2.price * 5);
        
        System.out.println("\n");
        
        
        processor.processQuickOrder(c1.name, p3.name, 1);
        c1.addOrder("ORD003", p3.price);
        
        System.out.println("\n=== Reports ===\n");
        
        
        processor.generateReport(true);
        
        System.out.println("\n=== Customer Information ===\n");
        
        
        for (Customer customer : customers) {
            System.out.println("Name: " + customer.name);
            System.out.println("Email: " + customer.email);
            System.out.println("Premium: " + customer.premium);
            System.out.println("Loyalty Points: " + customer.loyaltyPoints);
            System.out.println("Last Order: " + customer.getLastOrder());
            System.out.println();
        }
        
        System.out.println("=== Product Information ===\n");
        
        
        for (Product product : products) {
            System.out.println(product.getProductInfo());
            System.out.println("Available: " + product.isAvailable());
            System.out.println("---");
        }
        
        
        demonstrateProblems();
    }
    
    
    public static void demonstrateProblems() {
        System.out.println("\n=== Demonstrating Coupling Issues ===\n");
        
        
        Product laptop = products.get(0);
        laptop.price = 899.99; 
        System.out.println("Updated laptop price: " + Product.formatPrice(laptop.price));
        
        
        if (laptop.canFulfillOrder(10)) {
            System.out.println("Order can be fulfilled");
        }
        
        System.out.println("Remaining stock: " + laptop.stockQuantity);
        
        
        Customer customer = customers.get(0);
        double discount1 = customer.calculateDiscount(150.0);
        double discount2 = laptop.applyDiscount(customer.premium, 150.0);
        
        System.out.println("Discount from Customer class: $" + discount1);
        System.out.println("Discount from Product class: $" + discount2);
        
        
        Customer newCustomer = new Customer("Test User", "invalid-email", 
                                          "555-0000", "789 Test St", 
                                          "Chicago", "IL", "60601", 
                                          0, false);
        
        if (!newCustomer.validateEmail()) {
            System.out.println("Invalid email detected!");
        }
        
        
        System.out.println("\nTotal orders processed: " + processor.orderCount);
        System.out.println("Total revenue: " + OrderProcessor.formatCurrency(processor.total));
    }
}
