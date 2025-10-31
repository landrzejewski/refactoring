package pl.training.refactorings.shop;


public class Product {
    
    
    public String id;
    public String name;
    public double price;
    public int stockQuantity;
    public String category;
    public String description;
    public double weight;
    public String supplier;
    
    
    
    
    public void updateProduct(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stock;
        
        
        System.out.println("Product updated: " + name);
        
        
        if (price < 0) {
            this.price = 0;
            System.out.println("Warning: Invalid price, set to 0");
        }
        
        
        logUpdate();
    }
    
    
    public boolean canFulfillOrder(int requestedQuantity) {
        if (requestedQuantity <= 0) {
            System.out.println("Error: Invalid quantity");
            return false;
        }
        
        if (stockQuantity < requestedQuantity) {
            System.out.println("Error: Insufficient stock");
            notifySupplier(); 
            return false;
        }
        
        
        stockQuantity -= requestedQuantity;
        System.out.println("Stock reduced. Remaining: " + stockQuantity);
        
        return true;
    }
    
    
    private void notifySupplier() {
        System.out.println("Sending notification to supplier: " + supplier);
        System.out.println("Product " + name + " is low in stock!");
    }
    
    
    private void logUpdate() {
        System.out.println("[LOG] Product " + id + " updated at " + 
                          new java.util.Date());
    }
    
    
    public double calculateShippingCost(String destination) {
        double baseCost = 5.0;
        double weightCost = weight * 0.5;
        double total = baseCost + weightCost;
        
        
        if (destination.equals("international")) {
            total *= 3;
        } else if (destination.equals("express")) {
            total *= 2;
        }
        
        
        if (price > 100) {
            total = 0; 
        }
        
        return total;
    }
    
    
    public String getProductInfo() {
        String info = "";
        info += "Product ID: " + id + "\n";
        info += "Name: " + name + "\n";
        info += "Price: $" + price + "\n";
        info += "Stock: " + stockQuantity + "\n";
        info += "Category: " + category + "\n";
        info += "Weight: " + weight + " kg\n";
        info += "Supplier: " + supplier + "\n";
        info += "Description: " + description + "\n";
        return info;
    }
    
    
    public double applyDiscount(boolean isPremiumCustomer, double orderTotal) {
        double discount = 0;
        
        if (isPremiumCustomer) {
            if (orderTotal > 200) {
                discount = price * 0.20;
            } else if (orderTotal > 100) {
                discount = price * 0.15;
            } else {
                discount = price * 0.10;
            }
        } else {
            if (orderTotal > 100) {
                discount = price * 0.05;
            }
        }
        
        return discount;
    }
    
    
    public static String formatPrice(double price) {
        return "$" + String.format("%.2f", price);
    }
    
    
    public boolean isAvailable() {
        if (category.equals("discontinued")) {
            return false;
        }
        if (category.equals("seasonal") && !isCurrentSeason()) {
            return false;
        }
        return stockQuantity > 0;
    }
    
    
    private boolean isCurrentSeason() {
        int month = java.time.LocalDate.now().getMonthValue();
        
        return month >= 6 && month <= 8; 
    }
}
