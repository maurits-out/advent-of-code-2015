package day07;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static support.InputLoader.loadInput;

public class Assembly {

    private static final Pattern INSTRUCTION = Pattern.compile("^(.+) -> (.+)$");
    private static final Pattern CONSTANT = Pattern.compile("^([0-9]+)$");
    private static final Pattern WIRE = Pattern.compile("^([a-z]+)$");
    private static final Pattern NOT_OP = Pattern.compile("^NOT (.+)$");
    private static final Pattern R_SHIFT_OP = Pattern.compile("^(.+) RSHIFT (.+)$");
    private static final Pattern L_SHIFT_OP = Pattern.compile("^(.+) LSHIFT (.+)$");
    private static final Pattern AND_OP = Pattern.compile("^(.+) AND (.+)$");
    private static final Pattern OR_OP = Pattern.compile("^(.+) OR (.+)$");

    private final Map<String, Signal> circuitTable;

    Assembly(Stream<String> lines) {
        circuitTable = parse(lines);
    }

    Map<String, Integer> getSignalOfAllWires() {
        Map<String, Integer> wireValues = new HashMap<>();
        circuitTable.forEach((wire, signal) -> {
            var value = signal.compute(circuitTable, wireValues);
            wireValues.put(wire, value);
        });
        return wireValues;
    }

    private int part1() {
        return getSignalOfWireA(new HashMap<>());
    }

    private int part2(int a) {
        Map<String, Integer> wireValues = new HashMap<>();
        wireValues.put("b", a);
        return getSignalOfWireA(wireValues);
    }

    private int getSignalOfWireA(Map<String, Integer> wireValues) {
        var signal = circuitTable.get("a");
        return signal.compute(circuitTable, wireValues);
    }

    private Map<String, Signal> parse(Stream<String> lines) {
        Map<String, Signal> circuitTable = new HashMap<>();
        lines.forEach(line -> {
            Matcher matcher = INSTRUCTION.matcher(line);
            if (matcher.find()) {
                var expression = matcher.group(1);
                var wire = matcher.group(2);
                circuitTable.put(wire, parseSignal(expression));
            } else {
                throw new IllegalStateException("Could not parse line: " + line);
            }
        });
        return circuitTable;
    }

    private Signal parseSignal(String expr) {
        var matcher = CONSTANT.matcher(expr);
        if (matcher.find()) {
            return new Constant(parseInt(matcher.group()));
        }
        matcher = WIRE.matcher(expr);
        if (matcher.find()) {
            return new Wire(matcher.group());
        }
        matcher = R_SHIFT_OP.matcher(expr);
        if (matcher.find()) {
            return new RShiftGate(parseSignal(matcher.group(1)), parseSignal(matcher.group(2)));
        }
        matcher = L_SHIFT_OP.matcher(expr);
        if (matcher.find()) {
            return new LShiftGate(parseSignal(matcher.group(1)), parseSignal(matcher.group(2)));
        }
        matcher = NOT_OP.matcher(expr);
        if (matcher.find()) {
            return new NotGate(parseSignal(matcher.group(1)));
        }
        matcher = AND_OP.matcher(expr);
        if (matcher.find()) {
            return new AndGate(parseSignal(matcher.group(1)), parseSignal(matcher.group(2)));
        }
        matcher = OR_OP.matcher(expr);
        if (matcher.find()) {
            return new OrGate(parseSignal(matcher.group(1)), parseSignal(matcher.group(2)));
        }
        throw new IllegalArgumentException("Could not parse expression: " + expr);
    }

    public static void main(String[] args) {
        var input = loadInput("day07-input.txt");
        var assembly = new Assembly(input.lines());
        var a = assembly.part1();
        System.out.printf("Part 1: signal provided to wire a: %d\n", a);
        System.out.printf("Part 2: signal provided to wire a: %d\n", assembly.part2(a));
    }
}

abstract class Signal {

    abstract int compute(Map<String, Signal> circuitTable, Map<String, Integer> wireValues);
}

class Wire extends Signal {

    private final String id;

    Wire(String id) {
        this.id = id;
    }

    @Override
    int compute(Map<String, Signal> circuitTable, Map<String, Integer> wireValues) {
        if (wireValues.containsKey(id)) {
            return wireValues.get(id);
        }
        var signal = circuitTable.get(id);
        var result = signal.compute(circuitTable, wireValues);
        wireValues.put(id, result);
        return result;
    }
}

class Constant extends Signal {

    private final int value;

    Constant(int value) {
        this.value = value;
    }

    @Override
    int compute(Map<String, Signal> circuitTable, Map<String, Integer> wireValues) {
        return value;
    }
}

class NotGate extends Signal {

    private final Signal signal;

    NotGate(Signal signal) {
        this.signal = signal;
    }

    @Override
    int compute(Map<String, Signal> circuitTable, Map<String, Integer> wireValues) {
        return ~signal.compute(circuitTable, wireValues) & 0xFFFF;
    }
}

abstract class BiGate extends Signal {

    private final Signal left;
    private final Signal right;

    BiGate(Signal left, Signal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    int compute(Map<String, Signal> circuitTable, Map<String, Integer> wireValues) {
        return apply(left.compute(circuitTable, wireValues), right.compute(circuitTable, wireValues));
    }

    abstract int apply(int left, int right);
}

class RShiftGate extends BiGate {

    RShiftGate(Signal left, Signal right) {
        super(left, right);
    }

    @Override
    int apply(int left, int right) {
        return left >> right;
    }
}

class LShiftGate extends BiGate {

    LShiftGate(Signal left, Signal right) {
        super(left, right);
    }

    @Override
    int apply(int left, int right) {
        return left << right;
    }
}

class AndGate extends BiGate {

    AndGate(Signal left, Signal right) {
        super(left, right);
    }

    @Override
    int apply(int left, int right) {
        return left & right;
    }
}

class OrGate extends BiGate {

    OrGate(Signal left, Signal right) {
        super(left, right);
    }

    @Override
    int apply(int left, int right) {
        return left | right;
    }
}