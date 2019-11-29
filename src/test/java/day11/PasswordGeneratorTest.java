package day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @ParameterizedTest
    @CsvSource({"xx, xy", "xy, xz", "xz, ya", "ya, yb", "yzz, zaa"})
    void increment(String input, String expected) {
        assertEquals(expected, passwordGenerator.increment(input));
    }

    @ParameterizedTest
    @CsvSource({"hijklmmn", "aabbabc"})
    void increasingStraight(String input) {
        assertTrue(passwordGenerator.hasIncreasingStraightOfThreeLetters(input));
    }

    @ParameterizedTest
    @CsvSource({"abbceffg", "abbcegjk"})
    void noIncreasingStraight(String input) {
        assertFalse(passwordGenerator.hasIncreasingStraightOfThreeLetters(input));
    }

    @ParameterizedTest
    @CsvSource({"hijklmmn", "aobcegjk", "abbcegjkl"})
    void containsIllegalCharacter(String input) {
        assertTrue(passwordGenerator.containsIllegalCharacters(input));
    }

    @ParameterizedTest
    @CsvSource({"abbceffg"})
    void containsNoIllegalCharacters(String input) {
        assertFalse(passwordGenerator.containsIllegalCharacters(input));
    }

    @ParameterizedTest
    @CsvSource({"abbceffg", "abcdffaa"})
    void containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(String input) {
        assertTrue(passwordGenerator.containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(input));
    }

    @ParameterizedTest
    @CsvSource({"hijklmmn", "aobcegjk"})
    void containsNoTwoDifferentNonOverlappingPairsOfCharacters(String input) {
        assertFalse(passwordGenerator.containsAtLeastTwoDifferentNonOverlappingPairsOfCharacters(input));
    }

    @ParameterizedTest
    @CsvSource({"abcdefgh, abcdffaa", "ghijklmn, ghjaabcc"})
    void nextPassword(String current, String expected) {
        assertEquals(expected, passwordGenerator.nextPassword(current));
    }
}