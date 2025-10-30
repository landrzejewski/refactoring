package pl.training.patterns.behavioral.observer;

import lombok.extern.java.Log;

import java.util.function.Consumer;

@Log
public class Logger implements Consumer<ServerEvent> {

    @Override
    public void accept(ServerEvent serverEvent) {
        log.info(serverEvent.getPayload());
    }

}
