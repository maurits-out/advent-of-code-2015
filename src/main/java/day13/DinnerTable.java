package day13;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.System.arraycopy;
import static java.util.stream.IntStream.range;
import static support.InputLoader.loadInput;

public class DinnerTable {

    private final HappinessUnits table;

    public DinnerTable(HappinessUnits table) {
        this.table = table;
    }

    private void swap(String[] array, int i, int j) {
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private String[] copy(String[] array) {
        String[] result = new String[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * Heaps algorithm to generate permutations
     * (https://en.wikipedia.org/wiki/Heap%27s_algorithm)
     */
    private Stream<String[]> possibleArrangements(boolean includeSelf) {
        Set<String[]> arrangements = new HashSet<>();
        var arrangement = table.getAttendees(includeSelf).toArray(new String[0]);
        var indices = new int[arrangement.length];
        var current = 0;
        arrangements.add(arrangement);
        while (current < arrangement.length) {
            if (indices[current] < current) {
                swap(arrangement, current % 2 == 0 ? 0 : indices[current], current);
                arrangements.add(copy(arrangement));
                indices[current]++;
                current = 0;
            } else {
                indices[current] = 0;
                current++;
            }
        }
        return arrangements.stream();
    }

    private int totalHappiness(String[] arrangement) {
        var total = table.happiness(arrangement[0], arrangement[arrangement.length - 1]) +
                    table.happiness(arrangement[0], arrangement[1]);
        total += range(1, arrangement.length - 1)
                .map(idx -> table.happiness(arrangement[idx], arrangement[idx - 1]) +
                            table.happiness(arrangement[idx], arrangement[idx + 1]))
                .sum();
        total += table.happiness(arrangement[arrangement.length - 1], arrangement[arrangement.length - 2]) +
                 table.happiness(arrangement[arrangement.length - 1], arrangement[0]);
        return total;
    }

    int totalChangeInHappinessForOptimalSeatingArrangement(boolean selfIncluded) {
        return possibleArrangements(selfIncluded)
                .mapToInt(this::totalHappiness)
                .max()
                .orElseThrow();
    }

    public static void main(String[] args) {
        var input = loadInput("day13-input.txt");
        var happinessUnits = new HappinessUnits(input);
        var dinnerTable = new DinnerTable(happinessUnits);
        System.out.printf("Total change in happiness for the optimal seating arrangement (part 1): %d\n",
                dinnerTable.totalChangeInHappinessForOptimalSeatingArrangement(false));
        System.out.printf("Total change in happiness for the optimal seating arrangement (part 2): %d\n",
                dinnerTable.totalChangeInHappinessForOptimalSeatingArrangement(true));
    }
}