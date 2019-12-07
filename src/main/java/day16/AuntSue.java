package day16;

import java.util.Map;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;
import static java.util.stream.IntStream.range;
import static support.InputLoader.loadInput;

public class AuntSue {

    private final static Pattern PATTERN = compile("Sue \\d+: (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");

    private final AuntSueCharacteristics[] characteristics;

    public AuntSue(String input) {
        characteristics = input.lines()
                .map(PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher ->
                        new AuntSueCharacteristics(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6))
                )
                .toArray(AuntSueCharacteristics[]::new);
    }

    private int findMatchingAuntSuePart1(Map<String, Integer> tickerTape) {
        return findMatchingAuntSue(i -> characteristics[i].matchesPart1(tickerTape));
    }

    private int findMatchingAuntSuePart2(Map<String, Integer> tickerTape) {
        return findMatchingAuntSue(i -> characteristics[i].matchesPart2(tickerTape));
    }

    private int findMatchingAuntSue(IntPredicate intPredicate) {
        return 1 + range(0, characteristics.length)
                .filter(intPredicate)
                .findFirst()
                .orElseThrow();
    }

    public static void main(String[] args) {
        var input = loadInput("day16-input.txt");
        AuntSue auntSue = new AuntSue(input);
        Map<String, Integer> tickerTape = Map.of("children", 3, "cats", 7, "samoyeds", 2, "pomeranians", 3, "akitas", 0, "vizslas", 0, "goldfish", 5, "trees", 3, "cars", 2, "perfumes", 1);
        System.out.printf("Number of Sue (part 1): %d\n", auntSue.findMatchingAuntSuePart1(tickerTape));
        System.out.printf("Number of Sue (part 2): %d\n", auntSue.findMatchingAuntSuePart2(tickerTape));
    }
}

class AuntSueCharacteristics {

    private final Map<String, Integer> characteristics;

    AuntSueCharacteristics(String characteristic1, String value1, String characteristic2, String value2, String characteristic3, String value3) {
        this.characteristics = Map.of(
                characteristic1, parseInt(value1),
                characteristic2, parseInt(value2),
                characteristic3, parseInt(value3)
        );
    }

    public boolean matchesPart1(Map<String, Integer> tickerTape) {
        return tickerTape.keySet().stream().allMatch(name -> !characteristics.containsKey(name) || characteristics.get(name).equals(tickerTape.get(name)));
    }

    public boolean matchesPart2(Map<String, Integer> tickerTape) {
        return tickerTape.keySet().stream().allMatch(name -> {
            if (!characteristics.containsKey(name)) {
                return true;
            }
            if (name.equals("cats") || name.equals("trees")) {
                return characteristics.get(name) > tickerTape.get(name);
            }
            if (name.equals("pomeranians") || name.equals("goldfish")) {
                return characteristics.get(name) < tickerTape.get(name);
            }
            return characteristics.get(name).equals(tickerTape.get(name));
        });
    }
}