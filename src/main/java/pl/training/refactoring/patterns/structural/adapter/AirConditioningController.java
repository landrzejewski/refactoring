package pl.training.refactoring.patterns.structural.adapter;

import lombok.extern.java.Log;

@Log
public class AirConditioningController {

    public void changeTemperature(double deltaInFahrenheit) {
        log.info("Changing temperature: " + deltaInFahrenheit);
    }

}
