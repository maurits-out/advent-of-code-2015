package day06;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

abstract class Grid {
    private static final int GRID_DIMENSION = 1000;

    abstract void handle(String instructions, int xLeft, int yTop, int xRight, int yBottom);

    abstract long getResult();

    final void updateLights(int xLeft, int yTop, int xRight, int yBottom, IntConsumer consumer) {
        IntStream
                .rangeClosed(yTop, yBottom)
                .flatMap(y -> IntStream
                        .rangeClosed(xLeft, xRight)
                        .map(x -> (y * GRID_DIMENSION) + x))
                .forEach(consumer);
    }
}
