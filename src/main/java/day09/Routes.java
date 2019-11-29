package day09;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Routes {

    private final DistanceTable table;

    public Routes(DistanceTable table) {
        this.table = table;
    }

    private void swap(String[] array, int i, int j) {
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private List<String> toList(String[] array) {
        List<String> result = new ArrayList<>(array.length);
        Collections.addAll(result, array);
        return result;
    }

    /**
     * Heaps algorithm to generate permutations
     * (https://en.wikipedia.org/wiki/Heap%27s_algorithm)
     */
    private Stream<List<String>> possibleRoutes() {
        Set<List<String>> routes = new HashSet<>();

        var locations = List.copyOf(table.getLocations());
        var route = locations.toArray(new String[0]);

        var indices = new int[locations.size()];

        routes.add(locations);

        var current = 0;
        while (current < locations.size()) {
            if (indices[current] < current) {
                swap(route, current % 2 == 0 ? 0 : indices[current], current);
                routes.add(toList(route));
                indices[current]++;
                current = 0;
            } else {
                indices[current] = 0;
                current++;
            }
        }

        return routes.stream();
    }

    private int distance(List<String> route) {
        var result = 0;
        var iterator = route.iterator();
        var current = iterator.next();
        String prev;
        while (iterator.hasNext()) {
            prev = current;
            current = iterator.next();
            result += table.distanceBetween(prev, current);
        }
        return result;
    }

    private IntStream distancesOfAllPossibleRoutes() {
        return possibleRoutes().mapToInt(this::distance);
    }

    int shortestRoute() {
        return distancesOfAllPossibleRoutes().min().orElseThrow();
    }

    int longestRoute() {
        return distancesOfAllPossibleRoutes().max().orElseThrow();
    }

    public static void main(String[] args) {
        var input = """
                Tristram to AlphaCentauri = 34
                Tristram to Snowdin = 100
                Tristram to Tambi = 63
                Tristram to Faerun = 108
                Tristram to Norrath = 111
                Tristram to Straylight = 89
                Tristram to Arbre = 132
                AlphaCentauri to Snowdin = 4
                AlphaCentauri to Tambi = 79
                AlphaCentauri to Faerun = 44
                AlphaCentauri to Norrath = 147
                AlphaCentauri to Straylight = 133
                AlphaCentauri to Arbre = 74
                Snowdin to Tambi = 105
                Snowdin to Faerun = 95
                Snowdin to Norrath = 48
                Snowdin to Straylight = 88
                Snowdin to Arbre = 7
                Tambi to Faerun = 68
                Tambi to Norrath = 134
                Tambi to Straylight = 107
                Tambi to Arbre = 40
                Faerun to Norrath = 11
                Faerun to Straylight = 66
                Faerun to Arbre = 144
                Norrath to Straylight = 115
                Norrath to Arbre = 135
                Straylight to Arbre = 127
                """;
        var table = new DistanceTable(input.lines());
        var routes = new Routes(table);
        System.out.printf("Shortest route (part 1): %d\n", routes.shortestRoute());
        System.out.printf("Longest route  (part 2): %d\n", routes.longestRoute());
    }
}

