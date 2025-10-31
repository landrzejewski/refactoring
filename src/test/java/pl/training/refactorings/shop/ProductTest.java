package pl.training.refactorings.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.id = "P001";
        product.name = "Laptop";
        product.price = 999.99;
        product.stockQuantity = 50;
        product.category = "Electronics";
        product.description = "High performance laptop";
        product.weight = 2.5;
        product.supplier = "TechSupply Inc";
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Nested
    @DisplayName("updateProduct Tests")
    class UpdateProductTests {

        @Test
        @DisplayName("Should update all product fields")
        void shouldUpdateAllProductFields() {
            product.updateProduct("Gaming Laptop", 1299.99, 30);

            assertEquals("Gaming Laptop", product.name);
            assertEquals(1299.99, product.price, 0.001);
            assertEquals(30, product.stockQuantity);
        }

        @Test
        @DisplayName("Should print update message")
        void shouldPrintUpdateMessage() {
            product.updateProduct("Gaming Laptop", 1299.99, 30);

            assertTrue(outputStreamCaptor.toString().contains("Product updated: Gaming Laptop"));
        }

        @Test
        @DisplayName("Should set price to zero for negative price")
        void shouldSetPriceToZeroForNegativePrice() {
            product.updateProduct("Laptop", -100.0, 50);

            assertEquals(0.0, product.price, 0.001);
            assertTrue(outputStreamCaptor.toString().contains("Warning: Invalid price, set to 0"));
        }

        @Test
        @DisplayName("Should not modify valid positive price")
        void shouldNotModifyValidPositivePrice() {
            product.updateProduct("Laptop", 100.0, 50);

            assertEquals(100.0, product.price, 0.001);
            assertFalse(outputStreamCaptor.toString().contains("Warning: Invalid price"));
        }

        @Test
        @DisplayName("Should handle zero price as valid")
        void shouldHandleZeroPriceAsValid() {
            product.updateProduct("Laptop", 0.0, 50);

            assertEquals(0.0, product.price, 0.001);
            assertFalse(outputStreamCaptor.toString().contains("Warning: Invalid price"));
        }

        @Test
        @DisplayName("Should call logUpdate")
        void shouldCallLogUpdate() {
            product.updateProduct("Laptop", 999.99, 50);

            assertTrue(outputStreamCaptor.toString().contains("[LOG] Product P001 updated at"));
        }

        @Test
        @DisplayName("Should update with zero stock")
        void shouldUpdateWithZeroStock() {
            product.updateProduct("Laptop", 999.99, 0);

            assertEquals(0, product.stockQuantity);
        }

        @Test
        @DisplayName("Should update with large stock quantity")
        void shouldUpdateWithLargeStockQuantity() {
            product.updateProduct("Laptop", 999.99, 10000);

            assertEquals(10000, product.stockQuantity);
        }
    }

    @Nested
    @DisplayName("canFulfillOrder Tests")
    class CanFulfillOrderTests {

        @Test
        @DisplayName("Should fulfill order when sufficient stock available")
        void shouldFulfillOrderWhenSufficientStockAvailable() {
            int initialStock = product.stockQuantity;
            boolean result = product.canFulfillOrder(10);

            assertTrue(result);
            assertEquals(initialStock - 10, product.stockQuantity);
            assertTrue(outputStreamCaptor.toString().contains("Stock reduced. Remaining: 40"));
        }

        @Test
        @DisplayName("Should fulfill order for exact stock amount")
        void shouldFulfillOrderForExactStockAmount() {
            product.stockQuantity = 10;
            boolean result = product.canFulfillOrder(10);

            assertTrue(result);
            assertEquals(0, product.stockQuantity);
        }

        @Test
        @DisplayName("Should reject order with zero quantity")
        void shouldRejectOrderWithZeroQuantity() {
            int initialStock = product.stockQuantity;
            boolean result = product.canFulfillOrder(0);

            assertFalse(result);
            assertEquals(initialStock, product.stockQuantity);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid quantity"));
        }

        @Test
        @DisplayName("Should reject order with negative quantity")
        void shouldRejectOrderWithNegativeQuantity() {
            int initialStock = product.stockQuantity;
            boolean result = product.canFulfillOrder(-5);

            assertFalse(result);
            assertEquals(initialStock, product.stockQuantity);
            assertTrue(outputStreamCaptor.toString().contains("Error: Invalid quantity"));
        }

        @Test
        @DisplayName("Should reject order when insufficient stock")
        void shouldRejectOrderWhenInsufficientStock() {
            product.stockQuantity = 5;
            boolean result = product.canFulfillOrder(10);

            assertFalse(result);
            assertEquals(5, product.stockQuantity);
            assertTrue(outputStreamCaptor.toString().contains("Error: Insufficient stock"));
        }

        @Test
        @DisplayName("Should notify supplier when stock insufficient")
        void shouldNotifySupplierWhenStockInsufficient() {
            product.stockQuantity = 5;
            product.canFulfillOrder(10);

            String output = outputStreamCaptor.toString();
            assertTrue(output.contains("Sending notification to supplier: TechSupply Inc"));
            assertTrue(output.contains("Product Laptop is low in stock!"));
        }

        @Test
        @DisplayName("Should not notify supplier when stock sufficient")
        void shouldNotNotifySupplierWhenStockSufficient() {
            product.canFulfillOrder(10);

            assertFalse(outputStreamCaptor.toString().contains("Sending notification to supplier"));
        }

        @Test
        @DisplayName("Should handle multiple fulfillments")
        void shouldHandleMultipleFulfillments() {
            product.stockQuantity = 100;
            
            assertTrue(product.canFulfillOrder(30));
            assertEquals(70, product.stockQuantity);
            
            assertTrue(product.canFulfillOrder(40));
            assertEquals(30, product.stockQuantity);
            
            assertTrue(product.canFulfillOrder(30));
            assertEquals(0, product.stockQuantity);
        }

        @Test
        @DisplayName("Should not fulfill after stock depleted")
        void shouldNotFulfillAfterStockDepleted() {
            product.stockQuantity = 10;
            
            assertTrue(product.canFulfillOrder(10));
            assertFalse(product.canFulfillOrder(1));
        }
    }

    @Nested
    @DisplayName("calculateShippingCost Tests")
    class CalculateShippingCostTests {



        @Test
        @DisplayName("Should provide free shipping for products over 100")
        void shouldProvideFreeShippingForProductsOver100() {
            product.price = 150.0;
            double cost = product.calculateShippingCost("domestic");
            
            assertEquals(0.0, cost, 0.001);
        }

        @Test
        @DisplayName("Should provide free shipping for international when price over 100")
        void shouldProvideFreeShippingForInternationalWhenPriceOver100() {
            product.price = 150.0;
            double cost = product.calculateShippingCost("international");
            
            assertEquals(0.0, cost, 0.001);
        }

        @Test
        @DisplayName("Should calculate shipping at boundary price 100")
        void shouldCalculateShippingAtBoundaryPrice100() {
            product.price = 100.0;
            double cost = product.calculateShippingCost("domestic");
            
            double expected = 5.0 + (2.5 * 0.5);
            assertEquals(expected, cost, 0.001);
        }


        @Test
        @DisplayName("Should calculate shipping for heavy product")
        void shouldCalculateShippingForHeavyProduct() {
            product.weight = 20.0;
            product.price = 50.0;
            double cost = product.calculateShippingCost("domestic");
            
            double expected = 5.0 + (20.0 * 0.5);
            assertEquals(expected, cost, 0.001);
        }
    }

    @Nested
    @DisplayName("getProductInfo Tests")
    class GetProductInfoTests {

        @Test
        @DisplayName("Should return complete product information")
        void shouldReturnCompleteProductInformation() {
            String info = product.getProductInfo();

            assertTrue(info.contains("Product ID: P001"));
            assertTrue(info.contains("Name: Laptop"));
            assertTrue(info.contains("Price: $999.99"));
            assertTrue(info.contains("Stock: 50"));
            assertTrue(info.contains("Category: Electronics"));
            assertTrue(info.contains("Weight: 2.5 kg"));
            assertTrue(info.contains("Supplier: TechSupply Inc"));
            assertTrue(info.contains("Description: High performance laptop"));
        }

        @Test
        @DisplayName("Should format all fields with labels")
        void shouldFormatAllFieldsWithLabels() {
            String info = product.getProductInfo();

            assertTrue(info.contains("Product ID:"));
            assertTrue(info.contains("Name:"));
            assertTrue(info.contains("Price:"));
            assertTrue(info.contains("Stock:"));
            assertTrue(info.contains("Category:"));
            assertTrue(info.contains("Weight:"));
            assertTrue(info.contains("Supplier:"));
            assertTrue(info.contains("Description:"));
        }

        @Test
        @DisplayName("Should handle null values gracefully")
        void shouldHandleNullValuesGracefully() {
            product.id = null;
            product.name = null;
            product.category = null;
            product.description = null;
            product.supplier = null;

            String info = product.getProductInfo();
            assertNotNull(info);
        }
    }

    @Nested
    @DisplayName("applyDiscount Tests")
    class ApplyDiscountTests {

        @Test
        @DisplayName("Should apply 20% discount for premium customer with order over 200")
        void shouldApply20PercentDiscountForPremiumOver200() {
            product.price = 100.0;
            double discount = product.applyDiscount(true, 250.0);

            assertEquals(20.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should apply 15% discount for premium customer with order over 100")
        void shouldApply15PercentDiscountForPremiumOver100() {
            product.price = 100.0;
            double discount = product.applyDiscount(true, 150.0);

            assertEquals(15.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should apply 10% discount for premium customer with order under 100")
        void shouldApply10PercentDiscountForPremiumUnder100() {
            product.price = 100.0;
            double discount = product.applyDiscount(true, 50.0);

            assertEquals(10.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should apply 5% discount for regular customer with order over 100")
        void shouldApply5PercentDiscountForRegularOver100() {
            product.price = 100.0;
            double discount = product.applyDiscount(false, 150.0);

            assertEquals(5.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should apply no discount for regular customer with order under 100")
        void shouldApplyNoDiscountForRegularUnder100() {
            product.price = 100.0;
            double discount = product.applyDiscount(false, 50.0);

            assertEquals(0.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should calculate discount based on product price")
        void shouldCalculateDiscountBasedOnProductPrice() {
            product.price = 200.0;
            double discount = product.applyDiscount(true, 250.0);

            assertEquals(40.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should handle boundary at 100 for premium")
        void shouldHandleBoundaryAt100ForPremium() {
            product.price = 100.0;
            double discount = product.applyDiscount(true, 100.0);

            assertEquals(10.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should handle boundary at 200 for premium")
        void shouldHandleBoundaryAt200ForPremium() {
            product.price = 100.0;
            double discount = product.applyDiscount(true, 200.0);

            assertEquals(15.0, discount, 0.001);
        }

        @Test
        @DisplayName("Should handle zero price")
        void shouldHandleZeroPrice() {
            product.price = 0.0;
            double discount = product.applyDiscount(true, 250.0);

            assertEquals(0.0, discount, 0.001);
        }
    }

    @Nested
    @DisplayName("formatPrice Tests")
    class FormatPriceTests {

        @Test
        @DisplayName("Should format price with dollar sign")
        void shouldFormatPriceWithDollarSign() {
            String formatted = Product.formatPrice(99.99);
            assertEquals("$99,99", formatted);
        }

        @Test
        @DisplayName("Should format whole number price")
        void shouldFormatWholeNumberPrice() {
            String formatted = Product.formatPrice(100.0);
            assertEquals("$100,00", formatted);
        }

        @Test
        @DisplayName("Should format zero price")
        void shouldFormatZeroPrice() {
            String formatted = Product.formatPrice(0.0);
            assertEquals("$0,00", formatted);
        }

        @Test
        @DisplayName("Should format price with one decimal")
        void shouldFormatPriceWithOneDecimal() {
            String formatted = Product.formatPrice(10.5);
            assertEquals("$10,50", formatted);
        }

        @Test
        @DisplayName("Should round to two decimal places")
        void shouldRoundToTwoDecimalPlaces() {
            String formatted = Product.formatPrice(10.999);
            assertEquals("$11,00", formatted);
        }
    }

    @Nested
    @DisplayName("isAvailable Tests")
    class IsAvailableTests {

        @Test
        @DisplayName("Should return true for available product")
        void shouldReturnTrueForAvailableProduct() {
            product.category = "Electronics";
            product.stockQuantity = 10;

            assertTrue(product.isAvailable());
        }

        @Test
        @DisplayName("Should return false for discontinued product")
        void shouldReturnFalseForDiscontinuedProduct() {
            product.category = "discontinued";
            product.stockQuantity = 10;

            assertFalse(product.isAvailable());
        }

        @Test
        @DisplayName("Should return false for zero stock")
        void shouldReturnFalseForZeroStock() {
            product.category = "Electronics";
            product.stockQuantity = 0;

            assertFalse(product.isAvailable());
        }

        @Test
        @DisplayName("Should return false for negative stock")
        void shouldReturnFalseForNegativeStock() {
            product.category = "Electronics";
            product.stockQuantity = -5;

            assertFalse(product.isAvailable());
        }

        @Test
        @DisplayName("Should check seasonal availability during summer")
        void shouldCheckSeasonalAvailabilityDuringSummer() {
            product.category = "seasonal";
            product.stockQuantity = 10;
            
            LocalDate now = LocalDate.now();
            int month = now.getMonthValue();
            
            boolean expected = month >= 6 && month <= 8;
            assertEquals(expected, product.isAvailable());
        }

        @Test
        @DisplayName("Should return false for seasonal product outside summer with stock")
        void shouldReturnFalseForSeasonalProductOutsideSummerWithStock() {
            product.category = "seasonal";
            product.stockQuantity = 10;
            
            LocalDate now = LocalDate.now();
            int month = now.getMonthValue();
            
            if (month < 6 || month > 8) {
                assertFalse(product.isAvailable());
            }
        }

        @Test
        @DisplayName("Should return false for seasonal product with zero stock")
        void shouldReturnFalseForSeasonalProductWithZeroStock() {
            product.category = "seasonal";
            product.stockQuantity = 0;

            assertFalse(product.isAvailable());
        }

        @Test
        @DisplayName("Should handle regular product categories")
        void shouldHandleRegularProductCategories() {
            product.category = "Electronics";
            product.stockQuantity = 1;

            assertTrue(product.isAvailable());
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
