package day05;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;
import static support.InputLoader.loadInput;

class NiceStrings {

    private static final String VOWELS = "aeiou";
    private static final List<String> FORBIDDEN_STRINGS = List.of("ab", "cd", "pq", "xy");

    boolean ruleOne(String string) {
        return hasAtLeastThreeVowels(string) && hasLetterThatAppearsTwiceInRow(string) && !containsForbiddenStrings(string);
    }

    boolean ruleTwo(String string) {
        return containsPairThatAppearsAtLeastTwiceWithoutOverlapping(string) && containsAtLeastOneLetterWhichRepeatsWithOneLetterInBetween(string);
    }

    private long count(Stream<String> strings, Predicate<String> rule) {
        return strings.filter(rule).count();
    }

    private Stream<String> extractPairs(String string) {
        return range(0, string.length() - 1)
                .boxed()
                .map(idx -> string.substring(idx, idx + 2));
    }

    private boolean containsForbiddenStrings(String string) {
        return extractPairs(string).anyMatch(FORBIDDEN_STRINGS::contains);
    }

    private boolean occursAtLeastTwiceWithoutOverlapping(String string, String pair) {
        var statistics = range(0, string.length() - 1)
                .filter(idx -> string.substring(idx).startsWith(pair))
                .summaryStatistics();
        return statistics.getMax() - statistics.getMin() > 1;
    }

    private boolean hasLetterThatAppearsTwiceInRow(String string) {
        return extractPairs(string).anyMatch(s -> s.charAt(0) == s.charAt(1));
    }

    private boolean containsPairThatAppearsAtLeastTwiceWithoutOverlapping(String string) {
        return extractPairs(string).anyMatch(pair -> occursAtLeastTwiceWithoutOverlapping(string, pair));
    }

    private boolean containsAtLeastOneLetterWhichRepeatsWithOneLetterInBetween(String string) {
        return range(0, string.length() - 2)
                .anyMatch(idx -> string.charAt(idx) == string.charAt(idx + 2));
    }

    private boolean hasAtLeastThreeVowels(String string) {
        return string
                .chars()
                .filter(ch -> VOWELS.indexOf(ch) >= 0)
                .count() >= 3;
    }

    public static void main(String[] args) {
        var niceStrings = new NiceStrings();
        var input = loadInput("day05-input.txt");
        System.out.printf("Number of nice strings (first rule) : %d\n", niceStrings.count(input.lines(), niceStrings::ruleOne));
        System.out.printf("Number of nice strings (second rule): %d\n", niceStrings.count(input.lines(), niceStrings::ruleTwo));
    }
}
