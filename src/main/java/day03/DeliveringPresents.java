package day03;

import support.InputLoader;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Stream.concat;
import static support.InputLoader.loadInput;

public class DeliveringPresents {

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
            case '>' -> new House(house.getRow(), house.getColumn() + 1);
            case '<' -> new House(house.getRow(), house.getColumn() - 1);
            case '^' -> new House(house.getRow() - 1, house.getColumn());
            case 'v' -> new House(house.getRow() + 1, house.getColumn());
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

class House {
    private final int row;
    private final int column;

    House(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return row == house.row &&
                column == house.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}