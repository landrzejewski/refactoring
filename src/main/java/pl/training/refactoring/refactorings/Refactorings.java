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
    void write(java.util.List<String[]> data);
    void save(String filename);
}

// Adapter - bridges the gap between interfaces
class PdfReportAdapter implements ReportWriter {
    private ThirdPartyPdfLib pdfLib;

    public PdfReportAdapter() {
        this.pdfLib = new ThirdPartyPdfLib();
    }

    // Adapter converts from our format to PDF library format
    public void write(java.util.List<String[]> data) {
        java.util.List<PdfRow> pdfRows = convertToPdfRows(data);
        pdfLib.createDocument(pdfRows);
    }

    public void save(String filename) {
        pdfLib.save(filename);
    }

    // Conversion logic encapsulated in adapter
    private java.util.List<PdfRow> convertToPdfRows(java.util.List<String[]> data) {
        java.util.List<PdfRow> pdfRows = new java.util.ArrayList<>();
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

/*
 * SOLUTION: Builder Pattern (GoF Creational)
 * - Separates construction of complex object from its representation
 * - Same construction process can create different representations
 * - Provides fluent, readable API
 * - Easy to add new optional parameters
 * - Can enforce invariants before object creation
 */
class HttpRequest {
    // Immutable fields
    private final String url;
    private final String method;
    private final String body;
    private final java.util.Map<String, String> headers;
    private final int timeout;
    private final boolean followRedirects;

    // Private constructor - can only be called by Builder
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.headers = new java.util.HashMap<>(builder.headers);
        this.timeout = builder.timeout;
        this.followRedirects = builder.followRedirects;
    }

    // Static nested Builder class
    public static class Builder {
        // Required parameters
        private final String url;

        // Optional parameters with defaults
        private String method = "GET";
        private String body = null;
        private java.util.Map<String, String> headers = new java.util.HashMap<>();
        private int timeout = 30000;
        private boolean followRedirects = true;

        // Constructor with required parameters only
        public Builder(String url) {
            this.url = url;
        }

        // Fluent setter methods return Builder for chaining
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder followRedirects(boolean follow) {
            this.followRedirects = follow;
            return this;
        }

        // Build method creates the actual object
        public HttpRequest build() {
            // Can validate here before creating object
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("URL is required");
            }
            return new HttpRequest(this);
        }
    }

    // Getters only - immutable object
    public String getUrl() { return url; }
    public String getMethod() { return method; }
    public String getBody() { return body; }
}

// Usage is now clean and readable:
// HttpRequest request = new HttpRequest.Builder("http://api.com")
//     .method("POST")
//     .body("{\"name\":\"John\"}")
//     .header("Content-Type", "application/json")
//     .timeout(5000)
//     .build();

// ============================================================================
// EXAMPLE 5: Introduce Composite (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Need to treat individual objects and compositions uniformly
 * - Client code must know if dealing with single item or collection
 * - Lots of instanceof checks and type casting
 * - Hard to add new composite types
 * - Recursive structures hard to implement
 */
class BadFileSystem {
    public long getSize(Object item) {
        if (item instanceof File) {
            // Handle single file
            return ((File) item).getSize();
        } else if (item instanceof Directory) {
            // Handle directory - must iterate
            long total = 0;
            Directory dir = (Directory) item;
            for (Object child : dir.getChildren()) {
                total += getSize(child); // Recursion with type checking
            }
            return total;
        }
        return 0;
    }

    public void display(Object item, int indent) {
        String spaces = " ".repeat(indent);

        if (item instanceof File) {
            File file = (File) item;
            System.out.println(spaces + file.getName() + " (" + file.getSize() + ")");
        } else if (item instanceof Directory) {
            Directory dir = (Directory) item;
            System.out.println(spaces + dir.getName() + "/");
            for (Object child : dir.getChildren()) {
                display(child, indent + 2); // More type checking
            }
        }
    }
}

class File {
    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() { return name; }
    public long getSize() { return size; }
}

class Directory {
    private String name;
    private java.util.List<Object> children = new java.util.ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(Object item) { children.add(item); }
    public String getName() { return name; }
    public java.util.List<Object> getChildren() { return children; }
}

