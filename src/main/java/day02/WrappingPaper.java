package day02;

import java.util.StringTokenizer;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static support.InputLoader.loadInput;

public class WrappingPaper {

    private int sum(String input, ToIntFunction<Present> function) {
        return input
                .lines()
                .map(Present::parse)
                .mapToInt(function)
                .sum();
    }

    int totalWrappingPaper(String input) {
        return sum(input, p -> p.surface() + p.areaOfSmallestSide());
    }

    int totalRibbon(String input) {
        return sum(input, p -> p.smallestPerimeter() + p.volume());
    }

    public static void main(String[] args) {
        var paper = new WrappingPaper();
        var input = loadInput("day02-input.txt");
        System.out.printf("Total square feet of wrapping paper: %d\n", paper.totalWrappingPaper(input));
        System.out.printf("Total feet of ribbon: %d\n", paper.totalRibbon(input));
    }
}

class Present {

    private final int length;
    private final int width;
    private final int height;

    private Present(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    static Present parse(String dimensions) {
        var tokenizer = new StringTokenizer(dimensions, "x");
        return new Present(parseInt(tokenizer.nextToken()), parseInt(tokenizer.nextToken()), parseInt(tokenizer.nextToken()));
    }

    int surface() {
        return 2 * IntStream.of(length * width, width * height, height * length).sum();
    }

    int areaOfSmallestSide() {
        return IntStream.of(length * width, length * height, width * height).min().getAsInt();
    }

    int smallestPerimeter() {
        return 2 * IntStream.of(length + width, length + height, width + height).min().getAsInt();
    }

    int volume() {
        return length * width * height;
    }
}

