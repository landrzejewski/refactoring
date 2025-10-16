package pl.training.refactoring.patterns.behavioral.comman;

import java.util.LinkedList;
import java.util.Queue;

public class Invoker {

    private final Queue<Command> commands = new LinkedList<>();

    public void register(Command command) {
        commands.add(command);
    }

    public void invokeAll() {
        commands.forEach(Command::execute);
    }

    public void invoke(Command command) {
        command.execute();
    }

}