/*
 * SOLUTION: Composite Pattern (GoF Structural)
 * - Compose objects into tree structures to represent part-whole hierarchies
 * - Lets clients treat individual objects and compositions uniformly
 * - Recursive composition made easy
 * - New component types easy to add
 */

// Component - common interface for leaf and composite
interface FileSystemComponent {
    String getName();
    long getSize();
    void display(int indent);

    // Default implementation for leaf nodes
    default void add(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add to a file");
    }

    default void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a file");
    }
}

// Leaf - represents end objects (no children)
class FileComponent implements FileSystemComponent {
    private String name;
    private long size;

    public FileComponent(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size; // Leaf returns its own size
    }

    public void display(int indent) {
        String spaces = " ".repeat(indent);
        System.out.println(spaces + name + " (" + size + " bytes)");
    }
}

// Composite - can contain other components (leaf or composite)
class DirectoryComponent implements FileSystemComponent {
    private String name;
    private java.util.List<FileSystemComponent> children = new java.util.ArrayList<>();

    public DirectoryComponent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Composite operation - sum of children
    public long getSize() {
        return children.stream()
                .mapToLong(FileSystemComponent::getSize)
                .sum();
    }

    public void display(int indent) {
        String spaces = " ".repeat(indent);
        System.out.println(spaces + name + "/ (" + getSize() + " bytes)");

        // Recursively display children - no type checking needed!
        for (FileSystemComponent child : children) {
            child.display(indent + 2);
        }
    }

    // Composite-specific operations
    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }
}

// Client code is now simple and uniform
class FileSystemClient {
    public void printStructure() {
        // Build file structure
        DirectoryComponent root = new DirectoryComponent("root");
        DirectoryComponent docs = new DirectoryComponent("docs");

        docs.add(new FileComponent("readme.txt", 1024));
        docs.add(new FileComponent("manual.pdf", 5120));

        root.add(docs);
        root.add(new FileComponent("config.xml", 512));

        // Treat both files and directories uniformly
        System.out.println("Total size: " + root.getSize());
        root.display(0);
    }
}

// ============================================================================
// EXAMPLE 6: Encapsulate Composite with Builder (Creational Pattern)
// ============================================================================

/*
 * PROBLEM: Complex composite structures hard to build
 * - Lots of boilerplate to create nested structures
 * - Easy to make mistakes in nesting
 * - Code doesn't reflect the structure visually
 */
class BadMenuBuilder {
    public Menu createMenu() {
        Menu root = new Menu("File");

        Menu newItem = new Menu("New");
        root.add(newItem);

        Menu openItem = new Menu("Open");
        root.add(openItem);

        Menu recentMenu = new Menu("Recent");
        Menu recent1 = new Menu("file1.txt");
        recentMenu.add(recent1);
        Menu recent2 = new Menu("file2.txt");
        recentMenu.add(recent2);
        root.add(recentMenu);

        // Hard to see the structure!
        return root;
    }
}

/*
 * SOLUTION: Fluent Builder for Composite
 * - Builder pattern combined with composite
 * - Chainable methods make structure visible
 * - Less error-prone
 */
class Menu {
    private String name;
    private java.util.List<Menu> children = new java.util.ArrayList<>();

    public Menu(String name) {
        this.name = name;
    }

    public void add(Menu child) {
        children.add(child);
    }

    public String getName() { return name; }
    public java.util.List<Menu> getChildren() { return children; }

    // Builder for fluent construction
    public static class Builder {
        private Menu menu;

        public Builder(String name) {
            this.menu = new Menu(name);
        }

        public Builder item(String name) {
            menu.add(new Menu(name));
            return this;
        }

        public Builder submenu(String name, java.util.function.Consumer<Builder> builderConsumer) {
            Builder subBuilder = new Builder(name);
            builderConsumer.accept(subBuilder);
            menu.add(subBuilder.build());
            return this;
        }

        public Menu build() {
            return menu;
        }
    }
}

