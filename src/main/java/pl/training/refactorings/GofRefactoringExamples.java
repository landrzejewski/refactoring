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

    private static void demonstrateSingletonPattern() {
        System.out.println("\n=== SINGLETON PATTERN ===");
        DatabaseConnectionAfter db1 = DatabaseConnectionAfter.getInstance();
        DatabaseConnectionAfter db2 = DatabaseConnectionAfter.getInstance();
        System.out.println("Czy to ta sama instancja? " + (db1 == db2));
    }

}