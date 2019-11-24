package day08;

import java.util.stream.Stream;

import static support.InputLoader.loadInput;

public class Matchsticks {

    int part1(Stream<String> lines) {
        return lines
                .mapToInt(line -> line.length() - countCharactersInMemory(line))
                .sum();
    }

    private int countCharactersInMemory(String line) {
        int count = 0;
        int idx = 1;
        while (idx < line.length() - 1) {
            if (line.charAt(idx) == '\\') {
                if (line.charAt(idx + 1) == 'x') {
                    idx += 2;
                }
                idx++;
            }
            count++;
            idx++;
        }
        return count;
    }

    public static void main(String[] args) {
        String input = loadInput("day08-input.txt");
        Matchsticks matchsticks = new Matchsticks();
        System.out.printf("Part 1: %d\n", matchsticks.part1(input.lines()));
    }
}
