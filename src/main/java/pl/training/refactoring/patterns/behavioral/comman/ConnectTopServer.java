package pl.training.refactoring.patterns.behavioral.comman;

import lombok.extern.java.Log;

@Log
public class ConnectTopServer implements Command{

    @Override
    public void execute() {
        log.info("Connecting...");
        log.info("Connected...");
    }

}
