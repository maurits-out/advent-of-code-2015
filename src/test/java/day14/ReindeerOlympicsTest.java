package day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReindeerOlympicsTest {

    @Test
    void olympics() {
        String input = """
                Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                """;
        ReindeerOlympics olympics = new ReindeerOlympics(input);
        olympics.travel(1000);
        assertEquals(1120, olympics.getDistanceOfReindeerInLead());
        assertEquals(689, olympics.getPointsOfWinningReindeer());
    }
}