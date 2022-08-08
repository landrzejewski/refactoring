package pl.training.refactoring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemplateTest {

    private static final String TEXT_WITHOUT_EXPRESSIONS = "My name is Jan Kowalski";
    private static final String TEXT_WITH_EXPRESSIONS = "My name is ${firstName} ${lastName}";

    @Test
    void given_a_text_without_expressions_when_evaluating_then_returns_the_text() {
        var template = new Template(TEXT_WITHOUT_EXPRESSIONS);
        assertEquals(TEXT_WITHOUT_EXPRESSIONS, template.evaluate(emptyMap()));
    }

    @Test
    void given_a_text_with_non_unique_expressions_when_creating_then_throws_an_exception() {
        assertThrows(IllegalArgumentException.class, () -> new Template("My name is ${firstName} ${firstName}"));
    }

    @DisplayName("given_a_text_with_expressions")
    @Nested
    class GivenTextWirthExpressions {

        @Test
        void when_evaluating_then_returns_the_text_with_substituted_values() {
            var parameters = Map.of("firstName", "Jan", "lastName", "Kowalski");
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            assertEquals(TEXT_WITHOUT_EXPRESSIONS, template.evaluate(parameters));
        }

        @Test
        void when_evaluating_without_providing_all_values_then_throws_an_exception() {
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            assertThrows(IllegalArgumentException.class, () -> template.evaluate(emptyMap()));
        }

        @Test
        void when_evaluating_with_providing_non_alphanumeric_values_then_throws_an_exception() {
            var parameters = Map.of("firstName", "Jan", "lastName", "@@");
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            assertThrows(IllegalArgumentException.class, () -> template.evaluate(parameters));
        }

    }

}
