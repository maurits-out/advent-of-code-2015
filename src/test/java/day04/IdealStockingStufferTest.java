package day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdealStockingStufferTest {

    private final IdealStockingStuffer stuffer = new IdealStockingStuffer();

    @ParameterizedTest
    @CsvSource({"abcdef, 00000, 609043", "pqrstuv, 00000, 1048970"})
    void findInputForHash(String secret, String prefix, int expected) throws NoSuchAlgorithmException {
        assertEquals(expected, stuffer.findInputForHash(secret, prefix));
    }
}