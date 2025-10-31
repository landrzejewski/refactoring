package pl.training.refactorings.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingSystemTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        ShoppingSystem.processor = new OrderProcessor();
        ShoppingSystem.customers.clear();
        ShoppingSystem.products.clear();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Nested
    @DisplayName("Static Field Initialization Tests")
    class StaticFieldTests {

        @Test
        @DisplayName("Should have processor initialized")
        void shouldHaveProcessorInitialized() {
            assertNotNull(ShoppingSystem.processor);
        }

        @Test
        @DisplayName("Should have customers list initialized")
        void shouldHaveCustomersListInitialized() {
            assertNotNull(ShoppingSystem.customers);
        }

        @Test
        @DisplayName("Should have products list initialized")
        void shouldHaveProductsListInitialized() {
            assertNotNull(ShoppingSystem.products);
        }

        @Test
        @DisplayName("Should allow adding customers to static list")
        void shouldAllowAddingCustomersToStaticList() {
            Customer customer = new Customer(
                "John Doe", "john@example.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            assertEquals(1, ShoppingSystem.customers.size());
            assertEquals("John Doe", ShoppingSystem.customers.get(0).name);
        }

        @Test
        @DisplayName("Should allow adding products to static list")
        void shouldAllowAddingProductsToStaticList() {
            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 999.99;
            ShoppingSystem.products.add(product);

            assertEquals(1, ShoppingSystem.products.size());
            assertEquals("P001", ShoppingSystem.products.get(0).id);
        }
    }

    @Nested
    @DisplayName("Product Initialization Tests")
    class ProductInitializationTests {

        @Test
        @DisplayName("Should initialize products with all required fields")
        void shouldInitializeProductsWithAllRequiredFields() {
            Product p1 = new Product();
            p1.id = "P001";
            p1.name = "Laptop";
            p1.price = 999.99;
            p1.stockQuantity = 50;
            p1.category = "Electronics";
            p1.description = "High performance laptop";
            p1.weight = 2.5;
            p1.supplier = "TechSupply Inc";

            assertEquals("P001", p1.id);
            assertEquals("Laptop", p1.name);
            assertEquals(999.99, p1.price, 0.001);
            assertEquals(50, p1.stockQuantity);
            assertEquals("Electronics", p1.category);
            assertEquals("High performance laptop", p1.description);
            assertEquals(2.5, p1.weight, 0.001);
            assertEquals("TechSupply Inc", p1.supplier);
        }

        @Test
        @DisplayName("Should allow public field modification")
        void shouldAllowPublicFieldModification() {
            Product p1 = new Product();
            p1.id = "P001";
            p1.price = 999.99;

            p1.price = 899.99;

            assertEquals(899.99, p1.price, 0.001);
        }
    }

    @Nested
    @DisplayName("Customer Creation Tests")
    class CustomerCreationTests {

        @Test
        @DisplayName("Should create premium customer")
        void shouldCreatePremiumCustomer() {
            Customer c1 = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );

            assertEquals("John Doe", c1.name);
            assertEquals("john@email.com", c1.email);
            assertTrue(c1.premium);
            assertEquals(500, c1.loyaltyPoints);
        }

        @Test
        @DisplayName("Should create regular customer")
        void shouldCreateRegularCustomer() {
            Customer c2 = new Customer(
                "Jane Smith", "jane@email.com", "555-5678",
                "456 Oak Ave", "Los Angeles", "CA", "90001",
                50, false
            );

            assertEquals("Jane Smith", c2.name);
            assertEquals("jane@email.com", c2.email);
            assertFalse(c2.premium);
            assertEquals(50, c2.loyaltyPoints);
        }
    }

    @Nested
    @DisplayName("Order Processing Integration Tests")
    class OrderProcessingTests {

        private Customer customer;
        private Product product;

        @BeforeEach
        void setUpOrderProcessing() {
            customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 999.99;
            product.stockQuantity = 50;
            product.category = "Electronics";
            product.description = "High performance laptop";
            product.weight = 2.5;
            product.supplier = "TechSupply Inc";
            ShoppingSystem.products.add(product);
        }

        @Test
        @DisplayName("Should process order through processor")
        void shouldProcessOrderThroughProcessor() {
            ShoppingSystem.processor.processOrder(
                customer.name,
                product.name,
                1,
                product.price,
                "credit",
                customer.email,
                customer.premium,
                customer.address
            );

            assertEquals(1, ShoppingSystem.processor.orderCount);
            assertEquals("John Doe", ShoppingSystem.processor.lastCustomer);
        }

        @Test
        @DisplayName("Should update customer order history")
        void shouldUpdateCustomerOrderHistory() {
            customer.addOrder("ORD001", product.price);

            assertEquals(1, customer.orderHistory.size());
            assertEquals("ORD001", customer.orderHistory.get(0));
        }

        @Test
        @DisplayName("Should maintain order count across multiple orders")
        void shouldMaintainOrderCountAcrossMultipleOrders() {
            ShoppingSystem.processor.processOrder(
                customer.name, product.name, 1, product.price,
                "credit", customer.email, customer.premium, customer.address
            );

            ShoppingSystem.processor.processOrder(
                customer.name, product.name, 1, product.price,
                "credit", customer.email, customer.premium, customer.address
            );

            assertEquals(2, ShoppingSystem.processor.orderCount);
        }
    }

    @Nested
    @DisplayName("Global State Tests")
    class GlobalStateTests {

        @Test
        @DisplayName("Should allow direct manipulation of processor")
        void shouldAllowDirectManipulationOfProcessor() {
            ShoppingSystem.processor.total = 1000.0;

            assertEquals(1000.0, ShoppingSystem.processor.total, 0.001);
        }

        @Test
        @DisplayName("Should allow direct access to customers list")
        void shouldAllowDirectAccessToCustomersList() {
            Customer c1 = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(c1);

            assertEquals(1, ShoppingSystem.customers.size());
            assertEquals("John Doe", ShoppingSystem.customers.get(0).name);
        }

        @Test
        @DisplayName("Should allow direct access to products list")
        void shouldAllowDirectAccessToProductsList() {
            Product p1 = new Product();
            p1.id = "P001";
            p1.name = "Laptop";
            ShoppingSystem.products.add(p1);

            assertEquals(1, ShoppingSystem.products.size());
            assertEquals("P001", ShoppingSystem.products.get(0).id);
        }

        @Test
        @DisplayName("Should share state across all operations")
        void shouldShareStateAcrossAllOperations() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 100.0;
            ShoppingSystem.products.add(product);

            ShoppingSystem.processor.processOrder(
                customer.name, product.name, 1, product.price,
                "credit", customer.email, customer.premium, customer.address
            );

            assertEquals(1, ShoppingSystem.customers.size());
            assertEquals(1, ShoppingSystem.products.size());
            assertEquals(1, ShoppingSystem.processor.orderCount);
        }
    }

    @Nested
    @DisplayName("demonstrateProblems Tests")
    class DemonstrateProblemsTests {

        @BeforeEach
        void setUpDemonstration() {
            Product laptop = new Product();
            laptop.id = "P001";
            laptop.name = "Laptop";
            laptop.price = 999.99;
            laptop.stockQuantity = 50;
            laptop.category = "Electronics";
            laptop.description = "High performance laptop";
            laptop.weight = 2.5;
            laptop.supplier = "TechSupply Inc";
            ShoppingSystem.products.add(laptop);

            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);
        }

        @Test
        @DisplayName("Should modify product price directly")
        void shouldModifyProductPriceDirectly() {
            Product laptop = ShoppingSystem.products.get(0);
            laptop.price = 899.99;

            assertEquals(899.99, ShoppingSystem.products.get(0).price, 0.001);
        }


        @Test
        @DisplayName("Should check stock fulfillment with side effects")
        void shouldCheckStockFulfillmentWithSideEffects() {
            Product laptop = ShoppingSystem.products.get(0);
            int initialStock = laptop.stockQuantity;

            boolean canFulfill = laptop.canFulfillOrder(10);

            assertTrue(canFulfill);
            assertEquals(initialStock - 10, laptop.stockQuantity);
        }

        @Test
        @DisplayName("Should calculate discount from customer class")
        void shouldCalculateDiscountFromCustomerClass() {
            Customer customer = ShoppingSystem.customers.get(0);
            double discount = customer.calculateDiscount(150.0);

            assertTrue(discount > 0);
        }

        @Test
        @DisplayName("Should calculate discount from product class")
        void shouldCalculateDiscountFromProductClass() {
            Product laptop = ShoppingSystem.products.get(0);
            Customer customer = ShoppingSystem.customers.get(0);
            double discount = laptop.applyDiscount(customer.premium, 150.0);

            assertTrue(discount > 0);
        }

        @Test
        @DisplayName("Should validate email in customer")
        void shouldValidateEmailInCustomer() {
            Customer newCustomer = new Customer(
                "Test User", "invalid-email", "555-0000",
                "789 Test St", "Chicago", "IL", "60601",
                0, false
            );

            assertFalse(newCustomer.validateEmail());
        }

        @Test
        @DisplayName("Should access processor order count")
        void shouldAccessProcessorOrderCount() {
            ShoppingSystem.processor.orderCount = 5;

            assertEquals(5, ShoppingSystem.processor.orderCount);
        }

    }

    @Nested
    @DisplayName("Report Generation Tests")
    class ReportGenerationTests {

        @BeforeEach
        void setUpReports() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 999.99;
            product.stockQuantity = 50;
            product.category = "Electronics";
            product.description = "High performance laptop";
            product.weight = 2.5;
            product.supplier = "TechSupply Inc";
            ShoppingSystem.products.add(product);

            ShoppingSystem.processor.processOrder(
                customer.name, product.name, 1, product.price,
                "credit", customer.email, customer.premium, customer.address
            );
        }

        @Test
        @DisplayName("Should generate detailed report")
        void shouldGenerateDetailedReport() {
            outputStreamCaptor.reset();
            ShoppingSystem.processor.generateReport(true);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("DETAILED ORDER REPORT"));
            assertTrue(output.contains("Total Orders:"));
        }

        @Test
        @DisplayName("Should generate summary report")
        void shouldGenerateSummaryReport() {
            outputStreamCaptor.reset();
            ShoppingSystem.processor.generateReport(false);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Orders:"));
            assertTrue(output.contains("Revenue:"));
        }
    }

    @Nested
    @DisplayName("Customer Information Display Tests")
    class CustomerInformationTests {

        @Test
        @DisplayName("Should display customer information")
        void shouldDisplayCustomerInformation() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            assertEquals("John Doe", customer.name);
            assertEquals("john@email.com", customer.email);
            assertTrue(customer.premium);
            assertEquals(500, customer.loyaltyPoints);
        }

        @Test
        @DisplayName("Should retrieve last order")
        void shouldRetrieveLastOrder() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            customer.addOrder("ORD001", 100.0);

            assertEquals("ORD001", customer.getLastOrder());
        }

        @Test
        @DisplayName("Should return null for customer with no orders")
        void shouldReturnNullForCustomerWithNoOrders() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );

            assertNull(customer.getLastOrder());
        }
    }

    @Nested
    @DisplayName("Product Information Display Tests")
    class ProductInformationTests {

        @Test
        @DisplayName("Should get product information")
        void shouldGetProductInformation() {
            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 999.99;
            product.stockQuantity = 50;
            product.category = "Electronics";
            product.description = "High performance laptop";
            product.weight = 2.5;
            product.supplier = "TechSupply Inc";

            String info = product.getProductInfo();

            assertTrue(info.contains("P001"));
            assertTrue(info.contains("Laptop"));
            assertTrue(info.contains("999.99"));
        }

        @Test
        @DisplayName("Should check product availability")
        void shouldCheckProductAvailability() {
            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.category = "Electronics";
            product.stockQuantity = 50;

            assertTrue(product.isAvailable());
        }

        @Test
        @DisplayName("Should check product unavailability when out of stock")
        void shouldCheckProductUnavailabilityWhenOutOfStock() {
            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.category = "Electronics";
            product.stockQuantity = 0;

            assertFalse(product.isAvailable());
        }

        @Test
        @DisplayName("Should check product unavailability when discontinued")
        void shouldCheckProductUnavailabilityWhenDiscontinued() {
            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.category = "discontinued";
            product.stockQuantity = 50;

            assertFalse(product.isAvailable());
        }
    }

    @Nested
    @DisplayName("Multiple Orders Integration Tests")
    class MultipleOrdersTests {

        @Test
        @DisplayName("Should handle multiple orders for same customer")
        void shouldHandleMultipleOrdersForSameCustomer() {
            Customer customer = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );
            ShoppingSystem.customers.add(customer);

            Product product1 = new Product();
            product1.id = "P001";
            product1.name = "Laptop";
            product1.price = 999.99;

            Product product2 = new Product();
            product2.id = "P002";
            product2.name = "Mouse";
            product2.price = 29.99;

            ShoppingSystem.processor.processOrder(
                customer.name, product1.name, 1, product1.price,
                "credit", customer.email, customer.premium, customer.address
            );

            ShoppingSystem.processor.processOrder(
                customer.name, product2.name, 1, product2.price,
                "credit", customer.email, customer.premium, customer.address
            );

            assertEquals(2, ShoppingSystem.processor.orderCount);
        }

        @Test
        @DisplayName("Should handle orders from different customers")
        void shouldHandleOrdersFromDifferentCustomers() {
            Customer customer1 = new Customer(
                "John Doe", "john@email.com", "555-1234",
                "123 Main St", "New York", "NY", "10001",
                500, true
            );

            Customer customer2 = new Customer(
                "Jane Smith", "jane@email.com", "555-5678",
                "456 Oak Ave", "Los Angeles", "CA", "90001",
                50, false
            );

            ShoppingSystem.customers.add(customer1);
            ShoppingSystem.customers.add(customer2);

            Product product = new Product();
            product.id = "P001";
            product.name = "Laptop";
            product.price = 999.99;

            ShoppingSystem.processor.processOrder(
                customer1.name, product.name, 1, product.price,
                "credit", customer1.email, customer1.premium, customer1.address
            );

            ShoppingSystem.processor.processOrder(
                customer2.name, product.name, 1, product.price,
                "debit", customer2.email, customer2.premium, customer2.address
            );

            assertEquals(2, ShoppingSystem.processor.orderCount);
            assertEquals("Jane Smith", ShoppingSystem.processor.lastCustomer);
        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        ShoppingSystem.processor = new OrderProcessor();
        ShoppingSystem.customers.clear();
        ShoppingSystem.products.clear();
    }
}
