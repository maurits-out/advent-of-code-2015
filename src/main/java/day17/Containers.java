package day17;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.concat;

public class Containers {

    private final int[] containerSizes;

    public Containers(int... containerSizes) {
        this.containerSizes = containerSizes;
    }

    public int countCombinations(int liters) {
        return countCombinations(liters, 0, 0).values().stream().mapToInt(v -> v).sum();
    }

    public int countCombinationsWithMinimumNumberOfContainers(int liters) {
        var combinations = countCombinations(liters, 0, 0);
        var smallestNumberOfContainers = combinations.keySet().stream().mapToInt(v -> v).min().orElseThrow();
        return combinations.get(smallestNumberOfContainers);
    }

    private Map<Integer, Integer> countCombinations(int liters, int current, int containersInUse) {
        if (liters == 0) {
            return Map.of(containersInUse, 1);
        }
        if (current == containerSizes.length) {
            return emptyMap();
        }
        Map<Integer, Integer> withoutCurrentContainer = countCombinations(liters, current + 1, containersInUse);
        if (containerSizes[current] <= liters) {
            var withCurrentContainer = countCombinations(liters - containerSizes[current], current + 1, containersInUse + 1);
            return concat(withoutCurrentContainer.entrySet().stream(), withCurrentContainer.entrySet().stream())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
        }
        return withoutCurrentContainer;
    }

    public static void main(String[] args) {
        var containers = new Containers(11, 30, 47, 31, 32, 36, 3, 1, 5, 3, 32, 36, 15, 11, 46, 26, 28, 1, 19, 3);
        System.out.printf("Number of different combinations to fill 150 liters: %d\n",
                containers.countCombinations(150));
        System.out.printf("Number of different combinations to fill 150 liters with minimum number of containers: %d\n",
                containers.countCombinationsWithMinimumNumberOfContainers(150));
    }
}