// Usage now clearly shows structure
class MenuCreator {
    public Menu createMenu() {
        return new Menu.Builder("File")
                .item("New")
                .item("Open")
                .submenu("Recent", recent -> recent
                        .item("file1.txt")
                        .item("file2.txt"))
                .item("Save")
                .build();
        // Structure is immediately visible!
    }
}

// ============================================================================
// EXAMPLE 7: Replace Hard-Coded Notifications with Observer (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Tight coupling between subject and observers
 * - Subject knows about concrete observer classes
 * - Hard to add new observers
 * - Violates Open/Closed Principle
 * - Changes to observers require changing subject
 */
class BadStockTicker {
    private double price;
    private StockDisplay display;
    private StockLogger logger;
    private StockAlert alert;

    // Tightly coupled to concrete observers
    public BadStockTicker(StockDisplay display, StockLogger logger, StockAlert alert) {
        this.display = display;
        this.logger = logger;
        this.alert = alert;
    }

    public void setPrice(double price) {
        this.price = price;

        // Must manually notify each observer
        display.update(price);
        logger.update(price);
        alert.update(price);

        // Adding new observer requires modifying this method!
    }
}

class StockDisplay {
    public void update(double price) {
        System.out.println("Display: $" + price);
    }
}

class StockLogger {
    public void update(double price) {
        System.out.println("Log: Price changed to $" + price);
    }
}

class StockAlert {
    public void update(double price) {
        if (price > 100) {
            System.out.println("Alert: Price exceeded $100!");
        }
    }
}

/*
 * SOLUTION: Observer Pattern (GoF Behavioral)
 * - Define one-to-many dependency between objects
 * - When one object changes state, all dependents notified automatically
 * - Loose coupling between subject and observers
 * - Easy to add/remove observers at runtime
 */

// Observer interface
interface StockObserver {
    void update(double price);
}

// Subject (Observable)
class StockTicker {
    private double price;
    private java.util.List<StockObserver> observers = new java.util.ArrayList<>();

    // Dynamic observer management
    public void attach(StockObserver observer) {
        observers.add(observer);
    }

    public void detach(StockObserver observer) {
        observers.remove(observer);
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    // Notify all observers - no knowledge of concrete types
    private void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(price);
        }
    }

    public double getPrice() {
        return price;
    }
}

// Concrete Observers
class DisplayObserver implements StockObserver {
    public void update(double price) {
        System.out.println("Display: $" + price);
    }
}

class LoggerObserver implements StockObserver {
    public void update(double price) {
        System.out.println("Log: Price changed to $" + price);
    }
}

class AlertObserver implements StockObserver {
    private double threshold;

    public AlertObserver(double threshold) {
        this.threshold = threshold;
    }

    public void update(double price) {
        if (price > threshold) {
            System.out.println("Alert: Price exceeded $" + threshold + "!");
        }
    }
}

// Usage - easy to add/remove observers
class TradingApp {
    public void run() {
        StockTicker ticker = new StockTicker();

        // Attach observers dynamically
        ticker.attach(new DisplayObserver());
        ticker.attach(new LoggerObserver());
        ticker.attach(new AlertObserver(100));

        // Change price - all observers notified automatically
        ticker.setPrice(105);

        // Easy to add new observers without changing StockTicker!
    }
}

// ============================================================================
// EXAMPLE 8: Replace Conditional with Decorator (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Adding functionality through inheritance or conditionals
 * - Explosion of subclasses for every combination
 * - Or complex conditionals checking feature flags
 * - Hard to combine features dynamically
 * - Violates Single Responsibility Principle
 */

// Bad approach 1: Subclass explosion
class SimpleCoffee {
    public double getCost() { return 5.0; }
    public String getDescription() { return "Simple coffee"; }
}

class CoffeeWithMilk extends SimpleCoffee {
    public double getCost() { return 6.0; }
    public String getDescription() { return "Coffee with milk"; }
}

class CoffeeWithSugar extends SimpleCoffee {
    public double getCost() { return 5.5; }
    public String getDescription() { return "Coffee with sugar"; }
}

