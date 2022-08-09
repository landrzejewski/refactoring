package pl.training.refactoring.patterns.creational.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class Director {

    private final ConnectionUrlBuilder connectionUrlBuilder;

    public void run() {
        var connectionUrl = connectionUrlBuilder.build();
        log.info("Connection url: " + connectionUrl);
    }

}
