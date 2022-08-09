package pl.training.refactoring.patterns.creational.prototype;

import lombok.extern.java.Log;

@Log
public class Application {

    public static void main(String[] args) throws CloneNotSupportedException {
        var fullScreenWindow = new Window(0, 0, 800, 600);
        var dialog = new Window(200, 200, 100, 50);
        //----------------------------------------------------------
        var window = fullScreenWindow.clone();
        log.info("Window: " + window);
    }

}