// What about coffee with milk AND sugar? Need another class!
class CoffeeWithMilkAndSugar extends SimpleCoffee {
    public double getCost() { return 6.5; }
    public String getDescription() { return "Coffee with milk and sugar"; }
}
// This grows exponentially with each new option!

// Bad approach 2: Conditional flags
class BadCoffee {
    private boolean hasMilk;
    private boolean hasSugar;
    private boolean hasWhip;

    public double getCost() {
        double cost = 5.0;
        // Conditionals everywhere!
        if (hasMilk) cost += 1.0;
        if (hasSugar) cost += 0.5;
        if (hasWhip) cost += 1.5;
        return cost;
    }

    public String getDescription() {
        String desc = "Coffee";
        // More conditionals!
        if (hasMilk) desc += " with milk";
        if (hasSugar) desc += " and sugar";
        if (hasWhip) desc += " and whip";
        return desc;
    }

    // Setters for all flags - messy!
    public void setMilk(boolean hasMilk) { this.hasMilk = hasMilk; }
    public void setSugar(boolean hasSugar) { this.hasSugar = hasSugar; }
    public void setWhip(boolean hasWhip) { this.hasWhip = hasWhip; }
}

/*
 * SOLUTION: Decorator Pattern (GoF Structural)
 * - Attach additional responsibilities to object dynamically
 * - Decorators provide flexible alternative to subclassing
 * - Can combine any decorators in any order
 * - Open/Closed Principle - open for extension, closed for modification
 */

// Component interface
interface Coffee {
    double getCost();
    String getDescription();
}

// Concrete component
class BasicCoffee implements Coffee {
    public double getCost() {
        return 5.0;
    }

    public String getDescription() {
        return "Coffee";
    }
}

// Decorator base class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }

    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
}

// Concrete decorators - each adds one feature
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost() + 1.0;
    }

    public String getDescription() {
        return super.getDescription() + ", milk";
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getDescription() {
        return super.getDescription() + ", sugar";
    }
}

class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }

    public double getCost() {
        return super.getCost() + 1.5;
    }

    public String getDescription() {
        return super.getDescription() + ", whipped cream";
    }
}

// Usage - combine decorators dynamically in any order!
class CoffeeShop {
    public void makeOrder() {
        // Simple coffee
        Coffee coffee1 = new BasicCoffee();
        System.out.println(coffee1.getDescription() + " = $" + coffee1.getCost());

        // Coffee with milk
        Coffee coffee2 = new MilkDecorator(new BasicCoffee());
        System.out.println(coffee2.getDescription() + " = $" + coffee2.getCost());

        // Coffee with milk and sugar - easy to combine!
        Coffee coffee3 = new SugarDecorator(new MilkDecorator(new BasicCoffee()));
        System.out.println(coffee3.getDescription() + " = $" + coffee3.getCost());

        // Coffee with everything - any combination possible!
        Coffee coffee4 = new WhipDecorator(
                new SugarDecorator(
                        new MilkDecorator(new BasicCoffee())
                )
        );
        System.out.println(coffee4.getDescription() + " = $" + coffee4.getCost());

        // No need for new classes or conditionals!
    }
}

// ============================================================================
// EXAMPLE 9: Replace Implicit Tree with Composite + Interpreter (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex expressions evaluated with conditionals
 * - Expression parsing logic mixed with evaluation
 * - Hard to add new operators
 * - Cannot represent expression tree
 * - Difficult to implement operations like printing, optimization
 */
class BadCalculator {
    public double evaluate(String expression) {
        // Ugly string parsing with conditionals
        if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            return Double.parseDouble(parts[0].trim()) +
                    Double.parseDouble(parts[1].trim());
        } else if (expression.contains("-")) {
            String[] parts = expression.split("-");
            return Double.parseDouble(parts[0].trim()) -
                    Double.parseDouble(parts[1].trim());
        } else if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            return Double.parseDouble(parts[0].trim()) *
                    Double.parseDouble(parts[1].trim());
        }
        // Nested expressions? Good luck!
        return Double.parseDouble(expression);
    }
}

