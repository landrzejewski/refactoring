package pl.training.refactorings.shop;

import java.util.*;


public class Customer {
    
    
    public String name;
    public String email;
    public String phone;
    public String address;
    public String city;
    public String state;
    public String zip;
    public int loyaltyPoints;
    public boolean premium;
    public List<String> orderHistory;
    
    
    public Customer(String name, String email, String phone, String address, 
                   String city, String state, String zip, int loyaltyPoints, 
                   boolean premium) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.loyaltyPoints = loyaltyPoints;
        this.premium = premium;
        this.orderHistory = new ArrayList<>();
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
    
    
    public boolean validateEmail() {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        if (!parts[1].contains(".")) {
            return false;
        }
        return true;
    }
    
    
    public String getFormattedAddress() {
        String result = address + "\n";
        result += city + ", " + state + " " + zip;
        return result;
    }
    
    
    public String getDisplayName() {
        String[] nameParts = name.split(" ");
        if (nameParts.length > 1) {
            return nameParts[0] + " " + nameParts[nameParts.length - 1];
        }
        return name;
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
