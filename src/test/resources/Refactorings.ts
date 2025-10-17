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
