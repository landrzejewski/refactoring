package pl.training.refactorings.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {

    private OrderProcessor processor;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        processor = new OrderProcessor();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Nested
    @DisplayName("Constructor and Initial State Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should initialize with empty orders list")
        void shouldInitializeWithEmptyOrdersList() {
            assertNotNull(processor.orders);
            assertEquals(0, processor.orders.size());
        }

        @Test
        @DisplayName("Should initialize total to zero")
        void shouldInitializeTotalToZero() {
            assertEquals(0.0, processor.total, 0.001);
        }

        @Test
        @DisplayName("Should initialize orderCount to zero")
        void shouldInitializeOrderCountToZero() {
            assertEquals(0, processor.orderCount);
        }

        @Test
        @DisplayName("Should initialize lastCustomer to null")
        void shouldInitializeLastCustomerToNull() {
            assertNull(processor.lastCustomer);
        }
    }

    @Nested
    @DisplayName("processOrder Tests - Valid Inputs")
    class ProcessOrderValidTests {

        @Test
        @DisplayName("Should process valid order for premium customer")
        void shouldProcessValidOrderForPremiumCustomer() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 999.99,
                "credit", "john@example.com", true, "123 Main St"
            );

            assertEquals(1, processor.orders.size());
            assertEquals(1, processor.orderCount);
            assertEquals("John Doe", processor.lastCustomer);
            assertTrue(processor.total > 0);
            assertTrue(outputStreamCaptor.toString().contains("Order processed successfully for John Doe"));
        }

        @Test
        @DisplayName("Should process valid order for regular customer")
        void shouldProcessValidOrderForRegularCustomer() {
            processor.processOrder(
                "Jane Smith", "Mouse", 2, 29.99,
                "debit", "jane@example.com", false, "456 Oak Ave"
            );

            assertEquals(1, processor.orders.size());
            assertEquals(1, processor.orderCount);
            assertEquals("Jane Smith", processor.lastCustomer);
        }

        @Test
        @DisplayName("Should calculate correct total with discount and tax for premium customer over 100")
        void shouldCalculateCorrectTotalWithDiscountAndTaxForPremiumOver100() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 150.0,
                "credit", "john@example.com", true, "123 Main St"
            );

            double subtotal = 150.0;
            double discount = subtotal * 0.15;
            double tax = (subtotal - discount) * 0.08;
            double expectedTotal = subtotal - discount + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should calculate correct total for premium customer under 100")
        void shouldCalculateCorrectTotalForPremiumUnder100() {
            processor.processOrder(
                "John Doe", "Mouse", 1, 50.0,
                "credit", "john@example.com", true, "123 Main St"
            );

            double subtotal = 50.0;
            double discount = subtotal * 0.10;
            double tax = (subtotal - discount) * 0.08;
            double expectedTotal = subtotal - discount + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should calculate correct total for regular customer over 100")
        void shouldCalculateCorrectTotalForRegularOver100() {
            processor.processOrder(
                "Jane Smith", "Laptop", 1, 150.0,
                "debit", "jane@example.com", false, "456 Oak Ave"
            );

            double subtotal = 150.0;
            double discount = subtotal * 0.05;
            double tax = (subtotal - discount) * 0.08;
            double expectedTotal = subtotal - discount + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should calculate correct total for regular customer under 100")
        void shouldCalculateCorrectTotalForRegularUnder100() {
            processor.processOrder(
                "Jane Smith", "Mouse", 1, 50.0,
                "debit", "jane@example.com", false, "456 Oak Ave"
            );

            double subtotal = 50.0;
            double tax = subtotal * 0.08;
            double expectedTotal = subtotal + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should process multiple orders and accumulate total")
        void shouldProcessMultipleOrdersAndAccumulateTotal() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );
            
            double firstTotal = processor.total;

            processor.processOrder(
                "Jane Smith", "Mouse", 1, 50.0,
                "debit", "jane@example.com", false, "456 Oak Ave"
            );

            assertEquals(2, processor.orders.size());
            assertEquals(2, processor.orderCount);
            assertTrue(processor.total > firstTotal);
        }

        @Test
        @DisplayName("Should handle multiple quantity correctly")
        void shouldHandleMultipleQuantityCorrectly() {
            processor.processOrder(
                "John Doe", "Mouse", 5, 29.99,
                "credit", "john@example.com", false, "123 Main St"
            );

            double subtotal = 5 * 29.99;
            double discount = subtotal * 0.05;
            double tax = (subtotal - discount) * 0.08;
            double expectedTotal = subtotal - discount + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }
    }

    @Nested
    @DisplayName("processOrder Tests - Payment Processing")
    class ProcessOrderPaymentTests {

        @Test
        @DisplayName("Should process credit card payment")
        void shouldProcessCreditCardPayment() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertTrue(outputStreamCaptor.toString().contains("Processing credit card"));
        }

        @Test
        @DisplayName("Should require verification for credit card over 1000")
        void shouldRequireVerificationForCreditCardOver1000() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 1500.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertTrue(outputStreamCaptor.toString().contains("Requires additional verification"));
        }

        @Test
        @DisplayName("Should not require verification for credit card under 1000")
        void shouldNotRequireVerificationForCreditCardUnder1000() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 500.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertFalse(outputStreamCaptor.toString().contains("Requires additional verification"));
        }

        @Test
        @DisplayName("Should process debit card payment")
        void shouldProcessDebitCardPayment() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "debit", "john@example.com", false, "123 Main St"
            );

            assertTrue(outputStreamCaptor.toString().contains("Processing debit card"));
        }

        @Test
        @DisplayName("Should process cash payment")
        void shouldProcessCashPayment() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "cash", "john@example.com", false, "123 Main St"
            );

            assertTrue(outputStreamCaptor.toString().contains("Cash payment received"));
        }
    }

    @Nested
    @DisplayName("processOrder Tests - Email Notifications")
    class ProcessOrderEmailTests {

        @Test
        @DisplayName("Should send email when email provided")
        void shouldSendEmailWhenEmailProvided() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Sending email to: john@example.com"));
            assertTrue(output.contains("Subject: Order Confirmation"));
            assertTrue(output.contains("Dear John Doe"));
        }

        @Test
        @DisplayName("Should not send email when email is null")
        void shouldNotSendEmailWhenEmailIsNull() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", null, false, "123 Main St"
            );

            assertFalse(outputStreamCaptor.toString().contains("Sending email"));
        }

        @Test
        @DisplayName("Should not send email when email is empty")
        void shouldNotSendEmailWhenEmailIsEmpty() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "", false, "123 Main St"
            );

            assertFalse(outputStreamCaptor.toString().contains("Sending email"));
        }

        @Test
        @DisplayName("Should include order details in email")
        void shouldIncludeOrderDetailsInEmail() {
            processor.processOrder(
                "John Doe", "Laptop", 2, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("2 x Laptop"));
            assertTrue(output.contains("Shipping to: 123 Main St"));
        }
    }

    @Nested
    @DisplayName("processOrder Tests - Inventory Updates")
    class ProcessOrderInventoryTests {

        @Test
        @DisplayName("Should call updateInventory for valid order")
        void shouldCallUpdateInventoryForValidOrder() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertTrue(outputStreamCaptor.toString().contains("Inventory updated: Laptop reduced by 1"));
        }
    }

    @Nested
    @DisplayName("processOrder Tests - Invalid Inputs")
    class ProcessOrderInvalidTests {

        @Test
        @DisplayName("Should reject null customer name")
        void shouldRejectNullCustomerName() {
            processor.processOrder(
                null, "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Customer name is null"));
        }

        @Test
        @DisplayName("Should reject null product name")
        void shouldRejectNullProductName() {
            processor.processOrder(
                "John Doe", null, 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Product name is null"));
        }

        @Test
        @DisplayName("Should reject zero quantity")
        void shouldRejectZeroQuantity() {
            processor.processOrder(
                "John Doe", "Laptop", 0, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid quantity"));
        }

        @Test
        @DisplayName("Should reject negative quantity")
        void shouldRejectNegativeQuantity() {
            processor.processOrder(
                "John Doe", "Laptop", -1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid quantity"));
        }

        @Test
        @DisplayName("Should reject zero price")
        void shouldRejectZeroPrice() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 0.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid price"));
        }

        @Test
        @DisplayName("Should reject negative price")
        void shouldRejectNegativePrice() {
            processor.processOrder(
                "John Doe", "Laptop", 1, -100.0,
                "credit", "john@example.com", false, "123 Main St"
            );

            assertEquals(0, processor.orders.size());
            assertEquals(0, processor.orderCount);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid price"));
        }
    }

    @Nested
    @DisplayName("processQuickOrder Tests")
    class ProcessQuickOrderTests {

        @Test
        @DisplayName("Should process quick order with default price")
        void shouldProcessQuickOrderWithDefaultPrice() {
            processor.processQuickOrder("John Doe", "Mouse", 1);

            assertEquals(1, processor.orders.size());
            assertEquals(1, processor.orderCount);
            assertEquals("John Doe", processor.lastCustomer);
            assertTrue(outputStreamCaptor.toString().contains("Quick order processed for John Doe"));
        }

        @Test
        @DisplayName("Should calculate total correctly for quick order")
        void shouldCalculateTotalCorrectlyForQuickOrder() {
            processor.processQuickOrder("John Doe", "Mouse", 2);

            double subtotal = 2 * 29.99;
            double tax = subtotal * 0.08;
            double expectedTotal = subtotal + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should apply discount for quick order over 100")
        void shouldApplyDiscountForQuickOrderOver100() {
            processor.processQuickOrder("John Doe", "Mouse", 4);

            double subtotal = 4 * 29.99;
            double discount = subtotal * 0.05;
            double tax = (subtotal - discount) * 0.08;
            double expectedTotal = subtotal - discount + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should not apply discount for quick order under 100")
        void shouldNotApplyDiscountForQuickOrderUnder100() {
            processor.processQuickOrder("John Doe", "Mouse", 1);

            double subtotal = 29.99;
            double tax = subtotal * 0.08;
            double expectedTotal = subtotal + tax;

            assertEquals(expectedTotal, processor.total, 0.01);
        }

        @Test
        @DisplayName("Should handle multiple quick orders")
        void shouldHandleMultipleQuickOrders() {
            processor.processQuickOrder("John Doe", "Mouse", 1);
            processor.processQuickOrder("Jane Smith", "Keyboard", 1);

            assertEquals(2, processor.orders.size());
            assertEquals(2, processor.orderCount);
            assertEquals("Jane Smith", processor.lastCustomer);
        }
    }

    @Nested
    @DisplayName("updateInventory Tests")
    class UpdateInventoryTests {

        @Test
        @DisplayName("Should print inventory update message")
        void shouldPrintInventoryUpdateMessage() {
            processor.updateInventory("Laptop", 5);

            assertTrue(outputStreamCaptor.toString().contains("Inventory updated: Laptop reduced by 5"));
        }

        @Test
        @DisplayName("Should modify product parameter to lowercase")
        void shouldModifyProductParameterToLowercase() {
            String product = "LAPTOP";
            processor.updateInventory(product, 1);

            assertEquals("LAPTOP", product);
        }
    }

    @Nested
    @DisplayName("generateReport Tests")
    class GenerateReportTests {

        @Test
        @DisplayName("Should generate detailed report when flag is true")
        void shouldGenerateDetailedReportWhenFlagIsTrue() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );
            
            outputStreamCaptor.reset();
            processor.generateReport(true);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("=== DETAILED ORDER REPORT ==="));
            assertTrue(output.contains("Total Orders: 1"));
            assertTrue(output.contains("Total Revenue:"));
            assertTrue(output.contains("Last Customer: John Doe"));
            assertTrue(output.contains("All Orders:"));
        }

        @Test
        @DisplayName("Should generate summary report when flag is false")
        void shouldGenerateSummaryReportWhenFlagIsFalse() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );
            
            outputStreamCaptor.reset();
            processor.generateReport(false);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Orders: 1"));
            assertTrue(output.contains("Revenue:"));
            assertFalse(output.contains("=== DETAILED ORDER REPORT ==="));
            assertFalse(output.contains("All Orders:"));
        }

        @Test
        @DisplayName("Should generate report with zero orders")
        void shouldGenerateReportWithZeroOrders() {
            processor.generateReport(true);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Total Orders: 0"));
            assertTrue(output.contains("Total Revenue: $0"));
        }

        @Test
        @DisplayName("Should list all orders in detailed report")
        void shouldListAllOrdersInDetailedReport() {
            processor.processOrder(
                "John Doe", "Laptop", 1, 100.0,
                "credit", "john@example.com", false, "123 Main St"
            );
            processor.processOrder(
                "Jane Smith", "Mouse", 1, 50.0,
                "debit", "jane@example.com", false, "456 Oak Ave"
            );
            
            outputStreamCaptor.reset();
            processor.generateReport(true);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("John Doe"));
            assertTrue(output.contains("Jane Smith"));
        }
    }

    @Nested
    @DisplayName("formatCurrency Tests")
    class FormatCurrencyTests {

        @Test
        @DisplayName("Should format whole number correctly")
        void shouldFormatWholeNumberCorrectly() {
            String formatted = OrderProcessor.formatCurrency(100.0);
            assertEquals("$100", formatted);
        }

        @Test
        @DisplayName("Should format decimal number correctly")
        void shouldFormatDecimalNumberCorrectly() {
            String formatted = OrderProcessor.formatCurrency(100.99);
            assertEquals("$100,99", formatted);
        }

        @Test
        @DisplayName("Should format zero correctly")
        void shouldFormatZeroCorrectly() {
            String formatted = OrderProcessor.formatCurrency(0.0);
            assertEquals("$0", formatted);
        }

        @Test
        @DisplayName("Should round to two decimal places")
        void shouldRoundToTwoDecimalPlaces() {
            String formatted = OrderProcessor.formatCurrency(100.999);
            assertEquals("$101", formatted);
        }

        @Test
        @DisplayName("Should handle large numbers")
        void shouldHandleLargeNumbers() {
            String formatted = OrderProcessor.formatCurrency(1234567.89);
            assertEquals("$1234567,89", formatted);
        }
    }

    @Nested
    @DisplayName("getCustomerDisplayName Tests")
    class GetCustomerDisplayNameTests {

        @Test
        @DisplayName("Should format name with title")
        void shouldFormatNameWithTitle() {
            String formatted = processor.getCustomerDisplayName("John", "Doe", "Mr.");
            assertEquals("Mr. John Doe", formatted);
        }

        @Test
        @DisplayName("Should format name without title")
        void shouldFormatNameWithoutTitle() {
            String formatted = processor.getCustomerDisplayName("John", "Doe", null);
            assertEquals("John Doe", formatted);
        }

        @Test
        @DisplayName("Should format name with empty title")
        void shouldFormatNameWithEmptyTitle() {
            String formatted = processor.getCustomerDisplayName("John", "Doe", "");
            assertEquals("John Doe", formatted);
        }

        @Test
        @DisplayName("Should format name with only first name")
        void shouldFormatNameWithOnlyFirstName() {
            String formatted = processor.getCustomerDisplayName("John", null, null);
            assertEquals("John", formatted);
        }

        @Test
        @DisplayName("Should format name with only last name")
        void shouldFormatNameWithOnlyLastName() {
            String formatted = processor.getCustomerDisplayName(null, "Doe", null);
            assertEquals("Doe", formatted);
        }

        @Test
        @DisplayName("Should handle all null values")
        void shouldHandleAllNullValues() {
            String formatted = processor.getCustomerDisplayName(null, null, null);
            assertEquals("", formatted);
        }

        @Test
        @DisplayName("Should trim extra spaces")
        void shouldTrimExtraSpaces() {
            String formatted = processor.getCustomerDisplayName("John", "Doe", "Dr.");
            assertEquals("Dr. John Doe", formatted);
            assertFalse(formatted.endsWith(" "));
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
