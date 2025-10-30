package pl.training.refactorings;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Przykłady refaktoryzacji według książki "Refactoring: Improving the Design of Existing Code"
 * Martina Fowlera
 * 
 * Każda sekcja pokazuje kod PRZED i PO refaktoryzacji z uzasadnieniem.
 */
public class RefactoringExamples {

    public static void main(String[] args) {
        System.out.println("=== PRZYKŁADY REFAKTORYZACJI ===\n");
        
        // Przykład 1: Extract Method
        demonstrateExtractMethod();
        
        // Przykład 2: Replace Temp with Query
        demonstrateReplaceTempWithQuery();
        
        // Przykład 3: Introduce Parameter Object
        demonstrateIntroduceParameterObject();
        
        // Przykład 4: Replace Conditional with Polymorphism
        demonstrateReplaceConditionalWithPolymorphism();
        
        // Przykład 5: Decompose Conditional
        demonstrateDecomposeConditional();
        
        // Przykład 6: Replace Magic Numbers with Named Constants
        demonstrateReplaceMagicNumbers();
        
        // Przykład 7: Extract Class
        demonstrateExtractClass();
        
        // Przykład 8: Replace Type Code with Subclasses
        demonstrateReplaceTypeCodeWithSubclasses();
        
        // Przykład 9: Remove Middle Man
        demonstrateRemoveMiddleMan();
        
        // Przykład 10: Introduce Null Object
        demonstrateIntroduceNullObject();
        
        // Przykład 11: Replace Error Code with Exception
        demonstrateReplaceErrorCodeWithException();
        
        // Przykład 12: Pull Up Method
        demonstratePullUpMethod();
        
        // Przykład 13: Replace Nested Conditional with Guard Clauses
        demonstrateReplaceNestedConditional();
        
        // Przykład 14: Separate Query from Modifier
        demonstrateSeparateQueryFromModifier();
        
        // Przykład 15: Replace Constructor with Factory Method
        demonstrateReplaceConstructorWithFactory();
        
        // Przykład 16: Encapsulate Collection
        demonstrateEncapsulateCollection();
        
        // Przykład 17: Replace Data Value with Object
        demonstrateReplaceDataValueWithObject();
        
        // Przykład 18: Introduce Assertion
        demonstrateIntroduceAssertion();
        
        // Przykład 19: Replace Inheritance with Delegation
        demonstrateReplaceInheritanceWithDelegation();
        
        // Przykład 20: Consolidate Duplicate Conditional Fragments
        demonstrateConsolidateDuplicateConditional();
        
        // Przykład 21: Replace Algorithm
        demonstrateReplaceAlgorithm();
        
        // Przykład 22: Hide Delegate
        demonstrateHideDelegate();
        
        // Przykład 23: Replace Method with Method Object
        demonstrateReplaceMethodWithMethodObject();
        
        // Przykład 24: Preserve Whole Object
        demonstratePreserveWholeObject();
        
        // Przykład 25: Replace Array with Object
        demonstrateReplaceArrayWithObject();
        
        // Przykład 26: Introduce Foreign Method
        demonstrateIntroduceForeignMethod();
        
        // Przykład 27: Form Template Method
        demonstrateFormTemplateMethod();
        
        // Przykład 28: Replace Parameter with Method Call
        demonstrateReplaceParameterWithMethodCall();
        
        // Przykład 29: Parameterize Method
        demonstrateParameterizeMethod();
        
        // Przykład 30: Replace Exception with Test
        demonstrateReplaceExceptionWithTest();
        
        // Przykład 31: Introduce Gateway
        demonstrateIntroduceGateway();
        
        // Przykład 32: Extract Interface
        demonstrateExtractInterface();
        
        // Przykład 33: Collapse Hierarchy
        demonstrateCollapseHierarchy();
        
        // Przykład 34: Inline Class
        demonstrateInlineClass();
        
        // Przykład 35: Replace Record with Data Class
        demonstrateReplaceRecordWithDataClass();
        
        // Przykład 36: Introduce Explaining Variable
        demonstrateIntroduceExplainingVariable();
        
        // Przykład 37: Split Temporary Variable
        demonstrateSplitTemporaryVariable();
        
        // Przykład 38: Remove Assignments to Parameters
        demonstrateRemoveAssignmentsToParameters();
        
        // Przykład 39: Replace Method with Method Chain
        demonstrateReplaceMethodWithMethodChain();
        
        // Przykład 40: Introduce Local Extension
        demonstrateIntroduceLocalExtension();
        
        // Przykład 41: Self Encapsulate Field
        demonstrateSelfEncapsulateField();
        
        // Przykład 42: Replace Subclass with Fields
        demonstrateReplaceSubclassWithFields();
        
        // Przykład 43: Lazy Initialization
        demonstrateLazyInitialization();
        
        // Przykład 44: Replace Loop with Pipeline
        demonstrateReplaceLoopWithPipeline();
        
        // Przykład 45: Remove Dead Code
        demonstrateRemoveDeadCode();
        
        // Przykład 46: Introduce Special Case
        demonstrateIntroduceSpecialCase();
        
        // Przykład 47: Combine Functions into Class
        demonstrateCombineFunctionsIntoClass();
        
        // Przykład 48: Split Phase
        demonstrateSplitPhase();
        
        // Przykład 49: Replace Primitive with Object
        demonstrateReplacePrimitiveWithObject();
        
        // Przykład 50: Slide Statements
        demonstrateSlideStatements();
        
        // Przykład 51: Replace Derived Variable with Query
        demonstrateReplaceDerivedVariableWithQuery();
        
        // Przykład 52: Change Reference to Value
        demonstrateChangeReferenceToValue();
        
        // Przykład 53: Change Value to Reference
        demonstrateChangeValueToReference();
        
        // Przykład 54: Remove Setting Method
        demonstrateRemoveSettingMethod();
        
        // Przykład 55: Replace Command with Function
        demonstrateReplaceCommandWithFunction();
        
        // Przykład 56: Return Modified Value
        demonstrateReturnModifiedValue();
        
        // Przykład 57: Replace Constructor with Builder
        demonstrateReplaceConstructorWithBuilder();
        
        // Przykład 58: Encapsulate Downcast
        demonstrateEncapsulateDowncast();
        
        // Przykład 59: Replace Type Code with State/Strategy
        demonstrateReplaceTypeCodeWithStateStrategy();
        
        // Przykład 60: Introduce Parameter Object with Builder
        demonstrateIntroduceParameterObjectWithBuilder();
        
        // Przykład 61: Replace Loop with Recursion
        demonstrateReplaceLoopWithRecursion();
        
        // Przykład 62: Introduce Polymorphic Creation
        demonstrateIntroducePolymorphicCreation();
        
        // Przykład 63: Replace Callback with Promise
        demonstrateReplaceCallbackWithPromise();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("KONIEC - Przedstawiono 63 refaktoryzacje!");
        System.out.println("=".repeat(70));
    }

    // ============================================================================
    // PRZYKŁAD 1: EXTRACT METHOD
    // ============================================================================
    // Refaktoryzacja: Wydzielenie metody z długiej metody
    // Uzasadnienie: Poprawia czytelność, umożliwia wielokrotne użycie,
    //               ułatwia testowanie i nazwanie logiki
    
    static class ExtractMethodExample {
        
        // PRZED REFAKTORYZACJĄ - metoda robi zbyt wiele, trudna do zrozumienia
        static class Before {
            static class Order {
                List<OrderItem> items;
                String customerName;
                
                Order(String customerName, List<OrderItem> items) {
                    this.customerName = customerName;
                    this.items = items;
                }
                
                void printInvoice() {
                    double total = 0.0;
                    
                    // Drukowanie nagłówka
                    System.out.println("*****************************");
                    System.out.println("*** Faktura ***");
                    System.out.println("Klient: " + customerName);
                    System.out.println("*****************************");
                    
                    // Drukowanie pozycji
                    for (OrderItem item : items) {
                        double itemTotal = item.quantity * item.price;
                        total += itemTotal;
                        System.out.println(item.name + " - " + item.quantity + 
                                         " x " + item.price + " = " + itemTotal);
                    }
                    
                    // Drukowanie sumy
                    System.out.println("-----------------------------");
                    System.out.println("Suma: " + total);
                    System.out.println("*****************************");
                }
            }
        }
        
        // PO REFAKTORYZACJI - wydzielone metody, każda robi jedną rzecz
        static class After {
            static class Order {
                List<OrderItem> items;
                String customerName;
                
                Order(String customerName, List<OrderItem> items) {
                    this.customerName = customerName;
                    this.items = items;
                }
                
                void printInvoice() {
                    printHeader();
                    double total = printItems();
                    printFooter(total);
                }
                
                private void printHeader() {
                    System.out.println("*****************************");
                    System.out.println("*** Faktura ***");
                    System.out.println("Klient: " + customerName);
                    System.out.println("*****************************");
                }
                
                private double printItems() {
                    double total = 0.0;
                    for (OrderItem item : items) {
                        double itemTotal = calculateItemTotal(item);
                        total += itemTotal;
                        printItem(item, itemTotal);
                    }
                    return total;
                }
                
                private double calculateItemTotal(OrderItem item) {
                    return item.quantity * item.price;
                }
                
                private void printItem(OrderItem item, double itemTotal) {
                    System.out.println(item.name + " - " + item.quantity + 
                                     " x " + item.price + " = " + itemTotal);
                }
                
                private void printFooter(double total) {
                    System.out.println("-----------------------------");
                    System.out.println("Suma: " + total);
                    System.out.println("*****************************");
                }
            }
        }
    }
    
    static class OrderItem {
        String name;
        int quantity;
        double price;
        
        OrderItem(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
    }

    // ============================================================================
    // PRZYKŁAD 2: REPLACE TEMP WITH QUERY
    // ============================================================================
    // Refaktoryzacja: Zastąpienie zmiennej tymczasowej wywołaniem metody
    // Uzasadnienie: Eliminuje duplikację, umożliwia łatwiejsze wydzielanie metod,
    //               zmniejsza ryzyko błędów przy modyfikacji
    
    static class ReplaceTempWithQueryExample {
        
        // PRZED REFAKTORYZACJĄ - zmienne tymczasowe utrudniają wydzielanie logiki
        static class Before {
            static class PriceCalculator {
                private double basePrice;
                private int quantity;
                private double discountRate;
                
                PriceCalculator(double basePrice, int quantity, double discountRate) {
                    this.basePrice = basePrice;
                    this.quantity = quantity;
                    this.discountRate = discountRate;
                }
                
                double calculateFinalPrice() {
                    double baseAmount = quantity * basePrice;
                    double discount = baseAmount * discountRate;
                    double shipping = Math.min(baseAmount * 0.1, 100.0);
                    
                    return baseAmount - discount + shipping;
                }
            }
        }
        
        // PO REFAKTORYZACJI - obliczenia w osobnych metodach, łatwiejsze do testowania
        static class After {
            static class PriceCalculator {
                private double basePrice;
                private int quantity;
                private double discountRate;
                
                PriceCalculator(double basePrice, int quantity, double discountRate) {
                    this.basePrice = basePrice;
                    this.quantity = quantity;
                    this.discountRate = discountRate;
                }
                
                double calculateFinalPrice() {
                    return getBaseAmount() - getDiscount() + getShipping();
                }
                
                private double getBaseAmount() {
                    return quantity * basePrice;
                }
                
                private double getDiscount() {
                    return getBaseAmount() * discountRate;
                }
                
