package pl.training.patterns.fn;

import java.util.function.Consumer;

public class FunctionalStyleState {
    interface State extends Consumer<FunctionalStyleState> {
    }

    private State current;

    public FunctionalStyleState(State initial) {
        this.current = initial;
    }

    public void setState(State newState) {
        this.current = newState;
    }

    public void onEvent() {
        current.accept(this);
    }

    public static void main(String[] args) {
        State locked = s -> {
            System.out.println("Locked → unlocking");
            //  s.setState(unlocked);
        };
        State unlocked = s -> {
            System.out.println("Unlocked → locking");
            s.setState(locked);
        };

        FunctionalStyleState turnstile = new FunctionalStyleState(locked);
        turnstile.onEvent(); // unlock
        turnstile.onEvent(); // lock
        turnstile.onEvent(); // unlock again
    }
}
