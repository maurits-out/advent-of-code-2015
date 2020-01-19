package day20;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfiniteElvesTest {

    @ParameterizedTest
    @CsvSource({"10, 1", "30, 2", "40, 3", "60, 4", "70, 4"})
    void lowestHouseNumber(int input, int houseNumber) {
        InfiniteElves elves = new InfiniteElves(input);
        assertEquals(houseNumber, elves.lowestHouseNumberPart1());
    }
}