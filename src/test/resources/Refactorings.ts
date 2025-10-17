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
    private static readonly TERM_LOAN = 1;
    private static readonly REVOLVER = 2;
    private static readonly ADVISED_LINE = 3;

    constructor(
        private type: number,
        private notional: number,
        private outstanding: number,
        private rating: number
    ) {}

    // This method will grow with every new loan type
    calculateCapital(): number {
        if (this.type === BadLoan.TERM_LOAN) {
            return this.notional * this.duration() * this.riskFactor();
        } else if (this.type === BadLoan.REVOLVER) {
            return (this.notional * 0.5 + this.outstanding) * this.duration() * this.riskFactor();
        } else if (this.type === BadLoan.ADVISED_LINE) {
            return this.notional * 0.3 * this.duration() * this.riskFactor();
        }
        return 0;
    }

    private duration(): number { return 1.5; }
    private riskFactor(): number { return this.rating * 0.01; }
}

/*
 * SOLUTION: Strategy Pattern (GoF Behavioral)
 * - Defines family of algorithms, encapsulates each one, makes them interchangeable
 * - Strategy lets algorithm vary independently from clients that use it
 */
interface CapitalStrategy {
    calculateCapital(loan: Loan): number;
}

class TermLoanStrategy implements CapitalStrategy {
    calculateCapital(loan: Loan): number {
        return loan.notional * loan.duration() * loan.riskFactor();
    }
}

class RevolverStrategy implements CapitalStrategy {
    calculateCapital(loan: Loan): number {
        return (loan.notional * 0.5 + loan.outstanding)
            * loan.duration() * loan.riskFactor();
    }
}

class AdvisedLineStrategy implements CapitalStrategy {
    calculateCapital(loan: Loan): number {
        return loan.notional * 0.3 * loan.duration() * loan.riskFactor();
    }
}

class Loan {
    constructor(
        private strategy: CapitalStrategy,
        public readonly notional: number,
        public readonly outstanding: number,
        private rating: number
    ) {}

    calculateCapital(): number {
        return this.strategy.calculateCapital(this);
    }

    duration(): number { return 1.5; }
    riskFactor(): number { return this.rating * 0.01; }
}

// ============================================================================
// EXAMPLE 2: Extract Adapter (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Incompatible interfaces force ugly code
 * - Client expects one interface but third-party library provides another
 * - Conversion logic scattered throughout client code
 * - Hard to switch to different library
 */
class BadReportGenerator {
    generateReport(data: string[][]): void {
        const pdfLib = new ThirdPartyPdfLib();

        // Ugly conversion code scattered in client
        const pdfRows: PdfRow[] = [];
        for (const row of data) {
            const pdfRow = new PdfRow();
            for (const cell of row) {
                pdfRow.addCell(new PdfCell(cell));
            }
            pdfRows.push(pdfRow);
        }

        pdfLib.createDocument(pdfRows);
        pdfLib.save('report.pdf');
    }
}

// Third-party library with incompatible interface
class ThirdPartyPdfLib {
    createDocument(rows: PdfRow[]): void {
        console.log(`Creating PDF with ${rows.length} rows`);
    }

    save(filename: string): void {
        console.log(`Saving to ${filename}`);
    }
}

class PdfRow {
    private cells: PdfCell[] = [];

    addCell(cell: PdfCell): void {
        this.cells.push(cell);
    }

    getCells(): PdfCell[] {
        return this.cells;
    }
}

class PdfCell {
    constructor(public readonly content: string) {}
}

/*
 * SOLUTION: Adapter Pattern (GoF Structural)
 * - Converts interface of a class into another interface clients expect
 * - Lets classes work together that couldn't otherwise
 * - Encapsulates conversion logic in one place
 */

// Target interface - what our client expects
interface ReportWriter {
    write(data: string[][]): void;
    save(filename: string): void;
}

// Adapter - bridges the gap between interfaces
class PdfReportAdapter implements ReportWriter {
    private pdfLib: ThirdPartyPdfLib;

    constructor() {
        this.pdfLib = new ThirdPartyPdfLib();
    }

    write(data: string[][]): void {
        const pdfRows = this.convertToPdfRows(data);
        this.pdfLib.createDocument(pdfRows);
    }

    save(filename: string): void {
        this.pdfLib.save(filename);
    }

    private convertToPdfRows(data: string[][]): PdfRow[] {
        const pdfRows: PdfRow[] = [];
        for (const row of data) {
            const pdfRow = new PdfRow();
            for (const cell of row) {
                pdfRow.addCell(new PdfCell(cell));
            }
            pdfRows.push(pdfRow);
        }
        return pdfRows;
    }
}

// Now client code is clean
class ReportGenerator {
    constructor(private writer: ReportWriter) {}

