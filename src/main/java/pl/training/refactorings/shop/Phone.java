package pl.training.refactorings.shop;

public record Phone(String number) {

    public Phone {
        if (!number.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
    }

}
