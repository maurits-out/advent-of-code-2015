package day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotQuiteLispTest {

    private final NotQuiteLisp notQuiteLisp = new NotQuiteLisp();

    @ParameterizedTest
    @CsvSource({
            "(()), 0", "()(), 0",
            "(((, 3", "(()(()(, 3",
            "))(((((, 3",
            "()), -1", "))(, -1",
            "))), -3", ")())()), -3"
    })
    void floor(String instructions, int expectedFloor) {
        assertEquals(expectedFloor, notQuiteLisp.floor(instructions));
    }

    @ParameterizedTest
    @CsvSource({
            "), 1",
            "()()), 5"
    })
    void basementPosition(String instructions, int expectedPosition) {
        assertEquals(expectedPosition, notQuiteLisp.basementPosition(instructions));
    }
}