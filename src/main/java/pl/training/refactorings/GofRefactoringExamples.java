package pl.training.refactorings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GofRefactoringExamples {

    public static void main(String[] args) {
        // demonstrateSingletonPattern();
        // demonstrateFactoryMethodPattern();
        demonstrateStrategyPattern();
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

    /*private static void demonstrateObserverPattern() {
        System.out.println("\n=== OBSERVER PATTERN ===");
        var market = new StockMarketAfter();

        // Dynamiczne dodawanie obserwatorów
        var emailObserver = new EmailNotifierObserver("investor@example.com");
        var smsObserver = new SMSNotifierObserver("+48123456789");
        var dashboardObserver = new DashboardObserver();

        market.attach(emailObserver);
        market.attach(smsObserver);
        market.attach(dashboardObserver);

        // Symulacja zmian cen akcji
        market.setStockPrice("AAPL", 150.00);
        market.setStockPrice("AAPL", 158.00);
        market.setStockPrice("AAPL", 175.00);

        // Można dynamicznie usunąć obserwatora
        market.detach(smsObserver);
        market.setStockPrice("AAPL", 170.00);
    }*/

}