package pl.training.refactorings.shop;

import java.util.*;


public class Customer {

    private String name;
    private Email email;
    private Phone phone;
    private Address address;
    private int loyaltyPoints;
    private boolean premium;
    private final List<String> orderHistory = new ArrayList<>();

    public Customer(String name, Email email, Phone phone, Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public void addLoyaltyPoints(int newPoints) {
        if (newPoints <= 0) {
            throw new IllegalArgumentException("New points must be positive");
        }
        loyaltyPoints += newPoints;
    }
    
    
    public void addOrder(String orderId, double amount) {
        orderHistory.add(orderId);
        
        
        int pointsEarned = (int)(amount / 10);
        loyaltyPoints += pointsEarned;
        
        
        if (loyaltyPoints > 1000 && !premium) {
            premium = true;
            System.out.println("Congratulations! You are now a premium member!");
            sendPromotionEmail();
        }
        
        
        saveToDatabase();
    }
    
    
    private void sendPromotionEmail() {
        System.out.println("Sending promotion email to: " + email);
        System.out.println("Subject: Welcome to Premium!");
        
    }
    
    
    private void saveToDatabase() {
        System.out.println("Saving customer " + name + " to database...");
        
    }


    
    public String getLastOrder() {
        if (orderHistory.isEmpty()) {
            return null;
        }
        return orderHistory.get(orderHistory.size() - 1);
    }
    
    
    public double calculateDiscount(double orderAmount) {
        double discount = 0;
        
        if (premium) {
            if (orderAmount > 200) {
                discount = orderAmount * 0.20;
            } else if (orderAmount > 100) {
                discount = orderAmount * 0.15;
            } else {
                discount = orderAmount * 0.10;
            }
        } else {
            if (orderAmount > 100) {
                discount = orderAmount * 0.05;
            }
        }
        
        return discount;
    }
}
