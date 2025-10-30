package pl.training;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.function.Consumer;

@Log
public class DataProvider {

    public void get(Consumer<String> callback) {
        new Thread(() -> {
            fakeDelay();
            log.info("After sleep");
            callback.accept("Success");
        }).start();
    }

    @SneakyThrows
    private void fakeDelay() {
        Thread.sleep(2_000);
    }

}