                private double getShipping() {
                    return Math.min(getBaseAmount() * 0.1, 100.0);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 3: INTRODUCE PARAMETER OBJECT
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie obiektu parametrów
    // Uzasadnienie: Redukuje liczbę parametrów, grupuje powiązane dane,
    //               ułatwia dodawanie nowych pól bez zmiany sygnatur metod
    
    static class IntroduceParameterObjectExample {
        
        // PRZED REFAKTORYZACJĄ - długa lista parametrów
        static class Before {
            static class ReportGenerator {
                void generateReport(String title, LocalDate startDate, LocalDate endDate,
                                  String author, String department, boolean includeCharts,
                                  boolean includeDetails, String outputFormat) {
                    System.out.println("Generowanie raportu:");
                    System.out.println("Tytuł: " + title);
                    System.out.println("Okres: " + startDate + " - " + endDate);
                    System.out.println("Autor: " + author);
                    System.out.println("Dział: " + department);
                    System.out.println("Wykresy: " + includeCharts);
                    System.out.println("Szczegóły: " + includeDetails);
                    System.out.println("Format: " + outputFormat);
                }
            }
        }
        
        // PO REFAKTORYZACJI - parametry zgrupowane w obiekt
        static class After {
            static class ReportConfig {
                private final String title;
                private final LocalDate startDate;
                private final LocalDate endDate;
                private final String author;
                private final String department;
                private final boolean includeCharts;
                private final boolean includeDetails;
                private final String outputFormat;
                
                ReportConfig(String title, LocalDate startDate, LocalDate endDate,
                           String author, String department, boolean includeCharts,
                           boolean includeDetails, String outputFormat) {
                    this.title = title;
                    this.startDate = startDate;
                    this.endDate = endDate;
                    this.author = author;
                    this.department = department;
                    this.includeCharts = includeCharts;
                    this.includeDetails = includeDetails;
                    this.outputFormat = outputFormat;
                }
                
                // Gettery
                public String getTitle() { return title; }
                public LocalDate getStartDate() { return startDate; }
                public LocalDate getEndDate() { return endDate; }
                public String getAuthor() { return author; }
                public String getDepartment() { return department; }
                public boolean isIncludeCharts() { return includeCharts; }
                public boolean isIncludeDetails() { return includeDetails; }
                public String getOutputFormat() { return outputFormat; }
            }
            
            static class ReportGenerator {
                void generateReport(ReportConfig config) {
                    System.out.println("Generowanie raportu:");
                    System.out.println("Tytuł: " + config.getTitle());
                    System.out.println("Okres: " + config.getStartDate() + " - " + config.getEndDate());
                    System.out.println("Autor: " + config.getAuthor());
                    System.out.println("Dział: " + config.getDepartment());
                    System.out.println("Wykresy: " + config.isIncludeCharts());
                    System.out.println("Szczegóły: " + config.isIncludeDetails());
                    System.out.println("Format: " + config.getOutputFormat());
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 4: REPLACE CONDITIONAL WITH POLYMORPHISM
    // ============================================================================
    // Refaktoryzacja: Zastąpienie instrukcji warunkowych polimorfizmem
    // Uzasadnienie: Eliminuje switch/if-else, ułatwia dodawanie nowych typów,
    //               zgodne z Open/Closed Principle
    
    static class ReplaceConditionalExample {
        
        // PRZED REFAKTORYZACJĄ - switch na typie pracownika
        static class Before {
            enum EmployeeType { ENGINEER, MANAGER, SALESPERSON }
            
            static class Employee {
                private EmployeeType type;
                private double baseSalary;
                private double commission;
                private double bonus;
                
                Employee(EmployeeType type, double baseSalary, double commission, double bonus) {
                    this.type = type;
                    this.baseSalary = baseSalary;
                    this.commission = commission;
                    this.bonus = bonus;
                }
                
                double calculateSalary() {
                    switch (type) {
                        case ENGINEER:
                            return baseSalary + bonus;
                        case MANAGER:
                            return baseSalary + bonus * 2;
                        case SALESPERSON:
                            return baseSalary + commission;
                        default:
                            throw new IllegalStateException("Unknown employee type");
                    }
                }
                
                String getRole() {
                    switch (type) {
                        case ENGINEER:
                            return "Inżynier";
                        case MANAGER:
                            return "Menedżer";
                        case SALESPERSON:
                            return "Sprzedawca";
                        default:
                            throw new IllegalStateException("Unknown employee type");
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - polimorfizm zamiast warunków
        static class After {
            static abstract class Employee {
                protected double baseSalary;
                
                Employee(double baseSalary) {
                    this.baseSalary = baseSalary;
                }
                
                abstract double calculateSalary();
                abstract String getRole();
            }
            
            static class Engineer extends Employee {
                private double bonus;
                
                Engineer(double baseSalary, double bonus) {
                    super(baseSalary);
                    this.bonus = bonus;
                }
                
                @Override
                double calculateSalary() {
                    return baseSalary + bonus;
                }
                
                @Override
                String getRole() {
                    return "Inżynier";
                }
            }
            
            static class Manager extends Employee {
                private double bonus;
                
                Manager(double baseSalary, double bonus) {
                    super(baseSalary);
                    this.bonus = bonus;
                }
                
                @Override
                double calculateSalary() {
                    return baseSalary + bonus * 2;
                }
                
                @Override
                String getRole() {
                    return "Menedżer";
                }
            }
            
            static class Salesperson extends Employee {
                private double commission;
                
                Salesperson(double baseSalary, double commission) {
                    super(baseSalary);
                    this.commission = commission;
                }
                
                @Override
                double calculateSalary() {
                    return baseSalary + commission;
                }
                
                @Override
                String getRole() {
                    return "Sprzedawca";
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 5: DECOMPOSE CONDITIONAL
    // ============================================================================
    // Refaktoryzacja: Dekompozycja złożonych warunków
    // Uzasadnienie: Warunki stają się samodokumentujące, łatwiejsze do zrozumienia
    //               i modyfikacji
    
    static class DecomposeConditionalExample {
        
        // PRZED REFAKTORYZACJĄ - skomplikowane warunki
        static class Before {
            static class SubscriptionService {
                double calculatePrice(LocalDate date, int quantity, boolean isPremium) {
                    if (date.getMonthValue() < 4 || date.getMonthValue() > 10) {
                        return quantity * 10.0 * (isPremium ? 0.8 : 1.0);
                    } else {
                        return quantity * 15.0 * (isPremium ? 0.9 : 1.0);
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - warunki wydzielone do nazwanych metod
        static class After {
            static class SubscriptionService {
                private static final double WINTER_PRICE = 10.0;
                private static final double SUMMER_PRICE = 15.0;
                private static final double PREMIUM_WINTER_DISCOUNT = 0.8;
                private static final double PREMIUM_SUMMER_DISCOUNT = 0.9;
                
                double calculatePrice(LocalDate date, int quantity, boolean isPremium) {
                    if (isWinterSeason(date)) {
                        return calculateWinterPrice(quantity, isPremium);
                    } else {
                        return calculateSummerPrice(quantity, isPremium);
                    }
                }
                
                private boolean isWinterSeason(LocalDate date) {
                    int month = date.getMonthValue();
                    return month < 4 || month > 10;
                }
                
                private double calculateWinterPrice(int quantity, boolean isPremium) {
                    double basePrice = quantity * WINTER_PRICE;
                    return isPremium ? basePrice * PREMIUM_WINTER_DISCOUNT : basePrice;
                }
                
                private double calculateSummerPrice(int quantity, boolean isPremium) {
                    double basePrice = quantity * SUMMER_PRICE;
                    return isPremium ? basePrice * PREMIUM_SUMMER_DISCOUNT : basePrice;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 6: REPLACE MAGIC NUMBERS WITH NAMED CONSTANTS
    // ============================================================================
    // Refaktoryzacja: Zastąpienie magicznych liczb nazwanymi stałymi
    // Uzasadnienie: Kod staje się samodokumentujący, łatwiej zarządzać wartościami,
    //               łatwiejsza konserwacja
    
    static class ReplaceMagicNumbersExample {
        
        // PRZED REFAKTORYZACJĄ - magiczne liczby w kodzie
        static class Before {
            static class ShippingCalculator {
                double calculateShipping(double weight, double distance) {
                    if (weight <= 2.0) {
                        return 5.0 + distance * 0.5;
                    } else if (weight <= 10.0) {
                        return 10.0 + distance * 0.8;
                    } else {
                        return 20.0 + distance * 1.2;
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - nazwane stałe zamiast magicznych liczb
        static class After {
            static class ShippingCalculator {
                private static final double LIGHT_WEIGHT_THRESHOLD = 2.0;
                private static final double MEDIUM_WEIGHT_THRESHOLD = 10.0;
                
                private static final double LIGHT_BASE_COST = 5.0;
                private static final double MEDIUM_BASE_COST = 10.0;
                private static final double HEAVY_BASE_COST = 20.0;
                
                private static final double LIGHT_DISTANCE_RATE = 0.5;
                private static final double MEDIUM_DISTANCE_RATE = 0.8;
                private static final double HEAVY_DISTANCE_RATE = 1.2;
                
                double calculateShipping(double weight, double distance) {
                    if (isLightPackage(weight)) {
                        return LIGHT_BASE_COST + distance * LIGHT_DISTANCE_RATE;
                    } else if (isMediumPackage(weight)) {
                        return MEDIUM_BASE_COST + distance * MEDIUM_DISTANCE_RATE;
                    } else {
                        return HEAVY_BASE_COST + distance * HEAVY_DISTANCE_RATE;
                    }
                }
                
                private boolean isLightPackage(double weight) {
                    return weight <= LIGHT_WEIGHT_THRESHOLD;
                }
                
                private boolean isMediumPackage(double weight) {
                    return weight <= MEDIUM_WEIGHT_THRESHOLD;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 7: EXTRACT CLASS
    // ============================================================================
    // Refaktoryzacja: Wydzielenie klasy z klasy robiącej zbyt wiele
    // Uzasadnienie: Każda klasa ma jedną odpowiedzialność (SRP),
    //               lepsze rozdzielenie obowiązków, łatwiejsze testowanie
    
    static class ExtractClassExample {
        
        // PRZED REFAKTORYZACJĄ - klasa robi zbyt wiele
        static class Before {
            static class Person {
                private String firstName;
                private String lastName;
                private String street;
                private String city;
                private String zipCode;
                private String phoneNumber;
                private String email;
                
                Person(String firstName, String lastName, String street, String city,
                      String zipCode, String phoneNumber, String email) {
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.street = street;
                    this.city = city;
                    this.zipCode = zipCode;
                    this.phoneNumber = phoneNumber;
                    this.email = email;
                }
                
                String getFullName() {
                    return firstName + " " + lastName;
                }
                
                String getFullAddress() {
                    return street + ", " + zipCode + " " + city;
                }
                
                String getContactInfo() {
                    return "Tel: " + phoneNumber + ", Email: " + email;
                }
                
                // Gettery i settery dla wszystkich pól...
            }
        }
        
        // PO REFAKTORYZACJI - osobne klasy dla różnych koncepcji
        static class After {
            static class Address {
                private final String street;
                private final String city;
                private final String zipCode;
                
                Address(String street, String city, String zipCode) {
                    this.street = street;
                    this.city = city;
                    this.zipCode = zipCode;
                }
                
                String getFullAddress() {
                    return street + ", " + zipCode + " " + city;
                }
                
                public String getStreet() { return street; }
                public String getCity() { return city; }
                public String getZipCode() { return zipCode; }
            }
            
            static class ContactInfo {
                private final String phoneNumber;
                private final String email;
                
                ContactInfo(String phoneNumber, String email) {
                    this.phoneNumber = phoneNumber;
                    this.email = email;
                }
                
                String getContactDetails() {
                    return "Tel: " + phoneNumber + ", Email: " + email;
                }
                
                public String getPhoneNumber() { return phoneNumber; }
                public String getEmail() { return email; }
            }
            
            static class Person {
                private final String firstName;
                private final String lastName;
                private final Address address;
                private final ContactInfo contactInfo;
                
                Person(String firstName, String lastName, Address address, ContactInfo contactInfo) {
                    this.firstName = firstName;
                    this.lastName = lastName;
                    this.address = address;
                    this.contactInfo = contactInfo;
                }
                
                String getFullName() {
                    return firstName + " " + lastName;
                }
                
                public String getFirstName() { return firstName; }
                public String getLastName() { return lastName; }
                public Address getAddress() { return address; }
                public ContactInfo getContactInfo() { return contactInfo; }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 8: REPLACE TYPE CODE WITH SUBCLASSES
    // ============================================================================
    // Refaktoryzacja: Zastąpienie kodu typu podklasami
    // Uzasadnienie: Eliminuje kod warunkowy, umożliwia specyficzne zachowania
    //               dla każdego typu, zgodne z OOP
    
    static class ReplaceTypeCodeExample {
        
        // PRZED REFAKTORYZACJĄ - kod typu z warunkami
        static class Before {
            enum AccountType { CHECKING, SAVINGS, INVESTMENT }
            
            static class BankAccount {
                private AccountType type;
                private double balance;
                private double interestRate;
                
                BankAccount(AccountType type, double balance) {
                    this.type = type;
                    this.balance = balance;
                    this.interestRate = calculateInterestRate();
                }
                
                private double calculateInterestRate() {
                    switch (type) {
                        case CHECKING: return 0.0;
                        case SAVINGS: return 2.5;
                        case INVESTMENT: return 5.0;
                        default: throw new IllegalStateException();
                    }
                }
                
                double calculateMonthlyFee() {
                    switch (type) {
                        case CHECKING: return balance < 1000 ? 10.0 : 0.0;
                        case SAVINGS: return 0.0;
                        case INVESTMENT: return 25.0;
                        default: throw new IllegalStateException();
                    }
                }
                
                double calculateYearlyInterest() {
                    return balance * (interestRate / 100);
                }
                
                boolean canWithdraw(double amount) {
                    switch (type) {
                        case CHECKING: return balance >= amount;
                        case SAVINGS: return balance - amount >= 100;
                        case INVESTMENT: return balance - amount >= 5000;
                        default: throw new IllegalStateException();
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - podklasy zamiast kodu typu
        static class After {
            static abstract class BankAccount {
                protected double balance;
                
                BankAccount(double balance) {
                    this.balance = balance;
                }
                
                abstract double getInterestRate();
                abstract double calculateMonthlyFee();
                abstract boolean canWithdraw(double amount);
                
                double calculateYearlyInterest() {
                    return balance * (getInterestRate() / 100);
                }
                
                public double getBalance() {
                    return balance;
                }
            }
            
            static class CheckingAccount extends BankAccount {
                private static final double MIN_BALANCE_FOR_FREE = 1000.0;
                private static final double MONTHLY_FEE = 10.0;
                
                CheckingAccount(double balance) {
                    super(balance);
                }
                
                @Override
                double getInterestRate() {
                    return 0.0;
                }
                
                @Override
                double calculateMonthlyFee() {
                    return balance < MIN_BALANCE_FOR_FREE ? MONTHLY_FEE : 0.0;
                }
                
                @Override
                boolean canWithdraw(double amount) {
                    return balance >= amount;
                }
            }
            
            static class SavingsAccount extends BankAccount {
                private static final double INTEREST_RATE = 2.5;
                private static final double MIN_BALANCE = 100.0;
                
                SavingsAccount(double balance) {
                    super(balance);
                }
                
                @Override
                double getInterestRate() {
                    return INTEREST_RATE;
                }
                
                @Override
                double calculateMonthlyFee() {
                    return 0.0;
                }
                
                @Override
                boolean canWithdraw(double amount) {
                    return balance - amount >= MIN_BALANCE;
                }
            }
            
            static class InvestmentAccount extends BankAccount {
                private static final double INTEREST_RATE = 5.0;
                private static final double MONTHLY_FEE = 25.0;
                private static final double MIN_BALANCE = 5000.0;
                
                InvestmentAccount(double balance) {
                    super(balance);
                }
                
                @Override
                double getInterestRate() {
                    return INTEREST_RATE;
                }
                
                @Override
                double calculateMonthlyFee() {
                    return MONTHLY_FEE;
                }
                
                @Override
                boolean canWithdraw(double amount) {
                    return balance - amount >= MIN_BALANCE;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 9: REMOVE MIDDLE MAN
    // ============================================================================
    // Refaktoryzacja: Usunięcie pośrednika
    // Uzasadnienie: Gdy klasa tylko deleguje wywołania, lepiej dać bezpośredni dostęp
    
    static class RemoveMiddleManExample {
        
        // PRZED REFAKTORYZACJĄ - zbyt wiele delegacji
        static class Before {
            static class Department {
                private String name;
                private Manager manager;
                
                Department(String name, Manager manager) {
                    this.name = name;
                    this.manager = manager;
                }
                
                String getManagerName() {
                    return manager.getName();
                }
                
                String getManagerEmail() {
                    return manager.getEmail();
                }
                
                int getManagerExperience() {
                    return manager.getYearsOfExperience();
                }
                
                void approveExpense(double amount) {
                    manager.approveExpense(amount);
                }
            }
            
            static class Manager {
                private String name;
                private String email;
                private int yearsOfExperience;
                
                Manager(String name, String email, int yearsOfExperience) {
                    this.name = name;
                    this.email = email;
                    this.yearsOfExperience = yearsOfExperience;
                }
                
                String getName() { return name; }
                String getEmail() { return email; }
                int getYearsOfExperience() { return yearsOfExperience; }
                
                void approveExpense(double amount) {
                    System.out.println(name + " zatwierdza wydatek: " + amount + " PLN");
                }
            }
        }
        
        // PO REFAKTORYZACJI - bezpośredni dostęp do managera
        static class After {
            static class Department {
                private String name;
                private Manager manager;
                
                Department(String name, Manager manager) {
                    this.name = name;
                    this.manager = manager;
                }
                
                public Manager getManager() {
                    return manager;
                }
                
                public String getName() {
                    return name;
                }
            }
            
            static class Manager {
                private String name;
                private String email;
                private int yearsOfExperience;
                
                Manager(String name, String email, int yearsOfExperience) {
                    this.name = name;
                    this.email = email;
                    this.yearsOfExperience = yearsOfExperience;
                }
                
                public String getName() { return name; }
                public String getEmail() { return email; }
                public int getYearsOfExperience() { return yearsOfExperience; }
                
                public void approveExpense(double amount) {
                    System.out.println(name + " zatwierdza wydatek: " + amount + " PLN");
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 10: INTRODUCE NULL OBJECT
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie obiektu Null
    // Uzasadnienie: Eliminuje sprawdzanie null, zapewnia bezpieczne domyślne zachowanie
    
    static class IntroduceNullObjectExample {
        
        // PRZED REFAKTORYZACJĄ - ciągłe sprawdzanie null
        static class Before {
            static class Customer {
                private String name;
                private String email;
                
                Customer(String name, String email) {
                    this.name = name;
                    this.email = email;
                }
                
                String getName() { return name; }
                String getEmail() { return email; }
                
                double getDiscount() {
                    return 10.0;
                }
            }
            
            static class Order {
                private Customer customer;
                private double amount;
                
                Order(Customer customer, double amount) {
                    this.customer = customer;
                    this.amount = amount;
                }
                
                String getCustomerName() {
                    return customer != null ? customer.getName() : "Gość";
                }
                
                double calculateTotal() {
                    double discount = customer != null ? customer.getDiscount() : 0.0;
                    return amount * (1 - discount / 100);
                }
                
                void sendConfirmation() {
                    if (customer != null && customer.getEmail() != null) {
                        System.out.println("Wysyłanie potwierdzenia do: " + customer.getEmail());
                    } else {
                        System.out.println("Brak adresu email - potwierdzenie nie wysłane");
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - obiekt Null zamiast null
        static class After {
            static abstract class Customer {
                abstract String getName();
                abstract String getEmail();
                abstract double getDiscount();
                abstract boolean isNull();
            }
            
            static class RealCustomer extends Customer {
                private String name;
                private String email;
                
                RealCustomer(String name, String email) {
                    this.name = name;
                    this.email = email;
                }
                
                @Override
                String getName() { return name; }
                
                @Override
                String getEmail() { return email; }
                
                @Override
                double getDiscount() { return 10.0; }
                
                @Override
                boolean isNull() { return false; }
            }
            
            static class NullCustomer extends Customer {
                @Override
                String getName() { return "Gość"; }
                
                @Override
                String getEmail() { return ""; }
                
                @Override
                double getDiscount() { return 0.0; }
                
                @Override
                boolean isNull() { return true; }
            }
            
            static class Order {
                private Customer customer;
                private double amount;
                
                Order(Customer customer, double amount) {
                    this.customer = customer != null ? customer : new NullCustomer();
                    this.amount = amount;
                }
                
                String getCustomerName() {
                    return customer.getName();
                }
                
                double calculateTotal() {
                    double discount = customer.getDiscount();
                    return amount * (1 - discount / 100);
                }
                
                void sendConfirmation() {
                    if (!customer.isNull() && !customer.getEmail().isEmpty()) {
                        System.out.println("Wysyłanie potwierdzenia do: " + customer.getEmail());
                    } else {
                        System.out.println("Brak adresu email - potwierdzenie nie wysłane");
                    }
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 11: REPLACE ERROR CODE WITH EXCEPTION
    // ============================================================================
    // Refaktoryzacja: Zastąpienie kodów błędów wyjątkami
    // Uzasadnienie: Czytelniejsza obsługa błędów, wymuszenie ich obsługi,
    //               oddzielenie ścieżki sukcesu od obsługi błędów
    
    static class ReplaceErrorCodeExample {
        
        // PRZED REFAKTORYZACJĄ - kody błędów jako return value
        static class Before {
            static class PaymentProcessor {
                static final int SUCCESS = 0;
                static final int INSUFFICIENT_FUNDS = -1;
                static final int INVALID_CARD = -2;
                static final int NETWORK_ERROR = -3;
                
                int processPayment(String cardNumber, double amount, double balance) {
                    if (cardNumber == null || cardNumber.length() != 16) {
                        return INVALID_CARD;
                    }
                    
                    if (balance < amount) {
                        return INSUFFICIENT_FUNDS;
                    }
                    
                    // Symulacja błędu sieciowego
                    if (Math.random() < 0.1) {
                        return NETWORK_ERROR;
                    }
                    
                    System.out.println("Płatność przetworzona: " + amount + " PLN");
                    return SUCCESS;
                }
            }
        }
        
        // PO REFAKTORYZACJI - wyjątki zamiast kodów błędów
        static class After {
            static class InsufficientFundsException extends Exception {
                InsufficientFundsException(String message) {
                    super(message);
                }
            }
            
            static class InvalidCardException extends Exception {
                InvalidCardException(String message) {
                    super(message);
                }
            }
            
            static class NetworkException extends Exception {
                NetworkException(String message) {
                    super(message);
                }
            }
            
            static class PaymentProcessor {
                void processPayment(String cardNumber, double amount, double balance) 
                        throws InsufficientFundsException, InvalidCardException, NetworkException {
                    
                    validateCard(cardNumber);
                    validateFunds(amount, balance);
                    performTransaction(amount);
                    
                    System.out.println("Płatność przetworzona: " + amount + " PLN");
                }
                
                private void validateCard(String cardNumber) throws InvalidCardException {
                    if (cardNumber == null || cardNumber.length() != 16) {
                        throw new InvalidCardException("Nieprawidłowy numer karty");
                    }
                }
                
                private void validateFunds(double amount, double balance) 
                        throws InsufficientFundsException {
                    if (balance < amount) {
                        throw new InsufficientFundsException(
                            "Niewystarczające środki. Saldo: " + balance + ", wymagane: " + amount);
                    }
                }
                
                private void performTransaction(double amount) throws NetworkException {
                    // Symulacja błędu sieciowego
                    if (Math.random() < 0.1) {
                        throw new NetworkException("Błąd połączenia z serwerem płatności");
                    }
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 12: PULL UP METHOD
    // ============================================================================
    // Refaktoryzacja: Wyciągnięcie metody do nadklasy
    // Uzasadnienie: Eliminuje duplikację kodu w podklasach
    
    static class PullUpMethodExample {
        
        // PRZED REFAKTORYZACJĄ - zduplikowane metody w podklasach
        static class Before {
            static class EmailNotification {
                private String recipient;
                private String subject;
                private String body;
                
                EmailNotification(String recipient, String subject, String body) {
                    this.recipient = recipient;
                    this.subject = subject;
                    this.body = body;
                }
                
                void send() {
                    validate();
                    log();
                    System.out.println("Wysyłanie email do: " + recipient);
                    System.out.println("Temat: " + subject);
                }
                
                private void validate() {
                    if (recipient == null || recipient.isEmpty()) {
                        throw new IllegalStateException("Odbiorca nie może być pusty");
                    }
                }
                
                private void log() {
                    System.out.println("[LOG] Przygotowanie do wysyłki: " + subject);
                }
            }
            
            static class SmsNotification {
                private String phoneNumber;
                private String message;
                
                SmsNotification(String phoneNumber, String message) {
                    this.phoneNumber = phoneNumber;
                    this.message = message;
                }
                
                void send() {
                    validate();
                    log();
                    System.out.println("Wysyłanie SMS do: " + phoneNumber);
                    System.out.println("Treść: " + message);
                }
                
                private void validate() {
                    if (phoneNumber == null || phoneNumber.isEmpty()) {
                        throw new IllegalStateException("Numer telefonu nie może być pusty");
                    }
                }
                
                private void log() {
                    System.out.println("[LOG] Przygotowanie do wysyłki SMS");
                }
            }
        }
        
        // PO REFAKTORYZACJI - wspólna logika w nadklasie
        static class After {
            static abstract class Notification {
                protected String recipient;
                
                Notification(String recipient) {
                    this.recipient = recipient;
                }
                
                public void send() {
                    validate();
                    log();
                    doSend();
                }
                
                protected void validate() {
                    if (recipient == null || recipient.isEmpty()) {
                        throw new IllegalStateException("Odbiorca nie może być pusty");
                    }
                }
                
                protected void log() {
                    System.out.println("[LOG] Przygotowanie do wysyłki: " + getNotificationType());
                }
                
                protected abstract void doSend();
                protected abstract String getNotificationType();
            }
            
            static class EmailNotification extends Notification {
                private String subject;
                private String body;
                
                EmailNotification(String recipient, String subject, String body) {
                    super(recipient);
                    this.subject = subject;
                    this.body = body;
                }
                
                @Override
                protected void doSend() {
                    System.out.println("Wysyłanie email do: " + recipient);
                    System.out.println("Temat: " + subject);
                }
                
                @Override
                protected String getNotificationType() {
                    return "Email";
                }
            }
            
            static class SmsNotification extends Notification {
                private String message;
                
                SmsNotification(String phoneNumber, String message) {
                    super(phoneNumber);
                    this.message = message;
                }
                
                @Override
                protected void doSend() {
                    System.out.println("Wysyłanie SMS do: " + recipient);
                    System.out.println("Treść: " + message);
                }
                
                @Override
                protected String getNotificationType() {
                    return "SMS";
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 13: REPLACE NESTED CONDITIONAL WITH GUARD CLAUSES
    // ============================================================================
    // Refaktoryzacja: Zastąpienie zagnieżdżonych warunków klauzulami strażniczymi
    // Uzasadnienie: Czytelniejszy kod, wczesne zakończenie w przypadku błędów
    
    static class ReplaceNestedConditionalExample {
        
        // PRZED REFAKTORYZACJĄ - głęboko zagnieżdżone warunki
        static class Before {
            static class LoanApproval {
                double calculateLoanAmount(int age, double income, int creditScore, 
                                          boolean hasExistingLoans) {
                    double maxLoan = 0.0;
                    
                    if (age >= 18) {
                        if (age <= 65) {
                            if (income >= 3000) {
                                if (creditScore >= 600) {
                                    if (!hasExistingLoans) {
                                        maxLoan = income * 5;
                                    } else {
                                        if (creditScore >= 700) {
                                            maxLoan = income * 3;
                                        } else {
                                            maxLoan = income * 2;
                                        }
                                    }
                                } else {
                                    maxLoan = income * 1.5;
                                }
                            }
                        }
                    }
                    
                    return maxLoan;
                }
            }
        }
        
        // PO REFAKTORYZACJI - guard clauses
        static class After {
            static class LoanApproval {
                private static final int MIN_AGE = 18;
                private static final int MAX_AGE = 65;
                private static final double MIN_INCOME = 3000.0;
                private static final int MIN_CREDIT_SCORE = 600;
                private static final int GOOD_CREDIT_SCORE = 700;
                
                double calculateLoanAmount(int age, double income, int creditScore, 
                                          boolean hasExistingLoans) {
                    
                    if (age < MIN_AGE || age > MAX_AGE) {
                        return 0.0;
                    }
                    
                    if (income < MIN_INCOME) {
                        return 0.0;
                    }
                    
                    if (creditScore < MIN_CREDIT_SCORE) {
                        return income * 1.5;
                    }
                    
                    if (!hasExistingLoans) {
                        return income * 5;
                    }
                    
                    if (creditScore >= GOOD_CREDIT_SCORE) {
                        return income * 3;
                    }
                    
                    return income * 2;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 14: SEPARATE QUERY FROM MODIFIER
    // ============================================================================
    // Refaktoryzacja: Oddzielenie zapytań od modyfikatorów
    // Uzasadnienie: Metody albo zwracają wartość albo modyfikują stan, nie oba naraz
    //               (Command-Query Separation)
    
    static class SeparateQueryFromModifierExample {
        
        // PRZED REFAKTORYZACJĄ - metoda modyfikuje i zwraca wartość
        static class Before {
            static class ShoppingCart {
                private List<String> items = new ArrayList<>();
                private int totalCheckouts = 0;
                
                // Złamanie CQS - metoda modyfikuje stan i zwraca wartość
                double getTotalAndCheckout(Map<String, Double> prices) {
                    double total = 0.0;
                    for (String item : items) {
                        total += prices.getOrDefault(item, 0.0);
                    }
                    
                    items.clear();
                    totalCheckouts++;
                    
                    return total;
                }
                
                void addItem(String item) {
                    items.add(item);
                }
            }
        }
        
        // PO REFAKTORYZACJI - oddzielne metody do zapytań i modyfikacji
        static class After {
            static class ShoppingCart {
                private List<String> items = new ArrayList<>();
                private int totalCheckouts = 0;
                
                // Query - tylko zwraca wartość
                public double calculateTotal(Map<String, Double> prices) {
                    return items.stream()
                            .mapToDouble(item -> prices.getOrDefault(item, 0.0))
                            .sum();
                }
                
                // Modifier - tylko modyfikuje stan
                public void checkout() {
                    items.clear();
                    totalCheckouts++;
                }
                
                public void addItem(String item) {
                    items.add(item);
                }
                
                // Queries
                public int getItemCount() {
                    return items.size();
                }
                
                public int getTotalCheckouts() {
                    return totalCheckouts;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 15: REPLACE CONSTRUCTOR WITH FACTORY METHOD
    // ============================================================================
    // Refaktoryzacja: Zastąpienie konstruktora metodą fabrykującą
    // Uzasadnienie: Większa elastyczność w tworzeniu obiektów, możliwość zwracania
    //               podklas, cache'owanie, opisowe nazwy metod
    
    static class ReplaceConstructorWithFactoryExample {
        
        // PRZED REFAKTORYZACJĄ - konstruktor z logicznym parametrem
        static class Before {
            static class Employee {
                private String name;
                private String type;
                private double baseSalary;
                
                // Niejasny konstruktor - co oznacza boolean?
                Employee(String name, double baseSalary, boolean isPermanent) {
                    this.name = name;
                    this.baseSalary = baseSalary;
                    this.type = isPermanent ? "Permanent" : "Contract";
                }
                
                Employee(String name, double baseSalary, int contractMonths) {
                    this.name = name;
                    this.baseSalary = baseSalary;
                    this.type = "Contract-" + contractMonths;
                }
                
                String getDescription() {
                    return name + " (" + type + ") - " + baseSalary;
                }
            }
        }
        
        // PO REFAKTORYZACJI - opisowe metody fabrykujące
        static class After {
            static class Employee {
                private String name;
                private String type;
                private double baseSalary;
                
                private Employee(String name, String type, double baseSalary) {
                    this.name = name;
                    this.type = type;
                    this.baseSalary = baseSalary;
                }
                
                // Opisowe metody fabrykujące - cel jest jasny
                static Employee createPermanentEmployee(String name, double baseSalary) {
                    return new Employee(name, "Permanent", baseSalary);
                }
                
                static Employee createContractEmployee(String name, double baseSalary, int months) {
                    return new Employee(name, "Contract-" + months, baseSalary);
                }
                
                static Employee createIntern(String name) {
                    return new Employee(name, "Intern", 0.0);
                }
                
                String getDescription() {
                    return name + " (" + type + ") - " + baseSalary;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 16: ENCAPSULATE COLLECTION
    // ============================================================================
    // Refaktoryzacja: Enkapsulacja kolekcji
    // Uzasadnienie: Zapobiega nieautoryzowanym modyfikacjom, kontrola nad danymi
    
    static class EncapsulateCollectionExample {
        
        // PRZED REFAKTORYZACJĄ - bezpośredni dostęp do kolekcji
        static class Before {
            static class Course {
                private String name;
                private List<String> students;
                
                Course(String name) {
                    this.name = name;
                    this.students = new ArrayList<>();
                }
                
                // Niebezpieczne - zwraca referencję do wewnętrznej kolekcji
                List<String> getStudents() {
                    return students;
                }
                
                String getName() {
                    return name;
                }
            }
        }
        
        // PO REFAKTORYZACJI - kontrolowany dostęp do kolekcji
        static class After {
            static class Course {
                private String name;
                private List<String> students;
                
                Course(String name) {
                    this.name = name;
                    this.students = new ArrayList<>();
                }
                
                // Zwraca kopię - bezpieczne
                public List<String> getStudents() {
                    return new ArrayList<>(students);
                }
                
                // Kontrolowane dodawanie
                public void addStudent(String student) {
                    if (student == null || student.trim().isEmpty()) {
                        throw new IllegalArgumentException("Student name cannot be empty");
                    }
                    if (students.contains(student)) {
                        throw new IllegalArgumentException("Student already enrolled: " + student);
                    }
                    students.add(student);
                }
                
                // Kontrolowane usuwanie
                public void removeStudent(String student) {
                    if (!students.contains(student)) {
                        throw new IllegalArgumentException("Student not found: " + student);
                    }
                    students.remove(student);
                }
                
                public int getStudentCount() {
                    return students.size();
                }
                
                public boolean hasStudent(String student) {
                    return students.contains(student);
                }
                
                public String getName() {
                    return name;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 17: REPLACE DATA VALUE WITH OBJECT
    // ============================================================================
    // Refaktoryzacja: Zastąpienie wartości prostej obiektem
    // Uzasadnienie: Grupuje powiązane dane i zachowania, łatwiejsza walidacja
    
    static class ReplaceDataValueExample {
        
        // PRZED REFAKTORYZACJĄ - proste wartości String
        static class Before {
            static class Customer {
                private String name;
                private String phoneNumber;
                
                Customer(String name, String phoneNumber) {
                    this.name = name;
                    this.phoneNumber = phoneNumber;
                }
                
                String getAreaCode() {
                    return phoneNumber.substring(0, 3);
                }
                
                String formatPhoneNumber() {
                    return "(" + phoneNumber.substring(0, 3) + ") " +
                           phoneNumber.substring(3, 6) + "-" +
                           phoneNumber.substring(6);
                }
                
                boolean isLocalNumber(String areaCode) {
                    return phoneNumber.startsWith(areaCode);
                }
            }
        }
        
        // PO REFAKTORYZACJI - dedykowany obiekt dla numeru telefonu
        static class After {
            static class PhoneNumber {
                private final String areaCode;
                private final String prefix;
                private final String lineNumber;
                
                PhoneNumber(String fullNumber) {
                    if (fullNumber == null || fullNumber.length() != 9) {
                        throw new IllegalArgumentException(
                            "Phone number must be 9 digits");
                    }
                    
                    this.areaCode = fullNumber.substring(0, 3);
                    this.prefix = fullNumber.substring(3, 6);
                    this.lineNumber = fullNumber.substring(6);
                }
                
                PhoneNumber(String areaCode, String prefix, String lineNumber) {
                    validateSegment(areaCode, 3, "Area code");
                    validateSegment(prefix, 3, "Prefix");
                    validateSegment(lineNumber, 3, "Line number");
                    
                    this.areaCode = areaCode;
                    this.prefix = prefix;
                    this.lineNumber = lineNumber;
                }
                
                private void validateSegment(String segment, int length, String name) {
                    if (segment == null || segment.length() != length) {
                        throw new IllegalArgumentException(
                            name + " must be " + length + " digits");
                    }
                    if (!segment.matches("\\d+")) {
                        throw new IllegalArgumentException(
                            name + " must contain only digits");
                    }
                }
                
                public String getAreaCode() {
                    return areaCode;
                }
                
                public String format() {
                    return "(" + areaCode + ") " + prefix + "-" + lineNumber;
                }
                
                public boolean isInArea(String areaCode) {
                    return this.areaCode.equals(areaCode);
                }
                
                public String getRawNumber() {
                    return areaCode + prefix + lineNumber;
                }
                
                @Override
                public String toString() {
                    return format();
                }
            }
            
            static class Customer {
                private String name;
                private PhoneNumber phoneNumber;
                
                Customer(String name, PhoneNumber phoneNumber) {
                    this.name = name;
                    this.phoneNumber = phoneNumber;
                }
                
                public String getName() {
                    return name;
                }
                
                public PhoneNumber getPhoneNumber() {
                    return phoneNumber;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 18: INTRODUCE ASSERTION
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie asercji
    // Uzasadnienie: Dokumentuje założenia, ułatwia debugowanie, fail-fast
    
    static class IntroduceAssertionExample {
        
        // PRZED REFAKTORYZACJĄ - ukryte założenia
        static class Before {
            static class ExpenseReport {
                private double limit;
                
                ExpenseReport(double limit) {
                    this.limit = limit;
                }
                
                double calculateReimbursement(double[] expenses) {
                    double total = 0.0;
                    for (double expense : expenses) {
                        total += expense;
                    }
                    
                    // Ukryte założenie: limit jest zawsze > 0
                    double reimbursement = Math.min(total, limit);
                    
                    return reimbursement;
                }
            }
        }
        
        // PO REFAKTORYZACJI - jawne asercje
        static class After {
            static class ExpenseReport {
                private double limit;
                
                ExpenseReport(double limit) {
                    assert limit > 0 : "Limit must be positive, got: " + limit;
                    this.limit = limit;
                }
                
                double calculateReimbursement(double[] expenses) {
                    assert expenses != null : "Expenses array cannot be null";
                    assert expenses.length > 0 : "Expenses array cannot be empty";
                    
                    double total = 0.0;
                    for (double expense : expenses) {
                        assert expense >= 0 : "Expense cannot be negative: " + expense;
                        total += expense;
                    }
                    
                    double reimbursement = Math.min(total, limit);
                    
                    assert reimbursement >= 0 : "Reimbursement cannot be negative";
                    assert reimbursement <= limit : "Reimbursement cannot exceed limit";
                    
                    return reimbursement;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 19: REPLACE INHERITANCE WITH DELEGATION
    // ============================================================================
    // Refaktoryzacja: Zastąpienie dziedziczenia delegacją
    // Uzasadnienie: Prefer composition over inheritance, większa elastyczność
    
    static class ReplaceInheritanceExample {
        
        // PRZED REFAKTORYZACJĄ - niewłaściwe dziedziczenie
        static class Before {
            static class Stack<T> extends ArrayList<T> {
                void push(T element) {
                    add(element);
                }
                
                T pop() {
                    if (isEmpty()) {
                        throw new IllegalStateException("Stack is empty");
                    }
                    return remove(size() - 1);
                }
                
                T peek() {
                    if (isEmpty()) {
                        throw new IllegalStateException("Stack is empty");
                    }
                    return get(size() - 1);
                }
                
                // Problem: dziedziczymy wszystkie metody ArrayList
                // które mogą złamać semantykę stosu (np. add(index, element))
            }
        }
        
        // PO REFAKTORYZACJI - kompozycja zamiast dziedziczenia
        static class After {
            static class Stack<T> {
                private final List<T> elements;
                
                Stack() {
                    this.elements = new ArrayList<>();
                }
                
                public void push(T element) {
                    elements.add(element);
                }
                
                public T pop() {
                    if (isEmpty()) {
                        throw new IllegalStateException("Stack is empty");
                    }
                    return elements.remove(elements.size() - 1);
                }
                
                public T peek() {
                    if (isEmpty()) {
                        throw new IllegalStateException("Stack is empty");
                    }
                    return elements.get(elements.size() - 1);
                }
                
                public boolean isEmpty() {
                    return elements.isEmpty();
                }
                
                public int size() {
                    return elements.size();
                }
                
                // Kontrolujemy dokładnie, które operacje są dostępne
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 20: CONSOLIDATE DUPLICATE CONDITIONAL FRAGMENTS
    // ============================================================================
    // Refaktoryzacja: Konsolidacja zduplikowanych fragmentów warunkowych
    // Uzasadnienie: Eliminuje duplikację, łatwiejsza konserwacja
    
    static class ConsolidateDuplicateConditionalExample {
        
        // PRZED REFAKTORYZACJĄ - zduplikowany kod w każdej gałęzi
        static class Before {
            static class OrderProcessor {
                void processOrder(boolean isExpressShipping, double amount) {
                    double shippingCost;
                    
                    if (isExpressShipping) {
                        shippingCost = 20.0;
                        System.out.println("Processing express order");
                        double total = amount + shippingCost;
                        System.out.println("Total: " + total);
                        logOrder(total);
                    } else {
                        shippingCost = 5.0;
                        System.out.println("Processing standard order");
                        double total = amount + shippingCost;
                        System.out.println("Total: " + total);
                        logOrder(total);
                    }
                }
                
                private void logOrder(double total) {
                    System.out.println("[LOG] Order processed with total: " + total);
                }
            }
        }
        
        // PO REFAKTORYZACJI - wspólny kod poza warunkiem
        static class After {
            static class OrderProcessor {
                private static final double EXPRESS_SHIPPING = 20.0;
                private static final double STANDARD_SHIPPING = 5.0;
                
                void processOrder(boolean isExpressShipping, double amount) {
                    double shippingCost = calculateShippingCost(isExpressShipping);
                    printOrderType(isExpressShipping);
                    
                    double total = amount + shippingCost;
                    System.out.println("Total: " + total);
                    logOrder(total);
                }
                
                private double calculateShippingCost(boolean isExpressShipping) {
                    return isExpressShipping ? EXPRESS_SHIPPING : STANDARD_SHIPPING;
                }
                
                private void printOrderType(boolean isExpressShipping) {
                    System.out.println("Processing " + 
                        (isExpressShipping ? "express" : "standard") + " order");
                }
                
                private void logOrder(double total) {
                    System.out.println("[LOG] Order processed with total: " + total);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 21: REPLACE ALGORITHM
    // ============================================================================
    // Refaktoryzacja: Zastąpienie algorytmu prostszym
    // Uzasadnienie: Czytelniejszy, bardziej wydajny lub korzystający z bibliotek
    
    static class ReplaceAlgorithmExample {
        
        // PRZED REFAKTORYZACJĄ - skomplikowany własny algorytm
        static class Before {
            static class DataAnalyzer {
                List<String> findUniqueSorted(List<String> items) {
                    List<String> unique = new ArrayList<>();
                    
                    // Ręczne usuwanie duplikatów
                    for (String item : items) {
                        boolean found = false;
                        for (String existing : unique) {
                            if (existing.equals(item)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            unique.add(item);
                        }
                    }
                    
                    // Bubble sort
                    for (int i = 0; i < unique.size() - 1; i++) {
                        for (int j = 0; j < unique.size() - i - 1; j++) {
                            if (unique.get(j).compareTo(unique.get(j + 1)) > 0) {
                                String temp = unique.get(j);
                                unique.set(j, unique.get(j + 1));
                                unique.set(j + 1, temp);
                            }
                        }
                    }
                    
                    return unique;
                }
                
                double calculateAverage(List<Double> numbers) {
                    if (numbers.isEmpty()) {
                        return 0.0;
                    }
                    
                    double sum = 0.0;
                    for (Double number : numbers) {
                        sum += number;
                    }
                    return sum / numbers.size();
                }
            }
        }
        
        // PO REFAKTORYZACJI - użycie Stream API i gotowych rozwiązań
        static class After {
            static class DataAnalyzer {
                List<String> findUniqueSorted(List<String> items) {
                    return items.stream()
                            .distinct()
                            .sorted()
                            .collect(Collectors.toList());
                }
                
                double calculateAverage(List<Double> numbers) {
                    return numbers.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0);
                }
                
                // Dodatkowe metody wykorzystujące Stream API
                List<String> filterAndTransform(List<String> items, String prefix) {
                    return items.stream()
                            .filter(item -> item.startsWith(prefix))
                            .map(String::toUpperCase)
                            .collect(Collectors.toList());
                }
                
                Map<Integer, List<String>> groupByLength(List<String> items) {
                    return items.stream()
                            .collect(Collectors.groupingBy(String::length));
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 22: HIDE DELEGATE
    // ============================================================================
    // Refaktoryzacja: Ukrycie delegata (przeciwieństwo Remove Middle Man)
    // Uzasadnienie: Zmniejsza coupling, enkapsuluje strukturę obiektów
    
    static class HideDelegateExample {
        
        // PRZED REFAKTORYZACJĄ - klient musi znać strukturę obiektów
        static class Before {
            static class Person {
                private Department department;
                
                Person(Department department) {
                    this.department = department;
                }
                
                public Department getDepartment() {
                    return department;
                }
            }
            
            static class Department {
                private String code;
                private Person manager;
                
                Department(String code, Person manager) {
                    this.code = code;
                    this.manager = manager;
                }
                
                public Person getManager() {
                    return manager;
                }
                
                public String getCode() {
                    return code;
                }
            }
            
            // Klient musi znać całą strukturę: person -> department -> manager
            static Person getManager(Person person) {
                return person.getDepartment().getManager();
            }
        }
        
        // PO REFAKTORYZACJI - Person ukrywa delegację
        static class After {
            static class Person {
                private Department department;
                private String name;
                
                Person(String name, Department department) {
                    this.name = name;
                    this.department = department;
                }
                
                // Ukrywamy delegację - klient nie musi znać Department
                public Person getManager() {
                    return department.getManager();
                }
                
                public String getDepartmentCode() {
                    return department.getCode();
                }
                
                public String getName() {
                    return name;
                }
            }
            
            static class Department {
                private String code;
                private Person manager;
                
                Department(String code, Person manager) {
                    this.code = code;
                    this.manager = manager;
                }
                
                Person getManager() {
                    return manager;
                }
                
                String getCode() {
                    return code;
                }
            }
            
            // Klient pracuje tylko z Person
            static Person getManager(Person person) {
                return person.getManager();
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 23: REPLACE METHOD WITH METHOD OBJECT
    // ============================================================================
    // Refaktoryzacja: Zastąpienie metody obiektem metody
    // Uzasadnienie: Rozbija złożoną metodę z wieloma lokalnymi zmiennymi
    
    static class ReplaceMethodWithMethodObjectExample {
        
        // PRZED REFAKTORYZACJĄ - długa metoda z wieloma zmiennymi lokalnymi
        static class Before {
            static class PriceCalculator {
                double calculatePrice(int quantity, int itemPrice, int customerType, 
                                     int discountCode, boolean isSeasonalSale) {
                    // Wiele lokalnych zmiennych utrudnia wydzielanie metod
                    int basePrice = quantity * itemPrice;
                    double primaryDiscount = 0.0;
                    double secondaryDiscount = 0.0;
                    double seasonalDiscount = 0.0;
                    
                    // Skomplikowana logika rabatów
                    if (customerType == 1) {
                        primaryDiscount = basePrice * 0.05;
                    } else if (customerType == 2) {
                        primaryDiscount = basePrice * 0.10;
                    } else if (customerType == 3) {
                        primaryDiscount = basePrice * 0.15;
                    }
                    
                    if (discountCode == 100) {
                        secondaryDiscount = (basePrice - primaryDiscount) * 0.05;
                    } else if (discountCode == 200) {
                        secondaryDiscount = (basePrice - primaryDiscount) * 0.10;
                    }
                    
                    if (isSeasonalSale) {
                        seasonalDiscount = (basePrice - primaryDiscount - secondaryDiscount) * 0.20;
                    }
                    
                    double finalPrice = basePrice - primaryDiscount - secondaryDiscount - seasonalDiscount;
                    
                    // Minimalna cena
                    if (finalPrice < itemPrice) {
                        finalPrice = itemPrice;
                    }
                    
                    return finalPrice;
                }
            }
        }
        
        // PO REFAKTORYZACJI - obiekt metody
        static class After {
            static class PriceCalculation {
                private final int quantity;
                private final int itemPrice;
                private final int customerType;
                private final int discountCode;
                private final boolean isSeasonalSale;
                
                private int basePrice;
                private double primaryDiscount;
                private double secondaryDiscount;
                private double seasonalDiscount;
                
                PriceCalculation(int quantity, int itemPrice, int customerType, 
                               int discountCode, boolean isSeasonalSale) {
                    this.quantity = quantity;
                    this.itemPrice = itemPrice;
                    this.customerType = customerType;
                    this.discountCode = discountCode;
                    this.isSeasonalSale = isSeasonalSale;
                }
                
                double compute() {
                    calculateBasePrice();
                    calculatePrimaryDiscount();
                    calculateSecondaryDiscount();
                    calculateSeasonalDiscount();
                    return calculateFinalPrice();
                }
                
                private void calculateBasePrice() {
                    basePrice = quantity * itemPrice;
                }
                
                private void calculatePrimaryDiscount() {
                    switch (customerType) {
                        case 1: primaryDiscount = basePrice * 0.05; break;
                        case 2: primaryDiscount = basePrice * 0.10; break;
                        case 3: primaryDiscount = basePrice * 0.15; break;
                        default: primaryDiscount = 0.0;
                    }
                }
                
                private void calculateSecondaryDiscount() {
                    double priceAfterPrimary = basePrice - primaryDiscount;
                    
                    if (discountCode == 100) {
                        secondaryDiscount = priceAfterPrimary * 0.05;
                    } else if (discountCode == 200) {
                        secondaryDiscount = priceAfterPrimary * 0.10;
                    } else {
                        secondaryDiscount = 0.0;
                    }
                }
                
                private void calculateSeasonalDiscount() {
                    if (isSeasonalSale) {
                        double priceAfterDiscounts = basePrice - primaryDiscount - secondaryDiscount;
                        seasonalDiscount = priceAfterDiscounts * 0.20;
                    } else {
                        seasonalDiscount = 0.0;
                    }
                }
                
                private double calculateFinalPrice() {
                    double finalPrice = basePrice - primaryDiscount - secondaryDiscount - seasonalDiscount;
                    return Math.max(finalPrice, itemPrice);
                }
            }
            
            static class PriceCalculator {
                double calculatePrice(int quantity, int itemPrice, int customerType, 
                                     int discountCode, boolean isSeasonalSale) {
                    return new PriceCalculation(quantity, itemPrice, customerType, 
                                               discountCode, isSeasonalSale).compute();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 24: PRESERVE WHOLE OBJECT
    // ============================================================================
    // Refaktoryzacja: Przekazywanie całego obiektu zamiast pojedynczych wartości
    // Uzasadnienie: Prostsze API, łatwiej dodać nowe dane bez zmiany sygnatur
    
    static class PreserveWholeObjectExample {
        
        // PRZED REFAKTORYZACJĄ - przekazywanie wielu wartości
        static class Before {
            static class Temperature {
                private double min;
                private double max;
                
                Temperature(double min, double max) {
                    this.min = min;
                    this.max = max;
                }
                
                public double getMin() { return min; }
                public double getMax() { return max; }
            }
            
            static class HeatingPlan {
                private double temperatureRange;
                
                boolean withinRange(double min, double max) {
                    return min >= -10 && max <= 30;
                }
                
                String getRecommendation(double min, double max) {
                    if (max > 25) {
                        return "Włącz klimatyzację";
                    } else if (min < 15) {
                        return "Włącz ogrzewanie";
                    }
                    return "Temperatura optymalna";
                }
            }
        }
        
        // PO REFAKTORYZACJI - przekazywanie całego obiektu
        static class After {
            static class Temperature {
                private double min;
                private double max;
                private LocalDate date;
                private String location;
                
                Temperature(double min, double max, LocalDate date, String location) {
                    this.min = min;
                    this.max = max;
                    this.date = date;
                    this.location = location;
                }
                
                public double getMin() { return min; }
                public double getMax() { return max; }
                public LocalDate getDate() { return date; }
                public String getLocation() { return location; }
                
                public double getAverage() {
                    return (min + max) / 2.0;
                }
            }
            
            static class HeatingPlan {
                boolean withinRange(Temperature temperature) {
                    return temperature.getMin() >= -10 && temperature.getMax() <= 30;
                }
                
                String getRecommendation(Temperature temperature) {
                    if (temperature.getMax() > 25) {
                        return "Włącz klimatyzację w " + temperature.getLocation();
                    } else if (temperature.getMin() < 15) {
                        return "Włącz ogrzewanie w " + temperature.getLocation();
                    }
                    return "Temperatura optymalna";
                }
                
                // Łatwo dodać nowe metody wykorzystujące dodatkowe dane
                String getDetailedReport(Temperature temperature) {
                    return String.format("Raport dla %s (%s): min=%.1f°C, max=%.1f°C, śr=%.1f°C",
                        temperature.getLocation(),
                        temperature.getDate(),
                        temperature.getMin(),
                        temperature.getMax(),
                        temperature.getAverage());
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 25: REPLACE ARRAY WITH OBJECT
    // ============================================================================
    // Refaktoryzacja: Zastąpienie tablicy obiektem
    // Uzasadnienie: Typ bezpieczny, samoopisujący się kod, łatwiejsza walidacja
    
    static class ReplaceArrayWithObjectExample {
        
        // PRZED REFAKTORYZACJĄ - tablica z "magicznymi" indeksami
        static class Before {
            static class PerformanceTracker {
                // [0] = name, [1] = wins, [2] = losses
                String[] getPlayerStats(String name, int wins, int losses) {
                    return new String[] { name, String.valueOf(wins), String.valueOf(losses) };
                }
                
                void printStats(String[] stats) {
                    System.out.println("Gracz: " + stats[0]);
                    System.out.println("Wygrane: " + stats[1]);
                    System.out.println("Przegrane: " + stats[2]);
                    // Łatwo pomylić indeksy!
                }
                
                double calculateWinRate(String[] stats) {
                    int wins = Integer.parseInt(stats[1]);
                    int losses = Integer.parseInt(stats[2]);
                    int total = wins + losses;
                    return total > 0 ? (double) wins / total * 100 : 0.0;
                }
            }
        }
        
        // PO REFAKTORYZACJI - dedykowany obiekt
        static class After {
            static class PlayerStats {
                private final String name;
                private final int wins;
                private final int losses;
                
                PlayerStats(String name, int wins, int losses) {
                    if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    if (wins < 0 || losses < 0) {
                        throw new IllegalArgumentException("Stats cannot be negative");
                    }
                    
                    this.name = name;
                    this.wins = wins;
                    this.losses = losses;
                }
                
                public String getName() { return name; }
                public int getWins() { return wins; }
                public int getLosses() { return losses; }
                
                public int getTotalGames() {
                    return wins + losses;
                }
                
                public double getWinRate() {
                    int total = getTotalGames();
                    return total > 0 ? (double) wins / total * 100 : 0.0;
                }
                
                public String getRating() {
                    double winRate = getWinRate();
                    if (winRate >= 70) return "Ekspert";
                    if (winRate >= 50) return "Zaawansowany";
                    if (winRate >= 30) return "Średniozaawansowany";
                    return "Początkujący";
                }
                
                @Override
                public String toString() {
                    return String.format("%s: %d-%d (%.1f%%)", 
                        name, wins, losses, getWinRate());
                }
            }
            
            static class PerformanceTracker {
                void printStats(PlayerStats stats) {
                    System.out.println("Gracz: " + stats.getName());
                    System.out.println("Wygrane: " + stats.getWins());
                    System.out.println("Przegrane: " + stats.getLosses());
                    System.out.println("Współczynnik: " + stats.getWinRate() + "%");
                    System.out.println("Ocena: " + stats.getRating());
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 26: INTRODUCE FOREIGN METHOD
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie metody obcej (gdy nie możemy modyfikować klasy)
    // Uzasadnienie: Dodanie funkcjonalności bez modyfikacji istniejącej klasy
    
    static class IntroduceForeignMethodExample {
        
        // PRZED REFAKTORYZACJĄ - rozproszony kod w wielu miejscach
        static class Before {
            static class ReportGenerator {
                void generateReport() {
                    LocalDate date = LocalDate.now();
                    // Duplikacja: chcemy następny dzień roboczy
                    LocalDate nextDate = date.plusDays(1);
                    while (nextDate.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                           nextDate.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
                        nextDate = nextDate.plusDays(1);
                    }
                    System.out.println("Następny raport: " + nextDate);
                }
                
                void scheduleFollowUp() {
                    LocalDate date = LocalDate.now();
                    // Ta sama logika ponownie!
                    LocalDate nextDate = date.plusDays(1);
                    while (nextDate.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                           nextDate.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
                        nextDate = nextDate.plusDays(1);
                    }
                    System.out.println("Następne spotkanie: " + nextDate);
                }
            }
        }
        
        // PO REFAKTORYZACJI - metoda obca w naszej klasie
        static class After {
            static class DateUtils {
                // "Foreign method" - dodajemy funkcjonalność do LocalDate
                static LocalDate nextWorkingDay(LocalDate date) {
                    LocalDate result = date.plusDays(1);
                    while (result.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                           result.getDayOfWeek() == java.time.DayOfWeek.SUNDAY) {
                        result = result.plusDays(1);
                    }
                    return result;
                }
                
                static LocalDate addWorkingDays(LocalDate date, int days) {
                    LocalDate result = date;
                    int addedDays = 0;
                    while (addedDays < days) {
                        result = result.plusDays(1);
                        if (result.getDayOfWeek() != java.time.DayOfWeek.SATURDAY &&
                            result.getDayOfWeek() != java.time.DayOfWeek.SUNDAY) {
                            addedDays++;
                        }
                    }
                    return result;
                }
                
                static boolean isWorkingDay(LocalDate date) {
                    return date.getDayOfWeek() != java.time.DayOfWeek.SATURDAY &&
                           date.getDayOfWeek() != java.time.DayOfWeek.SUNDAY;
                }
            }
            
            static class ReportGenerator {
                void generateReport() {
                    LocalDate nextDate = DateUtils.nextWorkingDay(LocalDate.now());
                    System.out.println("Następny raport: " + nextDate);
                }
                
                void scheduleFollowUp() {
                    LocalDate nextDate = DateUtils.nextWorkingDay(LocalDate.now());
                    System.out.println("Następne spotkanie: " + nextDate);
                }
                
                void scheduleReviewIn5Days() {
                    LocalDate reviewDate = DateUtils.addWorkingDays(LocalDate.now(), 5);
                    System.out.println("Przegląd za 5 dni roboczych: " + reviewDate);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 27: FORM TEMPLATE METHOD
    // ============================================================================
    // Refaktoryzacja: Utworzenie metody szablonowej
    // Uzasadnienie: Wzorzec Template Method - wspólny algorytm, różne szczegóły
    
    static class FormTemplateMethodExample {
        
        // PRZED REFAKTORYZACJĄ - podobny kod w różnych klasach
        static class Before {
            static class HtmlReport {
                void generate(String title, List<String> data) {
                    System.out.println("<html>");
                    System.out.println("<head><title>" + title + "</title></head>");
                    System.out.println("<body>");
                    System.out.println("<h1>" + title + "</h1>");
                    for (String item : data) {
                        System.out.println("<p>" + item + "</p>");
                    }
                    System.out.println("</body>");
                    System.out.println("</html>");
                }
            }
            
            static class CsvReport {
                void generate(String title, List<String> data) {
                    System.out.println("\"" + title + "\"");
                    System.out.println("\"Generated: " + LocalDate.now() + "\"");
                    System.out.println("---");
                    for (String item : data) {
                        System.out.println("\"" + item + "\"");
                    }
                    System.out.println("---");
                    System.out.println("\"End of report\"");
                }
            }
        }
        
        // PO REFAKTORYZACJI - wzorzec Template Method
        static class After {
            static abstract class Report {
                // Metoda szablonowa - definiuje algorytm
                public final void generate(String title, List<String> data) {
                    printHeader();
                    printTitle(title);
                    printBody(data);
                    printFooter();
                }
                
                // Metody do nadpisania w podklasach
                protected abstract void printHeader();
                protected abstract void printTitle(String title);
                protected abstract void printBody(List<String> data);
                protected abstract void printFooter();
                
                // Hook method - może być nadpisana, ale nie musi
                protected void printMetadata() {
                    System.out.println("Generated: " + LocalDate.now());
                }
            }
            
            static class HtmlReport extends Report {
                @Override
                protected void printHeader() {
                    System.out.println("<html>");
                    System.out.println("<head></head>");
                    System.out.println("<body>");
                }
                
                @Override
                protected void printTitle(String title) {
                    System.out.println("<h1>" + title + "</h1>");
                }
                
                @Override
                protected void printBody(List<String> data) {
                    for (String item : data) {
                        System.out.println("<p>" + item + "</p>");
                    }
                }
                
                @Override
                protected void printFooter() {
                    System.out.println("</body>");
                    System.out.println("</html>");
                }
            }
            
            static class CsvReport extends Report {
                @Override
                protected void printHeader() {
                    printMetadata();
                    System.out.println("---");
                }
                
                @Override
                protected void printTitle(String title) {
                    System.out.println("\"" + title + "\"");
                }
                
                @Override
                protected void printBody(List<String> data) {
                    for (String item : data) {
                        System.out.println("\"" + item + "\"");
                    }
                }
                
                @Override
                protected void printFooter() {
                    System.out.println("---");
                    System.out.println("\"End of report\"");
                }
            }
            
            static class JsonReport extends Report {
                @Override
                protected void printHeader() {
                    System.out.println("{");
                }
                
                @Override
                protected void printTitle(String title) {
                    System.out.println("  \"title\": \"" + title + "\",");
                }
                
                @Override
                protected void printBody(List<String> data) {
                    System.out.println("  \"data\": [");
                    for (int i = 0; i < data.size(); i++) {
                        System.out.print("    \"" + data.get(i) + "\"");
                        if (i < data.size() - 1) System.out.println(",");
                        else System.out.println();
                    }
                    System.out.println("  ]");
                }
                
                @Override
                protected void printFooter() {
                    System.out.println("}");
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 28: REPLACE PARAMETER WITH METHOD CALL
    // ============================================================================
    // Refaktoryzacja: Zastąpienie parametru wywołaniem metody
    // Uzasadnienie: Upraszcza sygnaturę metody, eliminuje niepotrzebne przekazywanie
    
    static class ReplaceParameterWithMethodCallExample {
        
        // PRZED REFAKTORYZACJĄ - niepotrzebne parametry
        static class Before {
            static class Order {
                private int quantity;
                private double itemPrice;
                
                Order(int quantity, double itemPrice) {
                    this.quantity = quantity;
                    this.itemPrice = itemPrice;
                }
                
                double getPrice() {
                    int basePrice = quantity * (int)itemPrice;
                    int discountLevel = getDiscountLevel();
                    // Przekazujemy basePrice i discountLevel niepotrzebnie
                    return discountedPrice(basePrice, discountLevel);
                }
                
                private int getDiscountLevel() {
                    return quantity > 100 ? 2 : 1;
                }
                
                // Niepotrzebne parametry - moglibyśmy je obliczyć wewnątrz
                private double discountedPrice(int basePrice, int discountLevel) {
                    if (discountLevel == 2) {
                        return basePrice * 0.90;
                    }
                    return basePrice * 0.95;
                }
            }
        }
        
        // PO REFAKTORYZACJI - metoda sama oblicza potrzebne wartości
        static class After {
            static class Order {
                private int quantity;
                private double itemPrice;
                
                Order(int quantity, double itemPrice) {
                    this.quantity = quantity;
                    this.itemPrice = itemPrice;
                }
                
                double getPrice() {
                    return discountedPrice();
                }
                
                private int getBasePrice() {
                    return quantity * (int)itemPrice;
                }
                
                private int getDiscountLevel() {
                    return quantity > 100 ? 2 : 1;
                }
                
                // Metoda sama oblicza potrzebne wartości
                private double discountedPrice() {
                    int basePrice = getBasePrice();
                    int discountLevel = getDiscountLevel();
                    
                    if (discountLevel == 2) {
                        return basePrice * 0.90;
                    }
                    return basePrice * 0.95;
                }
                
                // Dodatkowe metody mogą łatwo korzystać z tych samych obliczeń
                String getPriceBreakdown() {
                    return String.format("Cena bazowa: %d, Poziom rabatu: %d, Cena końcowa: %.2f",
                        getBasePrice(), getDiscountLevel(), discountedPrice());
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 29: PARAMETERIZE METHOD
    // ============================================================================
    // Refaktoryzacja: Sparametryzowanie metody
    // Uzasadnienie: Eliminuje duplikację podobnych metod przez dodanie parametru
    
    static class ParameterizeMethodExample {
        
        // PRZED REFAKTORYZACJĄ - wiele podobnych metod
        static class Before {
            static class Employee {
                private double baseSalary;
                
                Employee(double baseSalary) {
                    this.baseSalary = baseSalary;
                }
                
                void tenPercentRaise() {
                    baseSalary *= 1.10;
                }
                
                void fivePercentRaise() {
                    baseSalary *= 1.05;
                }
                
                void fifteenPercentRaise() {
                    baseSalary *= 1.15;
                }
                
                double getBaseSalary() {
                    return baseSalary;
                }
            }
            
            static class Product {
                private double basePrice = 100.0;
                
                double priceWithFivePercentDiscount() {
                    return basePrice * 0.95;
                }
                
                double priceWithTenPercentDiscount() {
                    return basePrice * 0.90;
                }
                
                double priceWithTwentyPercentDiscount() {
                    return basePrice * 0.80;
                }
            }
        }
        
        // PO REFAKTORYZACJI - jedna sparametryzowana metoda
        static class After {
            static class Employee {
                private double baseSalary;
                
                Employee(double baseSalary) {
                    this.baseSalary = baseSalary;
                }
                
                void raise(double percentage) {
                    if (percentage < 0 || percentage > 50) {
                        throw new IllegalArgumentException(
                            "Raise percentage must be between 0 and 50");
                    }
                    baseSalary *= (1 + percentage / 100);
                }
                
                // Convenience methods dla popularnych wartości
                void standardRaise() {
                    raise(5.0);
                }
                
                void meritRaise() {
                    raise(10.0);
                }
                
                void exceptionalRaise() {
                    raise(15.0);
                }
                
                double getBaseSalary() {
                    return baseSalary;
                }
            }
            
            static class Product {
                private double basePrice = 100.0;
                
                double priceWithDiscount(double discountPercentage) {
                    if (discountPercentage < 0 || discountPercentage > 100) {
                        throw new IllegalArgumentException(
                            "Discount must be between 0 and 100");
                    }
                    return basePrice * (1 - discountPercentage / 100);
                }
                
                // Convenience methods
                double regularPrice() {
                    return priceWithDiscount(0);
                }
                
                double salePrice() {
                    return priceWithDiscount(10);
                }
                
                double clearancePrice() {
                    return priceWithDiscount(20);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 30: REPLACE EXCEPTION WITH TEST
    // ============================================================================
    // Refaktoryzacja: Zastąpienie wyjątku testem
    // Uzasadnienie: Wyjątki tylko do wyjątkowych sytuacji, testy do przepływu kontroli
    
    static class ReplaceExceptionWithTestExample {
        
        // PRZED REFAKTORYZACJĄ - używanie wyjątków do przepływu kontroli
        static class Before {
            static class ResourcePool {
                private Stack<String> available = new Stack<>();
                
                ResourcePool() {
                    available.push("Resource1");
                    available.push("Resource2");
                    available.push("Resource3");
                }
                
                String getResource() {
                    try {
                        return available.pop();
                    } catch (EmptyStackException e) {
                        return createNewResource();
                    }
                }
                
                private String createNewResource() {
                    return "NewResource-" + System.currentTimeMillis();
                }
                
                void returnResource(String resource) {
                    available.push(resource);
                }
            }
            
            static class BankAccount {
                private double balance;
                
                BankAccount(double balance) {
                    this.balance = balance;
                }
                
                void withdraw(double amount) throws Exception {
                    if (balance < amount) {
                        throw new Exception("Insufficient funds");
                    }
                    balance -= amount;
                }
            }
        }
        
        // PO REFAKTORYZACJI - testy zamiast wyjątków w normalnym przepływie
        static class After {
            static class ResourcePool {
                private Stack<String> available = new Stack<>();
                
                ResourcePool() {
                    available.push("Resource1");
                    available.push("Resource2");
                    available.push("Resource3");
                }
                
                String getResource() {
                    if (available.isEmpty()) {
                        return createNewResource();
                    }
                    return available.pop();
                }
                
                boolean hasAvailableResources() {
                    return !available.isEmpty();
                }
                
                int getAvailableCount() {
                    return available.size();
                }
                
                private String createNewResource() {
                    return "NewResource-" + System.currentTimeMillis();
                }
                
                void returnResource(String resource) {
                    available.push(resource);
                }
            }
            
            static class BankAccount {
                private double balance;
                
                BankAccount(double balance) {
                    this.balance = balance;
                }
                
                // Test method - pozwala sprawdzić przed wywołaniem
                boolean canWithdraw(double amount) {
                    return balance >= amount;
                }
                
                // Wyjątek tylko gdy naruszono kontrakt (nie sprawdzono przed)
                void withdraw(double amount) {
                    if (!canWithdraw(amount)) {
                        throw new IllegalStateException(
                            "Insufficient funds. Balance: " + balance + ", requested: " + amount);
                    }
                    balance -= amount;
                }
                
                // Bezpieczna wersja zwracająca status
                boolean tryWithdraw(double amount) {
                    if (canWithdraw(amount)) {
                        balance -= amount;
                        return true;
                    }
                    return false;
                }
                
                double getBalance() {
                    return balance;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 31: INTRODUCE GATEWAY
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie bramy (Gateway/Adapter)
    // Uzasadnienie: Izoluje kod od zewnętrznych zależności, ułatwia testowanie
    
    static class IntroduceGatewayExample {
        
        // PRZED REFAKTORYZACJĄ - bezpośrednie wywołania zewnętrznych serwisów
        static class Before {
            static class OrderService {
                void processOrder(String orderId, double amount, String email) {
                    // Bezpośrednie wywołanie zewnętrznego API płatności
                    System.out.println("Calling external payment API...");
                    boolean paymentSuccess = callPaymentApi(amount);
                    
                    if (paymentSuccess) {
                        // Bezpośrednie wywołanie zewnętrznego serwisu email
                        System.out.println("Sending email to: " + email);
                        sendEmailDirectly(email, "Order confirmed: " + orderId);
                        
                        // Bezpośredni zapis do bazy danych
                        System.out.println("Saving to database...");
                        saveToDatabaseDirectly(orderId, "COMPLETED");
                    }
                }
                
                private boolean callPaymentApi(double amount) {
                    // Symulacja zewnętrznego API
                    return amount > 0;
                }
                
                private void sendEmailDirectly(String to, String message) {
                    // Bezpośrednie wywołanie SMTP
                    System.out.println("SMTP: " + message);
                }
                
                private void saveToDatabaseDirectly(String orderId, String status) {
                    // Bezpośrednie wywołanie JDBC
                    System.out.println("INSERT INTO orders...");
                }
            }
        }
        
        // PO REFAKTORYZACJI - bramy izolujące zewnętrzne zależności
        static class After {
            // Gateway dla płatności
            interface PaymentGateway {
                boolean processPayment(double amount);
            }
            
            static class ExternalPaymentGateway implements PaymentGateway {
                @Override
                public boolean processPayment(double amount) {
                    System.out.println("Calling external payment API...");
                    return amount > 0;
                }
            }
            
            // Gateway dla email
            interface EmailGateway {
                void sendEmail(String to, String subject, String body);
            }
            
            static class SmtpEmailGateway implements EmailGateway {
                @Override
                public void sendEmail(String to, String subject, String body) {
                    System.out.println("Sending email to: " + to);
                    System.out.println("Subject: " + subject);
                }
            }
            
            // Gateway dla bazy danych
            interface OrderRepository {
                void save(String orderId, String status);
                String findStatus(String orderId);
            }
            
            static class DatabaseOrderRepository implements OrderRepository {
                @Override
                public void save(String orderId, String status) {
                    System.out.println("Saving to database...");
                    System.out.println("INSERT INTO orders VALUES('" + orderId + "', '" + status + "')");
                }
                
                @Override
                public String findStatus(String orderId) {
                    return "COMPLETED";
                }
            }
            
            // Serwis używa bram - łatwe do testowania i podmiany
            static class OrderService {
                private final PaymentGateway paymentGateway;
                private final EmailGateway emailGateway;
                private final OrderRepository orderRepository;
                
                OrderService(PaymentGateway paymentGateway, 
                           EmailGateway emailGateway,
                           OrderRepository orderRepository) {
                    this.paymentGateway = paymentGateway;
                    this.emailGateway = emailGateway;
                    this.orderRepository = orderRepository;
                }
                
                void processOrder(String orderId, double amount, String email) {
                    boolean paymentSuccess = paymentGateway.processPayment(amount);
                    
                    if (paymentSuccess) {
                        emailGateway.sendEmail(email, 
                            "Order Confirmation", 
                            "Order confirmed: " + orderId);
                        orderRepository.save(orderId, "COMPLETED");
                    }
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 32: EXTRACT INTERFACE
    // ============================================================================
    // Refaktoryzacja: Wydzielenie interfejsu
    // Uzasadnienie: Definiuje kontrakt, umożliwia różne implementacje, DIP
    
    static class ExtractInterfaceExample {
        
        // PRZED REFAKTORYZACJĄ - konkretne klasy bez abstrakcji
        static class Before {
            static class EmailNotificationService {
                void sendNotification(String recipient, String message) {
                    System.out.println("Email to " + recipient + ": " + message);
                }
                
                void sendBulkNotifications(List<String> recipients, String message) {
                    for (String recipient : recipients) {
                        sendNotification(recipient, message);
                    }
                }
            }
            
            static class UserService {
                private EmailNotificationService notificationService;
                
                UserService(EmailNotificationService notificationService) {
                    this.notificationService = notificationService;
                }
                
                void registerUser(String email, String name) {
                    System.out.println("Registering user: " + name);
                    notificationService.sendNotification(email, 
                        "Welcome " + name + "!");
                }
            }
        }
        
        // PO REFAKTORYZACJI - interfejs definiuje kontrakt
        static class After {
            // Interfejs definiuje kontrakt
            interface NotificationService {
                void sendNotification(String recipient, String message);
                void sendBulkNotifications(List<String> recipients, String message);
            }
            
            // Różne implementacje
            static class EmailNotificationService implements NotificationService {
                @Override
                public void sendNotification(String recipient, String message) {
                    System.out.println("Email to " + recipient + ": " + message);
                }
                
                @Override
                public void sendBulkNotifications(List<String> recipients, String message) {
                    for (String recipient : recipients) {
                        sendNotification(recipient, message);
                    }
                }
            }
            
            static class SmsNotificationService implements NotificationService {
                @Override
                public void sendNotification(String recipient, String message) {
                    System.out.println("SMS to " + recipient + ": " + message);
                }
                
                @Override
                public void sendBulkNotifications(List<String> recipients, String message) {
                    for (String recipient : recipients) {
                        sendNotification(recipient, message);
                    }
                }
            }
            
            static class PushNotificationService implements NotificationService {
                @Override
                public void sendNotification(String recipient, String message) {
                    System.out.println("Push notification to " + recipient + ": " + message);
                }
                
                @Override
                public void sendBulkNotifications(List<String> recipients, String message) {
                    for (String recipient : recipients) {
                        sendNotification(recipient, message);
                    }
                }
            }
            
            // Serwis zależy od abstrakcji, nie od konkretnej implementacji
            static class UserService {
                private NotificationService notificationService;
                
                UserService(NotificationService notificationService) {
                    this.notificationService = notificationService;
                }
                
                void registerUser(String identifier, String name) {
                    System.out.println("Registering user: " + name);
                    notificationService.sendNotification(identifier, 
                        "Welcome " + name + "!");
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 33: COLLAPSE HIERARCHY
    // ============================================================================
    // Refaktoryzacja: Zwinięcie hierarchii
    // Uzasadnienie: Gdy podklasa nie wnosi wartości, lepiej ją usunąć
    
    static class CollapseHierarchyExample {
        
        // PRZED REFAKTORYZACJĄ - niepotrzebna hierarchia
        static class Before {
            static abstract class Animal {
                protected String name;
                
                Animal(String name) {
                    this.name = name;
                }
                
                abstract void makeSound();
                
                String getName() {
                    return name;
                }
            }
            
            // Podklasa praktycznie nic nie wnosi
            static class Dog extends Animal {
                Dog(String name) {
                    super(name);
                }
                
                @Override
                void makeSound() {
                    System.out.println(name + " says: Woof!");
                }
            }
        }
        
        // PO REFAKTORYZACJI - jedna klasa zamiast hierarchii
        static class After {
            static class Animal {
                private String name;
                private String soundType;
                
                Animal(String name, String soundType) {
                    this.name = name;
                    this.soundType = soundType;
                }
                
                void makeSound() {
                    System.out.println(name + " says: " + soundType);
                }
                
                String getName() {
                    return name;
                }
                
                // Factory methods dla wygody
                static Animal createDog(String name) {
                    return new Animal(name, "Woof!");
                }
                
                static Animal createCat(String name) {
                    return new Animal(name, "Meow!");
                }
                
                static Animal createBird(String name) {
                    return new Animal(name, "Tweet!");
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 34: INLINE CLASS
    // ============================================================================
    // Refaktoryzacja: Wciągnięcie klasy (przeciwieństwo Extract Class)
    // Uzasadnienie: Gdy klasa robi zbyt mało, lepiej ją scalić z inną
    
    static class InlineClassExample {
        
        // PRZED REFAKTORYZACJĄ - klasa robi zbyt mało
        static class Before {
            static class TelephoneNumber {
                private String areaCode;
                private String number;
                
                TelephoneNumber(String areaCode, String number) {
                    this.areaCode = areaCode;
                    this.number = number;
                }
                
                String getAreaCode() { return areaCode; }
                String getNumber() { return number; }
                
                String getTelephoneNumber() {
                    return "(" + areaCode + ") " + number;
                }
            }
            
            static class Person {
                private String name;
                private TelephoneNumber officeTelephone;
                
                Person(String name, TelephoneNumber officeTelephone) {
                    this.name = name;
                    this.officeTelephone = officeTelephone;
                }
                
                String getName() { return name; }
                String getTelephoneNumber() { 
                    return officeTelephone.getTelephoneNumber(); 
                }
                String getAreaCode() { 
                    return officeTelephone.getAreaCode(); 
                }
            }
        }
        
        // PO REFAKTORYZACJI - TelephoneNumber scalony z Person
        static class After {
            static class Person {
                private String name;
                private String officeAreaCode;
                private String officeNumber;
                
                Person(String name, String officeAreaCode, String officeNumber) {
                    this.name = name;
                    this.officeAreaCode = officeAreaCode;
                    this.officeNumber = officeNumber;
                }
                
                String getName() { 
                    return name; 
                }
                
                String getTelephoneNumber() {
                    return "(" + officeAreaCode + ") " + officeNumber;
                }
                
                String getAreaCode() { 
                    return officeAreaCode; 
                }
                
                String getOfficeNumber() {
                    return officeNumber;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 35: REPLACE RECORD WITH DATA CLASS
    // ============================================================================
    // Refaktoryzacja: Zastąpienie struktury danych klasą
    // Uzasadnienie: Enkapsulacja, walidacja, dodanie zachowań
    
    static class ReplaceRecordWithDataClassExample {
        
        // PRZED REFAKTORYZACJĄ - struktura danych (Map/Array)
        static class Before {
            static class EmployeeRegistry {
                private List<Map<String, Object>> employees = new ArrayList<>();
                
                void addEmployee(String id, String name, int age, double salary) {
                    Map<String, Object> employee = new HashMap<>();
                    employee.put("id", id);
                    employee.put("name", name);
                    employee.put("age", age);
                    employee.put("salary", salary);
                    employees.add(employee);
                }
                
                void printEmployee(int index) {
                    Map<String, Object> emp = employees.get(index);
                    System.out.println("ID: " + emp.get("id"));
                    System.out.println("Name: " + emp.get("name"));
                    System.out.println("Age: " + emp.get("age"));
                    System.out.println("Salary: " + emp.get("salary"));
                }
                
                double calculateTotalSalary() {
                    double total = 0.0;
                    for (Map<String, Object> emp : employees) {
                        total += (Double) emp.get("salary");
                    }
                    return total;
                }
            }
        }
        
        // PO REFAKTORYZACJI - właściwa klasa danych
        static class After {
            static class Employee {
                private final String id;
                private String name;
                private int age;
                private double salary;
                
                Employee(String id, String name, int age, double salary) {
                    if (id == null || id.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID cannot be empty");
                    }
                    if (age < 18 || age > 100) {
                        throw new IllegalArgumentException("Invalid age: " + age);
                    }
                    if (salary < 0) {
                        throw new IllegalArgumentException("Salary cannot be negative");
                    }
                    
                    this.id = id;
                    this.name = name;
                    this.age = age;
                    this.salary = salary;
                }
                
                public String getId() { return id; }
                public String getName() { return name; }
                public int getAge() { return age; }
                public double getSalary() { return salary; }
                
                public void setName(String name) {
                    if (name == null || name.trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    this.name = name;
                }
                
                public void setSalary(double salary) {
                    if (salary < 0) {
                        throw new IllegalArgumentException("Salary cannot be negative");
                    }
                    this.salary = salary;
                }
                
                public void giveRaise(double percentage) {
                    this.salary *= (1 + percentage / 100);
                }
                
                public boolean isEligibleForBonus() {
                    return salary > 50000;
                }
                
                @Override
                public String toString() {
                    return String.format("Employee[id=%s, name=%s, age=%d, salary=%.2f]",
                        id, name, age, salary);
                }
            }
            
            static class EmployeeRegistry {
                private List<Employee> employees = new ArrayList<>();
                
                void addEmployee(Employee employee) {
                    employees.add(employee);
                }
                
                void printEmployee(int index) {
                    Employee emp = employees.get(index);
                    System.out.println(emp.toString());
                }
                
                double calculateTotalSalary() {
                    return employees.stream()
                            .mapToDouble(Employee::getSalary)
                            .sum();
                }
                
                List<Employee> getHighEarners(double threshold) {
                    return employees.stream()
                            .filter(emp -> emp.getSalary() > threshold)
                            .collect(Collectors.toList());
                }
                
                int getEmployeeCount() {
                    return employees.size();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 36: INTRODUCE EXPLAINING VARIABLE
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie zmiennej wyjaśniającej
    // Uzasadnienie: Czyni skomplikowane wyrażenia zrozumiałymi
    
    static class IntroduceExplainingVariableExample {
        
        // PRZED REFAKTORYZACJĄ - skomplikowane wyrażenia
        static class Before {
            static class OrderCalculator {
                double calculateTotal(int quantity, double itemPrice, String platform, boolean isPremiumCustomer) {
                    return (quantity * itemPrice) - 
                           ((quantity * itemPrice) * 
                            (platform.equals("mobile") && isPremiumCustomer ? 0.15 : 
                             platform.equals("mobile") ? 0.10 : 
                             isPremiumCustomer ? 0.10 : 0.05)) +
                           (quantity * itemPrice > 1000 ? 50 : 100);
                }
                
                boolean shouldApplyExpressShipping(int quantity, double itemPrice, String customerType, int distance) {
                    return (quantity * itemPrice > 500 && customerType.equals("premium") && distance < 100) ||
                           (quantity * itemPrice > 1000 && distance < 50);
                }
            }
        }
        
        // PO REFAKTORYZACJI - zmienne wyjaśniające
        static class After {
            static class OrderCalculator {
                double calculateTotal(int quantity, double itemPrice, String platform, boolean isPremiumCustomer) {
                    double basePrice = quantity * itemPrice;
                    double discountRate = calculateDiscountRate(platform, isPremiumCustomer);
                    double discount = basePrice * discountRate;
                    double shippingCost = calculateShippingCost(basePrice);
                    
                    return basePrice - discount + shippingCost;
                }
                
                private double calculateDiscountRate(String platform, boolean isPremiumCustomer) {
                    boolean isMobilePlatform = platform.equals("mobile");
                    
                    if (isMobilePlatform && isPremiumCustomer) {
                        return 0.15;
                    } else if (isMobilePlatform) {
                        return 0.10;
                    } else if (isPremiumCustomer) {
                        return 0.10;
                    }
                    return 0.05;
                }
                
                private double calculateShippingCost(double basePrice) {
                    boolean isLargeOrder = basePrice > 1000;
                    return isLargeOrder ? 50 : 100;
                }
                
                boolean shouldApplyExpressShipping(int quantity, double itemPrice, String customerType, int distance) {
                    double orderValue = quantity * itemPrice;
                    boolean isPremiumCustomer = customerType.equals("premium");
                    boolean isShortDistance = distance < 100;
                    boolean isVeryShortDistance = distance < 50;
                    
                    boolean premiumExpressEligible = orderValue > 500 && isPremiumCustomer && isShortDistance;
                    boolean highValueExpressEligible = orderValue > 1000 && isVeryShortDistance;
                    
                    return premiumExpressEligible || highValueExpressEligible;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 37: SPLIT TEMPORARY VARIABLE
    // ============================================================================
    // Refaktoryzacja: Podział zmiennej tymczasowej
    // Uzasadnienie: Każda zmienna powinna mieć jedno zastosowanie
    
    static class SplitTemporaryVariableExample {
        
        // PRZED REFAKTORYZACJĄ - zmienna używana do różnych celów
        static class Before {
            static class GeometryCalculator {
                double calculateArea(double width, double height) {
                    double temp = width * height;  // Pole prostokąta
                    System.out.println("Pole prostokąta: " + temp);
                    
                    temp = 2 * (width + height);   // Obwód (ta sama zmienna!)
                    System.out.println("Obwód: " + temp);
                    
                    temp = Math.sqrt(width * width + height * height);  // Przekątna
                    System.out.println("Przekątna: " + temp);
                    
                    return temp;  // Zwraca przekątną, ale niejasne
                }
            }
            
            static class PhysicsCalculator {
                double calculate(double mass, double velocity, double time) {
                    double result = mass * velocity;  // Pęd
                    System.out.println("Pęd: " + result);
                    
                    result = 0.5 * mass * velocity * velocity;  // Energia kinetyczna
                    System.out.println("Energia kinetyczna: " + result);
                    
                    result = velocity * time;  // Droga
                    System.out.println("Droga: " + result);
                    
                    return result;
                }
            }
        }
        
        // PO REFAKTORYZACJI - osobna zmienna dla każdego celu
        static class After {
            static class GeometryCalculator {
                double calculateDiagonal(double width, double height) {
                    double area = width * height;
                    System.out.println("Pole prostokąta: " + area);
                    
                    double perimeter = 2 * (width + height);
                    System.out.println("Obwód: " + perimeter);
                    
                    double diagonal = Math.sqrt(width * width + height * height);
                    System.out.println("Przekątna: " + diagonal);
                    
                    return diagonal;
                }
                
                // Bonus: osobne metody dla każdego obliczenia
                double calculateArea(double width, double height) {
                    return width * height;
                }
                
                double calculatePerimeter(double width, double height) {
                    return 2 * (width + height);
                }
            }
            
            static class PhysicsCalculator {
                double calculateDistance(double mass, double velocity, double time) {
                    double momentum = mass * velocity;
                    System.out.println("Pęd: " + momentum);
                    
                    double kineticEnergy = 0.5 * mass * velocity * velocity;
                    System.out.println("Energia kinetyczna: " + kineticEnergy);
                    
                    double distance = velocity * time;
                    System.out.println("Droga: " + distance);
                    
                    return distance;
                }
                
                double calculateMomentum(double mass, double velocity) {
                    return mass * velocity;
                }
                
                double calculateKineticEnergy(double mass, double velocity) {
                    return 0.5 * mass * velocity * velocity;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 38: REMOVE ASSIGNMENTS TO PARAMETERS
    // ============================================================================
    // Refaktoryzacja: Usunięcie przypisań do parametrów
    // Uzasadnienie: Parametry powinny być tylko do odczytu, przypisania mylące
    
    static class RemoveAssignmentsToParametersExample {
        
        // PRZED REFAKTORYZACJĄ - modyfikacja parametrów
        static class Before {
            static class DiscountCalculator {
                double applyDiscount(double price, double discountRate) {
                    price = price * (1 - discountRate);  // Modyfikacja parametru!
                    
                    if (price < 10) {
                        price = 10;  // Minimalna cena
                    }
                    
                    return price;
                }
                
                int calculateTax(int amount) {
                    amount = amount / 100;  // Konwersja na dolary
                    amount = amount * 23;   // Podatek 23%
                    return amount;
                }
            }
            
            static class StringProcessor {
                String process(String input) {
                    if (input == null) {
                        input = "";
                    }
                    
                    input = input.trim();
                    input = input.toLowerCase();
                    
                    if (input.isEmpty()) {
                        input = "default";
                    }
                    
                    return input;
                }
            }
        }
        
        // PO REFAKTORYZACJI - lokalne zmienne zamiast modyfikacji parametrów
        static class After {
            static class DiscountCalculator {
                double applyDiscount(double price, double discountRate) {
                    double discountedPrice = price * (1 - discountRate);
                    double finalPrice = Math.max(discountedPrice, 10.0);
                    return finalPrice;
                }
                
                int calculateTax(int amount) {
                    int dollars = amount / 100;
                    int tax = dollars * 23;
                    return tax;
                }
            }
            
            static class StringProcessor {
                String process(String input) {
                    String result = input != null ? input : "";
                    result = result.trim();
                    result = result.toLowerCase();
                    
                    if (result.isEmpty()) {
                        result = "default";
                    }
                    
                    return result;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 39: REPLACE METHOD WITH METHOD CHAIN (FLUENT INTERFACE)
    // ============================================================================
    // Refaktoryzacja: Zastąpienie metod łańcuchem metod
    // Uzasadnienie: Bardziej płynne API, lepsze DSL
    
    static class ReplaceMethodWithMethodChainExample {
        
        // PRZED REFAKTORYZACJĄ - osobne wywołania metod
        static class Before {
            static class QueryBuilder {
                private String table;
                private List<String> columns = new ArrayList<>();
                private String whereClause;
                private String orderBy;
                private int limit;
                
                void setTable(String table) {
                    this.table = table;
                }
                
                void addColumn(String column) {
                    this.columns.add(column);
                }
                
                void setWhere(String whereClause) {
                    this.whereClause = whereClause;
                }
                
                void setOrderBy(String orderBy) {
                    this.orderBy = orderBy;
                }
                
                void setLimit(int limit) {
                    this.limit = limit;
                }
                
                String build() {
                    StringBuilder query = new StringBuilder("SELECT ");
                    query.append(columns.isEmpty() ? "*" : String.join(", ", columns));
                    query.append(" FROM ").append(table);
                    
                    if (whereClause != null) {
                        query.append(" WHERE ").append(whereClause);
                    }
                    
                    if (orderBy != null) {
                        query.append(" ORDER BY ").append(orderBy);
                    }
                    
                    if (limit > 0) {
                        query.append(" LIMIT ").append(limit);
                    }
                    
                    return query.toString();
                }
            }
        }
        
        // PO REFAKTORYZACJI - fluent interface
        static class After {
            static class QueryBuilder {
                private String table;
                private List<String> columns = new ArrayList<>();
                private String whereClause;
                private String orderBy;
                private int limit;
                
                QueryBuilder from(String table) {
                    this.table = table;
                    return this;
                }
                
                QueryBuilder select(String... columns) {
                    this.columns.addAll(Arrays.asList(columns));
                    return this;
                }
                
                QueryBuilder where(String whereClause) {
                    this.whereClause = whereClause;
                    return this;
                }
                
                QueryBuilder orderBy(String orderBy) {
                    this.orderBy = orderBy;
                    return this;
                }
                
                QueryBuilder limit(int limit) {
                    this.limit = limit;
                    return this;
                }
                
                String build() {
                    StringBuilder query = new StringBuilder("SELECT ");
                    query.append(columns.isEmpty() ? "*" : String.join(", ", columns));
                    query.append(" FROM ").append(table);
                    
                    if (whereClause != null) {
                        query.append(" WHERE ").append(whereClause);
                    }
                    
                    if (orderBy != null) {
                        query.append(" ORDER BY ").append(orderBy);
                    }
                    
                    if (limit > 0) {
                        query.append(" LIMIT ").append(limit);
                    }
                    
                    return query.toString();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 40: INTRODUCE LOCAL EXTENSION
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie lokalnego rozszerzenia
    // Uzasadnienie: Dodanie funkcjonalności do klasy której nie możemy modyfikować
    
    static class IntroduceLocalExtensionExample {
        
        // PRZED REFAKTORYZACJĄ - metody pomocnicze rozproszone
        static class Before {
            static class DateHelper {
                static boolean isWeekend(LocalDate date) {
                    return date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                           date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
                }
                
                static boolean isQuarterStart(LocalDate date) {
                    int month = date.getMonthValue();
                    return date.getDayOfMonth() == 1 && (month == 1 || month == 4 || month == 7 || month == 10);
                }
                
                static int getQuarter(LocalDate date) {
                    return (date.getMonthValue() - 1) / 3 + 1;
                }
            }
            
            static class ReportGenerator {
                void generateReport() {
                    LocalDate today = LocalDate.now();
                    
                    if (DateHelper.isWeekend(today)) {
                        System.out.println("Weekend - no report");
                        return;
                    }
                    
                    if (DateHelper.isQuarterStart(today)) {
                        System.out.println("Quarterly report for Q" + DateHelper.getQuarter(today));
                    } else {
                        System.out.println("Daily report");
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - rozszerzenie LocalDate
        static class After {
            static class EnhancedDate {
                private final LocalDate date;
                
                EnhancedDate(LocalDate date) {
                    this.date = date;
                }
                
                static EnhancedDate now() {
                    return new EnhancedDate(LocalDate.now());
                }
                
                static EnhancedDate of(int year, int month, int day) {
                    return new EnhancedDate(LocalDate.of(year, month, day));
                }
                
                boolean isWeekend() {
                    return date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY ||
                           date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
                }
                
                boolean isWeekday() {
                    return !isWeekend();
                }
                
                boolean isQuarterStart() {
                    int month = date.getMonthValue();
                    return date.getDayOfMonth() == 1 && (month == 1 || month == 4 || month == 7 || month == 10);
                }
                
                boolean isQuarterEnd() {
                    return date.getDayOfMonth() == date.lengthOfMonth() &&
                           (date.getMonthValue() == 3 || date.getMonthValue() == 6 ||
                            date.getMonthValue() == 9 || date.getMonthValue() == 12);
                }
                
                int getQuarter() {
                    return (date.getMonthValue() - 1) / 3 + 1;
                }
                
                EnhancedDate addBusinessDays(int days) {
                    LocalDate result = date;
                    int addedDays = 0;
                    
                    while (addedDays < days) {
                        result = result.plusDays(1);
                        EnhancedDate temp = new EnhancedDate(result);
                        if (temp.isWeekday()) {
                            addedDays++;
                        }
                    }
                    
                    return new EnhancedDate(result);
                }
                
                public LocalDate getDate() {
                    return date;
                }
                
                @Override
                public String toString() {
                    return date.toString();
                }
            }
            
            static class ReportGenerator {
                void generateReport() {
                    EnhancedDate today = EnhancedDate.now();
                    
                    if (today.isWeekend()) {
                        System.out.println("Weekend - no report");
                        return;
                    }
                    
                    if (today.isQuarterStart()) {
                        System.out.println("Quarterly report for Q" + today.getQuarter());
                    } else {
                        System.out.println("Daily report");
                    }
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 41: SELF ENCAPSULATE FIELD
    // ============================================================================
    // Refaktoryzacja: Samo-enkapsulacja pola
    // Uzasadnienie: Dostęp przez gettery/settery nawet w tej samej klasie
    
    static class SelfEncapsulateFieldExample {
        
        // PRZED REFAKTORYZACJĄ - bezpośredni dostęp do pól
        static class Before {
            static class Rectangle {
                private double width;
                private double height;
                
                Rectangle(double width, double height) {
                    this.width = width;
                    this.height = height;
                }
                
                double getArea() {
                    return width * height;  // Bezpośredni dostęp
                }
                
                void resize(double factor) {
                    width = width * factor;   // Bezpośrednia modyfikacja
                    height = height * factor;
                }
                
                double getPerimeter() {
                    return 2 * (width + height);
                }
            }
        }
        
        // PO REFAKTORYZACJI - dostęp przez gettery/settery
        static class After {
            static class Rectangle {
                private double width;
                private double height;
                
                Rectangle(double width, double height) {
                    setWidth(width);
                    setHeight(height);
                }
                
                double getArea() {
                    return getWidth() * getHeight();  // Przez getter
                }
                
                void resize(double factor) {
                    setWidth(getWidth() * factor);    // Przez setter
                    setHeight(getHeight() * factor);
                }
                
                double getPerimeter() {
                    return 2 * (getWidth() + getHeight());
                }
                
                // Możemy dodać logikę walidacji
                protected double getWidth() {
                    return width;
                }
                
                protected void setWidth(double width) {
                    if (width <= 0) {
                        throw new IllegalArgumentException("Width must be positive");
                    }
                    this.width = width;
                }
                
                protected double getHeight() {
                    return height;
                }
                
                protected void setHeight(double height) {
                    if (height <= 0) {
                        throw new IllegalArgumentException("Height must be positive");
                    }
                    this.height = height;
                }
            }
            
            // Teraz możemy łatwo utworzyć podklasę z inną logiką
            static class Square extends Rectangle {
                Square(double side) {
                    super(side, side);
                }
                
                @Override
                protected void setWidth(double width) {
                    super.setWidth(width);
                    super.setHeight(width);  // Kwadrat ma równe boki
                }
                
                @Override
                protected void setHeight(double height) {
                    super.setHeight(height);
                    super.setWidth(height);  // Kwadrat ma równe boki
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 42: REPLACE SUBCLASS WITH FIELDS
    // ============================================================================
    // Refaktoryzacja: Zastąpienie podklas polami
    // Uzasadnienie: Gdy podklasy różnią się tylko stałymi wartościami
    
    static class ReplaceSubclassWithFieldsExample {
        
        // PRZED REFAKTORYZACJĄ - osobne podklasy dla różnych wartości
        static class Before {
            static abstract class Person {
                abstract boolean isMale();
                abstract char getCode();
                abstract String getTitle();
            }
            
            static class Male extends Person {
                @Override
                boolean isMale() {
                    return true;
                }
                
                @Override
                char getCode() {
                    return 'M';
                }
                
                @Override
                String getTitle() {
                    return "Mr.";
                }
            }
            
            static class Female extends Person {
                @Override
                boolean isMale() {
                    return false;
                }
                
                @Override
                char getCode() {
                    return 'F';
                }
                
                @Override
                String getTitle() {
                    return "Ms.";
                }
            }
        }
        
        // PO REFAKTORYZACJI - jedna klasa z polami
        static class After {
            static class Person {
                private final boolean isMale;
                private final char code;
                private final String title;
                
                private Person(boolean isMale, char code, String title) {
                    this.isMale = isMale;
                    this.code = code;
                    this.title = title;
                }
                
                // Factory methods zamiast konstruktorów
                static Person createMale() {
                    return new Person(true, 'M', "Mr.");
                }
                
                static Person createFemale() {
                    return new Person(false, 'F', "Ms.");
                }
                
                static Person createNonBinary() {
                    return new Person(false, 'X', "Mx.");
                }
                
                public boolean isMale() {
                    return isMale;
                }
                
                public char getCode() {
                    return code;
                }
                
                public String getTitle() {
                    return title;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 43: LAZY INITIALIZATION
    // ============================================================================
    // Refaktoryzacja: Leniwa inicjalizacja
    // Uzasadnienie: Opóźnia kosztowne operacje do momentu rzeczywistego użycia
    
    static class LazyInitializationExample {
        
        // PRZED REFAKTORYZACJĄ - natychmiastowa inicjalizacja
        static class Before {
            static class DataService {
                private List<String> expensiveData;
                
                DataService() {
                    // Kosztowna operacja wykonywana zawsze, nawet jeśli nieużywana
                    System.out.println("Loading expensive data...");
                    expensiveData = loadExpensiveData();
                }
                
                private List<String> loadExpensiveData() {
                    try {
                        Thread.sleep(100); // Symulacja kosztownej operacji
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return Arrays.asList("Data1", "Data2", "Data3");
                }
                
                List<String> getData() {
                    return expensiveData;
                }
            }
            
            static class Report {
                private String content;
                
                Report() {
                    // Generowanie zawsze, nawet jeśli report nigdy nie zostanie użyty
                    System.out.println("Generating report content...");
                    this.content = generateContent();
                }
                
                private String generateContent() {
                    return "Very long report content...";
                }
                
                String getContent() {
                    return content;
                }
            }
        }
        
        // PO REFAKTORYZACJI - leniwa inicjalizacja
        static class After {
            static class DataService {
                private List<String> expensiveData;
                
                DataService() {
                    // Nie inicjalizujemy od razu
                }
                
                List<String> getData() {
                    if (expensiveData == null) {
                        System.out.println("Loading expensive data (lazy)...");
                        expensiveData = loadExpensiveData();
                    }
                    return expensiveData;
                }
                
                private List<String> loadExpensiveData() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return Arrays.asList("Data1", "Data2", "Data3");
                }
            }
            
            static class Report {
                private String content;
                
                Report() {
                    // Nie generujemy od razu
                }
                
                String getContent() {
                    if (content == null) {
                        System.out.println("Generating report content (lazy)...");
                        content = generateContent();
                    }
                    return content;
                }
                
                private String generateContent() {
                    return "Very long report content...";
                }
                
                // Można sprawdzić czy zostało wygenerowane
                boolean isGenerated() {
                    return content != null;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 44: REPLACE LOOP WITH PIPELINE
    // ============================================================================
    // Refaktoryzacja: Zastąpienie pętli potokiem (Stream API)
    // Uzasadnienie: Bardziej deklaratywny kod, łatwiejszy do zrozumienia
    
    static class ReplaceLoopWithPipelineExample {
        
        // PRZED REFAKTORYZACJĄ - imperatywne pętle
        static class Before {
            static class OrderProcessor {
                double calculateTotalRevenue(List<Order> orders) {
                    double total = 0.0;
                    for (Order order : orders) {
                        if (order.isCompleted()) {
                            total += order.getAmount();
                        }
                    }
                    return total;
                }
                
                List<String> getCustomerNames(List<Order> orders, double minAmount) {
                    List<String> names = new ArrayList<>();
                    for (Order order : orders) {
                        if (order.getAmount() > minAmount) {
                            String name = order.getCustomerName();
                            if (!names.contains(name)) {
                                names.add(name);
                            }
                        }
                    }
                    return names;
                }
                
                Map<String, Integer> countOrdersByStatus(List<Order> orders) {
                    Map<String, Integer> counts = new HashMap<>();
                    for (Order order : orders) {
                        String status = order.getStatus();
                        counts.put(status, counts.getOrDefault(status, 0) + 1);
                    }
                    return counts;
                }
            }
            
            static class Order {
                private String customerName;
                private double amount;
                private String status;
                
                Order(String customerName, double amount, String status) {
                    this.customerName = customerName;
                    this.amount = amount;
                    this.status = status;
                }
                
                String getCustomerName() { return customerName; }
                double getAmount() { return amount; }
                String getStatus() { return status; }
                boolean isCompleted() { return "COMPLETED".equals(status); }
            }
        }
        
        // PO REFAKTORYZACJI - deklaratywne pipeline'y
        static class After {
            static class OrderProcessor {
                double calculateTotalRevenue(List<Order> orders) {
                    return orders.stream()
                            .filter(Order::isCompleted)
                            .mapToDouble(Order::getAmount)
                            .sum();
                }
                
                List<String> getCustomerNames(List<Order> orders, double minAmount) {
                    return orders.stream()
                            .filter(order -> order.getAmount() > minAmount)
                            .map(Order::getCustomerName)
                            .distinct()
                            .collect(Collectors.toList());
                }
                
                Map<String, Long> countOrdersByStatus(List<Order> orders) {
                    return orders.stream()
                            .collect(Collectors.groupingBy(
                                Order::getStatus,
                                Collectors.counting()
                            ));
                }
                
                // Dodatkowe analizy łatwe do napisania
                List<Order> getTopOrders(List<Order> orders, int limit) {
                    return orders.stream()
                            .sorted((o1, o2) -> Double.compare(o2.getAmount(), o1.getAmount()))
                            .limit(limit)
                            .collect(Collectors.toList());
                }
                
                double getAverageOrderValue(List<Order> orders) {
                    return orders.stream()
                            .mapToDouble(Order::getAmount)
                            .average()
                            .orElse(0.0);
                }
            }
            
            static class Order {
                private String customerName;
                private double amount;
                private String status;
                
                Order(String customerName, double amount, String status) {
                    this.customerName = customerName;
                    this.amount = amount;
                    this.status = status;
                }
                
                String getCustomerName() { return customerName; }
                double getAmount() { return amount; }
                String getStatus() { return status; }
                boolean isCompleted() { return "COMPLETED".equals(status); }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 45: REMOVE DEAD CODE
    // ============================================================================
    // Refaktoryzacja: Usunięcie martwego kodu
    // Uzasadnienie: Utrzymanie niepotrzebnego kodu kosztuje, mylące
    
    static class RemoveDeadCodeExample {
        
        // PRZED REFAKTORYZACJĄ - martwy kod
        static class Before {
            static class UserService {
                void processUser(String userId) {
                    // Stara implementacja - zakomentowana ale nie usunięta
                    // String oldData = fetchOldData(userId);
                    // processOldWay(oldData);
                    
                    String userData = fetchUserData(userId);
                    processUserData(userData);
                    
                    // TODO: To było planowane ale nigdy nie zrealizowane
                    // sendNotification(userId);
                }
                
                private String fetchUserData(String userId) {
                    return "User data for " + userId;
                }
                
                private void processUserData(String data) {
                    System.out.println("Processing: " + data);
                }
                
                // Metoda nigdy nie używana
                private String fetchOldData(String userId) {
                    return "Old data";
                }
                
                // Metoda nigdy nie używana
                private void processOldWay(String data) {
                    System.out.println("Old processing");
                }
                
                // Metoda z feature flag który jest zawsze false
                void deprecatedFeature(String userId) {
                    if (isOldFeatureEnabled()) {  // Zawsze false
                        System.out.println("Old feature");
                    }
                }
                
                private boolean isOldFeatureEnabled() {
                    return false;  // Zawsze false
                }
            }
        }
        
        // PO REFAKTORYZACJI - tylko aktywny kod
        static class After {
            static class UserService {
                void processUser(String userId) {
                    String userData = fetchUserData(userId);
                    processUserData(userData);
                }
                
                private String fetchUserData(String userId) {
                    return "User data for " + userId;
                }
                
                private void processUserData(String data) {
                    System.out.println("Processing: " + data);
                }
                
                // Tylko kod który jest rzeczywiście używany
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 46: INTRODUCE SPECIAL CASE
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie przypadku specjalnego (Special Case Pattern)
    // Uzasadnienie: Eliminuje sprawdzanie null/wartości specjalnych
    
    static class IntroduceSpecialCaseExample {
        
        // PRZED REFAKTORYZACJĄ - ciągłe sprawdzanie wartości specjalnych
        static class Before {
            static class Customer {
                private String name;
                private String plan;
                private int loyaltyPoints;
                
                Customer(String name, String plan, int loyaltyPoints) {
                    this.name = name;
                    this.plan = plan;
                    this.loyaltyPoints = loyaltyPoints;
                }
                
                String getName() { return name; }
                String getPlan() { return plan; }
                int getLoyaltyPoints() { return loyaltyPoints; }
            }
            
            static class BillingService {
                double calculateBill(Customer customer) {
                    if (customer == null) {
                        return 0.0;  // Specjalny przypadek
                    }
                    
                    String plan = customer.getPlan();
                    if ("BASIC".equals(plan)) {
                        return 10.0;
                    } else if ("PREMIUM".equals(plan)) {
                        return 20.0;
                    }
                    return 0.0;
                }
                
                String getCustomerInfo(Customer customer) {
                    if (customer == null) {
                        return "Unknown Customer";
                    }
                    return customer.getName();
                }
                
                int getLoyaltyDiscount(Customer customer) {
                    if (customer == null) {
                        return 0;
                    }
                    return customer.getLoyaltyPoints() / 100;
                }
            }
        }
        
        // PO REFAKTORYZACJI - Special Case Pattern
        static class After {
            interface Customer {
                String getName();
                String getPlan();
                int getLoyaltyPoints();
                boolean isUnknown();
            }
            
            static class RealCustomer implements Customer {
                private String name;
                private String plan;
                private int loyaltyPoints;
                
                RealCustomer(String name, String plan, int loyaltyPoints) {
                    this.name = name;
                    this.plan = plan;
                    this.loyaltyPoints = loyaltyPoints;
                }
                
                public String getName() { return name; }
                public String getPlan() { return plan; }
                public int getLoyaltyPoints() { return loyaltyPoints; }
                public boolean isUnknown() { return false; }
            }
            
            // Special Case dla nieznanego klienta
            static class UnknownCustomer implements Customer {
                public String getName() { return "Unknown Customer"; }
                public String getPlan() { return "NONE"; }
                public int getLoyaltyPoints() { return 0; }
                public boolean isUnknown() { return true; }
            }
            
            static class BillingService {
                private static final Customer UNKNOWN = new UnknownCustomer();
                
                double calculateBill(Customer customer) {
                    if (customer == null) {
                        customer = UNKNOWN;
                    }
                    
                    String plan = customer.getPlan();
                    if ("BASIC".equals(plan)) {
                        return 10.0;
                    } else if ("PREMIUM".equals(plan)) {
                        return 20.0;
                    }
                    return 0.0;
                }
                
                String getCustomerInfo(Customer customer) {
                    if (customer == null) {
                        customer = UNKNOWN;
                    }
                    return customer.getName();
                }
                
                int getLoyaltyDiscount(Customer customer) {
                    if (customer == null) {
                        customer = UNKNOWN;
                    }
                    return customer.getLoyaltyPoints() / 100;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 47: COMBINE FUNCTIONS INTO CLASS
    // ============================================================================
    // Refaktoryzacja: Połączenie funkcji w klasę
    // Uzasadnienie: Grupuje powiązane funkcje i dane
    
    static class CombineFunctionsIntoClassExample {
        
        // PRZED REFAKTORYZACJĄ - rozproszone funkcje
        static class Before {
            static class MortgageCalculator {
                static double calculateMonthlyPayment(double principal, double annualRate, int years) {
                    double monthlyRate = annualRate / 12 / 100;
                    int months = years * 12;
                    return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                           (Math.pow(1 + monthlyRate, months) - 1);
                }
                
                static double calculateTotalPayment(double principal, double annualRate, int years) {
                    double monthlyPayment = calculateMonthlyPayment(principal, annualRate, years);
                    return monthlyPayment * years * 12;
                }
                
                static double calculateTotalInterest(double principal, double annualRate, int years) {
                    double totalPayment = calculateTotalPayment(principal, annualRate, years);
                    return totalPayment - principal;
                }
                
                static double calculateRemainingBalance(double principal, double annualRate, 
                                                       int years, int monthsPaid) {
                    double monthlyRate = annualRate / 12 / 100;
                    int totalMonths = years * 12;
                    int remainingMonths = totalMonths - monthsPaid;
                    
                    double monthlyPayment = calculateMonthlyPayment(principal, annualRate, years);
                    return monthlyPayment * 
                           ((Math.pow(1 + monthlyRate, remainingMonths) - 1) /
                            (monthlyRate * Math.pow(1 + monthlyRate, remainingMonths)));
                }
            }
        }
        
        // PO REFAKTORYZACJI - klasa grupująca powiązane obliczenia
        static class After {
            static class Mortgage {
                private final double principal;
                private final double annualRate;
                private final int years;
                private final double monthlyRate;
                private final int totalMonths;
                
                Mortgage(double principal, double annualRate, int years) {
                    if (principal <= 0 || annualRate < 0 || years <= 0) {
                        throw new IllegalArgumentException("Invalid mortgage parameters");
                    }
                    
                    this.principal = principal;
                    this.annualRate = annualRate;
                    this.years = years;
                    this.monthlyRate = annualRate / 12 / 100;
                    this.totalMonths = years * 12;
                }
                
                double getMonthlyPayment() {
                    return (principal * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) /
                           (Math.pow(1 + monthlyRate, totalMonths) - 1);
                }
                
                double getTotalPayment() {
                    return getMonthlyPayment() * totalMonths;
                }
                
                double getTotalInterest() {
                    return getTotalPayment() - principal;
                }
                
                double getRemainingBalance(int monthsPaid) {
                    if (monthsPaid < 0 || monthsPaid > totalMonths) {
                        throw new IllegalArgumentException("Invalid months paid");
                    }
                    
                    int remainingMonths = totalMonths - monthsPaid;
                    if (remainingMonths == 0) {
                        return 0.0;
                    }
                    
                    double monthlyPayment = getMonthlyPayment();
                    return monthlyPayment * 
                           ((Math.pow(1 + monthlyRate, remainingMonths) - 1) /
                            (monthlyRate * Math.pow(1 + monthlyRate, remainingMonths)));
                }
                
                String getSummary() {
                    return String.format(
                        "Mortgage: %.2f PLN over %d years at %.2f%%\n" +
                        "Monthly payment: %.2f PLN\n" +
                        "Total payment: %.2f PLN\n" +
                        "Total interest: %.2f PLN",
                        principal, years, annualRate,
                        getMonthlyPayment(), getTotalPayment(), getTotalInterest()
                    );
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 48: SPLIT PHASE
    // ============================================================================
    // Refaktoryzacja: Podział fazy
    // Uzasadnienie: Oddzielenie logiki w sekwencyjne fazy
    
    static class SplitPhaseExample {
        
        // PRZED REFAKTORYZACJĄ - wszystko w jednej metodzie
        static class Before {
            static class OrderProcessor {
                void processOrder(String orderData) {
                    // Faza 1: Parsowanie - zmieszane z walidacją i przetwarzaniem
                    String[] parts = orderData.split(",");
                    String productId = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    String customerId = parts[2];
                    
                    // Faza 2: Walidacja - zmieszana z obliczeniami
                    if (quantity <= 0) {
                        System.out.println("Invalid quantity");
                        return;
                    }
                    
                    double price = getProductPrice(productId);
                    double total = price * quantity;
                    
                    if (!isCustomerValid(customerId)) {
                        System.out.println("Invalid customer");
                        return;
                    }
                    
                    // Faza 3: Przetwarzanie - zmieszane z zapisem
                    double discount = calculateDiscount(customerId, total);
                    double finalAmount = total - discount;
                    
                    saveOrder(productId, quantity, customerId, finalAmount);
                    System.out.println("Order processed: " + finalAmount);
                }
                
                private double getProductPrice(String productId) { return 100.0; }
                private boolean isCustomerValid(String customerId) { return true; }
                private double calculateDiscount(String customerId, double total) { return total * 0.1; }
                private void saveOrder(String productId, int quantity, String customerId, double amount) {}
            }
        }
        
        // PO REFAKTORYZACJI - wyraźnie wydzielone fazy
        static class After {
            // Intermediate object dla komunikacji między fazami
            static class OrderData {
                final String productId;
                final int quantity;
                final String customerId;
                
                OrderData(String productId, int quantity, String customerId) {
                    this.productId = productId;
                    this.quantity = quantity;
                    this.customerId = customerId;
                }
            }
            
            static class PricedOrder {
                final OrderData orderData;
                final double price;
                final double total;
                final double discount;
                final double finalAmount;
                
                PricedOrder(OrderData orderData, double price, double total, 
                           double discount, double finalAmount) {
                    this.orderData = orderData;
                    this.price = price;
                    this.total = total;
                    this.discount = discount;
                    this.finalAmount = finalAmount;
                }
            }
            
            static class OrderProcessor {
                void processOrder(String orderData) {
                    // Faza 1: Parsowanie
                    OrderData parsed = parseOrder(orderData);
                    if (parsed == null) return;
                    
                    // Faza 2: Walidacja
                    if (!validateOrder(parsed)) return;
                    
                    // Faza 3: Kalkulacja ceny
                    PricedOrder priced = calculatePrice(parsed);
                    
                    // Faza 4: Zapis
                    saveOrder(priced);
                    
                    System.out.println("Order processed: " + priced.finalAmount);
                }
                
                private OrderData parseOrder(String orderData) {
                    try {
                        String[] parts = orderData.split(",");
                        return new OrderData(parts[0], Integer.parseInt(parts[1]), parts[2]);
                    } catch (Exception e) {
                        System.out.println("Parse error");
                        return null;
                    }
                }
                
                private boolean validateOrder(OrderData order) {
                    if (order.quantity <= 0) {
                        System.out.println("Invalid quantity");
                        return false;
                    }
                    
                    if (!isCustomerValid(order.customerId)) {
                        System.out.println("Invalid customer");
                        return false;
                    }
                    
                    return true;
                }
                
                private PricedOrder calculatePrice(OrderData order) {
                    double price = getProductPrice(order.productId);
                    double total = price * order.quantity;
                    double discount = calculateDiscount(order.customerId, total);
                    double finalAmount = total - discount;
                    
                    return new PricedOrder(order, price, total, discount, finalAmount);
                }
                
                private void saveOrder(PricedOrder order) {
                    System.out.println("Saving order to database...");
                }
                
                private double getProductPrice(String productId) { return 100.0; }
                private boolean isCustomerValid(String customerId) { return true; }
                private double calculateDiscount(String customerId, double total) { return total * 0.1; }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 49: REPLACE PRIMITIVE WITH OBJECT
    // ============================================================================
    // Refaktoryzacja: Zastąpienie prymitywu obiektem (rozszerzona wersja przykładu 17)
    // Uzasadnienie: Typ bezpieczny, enkapsulacja zachowań związanych z wartością
    
    static class ReplacePrimitiveWithObjectExample {
        
        // PRZED REFAKTORYZACJĄ - prymitywy wszędzie
        static class Before {
            static class Order {
                private String priority;  // "low", "normal", "high"
                private int deliveryDays;
                
                Order(String priority) {
                    this.priority = priority;
                    
                    // Logika rozrzucona po kodzie
                    if ("low".equals(priority)) {
                        this.deliveryDays = 7;
                    } else if ("normal".equals(priority)) {
                        this.deliveryDays = 3;
                    } else if ("high".equals(priority)) {
                        this.deliveryDays = 1;
                    } else {
                        this.deliveryDays = 5;
                    }
                }
                
                boolean isRush() {
                    return "high".equals(priority);
                }
                
                int getDeliveryDays() {
                    return deliveryDays;
                }
                
                String getPriorityDisplay() {
                    // Duplikacja logiki
                    if ("high".equals(priority)) {
                        return "⚡ HIGH PRIORITY";
                    } else if ("normal".equals(priority)) {
                        return "→ Normal";
                    }
                    return "↓ Low";
                }
            }
        }
        
        // PO REFAKTORYZACJI - dedykowany obiekt Priority
        static class After {
            static class Priority {
                private final String value;
                private static final List<String> VALID_VALUES = 
                    Arrays.asList("low", "normal", "high");
                
                private Priority(String value) {
                    this.value = value;
                }
                
                static Priority low() {
                    return new Priority("low");
                }
                
                static Priority normal() {
                    return new Priority("normal");
                }
                
                static Priority high() {
                    return new Priority("high");
                }
                
                static Priority fromString(String value) {
                    if (!VALID_VALUES.contains(value)) {
                        throw new IllegalArgumentException("Invalid priority: " + value);
                    }
                    return new Priority(value);
                }
                
                boolean isRush() {
                    return "high".equals(value);
                }
                
                int getDeliveryDays() {
                    switch (value) {
                        case "low": return 7;
                        case "normal": return 3;
                        case "high": return 1;
                        default: return 5;
                    }
                }
                
                String getDisplayName() {
                    switch (value) {
                        case "high": return "⚡ HIGH PRIORITY";
                        case "normal": return "→ Normal";
                        case "low": return "↓ Low";
                        default: return value;
                    }
                }
                
                double getShippingMultiplier() {
                    switch (value) {
                        case "high": return 2.0;
                        case "normal": return 1.0;
                        case "low": return 0.8;
                        default: return 1.0;
                    }
                }
                
                boolean higherThan(Priority other) {
                    return getNumericValue() > other.getNumericValue();
                }
                
                private int getNumericValue() {
                    switch (value) {
                        case "high": return 3;
                        case "normal": return 2;
                        case "low": return 1;
                        default: return 0;
                    }
                }
                
                @Override
                public String toString() {
                    return value;
                }
                
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    Priority priority = (Priority) o;
                    return value.equals(priority.value);
                }
                
                @Override
                public int hashCode() {
                    return value.hashCode();
                }
            }
            
            static class Order {
                private Priority priority;
                
                Order(Priority priority) {
                    this.priority = priority;
                }
                
                boolean isRush() {
                    return priority.isRush();
                }
                
                int getDeliveryDays() {
                    return priority.getDeliveryDays();
                }
                
                String getPriorityDisplay() {
                    return priority.getDisplayName();
                }
                
                double calculateShippingCost(double basePrice) {
                    return basePrice * priority.getShippingMultiplier();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 50: SLIDE STATEMENTS
    // ============================================================================
    // Refaktoryzacja: Przesunięcie instrukcji
    // Uzasadnienie: Grupuje powiązane instrukcje razem, poprawia czytelność
    
    static class SlideStatementsExample {
        
        // PRZED REFAKTORYZACJĄ - rozproszone powiązane instrukcje
        static class Before {
            static class UserProfileBuilder {
                UserProfile buildProfile(String userId) {
                    String name = fetchUserName(userId);
                    
                    // Niezwiązana operacja w środku
                    logAccess(userId);
                    
                    String email = fetchUserEmail(userId);
                    String phone = fetchUserPhone(userId);
                    
                    // Kolejna niezwiązana operacja
                    updateLastAccessTime(userId);
                    
                    // Obliczenia używające name, email, phone
                    String displayName = formatDisplayName(name);
                    String maskedEmail = maskEmail(email);
                    String formattedPhone = formatPhone(phone);
                    
                    // Niezwiązana operacja
                    incrementAccessCounter(userId);
                    
                    return new UserProfile(displayName, maskedEmail, formattedPhone);
                }
                
                private String fetchUserName(String userId) { return "John Doe"; }
                private String fetchUserEmail(String userId) { return "john@example.com"; }
                private String fetchUserPhone(String userId) { return "123456789"; }
                private String formatDisplayName(String name) { return name.toUpperCase(); }
                private String maskEmail(String email) { return email.replaceAll("@.*", "@***"); }
                private String formatPhone(String phone) { return "+48 " + phone; }
                private void logAccess(String userId) {}
                private void updateLastAccessTime(String userId) {}
                private void incrementAccessCounter(String userId) {}
            }
            
            static class UserProfile {
                String displayName;
                String email;
                String phone;
                
                UserProfile(String displayName, String email, String phone) {
                    this.displayName = displayName;
                    this.email = email;
                    this.phone = phone;
                }
            }
        }
        
        // PO REFAKTORYZACJI - powiązane instrukcje zgrupowane
        static class After {
            static class UserProfileBuilder {
                UserProfile buildProfile(String userId) {
                    // Grupa 1: Pobieranie danych
                    String name = fetchUserName(userId);
                    String email = fetchUserEmail(userId);
                    String phone = fetchUserPhone(userId);
                    
                    // Grupa 2: Formatowanie danych
                    String displayName = formatDisplayName(name);
                    String maskedEmail = maskEmail(email);
                    String formattedPhone = formatPhone(phone);
                    
                    // Grupa 3: Operacje audytowe
                    logAccess(userId);
                    updateLastAccessTime(userId);
                    incrementAccessCounter(userId);
                    
                    // Utworzenie profilu
                    return new UserProfile(displayName, maskedEmail, formattedPhone);
                }
                
                private String fetchUserName(String userId) { return "John Doe"; }
                private String fetchUserEmail(String userId) { return "john@example.com"; }
                private String fetchUserPhone(String userId) { return "123456789"; }
                private String formatDisplayName(String name) { return name.toUpperCase(); }
                private String maskEmail(String email) { return email.replaceAll("@.*", "@***"); }
                private String formatPhone(String phone) { return "+48 " + phone; }
                private void logAccess(String userId) {}
                private void updateLastAccessTime(String userId) {}
                private void incrementAccessCounter(String userId) {}
            }
            
            static class UserProfile {
                String displayName;
                String email;
                String phone;
                
                UserProfile(String displayName, String email, String phone) {
                    this.displayName = displayName;
                    this.email = email;
                    this.phone = phone;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 51: REPLACE DERIVED VARIABLE WITH QUERY
    // ============================================================================
    // Refaktoryzacja: Zastąpienie zmiennej pochodnej zapytaniem
    // Uzasadnienie: Eliminuje potrzebę synchronizacji, źródło prawdy
    
    static class ReplaceDerivedVariableWithQueryExample {
        
        // PRZED REFAKTORYZACJĄ - zmienna pochodna może być nieaktualna
        static class Before {
            static class ShoppingCart {
                private List<Item> items = new ArrayList<>();
                private double totalPrice = 0.0;  // Zmienna pochodna - może być nieaktualna!
                
                void addItem(String name, double price, int quantity) {
                    items.add(new Item(name, price, quantity));
                    totalPrice += price * quantity;  // Musimy pamiętać o aktualizacji
                }
                
                void removeItem(int index) {
                    Item removed = items.remove(index);
                    totalPrice -= removed.price * removed.quantity;  // Łatwo zapomnieć
                }
                
                void updateQuantity(int index, int newQuantity) {
                    Item item = items.get(index);
                    totalPrice -= item.price * item.quantity;
                    item.quantity = newQuantity;
                    totalPrice += item.price * item.quantity;  // Skomplikowane
                }
                
                double getTotalPrice() {
                    return totalPrice;
                }
            }
            
            static class Item {
                String name;
                double price;
                int quantity;
                
                Item(String name, double price, int quantity) {
                    this.name = name;
                    this.price = price;
                    this.quantity = quantity;
                }
            }
        }
        
        // PO REFAKTORYZACJI - obliczanie na żądanie
        static class After {
            static class ShoppingCart {
                private List<Item> items = new ArrayList<>();
                
                void addItem(String name, double price, int quantity) {
                    items.add(new Item(name, price, quantity));
                    // Nie trzeba aktualizować zmiennej totalPrice
                }
                
                void removeItem(int index) {
                    items.remove(index);
                    // Nie trzeba aktualizować zmiennej totalPrice
                }
                
                void updateQuantity(int index, int newQuantity) {
                    items.get(index).quantity = newQuantity;
                    // Nie trzeba aktualizować zmiennej totalPrice
                }
                
                // Obliczamy na żądanie - zawsze aktualne
                double getTotalPrice() {
                    return items.stream()
                            .mapToDouble(item -> item.price * item.quantity)
                            .sum();
                }
                
                int getItemCount() {
                    return items.stream()
                            .mapToInt(item -> item.quantity)
                            .sum();
                }
                
                double getAverageItemPrice() {
                    return items.stream()
                            .mapToDouble(item -> item.price)
                            .average()
                            .orElse(0.0);
                }
            }
            
            static class Item {
                String name;
                double price;
                int quantity;
                
                Item(String name, double price, int quantity) {
                    this.name = name;
                    this.price = price;
                    this.quantity = quantity;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 52: CHANGE REFERENCE TO VALUE
    // ============================================================================
    // Refaktoryzacja: Zmiana referencji na wartość
    // Uzasadnienie: Niemutowalność, bezpieczne współdzielenie, prostsze testowanie
    
    static class ChangeReferenceToValueExample {
        
        // PRZED REFAKTORYZACJĄ - obiekt referencyjny (mutowalny)
        static class Before {
            static class Person {
                private TelephoneNumber telephoneNumber;
                
                Person(String areaCode, String number) {
                    this.telephoneNumber = new TelephoneNumber(areaCode, number);
                }
                
                String getTelephoneNumber() {
                    return telephoneNumber.toString();
                }
                
                void setTelephoneNumber(String areaCode, String number) {
                    telephoneNumber.setAreaCode(areaCode);
                    telephoneNumber.setNumber(number);
                }
                
                TelephoneNumber getOfficeNumber() {
                    return telephoneNumber;  // Zwraca referencję - może być zmieniona!
                }
            }
            
            static class TelephoneNumber {
                private String areaCode;
                private String number;
                
                TelephoneNumber(String areaCode, String number) {
                    this.areaCode = areaCode;
                    this.number = number;
                }
                
                String getAreaCode() { return areaCode; }
                void setAreaCode(String areaCode) { this.areaCode = areaCode; }
                
                String getNumber() { return number; }
                void setNumber(String number) { this.number = number; }
                
                @Override
                public String toString() {
                    return "(" + areaCode + ") " + number;
                }
            }
        }
        
        // PO REFAKTORYZACJI - obiekt wartości (niemutowalny)
        static class After {
            static class Person {
                private TelephoneNumber telephoneNumber;
                
                Person(String areaCode, String number) {
                    this.telephoneNumber = new TelephoneNumber(areaCode, number);
                }
                
                String getTelephoneNumber() {
                    return telephoneNumber.toString();
                }
                
                void setTelephoneNumber(String areaCode, String number) {
                    // Tworzymy nowy obiekt zamiast modyfikować istniejący
                    this.telephoneNumber = new TelephoneNumber(areaCode, number);
                }
                
                TelephoneNumber getOfficeNumber() {
                    return telephoneNumber;  // Bezpieczne - obiekt niemutowalny
                }
            }
            
            static class TelephoneNumber {
                private final String areaCode;
                private final String number;
                
                TelephoneNumber(String areaCode, String number) {
                    this.areaCode = areaCode;
                    this.number = number;
                }
                
                String getAreaCode() { return areaCode; }
                String getNumber() { return number; }
                
                // Metody tworzące nowe obiekty zamiast modyfikować
                TelephoneNumber withAreaCode(String newAreaCode) {
                    return new TelephoneNumber(newAreaCode, this.number);
                }
                
                TelephoneNumber withNumber(String newNumber) {
                    return new TelephoneNumber(this.areaCode, newNumber);
                }
                
                @Override
                public String toString() {
                    return "(" + areaCode + ") " + number;
                }
                
                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    TelephoneNumber that = (TelephoneNumber) o;
                    return areaCode.equals(that.areaCode) && number.equals(that.number);
                }
                
                @Override
                public int hashCode() {
                    return 31 * areaCode.hashCode() + number.hashCode();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 53: CHANGE VALUE TO REFERENCE
    // ============================================================================
    // Refaktoryzacja: Zmiana wartości na referencję
    // Uzasadnienie: Współdzielona tożsamość, centralna aktualizacja
    
    static class ChangeValueToReferenceExample {
        
        // PRZED REFAKTORYZACJĄ - każde zamówienie ma własną kopię klienta
        static class Before {
            static class Order {
                private Customer customer;
                private double amount;
                
                Order(String customerName, String email, double amount) {
                    this.customer = new Customer(customerName, email);
                    this.amount = amount;
                }
                
                String getCustomerName() {
                    return customer.getName();
                }
                
                double getAmount() {
                    return amount;
                }
            }
            
            static class Customer {
                private String name;
                private String email;
                
                Customer(String name, String email) {
                    this.name = name;
                    this.email = email;
                }
                
                String getName() { return name; }
                String getEmail() { return email; }
            }
        }
        
        // PO REFAKTORYZACJI - współdzielone instancje klienta
        static class After {
            static class CustomerRepository {
                private Map<String, Customer> customers = new HashMap<>();
                
                Customer getCustomer(String customerId) {
                    return customers.computeIfAbsent(customerId, 
                        id -> new Customer(id, "customer" + id + "@example.com"));
                }
                
                void updateCustomerEmail(String customerId, String newEmail) {
                    Customer customer = customers.get(customerId);
                    if (customer != null) {
                        customer.setEmail(newEmail);
                        // Automatycznie zaktualizowane we wszystkich zamówieniach
                    }
                }
            }
            
            static class Order {
                private Customer customer;
                private double amount;
                
                Order(Customer customer, double amount) {
                    this.customer = customer;
                    this.amount = amount;
                }
                
                String getCustomerName() {
                    return customer.getName();
                }
                
                String getCustomerEmail() {
                    return customer.getEmail();
                }
                
                double getAmount() {
                    return amount;
                }
            }
            
            static class Customer {
                private String id;
                private String email;
                
                Customer(String id, String email) {
                    this.id = id;
                    this.email = email;
                }
                
                String getName() { return "Customer " + id; }
                String getEmail() { return email; }
                void setEmail(String email) { this.email = email; }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 54: REMOVE SETTING METHOD
    // ============================================================================
    // Refaktoryzacja: Usunięcie metody ustawiającej
    // Uzasadnienie: Niemutowalność, jasny cykl życia obiektu
    
    static class RemoveSettingMethodExample {
        
        // PRZED REFAKTORYZACJĄ - settery dla pól które nie powinny się zmieniać
        static class Before {
            static class Account {
                private String id;
                private String owner;
                private LocalDate createdDate;
                
                Account() {
                    // Pusty konstruktor - wymaga setterów
                }
                
                String getId() { return id; }
                void setId(String id) { this.id = id; }  // ID nie powinno się zmieniać!
                
                String getOwner() { return owner; }
                void setOwner(String owner) { this.owner = owner; }
                
                LocalDate getCreatedDate() { return createdDate; }
                void setCreatedDate(LocalDate date) { this.createdDate = date; }  // Data nie powinna się zmieniać!
            }
        }
        
        // PO REFAKTORYZACJI - tylko gettery dla niemutowalnych pól
        static class After {
            static class Account {
                private final String id;
                private final LocalDate createdDate;
                private String owner;  // Może się zmieniać
                
                Account(String id, String owner) {
                    this.id = id;
                    this.owner = owner;
                    this.createdDate = LocalDate.now();
                }
                
                // Tylko gettery dla niemutowalnych pól
                public String getId() { return id; }
                public LocalDate getCreatedDate() { return createdDate; }
                
                // Setter tylko dla mutowalnego pola
                public String getOwner() { return owner; }
                public void setOwner(String owner) {
                    if (owner == null || owner.trim().isEmpty()) {
                        throw new IllegalArgumentException("Owner cannot be empty");
                    }
                    this.owner = owner;
                }
                
                // Metoda fabrykująca zamiast setterów
                static Account create(String id, String owner) {
                    if (id == null || id.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID cannot be empty");
                    }
                    return new Account(id, owner);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 55: REPLACE COMMAND WITH FUNCTION
    // ============================================================================
    // Refaktoryzacja: Zastąpienie komendy funkcją
    // Uzasadnienie: Gdy Command nie ma stanu ani zachowań, prosta funkcja wystarczy
    
    static class ReplaceCommandWithFunctionExample {
        
        // PRZED REFAKTORYZACJĄ - Command pattern gdzie niepotrzebny
        static class Before {
            interface Command {
                void execute();
            }
            
            static class CalculatePriceCommand implements Command {
                private double basePrice;
                private double discount;
                private double result;
                
                CalculatePriceCommand(double basePrice, double discount) {
                    this.basePrice = basePrice;
                    this.discount = discount;
                }
                
                @Override
                public void execute() {
                    result = basePrice * (1 - discount);
                }
                
                double getResult() {
                    return result;
                }
            }
            
            static class SendEmailCommand implements Command {
                private String recipient;
                private String message;
                
                SendEmailCommand(String recipient, String message) {
                    this.recipient = recipient;
                    this.message = message;
                }
                
                @Override
                public void execute() {
                    System.out.println("Sending to " + recipient + ": " + message);
                }
            }
        }
        
        // PO REFAKTORYZACJI - proste funkcje
        static class After {
            static class PriceCalculator {
                static double calculatePrice(double basePrice, double discount) {
                    return basePrice * (1 - discount);
                }
            }
            
            static class EmailService {
                static void sendEmail(String recipient, String message) {
                    System.out.println("Sending to " + recipient + ": " + message);
                }
            }
            
            // Command pattern tylko gdy rzeczywiście potrzebny (undo/redo, queueing, etc.)
            interface Command {
                void execute();
                void undo();
            }
            
            static class TransferMoneyCommand implements Command {
                private String fromAccount;
                private String toAccount;
                private double amount;
                private boolean executed = false;
                
                TransferMoneyCommand(String fromAccount, String toAccount, double amount) {
                    this.fromAccount = fromAccount;
                    this.toAccount = toAccount;
                    this.amount = amount;
                }
                
                @Override
                public void execute() {
                    System.out.println("Transferring " + amount + " from " + fromAccount + " to " + toAccount);
                    executed = true;
                }
                
                @Override
                public void undo() {
                    if (executed) {
                        System.out.println("Reversing transfer");
                        executed = false;
                    }
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 56: RETURN MODIFIED VALUE
    // ============================================================================
    // Refaktoryzacja: Zwracanie zmodyfikowanej wartości
    // Uzasadnienie: Czytelniejsze, niemutowalne, łatwiejsze do śledzenia przepływu
    
    static class ReturnModifiedValueExample {
        
        // PRZED REFAKTORYZACJĄ - modyfikacja przez efekt uboczny
        static class Before {
            static class PriceProcessor {
                void applyDiscounts(Order order) {
                    double price = order.getBasePrice();
                    
                    // Modyfikacja przez setter
                    price = price * 0.9;  // 10% discount
                    order.setPrice(price);
                    
                    if (order.isPremiumCustomer()) {
                        price = price * 0.95;  // Additional 5% discount
                        order.setPrice(price);
                    }
                    
                    if (price > 1000) {
                        price = price - 50;  // Bulk discount
                        order.setPrice(price);
                    }
                }
            }
            
            static class Order {
                private double basePrice;
                private double price;
                private boolean premiumCustomer;
                
                Order(double basePrice, boolean premiumCustomer) {
                    this.basePrice = basePrice;
                    this.price = basePrice;
                    this.premiumCustomer = premiumCustomer;
                }
                
                double getBasePrice() { return basePrice; }
                double getPrice() { return price; }
                void setPrice(double price) { this.price = price; }
                boolean isPremiumCustomer() { return premiumCustomer; }
            }
        }
        
        // PO REFAKTORYZACJI - zwracanie zmodyfikowanej wartości
        static class After {
            static class PriceProcessor {
                double applyDiscounts(Order order) {
                    double price = order.getBasePrice();
                    
                    price = applyStandardDiscount(price);
                    
                    if (order.isPremiumCustomer()) {
                        price = applyPremiumDiscount(price);
                    }
                    
                    if (price > 1000) {
                        price = applyBulkDiscount(price);
                    }
                    
                    return price;
                }
                
                private double applyStandardDiscount(double price) {
                    return price * 0.9;
                }
                
                private double applyPremiumDiscount(double price) {
                    return price * 0.95;
                }
                
                private double applyBulkDiscount(double price) {
                    return price - 50;
                }
            }
            
            static class Order {
                private final double basePrice;
                private final boolean premiumCustomer;
                
                Order(double basePrice, boolean premiumCustomer) {
                    this.basePrice = basePrice;
                    this.premiumCustomer = premiumCustomer;
                }
                
                double getBasePrice() { return basePrice; }
                boolean isPremiumCustomer() { return premiumCustomer; }
                
                // Obliczamy cenę na żądanie
                double calculateFinalPrice(PriceProcessor processor) {
                    return processor.applyDiscounts(this);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 57: REPLACE CONSTRUCTOR WITH BUILDER
    // ============================================================================
    // Refaktoryzacja: Zastąpienie konstruktora builderem
    // Uzasadnienie: Wiele parametrów, opcjonalne wartości, czytelność
    
    static class ReplaceConstructorWithBuilderExample {
        
        // PRZED REFAKTORYZACJĄ - konstruktor z wieloma parametrami
        static class Before {
            static class EmailMessage {
                private String from;
                private String to;
                private String subject;
                private String body;
                private String cc;
                private String bcc;
                private boolean isHtml;
                private int priority;
                
                // Zbyt wiele parametrów, trudno zapamiętać kolejność
                EmailMessage(String from, String to, String subject, String body,
                           String cc, String bcc, boolean isHtml, int priority) {
                    this.from = from;
                    this.to = to;
                    this.subject = subject;
                    this.body = body;
                    this.cc = cc;
                    this.bcc = bcc;
                    this.isHtml = isHtml;
                    this.priority = priority;
                }
                
                // Wiele przeładowań konstruktora
                EmailMessage(String from, String to, String subject, String body) {
                    this(from, to, subject, body, null, null, false, 0);
                }
                
                EmailMessage(String from, String to, String subject, String body, boolean isHtml) {
                    this(from, to, subject, body, null, null, isHtml, 0);
                }
            }
        }
        
        // PO REFAKTORYZACJI - wzorzec Builder
        static class After {
            static class EmailMessage {
                private final String from;
                private final String to;
                private final String subject;
                private final String body;
                private final String cc;
                private final String bcc;
                private final boolean isHtml;
                private final int priority;
                
                private EmailMessage(Builder builder) {
                    this.from = builder.from;
                    this.to = builder.to;
                    this.subject = builder.subject;
                    this.body = builder.body;
                    this.cc = builder.cc;
                    this.bcc = builder.bcc;
                    this.isHtml = builder.isHtml;
                    this.priority = builder.priority;
                }
                
                static Builder builder() {
                    return new Builder();
                }
                
                static class Builder {
                    private String from;
                    private String to;
                    private String subject;
                    private String body;
                    private String cc;
                    private String bcc;
                    private boolean isHtml = false;
                    private int priority = 0;
                    
                    public Builder from(String from) {
                        this.from = from;
                        return this;
                    }
                    
                    public Builder to(String to) {
                        this.to = to;
                        return this;
                    }
                    
                    public Builder subject(String subject) {
                        this.subject = subject;
                        return this;
                    }
                    
                    public Builder body(String body) {
                        this.body = body;
                        return this;
                    }
                    
                    public Builder cc(String cc) {
                        this.cc = cc;
                        return this;
                    }
                    
                    public Builder bcc(String bcc) {
                        this.bcc = bcc;
                        return this;
                    }
                    
                    public Builder html(boolean isHtml) {
                        this.isHtml = isHtml;
                        return this;
                    }
                    
                    public Builder priority(int priority) {
                        this.priority = priority;
                        return this;
                    }
                    
                    public EmailMessage build() {
                        if (from == null || to == null || subject == null) {
                            throw new IllegalStateException("From, to, and subject are required");
                        }
                        return new EmailMessage(this);
                    }
                }
                
                @Override
                public String toString() {
                    return String.format("Email from %s to %s: %s", from, to, subject);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 58: ENCAPSULATE DOWNCAST
    // ============================================================================
    // Refaktoryzacja: Enkapsulacja rzutowania w dół
    // Uzasadnienie: Ukrywa szczegóły typów, bezpieczniejsze API
    
    static class EncapsulateDowncastExample {
        
        // PRZED REFAKTORYZACJĄ - klient musi rzutować
        static class Before {
            static class Library {
                private List<Object> items = new ArrayList<>();
                
                void addBook(String title, String author) {
                    items.add(new Book(title, author));
                }
                
                void addMagazine(String title, int issue) {
                    items.add(new Magazine(title, issue));
                }
                
                // Zwraca Object - klient musi rzutować
                Object getItem(int index) {
                    return items.get(index);
                }
                
                List<Object> getAllItems() {
                    return new ArrayList<>(items);
                }
            }
            
            static class Book {
                String title;
                String author;
                
                Book(String title, String author) {
                    this.title = title;
                    this.author = author;
                }
            }
            
            static class Magazine {
                String title;
                int issue;
                
                Magazine(String title, int issue) {
                    this.title = title;
                    this.issue = issue;
                }
            }
        }
        
        // PO REFAKTORYZACJI - enkapsulacja rzutowania
        static class After {
            interface LibraryItem {
                String getTitle();
                String getDescription();
            }
            
            static class Library {
                private List<LibraryItem> items = new ArrayList<>();
                
                void addBook(String title, String author) {
                    items.add(new Book(title, author));
                }
                
                void addMagazine(String title, int issue) {
                    items.add(new Magazine(title, issue));
                }
                
                // Zwraca typowany interfejs - bez rzutowania
                LibraryItem getItem(int index) {
                    return items.get(index);
                }
                
                List<LibraryItem> getAllItems() {
                    return new ArrayList<>(items);
                }
                
                // Bezpieczne metody dla konkretnych typów
                List<Book> getBooks() {
                    return items.stream()
                            .filter(item -> item instanceof Book)
                            .map(item -> (Book) item)
                            .collect(Collectors.toList());
                }
                
                List<Magazine> getMagazines() {
                    return items.stream()
                            .filter(item -> item instanceof Magazine)
                            .map(item -> (Magazine) item)
                            .collect(Collectors.toList());
                }
            }
            
            static class Book implements LibraryItem {
                private String title;
                private String author;
                
                Book(String title, String author) {
                    this.title = title;
                    this.author = author;
                }
                
                @Override
                public String getTitle() { return title; }
                
                @Override
                public String getDescription() {
                    return title + " by " + author;
                }
                
                public String getAuthor() { return author; }
            }
            
            static class Magazine implements LibraryItem {
                private String title;
                private int issue;
                
                Magazine(String title, int issue) {
                    this.title = title;
                    this.issue = issue;
                }
                
                @Override
                public String getTitle() { return title; }
                
                @Override
                public String getDescription() {
                    return title + " #" + issue;
                }
                
                public int getIssue() { return issue; }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 59: REPLACE TYPE CODE WITH STATE/STRATEGY
    // ============================================================================
    // Refaktoryzacja: Zastąpienie kodu typu wzorcem State/Strategy
    // Uzasadnienie: Dynamiczna zmiana zachowania, bardziej elastyczne niż podklasy
    
    static class ReplaceTypeCodeWithStateStrategyExample {
        
        // PRZED REFAKTORYZACJĄ - kod typu z warunkami
        static class Before {
            static class Document {
                private String content;
                private String state; // "DRAFT", "MODERATION", "PUBLISHED"
                
                Document(String content) {
                    this.content = content;
                    this.state = "DRAFT";
                }
                
                void publish() {
                    if ("DRAFT".equals(state)) {
                        state = "MODERATION";
                        System.out.println("Sent to moderation");
                    } else if ("MODERATION".equals(state)) {
                        state = "PUBLISHED";
                        System.out.println("Published");
                    } else {
                        System.out.println("Already published");
                    }
                }
                
                boolean canEdit() {
                    return "DRAFT".equals(state);
                }
                
                String getStatus() {
                    switch (state) {
                        case "DRAFT": return "Draft - can be edited";
                        case "MODERATION": return "In moderation - waiting for approval";
                        case "PUBLISHED": return "Published - read only";
                        default: return "Unknown";
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - wzorzec State
        static class After {
            interface DocumentState {
                void publish(Document document);
                boolean canEdit();
                String getStatus();
                String getName();
            }
            
            static class DraftState implements DocumentState {
                @Override
                public void publish(Document document) {
                    System.out.println("Sent to moderation");
                    document.setState(new ModerationState());
                }
                
                @Override
                public boolean canEdit() {
                    return true;
                }
                
                @Override
                public String getStatus() {
                    return "Draft - can be edited";
                }
                
                @Override
                public String getName() {
                    return "DRAFT";
                }
            }
            
            static class ModerationState implements DocumentState {
                @Override
                public void publish(Document document) {
                    System.out.println("Published");
                    document.setState(new PublishedState());
                }
                
                @Override
                public boolean canEdit() {
                    return false;
                }
                
                @Override
                public String getStatus() {
                    return "In moderation - waiting for approval";
                }
                
                @Override
                public String getName() {
                    return "MODERATION";
                }
            }
            
            static class PublishedState implements DocumentState {
                @Override
                public void publish(Document document) {
                    System.out.println("Already published");
                }
                
                @Override
                public boolean canEdit() {
                    return false;
                }
                
                @Override
                public String getStatus() {
                    return "Published - read only";
                }
                
                @Override
                public String getName() {
                    return "PUBLISHED";
                }
            }
            
            static class Document {
                private String content;
                private DocumentState state;
                
                Document(String content) {
                    this.content = content;
                    this.state = new DraftState();
                }
                
                void publish() {
                    state.publish(this);
                }
                
                boolean canEdit() {
                    return state.canEdit();
                }
                
                String getStatus() {
                    return state.getStatus();
                }
                
                void setState(DocumentState state) {
                    this.state = state;
                }
                
                String getCurrentStateName() {
                    return state.getName();
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 60: INTRODUCE PARAMETER OBJECT WITH BUILDER
    // ============================================================================
    // Refaktoryzacja: Połączenie Parameter Object z Builderem
    // Uzasadnienie: Wiele parametrów + opcjonalne wartości = Builder pattern
    
    static class IntroduceParameterObjectWithBuilderExample {
        
        // PRZED REFAKTORYZACJĄ - długa lista parametrów
        static class Before {
            static class ReportGenerator {
                void generateReport(String title, LocalDate startDate, LocalDate endDate,
                                  String format, boolean includeCharts, boolean includeDetails,
                                  String author, String department, int maxRows,
                                  String sortBy, String filterBy) {
                    System.out.println("Generating report: " + title);
                    System.out.println("Period: " + startDate + " to " + endDate);
                    System.out.println("Format: " + format);
                    // ... więcej logiki
                }
            }
        }
        
        // PO REFAKTORYZACJI - Parameter Object z Builderem
        static class After {
            static class ReportConfig {
                private final String title;
                private final LocalDate startDate;
                private final LocalDate endDate;
                private final String format;
                private final boolean includeCharts;
                private final boolean includeDetails;
                private final String author;
                private final String department;
                private final int maxRows;
                private final String sortBy;
                private final String filterBy;
                
                private ReportConfig(Builder builder) {
                    this.title = builder.title;
                    this.startDate = builder.startDate;
                    this.endDate = builder.endDate;
                    this.format = builder.format;
                    this.includeCharts = builder.includeCharts;
                    this.includeDetails = builder.includeDetails;
                    this.author = builder.author;
                    this.department = builder.department;
                    this.maxRows = builder.maxRows;
                    this.sortBy = builder.sortBy;
                    this.filterBy = builder.filterBy;
                }
                
                static Builder builder(String title, LocalDate startDate, LocalDate endDate) {
                    return new Builder(title, startDate, endDate);
                }
                
                static class Builder {
                    private final String title;
                    private final LocalDate startDate;
                    private final LocalDate endDate;
                    private String format = "PDF";
                    private boolean includeCharts = true;
                    private boolean includeDetails = true;
                    private String author = "System";
                    private String department = "General";
                    private int maxRows = 1000;
                    private String sortBy = "date";
                    private String filterBy = null;
                    
                    private Builder(String title, LocalDate startDate, LocalDate endDate) {
                        this.title = title;
                        this.startDate = startDate;
                        this.endDate = endDate;
                    }
                    
                    public Builder format(String format) {
                        this.format = format;
                        return this;
                    }
                    
                    public Builder charts(boolean include) {
                        this.includeCharts = include;
                        return this;
                    }
                    
                    public Builder details(boolean include) {
                        this.includeDetails = include;
                        return this;
                    }
                    
                    public Builder author(String author) {
                        this.author = author;
                        return this;
                    }
                    
                    public Builder department(String department) {
                        this.department = department;
                        return this;
                    }
                    
                    public Builder maxRows(int maxRows) {
                        this.maxRows = maxRows;
                        return this;
                    }
                    
                    public Builder sortBy(String sortBy) {
                        this.sortBy = sortBy;
                        return this;
                    }
                    
                    public Builder filterBy(String filterBy) {
                        this.filterBy = filterBy;
                        return this;
                    }
                    
                    public ReportConfig build() {
                        return new ReportConfig(this);
                    }
                }
                
                // Gettery
                public String getTitle() { return title; }
                public LocalDate getStartDate() { return startDate; }
                public LocalDate getEndDate() { return endDate; }
                public String getFormat() { return format; }
                public boolean isIncludeCharts() { return includeCharts; }
                public boolean isIncludeDetails() { return includeDetails; }
            }
            
            static class ReportGenerator {
                void generateReport(ReportConfig config) {
                    System.out.println("Generating report: " + config.getTitle());
                    System.out.println("Period: " + config.getStartDate() + " to " + config.getEndDate());
                    System.out.println("Format: " + config.getFormat());
                    System.out.println("Include charts: " + config.isIncludeCharts());
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 61: REPLACE LOOP WITH RECURSION
    // ============================================================================
    // Refaktoryzacja: Zastąpienie pętli rekursją
    // Uzasadnienie: Bardziej deklaratywne, naturalne dla pewnych problemów
    
    static class ReplaceLoopWithRecursionExample {
        
        // PRZED REFAKTORYZACJĄ - iteracyjne rozwiązanie
        static class Before {
            static class TreeNode {
                int value;
                List<TreeNode> children;
                
                TreeNode(int value) {
                    this.value = value;
                    this.children = new ArrayList<>();
                }
                
                void addChild(TreeNode child) {
                    children.add(child);
                }
            }
            
            static class TreeProcessor {
                int calculateSum(TreeNode root) {
                    int sum = 0;
                    List<TreeNode> queue = new ArrayList<>();
                    queue.add(root);
                    
                    while (!queue.isEmpty()) {
                        TreeNode node = queue.remove(0);
                        sum += node.value;
                        queue.addAll(node.children);
                    }
                    
                    return sum;
                }
                
                int findMax(TreeNode root) {
                    int max = root.value;
                    List<TreeNode> queue = new ArrayList<>();
                    queue.add(root);
                    
                    while (!queue.isEmpty()) {
                        TreeNode node = queue.remove(0);
                        if (node.value > max) {
                            max = node.value;
                        }
                        queue.addAll(node.children);
                    }
                    
                    return max;
                }
            }
        }
        
        // PO REFAKTORYZACJI - rozwiązanie rekurencyjne
        static class After {
            static class TreeNode {
                int value;
                List<TreeNode> children;
                
                TreeNode(int value) {
                    this.value = value;
                    this.children = new ArrayList<>();
                }
                
                void addChild(TreeNode child) {
                    children.add(child);
                }
            }
            
            static class TreeProcessor {
                int calculateSum(TreeNode node) {
                    if (node == null) {
                        return 0;
                    }
                    
                    int sum = node.value;
                    for (TreeNode child : node.children) {
                        sum += calculateSum(child);
                    }
                    return sum;
                }
                
                int findMax(TreeNode node) {
                    if (node == null) {
                        return Integer.MIN_VALUE;
                    }
                    
                    int max = node.value;
                    for (TreeNode child : node.children) {
                        int childMax = findMax(child);
                        if (childMax > max) {
                            max = childMax;
                        }
                    }
                    return max;
                }
                
                // Dodatkowe przydatne metody rekurencyjne
                int countNodes(TreeNode node) {
                    if (node == null) {
                        return 0;
                    }
                    
                    int count = 1;
                    for (TreeNode child : node.children) {
                        count += countNodes(child);
                    }
                    return count;
                }
                
                int getHeight(TreeNode node) {
                    if (node == null) {
                        return 0;
                    }
                    
                    int maxChildHeight = 0;
                    for (TreeNode child : node.children) {
                        int childHeight = getHeight(child);
                        if (childHeight > maxChildHeight) {
                            maxChildHeight = childHeight;
                        }
                    }
                    return 1 + maxChildHeight;
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 62: INTRODUCE POLYMORPHIC CREATION
    // ============================================================================
    // Refaktoryzacja: Wprowadzenie polimorficznego tworzenia
    // Uzasadnienie: Factory method w hierarchii, każda podklasa wie jak się stworzyć
    
    static class IntroducePolymorphicCreationExample {
        
        // PRZED REFAKTORYZACJĄ - zewnętrzna fabryka z warunkami
        static class Before {
            static abstract class Shape {
                abstract double area();
            }
            
            static class Circle extends Shape {
                double radius;
                
                Circle(double radius) {
                    this.radius = radius;
                }
                
                @Override
                double area() {
                    return Math.PI * radius * radius;
                }
            }
            
            static class Rectangle extends Shape {
                double width;
                double height;
                
                Rectangle(double width, double height) {
                    this.width = width;
                    this.height = height;
                }
                
                @Override
                double area() {
                    return width * height;
                }
            }
            
            static class ShapeFactory {
                static Shape createShape(String type, double... params) {
                    switch (type) {
                        case "circle":
                            return new Circle(params[0]);
                        case "rectangle":
                            return new Rectangle(params[0], params[1]);
                        default:
                            throw new IllegalArgumentException("Unknown shape type");
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - polimorficzne tworzenie
        static class After {
            static abstract class Shape {
                abstract double area();
                abstract Shape createSimilar(double... params);
            }
            
            static class Circle extends Shape {
                private double radius;
                
                Circle(double radius) {
                    this.radius = radius;
                }
                
                @Override
                double area() {
                    return Math.PI * radius * radius;
                }
                
                @Override
                Circle createSimilar(double... params) {
                    return new Circle(params[0]);
                }
                
                Circle withRadius(double newRadius) {
                    return new Circle(newRadius);
                }
            }
            
            static class Rectangle extends Shape {
                private double width;
                private double height;
                
                Rectangle(double width, double height) {
                    this.width = width;
                    this.height = height;
                }
                
                @Override
                double area() {
                    return width * height;
                }
                
                @Override
                Rectangle createSimilar(double... params) {
                    return new Rectangle(params[0], params[1]);
                }
                
                Rectangle withDimensions(double newWidth, double newHeight) {
                    return new Rectangle(newWidth, newHeight);
                }
            }
        }
    }

    // ============================================================================
    // PRZYKŁAD 63: REPLACE CALLBACK WITH PROMISE (using CompletableFuture)
    // ============================================================================
    // Refaktoryzacja: Zastąpienie callback'a Promise
    // Uzasadnienie: Unikanie callback hell, łatwiejsze łączenie operacji
    
    static class ReplaceCallbackWithPromiseExample {
        
        // PRZED REFAKTORYZACJĄ - callback hell
        static class Before {
            interface Callback<T> {
                void onSuccess(T result);
                void onError(Exception error);
            }
            
            static class DataService {
                void fetchUser(String userId, Callback<String> callback) {
                    // Symulacja asynchronicznej operacji
                    try {
                        Thread.sleep(100);
                        callback.onSuccess("User-" + userId);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
                
                void fetchOrders(String user, Callback<List<String>> callback) {
                    try {
                        Thread.sleep(100);
                        callback.onSuccess(Arrays.asList("Order1", "Order2"));
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
                
                void processOrders(List<String> orders, Callback<String> callback) {
                    try {
                        Thread.sleep(100);
                        callback.onSuccess("Processed " + orders.size() + " orders");
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }
        }
        
        // PO REFAKTORYZACJI - CompletableFuture (Promise)
        static class After {
            static class DataService {
                java.util.concurrent.CompletableFuture<String> fetchUser(String userId) {
                    return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return "User-" + userId;
                    });
                }
                
                java.util.concurrent.CompletableFuture<List<String>> fetchOrders(String user) {
                    return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return Arrays.asList("Order1", "Order2");
                    });
                }
                
                java.util.concurrent.CompletableFuture<String> processOrders(List<String> orders) {
                    return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return "Processed " + orders.size() + " orders";
                    });
                }
                
                // Łatwe łączenie operacji
                java.util.concurrent.CompletableFuture<String> processUserOrders(String userId) {
                    return fetchUser(userId)
                            .thenCompose(this::fetchOrders)
                            .thenCompose(this::processOrders);
                }
            }
        }
    }

    // ============================================================================
    // METODY DEMONSTRACYJNE
    // ============================================================================
    
    private static void demonstrateExtractMethod() {
        System.out.println("1. EXTRACT METHOD - Wydzielenie metody");
        System.out.println("Refaktoryzacja długiej metody na mniejsze, bardziej czytelne metody\n");
        
        List<OrderItem> items = Arrays.asList(
            new OrderItem("Laptop", 1, 3000.0),
            new OrderItem("Mysz", 2, 50.0)
        );
        
        System.out.println("PRZED:");
        new ExtractMethodExample.Before.Order("Jan Kowalski", items).printInvoice();
        
        System.out.println("\nPO:");
        new ExtractMethodExample.After.Order("Jan Kowalski", items).printInvoice();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceTempWithQuery() {
        System.out.println("2. REPLACE TEMP WITH QUERY - Zastąpienie zmiennej tymczasowej metodą");
        System.out.println("Eliminuje zmienne tymczasowe, czyni kod bardziej ekspresyjnym\n");
        
        System.out.println("PRZED:");
        double priceBefore = new ReplaceTempWithQueryExample.Before
            .PriceCalculator(100.0, 5, 0.1).calculateFinalPrice();
        System.out.println("Cena końcowa: " + priceBefore);
        
        System.out.println("\nPO:");
        double priceAfter = new ReplaceTempWithQueryExample.After
            .PriceCalculator(100.0, 5, 0.1).calculateFinalPrice();
        System.out.println("Cena końcowa: " + priceAfter);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceParameterObject() {
        System.out.println("3. INTRODUCE PARAMETER OBJECT - Wprowadzenie obiektu parametrów");
        System.out.println("Grupuje powiązane parametry w jeden obiekt\n");
        
        IntroduceParameterObjectExample.After.ReportConfig config = 
            new IntroduceParameterObjectExample.After.ReportConfig(
                "Raport sprzedaży Q4",
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 12, 31),
                "Jan Kowalski",
                "Sprzedaż",
                true,
                true,
                "PDF"
            );
        
        System.out.println("PO refaktoryzacji (zamiast 8 parametrów - jeden obiekt):");
        new IntroduceParameterObjectExample.After.ReportGenerator().generateReport(config);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceConditionalWithPolymorphism() {
        System.out.println("4. REPLACE CONDITIONAL WITH POLYMORPHISM");
        System.out.println("Zastąpienie switch/if-else polimorfizmem\n");
        
        System.out.println("PRZED (z switch):");
        ReplaceConditionalExample.Before.Employee engineer = 
            new ReplaceConditionalExample.Before.Employee(
                ReplaceConditionalExample.Before.EmployeeType.ENGINEER, 8000, 0, 2000);
        System.out.println("Rola: " + engineer.getRole() + ", Pensja: " + engineer.calculateSalary());
        
        System.out.println("\nPO (polimorfizm):");
        ReplaceConditionalExample.After.Employee engineerAfter = 
            new ReplaceConditionalExample.After.Engineer(8000, 2000);
        System.out.println("Rola: " + engineerAfter.getRole() + ", Pensja: " + engineerAfter.calculateSalary());
        
        ReplaceConditionalExample.After.Employee manager = 
            new ReplaceConditionalExample.After.Manager(10000, 3000);
        System.out.println("Rola: " + manager.getRole() + ", Pensja: " + manager.calculateSalary());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateDecomposeConditional() {
        System.out.println("5. DECOMPOSE CONDITIONAL - Dekompozycja złożonych warunków");
        System.out.println("Wydzielenie warunków do nazwanych metod\n");
        
        LocalDate winterDate = LocalDate.of(2025, 1, 15);
        LocalDate summerDate = LocalDate.of(2025, 7, 15);
        
        System.out.println("PRZED:");
        DecomposeConditionalExample.Before.SubscriptionService serviceBefore = 
            new DecomposeConditionalExample.Before.SubscriptionService();
        System.out.println("Cena zimowa (premium): " + serviceBefore.calculatePrice(winterDate, 10, true));
        System.out.println("Cena letnia (standard): " + serviceBefore.calculatePrice(summerDate, 10, false));
        
        System.out.println("\nPO (z nazwanymi metodami):");
        DecomposeConditionalExample.After.SubscriptionService serviceAfter = 
            new DecomposeConditionalExample.After.SubscriptionService();
        System.out.println("Cena zimowa (premium): " + serviceAfter.calculatePrice(winterDate, 10, true));
        System.out.println("Cena letnia (standard): " + serviceAfter.calculatePrice(summerDate, 10, false));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceMagicNumbers() {
        System.out.println("6. REPLACE MAGIC NUMBERS - Zastąpienie magicznych liczb stałymi");
        System.out.println("Nazwane stałe zamiast literałów liczbowych\n");
        
        System.out.println("PRZED (magiczne liczby):");
        double shippingBefore = new ReplaceMagicNumbersExample.Before
            .ShippingCalculator().calculateShipping(1.5, 100);
        System.out.println("Koszt wysyłki (1.5kg, 100km): " + shippingBefore);
        
        System.out.println("\nPO (nazwane stałe):");
        double shippingAfter = new ReplaceMagicNumbersExample.After
            .ShippingCalculator().calculateShipping(1.5, 100);
        System.out.println("Koszt wysyłki (1.5kg, 100km): " + shippingAfter);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateExtractClass() {
        System.out.println("7. EXTRACT CLASS - Wydzielenie klasy");
        System.out.println("Podział klasy robiącej zbyt wiele na mniejsze, wyspecjalizowane klasy\n");
        
        System.out.println("PRZED (jedna klasa ze wszystkimi danymi):");
        ExtractClassExample.Before.Person personBefore = 
            new ExtractClassExample.Before.Person(
                "Jan", "Kowalski", "ul. Kwiatowa 5", 
                "Warszawa", "00-001", "123456789", "jan@example.com");
        System.out.println("Osoba: " + personBefore.getFullName());
        System.out.println("Adres: " + personBefore.getFullAddress());
        System.out.println("Kontakt: " + personBefore.getContactInfo());
        
        System.out.println("\nPO (osobne klasy dla różnych koncepcji):");
        ExtractClassExample.After.Address address = 
            new ExtractClassExample.After.Address("ul. Kwiatowa 5", "Warszawa", "00-001");
        ExtractClassExample.After.ContactInfo contact = 
            new ExtractClassExample.After.ContactInfo("123456789", "jan@example.com");
        ExtractClassExample.After.Person personAfter = 
            new ExtractClassExample.After.Person("Jan", "Kowalski", address, contact);
        
        System.out.println("Osoba: " + personAfter.getFullName());
        System.out.println("Adres: " + personAfter.getAddress().getFullAddress());
        System.out.println("Kontakt: " + personAfter.getContactInfo().getContactDetails());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceTypeCodeWithSubclasses() {
        System.out.println("8. REPLACE TYPE CODE WITH SUBCLASSES");
        System.out.println("Zastąpienie enum z warunkami podklasami\n");
        
        System.out.println("PRZED (enum z switch):");
        ReplaceTypeCodeExample.Before.BankAccount checkingBefore = 
            new ReplaceTypeCodeExample.Before.BankAccount(
                ReplaceTypeCodeExample.Before.AccountType.CHECKING, 800);
        System.out.println("Konto czekowe - Opłata: " + checkingBefore.calculateMonthlyFee() + 
                         ", Odsetki: " + checkingBefore.calculateYearlyInterest());
        
        System.out.println("\nPO (polimorfizm z podklasami):");
        ReplaceTypeCodeExample.After.BankAccount checkingAfter = 
            new ReplaceTypeCodeExample.After.CheckingAccount(800);
        System.out.println("Konto czekowe - Opłata: " + checkingAfter.calculateMonthlyFee() + 
                         ", Odsetki: " + checkingAfter.calculateYearlyInterest());
        
        ReplaceTypeCodeExample.After.BankAccount savingsAfter = 
            new ReplaceTypeCodeExample.After.SavingsAccount(5000);
        System.out.println("Konto oszczędnościowe - Opłata: " + savingsAfter.calculateMonthlyFee() + 
                         ", Odsetki: " + savingsAfter.calculateYearlyInterest());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateRemoveMiddleMan() {
        System.out.println("9. REMOVE MIDDLE MAN - Usunięcie pośrednika");
        System.out.println("Bezpośredni dostęp zamiast delegacji przez wiele metod\n");
        
        RemoveMiddleManExample.Before.Manager managerBefore = 
            new RemoveMiddleManExample.Before.Manager("Anna Nowak", "anna@example.com", 10);
        RemoveMiddleManExample.Before.Department deptBefore = 
            new RemoveMiddleManExample.Before.Department("IT", managerBefore);
        
        System.out.println("PRZED (wiele metod delegujących):");
        System.out.println("Manager: " + deptBefore.getManagerName());
        System.out.println("Email: " + deptBefore.getManagerEmail());
        deptBefore.approveExpense(5000);
        
        RemoveMiddleManExample.After.Manager managerAfter = 
            new RemoveMiddleManExample.After.Manager("Anna Nowak", "anna@example.com", 10);
        RemoveMiddleManExample.After.Department deptAfter = 
            new RemoveMiddleManExample.After.Department("IT", managerAfter);
        
        System.out.println("\nPO (bezpośredni dostęp):");
        System.out.println("Manager: " + deptAfter.getManager().getName());
        System.out.println("Email: " + deptAfter.getManager().getEmail());
        deptAfter.getManager().approveExpense(5000);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceNullObject() {
        System.out.println("10. INTRODUCE NULL OBJECT - Wprowadzenie obiektu Null");
        System.out.println("Obiekt Null zamiast sprawdzania null\n");
        
        System.out.println("PRZED (sprawdzanie null):");
        IntroduceNullObjectExample.Before.Order orderBefore1 = 
            new IntroduceNullObjectExample.Before.Order(
                new IntroduceNullObjectExample.Before.Customer("Jan Kowalski", "jan@example.com"), 
                1000);
        System.out.println("Klient: " + orderBefore1.getCustomerName());
        System.out.println("Suma: " + orderBefore1.calculateTotal());
        orderBefore1.sendConfirmation();
        
        IntroduceNullObjectExample.Before.Order orderBefore2 = 
            new IntroduceNullObjectExample.Before.Order(null, 1000);
        System.out.println("\nKlient: " + orderBefore2.getCustomerName());
        System.out.println("Suma: " + orderBefore2.calculateTotal());
        orderBefore2.sendConfirmation();
        
        System.out.println("\nPO (obiekt Null):");
        IntroduceNullObjectExample.After.Order orderAfter1 = 
            new IntroduceNullObjectExample.After.Order(
                new IntroduceNullObjectExample.After.RealCustomer("Jan Kowalski", "jan@example.com"), 
                1000);
        System.out.println("Klient: " + orderAfter1.getCustomerName());
        System.out.println("Suma: " + orderAfter1.calculateTotal());
        orderAfter1.sendConfirmation();
        
        IntroduceNullObjectExample.After.Order orderAfter2 = 
            new IntroduceNullObjectExample.After.Order(null, 1000);
        System.out.println("\nKlient: " + orderAfter2.getCustomerName());
        System.out.println("Suma: " + orderAfter2.calculateTotal());
        orderAfter2.sendConfirmation();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceErrorCodeWithException() {
        System.out.println("11. REPLACE ERROR CODE WITH EXCEPTION");
        System.out.println("Wyjątki zamiast kodów błędów\n");
        
        System.out.println("PRZED (kody błędów):");
        ReplaceErrorCodeExample.Before.PaymentProcessor processorBefore = 
            new ReplaceErrorCodeExample.Before.PaymentProcessor();
        int result = processorBefore.processPayment("1234567890123456", 100, 50);
        if (result == ReplaceErrorCodeExample.Before.PaymentProcessor.SUCCESS) {
            System.out.println("Sukces!");
        } else if (result == ReplaceErrorCodeExample.Before.PaymentProcessor.INSUFFICIENT_FUNDS) {
            System.out.println("Błąd: Niewystarczające środki");
        } else {
            System.out.println("Błąd: " + result);
        }
        
        System.out.println("\nPO (wyjątki):");
        ReplaceErrorCodeExample.After.PaymentProcessor processorAfter = 
            new ReplaceErrorCodeExample.After.PaymentProcessor();
        try {
            processorAfter.processPayment("1234567890123456", 100, 150);
            System.out.println("Sukces!");
        } catch (ReplaceErrorCodeExample.After.InsufficientFundsException e) {
            System.out.println("Błąd: " + e.getMessage());
        } catch (ReplaceErrorCodeExample.After.InvalidCardException e) {
            System.out.println("Błąd: " + e.getMessage());
        } catch (ReplaceErrorCodeExample.After.NetworkException e) {
            System.out.println("Błąd: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstratePullUpMethod() {
        System.out.println("12. PULL UP METHOD - Wyciągnięcie metody do nadklasy");
        System.out.println("Wspólna logika w nadklasie eliminuje duplikację\n");
        
        System.out.println("PRZED (zduplikowana logika w każdej klasie):");
        PullUpMethodExample.Before.EmailNotification emailBefore = 
            new PullUpMethodExample.Before.EmailNotification(
                "jan@example.com", "Witaj", "Treść wiadomości");
        emailBefore.send();
        
        System.out.println();
        PullUpMethodExample.Before.SmsNotification smsBefore = 
            new PullUpMethodExample.Before.SmsNotification("123456789", "Witaj!");
        smsBefore.send();
        
        System.out.println("\nPO (wspólna logika w nadklasie):");
        PullUpMethodExample.After.EmailNotification emailAfter = 
            new PullUpMethodExample.After.EmailNotification(
                "jan@example.com", "Witaj", "Treść wiadomości");
        emailAfter.send();
        
        System.out.println();
        PullUpMethodExample.After.SmsNotification smsAfter = 
            new PullUpMethodExample.After.SmsNotification("123456789", "Witaj!");
        smsAfter.send();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceNestedConditional() {
        System.out.println("13. REPLACE NESTED CONDITIONAL WITH GUARD CLAUSES");
        System.out.println("Klauzule strażnicze zamiast zagnieżdżonych warunków\n");
        
        System.out.println("PRZED (głęboko zagnieżdżone warunki):");
        ReplaceNestedConditionalExample.Before.LoanApproval loanBefore = 
            new ReplaceNestedConditionalExample.Before.LoanApproval();
        System.out.println("Maksymalna pożyczka (młody, dobry kredyt): " + 
                         loanBefore.calculateLoanAmount(30, 5000, 750, false));
        System.out.println("Maksymalna pożyczka (za młody): " + 
                         loanBefore.calculateLoanAmount(17, 5000, 750, false));
        
        System.out.println("\nPO (guard clauses):");
        ReplaceNestedConditionalExample.After.LoanApproval loanAfter = 
            new ReplaceNestedConditionalExample.After.LoanApproval();
        System.out.println("Maksymalna pożyczka (młody, dobry kredyt): " + 
                         loanAfter.calculateLoanAmount(30, 5000, 750, false));
        System.out.println("Maksymalna pożyczka (za młody): " + 
                         loanAfter.calculateLoanAmount(17, 5000, 750, false));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateSeparateQueryFromModifier() {
        System.out.println("14. SEPARATE QUERY FROM MODIFIER");
        System.out.println("Oddzielenie zapytań od modyfikatorów (Command-Query Separation)\n");
        
        Map<String, Double> prices = new HashMap<>();
        prices.put("Laptop", 3000.0);
        prices.put("Mysz", 50.0);
        prices.put("Klawiatura", 150.0);
        
        System.out.println("PRZED (metoda modyfikuje i zwraca wartość):");
        SeparateQueryFromModifierExample.Before.ShoppingCart cartBefore = 
            new SeparateQueryFromModifierExample.Before.ShoppingCart();
        cartBefore.addItem("Laptop");
        cartBefore.addItem("Mysz");
        double totalBefore = cartBefore.getTotalAndCheckout(prices);
        System.out.println("Suma (po wyczyszczeniu koszyka): " + totalBefore);
        
        System.out.println("\nPO (oddzielne metody):");
        SeparateQueryFromModifierExample.After.ShoppingCart cartAfter = 
            new SeparateQueryFromModifierExample.After.ShoppingCart();
        cartAfter.addItem("Laptop");
        cartAfter.addItem("Mysz");
        double totalAfter = cartAfter.calculateTotal(prices);
        System.out.println("Suma (koszyk nadal pełny): " + totalAfter);
        System.out.println("Produkty w koszyku: " + cartAfter.getItemCount());
        cartAfter.checkout();
        System.out.println("Produkty po checkout: " + cartAfter.getItemCount());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceConstructorWithFactory() {
        System.out.println("15. REPLACE CONSTRUCTOR WITH FACTORY METHOD");
        System.out.println("Metody fabrykujące zamiast konstruktorów\n");
        
        System.out.println("PRZED (niejasne konstruktory):");
        ReplaceConstructorWithFactoryExample.Before.Employee emp1Before = 
            new ReplaceConstructorWithFactoryExample.Before.Employee("Jan Kowalski", 5000, true);
        System.out.println(emp1Before.getDescription());
        
        System.out.println("\nPO (opisowe metody fabrykujące):");
        ReplaceConstructorWithFactoryExample.After.Employee emp1After = 
            ReplaceConstructorWithFactoryExample.After.Employee.createPermanentEmployee(
                "Jan Kowalski", 5000);
        System.out.println(emp1After.getDescription());
        
        ReplaceConstructorWithFactoryExample.After.Employee emp2After = 
            ReplaceConstructorWithFactoryExample.After.Employee.createContractEmployee(
                "Anna Nowak", 4000, 6);
        System.out.println(emp2After.getDescription());
        
        ReplaceConstructorWithFactoryExample.After.Employee emp3After = 
            ReplaceConstructorWithFactoryExample.After.Employee.createIntern("Piotr Wiśniewski");
        System.out.println(emp3After.getDescription());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateEncapsulateCollection() {
        System.out.println("16. ENCAPSULATE COLLECTION - Enkapsulacja kolekcji");
        System.out.println("Kontrolowany dostęp do kolekcji\n");
        
        System.out.println("PRZED (niebezpieczny bezpośredni dostęp):");
        EncapsulateCollectionExample.Before.Course courseBefore = 
            new EncapsulateCollectionExample.Before.Course("Java Fundamentals");
        List<String> studentsBefore = courseBefore.getStudents();
        studentsBefore.add("Jan Kowalski");
        studentsBefore.add("Anna Nowak");
        System.out.println("Studenci: " + courseBefore.getStudents());
        // Można modyfikować bezpośrednio - niebezpieczne!
        studentsBefore.add("");  // Nieprawidłowa wartość nie zostaje wykryta
        
        System.out.println("\nPO (kontrolowany dostęp):");
        EncapsulateCollectionExample.After.Course courseAfter = 
            new EncapsulateCollectionExample.After.Course("Java Fundamentals");
        courseAfter.addStudent("Jan Kowalski");
        courseAfter.addStudent("Anna Nowak");
        System.out.println("Studenci: " + courseAfter.getStudents());
        System.out.println("Liczba studentów: " + courseAfter.getStudentCount());
        
        try {
            courseAfter.addStudent("");  // Zostanie odrzucone
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd (oczekiwany): " + e.getMessage());
        }
        
        try {
            courseAfter.addStudent("Jan Kowalski");  // Duplikat zostanie odrzucony
        } catch (IllegalArgumentException e) {
            System.out.println("Błąd (oczekiwany): " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceDataValueWithObject() {
        System.out.println("17. REPLACE DATA VALUE WITH OBJECT");
        System.out.println("Dedykowany obiekt zamiast prostych wartości\n");
        
        System.out.println("PRZED (prosty String):");
        ReplaceDataValueExample.Before.Customer customerBefore = 
            new ReplaceDataValueExample.Before.Customer("Jan Kowalski", "123456789");
        System.out.println("Numer: " + customerBefore.formatPhoneNumber());
        System.out.println("Kod: " + customerBefore.getAreaCode());
        
        System.out.println("\nPO (dedykowany obiekt PhoneNumber):");
        ReplaceDataValueExample.After.PhoneNumber phone = 
            new ReplaceDataValueExample.After.PhoneNumber("123", "456", "789");
        ReplaceDataValueExample.After.Customer customerAfter = 
            new ReplaceDataValueExample.After.Customer("Jan Kowalski", phone);
        System.out.println("Numer: " + customerAfter.getPhoneNumber().format());
        System.out.println("Kod: " + customerAfter.getPhoneNumber().getAreaCode());
        System.out.println("Czy lokalny (123): " + customerAfter.getPhoneNumber().isInArea("123"));
        
        try {
            new ReplaceDataValueExample.After.PhoneNumber("12", "456", "789");
        } catch (IllegalArgumentException e) {
            System.out.println("Walidacja działa: " + e.getMessage());
        }
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceAssertion() {
        System.out.println("18. INTRODUCE ASSERTION - Wprowadzenie asercji");
        System.out.println("Jawne dokumentowanie założeń\n");
        
        System.out.println("Uwaga: Asercje są domyślnie wyłączone.");
        System.out.println("Aby je włączyć, uruchom z parametrem: java -ea RefactoringExamples\n");
        
        System.out.println("PRZED (ukryte założenia):");
        IntroduceAssertionExample.Before.ExpenseReport reportBefore = 
            new IntroduceAssertionExample.Before.ExpenseReport(1000);
        double[] expenses = {100, 200, 300};
        System.out.println("Zwrot kosztów: " + reportBefore.calculateReimbursement(expenses));
        
        System.out.println("\nPO (z asercjami):");
        IntroduceAssertionExample.After.ExpenseReport reportAfter = 
            new IntroduceAssertionExample.After.ExpenseReport(1000);
        System.out.println("Zwrot kosztów: " + reportAfter.calculateReimbursement(expenses));
        System.out.println("Asercje dokumentują założenia i pomagają w debugowaniu");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceInheritanceWithDelegation() {
        System.out.println("19. REPLACE INHERITANCE WITH DELEGATION");
        System.out.println("Kompozycja zamiast dziedziczenia\n");
        
        System.out.println("PRZED (niewłaściwe dziedziczenie):");
        ReplaceInheritanceExample.Before.Stack<String> stackBefore = 
            new ReplaceInheritanceExample.Before.Stack<>();
        stackBefore.push("pierwszy");
        stackBefore.push("drugi");
        System.out.println("Pop: " + stackBefore.pop());
        System.out.println("Peek: " + stackBefore.peek());
        // Problem: możemy użyć metod ArrayList które łamią semantykę stosu
        stackBefore.add(0, "zły element");  // Nie powinno być dostępne!
        System.out.println("Rozmiar: " + stackBefore.size());
        
        System.out.println("\nPO (kompozycja):");
        ReplaceInheritanceExample.After.Stack<String> stackAfter = 
            new ReplaceInheritanceExample.After.Stack<>();
        stackAfter.push("pierwszy");
        stackAfter.push("drugi");
        System.out.println("Pop: " + stackAfter.pop());
        System.out.println("Peek: " + stackAfter.peek());
        System.out.println("Rozmiar: " + stackAfter.size());
        // Teraz mamy tylko metody, które mają sens dla stosu
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateConsolidateDuplicateConditional() {
        System.out.println("20. CONSOLIDATE DUPLICATE CONDITIONAL FRAGMENTS");
        System.out.println("Eliminacja duplikacji w gałęziach warunkowych\n");
        
        System.out.println("PRZED (zduplikowany kod w każdej gałęzi):");
        ConsolidateDuplicateConditionalExample.Before.OrderProcessor processorBefore = 
            new ConsolidateDuplicateConditionalExample.Before.OrderProcessor();
        processorBefore.processOrder(true, 100);
        
        System.out.println("\nPO (wspólny kod wyciągnięty):");
        ConsolidateDuplicateConditionalExample.After.OrderProcessor processorAfter = 
            new ConsolidateDuplicateConditionalExample.After.OrderProcessor();
        processorAfter.processOrder(false, 100);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceAlgorithm() {
        System.out.println("21. REPLACE ALGORITHM - Zastąpienie algorytmu");
        System.out.println("Prostszy, czytelniejszy algorytm używający Stream API\n");
        
        List<String> items = Arrays.asList("jabłko", "banan", "jabłko", "wiśnia", 
                                          "banan", "arbuz", "wiśnia");
        
        System.out.println("Dane wejściowe: " + items);
        
        System.out.println("\nPRZED (ręczna implementacja):");
        ReplaceAlgorithmExample.Before.DataAnalyzer analyzerBefore = 
            new ReplaceAlgorithmExample.Before.DataAnalyzer();
        List<String> uniqueSortedBefore = analyzerBefore.findUniqueSorted(items);
        System.out.println("Unikalne posortowane: " + uniqueSortedBefore);
        
        List<Double> numbers = Arrays.asList(10.5, 20.3, 15.7, 30.2);
        System.out.println("Średnia: " + analyzerBefore.calculateAverage(numbers));
        
        System.out.println("\nPO (Stream API):");
        ReplaceAlgorithmExample.After.DataAnalyzer analyzerAfter = 
            new ReplaceAlgorithmExample.After.DataAnalyzer();
        List<String> uniqueSortedAfter = analyzerAfter.findUniqueSorted(items);
        System.out.println("Unikalne posortowane: " + uniqueSortedAfter);
        System.out.println("Średnia: " + analyzerAfter.calculateAverage(numbers));
        
        // Dodatkowe funkcje
        System.out.println("\nDodatkowe możliwości Stream API:");
        List<String> filtered = analyzerAfter.filterAndTransform(items, "b");
        System.out.println("Filtrowane (rozpoczynające się od 'b'): " + filtered);
        
        Map<Integer, List<String>> grouped = analyzerAfter.groupByLength(items);
        System.out.println("Pogrupowane wg długości: " + grouped);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateHideDelegate() {
        System.out.println("22. HIDE DELEGATE - Ukrycie delegata");
        System.out.println("Enkapsulacja struktury obiektów\n");
        
        System.out.println("PRZED (klient musi znać strukturę):");
        HideDelegateExample.Before.Person managerBefore = 
            new HideDelegateExample.Before.Person(null);
        HideDelegateExample.Before.Department deptBefore = 
            new HideDelegateExample.Before.Department("IT", managerBefore);
        HideDelegateExample.Before.Person employeeBefore = 
            new HideDelegateExample.Before.Person(deptBefore);
        
        // Klient musi znać: employee -> department -> manager
        HideDelegateExample.Before.Person mgr = 
            HideDelegateExample.Before.getManager(employeeBefore);
        System.out.println("Dostęp przez: employee.getDepartment().getManager()");
        
        System.out.println("\nPO (Person ukrywa delegację):");
        HideDelegateExample.After.Person managerAfter = 
            new HideDelegateExample.After.Person("Anna Kowalska", null);
        HideDelegateExample.After.Department deptAfter = 
            new HideDelegateExample.After.Department("IT", managerAfter);
        HideDelegateExample.After.Person employeeAfter = 
            new HideDelegateExample.After.Person("Jan Nowak", deptAfter);
        
        // Klient pracuje tylko z Person
        HideDelegateExample.After.Person mgrAfter = 
            HideDelegateExample.After.getManager(employeeAfter);
        System.out.println("Dostęp przez: employee.getManager()");
        System.out.println("Manager: " + mgrAfter.getName());
        System.out.println("Kod działu: " + employeeAfter.getDepartmentCode());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceMethodWithMethodObject() {
        System.out.println("23. REPLACE METHOD WITH METHOD OBJECT");
        System.out.println("Obiekt metody dla złożonych obliczeń\n");
        
        System.out.println("PRZED (długa metoda z wieloma zmiennymi lokalnymi):");
        ReplaceMethodWithMethodObjectExample.Before.PriceCalculator calcBefore = 
            new ReplaceMethodWithMethodObjectExample.Before.PriceCalculator();
        double priceBefore = calcBefore.calculatePrice(10, 100, 2, 200, true);
        System.out.println("Cena: " + priceBefore);
        
        System.out.println("\nPO (obiekt metody z małymi, czytelnymi metodami):");
        ReplaceMethodWithMethodObjectExample.After.PriceCalculator calcAfter = 
            new ReplaceMethodWithMethodObjectExample.After.PriceCalculator();
        double priceAfter = calcAfter.calculatePrice(10, 100, 2, 200, true);
        System.out.println("Cena: " + priceAfter);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstratePreserveWholeObject() {
        System.out.println("24. PRESERVE WHOLE OBJECT");
        System.out.println("Przekazywanie całego obiektu zamiast pojedynczych wartości\n");
        
        System.out.println("PRZED (przekazywanie min i max osobno):");
        PreserveWholeObjectExample.Before.Temperature tempBefore = 
            new PreserveWholeObjectExample.Before.Temperature(12, 22);
        PreserveWholeObjectExample.Before.HeatingPlan planBefore = 
            new PreserveWholeObjectExample.Before.HeatingPlan();
        System.out.println("W zakresie: " + 
            planBefore.withinRange(tempBefore.getMin(), tempBefore.getMax()));
        System.out.println("Rekomendacja: " + 
            planBefore.getRecommendation(tempBefore.getMin(), tempBefore.getMax()));
        
        System.out.println("\nPO (przekazywanie całego obiektu):");
        PreserveWholeObjectExample.After.Temperature tempAfter = 
            new PreserveWholeObjectExample.After.Temperature(12, 22, 
                LocalDate.now(), "Warszawa");
        PreserveWholeObjectExample.After.HeatingPlan planAfter = 
            new PreserveWholeObjectExample.After.HeatingPlan();
        System.out.println("W zakresie: " + planAfter.withinRange(tempAfter));
        System.out.println("Rekomendacja: " + planAfter.getRecommendation(tempAfter));
        System.out.println("Szczegóły: " + planAfter.getDetailedReport(tempAfter));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceArrayWithObject() {
        System.out.println("25. REPLACE ARRAY WITH OBJECT");
        System.out.println("Dedykowany obiekt zamiast tablicy z magicznymi indeksami\n");
        
        System.out.println("PRZED (tablica z magicznymi indeksami):");
        ReplaceArrayWithObjectExample.Before.PerformanceTracker trackerBefore = 
            new ReplaceArrayWithObjectExample.Before.PerformanceTracker();
        String[] stats = trackerBefore.getPlayerStats("Jan Kowalski", 45, 15);
        trackerBefore.printStats(stats);
        System.out.println("Współczynnik wygranych: " + trackerBefore.calculateWinRate(stats) + "%");
        
        System.out.println("\nPO (dedykowany obiekt PlayerStats):");
        ReplaceArrayWithObjectExample.After.PlayerStats playerStats = 
            new ReplaceArrayWithObjectExample.After.PlayerStats("Jan Kowalski", 45, 15);
        ReplaceArrayWithObjectExample.After.PerformanceTracker trackerAfter = 
            new ReplaceArrayWithObjectExample.After.PerformanceTracker();
        trackerAfter.printStats(playerStats);
        System.out.println("ToString: " + playerStats.toString());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceForeignMethod() {
        System.out.println("26. INTRODUCE FOREIGN METHOD");
        System.out.println("Dodanie funkcjonalności do klasy, której nie możemy modyfikować\n");
        
        System.out.println("PRZED (zduplikowana logika w wielu miejscach):");
        IntroduceForeignMethodExample.Before.ReportGenerator generatorBefore = 
            new IntroduceForeignMethodExample.Before.ReportGenerator();
        generatorBefore.generateReport();
        generatorBefore.scheduleFollowUp();
        
        System.out.println("\nPO (metoda obca w klasie pomocniczej):");
        IntroduceForeignMethodExample.After.ReportGenerator generatorAfter = 
            new IntroduceForeignMethodExample.After.ReportGenerator();
        generatorAfter.generateReport();
        generatorAfter.scheduleFollowUp();
        generatorAfter.scheduleReviewIn5Days();
        
        System.out.println("\nDodatkowe metody pomocnicze:");
        LocalDate today = LocalDate.now();
        System.out.println("Dziś: " + today + 
            " (dzień roboczy: " + IntroduceForeignMethodExample.After.DateUtils.isWorkingDay(today) + ")");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateFormTemplateMethod() {
        System.out.println("27. FORM TEMPLATE METHOD - Wzorzec Template Method");
        System.out.println("Wspólny algorytm w nadklasie, szczegóły w podklasach\n");
        
        List<String> data = Arrays.asList("Sprzedaż Q1: 100K", "Sprzedaż Q2: 150K", "Sprzedaż Q3: 120K");
        
        System.out.println("PRZED (duplikacja algorytmu w każdej klasie):");
        FormTemplateMethodExample.Before.HtmlReport htmlBefore = 
            new FormTemplateMethodExample.Before.HtmlReport();
        htmlBefore.generate("Raport roczny", data);
        
        System.out.println("\n" + "-".repeat(70) + "\n");
        
        FormTemplateMethodExample.Before.CsvReport csvBefore = 
            new FormTemplateMethodExample.Before.CsvReport();
        csvBefore.generate("Raport roczny", data);
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PO (wzorzec Template Method):\n");
        
        FormTemplateMethodExample.After.Report htmlAfter = 
            new FormTemplateMethodExample.After.HtmlReport();
        htmlAfter.generate("Raport roczny", data);
        
        System.out.println("\n" + "-".repeat(70) + "\n");
        
        FormTemplateMethodExample.After.Report csvAfter = 
            new FormTemplateMethodExample.After.CsvReport();
        csvAfter.generate("Raport roczny", data);
        
        System.out.println("\n" + "-".repeat(70) + "\n");
        
        FormTemplateMethodExample.After.Report jsonAfter = 
            new FormTemplateMethodExample.After.JsonReport();
        jsonAfter.generate("Raport roczny", data);
        
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceParameterWithMethodCall() {
        System.out.println("28. REPLACE PARAMETER WITH METHOD CALL");
        System.out.println("Metoda sama oblicza potrzebne wartości\n");
        
        System.out.println("PRZED (niepotrzebne parametry):");
        ReplaceParameterWithMethodCallExample.Before.Order orderBefore = 
            new ReplaceParameterWithMethodCallExample.Before.Order(120, 50);
        System.out.println("Cena: " + orderBefore.getPrice());
        
        System.out.println("\nPO (metoda sama oblicza wartości):");
        ReplaceParameterWithMethodCallExample.After.Order orderAfter = 
            new ReplaceParameterWithMethodCallExample.After.Order(120, 50);
        System.out.println("Cena: " + orderAfter.getPrice());
        System.out.println("Szczegóły: " + orderAfter.getPriceBreakdown());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateParameterizeMethod() {
        System.out.println("29. PARAMETERIZE METHOD - Sparametryzowanie metody");
        System.out.println("Jedna metoda z parametrem zamiast wielu podobnych metod\n");
        
        System.out.println("PRZED (osobne metody dla każdej wartości):");
        ParameterizeMethodExample.Before.Employee empBefore = 
            new ParameterizeMethodExample.Before.Employee(5000);
        System.out.println("Pensja początkowa: " + empBefore.getBaseSalary());
        empBefore.tenPercentRaise();
        System.out.println("Po 10% podwyżce: " + empBefore.getBaseSalary());
        
        ParameterizeMethodExample.Before.Product productBefore = 
            new ParameterizeMethodExample.Before.Product();
        System.out.println("Cena z 10% rabatem: " + productBefore.priceWithTenPercentDiscount());
        
        System.out.println("\nPO (jedna sparametryzowana metoda):");
        ParameterizeMethodExample.After.Employee empAfter = 
            new ParameterizeMethodExample.After.Employee(5000);
        System.out.println("Pensja początkowa: " + empAfter.getBaseSalary());
        empAfter.raise(10.0);
        System.out.println("Po 10% podwyżce: " + empAfter.getBaseSalary());
        empAfter.raise(7.5);
        System.out.println("Po kolejnej 7.5% podwyżce: " + empAfter.getBaseSalary());
        
        ParameterizeMethodExample.After.Product productAfter = 
            new ParameterizeMethodExample.After.Product();
        System.out.println("Cena z 10% rabatem: " + productAfter.priceWithDiscount(10));
        System.out.println("Cena z 25% rabatem: " + productAfter.priceWithDiscount(25));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceExceptionWithTest() {
        System.out.println("30. REPLACE EXCEPTION WITH TEST");
        System.out.println("Testy zamiast wyjątków w normalnym przepływie\n");
        
        System.out.println("PRZED (wyjątki do kontroli przepływu):");
        ReplaceExceptionWithTestExample.Before.ResourcePool poolBefore = 
            new ReplaceExceptionWithTestExample.Before.ResourcePool();
        System.out.println("Zasób 1: " + poolBefore.getResource());
        System.out.println("Zasób 2: " + poolBefore.getResource());
        System.out.println("Zasób 3: " + poolBefore.getResource());
        System.out.println("Zasób 4 (nowy): " + poolBefore.getResource());
        
        System.out.println("\nPO (testy przed wywołaniem):");
        ReplaceExceptionWithTestExample.After.ResourcePool poolAfter = 
            new ReplaceExceptionWithTestExample.After.ResourcePool();
        System.out.println("Dostępne zasoby: " + poolAfter.getAvailableCount());
        System.out.println("Zasób 1: " + poolAfter.getResource());
        System.out.println("Zasób 2: " + poolAfter.getResource());
        System.out.println("Dostępne zasoby: " + poolAfter.getAvailableCount());
        
        System.out.println("\nPrzykład z kontem bankowym:");
        ReplaceExceptionWithTestExample.After.BankAccount account = 
            new ReplaceExceptionWithTestExample.After.BankAccount(1000);
        System.out.println("Saldo: " + account.getBalance());
        
        if (account.canWithdraw(500)) {
            account.withdraw(500);
            System.out.println("Wypłacono 500, nowe saldo: " + account.getBalance());
        }
        
        boolean success = account.tryWithdraw(600);
        System.out.println("Próba wypłaty 600: " + (success ? "sukces" : "niewystarczające środki"));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceGateway() {
        System.out.println("31. INTRODUCE GATEWAY - Wprowadzenie bramy");
        System.out.println("Izolacja zewnętrznych zależności przez bramy\n");
        
        System.out.println("PRZED (bezpośrednie wywołania zewnętrznych serwisów):");
        IntroduceGatewayExample.Before.OrderService serviceBefore = 
            new IntroduceGatewayExample.Before.OrderService();
        serviceBefore.processOrder("ORD-001", 299.99, "customer@example.com");
        
        System.out.println("\nPO (bramy izolujące zależności):");
        IntroduceGatewayExample.After.PaymentGateway paymentGateway = 
            new IntroduceGatewayExample.After.ExternalPaymentGateway();
        IntroduceGatewayExample.After.EmailGateway emailGateway = 
            new IntroduceGatewayExample.After.SmtpEmailGateway();
        IntroduceGatewayExample.After.OrderRepository orderRepository = 
            new IntroduceGatewayExample.After.DatabaseOrderRepository();
        
        IntroduceGatewayExample.After.OrderService serviceAfter = 
            new IntroduceGatewayExample.After.OrderService(
                paymentGateway, emailGateway, orderRepository);
        serviceAfter.processOrder("ORD-002", 299.99, "customer@example.com");
        System.out.println("\nZalety: łatwe testowanie, możliwość podmiany implementacji");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateExtractInterface() {
        System.out.println("32. EXTRACT INTERFACE - Wydzielenie interfejsu");
        System.out.println("Interfejs definiuje kontrakt, umożliwia różne implementacje\n");
        
        System.out.println("PRZED (zależność od konkretnej klasy):");
        ExtractInterfaceExample.Before.EmailNotificationService notifBefore = 
            new ExtractInterfaceExample.Before.EmailNotificationService();
        ExtractInterfaceExample.Before.UserService userServiceBefore = 
            new ExtractInterfaceExample.Before.UserService(notifBefore);
        userServiceBefore.registerUser("jan@example.com", "Jan Kowalski");
        
        System.out.println("\nPO (zależność od abstrakcji - różne implementacje):");
        
        System.out.println("\nImplementacja Email:");
        ExtractInterfaceExample.After.NotificationService emailService = 
            new ExtractInterfaceExample.After.EmailNotificationService();
        ExtractInterfaceExample.After.UserService userService1 = 
            new ExtractInterfaceExample.After.UserService(emailService);
        userService1.registerUser("jan@example.com", "Jan Kowalski");
        
        System.out.println("\nImplementacja SMS:");
        ExtractInterfaceExample.After.NotificationService smsService = 
            new ExtractInterfaceExample.After.SmsNotificationService();
        ExtractInterfaceExample.After.UserService userService2 = 
            new ExtractInterfaceExample.After.UserService(smsService);
        userService2.registerUser("123456789", "Anna Nowak");
        
        System.out.println("\nImplementacja Push:");
        ExtractInterfaceExample.After.NotificationService pushService = 
            new ExtractInterfaceExample.After.PushNotificationService();
        ExtractInterfaceExample.After.UserService userService3 = 
            new ExtractInterfaceExample.After.UserService(pushService);
        userService3.registerUser("device-token-123", "Piotr Wiśniewski");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateCollapseHierarchy() {
        System.out.println("33. COLLAPSE HIERARCHY - Zwinięcie hierarchii");
        System.out.println("Usunięcie niepotrzebnej hierarchii klas\n");
        
        System.out.println("PRZED (niepotrzebna hierarchia):");
        CollapseHierarchyExample.Before.Dog dogBefore = 
            new CollapseHierarchyExample.Before.Dog("Burek");
        System.out.println("Nazwa: " + dogBefore.getName());
        dogBefore.makeSound();
        
        System.out.println("\nPO (jedna klasa z parametrem):");
        CollapseHierarchyExample.After.Animal dog = 
            CollapseHierarchyExample.After.Animal.createDog("Burek");
        System.out.println("Nazwa: " + dog.getName());
        dog.makeSound();
        
        CollapseHierarchyExample.After.Animal cat = 
            CollapseHierarchyExample.After.Animal.createCat("Mruczek");
        cat.makeSound();
        
        CollapseHierarchyExample.After.Animal bird = 
            CollapseHierarchyExample.After.Animal.createBird("Tweety");
        bird.makeSound();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateInlineClass() {
        System.out.println("34. INLINE CLASS - Wciągnięcie klasy");
        System.out.println("Scalenie klasy która robi zbyt mało\n");
        
        System.out.println("PRZED (osobna klasa TelephoneNumber):");
        InlineClassExample.Before.TelephoneNumber phoneBefore = 
            new InlineClassExample.Before.TelephoneNumber("22", "123-4567");
        InlineClassExample.Before.Person personBefore = 
            new InlineClassExample.Before.Person("Jan Kowalski", phoneBefore);
        System.out.println("Osoba: " + personBefore.getName());
        System.out.println("Telefon: " + personBefore.getTelephoneNumber());
        System.out.println("Kod: " + personBefore.getAreaCode());
        
        System.out.println("\nPO (TelephoneNumber scalony z Person):");
        InlineClassExample.After.Person personAfter = 
            new InlineClassExample.After.Person("Jan Kowalski", "22", "123-4567");
        System.out.println("Osoba: " + personAfter.getName());
        System.out.println("Telefon: " + personAfter.getTelephoneNumber());
        System.out.println("Kod: " + personAfter.getAreaCode());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceRecordWithDataClass() {
        System.out.println("35. REPLACE RECORD WITH DATA CLASS");
        System.out.println("Klasa danych zamiast struktury (Map/Array)\n");
        
        System.out.println("PRZED (Map jako struktura danych):");
        ReplaceRecordWithDataClassExample.Before.EmployeeRegistry registryBefore = 
            new ReplaceRecordWithDataClassExample.Before.EmployeeRegistry();
        registryBefore.addEmployee("E001", "Jan Kowalski", 30, 5000);
        registryBefore.addEmployee("E002", "Anna Nowak", 28, 6000);
        registryBefore.printEmployee(0);
        System.out.println("Suma pensji: " + registryBefore.calculateTotalSalary());
        
        System.out.println("\nPO (właściwa klasa Employee):");
        ReplaceRecordWithDataClassExample.After.EmployeeRegistry registryAfter = 
            new ReplaceRecordWithDataClassExample.After.EmployeeRegistry();
        
        ReplaceRecordWithDataClassExample.After.Employee emp1 = 
            new ReplaceRecordWithDataClassExample.After.Employee("E001", "Jan Kowalski", 30, 5000);
        ReplaceRecordWithDataClassExample.After.Employee emp2 = 
            new ReplaceRecordWithDataClassExample.After.Employee("E002", "Anna Nowak", 28, 60000);
        
        registryAfter.addEmployee(emp1);
        registryAfter.addEmployee(emp2);
        
        registryAfter.printEmployee(0);
        System.out.println("Suma pensji: " + registryAfter.calculateTotalSalary());
        
        System.out.println("\nDodatkowe funkcje:");
        emp1.giveRaise(10);
        System.out.println("Po podwyżce: " + emp1);
        System.out.println("Uprawnienie do bonusu: " + emp1.isEligibleForBonus());
        
        List<ReplaceRecordWithDataClassExample.After.Employee> highEarners = 
            registryAfter.getHighEarners(50000);
        System.out.println("Wysokozarabiający (>50k): " + highEarners.size() + " pracowników");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceExplainingVariable() {
        System.out.println("36. INTRODUCE EXPLAINING VARIABLE");
        System.out.println("Zmienne wyjaśniające dla skomplikowanych wyrażeń\n");
        
        System.out.println("PRZED (skomplikowane wyrażenia):");
        IntroduceExplainingVariableExample.Before.OrderCalculator calcBefore = 
            new IntroduceExplainingVariableExample.Before.OrderCalculator();
        double totalBefore = calcBefore.calculateTotal(10, 100, "mobile", true);
        System.out.println("Suma: " + totalBefore);
        boolean expressBefore = calcBefore.shouldApplyExpressShipping(15, 50, "premium", 80);
        System.out.println("Express shipping: " + expressBefore);
        
        System.out.println("\nPO (zmienne wyjaśniające):");
        IntroduceExplainingVariableExample.After.OrderCalculator calcAfter = 
            new IntroduceExplainingVariableExample.After.OrderCalculator();
        double totalAfter = calcAfter.calculateTotal(10, 100, "mobile", true);
        System.out.println("Suma: " + totalAfter);
        boolean expressAfter = calcAfter.shouldApplyExpressShipping(15, 50, "premium", 80);
        System.out.println("Express shipping: " + expressAfter);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateSplitTemporaryVariable() {
        System.out.println("37. SPLIT TEMPORARY VARIABLE");
        System.out.println("Osobna zmienna dla każdego celu\n");
        
        System.out.println("PRZED (jedna zmienna do różnych celów):");
        SplitTemporaryVariableExample.Before.GeometryCalculator geoBefore = 
            new SplitTemporaryVariableExample.Before.GeometryCalculator();
        double resultBefore = geoBefore.calculateArea(5, 12);
        System.out.println("Wynik końcowy (przekątna): " + resultBefore);
        
        System.out.println("\nPO (osobna zmienna dla każdego celu):");
        SplitTemporaryVariableExample.After.GeometryCalculator geoAfter = 
            new SplitTemporaryVariableExample.After.GeometryCalculator();
        double diagonal = geoAfter.calculateDiagonal(5, 12);
        System.out.println("Przekątna: " + diagonal);
        System.out.println("Pole: " + geoAfter.calculateArea(5, 12));
        System.out.println("Obwód: " + geoAfter.calculatePerimeter(5, 12));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateRemoveAssignmentsToParameters() {
        System.out.println("38. REMOVE ASSIGNMENTS TO PARAMETERS");
        System.out.println("Lokalne zmienne zamiast modyfikacji parametrów\n");
        
        System.out.println("PRZED (modyfikacja parametrów):");
        RemoveAssignmentsToParametersExample.Before.DiscountCalculator calcBefore = 
            new RemoveAssignmentsToParametersExample.Before.DiscountCalculator();
        System.out.println("Cena po rabacie: " + calcBefore.applyDiscount(100, 0.15));
        System.out.println("Podatek: " + calcBefore.calculateTax(10000));
        
        RemoveAssignmentsToParametersExample.Before.StringProcessor procBefore = 
            new RemoveAssignmentsToParametersExample.Before.StringProcessor();
        System.out.println("Przetworzony tekst: '" + procBefore.process("  HELLO  ") + "'");
        
        System.out.println("\nPO (lokalne zmienne):");
        RemoveAssignmentsToParametersExample.After.DiscountCalculator calcAfter = 
            new RemoveAssignmentsToParametersExample.After.DiscountCalculator();
        System.out.println("Cena po rabacie: " + calcAfter.applyDiscount(100, 0.15));
        System.out.println("Podatek: " + calcAfter.calculateTax(10000));
        
        RemoveAssignmentsToParametersExample.After.StringProcessor procAfter = 
            new RemoveAssignmentsToParametersExample.After.StringProcessor();
        System.out.println("Przetworzony tekst: '" + procAfter.process("  HELLO  ") + "'");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceMethodWithMethodChain() {
        System.out.println("39. REPLACE METHOD WITH METHOD CHAIN (Fluent Interface)");
        System.out.println("Płynne API z łańcuchem wywołań\n");
        
        System.out.println("PRZED (osobne wywołania metod):");
        ReplaceMethodWithMethodChainExample.Before.QueryBuilder builderBefore = 
            new ReplaceMethodWithMethodChainExample.Before.QueryBuilder();
        builderBefore.setTable("users");
        builderBefore.addColumn("name");
        builderBefore.addColumn("email");
        builderBefore.setWhere("age > 18");
        builderBefore.setOrderBy("name");
        builderBefore.setLimit(10);
        System.out.println(builderBefore.build());
        
        System.out.println("\nPO (fluent interface):");
        String query = new ReplaceMethodWithMethodChainExample.After.QueryBuilder()
            .from("users")
            .select("name", "email")
            .where("age > 18")
            .orderBy("name")
            .limit(10)
            .build();
        System.out.println(query);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceLocalExtension() {
        System.out.println("40. INTRODUCE LOCAL EXTENSION");
        System.out.println("Rozszerzenie klasy której nie możemy modyfikować\n");
        
        System.out.println("PRZED (metody pomocnicze rozproszone):");
        IntroduceLocalExtensionExample.Before.ReportGenerator genBefore = 
            new IntroduceLocalExtensionExample.Before.ReportGenerator();
        genBefore.generateReport();
        
        System.out.println("\nPO (rozszerzona klasa EnhancedDate):");
        IntroduceLocalExtensionExample.After.ReportGenerator genAfter = 
            new IntroduceLocalExtensionExample.After.ReportGenerator();
        genAfter.generateReport();
        
        System.out.println("\nDodatkowe możliwości EnhancedDate:");
        IntroduceLocalExtensionExample.After.EnhancedDate date = 
            IntroduceLocalExtensionExample.After.EnhancedDate.of(2025, 3, 31);
        System.out.println("Data: " + date);
        System.out.println("Weekend: " + date.isWeekend());
        System.out.println("Koniec kwartału: " + date.isQuarterEnd());
        System.out.println("Kwartał: " + date.getQuarter());
        
        IntroduceLocalExtensionExample.After.EnhancedDate futureDate = 
            date.addBusinessDays(5);
        System.out.println("Za 5 dni roboczych: " + futureDate);
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateSelfEncapsulateField() {
        System.out.println("41. SELF ENCAPSULATE FIELD");
        System.out.println("Dostęp przez gettery/settery nawet w tej samej klasie\n");
        
        System.out.println("PRZED (bezpośredni dostęp do pól):");
        SelfEncapsulateFieldExample.Before.Rectangle rectBefore = 
            new SelfEncapsulateFieldExample.Before.Rectangle(10, 5);
        System.out.println("Pole: " + rectBefore.getArea());
        System.out.println("Obwód: " + rectBefore.getPerimeter());
        rectBefore.resize(2);
        System.out.println("Po powiększeniu x2 - Pole: " + rectBefore.getArea());
        
        System.out.println("\nPO (dostęp przez gettery/settery z walidacją):");
        SelfEncapsulateFieldExample.After.Rectangle rectAfter = 
            new SelfEncapsulateFieldExample.After.Rectangle(10, 5);
        System.out.println("Pole: " + rectAfter.getArea());
        System.out.println("Obwód: " + rectAfter.getPerimeter());
        rectAfter.resize(2);
        System.out.println("Po powiększeniu x2 - Pole: " + rectAfter.getArea());
        
        System.out.println("\nKwadrat (podklasa z własną logiką):");
        SelfEncapsulateFieldExample.After.Square square = 
            new SelfEncapsulateFieldExample.After.Square(10);
        System.out.println("Pole kwadratu: " + square.getArea());
        square.resize(1.5);
        System.out.println("Po powiększeniu - Pole: " + square.getArea());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceSubclassWithFields() {
        System.out.println("42. REPLACE SUBCLASS WITH FIELDS");
        System.out.println("Jedna klasa z polami zamiast podklas\n");
        
        System.out.println("PRZED (osobne podklasy):");
        ReplaceSubclassWithFieldsExample.Before.Person maleBefore = 
            new ReplaceSubclassWithFieldsExample.Before.Male();
        System.out.println("Kod: " + maleBefore.getCode() + ", Tytuł: " + maleBefore.getTitle());
        
        ReplaceSubclassWithFieldsExample.Before.Person femaleBefore = 
            new ReplaceSubclassWithFieldsExample.Before.Female();
        System.out.println("Kod: " + femaleBefore.getCode() + ", Tytuł: " + femaleBefore.getTitle());
        
        System.out.println("\nPO (jedna klasa z factory methods):");
        ReplaceSubclassWithFieldsExample.After.Person maleAfter = 
            ReplaceSubclassWithFieldsExample.After.Person.createMale();
        System.out.println("Kod: " + maleAfter.getCode() + ", Tytuł: " + maleAfter.getTitle());
        
        ReplaceSubclassWithFieldsExample.After.Person femaleAfter = 
            ReplaceSubclassWithFieldsExample.After.Person.createFemale();
        System.out.println("Kod: " + femaleAfter.getCode() + ", Tytuł: " + femaleAfter.getTitle());
        
        ReplaceSubclassWithFieldsExample.After.Person nonBinary = 
            ReplaceSubclassWithFieldsExample.After.Person.createNonBinary();
        System.out.println("Kod: " + nonBinary.getCode() + ", Tytuł: " + nonBinary.getTitle());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateLazyInitialization() {
        System.out.println("43. LAZY INITIALIZATION - Leniwa inicjalizacja");
        System.out.println("Opóźnienie kosztownych operacji do momentu użycia\n");
        
        System.out.println("PRZED (natychmiastowa inicjalizacja):");
        System.out.println("Tworzenie serwisu...");
        LazyInitializationExample.Before.DataService serviceBefore = 
            new LazyInitializationExample.Before.DataService();
        System.out.println("Serwis utworzony (dane załadowane od razu)");
        
        System.out.println("\nPO (leniwa inicjalizacja):");
        System.out.println("Tworzenie serwisu...");
        LazyInitializationExample.After.DataService serviceAfter = 
            new LazyInitializationExample.After.DataService();
        System.out.println("Serwis utworzony (dane NIE załadowane)");
        System.out.println("Teraz pobieramy dane...");
        List<String> data = serviceAfter.getData();
        System.out.println("Dane: " + data);
        System.out.println("Kolejne pobranie (z cache): " + serviceAfter.getData());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceLoopWithPipeline() {
        System.out.println("44. REPLACE LOOP WITH PIPELINE");
        System.out.println("Deklaratywne pipeline'y zamiast imperatywnych pętli\n");
        
        List<ReplaceLoopWithPipelineExample.Before.Order> ordersBefore = Arrays.asList(
            new ReplaceLoopWithPipelineExample.Before.Order("Jan", 100, "COMPLETED"),
            new ReplaceLoopWithPipelineExample.Before.Order("Anna", 200, "COMPLETED"),
            new ReplaceLoopWithPipelineExample.Before.Order("Jan", 150, "PENDING"),
            new ReplaceLoopWithPipelineExample.Before.Order("Piotr", 300, "COMPLETED")
        );
        
        System.out.println("PRZED (imperatywne pętle):");
        ReplaceLoopWithPipelineExample.Before.OrderProcessor procBefore = 
            new ReplaceLoopWithPipelineExample.Before.OrderProcessor();
        System.out.println("Total revenue: " + procBefore.calculateTotalRevenue(ordersBefore));
        System.out.println("Customers (>100): " + procBefore.getCustomerNames(ordersBefore, 100));
        System.out.println("Orders by status: " + procBefore.countOrdersByStatus(ordersBefore));
        
        List<ReplaceLoopWithPipelineExample.After.Order> ordersAfter = Arrays.asList(
            new ReplaceLoopWithPipelineExample.After.Order("Jan", 100, "COMPLETED"),
            new ReplaceLoopWithPipelineExample.After.Order("Anna", 200, "COMPLETED"),
            new ReplaceLoopWithPipelineExample.After.Order("Jan", 150, "PENDING"),
            new ReplaceLoopWithPipelineExample.After.Order("Piotr", 300, "COMPLETED")
        );
        
        System.out.println("\nPO (deklaratywne pipeline'y):");
        ReplaceLoopWithPipelineExample.After.OrderProcessor procAfter = 
            new ReplaceLoopWithPipelineExample.After.OrderProcessor();
        System.out.println("Total revenue: " + procAfter.calculateTotalRevenue(ordersAfter));
        System.out.println("Customers (>100): " + procAfter.getCustomerNames(ordersAfter, 100));
        System.out.println("Orders by status: " + procAfter.countOrdersByStatus(ordersAfter));
        System.out.println("Average order: " + procAfter.getAverageOrderValue(ordersAfter));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateRemoveDeadCode() {
        System.out.println("45. REMOVE DEAD CODE - Usunięcie martwego kodu");
        System.out.println("Kod który nie jest używany powinien zostać usunięty\n");
        
        System.out.println("PRZED (z martwym kodem, zakomentowanym kodem, nieużywanymi metodami):");
        System.out.println("Klasa Before.UserService ma 7 metod (3 nieużywane)");
        RemoveDeadCodeExample.Before.UserService serviceBefore = 
            new RemoveDeadCodeExample.Before.UserService();
        serviceBefore.processUser("USER-123");
        
        System.out.println("\nPO (tylko aktywny kod):");
        System.out.println("Klasa After.UserService ma 3 metody (wszystkie używane)");
        RemoveDeadCodeExample.After.UserService serviceAfter = 
            new RemoveDeadCodeExample.After.UserService();
        serviceAfter.processUser("USER-123");
        System.out.println("\nKorzyści: łatwiejsza konserwacja, brak mylących fragmentów");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceSpecialCase() {
        System.out.println("46. INTRODUCE SPECIAL CASE - Przypadek specjalny");
        System.out.println("Special Case Pattern zamiast sprawdzania null\n");
        
        System.out.println("PRZED (ciągłe sprawdzanie null):");
        IntroduceSpecialCaseExample.Before.BillingService billingBefore = 
            new IntroduceSpecialCaseExample.Before.BillingService();
        IntroduceSpecialCaseExample.Before.Customer customer = 
            new IntroduceSpecialCaseExample.Before.Customer("Jan Kowalski", "PREMIUM", 500);
        System.out.println("Rachunek dla klienta: " + billingBefore.calculateBill(customer));
        System.out.println("Info: " + billingBefore.getCustomerInfo(customer));
        System.out.println("Rachunek dla null: " + billingBefore.calculateBill(null));
        System.out.println("Info dla null: " + billingBefore.getCustomerInfo(null));
        
        System.out.println("\nPO (Special Case Pattern):");
        IntroduceSpecialCaseExample.After.BillingService billingAfter = 
            new IntroduceSpecialCaseExample.After.BillingService();
        IntroduceSpecialCaseExample.After.Customer realCustomer = 
            new IntroduceSpecialCaseExample.After.RealCustomer("Jan Kowalski", "PREMIUM", 500);
        System.out.println("Rachunek dla klienta: " + billingAfter.calculateBill(realCustomer));
        System.out.println("Info: " + billingAfter.getCustomerInfo(realCustomer));
        System.out.println("Rachunek dla null: " + billingAfter.calculateBill(null));
        System.out.println("Info dla null: " + billingAfter.getCustomerInfo(null));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateCombineFunctionsIntoClass() {
        System.out.println("47. COMBINE FUNCTIONS INTO CLASS");
        System.out.println("Połączenie powiązanych funkcji w klasę\n");
        
        System.out.println("PRZED (statyczne funkcje):");
        double monthlyBefore = CombineFunctionsIntoClassExample.Before.MortgageCalculator
            .calculateMonthlyPayment(300000, 3.5, 30);
        System.out.println("Miesięczna płatność: " + String.format("%.2f", monthlyBefore));
        double totalBefore = CombineFunctionsIntoClassExample.Before.MortgageCalculator
            .calculateTotalPayment(300000, 3.5, 30);
        System.out.println("Całkowita płatność: " + String.format("%.2f", totalBefore));
        
        System.out.println("\nPO (klasa grupująca dane i obliczenia):");
        CombineFunctionsIntoClassExample.After.Mortgage mortgage = 
            new CombineFunctionsIntoClassExample.After.Mortgage(300000, 3.5, 30);
        System.out.println(mortgage.getSummary());
        System.out.println("\nPo 5 latach (60 miesięcy):");
        System.out.println("Pozostała kwota: " + 
            String.format("%.2f", mortgage.getRemainingBalance(60)));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateSplitPhase() {
        System.out.println("48. SPLIT PHASE - Podział na fazy");
        System.out.println("Wyraźne wydzielenie sekwencyjnych faz przetwarzania\n");
        
        System.out.println("PRZED (wszystko zmieszane w jednej metodzie):");
        SplitPhaseExample.Before.OrderProcessor processorBefore = 
            new SplitPhaseExample.Before.OrderProcessor();
        processorBefore.processOrder("PROD-123,5,CUST-456");
        
        System.out.println("\nPO (wyraźne fazy: parse -> validate -> calculate -> save):");
        SplitPhaseExample.After.OrderProcessor processorAfter = 
            new SplitPhaseExample.After.OrderProcessor();
        processorAfter.processOrder("PROD-123,5,CUST-456");
        System.out.println("\nKorzyści: łatwiejsze testowanie każdej fazy osobno");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplacePrimitiveWithObject() {
        System.out.println("49. REPLACE PRIMITIVE WITH OBJECT");
        System.out.println("Dedykowany obiekt zamiast prymitywu\n");
        
        System.out.println("PRZED (String jako priorytet):");
        ReplacePrimitiveWithObjectExample.Before.Order orderBefore = 
            new ReplacePrimitiveWithObjectExample.Before.Order("high");
        System.out.println("Priorytet: " + orderBefore.getPriorityDisplay());
        System.out.println("Rush order: " + orderBefore.isRush());
        System.out.println("Dni dostawy: " + orderBefore.getDeliveryDays());
        
        System.out.println("\nPO (obiekt Priority z zachowaniami):");
        ReplacePrimitiveWithObjectExample.After.Order orderAfter = 
            new ReplacePrimitiveWithObjectExample.After.Order(
                ReplacePrimitiveWithObjectExample.After.Priority.high());
        System.out.println("Priorytet: " + orderAfter.getPriorityDisplay());
        System.out.println("Rush order: " + orderAfter.isRush());
        System.out.println("Dni dostawy: " + orderAfter.getDeliveryDays());
        System.out.println("Koszt wysyłki (podstawa 100): " + 
            orderAfter.calculateShippingCost(100));
        
        System.out.println("\nPorównanie priorytetów:");
        ReplacePrimitiveWithObjectExample.After.Priority high = 
            ReplacePrimitiveWithObjectExample.After.Priority.high();
        ReplacePrimitiveWithObjectExample.After.Priority low = 
            ReplacePrimitiveWithObjectExample.After.Priority.low();
        System.out.println("High > Low: " + high.higherThan(low));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateSlideStatements() {
        System.out.println("50. SLIDE STATEMENTS - Przesunięcie instrukcji");
        System.out.println("Grupowanie powiązanych instrukcji razem\n");
        
        System.out.println("PRZED (rozproszone instrukcje):");
        SlideStatementsExample.Before.UserProfileBuilder builderBefore = 
            new SlideStatementsExample.Before.UserProfileBuilder();
        SlideStatementsExample.Before.UserProfile profileBefore = 
            builderBefore.buildProfile("USER-123");
        System.out.println("Profil: " + profileBefore.displayName + ", " + profileBefore.email);
        
        System.out.println("\nPO (zgrupowane logicznie):");
        SlideStatementsExample.After.UserProfileBuilder builderAfter = 
            new SlideStatementsExample.After.UserProfileBuilder();
        SlideStatementsExample.After.UserProfile profileAfter = 
            builderAfter.buildProfile("USER-123");
        System.out.println("Profil: " + profileAfter.displayName + ", " + profileAfter.email);
        System.out.println("Korzyści: łatwiej zrozumieć co się dzieje w każdej sekcji");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceDerivedVariableWithQuery() {
        System.out.println("51. REPLACE DERIVED VARIABLE WITH QUERY");
        System.out.println("Obliczanie na żądanie zamiast przechowywania zmiennej pochodnej\n");
        
        System.out.println("PRZED (zmienna pochodna może być nieaktualna):");
        ReplaceDerivedVariableWithQueryExample.Before.ShoppingCart cartBefore = 
            new ReplaceDerivedVariableWithQueryExample.Before.ShoppingCart();
        cartBefore.addItem("Laptop", 3000, 1);
        cartBefore.addItem("Mouse", 50, 2);
        System.out.println("Total: " + cartBefore.getTotalPrice());
        cartBefore.updateQuantity(1, 3);
        System.out.println("Po aktualizacji: " + cartBefore.getTotalPrice());
        
        System.out.println("\nPO (obliczanie na żądanie - zawsze aktualne):");
        ReplaceDerivedVariableWithQueryExample.After.ShoppingCart cartAfter = 
            new ReplaceDerivedVariableWithQueryExample.After.ShoppingCart();
        cartAfter.addItem("Laptop", 3000, 1);
        cartAfter.addItem("Mouse", 50, 2);
        System.out.println("Total: " + cartAfter.getTotalPrice());
        System.out.println("Item count: " + cartAfter.getItemCount());
        cartAfter.updateQuantity(1, 3);
        System.out.println("Po aktualizacji: " + cartAfter.getTotalPrice());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateChangeReferenceToValue() {
        System.out.println("52. CHANGE REFERENCE TO VALUE");
        System.out.println("Obiekt niemutowalny (wartość) zamiast mutowalnego (referencja)\n");
        
        System.out.println("PRZED (obiekt mutowalny):");
        ChangeReferenceToValueExample.Before.Person personBefore = 
            new ChangeReferenceToValueExample.Before.Person("22", "123-4567");
        System.out.println("Telefon: " + personBefore.getTelephoneNumber());
        ChangeReferenceToValueExample.Before.TelephoneNumber phone = personBefore.getOfficeNumber();
        phone.setAreaCode("48");  // Modyfikacja zewnętrzna!
        System.out.println("Po zewnętrznej modyfikacji: " + personBefore.getTelephoneNumber());
        
        System.out.println("\nPO (obiekt niemutowalny):");
        ChangeReferenceToValueExample.After.Person personAfter = 
            new ChangeReferenceToValueExample.After.Person("22", "123-4567");
        System.out.println("Telefon: " + personAfter.getTelephoneNumber());
        personAfter.setTelephoneNumber("48", "987-6543");
        System.out.println("Po zmianie: " + personAfter.getTelephoneNumber());
        
        ChangeReferenceToValueExample.After.TelephoneNumber phone2 = 
            personAfter.getOfficeNumber().withAreaCode("11");
        System.out.println("Nowy telefon (immutable): " + phone2);
        System.out.println("Oryginalny niezmieniony: " + personAfter.getTelephoneNumber());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateChangeValueToReference() {
        System.out.println("53. CHANGE VALUE TO REFERENCE");
        System.out.println("Współdzielone instancje zamiast kopii\n");
        
        System.out.println("PRZED (każde zamówienie ma własną kopię klienta):");
        ChangeValueToReferenceExample.Before.Order order1Before = 
            new ChangeValueToReferenceExample.Before.Order("John Doe", "john@example.com", 100);
        ChangeValueToReferenceExample.Before.Order order2Before = 
            new ChangeValueToReferenceExample.Before.Order("John Doe", "john@example.com", 200);
        System.out.println("Order 1: " + order1Before.getCustomerName());
        System.out.println("Order 2: " + order2Before.getCustomerName());
        System.out.println("Problem: dwie kopie tego samego klienta!");
        
        System.out.println("\nPO (współdzielone instancje):");
        ChangeValueToReferenceExample.After.CustomerRepository repo = 
            new ChangeValueToReferenceExample.After.CustomerRepository();
        ChangeValueToReferenceExample.After.Customer customer = repo.getCustomer("CUST-001");
        ChangeValueToReferenceExample.After.Order order1After = 
            new ChangeValueToReferenceExample.After.Order(customer, 100);
        ChangeValueToReferenceExample.After.Order order2After = 
            new ChangeValueToReferenceExample.After.Order(customer, 200);
        
        System.out.println("Order 1: " + order1After.getCustomerName() + " - " + order1After.getCustomerEmail());
        System.out.println("Order 2: " + order2After.getCustomerName() + " - " + order2After.getCustomerEmail());
        
        repo.updateCustomerEmail("CUST-001", "newemail@example.com");
        System.out.println("\nPo aktualizacji emaila:");
        System.out.println("Order 1: " + order1After.getCustomerEmail());
        System.out.println("Order 2: " + order2After.getCustomerEmail());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateRemoveSettingMethod() {
        System.out.println("54. REMOVE SETTING METHOD");
        System.out.println("Usunięcie setterów dla niemutowalnych pól\n");
        
        System.out.println("PRZED (settery dla wszystkiego):");
        RemoveSettingMethodExample.Before.Account accountBefore = 
            new RemoveSettingMethodExample.Before.Account();
        accountBefore.setId("ACC-001");
        accountBefore.setOwner("Jan Kowalski");
        accountBefore.setCreatedDate(LocalDate.now());
        System.out.println("ID: " + accountBefore.getId());
        accountBefore.setId("ACC-002");  // ID nie powinno się zmieniać!
        System.out.println("ID po zmianie: " + accountBefore.getId());
        
        System.out.println("\nPO (tylko gettery dla niemutowalnych pól):");
        RemoveSettingMethodExample.After.Account accountAfter = 
            RemoveSettingMethodExample.After.Account.create("ACC-001", "Jan Kowalski");
        System.out.println("ID: " + accountAfter.getId());
        System.out.println("Created: " + accountAfter.getCreatedDate());
        System.out.println("Owner: " + accountAfter.getOwner());
        accountAfter.setOwner("Anna Nowak");
        System.out.println("Owner po zmianie: " + accountAfter.getOwner());
        // accountAfter.setId("ACC-002");  // Nie skompiluje się!
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceCommandWithFunction() {
        System.out.println("55. REPLACE COMMAND WITH FUNCTION");
        System.out.println("Prosta funkcja zamiast Command pattern gdy nie potrzebny\n");
        
        System.out.println("PRZED (Command pattern gdzie niepotrzebny):");
        ReplaceCommandWithFunctionExample.Before.CalculatePriceCommand cmd1 = 
            new ReplaceCommandWithFunctionExample.Before.CalculatePriceCommand(100, 0.1);
        cmd1.execute();
        System.out.println("Price: " + cmd1.getResult());
        
        ReplaceCommandWithFunctionExample.Before.SendEmailCommand cmd2 = 
            new ReplaceCommandWithFunctionExample.Before.SendEmailCommand(
                "user@example.com", "Hello");
        cmd2.execute();
        
        System.out.println("\nPO (proste funkcje):");
        double price = ReplaceCommandWithFunctionExample.After.PriceCalculator
            .calculatePrice(100, 0.1);
        System.out.println("Price: " + price);
        
        ReplaceCommandWithFunctionExample.After.EmailService
            .sendEmail("user@example.com", "Hello");
        
        System.out.println("\nCommand pattern gdy RZECZYWIŚCIE potrzebny (undo/redo):");
        ReplaceCommandWithFunctionExample.After.TransferMoneyCommand transferCmd = 
            new ReplaceCommandWithFunctionExample.After.TransferMoneyCommand(
                "ACC-001", "ACC-002", 1000);
        transferCmd.execute();
        transferCmd.undo();
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReturnModifiedValue() {
        System.out.println("56. RETURN MODIFIED VALUE");
        System.out.println("Zwracanie zmodyfikowanej wartości zamiast mutacji\n");
        
        System.out.println("PRZED (modyfikacja przez efekt uboczny):");
        ReturnModifiedValueExample.Before.Order orderBefore = 
            new ReturnModifiedValueExample.Before.Order(1200, true);
        System.out.println("Base price: " + orderBefore.getBasePrice());
        ReturnModifiedValueExample.Before.PriceProcessor processorBefore = 
            new ReturnModifiedValueExample.Before.PriceProcessor();
        processorBefore.applyDiscounts(orderBefore);
        System.out.println("Final price: " + orderBefore.getPrice());
        
        System.out.println("\nPO (zwracanie wartości - niemutowalność):");
        ReturnModifiedValueExample.After.Order orderAfter = 
            new ReturnModifiedValueExample.After.Order(1200, true);
        System.out.println("Base price: " + orderAfter.getBasePrice());
        ReturnModifiedValueExample.After.PriceProcessor processorAfter = 
            new ReturnModifiedValueExample.After.PriceProcessor();
        double finalPrice = processorAfter.applyDiscounts(orderAfter);
        System.out.println("Final price: " + finalPrice);
        System.out.println("Base price unchanged: " + orderAfter.getBasePrice());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceConstructorWithBuilder() {
        System.out.println("57. REPLACE CONSTRUCTOR WITH BUILDER");
        System.out.println("Wzorzec Builder dla obiektów z wieloma parametrami\n");
        
        System.out.println("PRZED (długi konstruktor):");
        ReplaceConstructorWithBuilderExample.Before.EmailMessage emailBefore = 
            new ReplaceConstructorWithBuilderExample.Before.EmailMessage(
                "sender@example.com", "recipient@example.com", 
                "Subject", "Body", null, null, true, 1);
        System.out.println("Utworzono email (trudna kolejność parametrów)");
        
        System.out.println("\nPO (wzorzec Builder):");
        ReplaceConstructorWithBuilderExample.After.EmailMessage emailAfter = 
            ReplaceConstructorWithBuilderExample.After.EmailMessage.builder()
                .from("sender@example.com")
                .to("recipient@example.com")
                .subject("Subject")
                .body("Body")
                .html(true)
                .priority(1)
                .build();
        System.out.println("Utworzono email: " + emailAfter);
        System.out.println("Czytelne, elastyczne, z wartościami domyślnymi");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateEncapsulateDowncast() {
        System.out.println("58. ENCAPSULATE DOWNCAST");
        System.out.println("Ukrycie rzutowania w metodach\n");
        
        System.out.println("PRZED (klient musi rzutować):");
        EncapsulateDowncastExample.Before.Library libBefore = 
            new EncapsulateDowncastExample.Before.Library();
        libBefore.addBook("Java", "Author");
        libBefore.addMagazine("Tech Weekly", 42);
        
        Object item = libBefore.getItem(0);
        if (item instanceof EncapsulateDowncastExample.Before.Book) {
            EncapsulateDowncastExample.Before.Book book = 
                (EncapsulateDowncastExample.Before.Book) item;
            System.out.println("Książka: " + book.title + " by " + book.author);
        }
        
        System.out.println("\nPO (bez rzutowania):");
        EncapsulateDowncastExample.After.Library libAfter = 
            new EncapsulateDowncastExample.After.Library();
        libAfter.addBook("Java", "Author");
        libAfter.addMagazine("Tech Weekly", 42);
        
        EncapsulateDowncastExample.After.LibraryItem item2 = libAfter.getItem(0);
        System.out.println("Element: " + item2.getDescription());
        
        List<EncapsulateDowncastExample.After.Book> books = libAfter.getBooks();
        System.out.println("Książki: " + books.size());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceTypeCodeWithStateStrategy() {
        System.out.println("59. REPLACE TYPE CODE WITH STATE/STRATEGY");
        System.out.println("Wzorzec State zamiast kodu typu\n");
        
        System.out.println("PRZED (String jako stan z warunkami):");
        ReplaceTypeCodeWithStateStrategyExample.Before.Document docBefore = 
            new ReplaceTypeCodeWithStateStrategyExample.Before.Document("Content");
        System.out.println("Status: " + docBefore.getStatus());
        System.out.println("Can edit: " + docBefore.canEdit());
        docBefore.publish();
        System.out.println("Can edit after publish: " + docBefore.canEdit());
        docBefore.publish();
        
        System.out.println("\nPO (wzorzec State):");
        ReplaceTypeCodeWithStateStrategyExample.After.Document docAfter = 
            new ReplaceTypeCodeWithStateStrategyExample.After.Document("Content");
        System.out.println("Status: " + docAfter.getStatus());
        System.out.println("Current state: " + docAfter.getCurrentStateName());
        System.out.println("Can edit: " + docAfter.canEdit());
        docAfter.publish();
        System.out.println("State: " + docAfter.getCurrentStateName());
        System.out.println("Can edit: " + docAfter.canEdit());
        docAfter.publish();
        System.out.println("State: " + docAfter.getCurrentStateName());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroduceParameterObjectWithBuilder() {
        System.out.println("60. INTRODUCE PARAMETER OBJECT WITH BUILDER");
        System.out.println("Połączenie Parameter Object z wzorcem Builder\n");
        
        System.out.println("PO refaktoryzacji (Parameter Object + Builder):");
        IntroduceParameterObjectWithBuilderExample.After.ReportConfig config = 
            IntroduceParameterObjectWithBuilderExample.After.ReportConfig
                .builder("Q4 Report", LocalDate.of(2025, 10, 1), LocalDate.of(2025, 12, 31))
                .format("PDF")
                .charts(true)
                .details(false)
                .author("Jan Kowalski")
                .department("Sales")
                .maxRows(500)
                .sortBy("revenue")
                .build();
        
        IntroduceParameterObjectWithBuilderExample.After.ReportGenerator generator = 
            new IntroduceParameterObjectWithBuilderExample.After.ReportGenerator();
        generator.generateReport(config);
        System.out.println("\nKorzyści: czytelność + elastyczność + wartości domyślne");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceLoopWithRecursion() {
        System.out.println("61. REPLACE LOOP WITH RECURSION");
        System.out.println("Rozwiązanie rekurencyjne zamiast iteracyjnego\n");
        
        // Budowa drzewa
        ReplaceLoopWithRecursionExample.Before.TreeNode rootBefore = 
            new ReplaceLoopWithRecursionExample.Before.TreeNode(1);
        ReplaceLoopWithRecursionExample.Before.TreeNode child1 = 
            new ReplaceLoopWithRecursionExample.Before.TreeNode(2);
        ReplaceLoopWithRecursionExample.Before.TreeNode child2 = 
            new ReplaceLoopWithRecursionExample.Before.TreeNode(3);
        rootBefore.addChild(child1);
        rootBefore.addChild(child2);
        child1.addChild(new ReplaceLoopWithRecursionExample.Before.TreeNode(4));
        child1.addChild(new ReplaceLoopWithRecursionExample.Before.TreeNode(5));
        
        System.out.println("PRZED (iteracyjnie z kolejką):");
        ReplaceLoopWithRecursionExample.Before.TreeProcessor procBefore = 
            new ReplaceLoopWithRecursionExample.Before.TreeProcessor();
        System.out.println("Suma: " + procBefore.calculateSum(rootBefore));
        System.out.println("Max: " + procBefore.findMax(rootBefore));
        
        ReplaceLoopWithRecursionExample.After.TreeNode rootAfter = 
            new ReplaceLoopWithRecursionExample.After.TreeNode(1);
        ReplaceLoopWithRecursionExample.After.TreeNode child1a = 
            new ReplaceLoopWithRecursionExample.After.TreeNode(2);
        ReplaceLoopWithRecursionExample.After.TreeNode child2a = 
            new ReplaceLoopWithRecursionExample.After.TreeNode(3);
        rootAfter.addChild(child1a);
        rootAfter.addChild(child2a);
        child1a.addChild(new ReplaceLoopWithRecursionExample.After.TreeNode(4));
        child1a.addChild(new ReplaceLoopWithRecursionExample.After.TreeNode(5));
        
        System.out.println("\nPO (rekurencyjnie):");
        ReplaceLoopWithRecursionExample.After.TreeProcessor procAfter = 
            new ReplaceLoopWithRecursionExample.After.TreeProcessor();
        System.out.println("Suma: " + procAfter.calculateSum(rootAfter));
        System.out.println("Max: " + procAfter.findMax(rootAfter));
        System.out.println("Liczba węzłów: " + procAfter.countNodes(rootAfter));
        System.out.println("Wysokość: " + procAfter.getHeight(rootAfter));
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateIntroducePolymorphicCreation() {
        System.out.println("62. INTRODUCE POLYMORPHIC CREATION");
        System.out.println("Polimorficzne tworzenie - każda klasa wie jak się stworzyć\n");
        
        System.out.println("PRZED (zewnętrzna fabryka):");
        ReplaceLoopWithRecursionExample.Before.TreeNode node = 
            new ReplaceLoopWithRecursionExample.Before.TreeNode(10);
        System.out.println("Utworzono węzeł przez konstruktor");
        
        System.out.println("\nPO (polimorficzne tworzenie):");
        IntroducePolymorphicCreationExample.After.Circle circle = 
            new IntroducePolymorphicCreationExample.After.Circle(5.0);
        System.out.println("Pole koła: " + circle.area());
        
        IntroducePolymorphicCreationExample.After.Circle newCircle = circle.withRadius(10.0);
        System.out.println("Nowe koło: " + newCircle.area());
        
        IntroducePolymorphicCreationExample.After.Shape similarCircle = circle.createSimilar(7.0);
        System.out.println("Podobne koło: " + similarCircle.area());
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    private static void demonstrateReplaceCallbackWithPromise() {
        System.out.println("63. REPLACE CALLBACK WITH PROMISE (CompletableFuture)");
        System.out.println("CompletableFuture zamiast callback hell\n");
        
        System.out.println("PRZED (callback hell):");
        ReplaceCallbackWithPromiseExample.Before.DataService serviceBefore = 
            new ReplaceCallbackWithPromiseExample.Before.DataService();
        
        serviceBefore.fetchUser("USER-123", new ReplaceCallbackWithPromiseExample.Before.Callback<String>() {
            @Override
            public void onSuccess(String user) {
                System.out.println("Fetched user: " + user);
                serviceBefore.fetchOrders(user, new ReplaceCallbackWithPromiseExample.Before.Callback<List<String>>() {
                    @Override
                    public void onSuccess(List<String> orders) {
                        System.out.println("Fetched orders: " + orders);
                        serviceBefore.processOrders(orders, new ReplaceCallbackWithPromiseExample.Before.Callback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                System.out.println("Result: " + result);
                            }
                            
                            @Override
                            public void onError(Exception error) {
                                System.err.println("Error: " + error.getMessage());
                            }
                        });
                    }
                    
                    @Override
                    public void onError(Exception error) {
                        System.err.println("Error: " + error.getMessage());
                    }
                });
            }
            
            @Override
            public void onError(Exception error) {
                System.err.println("Error: " + error.getMessage());
            }
        });
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nPO (CompletableFuture - płynne łańcuchowanie):");
        ReplaceCallbackWithPromiseExample.After.DataService serviceAfter = 
            new ReplaceCallbackWithPromiseExample.After.DataService();
        
        serviceAfter.processUserOrders("USER-123")
            .thenAccept(result -> System.out.println("Result: " + result))
            .exceptionally(error -> {
                System.err.println("Error: " + error.getMessage());
                return null;
            });
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
}