package day20;

import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.iterate;

public class InfiniteElves {

    private static final int INPUT = 36000000;
    private final int presents;

    public InfiniteElves(int presents) {
        this.presents = presents;
    }

    int lowestHouseNumber() {
        return iterate(1, houseNumber -> houseNumber + 1)
                .filter(houseNumber -> numberOfPresents(houseNumber) >= presents)
                .findFirst()
                .orElseThrow();
    }

    private int numberOfPresents(int houseNumber) {
        return 10 * sumOfFactors(houseNumber);
    }

    private int sumOfFactors(int num) {
        var result = 1;
        for (int i = 2; i <= sqrt(num); i++) {
            var sum = 1;
            var term = 1;
            while (num % i == 0) {
                num = num / i;
                term *= i;
                sum += term;
            }
            result *= sum;
        }
        if (num >= 2) {
            result *= (1 + num);
        }
        return result;
    }

    public static void main(String[] args) {
        var elves = new InfiniteElves(INPUT);
        System.out.printf("Lowest house number of the house to get at least %d presents: %d\n", INPUT, elves.lowestHouseNumber());
    }
}
