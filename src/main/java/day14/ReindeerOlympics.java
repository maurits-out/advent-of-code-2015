package day14;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class ReindeerOlympics {

    private static final Pattern PATTERN = compile("^[a-zA-Z]+ can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.$");

    private final List<Reindeer> reindeer;

    ReindeerOlympics(String input) {
        reindeer = parseLines(input);
    }

    void travel(int seconds) {
        range(0, seconds).forEach(i -> {
            reindeer.forEach(Reindeer::tick);
            var distance = getDistanceOfReindeerInLead();
            reindeer.stream()
                    .filter(r -> r.getDistanceTravelled() == distance)
                    .forEach(Reindeer::awardPoint);
        });
    }

    int getDistanceOfReindeerInLead() {
        return reindeer.stream().mapToInt(Reindeer::getDistanceTravelled).max().orElseThrow();
    }

    int getPointsOfWinningReindeer() {
        return reindeer.stream().mapToInt(Reindeer::getPoints).max().orElseThrow();
    }

    private List<Reindeer> parseLines(String input) {
        return input.lines()
                .map(PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> {
                    var speedInKmPerSec = parseInt(matcher.group(1));
                    var flyingTimeInSec = parseInt(matcher.group(2));
                    var restingTimeInSec = parseInt(matcher.group(3));
                    return new Reindeer(speedInKmPerSec, flyingTimeInSec, restingTimeInSec);
                })
                .collect(toList());
    }

    public static void main(String[] args) {
        var input = """
                Vixen can fly 8 km/s for 8 seconds, but then must rest for 53 seconds.
                Blitzen can fly 13 km/s for 4 seconds, but then must rest for 49 seconds.
                Rudolph can fly 20 km/s for 7 seconds, but then must rest for 132 seconds.
                Cupid can fly 12 km/s for 4 seconds, but then must rest for 43 seconds.
                Donner can fly 9 km/s for 5 seconds, but then must rest for 38 seconds.
                Dasher can fly 10 km/s for 4 seconds, but then must rest for 37 seconds.
                Comet can fly 3 km/s for 37 seconds, but then must rest for 76 seconds.
                Prancer can fly 9 km/s for 12 seconds, but then must rest for 97 seconds.
                Dancer can fly 37 km/s for 1 seconds, but then must rest for 36 seconds.
                """;
        var olympics = new ReindeerOlympics(input);
        olympics.travel(2503);
        System.out.printf("Distance travelled by winning reindeer (part 1): %d\n", olympics.getDistanceOfReindeerInLead());
        System.out.printf("Points by winning reindeer (part 2): %d\n", olympics.getPointsOfWinningReindeer());
    }
}
