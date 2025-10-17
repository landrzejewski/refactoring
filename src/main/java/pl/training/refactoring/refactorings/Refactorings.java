package pl.training.refactoring.refactorings;

// ============================================================================
// EXAMPLE 1: Replace Conditional with Strategy Pattern (Behavioral - GoF)
// ============================================================================

import java.util.ArrayList;
import java.util.List;

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

/*
 * SOLUTION: Strategy Pattern (GoF Behavioral)
 * - Defines family of algorithms, encapsulates each one, makes them interchangeable
 * - Strategy lets algorithm vary independently from clients that use it
 */
interface CapitalStrategy {
    double calculateCapital(Loan loan);
}

class TermLoanStrategy implements CapitalStrategy {
    public double calculateCapital(Loan loan) {
        return loan.getNotional() * loan.duration() * loan.riskFactor();
    }
}

class RevolverStrategy implements CapitalStrategy {
    public double calculateCapital(Loan loan) {
        return (loan.getNotional() * 0.5 + loan.getOutstanding())
                * loan.duration() * loan.riskFactor();
    }
}

class Loan {
    private CapitalStrategy strategy;
    private double notional;
    private double outstanding;
    private int rating;

    public Loan(CapitalStrategy strategy, double notional, double outstanding, int rating) {
        this.strategy = strategy;
        this.notional = notional;
        this.outstanding = outstanding;
        this.rating = rating;
    }

    public double calculateCapital() {
        return strategy.calculateCapital(this);
    }

    public double getNotional() { return notional; }
    public double getOutstanding() { return outstanding; }
    double duration() { return 1.5; }
    double riskFactor() { return rating * 0.01; }
}

// ============================================================================
// EXAMPLE 2: Extract Adapter (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Incompatible interfaces force ugly code
 * - Client expects one interface but third-party library provides another
 * - Conversion logic scattered throughout client code
 * - Hard to switch to different library
 * - Code duplication when multiple clients need same conversion
 */
class BadReportGenerator {
    public void generateReport(java.util.List<String[]> data) {
        // We need to use third-party PDF library, but it expects different format
        ThirdPartyPdfLib pdfLib = new ThirdPartyPdfLib();

        // Ugly conversion code scattered in client
        java.util.List<PdfRow> pdfRows = new java.util.ArrayList<>();
        for (String[] row : data) {
            PdfRow pdfRow = new PdfRow();
            for (String cell : row) {
                pdfRow.addCell(new PdfCell(cell));
            }
            pdfRows.add(pdfRow);
        }

        // Finally can use the library
        pdfLib.createDocument(pdfRows);
        pdfLib.save("report.pdf");
    }
}

// Third-party library with incompatible interface
class ThirdPartyPdfLib {
    public void createDocument(java.util.List<PdfRow> rows) {
        System.out.println("Creating PDF with " + rows.size() + " rows");
    }
    public void save(String filename) {
        System.out.println("Saving to " + filename);
    }
}

class PdfRow {
    private java.util.List<PdfCell> cells = new java.util.ArrayList<>();
    public void addCell(PdfCell cell) { cells.add(cell); }
    public java.util.List<PdfCell> getCells() { return cells; }
}

class PdfCell {
    private String content;
    public PdfCell(String content) { this.content = content; }
    public String getContent() { return content; }
}

/*
 * SOLUTION: Adapter Pattern (GoF Structural)
 * - Converts interface of a class into another interface clients expect
 * - Lets classes work together that couldn't otherwise because of incompatible interfaces
 * - Encapsulates conversion logic in one place
 */

// Target interface - what our client expects
interface ReportWriter {
    void write(List<String[]> data);
    void save(String filename);
}

// Adapter - bridges the gap between interfaces
class PdfReportAdapter implements ReportWriter {
    private ThirdPartyPdfLib pdfLib;

    public PdfReportAdapter() {
        this.pdfLib = new ThirdPartyPdfLib();
    }

    // Adapter converts from our format to PDF library format
    public void write(List<String[]> data) {
        List<PdfRow> pdfRows = convertToPdfRows(data);
        pdfLib.createDocument(pdfRows);
    }

    public void save(String filename) {
        pdfLib.save(filename);
    }

    // Conversion logic encapsulated in adapter
    private List<PdfRow> convertToPdfRows(List<String[]> data) {
        List<PdfRow> pdfRows = new ArrayList<>();
        for (String[] row : data) {
            PdfRow pdfRow = new PdfRow();
            for (String cell : row) {
                pdfRow.addCell(new PdfCell(cell));
            }
            pdfRows.add(pdfRow);
        }
        return pdfRows;
    }
}

// Now client code is clean and simple
class ReportGenerator {
    private ReportWriter writer;

    public ReportGenerator(ReportWriter writer) {
        this.writer = writer; // Can use any writer implementation
    }

