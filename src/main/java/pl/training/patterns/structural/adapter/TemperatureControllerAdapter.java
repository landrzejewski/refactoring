package pl.training.patterns.structural.adapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TemperatureControllerAdapter implements TemperatureController {

    private final AirConditioningController controller;

    @Override
    public void temperatureUp(double value) {
        controller.changeTemperature(celsiusToFahrenheit(value));
    }

    @Override
    public void temperatureDown(double value) {
        controller.changeTemperature(-celsiusToFahrenheit(value));
    }

    // move to mapper class
    private Double celsiusToFahrenheit(Double value) {
        return value * 1.8 + 32;
    }

}
