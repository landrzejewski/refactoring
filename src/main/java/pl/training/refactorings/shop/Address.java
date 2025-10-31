package pl.training.refactorings.shop;

public record Address(String street, String city, String postalCode, String state) {

    public Address {
        if (street.isEmpty() || city.isEmpty() || postalCode.isEmpty() || state.isEmpty()) {
            throw new IllegalArgumentException("All fields must are required");
        }
    }

}
