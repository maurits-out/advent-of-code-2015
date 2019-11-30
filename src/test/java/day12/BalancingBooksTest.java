package day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalancingBooksTest {

    @Test
    void getSumOfAllNumbers() {
        assertSum("[1,2,3]", 6);
        assertSum("[[[3]]]", 3);
        assertSum("[-1,{\"a\":1}]", 0);
    }

    @Test
    void getSumOfAllNumbersIgnoringRed() {
        assertSumIgnoringRed("[1,2,3]", 6);
        assertSumIgnoringRed("[1,{\"c\":\"red\",\"b\":2},3]", 4);
        assertSumIgnoringRed("[1,\"red\",5]", 6);
    }

    private void assertSumIgnoringRed(String input, int expected) {
        BalancingBooks balancingBooks = new BalancingBooks(input);
        assertEquals(expected, balancingBooks.getSumOfAllNumbersIgnoringRed());
    }

    private void assertSum(String input, int expected) {
        BalancingBooks balancingBooks = new BalancingBooks(input);
        assertEquals(expected, balancingBooks.getSumOfAllNumbers());
    }
}