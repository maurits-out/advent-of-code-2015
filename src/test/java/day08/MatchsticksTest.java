package day08;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MatchsticksTest {

    private final Matchsticks matchsticks = new Matchsticks();

    @Test
    void part1() {
        Stream<String> lines = Stream.of("\"\"", "\"abc\"", "\"aaa\\\"aaa\"", "\"\\x27\"");
        assertEquals(12, matchsticks.part1(lines));
    }
}