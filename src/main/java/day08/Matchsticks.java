package day08;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import static support.InputLoader.loadInput;

public class Matchsticks {

    private int mapAndSum(Stream<String> lines, ToIntFunction<String> function) {
        return lines.mapToInt(function).sum();
    }

    int part1(Stream<String> lines) {
        return mapAndSum(lines, line -> line.length() - countCharactersInMemory(line));
    }

    int part2(Stream<String> lines) {
        return mapAndSum(lines, line -> countCharactersEncoded(line) - line.length());
    }

    private int countCharactersInMemory(String line) {
        var count = 0;
        var idx = 1;
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

    private int countCharactersEncoded(String line) {
        return 2 + line.chars()
                .map(ch -> {
                    if (ch == '"' || ch == '\\') {
                        return 2;
                    }
                    return 1;
                })
                .sum();
    }

    public static void main(String[] args) {
        var input = loadInput("day08-input.txt");
        var matchsticks = new Matchsticks();
        System.out.printf("Part 1: %d\n", matchsticks.part1(input.lines()));
        System.out.printf("Part 2: %d\n", matchsticks.part2(input.lines()));
    }
}
