package day18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LightsTest {

    private static final String INPUT = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
            """;
    private final Lights lights = new Lights(INPUT);

    @Test
    void countNumberOfLights() {
        assertEquals(4, lights.steps(4, false));
    }

    @Test
    void countNumberOfLightsWithCornerLightsStuckOn() {
        assertEquals(17, lights.steps(5, true));
    }
}