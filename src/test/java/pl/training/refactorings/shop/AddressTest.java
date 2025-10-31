package pl.training.refactorings.shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressTest {

    @Test
    void shouldThrowWhenAddressIsNotComplete() {
        assertThrows(IllegalArgumentException.class, () -> new Address("Main street 19", "Dublin", "", ""));
    }

}
