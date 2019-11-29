package day11;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PasswordGenerator {

    private static final String ILLEGAL_CHARACTERS = "iol";

    String increment(String password) {
        char[] chars = password.toCharArray();
        int idx = chars.length - 1;
        while (chars[idx] == 'z') {
            chars[idx] = 'a';
            idx--;
        }
        chars[idx]++;
        return new String(chars);
    }

    boolean hasIncreasingStraightOfThreeLetters(String candidate) {
        return IntStream.range(0, candidate.length() - 2)
                .anyMatch(idx -> isStraightOfThree(candidate, idx));
    }

    private boolean isStraightOfThree(String candidate, int idx) {
        return candidate.charAt(idx + 1) - candidate.charAt(idx) == 1 && candidate.charAt(idx + 2) - candidate.charAt(idx + 1) == 1;
    }

    boolean containsIllegalCharacters(String candidate) {
        return candidate.chars().map(ILLEGAL_CHARACTERS::indexOf).anyMatch(idx -> idx >= 0);
    }

    boolean containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(String candidate) {
        IntSummaryStatistics statistics = IntStream.range(0, candidate.length() - 1)
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
        return Stream.iterate(increment(password), this::increment)
                .filter(this::isValidPassword)
                .findFirst()
                .orElseThrow();
    }

    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        String next = generator.nextPassword("cqjxjnds");
        System.out.printf("Next password (part 1): %s\n", next);
        System.out.printf("Next password (part 2): %s\n", generator.nextPassword(next));
    }
}
