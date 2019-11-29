package day11;

import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.iterate;

public class PasswordGenerator {

    private static final String ILLEGAL_CHARACTERS = "iol";

    String increment(String password) {
        var chars = password.toCharArray();
        var idx = chars.length - 1;
        while (chars[idx] == 'z') {
            chars[idx] = 'a';
            idx--;
        }
        chars[idx]++;
        return new String(chars);
    }

    boolean hasIncreasingStraightOfThreeLetters(String candidate) {
        return range(0, candidate.length() - 2)
                .anyMatch(idx -> isStraightOfThree(candidate, idx));
    }

    private boolean isStraightOfThree(String candidate, int idx) {
        return candidate.charAt(idx + 1) - candidate.charAt(idx) == 1 &&
                candidate.charAt(idx + 2) - candidate.charAt(idx + 1) == 1;
    }

    boolean containsIllegalCharacters(String candidate) {
        return candidate.chars().map(ILLEGAL_CHARACTERS::indexOf).anyMatch(idx -> idx >= 0);
    }

    boolean containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(String candidate) {
        var statistics = range(0, candidate.length() - 1)
                .filter(idx -> candidate.charAt(idx) == candidate.charAt(idx + 1))
                .summaryStatistics();
        return statistics.getMax() - statistics.getMin() > 1;
    }

    private boolean isValidPassword(String candidate) {
        return hasIncreasingStraightOfThreeLetters(candidate) &&
                !containsIllegalCharacters(candidate) &&
                containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(candidate);
    }

    String nextPassword(String password) {
        return iterate(increment(password), this::increment)
                .filter(this::isValidPassword)
                .findFirst()
                .orElseThrow();
    }

    public static void main(String[] args) {
        var generator = new PasswordGenerator();
        var next = generator.nextPassword("cqjxjnds");
        System.out.printf("Next password (part 1): %s\n", next);
        System.out.printf("Next password (part 2): %s\n", generator.nextPassword(next));
    }
}
