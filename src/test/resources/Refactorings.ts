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



// ============================================================================
// Supporting Types and Classes
// ============================================================================

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

interface DiscountPolicy {
    calculateDiscount(amount: number): number;
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
