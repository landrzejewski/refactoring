package pl.training.patterns.behavioral.templatemethod;

public abstract class Processor<V, PV> {

    public final void run() {
        var data = read();
        var result = process(data);
        write(result);
    }

    protected abstract V read();

    protected abstract PV process(V data);

    protected abstract void write(PV data);

}
