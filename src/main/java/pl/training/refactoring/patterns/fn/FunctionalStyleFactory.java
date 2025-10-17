package pl.training.refactoring.patterns.fn;

import java.util.function.Supplier;

public class FunctionalStyleFactory {
    interface Shape { void draw(); }

    static class Circle implements Shape { public void draw() { System.out.println("Circle"); } }
    static class Square implements Shape { public void draw() { System.out.println("Square"); } }

    public static void main(String[] args) {
        Supplier<Shape> circleFactory = Circle::new;
        Supplier<Shape> squareFactory = Square::new;

        Shape s1 = circleFactory.get();
        Shape s2 = squareFactory.get();

        s1.draw();
        s2.draw();
    }
}
