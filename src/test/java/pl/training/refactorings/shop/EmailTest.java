package pl.training.refactorings.shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @Test
    void shouldThrowWhenAddressIsNotValid() {
        assertThrows(IllegalArgumentException.class, () -> new Email("notValidEmail"));
    }

}
