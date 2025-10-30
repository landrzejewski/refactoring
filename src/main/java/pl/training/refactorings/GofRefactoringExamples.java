package pl.training.refactorings;

public class GofRefactoringExamples {

    public static void main(String[] args) {
        demonstrateSingletonPattern();
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

    private static void demonstrateFactoryMethodPattern() {
        System.out.println("\n=== FACTORY METHOD PATTERN ===");
        var pdfFactory = new PdfDocumentFactory();
        pdfFactory.processDocument("Raport roczny 2025");

        var wordFactory = new WordDocumentFactory();
        wordFactory.processDocument("Dokument biznesowy");
    }

}