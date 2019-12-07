package day15;

import day15.Ingredient.Property;

import java.util.Map;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static day15.Ingredient.Property.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.rangeClosed;

class Cookies {

    private static final Pattern PATTERN = Pattern.compile("(\\w+): capacity (-?[0-9]+), durability (-?[0-9]+), flavor (-?[0-9]+), texture (-?[0-9]+), calories (-?[0-9]+)");
    private static final int NUM_TEASPOONS = 100;

    private final Map<String, Ingredient> ingredients;

    public Cookies(String input) {
        ingredients = parseIngredients(input);
    }

    private Map<String, Ingredient> parseIngredients(String input) {
        return input.lines()
                .map(PATTERN::matcher)
                .filter(Matcher::find)
                .collect(toMap(
                        matcher -> matcher.group(1),
                        matcher -> new Ingredient(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6))));
    }

    private int totalValueOfProperty(Property property, Map<String, Integer> teaspoons) {
        int sum = teaspoons.keySet().stream()
                .mapToInt(name -> ingredients.get(name).getProperty(property) * teaspoons.get(name))
                .sum();
        return max(sum, 0);
    }

    private int totalScore(Map<String, Integer> teaspoons) {
        return Stream.of(Property.values()).filter(p -> p != CALORIES).reduce(1,
                (acc, property) -> acc * totalValueOfProperty(property, teaspoons),
                (acc1, acc2) -> acc1 * acc2);
    }

    private Stream<Map<String, Integer>> teaspoons() {
        var names = ingredients.keySet().toArray(new String[0]);
        return rangeClosed(0, NUM_TEASPOONS).boxed()
                .flatMap(i -> rangeClosed(0, NUM_TEASPOONS - i).boxed()
                        .flatMap(j -> rangeClosed(0, NUM_TEASPOONS - i - j)
                                .mapToObj(k -> {
                                    var l = NUM_TEASPOONS - i - j - k;
                                    return Map.of(names[0], i, names[1], j, names[2], k, names[3], l);
                                })
                        )
                );
    }

    private int totalScoreOfHighestScoringCookie(IntPredicate calorieFilter) {
        return teaspoons()
                .filter(teaspoons -> calorieFilter.test(totalValueOfProperty(CALORIES, teaspoons)))
                .mapToInt(this::totalScore)
                .max()
                .orElseThrow();
    }

    public static void main(String[] args) {
        String input = """
                       Sprinkles: capacity 2, durability 0, flavor -2, texture 0, calories 3
                       Butterscotch: capacity 0, durability 5, flavor -3, texture 0, calories 3
                       Chocolate: capacity 0, durability 0, flavor 5, texture -1, calories 8
                       Candy: capacity 0, durability -1, flavor 0, texture 5, calories 8
                       """;

        Cookies cookies = new Cookies(input);
        System.out.printf("Total score of highest scoring cookie (part 1): %d\n",
                cookies.totalScoreOfHighestScoringCookie(calories -> true));
        System.out.printf("Total score of highest scoring cookie with 500 calories (part 2): %d\n",
                cookies.totalScoreOfHighestScoringCookie(calories -> calories == 500));
    }
}

class Ingredient {
    private final Map<Property, Integer> properties;

    enum Property {
        CAPACITY,
        DURABILITY,
        FLAVOR,
        TEXTURE,
        CALORIES
    }

    public Ingredient(String capacity, String durability, String flavor, String texture, String calories) {
        this.properties = Map.of(
                CAPACITY, parseInt(capacity),
                DURABILITY, parseInt(durability),
                FLAVOR, parseInt(flavor),
                TEXTURE, parseInt(texture),
                CALORIES, parseInt(calories));
    }

    public Integer getProperty(Property property) {
        return properties.get(property);
    }
}