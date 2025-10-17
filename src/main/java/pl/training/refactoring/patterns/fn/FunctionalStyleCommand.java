package pl.training.refactoring.patterns.fn;

public class FunctionalStyleCommand {
    public static void main(String[] args) {
        Runnable open = () -> System.out.println("Opening file...");
        Runnable save = () -> System.out.println("Saving file...");
        Runnable close = () -> System.out.println("Closing file...");

        execute(open);
        execute(save);
        execute(close);
    }

    static void execute(Runnable command) {
        command.run();
    }
}
