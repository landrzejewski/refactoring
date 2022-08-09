package pl.training.refactoring.patterns.structural.decorator;

public class LowerCaseReaderDecorator extends ReaderDecorator {

    public LowerCaseReaderDecorator(Reader reader) {
        super(reader);
    }

    @Override
    public String getText() {
        return super.getText().toLowerCase();
    }

    @Override
    public int getInt() {
        return 0;
    }

}
