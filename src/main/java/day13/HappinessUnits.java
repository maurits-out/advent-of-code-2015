package day13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

class HappinessUnits {

    private final Map<String, Integer> happiness = new HashMap<>();
    private final Set<String> attendees = new HashSet<>();

    HappinessUnits(String input) {
        var pattern = Pattern.compile("^(.+) would (.+) (\\d+) happiness units by sitting next to (.+).$");
        input.lines()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .forEach(matcher -> {
                    var attendee = matcher.group(1);
                    var multiplier = "gain".equals(matcher.group(2)) ? 1 : -1;
                    var units = multiplier * parseInt(matcher.group(3));
                    var neighbour = matcher.group(4);
                    attendees.add(attendee);
                    attendees.add(neighbour);
                    happiness.put(attendee + "-" + neighbour, units);
                });
        attendees.forEach(att -> {
            happiness.put("self-" + att, 0);
            happiness.put(att + "-self", 0);
        });
    }

    int happiness(String attendee, String neighbour) {
        return happiness.get(attendee + "-" + neighbour);
    }

    public Set<String> getAttendees(boolean includeSelf) {
        Set<String> result = new HashSet<>(attendees);
        if (includeSelf) {
            result.add("self");
        }
        return result;
    }
}
