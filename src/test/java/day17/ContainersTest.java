package day17;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainersTest {

    private final Containers containers = new Containers(20, 15, 10, 5, 5);

    @Test
    void countCombinations() {
        assertEquals(4, containers.countCombinations(25));
    }

    @Test
    void countCombinationsWithMinimumNumberOfContainers() {
        assertEquals(3, containers.countCombinationsWithMinimumNumberOfContainers(25));
    }
}