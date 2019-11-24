package day07;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AssemblyTest {

    @Test
    void signals() {
        Stream<String> lines = Stream.of(
                "123 -> x",
                "456 -> y",
                "x AND y -> d",
                "x OR y -> e",
                "x LSHIFT 2 -> f",
                "y RSHIFT 2 -> g",
                "NOT x -> h",
                "NOT y -> i"
        );
        Assembly assembly = new Assembly(lines);
        Map<String, Integer> signals = assembly.getSignalOfAllWires();

        assertEquals(72, signals.get("d"));
        assertEquals(507, signals.get("e"));
        assertEquals(492, signals.get("f"));
        assertEquals(114, signals.get("g"));
        assertEquals(65412, signals.get("h"));
        assertEquals(65079, signals.get("i"));
        assertEquals(123, signals.get("x"));
        assertEquals(456, signals.get("y"));
    }
}