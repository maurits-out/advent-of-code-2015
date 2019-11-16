package day01;

import static support.InputLoader.loadInput;

public class NotQuiteLisp {

    int floor(String instructions) {
        return instructions
                .chars()
                .map(this::convertInstruction)
                .sum();
    }

    int basementPosition(String instructions) {
        var pos = 0;
        for (var currentLevel = 0; currentLevel >= 0; pos++) {
            currentLevel += convertInstruction(instructions.charAt(pos));
        }
        return pos;
    }

    private int convertInstruction(int ch) {
        return switch (ch) {
            case '(' -> 1;
            case ')' -> -1;
            default -> throw new IllegalStateException("Unexpected character: " + ch);
        };
    }

    public static void main(String[] args) {
        var notQuiteLisp = new NotQuiteLisp();
        var input = loadInput("day01-input.txt");

        System.out.printf("Floor: %d\n", notQuiteLisp.floor(input));
        System.out.printf("Position of basement: %d\n", notQuiteLisp.basementPosition(input));
    }
}
