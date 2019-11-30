package day12;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.util.stream.StreamSupport.stream;
import static support.InputLoader.loadInput;

public class BalancingBooks {

    private final JSONArray input;

    public BalancingBooks(String source) {
        input = new JSONArray(source);
    }

    int getSumOfAllNumbers() {
        return sum(input, false);
    }

    int getSumOfAllNumbersIgnoringRed() {
        return sum(input, true);
    }

    private int sum(Object object, boolean ignoreRed) {
        if (object instanceof JSONArray) {
            return sum((JSONArray) object, ignoreRed);
        }
        if (object instanceof JSONObject) {
            return sum((JSONObject) object, ignoreRed);
        }
        if (object instanceof String) {
            return 0;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        throw new IllegalStateException("Unsupported class: " + object.getClass().getSimpleName());
    }

    private int sum(JSONObject jsonObject, boolean ignoreRed) {
        if (ignoreRed && hasValueRed(jsonObject)) {
            return 0;
        }
        return jsonObject
                .keySet()
                .stream()
                .map(jsonObject::get)
                .mapToInt(obj -> sum(obj, ignoreRed))
                .sum();
    }

    private boolean hasValueRed(JSONObject jsonObject) {
        return jsonObject
                .keySet()
                .stream()
                .map(jsonObject::get)
                .anyMatch(obj -> obj.equals("red"));
    }

    private int sum(JSONArray jsonArray, boolean ignoreRed) {
        return stream(jsonArray.spliterator(), true)
                .mapToInt(obj -> sum(obj, ignoreRed))
                .sum();
    }

    public static void main(String[] args) {
        String json = loadInput("day12-input.txt");
        var books = new BalancingBooks(json);
        System.out.printf("Sum of all numbers in the document (part 1): %d\n", books.getSumOfAllNumbers());
        System.out.printf("Sum of all numbers in the document (part 2): %d\n", books.getSumOfAllNumbersIgnoringRed());
    }
}