    generateReport(data: string[][]): void {
        this.writer.write(data);
        this.writer.save('report.pdf');
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
 */
class BadCsvDataExporter {
    exportCustomers(customers: Customer[]): void {
        console.log('Opening customers.csv');
        console.log('Name,Email,VIP');

        for (const c of customers) {
            console.log(`${c.name},${c.email},${c.isVIP}`);
        }

        console.log('File closed');
    }

    exportProducts(products: Product[]): void {
        // DUPLICATED structure!
        console.log('Opening products.csv');
        console.log('Name,Price');

        for (const p of products) {
            console.log(`${p.name},${p.price}`);
        }

        console.log('File closed');
    }
}

/*
 * SOLUTION: Template Method Pattern (GoF Behavioral)
 * - Defines skeleton of algorithm in base class
 * - Lets subclasses override specific steps without changing structure
 */
abstract class DataExporter {
    // Template method - defines algorithm structure
    export(): void {
        this.openFile();
        this.writeHeader();
        this.writeData();
        this.closeFile();
    }

    private openFile(): void {
        console.log(`Opening ${this.getFileName()}`);
    }

    private closeFile(): void {
        console.log('File closed');
    }

    // Abstract methods - subclasses must implement
    protected abstract writeHeader(): void;
    protected abstract writeData(): void;
    protected abstract getFileName(): string;
}

class CustomerExporter extends DataExporter {
    constructor(private customers: Customer[]) {
        super();
    }

    protected writeHeader(): void {
        console.log('Name,Email,VIP');
    }

    protected writeData(): void {
        for (const c of this.customers) {
            console.log(`${c.name},${c.email},${c.isVIP}`);
        }
    }

    protected getFileName(): string {
        return 'customers.csv';
    }
}

class ProductExporter extends DataExporter {
    constructor(private products: Product[]) {
        super();
    }

    protected writeHeader(): void {
        console.log('Name,Price');
    }

    protected writeData(): void {
        for (const p of this.products) {
            console.log(`${p.name},${p.price}`);
        }
    }

    protected getFileName(): string {
        return 'products.csv';
    }
}

// ============================================================================
// EXAMPLE 4: Replace Constructors with Builder (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Complex object construction with many parameters
 * - Many optional parameters lead to confusing constructors
 * - Hard to remember parameter order
 * - Cannot represent optional parameters well
 */
class BadHttpRequest {
    constructor(
        public readonly url: string,
        public readonly method: string = 'GET',
        public readonly body?: string,
        public readonly headers: Record<string, string> = {},
        public readonly timeout: number = 30000,
        public readonly followRedirects: boolean = true
    ) {
        // TypeScript default params help but still messy
        // new BadHttpRequest('url', 'POST', undefined, {}, 5000, false)
        // What does 5000 mean? What is false?
    }
}

/*
 * SOLUTION: Builder Pattern (GoF Creational)
 * - Separates construction of complex object from its representation
 * - Provides fluent, readable API
 * - Easy to add new optional parameters
 */
class HttpRequest {
    constructor(
        public readonly url: string,
        public readonly method: string,
        public readonly body?: string,
        public readonly headers?: Readonly<Record<string, string>>,
        public readonly timeout?: number,
        public readonly followRedirects?: boolean
    ) {}

    static builder(url: string): HttpRequestBuilder {
        return new HttpRequestBuilder(url);
    }
}

class HttpRequestBuilder {
    private method: string = 'GET';
    private body?: string;
    private headers: Record<string, string> = {};
    private timeout: number = 30000;
    private followRedirects: boolean = true;

    constructor(private url: string) {}

    // Fluent interface - returns this for chaining
    withMethod(method: string): this {
        this.method = method;
        return this;
    }

    withBody(body: string): this {
        this.body = body;
        return this;
    }

    withHeader(key: string, value: string): this {
        this.headers[key] = value;
        return this;
    }

    withTimeout(timeout: number): this {
        this.timeout = timeout;
        return this;
    }

    withFollowRedirects(follow: boolean): this {
        this.followRedirects = follow;
        return this;
    }

    build(): HttpRequest {
        if (!this.url) {
            throw new Error('URL is required');
        }

        return new HttpRequest(
            this.url,
            this.method,
            this.body,
            { ...this.headers },
            this.timeout,
            this.followRedirects
        );
    }
}

// Usage:
// const request = HttpRequest.builder('http://api.com')
//     .withMethod('POST')
//     .withBody('{"name":"John"}')
//     .withHeader('Content-Type', 'application/json')
//     .withTimeout(5000)
//     .build();

// ============================================================================
// EXAMPLE 5: Introduce Composite (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Need to treat individual objects and compositions uniformly
 * - Client must know if dealing with single item or collection
 * - Lots of type checking
 */
class BadFileSystem {
    getSize(item: any): number {
        if ('size' in item && typeof item.size === 'number') {
            // It's a file
            return item.size;
        } else if ('children' in item && Array.isArray(item.children)) {
            // It's a directory
            let total = 0;
            for (const child of item.children) {
                total += this.getSize(child);
            }
            return total;
        }
        return 0;
    }
}

/*
 * SOLUTION: Composite Pattern (GoF Structural)
 * - Compose objects into tree structures
 * - Lets clients treat individual objects and compositions uniformly
 */
interface FileSystemComponent {
    readonly name: string;
    getSize(): number;
    display(indent: number): void;
}

// Leaf - represents end objects
class FileComponent implements FileSystemComponent {
    constructor(
        public readonly name: string,
        private size: number
    ) {}

    getSize(): number {
        return this.size;
    }

    display(indent: number): void {
        console.log(`${' '.repeat(indent)}${this.name} (${this.size} bytes)`);
    }
}

// Composite - can contain other components
class DirectoryComponent implements FileSystemComponent {
    private children: FileSystemComponent[] = [];

    constructor(public readonly name: string) {}

    getSize(): number {
        return this.children.reduce((sum, child) => sum + child.getSize(), 0);
    }

    display(indent: number): void {
        console.log(`${' '.repeat(indent)}${this.name}/ (${this.getSize()} bytes)`);
        for (const child of this.children) {
            child.display(indent + 2);
        }
    }

    add(component: FileSystemComponent): void {
        this.children.push(component);
    }

    remove(component: FileSystemComponent): void {
        const index = this.children.indexOf(component);
        if (index !== -1) {
            this.children.splice(index, 1);
        }
    }
}

// ============================================================================
// EXAMPLE 6: Replace Hard-Coded Notifications with Observer (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Tight coupling between subject and observers
 * - Subject knows about concrete observer classes
 * - Hard to add new observers
 * - Changes to observers require changing subject
 */
class BadStockTicker {
    private price: number = 0;

    constructor(
        private display: StockDisplay,
        private logger: StockLogger
    ) {}

    setPrice(price: number): void {
        this.price = price;
        // Must manually notify each observer
        this.display.update(price);
        this.logger.update(price);
        // Adding new observer requires modifying this!
    }
}

class StockDisplay {
    update(price: number): void {
        console.log(`Display: $${price}`);
    }
}

class StockLogger {
    update(price: number): void {
        console.log(`Log: Price changed to $${price}`);
    }
}

/*
 * SOLUTION: Observer Pattern (GoF Behavioral)
 * - Define one-to-many dependency between objects
 * - When one object changes state, all dependents notified automatically
 * - Loose coupling between subject and observers
 */
interface StockObserver {
    update(price: number): void;
}

class StockTicker {
    private price: number = 0;
    private observers: StockObserver[] = [];

    attach(observer: StockObserver): void {
        this.observers.push(observer);
    }

    detach(observer: StockObserver): void {
        const index = this.observers.indexOf(observer);
        if (index !== -1) {
            this.observers.splice(index, 1);
        }
    }

    setPrice(price: number): void {
        this.price = price;
        this.notifyObservers();
    }

    private notifyObservers(): void {
        for (const observer of this.observers) {
            observer.update(this.price);
        }
    }
}

class DisplayObserver implements StockObserver {
    update(price: number): void {
        console.log(`Display: $${price}`);
    }
}

class LoggerObserver implements StockObserver {
    update(price: number): void {
        console.log(`Log: Price changed to $${price}`);
    }
}

class AlertObserver implements StockObserver {
    constructor(private threshold: number) {}

    update(price: number): void {
        if (price > this.threshold) {
            console.log(`Alert: Price exceeded $${this.threshold}!`);
        }
    }
}

// ============================================================================
// EXAMPLE 7: Replace Conditional with Decorator (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Adding functionality through conditionals
 * - Complex conditionals checking feature flags
 * - Hard to combine features dynamically
 */
class BadCoffee {
    private hasMilk: boolean = false;
    private hasSugar: boolean = false;
    private hasWhip: boolean = false;

    getCost(): number {
        let cost = 5.0;
        if (this.hasMilk) cost += 1.0;
        if (this.hasSugar) cost += 0.5;
        if (this.hasWhip) cost += 1.5;
        return cost;
    }

    getDescription(): string {
        let desc = 'Coffee';
        if (this.hasMilk) desc += ' with milk';
        if (this.hasSugar) desc += ' and sugar';
        if (this.hasWhip) desc += ' and whip';
        return desc;
    }

    setMilk(hasMilk: boolean): void { this.hasMilk = hasMilk; }
    setSugar(hasSugar: boolean): void { this.hasSugar = hasSugar; }
    setWhip(hasWhip: boolean): void { this.hasWhip = hasWhip; }
}

/*
 * SOLUTION: Decorator Pattern (GoF Structural)
 * - Attach additional responsibilities to object dynamically
 * - Flexible alternative to subclassing
 * - Can combine any decorators in any order
 */
interface Coffee {
    getCost(): number;
    getDescription(): string;
}

class BasicCoffee implements Coffee {
    getCost(): number {
        return 5.0;
    }

    getDescription(): string {
        return 'Coffee';
    }
}

abstract class CoffeeDecorator implements Coffee {
    constructor(protected decoratedCoffee: Coffee) {}

    getCost(): number {
        return this.decoratedCoffee.getCost();
    }

    getDescription(): string {
        return this.decoratedCoffee.getDescription();
    }
}

class MilkDecorator extends CoffeeDecorator {
    getCost(): number {
        return super.getCost() + 1.0;
    }

    getDescription(): string {
        return super.getDescription() + ', milk';
    }
}

class SugarDecorator extends CoffeeDecorator {
    getCost(): number {
        return super.getCost() + 0.5;
    }

    getDescription(): string {
        return super.getDescription() + ', sugar';
    }
}

class WhipDecorator extends CoffeeDecorator {
    getCost(): number {
        return super.getCost() + 1.5;
    }

    getDescription(): string {
        return super.getDescription() + ', whipped cream';
    }
}

// Usage:
// const coffee: Coffee = new WhipDecorator(
//     new SugarDecorator(
//         new MilkDecorator(new BasicCoffee())
//     )
// );

// ============================================================================
// EXAMPLE 8: Replace Type Code with State Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: State management with string type codes
 * - String comparisons are error-prone
 * - State transition logic scattered
 * - No compile-time checking
 */
class BadSystemPermission {
    private state: string = 'REQUESTED';
    private isGranted: boolean = false;

    claimedBy(userName: string): void {
        if (this.state === 'REQUESTED') {
            this.state = 'CLAIMED';
            this.isGranted = false;
        }
    }

    deniedBy(userName: string): void {
        if (this.state === 'CLAIMED') {
            this.state = 'DENIED';
            this.isGranted = false;
        }
    }

    grantedBy(userName: string): void {
        if (this.state === 'CLAIMED' && !this.isGranted) {
            this.state = 'GRANTED';
            this.isGranted = true;
        }
    }
}

/*
 * SOLUTION: State Pattern (GoF Behavioral)
 * - Each state is a class that knows its valid transitions
 * - Type-safe state machine
 * - State machine becomes explicit
 */
interface PermissionState {
    claimedBy(permission: SystemPermission, userName: string): void;
    deniedBy(permission: SystemPermission, userName: string): void;
    grantedBy(permission: SystemPermission, userName: string): void;
}

class RequestedState implements PermissionState {
    claimedBy(permission: SystemPermission, userName: string): void {
        permission.setState(new ClaimedState());
    }

    deniedBy(permission: SystemPermission, userName: string): void {}
    grantedBy(permission: SystemPermission, userName: string): void {}
}

class ClaimedState implements PermissionState {
    claimedBy(permission: SystemPermission, userName: string): void {}

    deniedBy(permission: SystemPermission, userName: string): void {
        permission.setState(new DeniedState());
    }

    grantedBy(permission: SystemPermission, userName: string): void {
        permission.setState(new GrantedState());
    }
}

class DeniedState implements PermissionState {
    claimedBy(permission: SystemPermission, userName: string): void {}
    deniedBy(permission: SystemPermission, userName: string): void {}
    grantedBy(permission: SystemPermission, userName: string): void {}
}

class GrantedState implements PermissionState {
    claimedBy(permission: SystemPermission, userName: string): void {}
    deniedBy(permission: SystemPermission, userName: string): void {}
    grantedBy(permission: SystemPermission, userName: string): void {}
}

class SystemPermission {
    private state: PermissionState;

    constructor() {
        this.state = new RequestedState();
    }

    setState(state: PermissionState): void {
        this.state = state;
    }

    claimedBy(userName: string): void {
        this.state.claimedBy(this, userName);
    }

    deniedBy(userName: string): void {
        this.state.deniedBy(this, userName);
    }

    grantedBy(userName: string): void {
        this.state.grantedBy(this, userName);
    }
}

// ============================================================================
// EXAMPLE 9: Extract Proxy for Lazy Loading (Structural - GoF)
// ============================================================================

/*
 * PROBLEM: Expensive objects loaded eagerly
 * - Large images loaded even if never displayed
 * - Wastes memory and time
 */
class BadImageViewer {
    private images: HeavyImage[] = [];

    loadImages(filenames: string[]): void {
        for (const filename of filenames) {
            this.images.push(new HeavyImage(filename));
        }
        console.log(`Loaded ${this.images.length} images`);
    }

    display(index: number): void {
        this.images[index].display();
    }
}

class HeavyImage {
    private data: Uint8Array;

    constructor(private filename: string) {
        this.loadFromDisk();
    }

    private loadFromDisk(): void {
        console.log(`Loading large image: ${this.filename}`);
        this.data = new Uint8Array(1000000);
    }

    display(): void {
        console.log(`Displaying: ${this.filename}`);
    }
}

/*
 * SOLUTION: Proxy Pattern (GoF Structural) - Virtual Proxy
 * - Provides placeholder for expensive object
 * - Creates real object only when needed
 */
interface Image {
    display(): void;
}

class RealImage implements Image {
    private data: Uint8Array;

    constructor(private filename: string) {
        this.loadFromDisk();
    }

    private loadFromDisk(): void {
        console.log(`Loading image: ${this.filename}`);
        this.data = new Uint8Array(1000000);
    }

    display(): void {
        console.log(`Displaying: ${this.filename}`);
    }
}

class ImageProxy implements Image {
    private realImage: RealImage | null = null;

    constructor(private filename: string) {}

    display(): void {
        if (!this.realImage) {
            this.realImage = new RealImage(this.filename);
        }
        this.realImage.display();
    }
}

class ImageViewer {
    private images: Image[] = [];

    loadImages(filenames: string[]): void {
        for (const filename of filenames) {
            this.images.push(new ImageProxy(filename));
        }
        console.log(`Loaded ${this.images.length} image proxies`);
    }

    display(index: number): void {
        this.images[index].display();
    }
}

// ============================================================================
// EXAMPLE 10: Replace Conditional with Chain of Responsibility (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex conditional chain for request handling
 * - Hard to add/remove/reorder handlers
 * - Cannot dynamically configure chain
 */
class BadSupportSystem {
    handleRequest(issue: string, priority: number): void {
        if (priority <= 1) {
            console.log(`Junior support handling: ${issue}`);
        } else if (priority <= 3) {
            console.log(`Senior support handling: ${issue}`);
        } else if (priority <= 5) {
            console.log(`Manager handling: ${issue}`);
        } else {
            console.log(`Director handling: ${issue}`);
        }
    }
}

/*
 * SOLUTION: Chain of Responsibility Pattern (GoF Behavioral)
 * - Chain handlers together
 * - Each handler decides to process or pass to next
 */
abstract class SupportHandler {
    protected nextHandler: SupportHandler | null = null;

    setNext(handler: SupportHandler): void {
        this.nextHandler = handler;
    }

    handleRequest(request: SupportRequest): void {
        if (this.canHandle(request)) {
            this.process(request);
        } else if (this.nextHandler) {
            this.nextHandler.handleRequest(request);
        } else {
            console.log(`No handler for: ${request.issue}`);
        }
    }

    protected abstract canHandle(request: SupportRequest): boolean;
    protected abstract process(request: SupportRequest): void;
}

interface SupportRequest {
    issue: string;
    priority: number;
}

class JuniorSupport extends SupportHandler {
    protected canHandle(request: SupportRequest): boolean {
        return request.priority <= 1;
    }

    protected process(request: SupportRequest): void {
        console.log(`Junior Support handling: ${request.issue}`);
    }
}

class SeniorSupport extends SupportHandler {
    protected canHandle(request: SupportRequest): boolean {
        return request.priority <= 3;
    }

    protected process(request: SupportRequest): void {
        console.log(`Senior Support handling: ${request.issue}`);
    }
}

class ManagerSupport extends SupportHandler {
    protected canHandle(request: SupportRequest): boolean {
        return request.priority <= 5;
    }

    protected process(request: SupportRequest): void {
        console.log(`Manager handling: ${request.issue}`);
    }
}

class DirectorSupport extends SupportHandler {
    protected canHandle(request: SupportRequest): boolean {
        return true;
    }

    protected process(request: SupportRequest): void {
        console.log(`Director handling critical: ${request.issue}`);
    }
}

class SupportSystem {
    private chain: SupportHandler;

    constructor() {
        const junior = new JuniorSupport();
        const senior = new SeniorSupport();
        const manager = new ManagerSupport();
        const director = new DirectorSupport();

        junior.setNext(senior);
        senior.setNext(manager);
        manager.setNext(director);

        this.chain = junior;
    }

    submitRequest(issue: string, priority: number): void {
        this.chain.handleRequest({ issue, priority });
    }
}

// ============================================================================
// EXAMPLE 11: Replace Lazy Initialization with Singleton (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Global state managed incorrectly
 * - Not thread-safe (in multi-threaded environments)
 * - Multiple instances possible
 * - No control over instantiation
 */
class BadDatabaseConnection {
    private static instance: BadDatabaseConnection;

    // Public constructor - anyone can create instances!
    constructor() {
        console.log('Expensive database connection created');
    }

    static getInstance(): BadDatabaseConnection {
        if (!BadDatabaseConnection.instance) {
            BadDatabaseConnection.instance = new BadDatabaseConnection();
        }
        return BadDatabaseConnection.instance;
    }

    query(sql: string): void {
        console.log(`Executing: ${sql}`);
    }
}

/*
 * SOLUTION: Singleton Pattern (GoF Creational) - TypeScript style
 * - Ensures class has only one instance
 * - Provides global point of access
 * - Lazy initialization
 */
class DatabaseConnection {
    private static instance: DatabaseConnection;

    // Private constructor prevents instantiation
    private constructor() {
        console.log('Database connection established');
    }

    static getInstance(): DatabaseConnection {
        if (!DatabaseConnection.instance) {
            DatabaseConnection.instance = new DatabaseConnection();
        }
        return DatabaseConnection.instance;
    }

    query(sql: string): void {
        console.log(`Executing: ${sql}`);
    }
}

// Alternative: Module pattern (TypeScript/ES6 modules are singletons by default)
class ConfigurationManager {
    constructor() {
        console.log('Configuration loaded');
    }

    loadConfig(): void {
        console.log('Loading configuration...');
    }
}

// Export single instance
export const configManager = new ConfigurationManager();

// ============================================================================
// EXAMPLE 12: Replace Type Code with Factory Method (Creational - GoF)
// ============================================================================

/*
 * PROBLEM: Object creation based on type codes
 * - Creation logic scattered
 * - Type codes are error-prone
 * - Hard to add new types
 */
class BadAnimalCreator {
    static readonly DOG = 1;
    static readonly CAT = 2;
    static readonly BIRD = 3;

    createAnimal(type: number, name: string): Animal {
        if (type === BadAnimalCreator.DOG) {
            return {
                type: 'Dog',
                name,
                sound: 'Woof',
                legs: 4,
                makeSound() {
                    console.log(`${this.name} says ${this.sound}`);
                }
            };
        } else if (type === BadAnimalCreator.CAT) {
            return {
                type: 'Cat',
                name,
                sound: 'Meow',
                legs: 4,
                makeSound() {
                    console.log(`${this.name} says ${this.sound}`);
                }
            };
        } else if (type === BadAnimalCreator.BIRD) {
            return {
                type: 'Bird',
                name,
                sound: 'Chirp',
                legs: 2,
                makeSound() {
                    console.log(`${this.name} says ${this.sound}`);
                }
            };
        }
        throw new Error('Unknown animal type');
    }
}

interface Animal {
    type: string;
    name: string;
    sound: string;
    legs: number;
    makeSound(): void;
}

/*
 * SOLUTION: Factory Method Pattern (GoF Creational)
 * - Define interface for creating objects
 * - Let subclasses decide which class to instantiate
 * - Encapsulates object creation
 */
interface IAnimal {
    readonly name: string;
    makeSound(): void;
}

class Dog implements IAnimal {
    constructor(public readonly name: string) {}

    makeSound(): void {
        console.log(`${this.name} says Woof!`);
    }
}

class Cat implements IAnimal {
    constructor(public readonly name: string) {}

    makeSound(): void {
        console.log(`${this.name} says Meow!`);
    }
}

class Bird implements IAnimal {
    constructor(public readonly name: string) {}

    makeSound(): void {
        console.log(`${this.name} says Chirp!`);
    }
}

// Factory Method approach
abstract class AnimalCreator {
    protected abstract createAnimal(name: string): IAnimal;

    orderAnimal(name: string): IAnimal {
        const animal = this.createAnimal(name);
        console.log(`Created: ${animal.name}`);
        return animal;
    }
}

class DogCreator extends AnimalCreator {
    protected createAnimal(name: string): IAnimal {
        return new Dog(name);
    }
}

class CatCreator extends AnimalCreator {
    protected createAnimal(name: string): IAnimal {
        return new Cat(name);
    }
}

// Simple Factory with type guards
type AnimalType = 'dog' | 'cat' | 'bird';

class AnimalFactory {
    static createAnimal(type: AnimalType, name: string): IAnimal {
        switch (type) {
            case 'dog':
                return new Dog(name);
            case 'cat':
                return new Cat(name);
            case 'bird':
                return new Bird(name);
            default:
                // TypeScript exhaustiveness check
                const _exhaustive: never = type;
                throw new Error(`Unknown animal type: ${_exhaustive}`);
        }
    }
}

// ============================================================================
// EXAMPLE 13: Replace Conditional Dispatcher with Command Pattern (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Giant switch/if-else for command dispatching
 * - Adding new commands requires modifying dispatcher
 * - All command logic in one place
 * - Hard to test individual commands
 */
class BadCommandProcessor {
    processCommand(commandType: string, data: string): void {
        if (commandType === 'CREATE') {
            console.log(`Creating: ${data}`);
        } else if (commandType === 'UPDATE') {
            console.log(`Updating: ${data}`);
        } else if (commandType === 'DELETE') {
            console.log(`Deleting: ${data}`);
        } else if (commandType === 'VALIDATE') {
            console.log(`Validating: ${data}`);
        }
    }
}

/*
 * SOLUTION: Command Pattern (GoF Behavioral)
 * - Each command is a separate class
 * - Commands stored in Map for O(1) lookup
 * - Easy to add new commands
 */
interface Command {
    execute(data: string): void;
}

class CreateCommand implements Command {
    execute(data: string): void {
        console.log(`Creating: ${data}`);
    }
}

class UpdateCommand implements Command {
    execute(data: string): void {
        console.log(`Updating: ${data}`);
    }
}

class DeleteCommand implements Command {
    execute(data: string): void {
        console.log(`Deleting: ${data}`);
    }
}

class ValidateCommand implements Command {
    execute(data: string): void {
        console.log(`Validating: ${data}`);
    }
}

class CommandProcessor {
    private commands = new Map<string, Command>();

    constructor() {
        this.commands.set('CREATE', new CreateCommand());
        this.commands.set('UPDATE', new UpdateCommand());
        this.commands.set('DELETE', new DeleteCommand());
        this.commands.set('VALIDATE', new ValidateCommand());
    }

    processCommand(commandType: string, data: string): void {
        const command = this.commands.get(commandType);
        if (command) {
            command.execute(data);
        } else {
            throw new Error(`Unknown command: ${commandType}`);
        }
    }

    registerCommand(type: string, command: Command): void {
        this.commands.set(type, command);
    }
}

// ============================================================================
// EXAMPLE 14: Introduce Null Object Pattern
// ============================================================================

/*
 * PROBLEM: Null checks scattered everywhere
 * - Every usage must check for null/undefined
 * - Defensive programming clutter
 * - TypeError risk
 */
class BadCustomerService {
    calculateDiscount(customer: Customer | null): number {
        if (!customer) return 0.0;

        if (!customer.discountPolicy) return 0.0;

        return customer.discountPolicy.calculateDiscount(customer.totalPurchases);
    }

    sendNewsletter(customer: Customer | null): void {
        if (customer && customer.email) {
            console.log(`Sending newsletter to ${customer.email}`);
        }
    }
}

/*
 * SOLUTION: Null Object Pattern
 * - Special object represents "no object"
 * - Null object has do-nothing or default behavior
 * - Eliminates null checks
 */
interface DiscountPolicy {
    calculateDiscount(amount: number): number;
}

class PercentageDiscount implements DiscountPolicy {
    constructor(private percentage: number) {}

    calculateDiscount(amount: number): number {
        return amount * this.percentage;
    }
}

// Null Object - safe default behavior
class NoDiscount implements DiscountPolicy {
    calculateDiscount(amount: number): number {
        return 0.0;
    }
}

class Customer {
    constructor(
        public name: string,
        public readonly email: string,
        public readonly discountPolicy: DiscountPolicy = new NoDiscount(),
        public totalPurchases: number = 0,
        public readonly isVIP: boolean = false
    ) {}
}

class CustomerService {
    calculateDiscount(customer: Customer): number {
        // No null checks needed!
        return customer.discountPolicy.calculateDiscount(customer.totalPurchases);
    }

    sendNewsletter(customer: Customer): void {
        if (customer.email) {
            console.log(`Sending newsletter to ${customer.email}`);
        }
    }
}

// ============================================================================
// EXAMPLE 15: Compose Method - Extract Till You Drop
// ============================================================================

/*
 * PROBLEM: Long methods doing too many things
 * - Hard to understand
 * - Multiple levels of abstraction mixed
 * - Difficult to test
 */
class BadOrderProcessor {
    processOrder(order: Order): void {
        // Validation
        if (order.items.length === 0) {
            throw new Error('Order has no items');
        }

        // Calculate total
        let total = 0;
        for (const item of order.items) {
            total += item.price * item.quantity;
        }

        // Apply discount
        if (order.customer.isVIP) {
            total *= 0.9;
        }

        // Process payment
        if (order.paymentMethod === 'CREDIT_CARD') {
            console.log(`Processing credit card: ${total}`);
        } else if (order.paymentMethod === 'PAYPAL') {
            console.log(`Processing PayPal: ${total}`);
        }

        // Update inventory
        for (const item of order.items) {
            item.product.decreaseStock(item.quantity);
        }

        // Send confirmation
        console.log(`Sending confirmation to: ${order.customer.email}`);
    }
}

/*
 * SOLUTION: Compose Method Pattern
 * - Break into small methods at same abstraction level
 * - Main method reads like documentation
 * - Each method has single clear purpose
 */
class OrderProcessor {
    processOrder(order: Order): void {
        this.validateOrder(order);
        let total = this.calculateTotal(order);
        total = this.applyDiscounts(order, total);
        this.processPayment(order, total);
        this.updateInventory(order);
        this.sendConfirmation(order);
    }

    private validateOrder(order: Order): void {
        if (order.items.length === 0) {
            throw new Error('Order has no items');
        }
    }

    private calculateTotal(order: Order): number {
        return order.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
    }

    private applyDiscounts(order: Order, total: number): number {
        return order.customer.isVIP ? total * 0.9 : total;
    }

    private processPayment(order: Order, total: number): void {
        const processor = PaymentProcessorFactory.create(order.paymentMethod);
        processor.process(total);
    }

    private updateInventory(order: Order): void {
        order.items.forEach(item => item.product.decreaseStock(item.quantity));
    }

    private sendConfirmation(order: Order): void {
        console.log(`Sending confirmation to: ${order.customer.email}`);
    }
}

// ============================================================================
// EXAMPLE 16: Replace Inheritance with Delegation (Favor Composition)
// ============================================================================

/*
 * PROBLEM: Inappropriate inheritance
 * - Subclass tightly coupled to superclass
 * - Cannot reuse without base class baggage
 * - Violates Liskov Substitution Principle
 */
class BadStack<T> extends Array<T> {
    push(item: T): number {
        return super.push(item);
    }

    pop(): T | undefined {
        return super.pop();
    }

    // PROBLEM: All Array methods exposed!
    // stack[5] - breaks stack abstraction
    // stack.splice(0, 1) - breaks stack semantics
}

/*
 * SOLUTION: Composition over Inheritance
 * - Contains array instead of extending it
 * - Only exposes appropriate Stack interface
 * - Maintains proper encapsulation
 */
class Stack<T> {
    private elements: T[] = [];

    push(item: T): void {
        this.elements.push(item);
    }

    pop(): T {
        if (this.isEmpty()) {
            throw new Error('Stack is empty');
        }
        return this.elements.pop()!;
    }

    peek(): T {
        if (this.isEmpty()) {
            throw new Error('Stack is empty');
        }
        return this.elements[this.elements.length - 1];
    }

    isEmpty(): boolean {
        return this.elements.length === 0;
    }

    get size(): number {
        return this.elements.length;
    }
}

// ============================================================================
// EXAMPLE 17: Replace Implicit Tree with Interpreter (Behavioral - GoF)
// ============================================================================

/*
 * PROBLEM: Complex expressions evaluated with conditionals
 * - Expression parsing mixed with evaluation
 * - Hard to add new operators
 * - Cannot represent expression tree
 */
class BadCalculator {
    evaluate(expression: string): number {
        if (expression.includes('+')) {
            const parts = expression.split('+');
            return parseFloat(parts[0]) + parseFloat(parts[1]);
        } else if (expression.includes('-')) {
            const parts = expression.split('-');
            return parseFloat(parts[0]) - parseFloat(parts[1]);
        } else if (expression.includes('*')) {
            const parts = expression.split('*');
            return parseFloat(parts[0]) * parseFloat(parts[1]);
        }
        return parseFloat(expression);
    }
}

/*
 * SOLUTION: Interpreter Pattern + Composite (GoF)
 * - Represents grammar rules as class hierarchy
 * - Easy to add new expressions
 * - Tree structure allows multiple operations
 */
interface Expression {
    interpret(): number;
    toString(): string;
}

// Terminal expression - leaf node
class NumberExpression implements Expression {
    constructor(private value: number) {}

    interpret(): number {
        return this.value;
    }

    toString(): string {
        return this.value.toString();
    }
}

// Non-terminal expressions - composite nodes
class AddExpression implements Expression {
    constructor(
        private left: Expression,
        private right: Expression
    ) {}

    interpret(): number {
        return this.left.interpret() + this.right.interpret();
    }

    toString(): string {
        return `(${this.left.toString()} + ${this.right.toString()})`;
    }
}

class SubtractExpression implements Expression {
    constructor(
        private left: Expression,
        private right: Expression
    ) {}

    interpret(): number {
        return this.left.interpret() - this.right.interpret();
    }

    toString(): string {
        return `(${this.left.toString()} - ${this.right.toString()})`;
    }
}

class MultiplyExpression implements Expression {
    constructor(
        private left: Expression,
        private right: Expression
    ) {}

    interpret(): number {
        return this.left.interpret() * this.right.interpret();
    }

    toString(): string {
        return `(${this.left.toString()} * ${this.right.toString()})`;
    }
}

// Usage
class Calculator {
    calculate(): void {
        // Build: (5 + 3) * (10 - 2)
        const expr: Expression = new MultiplyExpression(
            new AddExpression(
                new NumberExpression(5),
                new NumberExpression(3)
            ),
            new SubtractExpression(
                new NumberExpression(10),
                new NumberExpression(2)
            )
        );

        console.log(`Expression: ${expr.toString()}`);
        console.log(`Result: ${expr.interpret()}`);
    }
}

// ============================================================================
// EXAMPLE 18: Replace Parameter with Method (Simplification Pattern)
// ============================================================================

/*
 * PROBLEM: Parameters that can be obtained from existing data
 * - Unnecessary parameters make method signatures complex
 * - Client must calculate and pass redundant information
 * - Easy to pass incorrect values
 */
class BadPriceCalculator {
    // basePrice is redundant - can be calculated from order!
    calculateTotal(order: Order, basePrice: number): number {
        const discount = basePrice * 0.1;
        const tax = basePrice * 0.08;
        return basePrice - discount + tax;
    }
}

// Client code has to do extra work
function badProcessOrder(order: Order): void {
    const calculator = new BadPriceCalculator();
    // Client calculates basePrice...
    const basePrice = order.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
    // ...then passes it as parameter
    const total = calculator.calculateTotal(order, basePrice);
}

/*
 * SOLUTION: Remove redundant parameter
 * - Calculate needed values inside method
 * - Simpler method signature
 * - Single source of truth
 */
class PriceCalculator {
    // Simplified signature - only essential parameter
    calculateTotal(order: Order): number {
        const basePrice = this.getBasePrice(order);
        const discount = basePrice * 0.1;
        const tax = basePrice * 0.08;
        return basePrice - discount + tax;
    }

    private getBasePrice(order: Order): number {
        return order.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
    }
}

// Client code is much simpler
function processOrder(order: Order): void {
    const calculator = new PriceCalculator();
    // Just pass the order - calculator handles the rest
    const total = calculator.calculateTotal(order);
}

// ============================================================================
// EXAMPLE 19: Encapsulate Composite with Builder (Creational Pattern)
// ============================================================================

/*
 * PROBLEM: Complex composite structures hard to build
 * - Lots of boilerplate to create nested structures
 * - Easy to make mistakes in nesting
 * - Code doesn't reflect structure visually
 */
class Menu {
    constructor(
        public name: string,
        private children: Menu[] = []
    ) {}

    add(child: Menu): void {
        this.children.push(child);
    }

    display(indent: number = 0): void {
        console.log(' '.repeat(indent) + this.name);
        for (const child of this.children) {
            child.display(indent + 2);
        }
    }
}

function createBadMenu(): Menu {
    const root = new Menu('File');

    const newItem = new Menu('New');
    root.add(newItem);

    const openItem = new Menu('Open');
    root.add(openItem);

    const recentMenu = new Menu('Recent');
    const recent1 = new Menu('file1.txt');
    recentMenu.add(recent1);
    const recent2 = new Menu('file2.txt');
    recentMenu.add(recent2);
    root.add(recentMenu);

    // Hard to see the structure!
    return root;
}

/*
 * SOLUTION: Fluent Builder for Composite
 * - Builder pattern combined with composite
 * - Chainable methods make structure visible
 */
class MenuBuilder {
    private menu: Menu;

    constructor(name: string) {
        this.menu = new Menu(name);
    }

    item(name: string): this {
        this.menu.add(new Menu(name));
        return this;
    }

    submenu(name: string, builderFn: (builder: MenuBuilder) => void): this {
        const subBuilder = new MenuBuilder(name);
        builderFn(subBuilder);
        this.menu.add(subBuilder.build());
        return this;
    }

    build(): Menu {
        return this.menu;
    }
}

// Usage - structure is immediately visible!
function createMenu(): Menu {
    return new MenuBuilder('File')
        .item('New')
        .item('Open')
        .submenu('Recent', recent => {
            recent
                .item('file1.txt')
                .item('file2.txt');
        })
        .item('Save')
        .build();
}

// ============================================================================
// Supporting Types and Classes
// ============================================================================

interface Product {
    name: string;
    price: number;
    decreaseStock(quantity: number): void;
}

interface OrderItem {
    product: Product;
    quantity: number;
    price: number;
}

interface Order {
    items: OrderItem[];
    customer: Customer;
    paymentMethod: string;
}

interface PaymentProcessor {
    process(amount: number): void;
}

class PaymentProcessorFactory {
    static create(method: string): PaymentProcessor {
        return {
            process(amount: number): void {
                console.log(`Processing ${method} payment: ${amount}`);
            }
        };
    }
}

// ============================================================================
// Usage Examples
// ============================================================================

// Example: Using Strategy Pattern
function demoStrategy(): void {
    const termLoan = new Loan(new TermLoanStrategy(), 100000, 0, 5);
    console.log(`Term loan capital: ${termLoan.calculateCapital()}`);

    const revolver = new Loan(new RevolverStrategy(), 100000, 20000, 5);
    console.log(`Revolver capital: ${revolver.calculateCapital()}`);
}

// Example: Using Builder Pattern
function demoBuilder(): void {
    const request = HttpRequest.builder('https://api.example.com')
        .withMethod('POST')
        .withBody(JSON.stringify({ name: 'John' }))
        .withHeader('Content-Type', 'application/json')
        .withTimeout(5000)
        .build();

    console.log(`Request URL: ${request.url}`);
    console.log(`Request method: ${request.method}`);
}

// Example: Using Observer Pattern
function demoObserver(): void {
    const ticker = new StockTicker();

    ticker.attach(new DisplayObserver());
    ticker.attach(new LoggerObserver());
    ticker.attach(new AlertObserver(100));

    ticker.setPrice(105);
}

// Example: Using Decorator Pattern
function demoDecorator(): void {
    const coffee: Coffee = new WhipDecorator(
        new SugarDecorator(
            new MilkDecorator(new BasicCoffee())
        )
    );

    console.log(`${coffee.getDescription()} = ${coffee.getCost()}`);
}

// Example: Using Composite Pattern
function demoComposite(): void {
    const root = new DirectoryComponent('root');
    const docs = new DirectoryComponent('docs');

    docs.add(new FileComponent('readme.txt', 1024));
    docs.add(new FileComponent('manual.pdf', 5120));

    root.add(docs);
    root.add(new FileComponent('config.xml', 512));

    console.log(`Total size: ${root.getSize()} bytes`);
    root.display(0);
}