    public void generateReport(java.util.List<String[]> data) {
        writer.write(data); // No conversion code!
        writer.save("report.pdf");
    }
}

// ============================================================================
// EXAMPLE 3: Introduce Template Method (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Similar algorithms with duplicated structure
 * - Copy-paste programming leads to duplication
 * - Hard to maintain - changes needed in multiple places
 * - Algorithm structure not explicit
 * - Difficult to enforce consistent process
 */
class BadCsvDataExporter {
    public void exportCustomers(java.util.List<Customer> customers) {
        // Open file
        System.out.println("Opening customers.csv");

        // Write header
        System.out.println("Name,Email,VIP");

        // Write data - customer specific
        for (Customer c : customers) {
            System.out.println(c.getName() + "," + c.getEmail() + "," + c.isVIP());
        }

        // Close file
        System.out.println("File closed");
    }

    public void exportProducts(java.util.List<Product> products) {
        // Open file - DUPLICATED!
        System.out.println("Opening products.csv");

        // Write header - DUPLICATED STRUCTURE!
        System.out.println("Name,Price");

        // Write data - product specific
        for (Product p : products) {
            System.out.println(p.getName() + "," + p.getPrice());
        }

        // Close file - DUPLICATED!
        System.out.println("File closed");
    }

    // More exportXXX methods with same structure...
}

/*
 * SOLUTION: Template Method Pattern (GoF Behavioral)
 * - Defines skeleton of algorithm in base class
 * - Lets subclasses override specific steps without changing structure
 * - Enforces consistent algorithm structure
 * - "Hollywood Principle" - Don't call us, we'll call you
 */

// Abstract class defines the template
abstract class DataExporter {
    // Template method - defines algorithm structure (final to prevent override)
    public final void export() {
        openFile();
        writeHeader();
        writeData();
        closeFile();
    }

    // Common steps with default implementation
    private void openFile() {
        System.out.println("Opening " + getFileName());
    }

    private void closeFile() {
        System.out.println("File closed");
    }

    // Abstract methods - subclasses must implement (hooks)
    protected abstract void writeHeader();
    protected abstract void writeData();
    protected abstract String getFileName();
}

// Concrete implementation for customers
class CustomerExporter extends DataExporter {
    private java.util.List<Customer> customers;

    public CustomerExporter(java.util.List<Customer> customers) {
        this.customers = customers;
    }

    protected void writeHeader() {
        System.out.println("Name,Email,VIP");
    }

    protected void writeData() {
        for (Customer c : customers) {
            System.out.println(c.getName() + "," + c.getEmail() + "," + c.isVIP());
        }
    }

    protected String getFileName() {
        return "customers.csv";
    }
}

// Concrete implementation for products
class ProductExporter extends DataExporter {
    private java.util.List<Product> products;

    public ProductExporter(java.util.List<Product> products) {
        this.products = products;
    }

    protected void writeHeader() {
        System.out.println("Name,Price");
    }

    protected void writeData() {
        for (Product p : products) {
            System.out.println(p.getName() + "," + p.getPrice());
        }
    }

    protected String getFileName() {
        return "products.csv";
    }
}

// ============================================================================
// EXAMPLE 4: Replace Constructors with Builder (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Complex object construction with many parameters
 * - Telescoping constructors (multiple overloaded constructors)
 * - Hard to remember parameter order
 * - Cannot represent optional parameters well
 * - Immutability hard to achieve
 * - Error-prone when parameters have same type
 */
class BadHttpRequest {
    private String url;
    private String method;
    private String body;
    private java.util.Map<String, String> headers;
    private int timeout;
    private boolean followRedirects;

    // Telescoping constructors - nightmare!
    public BadHttpRequest(String url) {
        this(url, "GET");
    }

    public BadHttpRequest(String url, String method) {
        this(url, method, null);
    }

    public BadHttpRequest(String url, String method, String body) {
        this(url, method, body, new java.util.HashMap<>());
    }

    public BadHttpRequest(String url, String method, String body,
                          java.util.Map<String, String> headers) {
        this(url, method, body, headers, 30000);
    }

    public BadHttpRequest(String url, String method, String body,
                          java.util.Map<String, String> headers, int timeout) {
        this(url, method, body, headers, timeout, true);
    }

    // The "real" constructor with all parameters
    public BadHttpRequest(String url, String method, String body,
                          java.util.Map<String, String> headers,
                          int timeout, boolean followRedirects) {
        this.url = url;
        this.method = method;
        this.body = body;
        this.headers = headers;
        this.timeout = timeout;
        this.followRedirects = followRedirects;
    }

    // Usage is confusing:
    // new BadHttpRequest("url", "POST", null, headers, 5000, false)
    // What does 5000 mean? What is false?
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