package pl.training.refactoring.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemplateTest {

    private static final String TEXT_WITHOUT_EXPRESSIONS = "My name is Jan Kowalski";
    private static final String TEXT_WITH_EXPRESSIONS = "My name is ${firstName} ${lastName}";

    @Test
    void given_a_text_without_expression_when_evaluating_then_returns_the_text() {
        var template = new Template(TEXT_WITHOUT_EXPRESSIONS);
        var result = template.evaluate(emptyMap());
        assertEquals(TEXT_WITHOUT_EXPRESSIONS, result);
    }


    @DisplayName("given_a_text_with_expressions")
    @Nested
    class GivenTextWithExpressions {

        @Test
        void when_evaluating_then_returns_the_text_with_substituted_value() {
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            var data = Map.of("firstName", "Jan", "lastName", "Kowalski");
            var result = template.evaluate(data);
            assertEquals(TEXT_WITHOUT_EXPRESSIONS, result);
        }

        @Test
        void  when_evaluating_without_providing_all_values_then_throws_an_exception() {
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            assertThrows(IllegalArgumentException.class, () -> template.evaluate(emptyMap()));
        }

        @Test
        void  when_evaluating_with_providing_non_alphanumeric_values_then_throws_an_exception() {
            var template = new Template(TEXT_WITH_EXPRESSIONS);
            var data = Map.of("firstName", "Jan", "lastName", "@@@");
            assertThrows(IllegalArgumentException.class, () -> template.evaluate(data));
        }

    }

}
