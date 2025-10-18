package pl.training.refactoring.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTests {

    private static final int FIELD_INDEX = 1;

    private static Stream<Arguments> winningSequences() {
        return Stream.of(
                Arguments.of(Set.of(1, 2), 3), Arguments.of(Set.of(4, 5), 6), Arguments.of(Set.of(7, 8), 9), Arguments.of(Set.of(1, 4), 7),
                Arguments.of(Set.of(2, 5), 8), Arguments.of(Set.of(3, 6), 9), Arguments.of(Set.of(1, 5), 9), Arguments.of(Set.of(3, 5), 7)
        );
    }

    @Test
    void free_field_can_be_taken() {
        var ticTacToe = new TicTacToe();
        assertTrue(ticTacToe.takeField(FIELD_INDEX));
    }

    @Test
    void occupied_field_cannot_be_taken() {
        var ticTacToe = new TicTacToe();
        ticTacToe.takeField(FIELD_INDEX);
        assertFalse(ticTacToe.takeField(FIELD_INDEX));
    }

    @ValueSource(ints = {0, 10})
    @ParameterizedTest(name = "Invalid index: {0}")
    void field_with_invalid_index_cannot_be_taken(int index) {
        var ticTacToe = new TicTacToe();
        assertFalse(ticTacToe.takeField(index));
    }

    @Test
    void game_ends_when_all_fields_are_taken() {
        var ticTacToe = new TicTacToe(Set.of(1, 3, 5, 7), Set.of(2, 4, 6, 8));
        ticTacToe.takeField(9);
        assertTrue(ticTacToe.isGameOver());
    }

    @MethodSource("winningSequences")
    @ParameterizedTest(name = "Sequence {index}")
    void game_ends_when_one_player_took_winning_sequence(Set<Integer> crossFields, int fieldIndex) {
        var ticTacToe = new TicTacToe(crossFields, emptySet());
        ticTacToe.takeField(fieldIndex);
        assertTrue(ticTacToe.isGameOver());
    }

    @Test
    void first_player_with_winning_sequence_wins_the_game() {
        var ticTacToe = new TicTacToe(Set.of(1, 2), Set.of(4, 9));
        var player = ticTacToe.getCurrentPlayer();
        ticTacToe.takeField(3);
        assertEquals(Optional.of(player), ticTacToe.getWinner());
    }

    @Test
    void player_changes_when_field_is_taken() {
        var ticTacToe = new TicTacToe();
        var firstPlayer = ticTacToe.getCurrentPlayer();
        ticTacToe.takeField(FIELD_INDEX);
        assertNotEquals(firstPlayer, ticTacToe.getCurrentPlayer());
    }

    @Test
    void player_does_not_change_when_field_is_not_taken() {
        var ticTacToe = new TicTacToe();
        ticTacToe.takeField(FIELD_INDEX);
        var firstPlayer = ticTacToe.getCurrentPlayer();
        ticTacToe.takeField(FIELD_INDEX);
        assertEquals(firstPlayer, ticTacToe.getCurrentPlayer());
    }

    @Test
    public void throws_exception_when_initial_game_state_is_invalid() {
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(Set.of(1, 3, 5, 8), Set.of(1, 4, 6, 7, 9)));
    }

}
