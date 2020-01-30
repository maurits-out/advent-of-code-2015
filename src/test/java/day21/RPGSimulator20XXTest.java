package day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RPGSimulator20XXTest {

    @Test
    void simulator() {
        Stats bossStats = new Stats(12, 7, 2);
        RPGSimulator20XX simulator = new RPGSimulator20XX(bossStats, 8);

        assertEquals(65, simulator.leastAmountOfGoldToWinTheFight());
        assertEquals(188, simulator.mostAmountOfGoldAndLooseTheFight());
    }
}