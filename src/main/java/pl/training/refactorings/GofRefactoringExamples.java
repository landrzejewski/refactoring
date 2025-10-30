package pl.training.refactorings;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GofRefactoringExamples {

    public static void main(String[] args) {
        demonstrateSingletonPattern();
        demonstrateFactoryMethodPattern();
        demonstrateStrategyPattern();
        demonstrateObserverPattern();
        demonstrateDecoratorPattern();
        demonstrateBuilderPattern();
        demonstrateAdapterPattern();
        demonstrateFacadePattern();
        demonstrateTemplateMethodPattern();
        demonstrateCommandPattern();
        demonstrateChainOfResponsibilityPattern();
        demonstrateProxyPattern();
        demonstrateCompositePattern();
        demonstrateStatePattern();
        demonstrateAbstractFactoryPattern();
        demonstratePrototypePattern();
        demonstrateCompositePattern();
        demonstrateStatePattern();
        demonstrateAbstractFactoryPattern();
        demonstratePrototypePattern();
        demonstrateBridgePattern();
        demonstrateFlyweightPattern();
        demonstrateIteratorPattern();
        demonstrateMediatorPattern();
        demonstrateMementoPattern();
        demonstrateVisitorPattern();
        demonstrateInterpreterPattern();
    }

    // ========================================
    // 1. SINGLETON PATTERN
    // ========================================

    /**
     * PRZED: Problematyczna implementacja Singleton
     * Problemy:
     * - Nie jest thread-safe
     * - Może powstać wiele instancji w środowisku wielowątkowym
     * - Brak lazy initialization
     */
    static class DatabaseConnectionBefore {
        private static DatabaseConnectionBefore instance;

        private DatabaseConnectionBefore() {
            System.out.println("Tworzenie połączenia z bazą danych...");
        }

        // PROBLEM: Nie jest thread-safe!
        public static DatabaseConnectionBefore getInstance() {
            if (instance == null) {
                instance = new DatabaseConnectionBefore();
            }
            return instance;
        }

        public void executeQuery(String query) {
            System.out.println("Wykonuję zapytanie: " + query);
        }
    }

    /**
     * PO: Poprawna implementacja Singleton z thread-safety
     * Zastosowanie: Bill Pugh Singleton (Initialization-on-demand holder idiom)
     * Zalety:
     * - Thread-safe bez synchronizacji
     * - Lazy initialization
     * - Brak narzutu wydajnościowego
     */
    static class DatabaseConnectionAfter {
        private DatabaseConnectionAfter() {
            System.out.println("Tworzenie połączenia z bazą danych (thread-safe)...");
        }

        // Wewnętrzna klasa nie jest ładowana dopóki nie zostanie wywołana getInstance()
        private static class SingletonHolder {
            private static final DatabaseConnectionAfter INSTANCE = new DatabaseConnectionAfter();
        }

        public static DatabaseConnectionAfter getInstance() {
            return SingletonHolder.INSTANCE;
        }

        public void executeQuery(String query) {
            System.out.println("Wykonuję zapytanie (thread-safe): " + query);
        }
    }

    private static void demonstrateSingletonPattern() {
        System.out.println("\n=== SINGLETON PATTERN ===");
        DatabaseConnectionAfter db1 = DatabaseConnectionAfter.getInstance();
        DatabaseConnectionAfter db2 = DatabaseConnectionAfter.getInstance();
        System.out.println("Czy to ta sama instancja? " + (db1 == db2));
        db1.executeQuery("SELECT * FROM users");
    }

    // ========================================
    // 2. FACTORY METHOD PATTERN
    // ========================================

    /**
     * PRZED: Tworzenie obiektów bezpośrednio w kodzie klienta
     * Problemy:
     * - Naruszenie Open/Closed Principle
     * - Trudność w dodawaniu nowych typów
     * - Logika tworzenia rozproszona po całym kodzie
     */
    static class DocumentProcessorBefore {
        public void processDocument(String type, String content) {
            // PROBLEM: Logika if-else do tworzenia obiektów
            if (type.equals("PDF")) {
                PdfDocumentBefore pdf = new PdfDocumentBefore(content);
                pdf.open();
                pdf.render();
            } else if (type.equals("WORD")) {
                WordDocumentBefore word = new WordDocumentBefore(content);
                word.open();
                word.render();
            } else if (type.equals("HTML")) {
                HtmlDocumentBefore html = new HtmlDocumentBefore(content);
                html.open();
                html.render();
            }
        }
    }

    static class PdfDocumentBefore {
        private String content;

        public PdfDocumentBefore(String content) { this.content = content; }
        public void open() { System.out.println("Otwieranie PDF..."); }
        public void render() { System.out.println("Renderowanie PDF: " + content); }
    }

    static class WordDocumentBefore {
        private String content;

        public WordDocumentBefore(String content) { this.content = content; }
        public void open() { System.out.println("Otwieranie Word..."); }
        public void render() { System.out.println("Renderowanie Word: " + content); }
    }

    static class HtmlDocumentBefore {
        private String content;

        public HtmlDocumentBefore(String content) { this.content = content; }
        public void open() { System.out.println("Otwieranie HTML..."); }
        public void render() { System.out.println("Renderowanie HTML: " + content); }
    }

    /**
     * PO: Factory Method Pattern
     * Zalety:
     * - Centralizacja logiki tworzenia obiektów
     * - Łatwe dodawanie nowych typów bez modyfikacji istniejącego kodu
     * - Zgodność z Open/Closed Principle
     */
    interface Document {
        void open();
        void render();
        void close();
    }

    static class PdfDocument implements Document {
        private final String content;

        public PdfDocument(String content) { this.content = content; }

        @Override
        public void open() { System.out.println("Otwieranie PDF dokumentu..."); }

        @Override
        public void render() {
            System.out.println("Renderowanie PDF z zawartością: " + content);
        }

        @Override
        public void close() { System.out.println("Zamykanie PDF..."); }
    }

    static class WordDocument implements Document {
        private final String content;

        public WordDocument(String content) { this.content = content; }

        @Override
        public void open() { System.out.println("Otwieranie Word dokumentu..."); }

        @Override
        public void render() {
            System.out.println("Renderowanie Word z zawartością: " + content);
        }

        @Override
        public void close() { System.out.println("Zamykanie Word..."); }
    }

    static class HtmlDocument implements Document {
        private final String content;

        public HtmlDocument(String content) { this.content = content; }

        @Override
        public void open() { System.out.println("Otwieranie HTML dokumentu..."); }

        @Override
        public void render() {
            System.out.println("Renderowanie HTML z zawartością: " + content);
        }

        @Override
        public void close() { System.out.println("Zamykanie HTML..."); }
    }

    // Abstract Factory definiujący factory method
    static abstract class DocumentFactory {
        // Factory Method - podklasy decydują, jaki typ dokumentu utworzyć
        protected abstract Document createDocument(String content);

        // Template Method wykorzystujący Factory Method
        public final void processDocument(String content) {
            Document doc = createDocument(content);
            doc.open();
            doc.render();
            doc.close();
        }
    }

    static class PdfDocumentFactory extends DocumentFactory {
        @Override
        protected Document createDocument(String content) {
            return new PdfDocument(content);
        }
    }

    static class WordDocumentFactory extends DocumentFactory {
        @Override
        protected Document createDocument(String content) {
            return new WordDocument(content);
        }
    }

    static class HtmlDocumentFactory extends DocumentFactory {
        @Override
        protected Document createDocument(String content) {
            return new HtmlDocument(content);
        }
    }

    private static void demonstrateFactoryMethodPattern() {
        System.out.println("\n=== FACTORY METHOD PATTERN ===");
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        pdfFactory.processDocument("Raport roczny 2025");

        DocumentFactory wordFactory = new WordDocumentFactory();
        wordFactory.processDocument("Dokument biznesowy");
    }

    // ========================================
    // 3. STRATEGY PATTERN
    // ========================================

    /**
     * PRZED: Algorytmy zakodowane na sztywno w klasie
     * Problemy:
     * - Naruszenie Single Responsibility Principle
     * - Trudność w dodawaniu nowych algorytmów sortowania
     * - Niemożność zmiany algorytmu w runtime
     */
    static class DataSorterBefore {
        public void sortData(List<Integer> data, String algorithm) {
            // PROBLEM: Cała logika sortowania w jednej klasie
            if (algorithm.equals("QUICK")) {
                System.out.println("Sortowanie QuickSort");
                // Implementacja QuickSort
                quickSort(data, 0, data.size() - 1);
            } else if (algorithm.equals("MERGE")) {
                System.out.println("Sortowanie MergeSort");
                // Implementacja MergeSort
                mergeSort(data);
            } else if (algorithm.equals("BUBBLE")) {
                System.out.println("Sortowanie BubbleSort");
                // Implementacja BubbleSort
                bubbleSort(data);
            }
        }

        private void quickSort(List<Integer> data, int low, int high) {
            // Uproszczona implementacja
            Collections.sort(data);
        }

        private void mergeSort(List<Integer> data) {
            Collections.sort(data);
        }

        private void bubbleSort(List<Integer> data) {
            Collections.sort(data);
        }
    }

    /**
     * PO: Strategy Pattern
     * Zalety:
     * - Każdy algorytm w osobnej klasie (SRP)
     * - Łatwe dodawanie nowych strategii
     * - Możliwość zmiany strategii w runtime
     * - Eliminacja instrukcji warunkowych
     */
    interface SortingStrategy {
        void sort(List<Integer> data);
        String getAlgorithmName();
    }

    static class QuickSortStrategy implements SortingStrategy {
        @Override
        public void sort(List<Integer> data) {
            System.out.println("Wykonuję QuickSort...");
            // Rzeczywista implementacja QuickSort
            quickSortImpl(data, 0, data.size() - 1);
        }

        @Override
        public String getAlgorithmName() {
            return "QuickSort";
        }

        private void quickSortImpl(List<Integer> data, int low, int high) {
            if (low < high) {
                int pi = partition(data, low, high);
                quickSortImpl(data, low, pi - 1);
                quickSortImpl(data, pi + 1, high);
            }
        }

        private int partition(List<Integer> data, int low, int high) {
            int pivot = data.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (data.get(j) < pivot) {
                    i++;
                    Collections.swap(data, i, j);
                }
            }
            Collections.swap(data, i + 1, high);
            return i + 1;
        }
    }

    static class MergeSortStrategy implements SortingStrategy {
        @Override
        public void sort(List<Integer> data) {
            System.out.println("Wykonuję MergeSort...");
            if (data.size() > 1) {
                mergeSortImpl(data, 0, data.size() - 1);
            }
        }

        @Override
        public String getAlgorithmName() {
            return "MergeSort";
        }

        private void mergeSortImpl(List<Integer> data, int left, int right) {
            if (left < right) {
                int mid = (left + right) / 2;
                mergeSortImpl(data, left, mid);
                mergeSortImpl(data, mid + 1, right);
                merge(data, left, mid, right);
            }
        }

        private void merge(List<Integer> data, int left, int mid, int right) {
            List<Integer> leftList = new ArrayList<>(data.subList(left, mid + 1));
            List<Integer> rightList = new ArrayList<>(data.subList(mid + 1, right + 1));

            int i = 0, j = 0, k = left;

            while (i < leftList.size() && j < rightList.size()) {
                if (leftList.get(i) <= rightList.get(j)) {
                    data.set(k++, leftList.get(i++));
                } else {
                    data.set(k++, rightList.get(j++));
                }
            }

            while (i < leftList.size()) {
                data.set(k++, leftList.get(i++));
            }

            while (j < rightList.size()) {
                data.set(k++, rightList.get(j++));
            }
        }
    }

    static class BubbleSortStrategy implements SortingStrategy {
        @Override
        public void sort(List<Integer> data) {
            System.out.println("Wykonuję BubbleSort...");
            int n = data.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (data.get(j) > data.get(j + 1)) {
                        Collections.swap(data, j, j + 1);
                    }
                }
            }
        }

        @Override
        public String getAlgorithmName() {
            return "BubbleSort";
        }
    }

    static class DataSorterAfter {
        private SortingStrategy strategy;

        public DataSorterAfter(SortingStrategy strategy) {
            this.strategy = strategy;
        }

        // Możliwość zmiany strategii w runtime
        public void setStrategy(SortingStrategy strategy) {
            this.strategy = strategy;
        }

        public void sortData(List<Integer> data) {
            System.out.println("Używam strategii: " + strategy.getAlgorithmName());
            strategy.sort(data);
        }
    }

    private static void demonstrateStrategyPattern() {
        System.out.println("\n=== STRATEGY PATTERN ===");
        List<Integer> data = new ArrayList<>(Arrays.asList(64, 34, 25, 12, 22, 11, 90));

        DataSorterAfter sorter = new DataSorterAfter(new QuickSortStrategy());
        List<Integer> data1 = new ArrayList<>(data);
        sorter.sortData(data1);
        System.out.println("Posortowane dane: " + data1);

        // Zmiana strategii w runtime
        sorter.setStrategy(new MergeSortStrategy());
        List<Integer> data2 = new ArrayList<>(data);
        sorter.sortData(data2);
    }

    // ========================================
    // 4. OBSERVER PATTERN
    // ========================================

    /**
     * PRZED: Ścisłe powiązanie między podmiotem a obserwatorami
     * Problemy:
     * - Klasy są silnie sprzężone
     * - Trudność w dodawaniu nowych obserwatorów
     * - Naruszenie Dependency Inversion Principle
     */
    static class StockMarketBefore {
        private double stockPrice;
        private EmailNotifierBefore emailNotifier;
        private SMSNotifierBefore smsNotifier;
        private DashboardBefore dashboard;

        public StockMarketBefore() {
            // PROBLEM: Tworzenie konkretnych zależności
            this.emailNotifier = new EmailNotifierBefore();
            this.smsNotifier = new SMSNotifierBefore();
            this.dashboard = new DashboardBefore();
        }

        public void setStockPrice(double price) {
            this.stockPrice = price;
            // PROBLEM: Bezpośrednie wywoływanie metod konkretnych klas
            emailNotifier.sendEmail("Nowa cena akcji: " + price);
            smsNotifier.sendSMS("Nowa cena akcji: " + price);
            dashboard.updateDisplay("Cena: " + price);
        }
    }

    static class EmailNotifierBefore {
        public void sendEmail(String message) {
            System.out.println("Email: " + message);
        }
    }

    static class SMSNotifierBefore {
        public void sendSMS(String message) {
            System.out.println("SMS: " + message);
        }
    }

    static class DashboardBefore {
        public void updateDisplay(String message) {
            System.out.println("Dashboard: " + message);
        }
    }

    /**
     * PO: Observer Pattern
     * Zalety:
     * - Luźne powiązanie między Subject a Observer
     * - Łatwe dodawanie/usuwanie obserwatorów dynamicznie
     * - Zgodność z Open/Closed Principle
     * - Możliwość wielu obserwatorów nasłuchujących ten sam podmiot
     */
    interface StockObserver {
        void update(String stockSymbol, double price, double changePercent);
    }

    interface StockSubject {
        void attach(StockObserver observer);
        void detach(StockObserver observer);
        void notifyObservers();
    }

    static class StockMarketAfter implements StockSubject {
        private final List<StockObserver> observers = new ArrayList<>();
        private final Map<String, Double> stockPrices = new ConcurrentHashMap<>();
        private final Map<String, Double> previousPrices = new ConcurrentHashMap<>();

        @Override
        public void attach(StockObserver observer) {
            observers.add(observer);
            System.out.println("Dodano obserwatora: " + observer.getClass().getSimpleName());
        }

        @Override
        public void detach(StockObserver observer) {
            observers.remove(observer);
            System.out.println("Usunięto obserwatora: " + observer.getClass().getSimpleName());
        }

        @Override
        public void notifyObservers() {
            for (StockObserver observer : observers) {
                stockPrices.forEach((symbol, price) -> {
                    double previousPrice = previousPrices.getOrDefault(symbol, price);
                    double changePercent = ((price - previousPrice) / previousPrice) * 100;
                    observer.update(symbol, price, changePercent);
                });
            }
        }

        public void setStockPrice(String symbol, double price) {
            previousPrices.put(symbol, stockPrices.getOrDefault(symbol, price));
            stockPrices.put(symbol, price);
            notifyObservers();
        }
    }

    static class EmailNotifierObserver implements StockObserver {
        private final String emailAddress;

        public EmailNotifierObserver(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        @Override
        public void update(String stockSymbol, double price, double changePercent) {
            if (Math.abs(changePercent) > 5) {
                System.out.printf("📧 Email do %s: Uwaga! Akcje %s zmieniły się o %.2f%% (cena: %.2f)%n",
                        emailAddress, stockSymbol, changePercent, price);
            }
        }
    }

    static class SMSNotifierObserver implements StockObserver {
        private final String phoneNumber;

        public SMSNotifierObserver(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void update(String stockSymbol, double price, double changePercent) {
            if (Math.abs(changePercent) > 10) {
                System.out.printf("📱 SMS na %s: Alarm! %s zmiana %.2f%% (%.2f)%n",
                        phoneNumber, stockSymbol, changePercent, price);
            }
        }
    }

    static class DashboardObserver implements StockObserver {
        @Override
        public void update(String stockSymbol, double price, double changePercent) {
            String trend = changePercent > 0 ? "📈" : "📉";
            System.out.printf("🖥️  Dashboard: %s %s %.2f (zmiana: %.2f%%)%n",
                    trend, stockSymbol, price, changePercent);
        }
    }

    private static void demonstrateObserverPattern() {
        System.out.println("\n=== OBSERVER PATTERN ===");
        StockMarketAfter market = new StockMarketAfter();

        // Dynamiczne dodawanie obserwatorów
        StockObserver emailObserver = new EmailNotifierObserver("investor@example.com");
        StockObserver smsObserver = new SMSNotifierObserver("+48123456789");
        StockObserver dashboardObserver = new DashboardObserver();

        market.attach(emailObserver);
        market.attach(smsObserver);
        market.attach(dashboardObserver);

        // Symulacja zmian cen akcji
        market.setStockPrice("AAPL", 150.00);
        market.setStockPrice("AAPL", 158.00);  // Wzrost 5.33%
        market.setStockPrice("AAPL", 175.00);  // Wzrost 10.76% - alarm SMS!

        // Można dynamicznie usunąć obserwatora
        market.detach(smsObserver);
        market.setStockPrice("AAPL", 170.00);
    }

    // ========================================
    // 5. DECORATOR PATTERN
    // ========================================

    /**
     * PRZED: Eksplozja klas dla wszystkich kombinacji funkcjonalności
     * Problemy:
     * - Potrzeba osobnej klasy dla każdej kombinacji
     * - Trudność w dodawaniu nowych funkcjonalności
     * - Naruszenie Open/Closed Principle
     */
    static class BasicCoffeeBefore {
        public String getDescription() {
            return "Podstawowa kawa";
        }

        public double getCost() {
            return 5.0;
        }
    }

    static class CoffeeWithMilkBefore {
        public String getDescription() {
            return "Kawa z mlekiem";
        }

        public double getCost() {
            return 6.0;
        }
    }

    static class CoffeeWithMilkAndSugarBefore {
        public String getDescription() {
            return "Kawa z mlekiem i cukrem";
        }

        public double getCost() {
            return 6.5;
        }
    }

    // PROBLEM: Potrzeba dziesiątek klas dla wszystkich kombinacji!
    // CoffeeWithMilkSugarAndCaramel, CoffeeWithMilkAndWhippedCream, etc.

    /**
     * PO: Decorator Pattern
     * Zalety:
     * - Dynamiczne dodawanie funkcjonalności w runtime
     * - Nie potrzeba eksplozji klas
     * - Zgodność z Open/Closed Principle
     * - Elastyczne komponowanie zachowań
     */
    interface Coffee {
        String getDescription();
        double getCost();
    }

    static class BasicCoffee implements Coffee {
        @Override
        public String getDescription() {
            return "Podstawowa kawa";
        }

        @Override
        public double getCost() {
            return 5.0;
        }
    }

    // Abstrakcyjna klasa dekoratora
    static abstract class CoffeeDecorator implements Coffee {
        protected final Coffee decoratedCoffee;

        public CoffeeDecorator(Coffee coffee) {
            this.decoratedCoffee = coffee;
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription();
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost();
        }
    }

    static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", mleko";
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 1.0;
        }
    }

    static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", cukier";
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 0.5;
        }
    }

    static class CaramelDecorator extends CoffeeDecorator {
        public CaramelDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", karmel";
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 1.5;
        }
    }

    static class WhippedCreamDecorator extends CoffeeDecorator {
        public WhippedCreamDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", bita śmietana";
        }

        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 2.0;
        }
    }

    private static void demonstrateDecoratorPattern() {
        System.out.println("\n=== DECORATOR PATTERN ===");

        // Podstawowa kawa
        Coffee coffee1 = new BasicCoffee();
        System.out.println(coffee1.getDescription() + " - Koszt: " + coffee1.getCost() + " PLN");

        // Kawa z mlekiem
        Coffee coffee2 = new MilkDecorator(new BasicCoffee());
        System.out.println(coffee2.getDescription() + " - Koszt: " + coffee2.getCost() + " PLN");

        // Kawa z mlekiem, cukrem i karmelem
        Coffee coffee3 = new CaramelDecorator(
                new SugarDecorator(
                        new MilkDecorator(
                                new BasicCoffee())));
        System.out.println(coffee3.getDescription() + " - Koszt: " + coffee3.getCost() + " PLN");

        // Deluxe kawa z wszystkimi dodatkami
        Coffee coffee4 = new WhippedCreamDecorator(
                new CaramelDecorator(
                        new SugarDecorator(
                                new MilkDecorator(
                                        new BasicCoffee()))));
        System.out.println(coffee4.getDescription() + " - Koszt: " + coffee4.getCost() + " PLN");
    }

    // ========================================
    // 6. BUILDER PATTERN
    // ========================================

    /**
     * PRZED: Konstruktor z wieloma parametrami (Telescoping Constructor Anti-pattern)
     * Problemy:
     * - Niezrozumiały kod przy wywołaniu (co oznacza każdy parametr?)
     * - Trudność w dodawaniu nowych parametrów
     * - Niemożność pominięcia niektórych opcjonalnych parametrów
     * - Brak walidacji
     */
    static class HttpRequestBefore {
        private String url;
        private String method;
        private Map<String, String> headers;
        private String body;
        private int timeout;
        private boolean followRedirects;
        private String userAgent;

        // PROBLEM: Telescoping constructor
        public HttpRequestBefore(String url) {
            this(url, "GET", new HashMap<>(), null, 30000, true, "Mozilla/5.0");
        }

        public HttpRequestBefore(String url, String method) {
            this(url, method, new HashMap<>(), null, 30000, true, "Mozilla/5.0");
        }

        public HttpRequestBefore(String url, String method, Map<String, String> headers) {
            this(url, method, headers, null, 30000, true, "Mozilla/5.0");
        }

        public HttpRequestBefore(String url, String method, Map<String, String> headers,
                                 String body, int timeout, boolean followRedirects, String userAgent) {
            // PROBLEM: Co oznacza każdy parametr? Łatwo pomylić kolejność!
            this.url = url;
            this.method = method;
            this.headers = headers;
            this.body = body;
            this.timeout = timeout;
            this.followRedirects = followRedirects;
            this.userAgent = userAgent;
        }

        public void execute() {
            System.out.println("Wykonuję request (before): " + method + " " + url);
        }
    }

    /**
     * PO: Builder Pattern z fluent interface
     * Zalety:
     * - Czytelny, samodokumentujący się kod
     * - Łatwe dodawanie nowych parametrów
     * - Możliwość walidacji przed stworzeniem obiektu
     * - Niemutowalność utworzonego obiektu
     * - Elastyczność w ustawianiu tylko potrzebnych parametrów
     */
    static class HttpRequest {
        // Wszystkie pola final - niemutowalność
        private final String url;
        private final String method;
        private final Map<String, String> headers;
        private final String body;
        private final int timeout;
        private final boolean followRedirects;
        private final String userAgent;
        private final int retryCount;

        // Prywatny konstruktor - obiekt można utworzyć tylko przez Builder
        private HttpRequest(Builder builder) {
            this.url = builder.url;
            this.method = builder.method;
            this.headers = Collections.unmodifiableMap(new HashMap<>(builder.headers));
            this.body = builder.body;
            this.timeout = builder.timeout;
            this.followRedirects = builder.followRedirects;
            this.userAgent = builder.userAgent;
            this.retryCount = builder.retryCount;
        }

        public void execute() {
            System.out.printf("Wykonuję %s request do %s%n", method, url);
            System.out.println("  Headers: " + headers);
            System.out.println("  Timeout: " + timeout + "ms");
            System.out.println("  Retry count: " + retryCount);
            if (body != null) {
                System.out.println("  Body: " + body);
            }
        }

        // Gettery (bez setterów - immutability)
        public String getUrl() { return url; }
        public String getMethod() { return method; }
        public Map<String, String> getHeaders() { return headers; }

        // Builder jako wewnętrzna klasa statyczna
        public static class Builder {
            // Wymagane parametry
            private final String url;

            // Opcjonalne parametry z wartościami domyślnymi
            private String method = "GET";
            private Map<String, String> headers = new HashMap<>();
            private String body = null;
            private int timeout = 30000;
            private boolean followRedirects = true;
            private String userAgent = "Mozilla/5.0";
            private int retryCount = 3;

            // Konstruktor z wymaganymi parametrami
            public Builder(String url) {
                if (url == null || url.trim().isEmpty()) {
                    throw new IllegalArgumentException("URL nie może być pusty");
                }
                this.url = url;
            }

            // Fluent interface - każda metoda zwraca Builder
            public Builder method(String method) {
                this.method = method;
                return this;
            }

            public Builder addHeader(String key, String value) {
                this.headers.put(key, value);
                return this;
            }

            public Builder headers(Map<String, String> headers) {
                this.headers = new HashMap<>(headers);
                return this;
            }

            public Builder body(String body) {
                this.body = body;
                return this;
            }

            public Builder timeout(int timeout) {
                if (timeout <= 0) {
                    throw new IllegalArgumentException("Timeout musi być większy od 0");
                }
                this.timeout = timeout;
                return this;
            }

            public Builder followRedirects(boolean followRedirects) {
                this.followRedirects = followRedirects;
                return this;
            }

            public Builder userAgent(String userAgent) {
                this.userAgent = userAgent;
                return this;
            }

            public Builder retryCount(int retryCount) {
                if (retryCount < 0) {
                    throw new IllegalArgumentException("Retry count nie może być ujemny");
                }
                this.retryCount = retryCount;
                return this;
            }

            // Walidacja i budowanie obiektu
            public HttpRequest build() {
                // Dodatkowa walidacja przed utworzeniem obiektu
                if ("POST".equals(method) || "PUT".equals(method)) {
                    if (body == null) {
                        throw new IllegalStateException(
                                "Metoda " + method + " wymaga body");
                    }
                }

                return new HttpRequest(this);
            }
        }
    }

    private static void demonstrateBuilderPattern() {
        System.out.println("\n=== BUILDER PATTERN ===");

        // Prosty request z minimalnymi parametrami
        HttpRequest request1 = new HttpRequest.Builder("https://api.example.com/users")
                .build();
        request1.execute();

        System.out.println();

        // Złożony request z wieloma parametrami - czytelny kod!
        HttpRequest request2 = new HttpRequest.Builder("https://api.example.com/data")
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer token123")
                .body("{\"name\": \"Jan Kowalski\", \"age\": 30}")
                .timeout(60000)
                .followRedirects(false)
                .userAgent("MyApp/1.0")
                .retryCount(5)
                .build();
        request2.execute();

        System.out.println();

        // Próba utworzenia nieprawidłowego obiektu - walidacja
        try {
            HttpRequest invalidRequest = new HttpRequest.Builder("https://api.example.com/update")
                    .method("POST")
                    // Brak body!
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("❌ Błąd walidacji: " + e.getMessage());
        }
    }

    // ========================================
    // 7. ADAPTER PATTERN
    // ========================================

    /**
     * PRZED: Niekompatybilne interfejsy - nie można używać starych klas z nowym kodem
     * Problemy:
     * - Legacy kod ma inny interfejs niż nowy system
     * - Niemożność modyfikacji starego kodu (biblioteka zewnętrzna)
     * - Konieczność przepisywania całego starego kodu
     */

    // Stary system płatności (legacy)
    static class OldPaymentGateway {
        public void makePayment(String accountNumber, double amount) {
            System.out.println("LEGACY: Wykonuję płatność " + amount + " PLN z konta: " + accountNumber);
        }

        public String getTransactionStatus(String transactionId) {
            return "COMPLETED";
        }
    }

    // Nowy interfejs systemu płatności
    interface ModernPaymentProcessor {
        PaymentResult processPayment(PaymentRequest request);
        boolean verifyPayment(String paymentId);
    }

    static class PaymentRequest {
        private final String customerId;
        private final double amount;
        private final String currency;
        private final String description;

        public PaymentRequest(String customerId, double amount, String currency, String description) {
            this.customerId = customerId;
            this.amount = amount;
            this.currency = currency;
            this.description = description;
        }

        public String getCustomerId() { return customerId; }
        public double getAmount() { return amount; }
        public String getCurrency() { return currency; }
        public String getDescription() { return description; }
    }

    static class PaymentResult {
        private final boolean success;
        private final String transactionId;
        private final String message;

        public PaymentResult(boolean success, String transactionId, String message) {
            this.success = success;
            this.transactionId = transactionId;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getTransactionId() { return transactionId; }
        public String getMessage() { return message; }
    }

    /**
     * PO: Adapter Pattern - adaptuje stary interfejs do nowego
     * Zalety:
     * - Możliwość używania legacy kodu bez modyfikacji
     * - Integracja niekompatybilnych interfejsów
     * - Zgodność z Open/Closed Principle
     * - Możliwość łatwej wymiany implementacji
     */
    static class PaymentGatewayAdapter implements ModernPaymentProcessor {
        private final OldPaymentGateway oldGateway;
        private int transactionCounter = 1000;

        public PaymentGatewayAdapter(OldPaymentGateway oldGateway) {
            this.oldGateway = oldGateway;
        }

        @Override
        public PaymentResult processPayment(PaymentRequest request) {
            try {
                // Adaptacja: konwersja nowego interfejsu na stary
                System.out.println("Adapter: Konwertuję PaymentRequest na stary format...");
                String accountNumber = "ACC-" + request.getCustomerId();

                // Wywołanie starego interfejsu
                oldGateway.makePayment(accountNumber, request.getAmount());

                // Konwersja wyniku ze starego na nowy format
                String transactionId = "TXN-" + (transactionCounter++);
                return new PaymentResult(true, transactionId,
                        "Płatność " + request.getAmount() + " " + request.getCurrency() + " zakończona sukcesem");

            } catch (Exception e) {
                return new PaymentResult(false, null, "Błąd płatności: " + e.getMessage());
            }
        }

        @Override
        public boolean verifyPayment(String paymentId) {
            System.out.println("Adapter: Weryfikuję płatność " + paymentId);
            String status = oldGateway.getTransactionStatus(paymentId);
            return "COMPLETED".equals(status);
        }
    }

    // Nowa, nowoczesna implementacja
    static class StripePaymentProcessor implements ModernPaymentProcessor {
        @Override
        public PaymentResult processPayment(PaymentRequest request) {
            System.out.println("Stripe: Przetwarzam płatność przez nowoczesne API...");
            System.out.println("  Klient: " + request.getCustomerId());
            System.out.println("  Kwota: " + request.getAmount() + " " + request.getCurrency());
            System.out.println("  Opis: " + request.getDescription());

            return new PaymentResult(true, "STRIPE-" + UUID.randomUUID().toString().substring(0, 8),
                    "Płatność przetworzona przez Stripe");
        }

        @Override
        public boolean verifyPayment(String paymentId) {
            System.out.println("Stripe: Weryfikuję płatność " + paymentId);
            return true;
        }
    }

    // Klient używający nowoczesnego interfejsu
    static class PaymentService {
        private final ModernPaymentProcessor processor;

        public PaymentService(ModernPaymentProcessor processor) {
            this.processor = processor;
        }

        public void executePayment(String customerId, double amount, String currency, String description) {
            PaymentRequest request = new PaymentRequest(customerId, amount, currency, description);
            PaymentResult result = processor.processPayment(request);

            if (result.isSuccess()) {
                System.out.println("✅ " + result.getMessage());
                System.out.println("   ID transakcji: " + result.getTransactionId());
                processor.verifyPayment(result.getTransactionId());
            } else {
                System.out.println("❌ " + result.getMessage());
            }
        }
    }

    private static void demonstrateAdapterPattern() {
        System.out.println("\n=== ADAPTER PATTERN ===");

        // Używamy starego systemu przez adapter
        System.out.println("--- Używam legacy systemu przez adapter ---");
        OldPaymentGateway oldGateway = new OldPaymentGateway();
        ModernPaymentProcessor adaptedProcessor = new PaymentGatewayAdapter(oldGateway);
        PaymentService service1 = new PaymentService(adaptedProcessor);
        service1.executePayment("CUST-123", 299.99, "PLN", "Zakup laptopa");

        System.out.println("\n--- Używam nowoczesnego systemu Stripe ---");
        ModernPaymentProcessor stripeProcessor = new StripePaymentProcessor();
        PaymentService service2 = new PaymentService(stripeProcessor);
        service2.executePayment("CUST-456", 149.99, "PLN", "Subskrypcja roczna");
    }

    // ========================================
    // 8. FACADE PATTERN
    // ========================================

    /**
     * PRZED: Klient musi znać wiele złożonych podsystemów
     * Problemy:
     * - Klient musi zarządzać wieloma zależnościami
     * - Skomplikowany kod klienta
     * - Silne sprzężenie z wieloma klasami
     * - Trudność w użyciu systemu
     */

    // Złożone podsystemy
    static class VideoCodec {
        public void decode(String videoFile) {
            System.out.println("  [Codec] Dekodowanie pliku wideo: " + videoFile);
        }

        public void encode(String format) {
            System.out.println("  [Codec] Kodowanie do formatu: " + format);
        }
    }

    static class AudioMixer {
        public void extractAudio(String videoFile) {
            System.out.println("  [Audio] Ekstrakcja ścieżki dźwiękowej z: " + videoFile);
        }

        public void normalizeAudio() {
            System.out.println("  [Audio] Normalizacja głośności audio");
        }

        public void mixAudio(String audioTrack) {
            System.out.println("  [Audio] Miksowanie ścieżki: " + audioTrack);
        }
    }

    static class VideoEditor {
        public void cut(int startTime, int endTime) {
            System.out.println("  [Editor] Wycinanie fragmentu: " + startTime + "s - " + endTime + "s");
        }

        public void applyFilter(String filter) {
            System.out.println("  [Editor] Aplikowanie filtra: " + filter);
        }

        public void adjustBrightness(int level) {
            System.out.println("  [Editor] Regulacja jasności: " + level + "%");
        }
    }

    static class VideoRenderer {
        public void renderPreview() {
            System.out.println("  [Renderer] Renderowanie podglądu...");
        }

        public void renderFinal(String outputFormat, int quality) {
            System.out.println("  [Renderer] Renderowanie finalne: " + outputFormat + " (jakość: " + quality + "%)");
        }

        public void optimize() {
            System.out.println("  [Renderer] Optymalizacja rozmiaru pliku");
        }
    }

    static class FileManager {
        public void saveFile(String filename, String path) {
            System.out.println("  [FileManager] Zapisywanie: " + filename + " do " + path);
        }

        public void createThumbnail(String videoFile) {
            System.out.println("  [FileManager] Tworzenie miniaturki dla: " + videoFile);
        }
    }

    // Klient PRZED - musi zarządzać wszystkimi podsystemami
    static class ComplexVideoProcessingClientBefore {
        public void processVideo(String inputFile, String outputFile) {
            // PROBLEM: Klient musi znać szczegóły wszystkich podsystemów
            VideoCodec codec = new VideoCodec();
            AudioMixer audio = new AudioMixer();
            VideoEditor editor = new VideoEditor();
            VideoRenderer renderer = new VideoRenderer();
            FileManager fileManager = new FileManager();

            codec.decode(inputFile);
            audio.extractAudio(inputFile);
            audio.normalizeAudio();
            editor.cut(10, 120);
            editor.applyFilter("Sepia");
            editor.adjustBrightness(110);
            renderer.renderPreview();
            audio.mixAudio("background_music.mp3");
            codec.encode("MP4");
            renderer.renderFinal("MP4", 95);
            renderer.optimize();
            fileManager.saveFile(outputFile, "/videos/processed/");
            fileManager.createThumbnail(outputFile);
        }
    }

    /**
     * PO: Facade Pattern - prosty interfejs do złożonego systemu
     * Zalety:
     * - Uproszczenie interfejsu dla klienta
     * - Luźne powiązanie między klientem a podsystemami
     * - Łatwiejsze użycie złożonego systemu
     * - Możliwość ukrycia szczegółów implementacji
     */
    static class VideoProcessingFacade {
        private final VideoCodec codec;
        private final AudioMixer audio;
        private final VideoEditor editor;
        private final VideoRenderer renderer;
        private final FileManager fileManager;

        public VideoProcessingFacade() {
            this.codec = new VideoCodec();
            this.audio = new AudioMixer();
            this.editor = new VideoEditor();
            this.renderer = new VideoRenderer();
            this.fileManager = new FileManager();
        }

        // Prosty interfejs dla typowych operacji
        public void convertToMP4(String inputFile, String outputFile) {
            System.out.println("📹 Konwersja wideo do MP4...");
            codec.decode(inputFile);
            codec.encode("MP4");
            renderer.renderFinal("MP4", 95);
            fileManager.saveFile(outputFile, "/videos/converted/");
            System.out.println("✅ Konwersja zakończona!\n");
        }

        public void createShortClip(String inputFile, String outputFile, int startSec, int endSec) {
            System.out.println("✂️  Tworzenie krótkiego klipu...");
            codec.decode(inputFile);
            audio.extractAudio(inputFile);
            editor.cut(startSec, endSec);
            audio.normalizeAudio();
            renderer.renderFinal("MP4", 90);
            fileManager.saveFile(outputFile, "/videos/clips/");
            fileManager.createThumbnail(outputFile);
            System.out.println("✅ Klip utworzony!\n");
        }

        public void enhanceVideo(String inputFile, String outputFile, String filter, int brightness) {
            System.out.println("✨ Ulepszanie jakości wideo...");
            codec.decode(inputFile);
            audio.extractAudio(inputFile);
            audio.normalizeAudio();
            editor.applyFilter(filter);
            editor.adjustBrightness(brightness);
            renderer.renderPreview();
            codec.encode("MP4");
            renderer.renderFinal("MP4", 100);
            renderer.optimize();
            fileManager.saveFile(outputFile, "/videos/enhanced/");
            System.out.println("✅ Wideo ulepszone!\n");
        }

        public void addBackgroundMusic(String inputFile, String musicFile, String outputFile) {
            System.out.println("🎵 Dodawanie muzyki w tle...");
            codec.decode(inputFile);
            audio.extractAudio(inputFile);
            audio.mixAudio(musicFile);
            audio.normalizeAudio();
            codec.encode("MP4");
            renderer.renderFinal("MP4", 95);
            fileManager.saveFile(outputFile, "/videos/with_music/");
            System.out.println("✅ Muzyka dodana!\n");
        }
    }

    private static void demonstrateFacadePattern() {
        System.out.println("\n=== FACADE PATTERN ===");

        VideoProcessingFacade facade = new VideoProcessingFacade();

        // Proste wywołania zamiast zarządzania wieloma podsystemami
        facade.convertToMP4("vacation.avi", "vacation.mp4");
        facade.createShortClip("presentation.mp4", "intro.mp4", 0, 30);
        facade.enhanceVideo("old_movie.mp4", "restored.mp4", "Sharpen", 115);
    }

    // ========================================
    // 9. TEMPLATE METHOD PATTERN
    // ========================================

    /**
     * PRZED: Duplikacja kodu w podobnych procesach
     * Problemy:
     * - Powtarzający się kod w różnych klasach
     * - Trudność w utrzymaniu spójności
     * - Naruszenie DRY principle
     * - Ciężka modyfikacja wspólnej logiki
     */
    static class PDFReportGeneratorBefore {
        public void generateReport(String data) {
            // PROBLEM: Cały proces w jednej metodzie, duplikacja w innych generatorach
            System.out.println("Otwieranie połączenia z bazą danych...");
            System.out.println("Pobieranie danych: " + data);
            System.out.println("Formatowanie danych do PDF");
            System.out.println("Dodawanie nagłówka PDF");
            System.out.println("Generowanie treści PDF");
            System.out.println("Dodawanie stopki PDF");
            System.out.println("Zapisywanie pliku PDF");
            System.out.println("Zamykanie połączenia");
        }
    }

    static class ExcelReportGeneratorBefore {
        public void generateReport(String data) {
            // PROBLEM: Ten sam szkielet algorytmu, tylko szczegóły się różnią
            System.out.println("Otwieranie połączenia z bazą danych...");
            System.out.println("Pobieranie danych: " + data);
            System.out.println("Formatowanie danych do Excel");
            System.out.println("Tworzenie arkusza Excel");
            System.out.println("Generowanie treści Excel");
            System.out.println("Dodawanie wykresów");
            System.out.println("Zapisywanie pliku Excel");
            System.out.println("Zamykanie połączenia");
        }
    }

    /**
     * PO: Template Method Pattern
     * Zalety:
     * - Wspólny szkielet algorytmu w klasie bazowej
     * - Podklasy definiują tylko różniące się kroki
     * - Eliminacja duplikacji kodu
     * - Łatwa modyfikacja wspólnej logiki
     * - Zgodność z Hollywood Principle ("Don't call us, we'll call you")
     */
    static abstract class ReportGenerator {
        // Template Method - definiuje szkielet algorytmu
        public final void generateReport(String data) {
            openDatabaseConnection();
            fetchData(data);
            formatData();
            addHeader();
            generateContent();
            addFooter();
            saveReport();
            closeDatabaseConnection();

            // Hook method - opcjonalna operacja
            if (shouldSendEmail()) {
                sendEmailNotification();
            }
        }

        // Wspólne kroki - implementowane w klasie bazowej
        private void openDatabaseConnection() {
            System.out.println("  [DB] Otwieranie połączenia z bazą danych...");
        }

        private void fetchData(String data) {
            System.out.println("  [DB] Pobieranie danych: " + data);
        }

        private void closeDatabaseConnection() {
            System.out.println("  [DB] Zamykanie połączenia\n");
        }

        // Abstrakcyjne metody - podklasy MUSZĄ je zaimplementować
        protected abstract void formatData();
        protected abstract void addHeader();
        protected abstract void generateContent();
        protected abstract void addFooter();
        protected abstract void saveReport();
        protected abstract String getReportType();

        // Hook method - podklasy MOGĄ je nadpisać (domyślnie false)
        protected boolean shouldSendEmail() {
            return false;
        }

        protected void sendEmailNotification() {
            System.out.println("  [Email] Wysyłanie powiadomienia email...");
        }
    }

    static class PDFReportGenerator extends ReportGenerator {
        @Override
        protected void formatData() {
            System.out.println("  [PDF] Formatowanie danych do PDF");
        }

        @Override
        protected void addHeader() {
            System.out.println("  [PDF] Dodawanie nagłówka z logo firmy");
        }

        @Override
        protected void generateContent() {
            System.out.println("  [PDF] Generowanie treści w formacie PDF");
            System.out.println("  [PDF] Dodawanie grafik i tabel");
        }

        @Override
        protected void addFooter() {
            System.out.println("  [PDF] Dodawanie stopki z numeracją stron");
        }

        @Override
        protected void saveReport() {
            System.out.println("  [PDF] Zapisywanie pliku: report.pdf");
        }

        @Override
        protected String getReportType() {
            return "PDF";
        }

        @Override
        protected boolean shouldSendEmail() {
            return true; // PDF zawsze wysyła email
        }
    }

    static class ExcelReportGenerator extends ReportGenerator {
        @Override
        protected void formatData() {
            System.out.println("  [Excel] Formatowanie danych do arkusza kalkulacyjnego");
        }

        @Override
        protected void addHeader() {
            System.out.println("  [Excel] Tworzenie nagłówków kolumn");
        }

        @Override
        protected void generateContent() {
            System.out.println("  [Excel] Generowanie arkuszy z danymi");
            System.out.println("  [Excel] Dodawanie formuł i wykresów");
        }

        @Override
        protected void addFooter() {
            System.out.println("  [Excel] Dodawanie podsumowania i sum");
        }

        @Override
        protected void saveReport() {
            System.out.println("  [Excel] Zapisywanie pliku: report.xlsx");
        }

        @Override
        protected String getReportType() {
            return "Excel";
        }
    }

    static class HTMLReportGenerator extends ReportGenerator {
        private final boolean sendNotification;

        public HTMLReportGenerator(boolean sendNotification) {
            this.sendNotification = sendNotification;
        }

        @Override
        protected void formatData() {
            System.out.println("  [HTML] Formatowanie danych do HTML");
        }

        @Override
        protected void addHeader() {
            System.out.println("  [HTML] Dodawanie nagłówka HTML z CSS");
        }

        @Override
        protected void generateContent() {
            System.out.println("  [HTML] Generowanie treści z interaktywnymi elementami");
        }

        @Override
        protected void addFooter() {
            System.out.println("  [HTML] Dodawanie stopki z linkami");
        }

        @Override
        protected void saveReport() {
            System.out.println("  [HTML] Zapisywanie pliku: report.html");
        }

        @Override
        protected String getReportType() {
            return "HTML";
        }

        @Override
        protected boolean shouldSendEmail() {
            return sendNotification;
        }
    }

    private static void demonstrateTemplateMethodPattern() {
        System.out.println("\n=== TEMPLATE METHOD PATTERN ===");

        System.out.println("--- Generowanie raportu PDF ---");
        ReportGenerator pdfGenerator = new PDFReportGenerator();
        pdfGenerator.generateReport("Dane sprzedażowe Q4 2025");

        System.out.println("--- Generowanie raportu Excel ---");
        ReportGenerator excelGenerator = new ExcelReportGenerator();
        excelGenerator.generateReport("Analiza finansowa");

        System.out.println("--- Generowanie raportu HTML z powiadomieniem ---");
        ReportGenerator htmlGenerator = new HTMLReportGenerator(true);
        htmlGenerator.generateReport("Raport online");
    }

    // ========================================
    // 10. COMMAND PATTERN
    // ========================================

    /**
     * PRZED: Bezpośrednie wywołania operacji, brak historii, niemożność cofnięcia
     * Problemy:
     * - Brak możliwości cofnięcia operacji (undo)
     * - Niemożność kolejkowania operacji
     * - Brak historii wykonanych działań
     * - Silne sprzężenie między nadawcą a odbiorcą
     */
    static class TextEditorBefore {
        private StringBuilder content = new StringBuilder();

        public void write(String text) {
            // PROBLEM: Bezpośrednia operacja, nie można jej cofnąć
            content.append(text);
            System.out.println("Napisano: " + text);
        }

        public void delete(int length) {
            // PROBLEM: Nie ma możliwości przywrócenia usuniętego tekstu
            int start = Math.max(0, content.length() - length);
            content.delete(start, content.length());
            System.out.println("Usunięto " + length + " znaków");
        }

        public String getContent() {
            return content.toString();
        }
    }

    /**
     * PO: Command Pattern z obsługą Undo/Redo
     * Zalety:
     * - Możliwość cofania operacji (undo)
     * - Możliwość ponowienia operacji (redo)
     * - Historia wszystkich operacji
     * - Kolejkowanie i logowanie komend
     * - Luźne sprzężenie między nadawcą a odbiorcą
     */

    // Receiver - obiekt wykonujący rzeczywiste operacje
    static class TextDocument {
        private StringBuilder content = new StringBuilder();

        public void insertText(int position, String text) {
            content.insert(position, text);
        }

        public String deleteText(int position, int length) {
            String deleted = content.substring(position, Math.min(position + length, content.length()));
            content.delete(position, Math.min(position + length, content.length()));
            return deleted;
        }

        public String getContent() {
            return content.toString();
        }

        public int getLength() {
            return content.length();
        }
    }

    // Command interface
    interface Command {
        void execute();
        void undo();
        String getDescription();
    }

    // Concrete Commands
    static class WriteCommand implements Command {
        private final TextDocument document;
        private final String text;
        private final int position;

        public WriteCommand(TextDocument document, String text) {
            this.document = document;
            this.text = text;
            this.position = document.getLength();
        }

        @Override
        public void execute() {
            document.insertText(position, text);
            System.out.println("✍️  Napisano: \"" + text + "\"");
        }

        @Override
        public void undo() {
            document.deleteText(position, text.length());
            System.out.println("↩️  Cofnięto pisanie: \"" + text + "\"");
        }

        @Override
        public String getDescription() {
            return "Napisanie: \"" + text + "\"";
        }
    }

    static class DeleteCommand implements Command {
        private final TextDocument document;
        private final int length;
        private String deletedText;
        private int position;

        public DeleteCommand(TextDocument document, int length) {
            this.document = document;
            this.length = length;
        }

        @Override
        public void execute() {
            position = Math.max(0, document.getLength() - length);
            deletedText = document.deleteText(position, length);
            System.out.println("🗑️  Usunięto: \"" + deletedText + "\"");
        }

        @Override
        public void undo() {
            document.insertText(position, deletedText);
            System.out.println("↩️  Cofnięto usunięcie: \"" + deletedText + "\"");
        }

        @Override
        public String getDescription() {
            return "Usunięcie " + length + " znaków";
        }
    }

    static class ReplaceCommand implements Command {
        private final TextDocument document;
        private final String oldText;
        private final String newText;
        private boolean executed = false;

        public ReplaceCommand(TextDocument document, String oldText, String newText) {
            this.document = document;
            this.oldText = oldText;
            this.newText = newText;
        }

        @Override
        public void execute() {
            String content = document.getContent();
            int index = content.indexOf(oldText);
            if (index != -1) {
                document.deleteText(index, oldText.length());
                document.insertText(index, newText);
                executed = true;
                System.out.println("🔄 Zastąpiono: \"" + oldText + "\" → \"" + newText + "\"");
            } else {
                System.out.println("⚠️  Nie znaleziono tekstu: \"" + oldText + "\"");
            }
        }

        @Override
        public void undo() {
            if (executed) {
                String content = document.getContent();
                int index = content.indexOf(newText);
                if (index != -1) {
                    document.deleteText(index, newText.length());
                    document.insertText(index, oldText);
                    System.out.println("↩️  Cofnięto zamianę: \"" + newText + "\" → \"" + oldText + "\"");
                }
            }
        }

        @Override
        public String getDescription() {
            return "Zamiana: \"" + oldText + "\" na \"" + newText + "\"";
        }
    }

    // Invoker - zarządza wykonaniem komend i historią
    static class TextEditorWithHistory {
        private final TextDocument document;
        private final Stack<Command> history = new Stack<>();
        private final Stack<Command> redoStack = new Stack<>();

        public TextEditorWithHistory() {
            this.document = new TextDocument();
        }

        public void executeCommand(Command command) {
            command.execute();
            history.push(command);
            redoStack.clear(); // Po nowej operacji czyścimy redo
        }

        public void undo() {
            if (history.isEmpty()) {
                System.out.println("⚠️  Brak operacji do cofnięcia");
                return;
            }
            Command command = history.pop();
            command.undo();
            redoStack.push(command);
        }

        public void redo() {
            if (redoStack.isEmpty()) {
                System.out.println("⚠️  Brak operacji do ponowienia");
                return;
            }
            Command command = redoStack.pop();
            command.execute();
            history.push(command);
        }

        public void showHistory() {
            System.out.println("\n📋 Historia operacji:");
            if (history.isEmpty()) {
                System.out.println("  (pusta)");
            } else {
                for (int i = 0; i < history.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + history.get(i).getDescription());
                }
            }
        }

        public String getContent() {
            return document.getContent();
        }

        public TextDocument getDocument() {
            return document;
        }
    }

    private static void demonstrateCommandPattern() {
        System.out.println("\n=== COMMAND PATTERN ===");

        TextEditorWithHistory editor = new TextEditorWithHistory();

        // Wykonywanie komend
        editor.executeCommand(new WriteCommand(editor.getDocument(), "Witaj "));
        editor.executeCommand(new WriteCommand(editor.getDocument(), "świecie!"));
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        editor.executeCommand(new DeleteCommand(editor.getDocument(), 8));
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        editor.executeCommand(new WriteCommand(editor.getDocument(), " Polsko!"));
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        // Cofanie operacji
        System.out.println("--- Cofanie operacji ---");
        editor.undo();
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        editor.undo();
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        // Ponowne wykonanie
        System.out.println("--- Ponowne wykonanie ---");
        editor.redo();
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        // Zamiana tekstu
        editor.executeCommand(new ReplaceCommand(editor.getDocument(), "Witaj", "Cześć"));
        System.out.println("Treść: \"" + editor.getContent() + "\"\n");

        editor.showHistory();
    }

    // ========================================
    // 11. CHAIN OF RESPONSIBILITY PATTERN
    // ========================================

    /**
     * PRZED: Złożona logika if-else do obsługi różnych przypadków
     * Problemy:
     * - Długie łańcuchy if-else
     * - Trudność w dodawaniu nowych przypadków
     * - Naruszenie Single Responsibility Principle
     * - Ścisłe sprzężenie
     */
    static class SupportTicketHandlerBefore {
        public void handleTicket(String severity, String issue) {
            // PROBLEM: Cała logika w jednej metodzie z wieloma if-else
            if (severity.equals("LOW")) {
                if (issue.contains("password")) {
                    System.out.println("Bot: Resetuję hasło automatycznie");
                } else {
                    System.out.println("Level 1 Support: Obsługuję prosty problem");
                }
            } else if (severity.equals("MEDIUM")) {
                if (issue.contains("bug")) {
                    System.out.println("Level 2 Support: Analizuję i naprawiam bug");
                } else {
                    System.out.println("Level 2 Support: Rozwiązuję średnio złożony problem");
                }
            } else if (severity.equals("HIGH")) {
                if (issue.contains("security")) {
                    System.out.println("Security Team: Natychmiastowa reakcja na problem bezpieczeństwa!");
                } else {
                    System.out.println("Level 3 Support: Obsługuję krytyczny problem");
                }
            } else if (severity.equals("CRITICAL")) {
                System.out.println("Management: Eskalacja do zarządu!");
                System.out.println("CTO: Osobiste zajęcie się sprawą");
            }
        }
    }

    /**
     * PO: Chain of Responsibility Pattern
     * Zalety:
     * - Każdy handler w osobnej klasie
     * - Luźne powiązanie między nadawcą a odbiorcą
     * - Łatwe dodawanie nowych handlerów
     * - Dynamiczne budowanie łańcucha
     * - Zgodność z Single Responsibility Principle
     */

    static class SupportTicket {
        private final String id;
        private final String severity;
        private final String issue;
        private final String customerName;
        private boolean handled = false;

        public SupportTicket(String id, String severity, String issue, String customerName) {
            this.id = id;
            this.severity = severity;
            this.issue = issue;
            this.customerName = customerName;
        }

        public String getId() { return id; }
        public String getSeverity() { return severity; }
        public String getIssue() { return issue; }
        public String getCustomerName() { return customerName; }
        public boolean isHandled() { return handled; }
        public void markAsHandled() { this.handled = true; }

        @Override
        public String toString() {
            return String.format("Ticket #%s [%s] - %s (Klient: %s)", id, severity, issue, customerName);
        }
    }

    // Abstract Handler
    static abstract class SupportHandler {
        protected SupportHandler nextHandler;

        public SupportHandler setNext(SupportHandler handler) {
            this.nextHandler = handler;
            return handler;
        }

        public void handleTicket(SupportTicket ticket) {
            if (canHandle(ticket)) {
                process(ticket);
                ticket.markAsHandled();
            } else if (nextHandler != null) {
                System.out.println("  ⏭️  Przekazuję do następnego handlera...");
                nextHandler.handleTicket(ticket);
            } else {
                System.out.println("  ❌ Brak odpowiedniego handlera dla: " + ticket);
            }
        }

        protected abstract boolean canHandle(SupportTicket ticket);
        protected abstract void process(SupportTicket ticket);
        protected abstract String getHandlerName();
    }

    // Concrete Handlers
    static class AutomatedBotHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return "LOW".equals(ticket.getSeverity()) &&
                    (ticket.getIssue().toLowerCase().contains("password") ||
                            ticket.getIssue().toLowerCase().contains("login"));
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  🤖 [Bot] Automatyczne rozwiązanie problemu");
            System.out.println("      Wysyłam link do resetu hasła na email");
            System.out.println("      ✅ Problem rozwiązany automatycznie!");
        }

        @Override
        protected String getHandlerName() {
            return "Automated Bot";
        }
    }

    static class Level1SupportHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return "LOW".equals(ticket.getSeverity());
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  👤 [Level 1 Support] Obsługa podstawowego zgłoszenia");
            System.out.println("      Czas rozwiązania: ~15 minut");
            System.out.println("      ✅ Problem rozwiązany!");
        }

        @Override
        protected String getHandlerName() {
            return "Level 1 Support";
        }
    }

    static class Level2SupportHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return "MEDIUM".equals(ticket.getSeverity());
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  👨‍💻 [Level 2 Support] Analiza techniczna problemu");
            System.out.println("      Sprawdzam logi systemowe...");
            System.out.println("      Reprodukuję problem...");
            System.out.println("      ✅ Problem zidentyfikowany i naprawiony!");
        }

        @Override
        protected String getHandlerName() {
            return "Level 2 Support";
        }
    }

    static class SecurityTeamHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return ticket.getIssue().toLowerCase().contains("security") ||
                    ticket.getIssue().toLowerCase().contains("hack") ||
                    ticket.getIssue().toLowerCase().contains("breach");
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  🛡️  [Security Team] PRIORYTET MAKSYMALNY!");
            System.out.println("      Blokuję potencjalne zagrożenie...");
            System.out.println("      Analizuję logi bezpieczeństwa...");
            System.out.println("      Powiadamiam zespół zarządzający...");
            System.out.println("      ✅ Zagrożenie zneutralizowane!");
        }

        @Override
        protected String getHandlerName() {
            return "Security Team";
        }
    }

    static class Level3SupportHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return "HIGH".equals(ticket.getSeverity());
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  🔥 [Level 3 Support] Krytyczny problem - natychmiastowa reakcja");
            System.out.println("      Alarmowanie całego zespołu technicznego...");
            System.out.println("      Debugowanie na poziomie systemu...");
            System.out.println("      ✅ Problem rozwiązany z najwyższym priorytetem!");
        }

        @Override
        protected String getHandlerName() {
            return "Level 3 Support";
        }
    }

    static class ManagementHandler extends SupportHandler {
        @Override
        protected boolean canHandle(SupportTicket ticket) {
            return "CRITICAL".equals(ticket.getSeverity());
        }

        @Override
        protected void process(SupportTicket ticket) {
            System.out.println("  👔 [Management] KRYZYSOWE zgłoszenie - eskalacja do zarządu");
            System.out.println("      CTO osobiście zajmuje się sprawą");
            System.out.println("      Zwołuję naradę kryzysową...");
            System.out.println("      Bezpośredni kontakt z klientem: " + ticket.getCustomerName());
            System.out.println("      ✅ Sytuacja opanowana na najwyższym poziomie!");
        }

        @Override
        protected String getHandlerName() {
            return "Management";
        }
    }

    private static void demonstrateChainOfResponsibilityPattern() {
        System.out.println("\n=== CHAIN OF RESPONSIBILITY PATTERN ===");

        // Budowanie łańcucha odpowiedzialności
        SupportHandler chain = new AutomatedBotHandler();
        chain.setNext(new Level1SupportHandler())
                .setNext(new Level2SupportHandler())
                .setNext(new SecurityTeamHandler())
                .setNext(new Level3SupportHandler())
                .setNext(new ManagementHandler());

        // Testowanie różnych zgłoszeń
        List<SupportTicket> tickets = Arrays.asList(
                new SupportTicket("001", "LOW", "Nie mogę się zalogować - zapomniałem hasła", "Jan Kowalski"),
                new SupportTicket("002", "MEDIUM", "Znalazłem bug w formularzu zamówienia", "Anna Nowak"),
                new SupportTicket("003", "HIGH", "System nie odpowiada - przestoje w produkcji", "Piotr Wiśniewski"),
                new SupportTicket("004", "LOW", "Jak zmienić email w profilu?", "Maria Lewandowska"),
                new SupportTicket("005", "CRITICAL", "Podejrzenie security breach - wyciek danych", "Adam Kowalczyk"),
                new SupportTicket("006", "MEDIUM", "Security audit wykazał lukę w autoryzacji", "Ewa Szymańska")
        );

        for (SupportTicket ticket : tickets) {
            System.out.println("\n📩 Nowe zgłoszenie: " + ticket);
            chain.handleTicket(ticket);
        }
    }

    // ========================================
    // 12. PROXY PATTERN
    // ========================================

    /**
     * PRZED: Bezpośredni dostęp do ciężkich obiektów, brak kontroli, brak cache
     * Problemy:
     * - Załadowanie wszystkich danych na starcie (nawet nieużywanych)
     * - Brak kontroli dostępu
     * - Brak cachowania
     * - Brak logowania dostępu
     */
    static class RealImageBefore {
        private final String filename;
        private byte[] imageData;

        public RealImageBefore(String filename) {
            this.filename = filename;
            // PROBLEM: Ładowanie od razu przy tworzeniu obiektu
            loadFromDisk();
        }

        private void loadFromDisk() {
            System.out.println("Ładowanie obrazu z dysku: " + filename + " (zajmuje 5 sekund...)");
            try {
                Thread.sleep(1000); // Symulacja wolnego ładowania
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.imageData = new byte[1024 * 1024 * 10]; // 10MB
            System.out.println("Obraz załadowany: " + filename);
        }

        public void display() {
            System.out.println("Wyświetlanie obrazu: " + filename);
        }
    }

    /**
     * PO: Proxy Pattern z lazy loading, kontrolą dostępu i cachem
     * Zalety:
     * - Lazy loading - ładowanie tylko gdy potrzeba
     * - Kontrola dostępu
     * - Cachowanie
     * - Logowanie operacji
     * - Ten sam interfejs co rzeczywisty obiekt
     */

    interface Image {
        void display();
        String getFilename();
        long getSize();
    }

    static class RealImage implements Image {
        private final String filename;
        private byte[] imageData;
        private final long loadTime;

        public RealImage(String filename) {
            this.filename = filename;
            long start = System.currentTimeMillis();
            loadFromDisk();
            this.loadTime = System.currentTimeMillis() - start;
        }

        private void loadFromDisk() {
            System.out.println("  🔄 Ładowanie prawdziwego obrazu z dysku: " + filename);
            try {
                Thread.sleep(500); // Symulacja wolnego ładowania
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.imageData = new byte[1024 * 1024 * 10]; // 10MB
            System.out.println("  ✅ Obraz załadowany: " + filename);
        }

        @Override
        public void display() {
            System.out.println("  🖼️  Wyświetlanie obrazu: " + filename);
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public long getSize() {
            return imageData != null ? imageData.length : 0;
        }
    }

    // Virtual Proxy - dla lazy loading
    static class ImageProxy implements Image {
        private final String filename;
        private RealImage realImage;
        private final Set<String> authorizedUsers;

        public ImageProxy(String filename, Set<String> authorizedUsers) {
            this.filename = filename;
            this.authorizedUsers = authorizedUsers;
            System.out.println("  📋 Utworzono proxy dla: " + filename + " (obraz NIE został jeszcze załadowany)");
        }

        @Override
        public void display() {
            // Lazy loading - ładujemy dopiero przy pierwszym użyciu
            if (realImage == null) {
                System.out.println("  ⏳ Pierwsze użycie - ładuję prawdziwy obraz...");
                realImage = new RealImage(filename);
            } else {
                System.out.println("  ⚡ Używam załadowanego obrazu z cache");
            }
            realImage.display();
        }

        // Metoda z kontrolą dostępu
        public void displayForUser(String username) {
            System.out.println("\n🔐 Próba dostępu użytkownika: " + username);
            if (!authorizedUsers.contains(username)) {
                System.out.println("  ❌ DOSTĘP ZABRONIONY - użytkownik nie ma uprawnień!");
                return;
            }
            System.out.println("  ✅ Dostęp autoryzowany");
            display();
        }

        @Override
        public String getFilename() {
            return filename;
        }

        @Override
        public long getSize() {
            return realImage != null ? realImage.getSize() : 0;
        }
    }

    // Logging Proxy - proxy do logowania
    static class LoggingImageProxy implements Image {
        private final Image image;
        private int displayCount = 0;

        public LoggingImageProxy(Image image) {
            this.image = image;
        }

        @Override
        public void display() {
            displayCount++;
            System.out.println("  📊 [LOG] Wyświetlenie #" + displayCount + " obrazu: " + image.getFilename());
            long start = System.currentTimeMillis();
            image.display();
            long duration = System.currentTimeMillis() - start;
            System.out.println("  📊 [LOG] Czas wyświetlenia: " + duration + "ms");
        }

        @Override
        public String getFilename() {
            return image.getFilename();
        }

        @Override
        public long getSize() {
            return image.getSize();
        }

        public int getDisplayCount() {
            return displayCount;
        }
    }

    private static void demonstrateProxyPattern() {
        System.out.println("\n=== PROXY PATTERN ===");

        System.out.println("\n--- Virtual Proxy (Lazy Loading) ---");
        Set<String> authorizedUsers = new HashSet<>(Arrays.asList("admin", "john", "alice"));
        ImageProxy proxy = new ImageProxy("vacation_photo.jpg", authorizedUsers);

        System.out.println("\nPróba wyświetlenia obrazu:");
        proxy.display(); // Pierwsze wywołanie - ładuje obraz

        System.out.println("\nDrugie wyświetlenie:");
        proxy.display(); // Drugie wywołanie - używa cache

        System.out.println("\n--- Protection Proxy (Kontrola dostępu) ---");
        proxy.displayForUser("admin");  // Autoryzowany
        proxy.displayForUser("hacker"); // Nieautoryzowany

        System.out.println("\n--- Logging Proxy (Logowanie operacji) ---");
        Image realImage = new RealImage("document.pdf");
        LoggingImageProxy loggingProxy = new LoggingImageProxy(realImage);

        loggingProxy.display();
        loggingProxy.display();
        loggingProxy.display();

        System.out.println("\n📈 Statystyki: Obraz wyświetlony " + loggingProxy.getDisplayCount() + " razy");
    }

    // ========================================
    // 13. COMPOSITE PATTERN
    // ========================================

    /**
     * PRZED: Różne traktowanie pojedynczych obiektów i grup
     * Problemy:
     * - Klient musi rozróżniać pliki i foldery
     * - Duplikacja kodu dla operacji na grupach
     * - Trudność w obsłudze zagnieżdżonych struktur
     * - Brak rekurencyjnego przetwarzania
     * - Każdy poziom hierarchii wymaga osobnej logiki
     */
    static class FileBefore {
        private String name;
        private long size;

        public FileBefore(String name, long size) {
            this.name = name;
            this.size = size;
        }

        public void display() {
            System.out.println("Plik: " + name + " (" + size + " KB)");
        }

        public long getSize() {
            return size;
        }
    }

    static class FolderBefore {
        private String name;
        private List<FileBefore> files = new ArrayList<>();
        private List<FolderBefore> subfolders = new ArrayList<>();

        public FolderBefore(String name) {
            this.name = name;
        }

        public void addFile(FileBefore file) {
            files.add(file);
        }

        public void addFolder(FolderBefore folder) {
            subfolders.add(folder);
        }

        // PROBLEM: Różne metody dla plików i folderów, brak wspólnego interfejsu
        public void display() {
            System.out.println("Folder: " + name);
            for (FileBefore file : files) {
                System.out.print("  ");
                file.display();
            }
            for (FolderBefore folder : subfolders) {
                folder.display();
            }
        }

        // PROBLEM: Duplikacja logiki rekurencyjnej
        public long getSize() {
            long total = 0;
            for (FileBefore file : files) {
                total += file.getSize();
            }
            for (FolderBefore folder : subfolders) {
                total += folder.getSize();
            }
            return total;
        }
    }

    /**
     * PO: Composite Pattern - jednolite traktowanie obiektów i kompozytów
     * Zalety:
     * - Wspólny interfejs dla liści i kompozytów (FileSystemComponent)
     * - Klient traktuje wszystko jednakowo - nie musi wiedzieć czy to plik czy folder
     * - Łatwe dodawanie nowych komponentów do hierarchii
     * - Naturalne wsparcie dla struktur drzewiastych
     * - Rekurencyjne operacje działają automatycznie
     * - Zgodność z Open/Closed Principle
     */

    interface FileSystemComponent {
        void display(int indent);
        long getSize();
        String getName();
        void add(FileSystemComponent component);
        void remove(FileSystemComponent component);
        List<FileSystemComponent> getChildren();
    }

    static class FileLeaf implements FileSystemComponent {
        private final String name;
        private final long size;
        private final String type;

        public FileLeaf(String name, long size, String type) {
            this.name = name;
            this.size = size;
            this.type = type;
        }

        @Override
        public void display(int indent) {
            String indentation = "  ".repeat(indent);
            String icon = getIconForType();
            System.out.printf("%s%s %s (%d KB)%n", indentation, icon, name, size);
        }

        private String getIconForType() {
            switch (type.toLowerCase()) {
                case "pdf": return "📄";
                case "jpg":
                case "png": return "🖼️";
                case "mp4": return "🎬";
                case "java": return "☕";
                case "txt": return "📝";
                default: return "📄";
            }
        }

        @Override
        public long getSize() { return size; }

        @Override
        public String getName() { return name; }

        @Override
        public void add(FileSystemComponent component) {
            throw new UnsupportedOperationException("Nie można dodać komponentu do pliku");
        }

        @Override
        public void remove(FileSystemComponent component) {
            throw new UnsupportedOperationException("Nie można usunąć komponentu z pliku");
        }

        @Override
        public List<FileSystemComponent> getChildren() {
            return Collections.emptyList();
        }
    }

    static class FolderComposite implements FileSystemComponent {
        private final String name;
        private final List<FileSystemComponent> children = new ArrayList<>();

        public FolderComposite(String name) {
            this.name = name;
        }

        @Override
        public void display(int indent) {
            String indentation = "  ".repeat(indent);
            System.out.printf("%s📁 %s/ (%d KB total)%n", indentation, name, getSize());

            for (FileSystemComponent child : children) {
                child.display(indent + 1);
            }
        }

        @Override
        public long getSize() {
            return children.stream().mapToLong(FileSystemComponent::getSize).sum();
        }

        @Override
        public String getName() { return name; }

        @Override
        public void add(FileSystemComponent component) {
            children.add(component);
        }

        @Override
        public void remove(FileSystemComponent component) {
            children.remove(component);
        }

        @Override
        public List<FileSystemComponent> getChildren() {
            return new ArrayList<>(children);
        }

        public int countFiles() {
            int count = 0;
            for (FileSystemComponent child : children) {
                if (child instanceof FileLeaf) {
                    count++;
                } else if (child instanceof FolderComposite) {
                    count += ((FolderComposite) child).countFiles();
                }
            }
            return count;
        }
    }

    private static void demonstrateCompositePattern() {
        System.out.println("\n=== COMPOSITE PATTERN ===");

        FolderComposite root = new FolderComposite("Moje Dokumenty");

        FolderComposite photos = new FolderComposite("Zdjęcia");
        photos.add(new FileLeaf("wakacje_2024.jpg", 2048, "jpg"));
        photos.add(new FileLeaf("rodzina.jpg", 1536, "jpg"));
        photos.add(new FileLeaf("krajobraz.png", 3072, "png"));

        FolderComposite work = new FolderComposite("Praca");
        FolderComposite projects = new FolderComposite("Projekty");
        projects.add(new FileLeaf("Main.java", 45, "java"));
        projects.add(new FileLeaf("Utils.java", 32, "java"));

        work.add(projects);
        root.add(photos);
        root.add(work);

        System.out.println("\n📂 Struktura katalogów:");
        root.display(0);

        System.out.println("\n📊 Statystyki:");
        System.out.println("  Całkowity rozmiar: " + root.getSize() + " KB");
        System.out.println("  Liczba plików: " + root.countFiles());
    }

    // ========================================
    // 14. STATE PATTERN
    // ========================================

    /**
     * PRZED: Złożona logika warunkowa zależna od stanu
     * Problemy:
     * - Wielkie switch/if-else dla różnych stanów w każdej metodzie
     * - Trudność w dodawaniu nowych stanów (modyfikacja wielu metod)
     * - Rozproszona logika dla tego samego stanu po różnych metodach
     * - Naruszenie Open/Closed Principle
     * - Kod trudny do zrozumienia i utrzymania
     * - Wysokie ryzyko błędów przy dodawaniu stanów
     */
    static class VendingMachineBefore {
        private static final int NO_COIN = 0;
        private static final int HAS_COIN = 1;
        private static final int SOLD = 2;
        private static final int OUT_OF_STOCK = 3;

        private int state = NO_COIN;
        private int count = 0;

        public VendingMachineBefore(int count) {
            this.count = count;
            if (count > 0) {
                state = NO_COIN;
            } else {
                state = OUT_OF_STOCK;
            }
        }

        public void insertCoin() {
            // PROBLEM: Cała logika w jednej metodzie ze switch dla każdego stanu
            switch (state) {
                case NO_COIN:
                    System.out.println("Wrzucono monetę");
                    state = HAS_COIN;
                    break;
                case HAS_COIN:
                    System.out.println("Nie można wrzucić drugiej monety");
                    break;
                case SOLD:
                    System.out.println("Proszę czekać, wydajemy produkt");
                    break;
                case OUT_OF_STOCK:
                    System.out.println("Brak produktów");
                    break;
            }
        }

        public void selectProduct() {
            // PROBLEM: Duplikacja switch w każdej metodzie
            switch (state) {
                case NO_COIN:
                    System.out.println("Najpierw wrzuć monetę");
                    break;
                case HAS_COIN:
                    System.out.println("Wybrałeś produkt");
                    state = SOLD;
                    dispense();
                    break;
                case SOLD:
                    System.out.println("Już wydajemy produkt");
                    break;
                case OUT_OF_STOCK:
                    System.out.println("Brak produktów");
                    break;
            }
        }

        private void dispense() {
            // PROBLEM: I znowu switch...
            switch (state) {
                case SOLD:
                    System.out.println("Produkt został wydany");
                    count--;
                    if (count > 0) {
                        state = NO_COIN;
                    } else {
                        state = OUT_OF_STOCK;
                    }
                    break;
                default:
                    System.out.println("Błąd!");
                    break;
            }
        }
    }

    /**
     * PO: State Pattern - każdy stan jako osobna klasa
     * Zalety:
     * - Każdy stan w osobnej klasie - Single Responsibility Principle
     * - Łatwe dodawanie nowych stanów - tylko nowa klasa, bez modyfikacji istniejących
     * - Skoncentrowana logika dla każdego stanu w jednym miejscu
     * - Eliminacja switch/if-else - polimorfizm zamiast warunków
     * - Zgodność z Open/Closed Principle
     * - Przejścia między stanami są jawne i kontrolowane
     * - Łatwiejsze testowanie - każdy stan można testować osobno
     */

    interface VendingMachineState {
        void insertCoin(VendingMachineContext context);
        void selectProduct(VendingMachineContext context);
        void dispense(VendingMachineContext context);
        String getStateName();
    }

    static class VendingMachineContext {
        private VendingMachineState currentState;
        private final VendingMachineState noCoinState;
        private final VendingMachineState hasCoinState;
        private final VendingMachineState soldState;
        private int count;

        public VendingMachineContext(int count) {
            this.noCoinState = new NoCoinState();
            this.hasCoinState = new HasCoinState();
            this.soldState = new SoldState();
            this.count = count;
            this.currentState = count > 0 ? noCoinState : soldState;
        }

        public void insertCoin() { currentState.insertCoin(this); }
        public void selectProduct() {
            currentState.selectProduct(this);
            currentState.dispense(this);
        }

        public void setState(VendingMachineState state) {
            System.out.println("  [Stan: " + currentState.getStateName() + " → " + state.getStateName() + "]");
            this.currentState = state;
        }

        public void releaseProduct() {
            if (count > 0) {
                System.out.println("  🎁 Produkt wydany!");
                count--;
            }
        }

        public int getCount() { return count; }
        public VendingMachineState getNoCoinState() { return noCoinState; }
        public VendingMachineState getHasCoinState() { return hasCoinState; }
        public VendingMachineState getSoldState() { return soldState; }
    }

    static class NoCoinState implements VendingMachineState {
        @Override
        public void insertCoin(VendingMachineContext context) {
            System.out.println("💰 Wrzucono monetę");
            context.setState(context.getHasCoinState());
        }

        @Override
        public void selectProduct(VendingMachineContext context) {
            System.out.println("❌ Najpierw wrzuć monetę");
        }

        @Override
        public void dispense(VendingMachineContext context) {
            System.out.println("❌ Najpierw zapłać");
        }

        @Override
        public String getStateName() { return "Brak monety"; }
    }

    static class HasCoinState implements VendingMachineState {
        @Override
        public void insertCoin(VendingMachineContext context) {
            System.out.println("💰 Już masz monetę");
        }

        @Override
        public void selectProduct(VendingMachineContext context) {
            System.out.println("✅ Wybrałeś produkt");
            context.setState(context.getSoldState());
        }

        @Override
        public void dispense(VendingMachineContext context) {
            System.out.println("❌ Najpierw wybierz produkt");
        }

        @Override
        public String getStateName() { return "Ma monetę"; }
    }

    static class SoldState implements VendingMachineState {
        @Override
        public void insertCoin(VendingMachineContext context) {
            System.out.println("⏳ Proszę czekać...");
        }

        @Override
        public void selectProduct(VendingMachineContext context) {
            System.out.println("⏳ Już wydajemy...");
        }

        @Override
        public void dispense(VendingMachineContext context) {
            context.releaseProduct();
            if (context.getCount() > 0) {
                context.setState(context.getNoCoinState());
            }
        }

        @Override
        public String getStateName() { return "Sprzedaż"; }
    }

    private static void demonstrateStatePattern() {
        System.out.println("\n=== STATE PATTERN ===");

        System.out.println("🤖 Automat z napojami (2 produkty)");
        VendingMachineContext machine = new VendingMachineContext(2);

        System.out.println("\n--- Scenariusz 1: Normalny zakup ---");
        machine.insertCoin();
        machine.selectProduct();

        System.out.println("\n--- Scenariusz 2: Drugi zakup ---");
        machine.insertCoin();
        machine.selectProduct();
    }

    // ========================================
    // 15. ABSTRACT FACTORY PATTERN
    // ========================================

    /**
     * PRZED: Bezpośrednie tworzenie konkretnych klas z różnych rodzin
     * Problemy:
     * - Kod ściśle związany z konkretnymi implementacjami
     * - Trudność w zamianie całej rodziny obiektów (np. Windows → macOS)
     * - Brak gwarancji, że komponenty są ze sobą kompatybilne
     * - Naruszenie Dependency Inversion Principle
     * - Duplikacja logiki tworzenia w wielu miejscach
     * - Trudność w dodawaniu nowych platform
     */
    static class WindowsApplicationBefore {
        public void createUI() {
            // PROBLEM: Bezpośrednie tworzenie konkretnych klas Windows
            WindowsButton button = new WindowsButton();
            WindowsTextField textField = new WindowsTextField();
            WindowsCheckbox checkbox = new WindowsCheckbox();

            button.render();
            textField.render();
            checkbox.render();
        }
    }

    static class MacApplicationBefore {
        public void createUI() {
            // PROBLEM: Duplikacja kodu dla każdej platformy
            MacOSButton button = new MacOSButton();
            MacOSTextField textField = new MacOSTextField();
            MacOSCheckbox checkbox = new MacOSCheckbox();

            button.render();
            textField.render();
            checkbox.render();
        }
    }

    /**
     * PO: Abstract Factory Pattern - fabryka fabryk dla rodzin obiektów
     * Zalety:
     * - Kod niezależny od konkretnych implementacji - programowanie do interfejsów
     * - Łatwa zamiana całej rodziny obiektów (jedna zmiana fabryki)
     * - Gwarancja kompatybilności - komponenty z tej samej fabryki działają razem
     * - Zgodność z Dependency Inversion Principle
     * - Centralizacja logiki tworzenia
     * - Łatwe dodawanie nowych platform (nowa fabryka)
     * - Izolacja kodu od szczegółów implementacji
     * - Różnica vs Factory Method: tworzy RODZINY powiązanych obiektów
     */

    interface GUIFactory {
        Button createButton();
        TextField createTextField();
        Checkbox createCheckbox();
    }

    interface Button {
        void render();
        void onClick();
    }

    interface TextField {
        void render();
        void setText(String text);
    }

    interface Checkbox {
        void render();
        void check();
    }

    // Windows family
    static class WindowsFactory implements GUIFactory {
        @Override
        public Button createButton() { return new WindowsButton(); }

        @Override
        public TextField createTextField() { return new WindowsTextField(); }

        @Override
        public Checkbox createCheckbox() { return new WindowsCheckbox(); }
    }

    static class WindowsButton implements Button {
        @Override
        public void render() { System.out.println("  🔵 [Windows] Renderuję przycisk z płaskim designem"); }

        @Override
        public void onClick() { System.out.println("  🖱️  [Windows] Klik z efektem ripple"); }
    }

    static class WindowsTextField implements TextField {
        @Override
        public void render() { System.out.println("  📝 [Windows] Renderuję pole tekstowe z zaokrąglonymi rogami"); }

        @Override
        public void setText(String text) { System.out.println("  ✍️  [Windows] Ustawiam tekst: " + text); }
    }

    static class WindowsCheckbox implements Checkbox {
        @Override
        public void render() { System.out.println("  ☑️  [Windows] Renderuję checkbox z animacją"); }

        @Override
        public void check() { System.out.println("  ✅ [Windows] Checkbox zaznaczony z animacją"); }
    }

    // macOS family
    static class MacOSFactory implements GUIFactory {
        @Override
        public Button createButton() { return new MacOSButton(); }

        @Override
        public TextField createTextField() { return new MacOSTextField(); }

        @Override
        public Checkbox createCheckbox() { return new MacOSCheckbox(); }
    }

    static class MacOSButton implements Button {
        @Override
        public void render() { System.out.println("  🟦 [macOS] Renderuję przycisk z gradientem"); }

        @Override
        public void onClick() { System.out.println("  🖱️  [macOS] Klik z efektem światła"); }
    }

    static class MacOSTextField implements TextField {
        @Override
        public void render() { System.out.println("  📝 [macOS] Renderuję eleganckie pole tekstowe"); }

        @Override
        public void setText(String text) { System.out.println("  ✍️  [macOS] Ustawiam tekst: " + text); }
    }

    static class MacOSCheckbox implements Checkbox {
        @Override
        public void render() { System.out.println("  ☑️  [macOS] Renderuję checkbox w stylu Aqua"); }

        @Override
        public void check() { System.out.println("  ✅ [macOS] Checkbox zaznaczony"); }
    }

    // Application using factory
    static class Application {
        private final Button button;
        private final TextField textField;
        private final Checkbox checkbox;

        public Application(GUIFactory factory) {
            this.button = factory.createButton();
            this.textField = factory.createTextField();
            this.checkbox = factory.createCheckbox();
        }

        public void render() {
            button.render();
            textField.render();
            checkbox.render();
        }

        public void interact() {
            button.onClick();
            textField.setText("Hello World");
            checkbox.check();
        }
    }

    private static void demonstrateAbstractFactoryPattern() {
        System.out.println("\n=== ABSTRACT FACTORY PATTERN ===");

        System.out.println("\n--- Aplikacja na Windows ---");
        GUIFactory windowsFactory = new WindowsFactory();
        Application windowsApp = new Application(windowsFactory);
        windowsApp.render();
        windowsApp.interact();

        System.out.println("\n--- Aplikacja na macOS ---");
        GUIFactory macFactory = new MacOSFactory();
        Application macApp = new Application(macFactory);
        macApp.render();
        macApp.interact();
    }

    // ========================================
    // 16. PROTOTYPE PATTERN
    // ========================================

    /**
     * PRZED: Tworzenie nowych obiektów od zera za każdym razem
     * Problemy:
     * - Kosztowna inicjalizacja (ładowanie zasobów, konfiguracja)
     * - Duplikacja kodu inicjalizacyjnego
     * - Trudność w tworzeniu kopii złożonych obiektów
     * - Brak możliwości tworzenia obiektów na podstawie prototypu
     * - Konieczność znajomości wszystkich klas do utworzenia
     * - Problemy z deep copy mutowalnych pól
     */
    static class GameCharacterBefore {
        private String name;
        private int health;
        private int level;
        private List<String> inventory;
        private EquipmentBefore equipment;

        public GameCharacterBefore(String name, int health, int level) {
            // PROBLEM: Za każdym razem kosztowna inicjalizacja
            this.name = name;
            this.health = health;
            this.level = level;
            this.inventory = new ArrayList<>();
            this.equipment = new EquipmentBefore();

            // Symulacja kosztownej inicjalizacji
            loadCharacterAssets();
            initializeStats();
        }

        private void loadCharacterAssets() {
            // Kosztowne ładowanie grafik, animacji, dźwięków...
            System.out.println("Ładowanie zasobów postaci (kosztowne!)...");
        }

        private void initializeStats() {
            // Skomplikowane obliczenia statystyk bazowych
            System.out.println("Inicjalizacja statystyk...");
        }

        // PROBLEM: Brak możliwości łatwego skopiowania
    }

    static class EquipmentBefore {
        private String weapon = "Miecz";
        private String armor = "Zbroja";
        // PROBLEM: Jak skopiować ten obiekt?
    }

    /**
     * PO: Prototype Pattern - klonowanie zamiast tworzenia od zera
     * Zalety:
     * - Szybkie tworzenie przez klonowanie - omija kosztowną inicjalizację
     * - Ukrycie szczegółów tworzenia obiektów - klient nie musi znać klas
     * - Deep copy dla złożonych struktur - bezpieczne kopiowanie
     * - Możliwość tworzenia obiektów w runtime bez znajomości typów
     * - Redukcja liczby podklas (zamiast dziedziczenia - klonowanie i modyfikacja)
     * - Rejestr prototypów - cache gotowych do użycia obiektów
     * - Oszczędność zasobów - współdzielenie niezmienialnych części
     * - Różnica vs Factory: nie tworzy od zera, tylko kopiuje istniejący obiekt
     */

    interface Prototype extends Cloneable {
        Prototype clone();
    }

    static class GameCharacter implements Prototype {
        private String name;
        private int health;
        private int level;
        private List<String> inventory;
        private Equipment equipment;

        public GameCharacter(String name, int health, int level) {
            this.name = name;
            this.health = health;
            this.level = level;
            this.inventory = new ArrayList<>();
            this.equipment = new Equipment();
        }

        public void addItem(String item) {
            inventory.add(item);
        }

        @Override
        public GameCharacter clone() {
            try {
                GameCharacter cloned = (GameCharacter) super.clone();
                // Deep copy dla mutowalnych obiektów
                cloned.inventory = new ArrayList<>(this.inventory);
                cloned.equipment = this.equipment.clone();
                return cloned;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Klonowanie nie powiodło się", e);
            }
        }

        public void display() {
            System.out.printf("  👤 %s (HP: %d, Lvl: %d)%n", name, health, level);
            System.out.println("    Ekwipunek: " + inventory);
            System.out.println("    " + equipment);
        }

        public void setName(String name) { this.name = name; }
        public void setHealth(int health) { this.health = health; }
        public Equipment getEquipment() { return equipment; }
    }

    static class Equipment implements Cloneable {
        private String weapon = "Miecz";
        private String armor = "Zbroja";
        private String accessory = "Pierścień";

        @Override
        public Equipment clone() {
            try {
                return (Equipment) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        public void setWeapon(String weapon) { this.weapon = weapon; }

        @Override
        public String toString() {
            return String.format("Broń: %s, Zbroja: %s, Akcesorium: %s", weapon, armor, accessory);
        }
    }

    static class CharacterRegistry {
        private final Map<String, GameCharacter> prototypes = new HashMap<>();

        public void registerPrototype(String key, GameCharacter prototype) {
            prototypes.put(key, prototype);
        }

        public GameCharacter createCharacter(String key) {
            GameCharacter prototype = prototypes.get(key);
            if (prototype == null) {
                throw new IllegalArgumentException("Nieznany typ postaci: " + key);
            }
            return prototype.clone();
        }
    }

    private static void demonstratePrototypePattern() {
        System.out.println("\n=== PROTOTYPE PATTERN ===");

        // Tworzenie prototypów
        GameCharacter warriorPrototype = new GameCharacter("Wojownik", 100, 1);
        warriorPrototype.addItem("Miecz");
        warriorPrototype.addItem("Tarcza");

        GameCharacter magePrototype = new GameCharacter("Mag", 60, 1);
        magePrototype.addItem("Różdżka");
        magePrototype.addItem("Księga zaklęć");
        magePrototype.getEquipment().setWeapon("Różdżka");

        // Rejestr prototypów
        CharacterRegistry registry = new CharacterRegistry();
        registry.registerPrototype("warrior", warriorPrototype);
        registry.registerPrototype("mage", magePrototype);

        System.out.println("\n--- Tworzenie postaci przez klonowanie ---");

        GameCharacter player1 = registry.createCharacter("warrior");
        player1.setName("Aragorn");
        player1.setHealth(120);

        GameCharacter player2 = registry.createCharacter("warrior");
        player2.setName("Conan");
        player2.addItem("Topór bojowy");

        GameCharacter player3 = registry.createCharacter("mage");
        player3.setName("Gandalf");
        player3.setHealth(80);

        System.out.println("\n📊 Utworzone postacie:");
        player1.display();
        player2.display();
        player3.display();

        System.out.println("\n✅ Prototypy nie zostały zmodyfikowane:");
        warriorPrototype.display();
    }

    // ========================================
    // 17. BRIDGE PATTERN
    // ========================================

    /**
     * PRZED: Eksplozja klas przez dziedziczenie dla każdej kombinacji
     * Problemy:
     * - Potrzeba osobnej klasy dla każdej kombinacji (Shape x Color)
     * - Trudność w dodawaniu nowych wymiarów abstrakcji
     * - Naruszenie Single Responsibility Principle
     * - Silne sprzężenie między abstrakcją a implementacją
     * - Brak możliwości niezależnej zmiany hierarchii
     */
    static class RedCircleBefore {
        public void draw() {
            System.out.println("Rysowanie czerwonego koła");
        }
    }

    static class BlueCircleBefore {
        public void draw() {
            System.out.println("Rysowanie niebieskiego koła");
        }
    }

    static class RedSquareBefore {
        public void draw() {
            System.out.println("Rysowanie czerwonego kwadratu");
        }
    }

    static class BlueSquareBefore {
        public void draw() {
            System.out.println("Rysowanie niebieskiego kwadratu");
        }
    }

    // PROBLEM: Dla 3 kształtów i 3 kolorów = 9 klas!
    // Dla 5 kształtów i 5 kolorów = 25 klas!

    /**
     * PO: Bridge Pattern - oddzielenie abstrakcji od implementacji
     * Zalety:
     * - Niezależne hierarchie dla abstrakcji i implementacji
     * - Eliminacja eksplozji klas kombinatorycznych
     * - Możliwość dodawania nowych wymiarów bez modyfikacji istniejącego kodu
     * - Zgodność z Open/Closed Principle
     * - Runtime binding - można zmieniać implementację dynamicznie
     * - Ukrycie szczegółów implementacji przed klientem
     */

    // Implementor - interfejs implementacji
    interface DrawingAPI {
        void drawCircle(double x, double y, double radius);
        void drawRectangle(double x, double y, double width, double height);
    }

    // Concrete Implementors
    static class RedDrawingAPI implements DrawingAPI {
        @Override
        public void drawCircle(double x, double y, double radius) {
            System.out.printf("  🔴 [Red] Rysuję koło na (%.1f, %.1f) o promieniu %.1f%n", x, y, radius);
        }

        @Override
        public void drawRectangle(double x, double y, double width, double height) {
            System.out.printf("  🔴 [Red] Rysuję prostokąt na (%.1f, %.1f) o wymiarach %.1fx%.1f%n",
                    x, y, width, height);
        }
    }

    static class BlueDrawingAPI implements DrawingAPI {
        @Override
        public void drawCircle(double x, double y, double radius) {
            System.out.printf("  🔵 [Blue] Rysuję koło na (%.1f, %.1f) o promieniu %.1f%n", x, y, radius);
        }

        @Override
        public void drawRectangle(double x, double y, double width, double height) {
            System.out.printf("  🔵 [Blue] Rysuję prostokąt na (%.1f, %.1f) o wymiarach %.1fx%.1f%n",
                    x, y, width, height);
        }
    }

    static class GreenDrawingAPI implements DrawingAPI {
        @Override
        public void drawCircle(double x, double y, double radius) {
            System.out.printf("  🟢 [Green] Rysuję koło na (%.1f, %.1f) o promieniu %.1f%n", x, y, radius);
        }

        @Override
        public void drawRectangle(double x, double y, double width, double height) {
            System.out.printf("  🟢 [Green] Rysuję prostokąt na (%.1f, %.1f) o wymiarach %.1fx%.1f%n",
                    x, y, width, height);
        }
    }

    // Abstraction - kształt używający implementacji
    static abstract class Shape {
        protected DrawingAPI drawingAPI;

        protected Shape(DrawingAPI drawingAPI) {
            this.drawingAPI = drawingAPI;
        }

        public abstract void draw();
        public abstract void resize(double factor);

        // Możliwość zmiany implementacji w runtime
        public void setDrawingAPI(DrawingAPI drawingAPI) {
            this.drawingAPI = drawingAPI;
        }
    }

    // Refined Abstractions
    static class Circle extends Shape {
        private double x, y, radius;

        public Circle(double x, double y, double radius, DrawingAPI drawingAPI) {
            super(drawingAPI);
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        @Override
        public void draw() {
            drawingAPI.drawCircle(x, y, radius);
        }

        @Override
        public void resize(double factor) {
            radius *= factor;
            System.out.printf("  ↔️  Zmieniono rozmiar koła do promienia %.1f%n", radius);
        }
    }

    static class Rectangle extends Shape {
        private double x, y, width, height;

        public Rectangle(double x, double y, double width, double height, DrawingAPI drawingAPI) {
            super(drawingAPI);
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public void draw() {
            drawingAPI.drawRectangle(x, y, width, height);
        }

        @Override
        public void resize(double factor) {
            width *= factor;
            height *= factor;
            System.out.printf("  ↔️  Zmieniono rozmiar prostokąta do %.1fx%.1f%n", width, height);
        }
    }

    private static void demonstrateBridgePattern() {
        System.out.println("\n=== BRIDGE PATTERN ===");

        System.out.println("\n--- Tworzenie kształtów z różnymi implementacjami ---");
        Shape redCircle = new Circle(100, 100, 50, new RedDrawingAPI());
        Shape blueRectangle = new Rectangle(200, 150, 80, 60, new BlueDrawingAPI());
        Shape greenCircle = new Circle(50, 50, 30, new GreenDrawingAPI());

        redCircle.draw();
        blueRectangle.draw();
        greenCircle.draw();

        System.out.println("\n--- Zmiana rozmiaru ---");
        redCircle.resize(1.5);
        redCircle.draw();

        System.out.println("\n--- Zmiana implementacji w runtime ---");
        System.out.println("Zmieniam kolor czerwonego koła na niebieski:");
        redCircle.setDrawingAPI(new BlueDrawingAPI());
        redCircle.draw();

        System.out.println("\n✅ Zalety: " +
                "3 kolory + 2 kształty = tylko 5 klas zamiast 6! " +
                "Łatwo dodać nowy kolor lub kształt.");
    }

    // ========================================
    // 18. FLYWEIGHT PATTERN
    // ========================================

    /**
     * PRZED: Tworzenie tysięcy podobnych obiektów zabiera pamięć
     * Problemy:
     * - Wysokie zużycie pamięci przez duplikację danych
     * - Wolniejsze tworzenie obiektów
     * - Brak współdzielenia wspólnych danych (intrinsic state)
     * - Nieefektywne wykorzystanie zasobów
     */
    static class TreeBefore {
        private String type;
        private String texture;
        private String color;
        // PROBLEM: Te same dane dla tysięcy drzew tego samego typu!
        private int x, y;

        public TreeBefore(String type, String texture, String color, int x, int y) {
            this.type = type;
            this.texture = texture;
            this.color = color;
            this.x = x;
            this.y = y;
            // Symulacja ładowania zasobów
            System.out.println("Ładowanie zasobów dla drzewa (kosztowne!)");
        }

        public void render() {
            System.out.printf("Renderuję %s drzewo na (%d, %d)%n", type, x, y);
        }
    }

    /**
     * PO: Flyweight Pattern - współdzielenie wspólnych danych
     * Zalety:
     * - Drastyczna redukcja zużycia pamięci przez współdzielenie
     * - Szybsze tworzenie obiektów (cache)
     * - Separacja stanu wewnętrznego (intrinsic) od zewnętrznego (extrinsic)
     * - Efektywne zarządzanie dużą liczbą podobnych obiektów
     * - Szczególnie użyteczne w grach, edytorach graficznych
     */

    // Flyweight - współdzielony stan wewnętrzny (intrinsic state)
    static class TreeType {
        private final String name;
        private final String texture;
        private final String color;
        private final byte[] meshData;

        public TreeType(String name, String texture, String color) {
            this.name = name;
            this.texture = texture;
            this.color = color;
            this.meshData = new byte[1024]; // Symulacja dużych danych 3D
            System.out.printf("  💾 [Flyweight] Tworzenie nowego typu drzewa: %s (zajmuje pamięć!)%n", name);
        }

        public void render(int x, int y) {
            System.out.printf("  🌳 Renderuję %s %s drzewo na pozycji (%d, %d)%n",
                    color, name, x, y);
        }
    }

    // Context - unikalny stan zewnętrzny (extrinsic state)
    static class Tree {
        private final int x;
        private final int y;
        private final TreeType type; // Współdzielony Flyweight

        public Tree(int x, int y, TreeType type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public void render() {
            type.render(x, y);
        }
    }

    // Flyweight Factory - zarządza współdzielonymi obiektami
    static class TreeFactory {
        private static final Map<String, TreeType> treeTypes = new HashMap<>();

        public static TreeType getTreeType(String name, String texture, String color) {
            String key = name + "-" + texture + "-" + color;

            TreeType type = treeTypes.get(key);
            if (type == null) {
                type = new TreeType(name, texture, color);
                treeTypes.put(key, type);
            } else {
                System.out.printf("  ♻️  [Cache] Używam istniejącego typu: %s%n", name);
            }
            return type;
        }

        public static int getTreeTypeCount() {
            return treeTypes.size();
        }
    }

    // Forest - zarządza wszystkimi drzewami
    static class Forest {
        private final List<Tree> trees = new ArrayList<>();

        public void plantTree(int x, int y, String name, String texture, String color) {
            TreeType type = TreeFactory.getTreeType(name, texture, color);
            Tree tree = new Tree(x, y, type);
            trees.add(tree);
        }

        public void render() {
            System.out.println("\n🌲 Renderowanie lasu:");
            for (Tree tree : trees) {
                tree.render();
            }
        }

        public void showMemoryUsage() {
            System.out.println("\n📊 Statystyki pamięci:");
            System.out.println("  Liczba drzew: " + trees.size());
            System.out.println("  Liczba unikalnych typów (Flyweights): " +
                    TreeFactory.getTreeTypeCount());
            long memoryPerTreeType = 1024; // meshData + inne pola
            long totalMemory = TreeFactory.getTreeTypeCount() * memoryPerTreeType;
            System.out.println("  Zużycie pamięci: ~" + totalMemory + " bajtów");
            System.out.println("  💡 Bez Flyweight: " +
                    (trees.size() * memoryPerTreeType) + " bajtów");
            double saving = 100.0 * (1.0 - (double)TreeFactory.getTreeTypeCount() / trees.size());
            System.out.printf("  ✅ Oszczędność: %.1f%%%n", saving);
        }
    }

    private static void demonstrateFlyweightPattern() {
        System.out.println("\n=== FLYWEIGHT PATTERN ===");

        Forest forest = new Forest();

        System.out.println("🌱 Sadzenie lasu...");
        // Sadzenie wielu drzew tego samego typu
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                forest.plantTree(i * 10, i * 10, "Dąb", "oak_texture.png", "zielony");
            } else if (i % 3 == 1) {
                forest.plantTree(i * 10, i * 10, "Sosna", "pine_texture.png", "ciemnozielony");
            } else {
                forest.plantTree(i * 10, i * 10, "Brzoza", "birch_texture.png", "biały");
            }
        }

        forest.showMemoryUsage();

        System.out.println("\n--- Przykładowe renderowanie (pierwsze 5 drzew) ---");
        Forest smallForest = new Forest();
        smallForest.plantTree(10, 20, "Dąb", "oak_texture.png", "zielony");
        smallForest.plantTree(30, 40, "Dąb", "oak_texture.png", "zielony");
        smallForest.plantTree(50, 60, "Sosna", "pine_texture.png", "ciemnozielony");
        smallForest.plantTree(70, 80, "Brzoza", "birch_texture.png", "biały");
        smallForest.plantTree(90, 100, "Dąb", "oak_texture.png", "zielony");
        smallForest.render();
    }

    // ========================================
    // 19. ITERATOR PATTERN
    // ========================================

    /**
     * PRZED: Bezpośredni dostęp do wewnętrznej struktury kolekcji
     * Problemy:
     * - Naruszenie enkapsulacji - klient zna strukturę wewnętrzną
     * - Różne sposoby iteracji dla różnych kolekcji
     * - Trudność w zmianie implementacji kolekcji
     * - Brak możliwości wielu równoczesnych iteracji
     * - Duplikacja kodu iteracji w wielu miejscach
     */
    static class BookCollectionBefore {
        private List<String> books = new ArrayList<>();

        public void addBook(String book) {
            books.add(book);
        }

        // PROBLEM: Eksponowanie wewnętrznej struktury!
        public List<String> getBooks() {
            return books;
        }
    }

    static class ClientCodeBefore {
        public void processBooks(BookCollectionBefore collection) {
            // PROBLEM: Klient musi znać że to ArrayList
            List<String> books = collection.getBooks();
            for (String book : books) {
                System.out.println("Przetwarzam: " + book);
            }
            // Co jeśli zmienię ArrayList na inną strukturę?
        }
    }

    /**
     * PO: Iterator Pattern - jednolity interfejs do przechodzenia kolekcji
     * Zalety:
     * - Enkapsulacja - ukrycie struktury wewnętrznej kolekcji
     * - Jednolity interfejs do iteracji niezależnie od typu kolekcji
     * - Możliwość wielu równoczesnych iteracji (każdy iterator ma swój stan)
     * - Łatwa zmiana implementacji kolekcji bez wpływu na klientów
     * - Zgodność z Single Responsibility Principle
     * - Różne strategie iteracji (forward, backward, filtered)
     */

    interface Iterator<T> {
        boolean hasNext();
        T next();
        void reset();
    }

    interface IterableCollection<T> {
        Iterator<T> createIterator();
        Iterator<T> createReverseIterator();
    }

    // Konkretna kolekcja
    static class BookCollection implements IterableCollection<String> {
        private List<String> books = new ArrayList<>();

        public void addBook(String book) {
            books.add(book);
            System.out.println("  📚 Dodano książkę: " + book);
        }

        @Override
        public Iterator<String> createIterator() {
            return new ForwardIterator();
        }

        @Override
        public Iterator<String> createReverseIterator() {
            return new ReverseIterator();
        }

        // Konkretny iterator - forward
        private class ForwardIterator implements Iterator<String> {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < books.size();
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return books.get(currentIndex++);
            }

            @Override
            public void reset() {
                currentIndex = 0;
            }
        }

        // Konkretny iterator - reverse
        private class ReverseIterator implements Iterator<String> {
            private int currentIndex = books.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return books.get(currentIndex--);
            }

            @Override
            public void reset() {
                currentIndex = books.size() - 1;
            }
        }
    }

    // Inna implementacja kolekcji - array
    static class Magazine {
        private String title;
        private int year;

        public Magazine(String title, int year) {
            this.title = title;
            this.year = year;
        }

        @Override
        public String toString() {
            return title + " (" + year + ")";
        }

        public int getYear() {
            return year;
        }
    }

    static class MagazineCollection implements IterableCollection<Magazine> {
        private Magazine[] magazines;
        private int size = 0;

        public MagazineCollection(int capacity) {
            magazines = new Magazine[capacity];
        }

        public void addMagazine(Magazine magazine) {
            if (size < magazines.length) {
                magazines[size++] = magazine;
                System.out.println("  📰 Dodano magazyn: " + magazine);
            }
        }

        @Override
        public Iterator<Magazine> createIterator() {
            return new ArrayIterator();
        }

        @Override
        public Iterator<Magazine> createReverseIterator() {
            return new ReverseArrayIterator();
        }

        // Iterator dla tablicy
        private class ArrayIterator implements Iterator<Magazine> {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Magazine next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return magazines[currentIndex++];
            }

            @Override
            public void reset() {
                currentIndex = 0;
            }
        }

        private class ReverseArrayIterator implements Iterator<Magazine> {
            private int currentIndex = size - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public Magazine next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return magazines[currentIndex--];
            }

            @Override
            public void reset() {
                currentIndex = size - 1;
            }
        }
    }

    private static void demonstrateIteratorPattern() {
        System.out.println("\n=== ITERATOR PATTERN ===");

        System.out.println("\n--- Kolekcja książek (ArrayList) ---");
        BookCollection books = new BookCollection();
        books.addBook("Clean Code");
        books.addBook("Design Patterns");
        books.addBook("Effective Java");

        System.out.println("\n📖 Iteracja do przodu:");
        Iterator<String> bookIterator = books.createIterator();
        while (bookIterator.hasNext()) {
            System.out.println("  → " + bookIterator.next());
        }

        System.out.println("\n📖 Iteracja do tyłu:");
        Iterator<String> reverseIterator = books.createReverseIterator();
        while (reverseIterator.hasNext()) {
            System.out.println("  ← " + reverseIterator.next());
        }

        System.out.println("\n--- Kolekcja magazynów (Array) ---");
        MagazineCollection magazines = new MagazineCollection(5);
        magazines.addMagazine(new Magazine("National Geographic", 2023));
        magazines.addMagazine(new Magazine("Scientific American", 2024));
        magazines.addMagazine(new Magazine("Time", 2025));

        System.out.println("\n📰 Iteracja magazynów:");
        Iterator<Magazine> magIterator = magazines.createIterator();
        while (magIterator.hasNext()) {
            System.out.println("  → " + magIterator.next());
        }

        System.out.println("\n✅ Zaleta: Ten sam interfejs Iterator dla różnych kolekcji!");
    }

    // ========================================
    // 20. MEDIATOR PATTERN
    // ========================================

    /**
     * PRZED: Komponenty komunikują się bezpośrednio - chaotyczne zależności
     * Problemy:
     * - Silne sprzężenie między komponentami (każdy zna każdego)
     * - Trudność w zrozumieniu przepływu komunikacji
     * - Ciężka modyfikacja - zmiana w jednym komponencie wpływa na wiele innych
     * - Duplikacja logiki komunikacji
     * - Trudność w testowaniu komponentów osobno
     * - Naruszenie Dependency Inversion Principle
     */
    static class ChatUserBefore {
        private String name;
        private List<ChatUserBefore> contacts = new ArrayList<>();

        public ChatUserBefore(String name) {
            this.name = name;
        }

        public void addContact(ChatUserBefore user) {
            contacts.add(user);
        }

        public void sendMessage(ChatUserBefore recipient, String message) {
            // PROBLEM: Każdy użytkownik musi znać wszystkich innych
            System.out.println(name + " wysyła do " + recipient.name + ": " + message);
        }

        public void sendBroadcast(String message) {
            // PROBLEM: Logika rozgłaszania w każdym użytkowniku
            for (ChatUserBefore contact : contacts) {
                contact.receiveMessage(this, message);
            }
        }

        public void receiveMessage(ChatUserBefore sender, String message) {
            System.out.println(name + " otrzymał od " + sender.name + ": " + message);
        }
    }

    /**
     * PO: Mediator Pattern - centralizacja komunikacji
     * Zalety:
     * - Luźne sprzężenie - komponenty nie znają się bezpośrednio
     * - Centralizacja logiki komunikacji w jednym miejscu (mediator)
     * - Łatwiejsza modyfikacja - zmiany tylko w mediatorze
     * - Eliminacja zależności many-to-many na rzecz many-to-one
     * - Łatwiejsze testowanie - komponenty można testować z mock mediatorem
     * - Możliwość logowania, walidacji komunikatów w jednym miejscu
     * - Zgodność z Single Responsibility Principle
     */

    // Mediator interface
    interface ChatMediator {
        void sendMessage(String message, User sender);
        void addUser(User user);
        void removeUser(User user);
    }

    // Colleague - użytkownik komunikujący się przez mediatora
    static abstract class User {
        protected ChatMediator mediator;
        protected String name;

        public User(ChatMediator mediator, String name) {
            this.mediator = mediator;
            this.name = name;
        }

        public abstract void send(String message);
        public abstract void receive(String message, String senderName);

        public String getName() {
            return name;
        }
    }

    // Concrete Colleague
    static class ChatUser extends User {
        public ChatUser(ChatMediator mediator, String name) {
            super(mediator, name);
        }

        @Override
        public void send(String message) {
            System.out.printf("  💬 [%s] wysyła: \"%s\"%n", name, message);
            mediator.sendMessage(message, this);
        }

        @Override
        public void receive(String message, String senderName) {
            System.out.printf("  📨 [%s] otrzymał od [%s]: \"%s\"%n", name, senderName, message);
        }
    }

    // Concrete Mediator
    static class ChatRoom implements ChatMediator {
        private final String roomName;
        private final List<User> users = new ArrayList<>();
        private final List<String> messageHistory = new ArrayList<>();

        public ChatRoom(String roomName) {
            this.roomName = roomName;
        }

        @Override
        public void sendMessage(String message, User sender) {
            String fullMessage = sender.getName() + ": " + message;
            messageHistory.add(fullMessage);

            // Mediator zarządza logiką dostarczania wiadomości
            for (User user : users) {
                // Nie wysyłaj wiadomości do nadawcy
                if (user != sender) {
                    user.receive(message, sender.getName());
                }
            }
        }

        @Override
        public void addUser(User user) {
            users.add(user);
            System.out.printf("  ✅ [%s] dołączył do pokoju: %s%n", user.getName(), roomName);
        }

        @Override
        public void removeUser(User user) {
            users.remove(user);
            System.out.printf("  ❌ [%s] opuścił pokój: %s%n", user.getName(), roomName);
        }

        public void showHistory() {
            System.out.println("\n📜 Historia wiadomości w pokoju " + roomName + ":");
            for (String msg : messageHistory) {
                System.out.println("  " + msg);
            }
        }

        public int getUserCount() {
            return users.size();
        }
    }

    // Zaawansowany Mediator z dodatkowymi funkcjami
    static class ModeratedChatRoom extends ChatRoom {
        private final Set<String> bannedWords = new HashSet<>(
                Arrays.asList("spam", "reklama", "oszustwo")
        );

        public ModeratedChatRoom(String roomName) {
            super(roomName);
        }

        @Override
        public void sendMessage(String message, User sender) {
            // Moderacja wiadomości
            if (containsBannedWord(message)) {
                System.out.printf("  🚫 [Moderator] Wiadomość od [%s] zablokowana (niedozwolone słowa)%n",
                        sender.getName());
                sender.receive("Twoja wiadomość została zablokowana przez moderatora.", "System");
                return;
            }

            // Jeśli OK, wysyłaj normalnie
            super.sendMessage(message, sender);
        }

        private boolean containsBannedWord(String message) {
            String lowerMessage = message.toLowerCase();
            for (String word : bannedWords) {
                if (lowerMessage.contains(word)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void demonstrateMediatorPattern() {
        System.out.println("\n=== MEDIATOR PATTERN ===");

        System.out.println("\n--- Zwykły pokój czatu ---");
        ChatRoom generalRoom = new ChatRoom("Ogólny");

        User alice = new ChatUser(generalRoom, "Alice");
        User bob = new ChatUser(generalRoom, "Bob");
        User charlie = new ChatUser(generalRoom, "Charlie");

        generalRoom.addUser(alice);
        generalRoom.addUser(bob);
        generalRoom.addUser(charlie);

        System.out.println("\n💬 Konwersacja:");
        alice.send("Cześć wszystkim!");
        bob.send("Hej Alice!");
        charlie.send("Witajcie!");

        generalRoom.showHistory();

        System.out.println("\n--- Moderowany pokój czatu ---");
        ModeratedChatRoom vipRoom = new ModeratedChatRoom("VIP");

        User dave = new ChatUser(vipRoom, "Dave");
        User eve = new ChatUser(vipRoom, "Eve");

        vipRoom.addUser(dave);
        vipRoom.addUser(eve);

        System.out.println("\n💬 Próba wysłania wiadomości:");
        dave.send("Witam w pokoju VIP!");
        eve.send("Sprawdźcie moją spam stronę"); // Zostanie zablokowana
        eve.send("Dzięki za zaproszenie!");

        System.out.println("\n✅ Zaleta: Komponenty nie znają się - komunikacja przez mediatora!");
    }

    // ========================================
    // 21. MEMENTO PATTERN
    // ========================================

    /**
     * PRZED: Brak możliwości cofnięcia zmian, naruszenie enkapsulacji
     * Problemy:
     * - Brak historii zmian
     * - Niemożność przywrócenia poprzedniego stanu
     * - Naruszenie enkapsulacji - zewnętrzny dostęp do stanu wewnętrznego
     * - Trudność w implementacji undo/redo
     * - Brak snapshots stanu
     */
    static class SimpleTextEditorBefore {
        private String content;

        public void setContent(String content) {
            // PROBLEM: Stary stan jest tracony bezpowrotnie
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        // PROBLEM: Jak przywrócić poprzedni stan?
    }

    /**
     * PO: Memento Pattern - zapisywanie i przywracanie stanu
     * Zalety:
     * - Zachowanie historii stanów obiektu
     * - Możliwość undo/redo
     * - Enkapsulacja - stan jest ukryty w Memento
     * - Memento jest niemutowalny
     * - Originator ma pełną kontrolę nad tym co jest zapisywane
     * - Możliwość zapisywania snapshotów w dowolnym momencie
     * - Wsparcie dla Multiple Undo
     */

    // Memento - niemutowalny snapshot stanu
    static class EditorMemento {
        private final String content;
        private final int cursorPosition;
        private final long timestamp;

        public EditorMemento(String content, int cursorPosition) {
            this.content = content;
            this.cursorPosition = cursorPosition;
            this.timestamp = System.currentTimeMillis();
        }

        // Package-private gettery - tylko Originator ma dostęp
        String getContent() {
            return content;
        }

        int getCursorPosition() {
            return cursorPosition;
        }

        public String getInfo() {
            return String.format("Snapshot [%d znaków, kursor: %d]",
                    content.length(), cursorPosition);
        }
    }

    // Originator - obiekt którego stan chcemy zapisywać
    static class SimpleTextEditor {
        private String content = "";
        private int cursorPosition = 0;

        public void type(String text) {
            content = content.substring(0, cursorPosition) + text +
                    content.substring(cursorPosition);
            cursorPosition += text.length();
            System.out.printf("  ⌨️  Napisano: \"%s\" (kursor: %d)%n", text, cursorPosition);
        }

        public void delete(int count) {
            int start = Math.max(0, cursorPosition - count);
            String deleted = content.substring(start, cursorPosition);
            content = content.substring(0, start) + content.substring(cursorPosition);
            cursorPosition = start;
            System.out.printf("  🗑️  Usunięto: \"%s\" (kursor: %d)%n", deleted, cursorPosition);
        }

        public void moveCursor(int position) {
            cursorPosition = Math.max(0, Math.min(position, content.length()));
            System.out.printf("  ➡️  Kursor przesunięty na pozycję: %d%n", cursorPosition);
        }

        // Tworzenie Memento
        public EditorMemento save() {
            System.out.printf("  💾 Zapisuję stan: \"%s\" (kursor: %d)%n",
                    content, cursorPosition);
            return new EditorMemento(content, cursorPosition);
        }

        // Przywracanie ze Memento
        public void restore(EditorMemento memento) {
            this.content = memento.getContent();
            this.cursorPosition = memento.getCursorPosition();
            System.out.printf("  ↩️  Przywrócono stan: \"%s\" (kursor: %d)%n",
                    content, cursorPosition);
        }

        public String getContent() {
            return content;
        }

        public void display() {
            System.out.println("  📄 Zawartość: \"" + content + "\"");
            System.out.println("  📍 Pozycja kursora: " + cursorPosition);
        }
    }

    // Caretaker - zarządza Memento (historia)
    static class EditorHistory {
        private final Stack<EditorMemento> history = new Stack<>();
        private final Stack<EditorMemento> redoStack = new Stack<>();

        public void save(EditorMemento memento) {
            history.push(memento);
            redoStack.clear(); // Nowe działanie czyści redo
            System.out.println("  ✅ Stan zapisany w historii");
        }

        public EditorMemento undo() {
            if (history.isEmpty()) {
                System.out.println("  ⚠️  Brak stanów do cofnięcia");
                return null;
            }
            EditorMemento memento = history.pop();
            redoStack.push(memento);
            System.out.println("  ⬅️  Cofnięto do: " + memento.getInfo());
            return history.isEmpty() ? memento : history.peek();
        }

        public EditorMemento redo() {
            if (redoStack.isEmpty()) {
                System.out.println("  ⚠️  Brak stanów do ponowienia");
                return null;
            }
            EditorMemento memento = redoStack.pop();
            history.push(memento);
            System.out.println("  ➡️  Ponowiono: " + memento.getInfo());
            return memento;
        }

        public void showHistory() {
            System.out.println("\n  📚 Historia stanów:");
            if (history.isEmpty()) {
                System.out.println("    (pusta)");
            } else {
                int i = 1;
                for (EditorMemento memento : history) {
                    System.out.println("    " + i++ + ". " + memento.getInfo());
                }
            }
        }
    }

    private static void demonstrateMementoPattern() {
        System.out.println("\n=== MEMENTO PATTERN ===");

        SimpleTextEditor editor = new SimpleTextEditor();
        EditorHistory history = new EditorHistory();

        System.out.println("\n--- Edycja dokumentu ---");
        editor.type("Witaj ");
        history.save(editor.save());

        editor.type("świecie!");
        history.save(editor.save());

        editor.moveCursor(6);
        editor.type("piękny ");
        history.save(editor.save());

        System.out.println("\n📄 Aktualny stan:");
        editor.display();

        history.showHistory();

        System.out.println("\n--- Cofanie zmian (Undo) ---");
        EditorMemento memento = history.undo();
        if (memento != null) {
            editor.restore(memento);
            editor.display();
        }

        memento = history.undo();
        if (memento != null) {
            editor.restore(memento);
            editor.display();
        }

        System.out.println("\n--- Ponowne wykonanie (Redo) ---");
        memento = history.redo();
        if (memento != null) {
            editor.restore(memento);
            editor.display();
        }

        System.out.println("\n✅ Zaleta: Pełna kontrola nad historią zmian z zachowaniem enkapsulacji!");
    }

    // ========================================
    // 22. VISITOR PATTERN
    // ========================================

    /**
     * PRZED: Dodawanie nowych operacji wymaga modyfikacji każdej klasy
     * Problemy:
     * - Naruszenie Open/Closed Principle - klasy muszą być modyfikowane
     * - Rozproszenie logiki operacji po wielu klasach
     * - Trudność w dodawaniu nowych operacji
     * - Duplikacja kodu dla podobnych operacji
     * - Silne sprzężenie między strukturą a operacjami
     */
    static abstract class ShapeBefore {
        public abstract void draw();
        // PROBLEM: Co jeśli chcemy dodać export do XML, JSON, SVG?
        // Każda nowa operacja wymaga modyfikacji wszystkich klas!
    }

    static class CircleBefore extends ShapeBefore {
        @Override
        public void draw() {
            System.out.println("Rysowanie koła");
        }
        // Trzeba by dodać: exportToXML(), exportToJSON(), exportToSVG()...
    }

    static class SquareBefore extends ShapeBefore {
        @Override
        public void draw() {
            System.out.println("Rysowanie kwadratu");
        }
        // I tutaj też wszystkie metody...
    }

    /**
     * PO: Visitor Pattern - operacje w osobnych klasach wizytorów
     * Zalety:
     * - Łatwe dodawanie nowych operacji - nowy visitor, bez modyfikacji klas
     * - Zgodność z Open/Closed Principle
     * - Separacja algorytmów od struktury obiektów
     * - Skupienie powiązanej logiki w jednym miejscu (visitor)
     * - Double dispatch - wywołanie odpowiedniej metody na podstawie typów
     * - Możliwość zbierania informacji podczas odwiedzania
     */

    // Element interface
    interface ShapeElement {
        void accept(ShapeVisitor visitor);
    }

    // Visitor interface
    interface ShapeVisitor {
        void visitCircle(CircleElement circle);
        void visitRectangle(RectangleElement rectangle);
        void visitTriangle(TriangleElement triangle);
    }

    // Concrete Elements
    static class CircleElement implements ShapeElement {
        private final double radius;
        private final double x, y;

        public CircleElement(double x, double y, double radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visitCircle(this);
        }

        public double getRadius() { return radius; }
        public double getX() { return x; }
        public double getY() { return y; }
    }

    static class RectangleElement implements ShapeElement {
        private final double width, height;
        private final double x, y;

        public RectangleElement(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visitRectangle(this);
        }

        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public double getX() { return x; }
        public double getY() { return y; }
    }

    static class TriangleElement implements ShapeElement {
        private final double base, height;
        private final double x, y;

        public TriangleElement(double x, double y, double base, double height) {
            this.x = x;
            this.y = y;
            this.base = base;
            this.height = height;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visitTriangle(this);
        }

        public double getBase() { return base; }
        public double getHeight() { return height; }
        public double getX() { return x; }
        public double getY() { return y; }
    }

    // Concrete Visitors - różne operacje
    static class AreaCalculatorVisitor implements ShapeVisitor {
        private double totalArea = 0;

        @Override
        public void visitCircle(CircleElement circle) {
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            totalArea += area;
            System.out.printf("  🔵 Koło: pole = %.2f%n", area);
        }

        @Override
        public void visitRectangle(RectangleElement rectangle) {
            double area = rectangle.getWidth() * rectangle.getHeight();
            totalArea += area;
            System.out.printf("  ⬛ Prostokąt: pole = %.2f%n", area);
        }

        @Override
        public void visitTriangle(TriangleElement triangle) {
            double area = 0.5 * triangle.getBase() * triangle.getHeight();
            totalArea += area;
            System.out.printf("  🔺 Trójkąt: pole = %.2f%n", area);
        }

        public double getTotalArea() {
            return totalArea;
        }
    }

    static class XMLExportVisitor implements ShapeVisitor {
        private final StringBuilder xml = new StringBuilder();

        public XMLExportVisitor() {
            xml.append("<?xml version=\"1.0\"?>\n<shapes>\n");
        }

        @Override
        public void visitCircle(CircleElement circle) {
            xml.append(String.format("  <circle x=\"%.1f\" y=\"%.1f\" radius=\"%.1f\"/>\n",
                    circle.getX(), circle.getY(), circle.getRadius()));
        }

        @Override
        public void visitRectangle(RectangleElement rectangle) {
            xml.append(String.format("  <rectangle x=\"%.1f\" y=\"%.1f\" width=\"%.1f\" height=\"%.1f\"/>\n",
                    rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()));
        }

        @Override
        public void visitTriangle(TriangleElement triangle) {
            xml.append(String.format("  <triangle x=\"%.1f\" y=\"%.1f\" base=\"%.1f\" height=\"%.1f\"/>\n",
                    triangle.getX(), triangle.getY(), triangle.getBase(), triangle.getHeight()));
        }

        public String getXML() {
            return xml.toString() + "</shapes>";
        }
    }

    static class DrawVisitor implements ShapeVisitor {
        @Override
        public void visitCircle(CircleElement circle) {
            System.out.printf("  🎨 Rysuję koło na (%.1f, %.1f) o promieniu %.1f%n",
                    circle.getX(), circle.getY(), circle.getRadius());
        }

        @Override
        public void visitRectangle(RectangleElement rectangle) {
            System.out.printf("  🎨 Rysuję prostokąt na (%.1f, %.1f) o wymiarach %.1fx%.1f%n",
                    rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }

        @Override
        public void visitTriangle(TriangleElement triangle) {
            System.out.printf("  🎨 Rysuję trójkąt na (%.1f, %.1f) o podstawie %.1f i wysokości %.1f%n",
                    triangle.getX(), triangle.getY(), triangle.getBase(), triangle.getHeight());
        }
    }

    private static void demonstrateVisitorPattern() {
        System.out.println("\n=== VISITOR PATTERN ===");

        // Tworzenie struktury obiektów
        List<ShapeElement> shapes = Arrays.asList(
                new CircleElement(10, 10, 5),
                new RectangleElement(20, 20, 10, 8),
                new TriangleElement(30, 30, 6, 4),
                new CircleElement(40, 40, 3)
        );

        System.out.println("\n--- Operacja 1: Obliczanie pól ---");
        AreaCalculatorVisitor areaCalculator = new AreaCalculatorVisitor();
        for (ShapeElement shape : shapes) {
            shape.accept(areaCalculator);
        }
        System.out.printf("\n  📊 Suma pól wszystkich figur: %.2f%n", areaCalculator.getTotalArea());

        System.out.println("\n--- Operacja 2: Export do XML ---");
        XMLExportVisitor xmlExporter = new XMLExportVisitor();
        for (ShapeElement shape : shapes) {
            shape.accept(xmlExporter);
        }
        System.out.println(xmlExporter.getXML());

        System.out.println("\n--- Operacja 3: Rysowanie ---");
        DrawVisitor drawer = new DrawVisitor();
        for (ShapeElement shape : shapes) {
            shape.accept(drawer);
        }

        System.out.println("\n✅ Zaleta: Dodanie nowej operacji = nowy visitor, bez modyfikacji klas elementów!");
    }

    // ========================================
    // 23. INTERPRETER PATTERN
    // ========================================

    /**
     * PRZED: Parsowanie i ewaluacja wyrażeń w jednej monolitycznej klasie
     * Problemy:
     * - Trudność w obsłudze złożonych wyrażeń
     * - Brak możliwości kompozycji wyrażeń
     * - Ciężka rozbudowa o nowe operacje
     * - Duplikacja logiki parsowania
     * - Trudność w testowaniu poszczególnych elementów
     */
    static class CalculatorBefore {
        public int evaluate(String expression) {
            // PROBLEM: Parsowanie i obliczenia w jednej metodzie
            // Trudne do rozbudowy i utrzymania
            String[] parts = expression.split(" ");
            int result = Integer.parseInt(parts[0]);

            for (int i = 1; i < parts.length; i += 2) {
                String operator = parts[i];
                int operand = Integer.parseInt(parts[i + 1]);

                if (operator.equals("+")) {
                    result += operand;
                } else if (operator.equals("-")) {
                    result -= operand;
                }
            }
            return result;
        }
    }

    /**
     * PO: Interpreter Pattern - gramatyka jako hierarchia klas
     * Zalety:
     * - Łatwe reprezentowanie gramatyki języka
     * - Kompozycja wyrażeń w drzewo składniowe
     * - Łatwe dodawanie nowych reguł gramatycznych
     * - Możliwość wielokrotnej ewaluacji tego samego wyrażenia
     * - Separacja parsowania od ewaluacji
     * - Możliwość optymalizacji drzewa wyrażeń
     * Zastosowanie: DSL, języki zapytań, walidatory reguł
     */

    // Abstract Expression
    interface Expression {
        int interpret(Map<String, Integer> context);
        String toString();
    }

    // Terminal Expression - liczba
    static class NumberExpression implements Expression {
        private final int number;

        public NumberExpression(int number) {
            this.number = number;
        }

        @Override
        public int interpret(Map<String, Integer> context) {
            return number;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }
    }

    // Terminal Expression - zmienna
    static class VariableExpression implements Expression {
        private final String name;

        public VariableExpression(String name) {
            this.name = name;
        }

        @Override
        public int interpret(Map<String, Integer> context) {
            return context.getOrDefault(name, 0);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Non-terminal Expression - dodawanie
    static class AddExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public AddExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) + right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " + " + right + ")";
        }
    }

    // Non-terminal Expression - odejmowanie
    static class SubtractExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public SubtractExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) - right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " - " + right + ")";
        }
    }

    // Non-terminal Expression - mnożenie
    static class MultiplyExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public MultiplyExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) * right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " * " + right + ")";
        }
    }

    // Parser - buduje drzewo wyrażeń
    static class ExpressionParser {
        public Expression parse(String input) {
            // Uproszczony parser dla notacji postfiksowej (RPN)
            Stack<Expression> stack = new Stack<>();
            String[] tokens = input.split(" ");

            for (String token : tokens) {
                if (token.matches("\\d+")) {
                    // Liczba
                    stack.push(new NumberExpression(Integer.parseInt(token)));
                } else if (token.matches("[a-zA-Z]+")) {
                    // Zmienna
                    stack.push(new VariableExpression(token));
                } else if (token.equals("+")) {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new AddExpression(left, right));
                } else if (token.equals("-")) {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new SubtractExpression(left, right));
                } else if (token.equals("*")) {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    stack.push(new MultiplyExpression(left, right));
                }
            }

            return stack.pop();
        }
    }

    // Zaawansowany przykład - interpreter reguł biznesowych
    interface Rule {
        boolean evaluate(Map<String, Object> context);
        String getDescription();
    }

    static class AgeRule implements Rule {
        private final int minAge;

        public AgeRule(int minAge) {
            this.minAge = minAge;
        }

        @Override
        public boolean evaluate(Map<String, Object> context) {
            Integer age = (Integer) context.get("age");
            return age != null && age >= minAge;
        }

        @Override
        public String getDescription() {
            return "Wiek >= " + minAge;
        }
    }

    static class IncomeRule implements Rule {
        private final double minIncome;

        public IncomeRule(double minIncome) {
            this.minIncome = minIncome;
        }

        @Override
        public boolean evaluate(Map<String, Object> context) {
            Double income = (Double) context.get("income");
            return income != null && income >= minIncome;
        }

        @Override
        public String getDescription() {
            return "Dochód >= " + minIncome;
        }
    }

    static class AndRule implements Rule {
        private final Rule left;
        private final Rule right;

        public AndRule(Rule left, Rule right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean evaluate(Map<String, Object> context) {
            return left.evaluate(context) && right.evaluate(context);
        }

        @Override
        public String getDescription() {
            return "(" + left.getDescription() + " AND " + right.getDescription() + ")";
        }
    }

    static class OrRule implements Rule {
        private final Rule left;
        private final Rule right;

        public OrRule(Rule left, Rule right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean evaluate(Map<String, Object> context) {
            return left.evaluate(context) || right.evaluate(context);
        }

        @Override
        public String getDescription() {
            return "(" + left.getDescription() + " OR " + right.getDescription() + ")";
        }
    }

    private static void demonstrateInterpreterPattern() {
        System.out.println("\n=== INTERPRETER PATTERN ===");

        System.out.println("\n--- Przykład 1: Interpreter wyrażeń matematycznych ---");
        ExpressionParser parser = new ExpressionParser();

        // Wyrażenie w notacji postfiksowej (RPN): "5 3 + 2 *"  = (5 + 3) * 2 = 16
        String rpn1 = "5 3 + 2 *";
        Expression expr1 = parser.parse(rpn1);
        System.out.println("  📝 Wyrażenie: " + expr1);
        System.out.println("  🔢 Wynik: " + expr1.interpret(new HashMap<>()));

        // Wyrażenie ze zmiennymi: "x y + 10 *"
        String rpn2 = "x y + 10 *";
        Expression expr2 = parser.parse(rpn2);
        System.out.println("\n  📝 Wyrażenie: " + expr2);

        Map<String, Integer> context = new HashMap<>();
        context.put("x", 5);
        context.put("y", 3);
        System.out.println("  📋 Kontekst: x=5, y=3");
        System.out.println("  🔢 Wynik: " + expr2.interpret(context));

        System.out.println("\n--- Przykład 2: Interpreter reguł biznesowych ---");

        // Reguła: (Wiek >= 18) AND (Dochód >= 30000)
        Rule adultWithIncome = new AndRule(
                new AgeRule(18),
                new IncomeRule(30000)
        );

        // Reguła: (Wiek >= 65) OR (Dochód >= 100000)
        Rule seniorOrRich = new OrRule(
                new AgeRule(65),
                new IncomeRule(100000)
        );

        System.out.println("  📋 Reguła 1: " + adultWithIncome.getDescription());
        System.out.println("  📋 Reguła 2: " + seniorOrRich.getDescription());

        // Testowanie
        Map<String, Object> person1 = new HashMap<>();
        person1.put("age", 25);
        person1.put("income", 35000.0);

        Map<String, Object> person2 = new HashMap<>();
        person2.put("age", 16);
        person2.put("income", 50000.0);

        Map<String, Object> person3 = new HashMap<>();
        person3.put("age", 70);
        person3.put("income", 20000.0);

        System.out.println("\n  👤 Osoba 1 (25 lat, 35000 PLN):");
        System.out.println("    Reguła 1: " + (adultWithIncome.evaluate(person1) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));
        System.out.println("    Reguła 2: " + (seniorOrRich.evaluate(person1) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));

        System.out.println("\n  👤 Osoba 2 (16 lat, 50000 PLN):");
        System.out.println("    Reguła 1: " + (adultWithIncome.evaluate(person2) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));
        System.out.println("    Reguła 2: " + (seniorOrRich.evaluate(person2) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));

        System.out.println("\n  👤 Osoba 3 (70 lat, 20000 PLN):");
        System.out.println("    Reguła 1: " + (adultWithIncome.evaluate(person3) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));
        System.out.println("    Reguła 2: " + (seniorOrRich.evaluate(person3) ? "✅ SPEŁNIONA" : "❌ NIESPEŁNIONA"));

        System.out.println("\n✅ Zaleta: Złożone reguły jako kompozycja prostych wyrażeń!");
    }

}
