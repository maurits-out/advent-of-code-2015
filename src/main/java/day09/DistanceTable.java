package day09;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class DistanceTable {

    private final Map<String, Integer> distances = new HashMap<>();
    private final Set<String> locations = new HashSet<>();

    DistanceTable(Stream<String> lines) {
        var pattern = Pattern.compile("^(.+) to (.+) = (\\d+)$");
        lines.map(pattern::matcher).filter(Matcher::find).forEach(matcher -> {
            var location1 = matcher.group(1);
            var location2 = matcher.group(2);
            int distance = Integer.parseInt(matcher.group(3));
            locations.add(location1);
            locations.add(location2);
            distances.put(location1 + "-" + location2, distance);
            distances.put(location2 + "-" + location1, distance);
        });
    }

    int distanceBetween(String location1, String location2) {
        return distances.get(location1 + "-" + location2);
    }

    public Set<String> getLocations() {
        return locations;
    }
}
