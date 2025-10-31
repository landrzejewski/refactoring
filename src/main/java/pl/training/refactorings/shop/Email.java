package pl.training.refactorings.shop;

public record Email(String address) {

    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public Email {
        if (!address.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

}
