package pl.training.refactoring.patterns.creational.prototype;

import lombok.Value;

@Value
public class Window implements Cloneable {

    int x;
    int y;
    int width;
    int height;

    @Override
    public Window clone() throws CloneNotSupportedException {
        return (Window) super.clone();
    }

}
