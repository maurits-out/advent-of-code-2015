package day03;

import java.util.LinkedList;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static support.InputLoader.loadInput;

public class DeliveringPresents {

    static record House(int row, int column) {
    }

    private Stream<House> houses(String directions, int start, int step) {
        var result = new LinkedList<House>();
        result.add(new House(0, 0));
        for (var idx = start; idx < directions.length(); idx += step) {
            var lastHouse = result.getLast();
            result.add(nextHouse(lastHouse, directions.charAt(idx)));
        }
        return result.stream();
    }

    private House nextHouse(House house, int direction) {
        return switch (direction) {
            case '>' -> new House(house.row(), house.column() + 1);
            case '<' -> new House(house.row(), house.column() - 1);
            case '^' -> new House(house.row() - 1, house.column());
            case 'v' -> new House(house.row() + 1, house.column());
            default -> throw new IllegalArgumentException("Unknown direction: " + direction);
        };
    }

    long countHousesVisitedByElf(String directions) {
        return houses(directions, 0, 1).distinct().count();
    }

    long countHousesVisitedBySantaAndRoboSanta(String directions) {
        return concat(houses(directions, 0, 2), houses(directions, 1, 2)).distinct().count();
    }

    public static void main(String[] args) {
        var deliveringPresents = new DeliveringPresents();
        var directions = loadInput("day03-input.txt");
        System.out.printf("Number of houses that receive at least one present by elf: %d\n",
                deliveringPresents.countHousesVisitedByElf(directions));
        System.out.printf("Number of houses that receive at least one present by Santa and RoboSanta: %d\n",
                deliveringPresents.countHousesVisitedBySantaAndRoboSanta(directions));
    }
}
