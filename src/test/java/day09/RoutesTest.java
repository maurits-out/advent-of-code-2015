package day09;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutesTest {

    private Routes routes;

    @BeforeEach
    void setUp() {
        String input = """
                London to Dublin = 464
                London to Belfast = 518
                Dublin to Belfast = 141
                """;
        DistanceTable table = new DistanceTable(input.lines());
        routes = new Routes(table);
    }

    @Test
    void shortestRoute() {
        assertEquals(605, routes.shortestRoute());
    }

    @Test
    void longestRoute() {
        assertEquals(982, routes.longestRoute());
    }
}