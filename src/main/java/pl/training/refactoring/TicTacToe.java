package pl.training.refactoring;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.disjoint;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static pl.training.refactoring.Player.CROSS;

public class TicTacToe {

    private static final int MINIMAL_FIELD_INDEX = 1;
    private static final int MAXIMUM_FIELD_INDEX = 9;
    private static final Set<Set<Integer>> winningSequences = Set.of(
            Set.of(1, 2, 3), Set.of(4, 5, 6), Set.of(7, 8, 9),
            Set.of(1, 4, 7), Set.of(2, 5, 8), Set.of(3, 6, 9),
            Set.of(1, 5, 9), Set.of(3, 5, 7)
    );

    private final Set<Integer> crossFields;
    private final Set<Integer> circleFields;

    private Player currentPlayer = CROSS;

    public TicTacToe() {
        crossFields = new HashSet<>();
        circleFields = new HashSet<>();
    }

    public TicTacToe(Set<Integer> crossFields, Set<Integer> circleFields) {
        this.crossFields = new HashSet<>(crossFields);
        this.circleFields = new HashSet<>(circleFields);
        validateInitialState();
    }

    private void validateInitialState() {
        if (!disjoint(crossFields, circleFields) || takenFields().size() > MAXIMUM_FIELD_INDEX) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isGameOver() {
        return allFieldsAreTaken() || playerTookWinningSequence();
    }

    private boolean allFieldsAreTaken() {
        return MAXIMUM_FIELD_INDEX - takenFields().size() == 0;
    }

    private Set<Integer> takenFields() {
        return concat(crossFields.stream(), circleFields.stream()).collect(toSet());
    }

    private boolean playerTookWinningSequence() {
        return winningSequences.stream().anyMatch(sequence -> playerFields(currentPlayer.reverse()).containsAll(sequence));
    }

    private Set<Integer> playerFields(Player player) {
        return player == CROSS ? crossFields : circleFields;
    }

    public boolean takeField(int index) {
        if (isFieldTaken(index) || !isFieldOnBoard(index)) {
            return false;
        }
        playerFields(currentPlayer).add(index);
        currentPlayer = currentPlayer.reverse();
        return true;
    }

    private boolean isFieldTaken(int index) {
        return takenFields().contains(index);
    }

    private boolean isFieldOnBoard(int index) {
        return index >= MINIMAL_FIELD_INDEX && index <= MAXIMUM_FIELD_INDEX;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Optional<Player> getWinner() {
        if (!isGameOver()) {
            return Optional.empty();
        }
        return Optional.of(currentPlayer.reverse());
    }

}
