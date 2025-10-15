package pl.training.refactoring.patterns.structural.decorator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReaderDecorator implements Reader {

    protected final Reader reader;

    @Override
    public String getText() {
        return reader.getText();
    }

    public abstract int getInt();

}
