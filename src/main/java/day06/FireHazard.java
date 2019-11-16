package day06;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static support.InputLoader.loadInput;

public class FireHazard {

    private static final Pattern PATTERN = Pattern.compile("^([a-z ]+) (\\d+),(\\d+) through (\\d+),(\\d+)$");

    void updateGrid(Grid grid, String instruction) {
        var matcher = PATTERN.matcher(instruction);
        if (matcher.find()) {
            var command = matcher.group(1);
            var xLeft = parseInt(matcher.group(2));
            var yTop = parseInt(matcher.group(3));
            var xRight = parseInt(matcher.group(4));
            var yBottom = parseInt(matcher.group(5));
            grid.handle(command, xLeft, yTop, xRight, yBottom);
        } else {
            throw new IllegalStateException("Could not parse: " + instruction);
        }
    }

    private long processInstructions(Grid grid, Stream<String> instructions) {
        instructions.forEach(s -> updateGrid(grid, s));
        return grid.getResult();
    }

    public static void main(String[] args) {
        var input = loadInput("day06-input.txt");
        var fireHazard = new FireHazard();
        System.out.printf("Number of lights lit: %d\n", fireHazard.processInstructions(new GridPart1(), input.lines()));
        System.out.printf("Total brightness    : %d\n", fireHazard.processInstructions(new GridPart2(), input.lines()));
    }
}
