package pl.training.refactoring.refactorings;

// ============================================================================
// EXAMPLE 1: Replace Conditional with Strategy Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Multiple conditionals scattered throughout the code
 * - Hard to add new loan types (violates Open/Closed Principle)
 * - Risk calculation logic is duplicated and hard to maintain
 * - Testing requires checking all conditional branches
 * - Code becomes longer and more complex with each new type
 */
class BadLoan {
    private static final int TERM_LOAN = 1;
    private static final int REVOLVER = 2;
    private static final int ADVISED_LINE = 3;

    private int type; // Type code - a code smell!
    private double notional;
    private double outstanding;
    private int rating;

    // This method will grow with every new loan type
    public double calculateCapital() {
        if (type == TERM_LOAN) {
            return notional * duration() * riskFactor();
        } else if (type == REVOLVER) {
            return (notional * 0.5 + outstanding) * duration() * riskFactor();
        } else if (type == ADVISED_LINE) {
            return notional * 0.3 * duration() * riskFactor();
        }
        return 0;
    }

    private double duration() { return 1.5; }
    private double riskFactor() { return rating * 0.01; }
}


// ============================================================================
// Supporting classes for examples
// ============================================================================

class Customer {
    private String name;
    private String email;
    private boolean vip;

    public Customer(String name, String email, boolean vip) {
        this.name = name;
        this.email = email;
        this.vip = vip;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isVIP() { return vip; }
}

class Product {
    private String name;
    private double price;
    private int stock;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }
}

class Order {
    private java.util.List<OrderItem> items = new java.util.ArrayList<>();
    private Customer customer;
    private String paymentMethod;

    public Order(Customer customer, String paymentMethod) {
        this.customer = customer;
        this.paymentMethod = paymentMethod;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public java.util.List<OrderItem> getItems() { return items; }
    public Customer getCustomer() { return customer; }
    public String getPaymentMethod() { return paymentMethod; }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return product.getPrice(); }
}

class PaymentProcessorFactory {
    static PaymentProcessor create(String method) {
        return new PaymentProcessor();
    }
}

class PaymentProcessor {
    void process(double amount) {
        System.out.println("Processing payment: $" + amount);
    }
}

class EmailService {
    static void send(String to, String subject, String body) {
        System.out.println("Email sent to: " + to);
    }
}