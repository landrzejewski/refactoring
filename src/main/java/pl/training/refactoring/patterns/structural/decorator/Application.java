package pl.training.refactoring.patterns.structural.decorator;

import lombok.extern.java.Log;

@Log
public class Application {

    public static void main(String[] args) {
        Reader reader = new LowerCaseReaderDecorator(new UnderscoreReaderDecorator(new SystemInReader()));
        //------------------------------------------------
        log.info(reader.getText());
        ((ReaderDecorator) reader).getInt();
    }

}
