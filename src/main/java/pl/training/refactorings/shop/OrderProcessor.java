package pl.training.refactorings.shop;

import java.util.*;
import java.text.*;


public class OrderProcessor {
    
    
    public List<String> orders = new ArrayList<>();
    public double total = 0;
    public String lastCustomer;
    public int orderCount = 0;
    
    
    private static final int DISCOUNT_THRESHOLD = 100;
    
    
    public void processOrder(String customerName, String productName, int quantity, 
                            double price, String paymentType, String email, 
                            boolean isPremium, String address) {
        
        
        if (customerName != null) {
            if (productName != null) {
                if (quantity > 0) {
                    if (price > 0) {
                        
                        
                        double subtotal = quantity * price;
                        double discount = 0;
                        
                        
                        if (isPremium && subtotal > 100) {
                            discount = subtotal * 0.15;
                        } else if (isPremium) {
                            discount = subtotal * 0.10;
                        } else if (subtotal > 100) {
                            discount = subtotal * 0.05;
                        }
                        
                        double tax = (subtotal - discount) * 0.08;
                        double finalPrice = subtotal - discount + tax;
                        
                        
                        String orderRecord = customerName + "," + productName + "," + 
                                           quantity + "," + finalPrice + "," + 
                                           new Date().toString();
                        orders.add(orderRecord);
                        
                        total += finalPrice;
                        orderCount++;
                        lastCustomer = customerName;
                        
                        
                        if (paymentType.equals("credit")) {
                            System.out.println("Processing credit card for " + finalPrice);
                            
                            if (finalPrice > 1000) {
                                System.out.println("Requires additional verification");
                            }
                        } else if (paymentType.equals("debit")) {
                            System.out.println("Processing debit card for " + finalPrice);
                        } else if (paymentType.equals("cash")) {
                            System.out.println("Cash payment received: " + finalPrice);
                        }
                        
                        
                        if (email != null && !email.isEmpty()) {
                            String subject = "Order Confirmation";
                            String body = "Dear " + customerName + ",\n" +
                                        "Your order for " + quantity + " x " + productName + 
                                        " has been confirmed.\n" +
                                        "Subtotal: $" + subtotal + "\n" +
                                        "Discount: $" + discount + "\n" +
                                        "Tax: $" + tax + "\n" +
                                        "Total: $" + finalPrice + "\n" +
                                        "Shipping to: " + address;
                            
                            System.out.println("Sending email to: " + email);
                            System.out.println("Subject: " + subject);
                            System.out.println("Body: " + body);
                        }
                        
                        
                        updateInventory(productName, quantity);
                        
                        
                        System.out.println("Order processed successfully for " + customerName);
                        
                    } else {
                        System.out.println("Error: Invalid price");
                    }
                } else {
                    System.out.println("Error: Invalid quantity");
                }
            } else {
                System.out.println("Error: Product name is null");
            }
        } else {
            System.out.println("Error: Customer name is null");
        }
    }
    
    
    public void processQuickOrder(String customer, String product, int qty) {
        double price = 29.99; 
        double subtotal = qty * price;
        double discount = 0;
        
        if (subtotal > 100) {
            discount = subtotal * 0.05;
        }
        
        double tax = (subtotal - discount) * 0.08;
        double finalPrice = subtotal - discount + tax;
        
        String orderRecord = customer + "," + product + "," + 
                           qty + "," + finalPrice + "," + 
                           new Date().toString();
        orders.add(orderRecord);
        
        total += finalPrice;
        orderCount++;
        lastCustomer = customer;
        
        System.out.println("Quick order processed for " + customer);
    }
    
    
    public void updateInventory(String product, int quantity) {
        
        System.out.println("Inventory updated: " + product + " reduced by " + quantity);
        product = product.toLowerCase(); 
    }
    
    
    public void generateReport(boolean detailed) {
        if (detailed) {
            System.out.println("=== DETAILED ORDER REPORT ===");
            System.out.println("Total Orders: " + orderCount);
            System.out.println("Total Revenue: $" + total);
            System.out.println("Last Customer: " + lastCustomer);
            System.out.println("\nAll Orders:");
            for (String order : orders) {
                System.out.println(order);
            }
        } else {
            System.out.println("Orders: " + orderCount + ", Revenue: $" + total);
        }
    }
    
    
    public static String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return "$" + df.format(amount);
    }
    
    
    public String getCustomerDisplayName(String firstName, String lastName, String title) {
        String result = "";
        if (title != null && !title.isEmpty()) {
            result += title + " ";
        }
        if (firstName != null) {
            result += firstName + " ";
        }
        if (lastName != null) {
            result += lastName;
        }
        return result.trim();
    }
}
