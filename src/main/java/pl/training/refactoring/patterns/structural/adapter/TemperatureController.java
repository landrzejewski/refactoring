package pl.training.refactoring.patterns.structural.adapter;

public interface TemperatureController {

    void temperatureUp(double deltaInCelsius);

    void temperatureDown(double deltaInCelsius);

}
