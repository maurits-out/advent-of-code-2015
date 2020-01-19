package day20;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.*;

public class InfiniteElves {

    private final int presents;

    public InfiniteElves(int presents) {
        this.presents = presents;
    }

    int lowestHouseNumberPart1() {
        return lowestHouseNumber(houseNumber -> 10 * sumOfFactors(houseNumber));
    }

    int lowestHouseNumberPart2() {
        return lowestHouseNumber(houseNumber -> 11 * sumOfFactors(houseNumber, elf -> elf * 50 >= houseNumber));
    }

    private int lowestHouseNumber(IntFunction<Integer> countFunction) {
        return iterate(1, houseNumber -> houseNumber + 1)
                .filter(houseNumber -> countFunction.apply(houseNumber) >= presents)
                .findFirst()
                .orElseThrow();
    }

    private int sumOfFactors(int houseNumber) {
        var result = 1;
        for (int i = 2; i <= sqrt(houseNumber); i++) {
            var sum = 1;
            var term = 1;
            while (houseNumber % i == 0) {
                houseNumber = houseNumber / i;
                term *= i;
                sum += term;
            }
            result *= sum;
        }
        if (houseNumber >= 2) {
            result *= (1 + houseNumber);
        }
        return result;
    }

    private int sumOfFactors(int houseNumber, IntPredicate elfFilter) {
        return rangeClosed(1, (int) sqrt(houseNumber))
                .filter(elf -> houseNumber % elf == 0)
                .flatMap(elf -> of(elf, houseNumber / elf).distinct())
                .filter(elfFilter)
                .sum();
    }

    public static void main(String[] args) {
        var elves = new InfiniteElves(36000000);
        System.out.printf("Lowest house number (part 1): %d\n", elves.lowestHouseNumberPart1());
        System.out.printf("Lowest house number (part 2): %d\n", elves.lowestHouseNumberPart2());
    }
}
