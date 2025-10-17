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
