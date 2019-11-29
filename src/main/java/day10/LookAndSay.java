package day10;

public class LookAndSay {

    String nextSequence(String value) {
        StringBuilder result = new StringBuilder();
        for (int idx = 1, count = 1; idx <= value.length(); idx++, count++) {
            char prev = value.charAt(idx - 1);
            if (idx == value.length() || prev != value.charAt(idx)) {
                result.append(count).append(prev);
                count = 0;
            }
        }
        return result.toString();
    }

    int lengthOfResult(String value, int times) {
        String current = value;
        for (int count = 0; count < times; count++) {
            current = nextSequence(current);
        }
        return current.length();
    }

    public static void main(String[] args) {
        LookAndSay lookAndSay = new LookAndSay();
        System.out.printf("Length of the result (part 1): %d\n", lookAndSay.lengthOfResult("1113222113", 40));
        System.out.printf("Length of the result (part 2): %d\n", lookAndSay.lengthOfResult("1113222113", 50));
    }
}
