package pl.training;

import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.training.Tags.UNIT;

@Tag(UNIT)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Log
class CalculatorTests {

    private final Calculator sut = new Calculator();

    @BeforeEach
    void beforeEach() {
        log.info("Before each metod");
    }

    @AfterEach
    void afterEach() {
        log.info("After each metod");
    }

    @BeforeAll
    static void beforeAll() {
        log.info("Before all metod");
    }

    @AfterAll
    static void afterAll() {
        log.info("After all metod");
    }

    @DisplayName("given two numbers")
    @Nested
    class GivenTwoNumbers {

        //@DisplayName("given two numbers when add then returns their sum")
        @Test
        void when_add_then_returns_their_sum() {
            // Given / Arrange
            var leftHandSide = 1;
            var rightHandSide = 2;
            // When / Act
            var actual = sut.add(leftHandSide, rightHandSide);
            // Then // Assert
            var expected = 3;
            assertEquals(expected, actual);
        }

       /* private static Stream<Arguments> numbers() {
            return Stream.of(Arguments.of(1, 2), Arguments.of(2, 3));
        }*/

        //@MethodSource("numbers")
        @CsvFileSource(resources = "/data.csv")
        @ParameterizedTest(name = "attempt: {index} with ({0}, {1})")
        void when_substract_then_returns_their_difference(int leftHandSide, int rightHandSide) {
            assertEquals(leftHandSide - rightHandSide, sut.substract(leftHandSide, rightHandSide));
        }

    }

    //@Tag(INTEGRATION)
    @Test
    void given_divisor_equals_zero_when_divide_then_throws_exception() {
        assertThrows(IllegalArgumentException.class, () -> sut.divide(10, 0));
    }

    @Test
    void matchers_tests() {
        assertThat(List.of(1, 2, 3, 4, 5), hasItems(2, 5));
        assertThat("Java", both(containsString("Ja")).and(containsString("v")));
        assertThat("123", new Digits());
    }

    @Test
    void async_test() throws InterruptedException {
        var latch = new CountDownLatch(1);
        var sut = new DataProvider();
        sut.get(result -> {
            assertEquals("Success", result);
            latch.countDown();
        });
        log.info("Before lock");
        latch.await();
        log.info("After lock");
    }

}