/*
 * SOLUTION: Interpreter Pattern + Composite (GoF Behavioral + Structural)
 * - Represents grammar rules as class hierarchy
 * - Easy to add new expressions
 * - Tree structure allows multiple operations
 * - Separates parsing from interpretation
 */

// Abstract expression - component in composite
interface Expression {
    double interpret();
    String toString();
}

// Terminal expression - leaf node (number)
class NumberExpression implements Expression {
    private double value;

    public NumberExpression(double value) {
        this.value = value;
    }

    public double interpret() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

// Non-terminal expressions - composite nodes (operators)
class AddExpression implements Expression {
    private Expression left;
    private Expression right;

    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public double interpret() {
        return left.interpret() + right.interpret();
    }

    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }
}

class SubtractExpression implements Expression {
    private Expression left;
    private Expression right;

    public SubtractExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public double interpret() {
        return left.interpret() - right.interpret();
    }

    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}

class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public double interpret() {
        return left.interpret() * right.interpret();
    }

    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}

// Usage - build expression tree
class Calculator {
    public void calculate() {
        // Build: (5 + 3) * (10 - 2)
        Expression expr = new MultiplyExpression(
                new AddExpression(
                        new NumberExpression(5),
                        new NumberExpression(3)
                ),
                new SubtractExpression(
                        new NumberExpression(10),
                        new NumberExpression(2)
                )
        );

        System.out.println("Expression: " + expr.toString());
        System.out.println("Result: " + expr.interpret());

        // Easy to add new operations on the tree!
    }
}

// ============================================================================
// EXAMPLE 10: Replace Lazy Initialization with Singleton (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Global state managed incorrectly
 * - Not thread-safe
 * - Multiple instances possible
 * - No control over instantiation
 * - Resource waste if never used
 */
class BadDatabaseConnection {
    private static BadDatabaseConnection instance;

    // Public constructor - anyone can create instances!
    public BadDatabaseConnection() {
        System.out.println("Expensive database connection created");
    }

    // Not thread-safe!
    public static BadDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new BadDatabaseConnection();
            // Two threads could create two instances!
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }
}

/*
 * SOLUTION: Singleton Pattern (GoF Creational) - Thread-safe Lazy Holder
 * - Ensures class has only one instance
 * - Provides global point of access
 * - Lazy initialization (created when first used)
 * - Thread-safe without synchronization overhead
 * - Uses Java's class loading mechanism for thread safety
 */
class DatabaseConnection {
    // Private constructor prevents instantiation
    private DatabaseConnection() {
        System.out.println("Database connection established");
        // Expensive initialization here
    }

    // Static inner class - loaded only when accessed (lazy)
    // Java guarantees thread-safe class loading
    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    // Public access point
    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
        // Thread-safe, lazy, and efficient!
    }

    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }

    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
}

// ============================================================================
// EXAMPLE 11: Replace Type Code with Factory Method (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Object creation based on type codes
 * - Creation logic scattered in multiple places
 * - Type codes are error-prone (magic strings/numbers)
 * - Hard to add new types
 * - Violates Open/Closed Principle
 */
class BadAnimalCreator {
    public static final int DOG = 1;
    public static final int CAT = 2;
    public static final int BIRD = 3;

    public Animal createAnimal(int type, String name) {
        // Creation logic with conditionals
        if (type == DOG) {
            Animal dog = new Animal();
            dog.setType("Dog");
            dog.setName(name);
            dog.setSound("Woof");
            dog.setLegs(4);
            return dog;
        } else if (type == CAT) {
            Animal cat = new Animal();
            cat.setType("Cat");
            cat.setName(name);
            cat.setSound("Meow");
            cat.setLegs(4);
            return cat;
        } else if (type == BIRD) {
            Animal bird = new Animal();
            bird.setType("Bird");
            bird.setName(name);
            bird.setSound("Chirp");
            bird.setLegs(2);
            return bird;
        }
        return null;
    }
}

class Animal {
    private String type;
    private String name;
    private String sound;
    private int legs;

    public void setType(String type) { this.type = type; }
    public void setName(String name) { this.name = name; }
    public void setSound(String sound) { this.sound = sound; }
    public void setLegs(int legs) { this.legs = legs; }

