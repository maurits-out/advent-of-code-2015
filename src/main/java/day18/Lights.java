package day18;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static support.InputLoader.loadInput;

public class Lights {

    private final int dimension;
    private final String[] inputLines;

    Lights(String input) {
        inputLines = input.lines().toArray(String[]::new);
        dimension = inputLines.length;
    }

    int steps(int count, boolean cornerLightsFixed) {
        return range(0, count)
                .boxed()
                .reduce(initialState(inputLines, cornerLightsFixed),
                        (state, iteration) -> nextState(state, cornerLightsFixed),
                        (s1, s2) -> {
                            throw new UnsupportedOperationException();
                        })
                .size();
    }

    private Set<Cell> initialState(String[] lines, boolean cornerLightsFixed) {
        Set<Cell> lightsFromInput = range(0, dimension)
                .boxed()
                .flatMap(r -> range(0, dimension)
                        .filter(c -> lines[r].charAt(c) == '#')
                        .mapToObj(c -> new Cell(r, c)))
                .collect(toUnmodifiableSet());
        Set<Cell> result = new HashSet<>(lightsFromInput);
        if (cornerLightsFixed) {
            result.addAll(createCornerLights());
        }
        return result;
    }

    private Set<Cell> nextState(Set<Cell> lights, boolean cornerLightsFixed) {
        Set<Cell> result = new HashSet<>(newLightsToTurnOn(lights));
        result.addAll(lightsToKeepOn(lights));
        if (cornerLightsFixed) {
            result.addAll(createCornerLights());
        }
        return result;
    }

    private Set<Cell> createCornerLights() {
        return Set.of(
                new Cell(0, 0),
                new Cell(0, dimension - 1),
                new Cell(dimension - 1, 0),
                new Cell(dimension - 1, dimension - 1));
    }

    private Set<Cell> neighbors(Cell cell) {
        return rangeClosed(cell.getRow() - 1, cell.getRow() + 1)
                .filter(r -> r >= 0 && r < dimension)
                .boxed()
                .flatMap(r -> rangeClosed(cell.getColumn() - 1, cell.getColumn() + 1)
                        .filter(c -> c >= 0 && c < dimension)
                        .mapToObj(c -> new Cell(r, c)))
                .filter(c -> !c.equals(cell))
                .collect(toUnmodifiableSet());
    }

    private Set<Cell> lightsToKeepOn(Set<Cell> lights) {
        return lights.stream()
                .filter(cell -> countNeighbors(cell, lights, count -> count == 2 || count == 3))
                .collect(toUnmodifiableSet());
    }

    private Set<Cell> newLightsToTurnOn(Set<Cell> lights) {
        return range(0, dimension)
                .boxed()
                .flatMap(r -> range(0, dimension)
                        .mapToObj(c -> new Cell(r, c))
                        .filter(cell -> countNeighbors(cell, lights, count -> count == 3)))
                .collect(toUnmodifiableSet());
    }

    private boolean countNeighbors(Cell cell, Set<Cell> lights, Predicate<Long> predicate) {
        var count = neighbors(cell).stream()
                .filter(lights::contains)
                .count();
        return predicate.test(count);
    }

    public static void main(String[] args) {
        String input = loadInput("day18-input.txt");
        Lights lights = new Lights(input);
        System.out.printf("Number of lights on (part 1): %d\n", lights.steps(100, false));
        System.out.printf("Number of lights on (part 2): %d\n", lights.steps(100, true));
    }
}

class Cell {

    private final int row;
    private final int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var cell = (Cell) o;
        return row == cell.row && column == cell.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
