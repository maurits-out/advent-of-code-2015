package day05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NiceStringsTest {

    private final NiceStrings niceStrings = new NiceStrings();

    @ParameterizedTest
    @CsvSource({"ugknbfddgicrmopn", "aaa"})
    void niceAccordingToRuleOne(String string) {
        assertTrue(niceStrings.ruleOne(string));
    }

    @ParameterizedTest
    @CsvSource({"jchzalrnumimnmhp", "haegwjzuvuyypxyu", "dvszwmarrgswjxmb"})
    void naughtyAccordingToRuleOne(String string) {
        assertFalse(niceStrings.ruleOne(string));
    }

    @ParameterizedTest
    @CsvSource({"qjhvhtzxzqqjkmpb", "xxyxx"})
    void niceAccordingToRuleTwo(String string) {
        assertTrue(niceStrings.ruleTwo(string));
    }

    @ParameterizedTest
    @CsvSource({"uurcxstgmygtbstg", "ieodomkazucvgmuy"})
    void naughtyAccordingToRuleTwo(String string) {
        assertFalse(niceStrings.ruleOne(string));
    }
}