    public void makeSound() {
        System.out.println(name + " says " + sound);
    }
}

/*
 * SOLUTION: Factory Method Pattern (GoF Creational)
 * - Define interface for creating objects
 * - Let subclasses decide which class to instantiate
 * - Encapsulates object creation
 * - Each type has its own creation logic
 */

// Product interface
interface AnimalInterface {
    void makeSound();
    String getName();
}

// Concrete products
class Dog implements AnimalInterface {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public void makeSound() {
        System.out.println(name + " says Woof!");
    }

    public String getName() {
        return name;
    }
}

class Cat implements AnimalInterface {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public void makeSound() {
        System.out.println(name + " says Meow!");
    }

    public String getName() {
        return name;
    }
}

class Bird implements AnimalInterface {
    private String name;

    public Bird(String name) {
        this.name = name;
    }

    public void makeSound() {
        System.out.println(name + " says Chirp!");
    }

    public String getName() {
        return name;
    }
}

// Creator - defines factory method
abstract class AnimalCreator {
    // Factory method - subclasses override this
    protected abstract AnimalInterface createAnimal(String name);

    // Template method using factory method
    public AnimalInterface orderAnimal(String name) {
        AnimalInterface animal = createAnimal(name);
        System.out.println("Created: " + animal.getName());
        return animal;
    }
}

// Concrete creators
class DogCreator extends AnimalCreator {
    protected AnimalInterface createAnimal(String name) {
        return new Dog(name);
    }
}

class CatCreator extends AnimalCreator {
    protected AnimalInterface createAnimal(String name) {
        return new Cat(name);
    }
}

class BirdCreator extends AnimalCreator {
    protected AnimalInterface createAnimal(String name) {
        return new Bird(name);
    }
}

// Alternative: Simple Factory (not in GoF but commonly used)
class AnimalFactory {
    public static AnimalInterface createAnimal(String type, String name) {
        switch (type.toLowerCase()) {
            case "dog":
                return new Dog(name);
            case "cat":
                return new Cat(name);
            case "bird":
                return new Bird(name);
            default:
                throw new IllegalArgumentException("Unknown animal type: " + type);
        }
    }
}

// ============================================================================
// EXAMPLE 12: Extract Proxy for Lazy Loading (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Expensive objects loaded eagerly
 * - Large images/documents loaded even if never displayed
 * - Wastes memory and time
 * - Application startup slow
 * - Cannot control access to real object
 */
class BadImageViewer {
    private java.util.List<HeavyImage> images = new java.util.ArrayList<>();

    public void loadImages(String[] filenames) {
        for (String filename : filenames) {
            // All images loaded immediately - expensive!
            images.add(new HeavyImage(filename));
        }
        System.out.println("Loaded " + images.size() + " images");
        // User might only view a few images!
    }

    public void display(int index) {
        images.get(index).display();
    }
}

class HeavyImage {
    private String filename;
    private byte[] data; // Large data

    public HeavyImage(String filename) {
        this.filename = filename;
        loadFromDisk(); // Expensive operation done immediately
    }

    private void loadFromDisk() {
        System.out.println("Loading large image from disk: " + filename);
        // Simulate expensive loading
        this.data = new byte[1000000]; // 1MB
    }

    public void display() {
        System.out.println("Displaying: " + filename);
    }
}

/*
 * SOLUTION: Proxy Pattern (GoF Structural) - Virtual Proxy
 * - Provides placeholder for expensive object
 * - Creates real object only when needed (lazy loading)
 * - Client code unchanged
 * - Can add access control, caching, logging
 */

// Subject interface
interface Image {
    void display();
}

// Real subject - expensive object
class RealImage implements Image {
    private String filename;
    private byte[] data;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading image from disk: " + filename);
        this.data = new byte[1000000];
    }

    public void display() {
        System.out.println("Displaying: " + filename);
    }
}

