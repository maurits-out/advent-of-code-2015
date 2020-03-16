package day24;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Set.copyOf;
import static java.util.stream.Stream.generate;

public class Balance {

    private final int[] packageWeights;

    public Balance(int[] packageWeights) {
        this.packageWeights = packageWeights;
    }

    private Stream<Set<Set<Integer>>> generateGroupsForPassengerCompartment(int totalWeightOfGroup) {
        return generate(new Supplier<>() {

            private int targetSize = 1;

            @Override
            public Set<Set<Integer>> get() {
                Set<Set<Integer>> result = generateGroups(new HashSet<>(), 0, targetSize, totalWeightOfGroup);
                targetSize++;
                return result;
            }
        });
    }

    private long quantumEntanglement(Set<Integer> weights) {
        return weights
                .stream()
                .mapToLong(Integer::longValue)
                .reduce(1, (m, n) -> m * n);
    }

    long minimumQuantumEntanglement(int numberOfGroups) {
        var totalWeightOfGroup = IntStream.of(packageWeights).sum() / numberOfGroups;
        return generateGroupsForPassengerCompartment(totalWeightOfGroup)
                .filter(groups -> !groups.isEmpty())
                .findFirst()
                .orElseThrow()
                .stream()
                .mapToLong(this::quantumEntanglement)
                .min()
                .orElseThrow();
    }

    private Set<Set<Integer>> generateGroups(Set<Integer> current, int start, int targetSize, int totalWeightOfGroup) {
        Set<Set<Integer>> result = new HashSet<>();
        var currentWeight = current.stream().mapToInt(Integer::intValue).sum();
        if (currentWeight == totalWeightOfGroup && targetSize == current.size()) {
            result.add(copyOf(current));
        } else if (current.size() < targetSize) {
            var i = start;
            while (i < packageWeights.length && currentWeight + packageWeights[i] <= totalWeightOfGroup) {
                current.add(packageWeights[i]);
                result.addAll(generateGroups(current, i + 1, targetSize, totalWeightOfGroup));
                current.remove(packageWeights[i]);
                i++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] packageWeights = {
                1, 3, 5, 11, 13, 17, 19,
                23, 29, 31, 37, 41, 43, 47,
                53, 59, 67, 71, 73, 79, 83,
                89, 97, 101, 103, 107, 109, 113
        };
        Balance balance = new Balance(packageWeights);
        System.out.printf("Smallest quantum entanglement for passenger compartment with 3 groups: %d\n",
                balance.minimumQuantumEntanglement(3));
        System.out.printf("Smallest quantum entanglement for passenger compartment with 4 groups: %d\n",
                balance.minimumQuantumEntanglement(4));
    }
}
