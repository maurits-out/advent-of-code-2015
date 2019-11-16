package day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WrappingPaperTest {

    private final WrappingPaper wrappingPaper = new WrappingPaper();

    @ParameterizedTest
    @CsvSource({"2x3x4, 58", "1x1x10, 43"})
    void totalWrappingPaper(String dimensions, int expectedTotalWrappingPaper) {
        assertEquals(expectedTotalWrappingPaper, wrappingPaper.totalWrappingPaper(dimensions));
    }

    @ParameterizedTest
    @CsvSource({"2x3x4, 34", "1x1x10, 14"})
    void totalRibbon(String dimensions, int expectedTotalRibbon) {
        assertEquals(expectedTotalRibbon, wrappingPaper.totalRibbon(dimensions));
    }
}