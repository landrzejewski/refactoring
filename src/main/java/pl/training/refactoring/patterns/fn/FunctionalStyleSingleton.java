package pl.training.refactoring.patterns.fn;

import java.util.function.Supplier;

public class FunctionalStyleSingleton {
    public static void main(String[] args) {
        Supplier<DatabaseConnection> connection = memoize(DatabaseConnection::new);

        DatabaseConnection c1 = connection.get();
        DatabaseConnection c2 = connection.get();

        System.out.println(c1 == c2); // true
    }

    static class DatabaseConnection {
        DatabaseConnection() { System.out.println("Connecting to DB..."); }
    }

    static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return new Supplier<T>() {
            private T instance;
            public T get() {
                synchronized (this) {
                    if (instance == null) {
                        instance = supplier.get();
                    }
                }
                return instance;
            }
        };
    }
}
