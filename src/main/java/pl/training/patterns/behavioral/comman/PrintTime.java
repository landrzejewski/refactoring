package pl.training.patterns.behavioral.comman;

import lombok.extern.java.Log;

import java.time.LocalDateTime;

@Log
public class PrintTime implements Command {

    @Override
    public void execute() {
        log.info(LocalDateTime.now().toString());
    }

}
