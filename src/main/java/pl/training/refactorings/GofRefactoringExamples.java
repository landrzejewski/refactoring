package pl.training.refactorings;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GofRefactoringExamples {

    public static void main(String[] args) {
        // demonstrateSingletonPattern();
        // demonstrateFactoryMethodPattern();
        // demonstrateStrategyPattern();
        // demonstrateObserverPattern();
        // demonstrateDecoratorPattern();
        demonstrateBuilderPattern();
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

        // Wewnętrzna klasa nie jest ładowana, dopóki nie zostanie wywołana getInstance()
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
        var db1 = DatabaseConnectionAfter.getInstance();
        var db2 = DatabaseConnectionAfter.getInstance();
        System.out.println("Czy to ta sama instancja? " + (db1 == db2));
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
                var pdf = new PdfDocumentBefore(content);
                pdf.open();
                pdf.render();
            } else if (type.equals("WORD")) {
                var word = new WordDocumentBefore(content);
                word.open();
                word.render();
            } else if (type.equals("HTML")) {
                var html = new HtmlDocumentBefore(content);
                html.open();
                html.render();
            }
        }

    }

    static class PdfDocumentBefore {

        private String content;

        public PdfDocumentBefore(String content) {
            this.content = content;
        }

        public void open() {
            System.out.println("Otwieranie PDF...");
        }

        public void render() {
            System.out.println("Renderowanie PDF: " + content);
        }

    }

    static class WordDocumentBefore {

        private String content;

        public WordDocumentBefore(String content) {
            this.content = content;
        }

        public void open() {
            System.out.println("Otwieranie Word...");
        }

        public void render() {
            System.out.println("Renderowanie Word: " + content);
        }

    }

    static class HtmlDocumentBefore {

        private String content;

        public HtmlDocumentBefore(String content) {
            this.content = content;
        }

        public void open() {
            System.out.println("Otwieranie HTML...");
        }

        public void render() {
            System.out.println("Renderowanie HTML: " + content);
        }

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

        public PdfDocument(String content) {
            this.content = content;
        }

        @Override
        public void open() {
            System.out.println("Otwieranie PDF dokumentu...");
        }

        @Override
        public void render() {
            System.out.println("Renderowanie PDF z zawartością: " + content);
        }

        @Override
        public void close() {
            System.out.println("Zamykanie PDF...");
        }

    }

    static class WordDocument implements Document {

        private final String content;

        public WordDocument(String content) {
            this.content = content;
        }

        @Override
        public void open() {
            System.out.println("Otwieranie Word dokumentu...");
        }

        @Override
        public void render() {
            System.out.println("Renderowanie Word z zawartością: " + content);
        }

        @Override
        public void close() {
            System.out.println("Zamykanie Word...");
        }

    }

    static class HtmlDocument implements Document {

        private final String content;

        public HtmlDocument(String content) {
            this.content = content;
        }

        @Override
        public void open() {
            System.out.println("Otwieranie HTML dokumentu...");
        }

        @Override
        public void render() {
            System.out.println("Renderowanie HTML z zawartością: " + content);
        }

        @Override
        public void close() {
            System.out.println("Zamykanie HTML...");
        }

    }

    // Abstract Factory definiujący factory method
    static interface DocumentFactory {

        // Factory Method - podklasy decydują, jaki typ dokumentu utworzyć
        Document createDocument(String content);

    }

    static class PdfDocumentFactory implements DocumentFactory {

        @Override
        public Document createDocument(String content) {
            return new PdfDocument(content);
        }

    }

    static class WordDocumentFactory implements DocumentFactory {

        @Override
        public Document createDocument(String content) {
            return new WordDocument(content);
        }

    }

    static class HtmlDocumentFactory implements DocumentFactory {

        @Override
        public Document createDocument(String content) {
            return new HtmlDocument(content);
        }

    }

    private static void demonstrateFactoryMethodPattern() {
        System.out.println("\n=== FACTORY METHOD PATTERN ===");
        DocumentFactory documentFactory = new WordDocumentFactory(); // new PdfDocumentFactory();

        var document = documentFactory.createDocument("Raport roczny 2025");
        document.open();
        document.render();
        document.close();
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

    }

    static class QuickSortStrategy implements SortingStrategy {

        @Override
        public void sort(List<Integer> data) {
            System.out.println("Wykonuję QuickSort...");
            quickSortImpl(data, 0, data.size() - 1);
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
            strategy.sort(data);
        }

    }

    private static void demonstrateStrategyPattern() {
        System.out.println("\n=== STRATEGY PATTERN ===");
        var data = new ArrayList<>(Arrays.asList(64, 34, 25, 12, 22, 11, 90));

        var sorter = new DataSorterAfter(new QuickSortStrategy());
        var data1 = new ArrayList<>(data);
        sorter.sortData(data1);
        System.out.println("Posortowane dane: " + data1);

        // Zmiana strategii w runtime
        sorter.setStrategy(new MergeSortStrategy());
        var data2 = new ArrayList<>(data);
        sorter.sortData(data2);
        System.out.println("Posortowane dane: " + data2);
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
        var market = new StockMarketAfter();

        var emailObserver = new EmailNotifierObserver("investor@example.com");
        var smsObserver = new SMSNotifierObserver("+48123456789");
        var dashboardObserver = new DashboardObserver();

        market.attach(emailObserver);
        market.attach(smsObserver);
        market.attach(dashboardObserver);

        market.setStockPrice("AAPL", 150.00);

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
        var coffee1 = new BasicCoffee();
        System.out.println(coffee1.getDescription() + " - Koszt: " + coffee1.getCost() + " PLN");

        // Kawa z mlekiem
        var coffee2 = new MilkDecorator(new BasicCoffee());
        System.out.println(coffee2.getDescription() + " - Koszt: " + coffee2.getCost() + " PLN");

        // Kawa z mlekiem, cukrem i karmelem
        var coffee3 = new CaramelDecorator(
                new SugarDecorator(
                        new MilkDecorator(
                                new BasicCoffee())));
        System.out.println(coffee3.getDescription() + " - Koszt: " + coffee3.getCost() + " PLN");

        // Deluxe kawa z wszystkimi dodatkami
        var coffee4 = new WhippedCreamDecorator(
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
        var request1 = new HttpRequest.Builder("https://api.example.com/users")
                .build();
        request1.execute();

        System.out.println();

        // Złożony request z wieloma parametrami - czytelny kod!
        var request2 = new HttpRequest.Builder("https://api.example.com/data")
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
            var invalidRequest = new HttpRequest.Builder("https://api.example.com/update")
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

    /*private static void demonstrateAdapterPattern() {
        System.out.println("\n=== ADAPTER PATTERN ===");

        // Używamy starego systemu przez adapter
        System.out.println("--- Używam legacy systemu przez adapter ---");
        var oldGateway = new OldPaymentGateway();
        var adaptedProcessor = new PaymentGatewayAdapter(oldGateway);
        var service1 = new PaymentService(adaptedProcessor);
        service1.executePayment("CUST-123", 299.99, "PLN", "Zakup laptopa");

        System.out.println("\n--- Używam nowoczesnego systemu Stripe ---");
        var stripeProcessor = new StripePaymentProcessor();
        var service2 = new PaymentService(stripeProcessor);
        service2.executePayment("CUST-456", 149.99, "PLN", "Subskrypcja roczna");
    }*/

}