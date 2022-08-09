package pl.training.refactoring.patterns.behavioral.iterator;

import java.util.Set;

public class Application {

    public static void main(String[] args) {
        var collection = Set.of(1, 2, 3, 4, 5); // List.of(1, 2, 3, 4, 5);
        var iterator = collection.iterator();
        //------------------------------------------------------
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}
