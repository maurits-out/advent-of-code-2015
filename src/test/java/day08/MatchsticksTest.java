package day08;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class MatchsticksTest {

    private final Matchsticks matchsticks = new Matchsticks();

    private static final List<String> LINES = asList("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"");

    @Test
    void part1() {
        assertEquals(12, matchsticks.part1(LINES.stream()));
    }

    @Test
    void part2() {
        assertEquals(19, matchsticks.part2(LINES.stream()));
    }
}