// Proxy - controls access to real subject
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage; // Loaded lazily

    public ImageProxy(String filename) {
        this.filename = filename;
        // Real image NOT created yet!
    }

    public void display() {
        // Create real image only when needed
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

// Client code uses proxy transparently
class ImageViewer {
    private java.util.List<Image> images = new java.util.ArrayList<>();

    public void loadImages(String[] filenames) {
        for (String filename : filenames) {
            // Proxies created quickly - no disk I/O yet!
            images.add(new ImageProxy(filename));
        }
        System.out.println("Loaded " + images.size() + " image proxies");
    }

    public void display(int index) {
        // Real image loaded only when displayed
        images.get(index).display();
    }
}

// ============================================================================
// EXAMPLE 13: Replace Conditional Complexity with Chain of Responsibility (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex conditional chain for request handling
 * - Hard to add/remove/reorder handlers
 * - Each handler tightly coupled to next
 * - Difficult to see handling flow
 * - Cannot dynamically configure chain
 */
class BadSupportSystem {
    public void handleRequest(String issue, int priority) {
        // Giant conditional chain
        if (priority <= 1) {
            System.out.println("Junior support handling: " + issue);
            // Simple issues
        } else if (priority <= 3) {
            System.out.println("Senior support handling: " + issue);
            // Medium issues
        } else if (priority <= 5) {
            System.out.println("Manager handling: " + issue);
            // Complex issues
        } else {
            System.out.println("Director handling: " + issue);
            // Critical issues
        }
        // Adding new level requires modifying this method
    }
}

/*
 * SOLUTION: Chain of Responsibility Pattern (GoF Behavioral)
 * - Chain handlers together
 * - Each handler decides to process or pass to next
 * - Decouples sender from receiver
 * - Can dynamically configure chain
 */

// Handler interface
abstract class SupportHandler {
    protected SupportHandler nextHandler;

    public void setNext(SupportHandler handler) {
        this.nextHandler = handler;
    }

    // Template method - defines handling flow
    public void handleRequest(SupportRequest request) {
        if (canHandle(request)) {
            process(request);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("No handler available for: " + request.getIssue());
        }
    }

    // Subclasses implement these
    protected abstract boolean canHandle(SupportRequest request);
    protected abstract void process(SupportRequest request);
}

// Request object
class SupportRequest {
    private String issue;
    private int priority;

    public SupportRequest(String issue, int priority) {
        this.issue = issue;
        this.priority = priority;
    }

    public String getIssue() { return issue; }
    public int getPriority() { return priority; }
}

// Concrete handlers
class JuniorSupport extends SupportHandler {
    protected boolean canHandle(SupportRequest request) {
        return request.getPriority() <= 1;
    }

    protected void process(SupportRequest request) {
        System.out.println("Junior Support handling: " + request.getIssue());
    }
}

class SeniorSupport extends SupportHandler {
    protected boolean canHandle(SupportRequest request) {
        return request.getPriority() <= 3;
    }

    protected void process(SupportRequest request) {
        System.out.println("Senior Support handling: " + request.getIssue());
    }
}

class ManagerSupport extends SupportHandler {
    protected boolean canHandle(SupportRequest request) {
        return request.getPriority() <= 5;
    }

    protected void process(SupportRequest request) {
        System.out.println("Manager handling: " + request.getIssue());
    }
}

class DirectorSupport extends SupportHandler {
    protected boolean canHandle(SupportRequest request) {
        return true; // Handles everything
    }

    protected void process(SupportRequest request) {
        System.out.println("Director handling critical issue: " + request.getIssue());
    }
}

// Usage - easy to configure chain
class SupportSystem {
    private SupportHandler chain;

    public SupportSystem() {
        // Build chain: Junior -> Senior -> Manager -> Director
        SupportHandler junior = new JuniorSupport();
        SupportHandler senior = new SeniorSupport();
        SupportHandler manager = new ManagerSupport();
        SupportHandler director = new DirectorSupport();

        junior.setNext(senior);
        senior.setNext(manager);
        manager.setNext(director);

        this.chain = junior;
        // Easy to reorder or add new handlers!
    }

    public void submitRequest(String issue, int priority) {
        SupportRequest request = new SupportRequest(issue, priority);
        chain.handleRequest(request);
    }
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