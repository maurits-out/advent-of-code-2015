package day06;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GridPart2 extends Grid {

    private final Map<Integer, Integer> brightness = new HashMap<>();

    private static final Map<String, Function<Integer, Integer>> INSTRUCTIONS = Map.of(
            "turn on", x -> x + 1,
            "turn off", x -> x > 0 ? x - 1 : x,
            "toggle", x -> x + 2);

    @Override
    public void handle(String instruction, int xLeft, int yTop, int xRight, int yBottom) {
        updateLights(xLeft, yTop, xRight, yBottom, i -> {
            Integer current = brightness.getOrDefault(i, 0);
            brightness.put(i, INSTRUCTIONS.get(instruction).apply(current));
        });
    }

    @Override
    public long getResult() {
        return brightness.values().stream().mapToInt(i -> i).sum();
    }
}
