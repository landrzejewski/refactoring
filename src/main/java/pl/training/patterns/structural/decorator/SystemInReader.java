package pl.training.patterns.structural.decorator;

import java.util.Scanner;

public class SystemInReader implements Reader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getText() {
        return scanner.nextLine();
    }

}
