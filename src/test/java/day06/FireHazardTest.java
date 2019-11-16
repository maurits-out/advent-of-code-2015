package day06;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FireHazardTest {

    private final FireHazard hazard = new FireHazard();

    @Test
    void part1() {
        assertResult(new GridPart1(), 1000000, "turn on 0,0 through 999,999");
        assertResult(new GridPart1(), 1000, "toggle 0,0 through 999,0");
        assertResult(new GridPart1(), 999996, "turn on 0,0 through 999,999", "turn off 499,499 through 500,500");
    }

    @Test
    void part2() {
        assertResult(new GridPart2(), 1, "turn on 0,0 through 0,0");
        assertResult(new GridPart2(), 2000000, "toggle 0,0 through 999,999");
    }

    private void assertResult(Grid grid, int expectedLit, String... instructions) {
        stream(instructions).forEach(i -> hazard.updateGrid(grid, i));
        assertEquals(expectedLit, grid.getResult());
    }
}