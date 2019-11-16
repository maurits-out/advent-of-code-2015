package day04;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.charset.StandardCharsets.US_ASCII;

public class IdealStockingStuffer {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        var hexChars = new char[bytes.length * 2];
        for (var i = 0; i < bytes.length; i++) {
            var v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private Predicate<Integer> validHash(String secret, String prefix) throws NoSuchAlgorithmException {
        var digest = MessageDigest.getInstance("MD5");
        return number -> {
            var input = secret + number;
            var bytes = digest.digest(input.getBytes(US_ASCII));
            return bytesToHex(bytes).startsWith(prefix);
        };
    }

    int findInputForHash(String secret, String prefix) throws NoSuchAlgorithmException {
        var validHash = validHash(secret, prefix);
        return IntStream.rangeClosed(0, MAX_VALUE)
                .filter(validHash::test)
                .findFirst()
                .orElseThrow();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        var stuffer = new IdealStockingStuffer();
        System.out.printf("Input for hash starting with 5 zero's is %d\n", stuffer.findInputForHash("yzbqklnj", "00000"));
        System.out.printf("Input for hash starting with 6 zero's is %d\n", stuffer.findInputForHash("yzbqklnj", "000000"));
    }
}
