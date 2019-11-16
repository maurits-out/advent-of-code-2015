package day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveringPresentsTest {

    private final DeliveringPresents deliveringPresents = new DeliveringPresents();

    @ParameterizedTest
    @CsvSource({">, 2", "^>v<, 4", "^v^v^v^v^v, 2"})
    void countHousesVisitedByElf(String directions, int expectedCount) {
        assertEquals(expectedCount, deliveringPresents.countHousesVisitedByElf(directions));
    }

    @ParameterizedTest
    @CsvSource({"^v, 3", "^>v<, 3", "^v^v^v^v^v, 11"})
    void countHousesVisitedBySantaAndRoboSanta(String directions, int expectedCount) {
        assertEquals(expectedCount, deliveringPresents.countHousesVisitedBySantaAndRoboSanta(directions));
    }
}