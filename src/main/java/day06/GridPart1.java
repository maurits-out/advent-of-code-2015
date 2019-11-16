package day06;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;

class GridPart1 extends Grid {
    private final Set<Integer> lights = new HashSet<>();
    private final Map<String, IntConsumer> instructions = Map.of(
            "turn on", lights::add,
            "turn off", lights::remove,
            "toggle", id -> {
                if (lights.contains(id)) {
                    lights.remove(id);
                } else {
                    lights.add(id);
                }
            });

    @Override
    public void handle(String instruction, int xLeft, int yTop, int xRight, int yBottom) {
        updateLights(xLeft, yTop, xRight, yBottom, instructions.get(instruction));
    }

    @Override
    public long getResult() {
        return lights.size();
    }
}
