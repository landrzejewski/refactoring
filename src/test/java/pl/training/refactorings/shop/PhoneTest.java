package pl.training.refactorings.shop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneTest {

    @Test
    void shouldThrowWhenPhoneIsNotValid() {
        assertThrows(IllegalArgumentException.class, () -> new Phone(""));
    }

}
