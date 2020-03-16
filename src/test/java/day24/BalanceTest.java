package day24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceTest {

    @Test
    void minimumQuantumEntanglement() {
        Balance balance = new Balance(new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10, 11});
        assertEquals(99, balance.minimumQuantumEntanglement(3));
        assertEquals(44, balance.minimumQuantumEntanglement(4));
    }
}