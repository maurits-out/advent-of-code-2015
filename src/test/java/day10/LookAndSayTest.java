package day10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LookAndSayTest {

    private final LookAndSay lookAndSay = new LookAndSay();

    @ParameterizedTest
    @CsvSource({"1, 11", "11, 21", "21, 1211", "1211, 111221", "111221, 312211"})
    void nextSequence(String input, String expected) {
        assertEquals(expected, lookAndSay.nextSequence(input));
    }

    @Test
    void repeat() {
        assertEquals(6, lookAndSay.lengthOfResult("1",5));
    }
}